package bootey.io;

import bootey.dto.ChallengeModel;
import bootey.dto.Demon;
import bootey.utils.Constants;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Log4j2
public class DataParser {

    private DataParser() {
    }

    public static ChallengeModel fromFile(File file) {
        ChallengeModel challenge = new ChallengeModel();
        String data;
        String[] splitData;

        Scanner scanner;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        //TODO: decide how to scan and save data

        // first line
        data = scanner.nextLine();
        splitData = data.split(" ");
        challenge.setSi(Integer.parseInt(splitData[0]));
        challenge.setSmax(Integer.parseInt(splitData[1]));
        challenge.setT(Integer.parseInt(splitData[2]));
        challenge.setD(Integer.parseInt(splitData[3]));

        // next 'till end

        challenge.setListDemons(new ArrayList<>());

        List<Demon> demonList = new ArrayList<>();
        int libIxd = 0;
        while (scanner.hasNextLine()) {
            // first line
            data = scanner.nextLine();
            splitData = data.split(" ");
            //log.debug("Demon {}", libIxd);

            Integer Id = libIxd;
            Integer Sc = Integer.parseInt(splitData[0]);
            Integer Tr = Integer.parseInt(splitData[1]);
            Integer Sr = Integer.parseInt(splitData[2]);
            Integer Na = Integer.parseInt(splitData[3]);

            List<Integer> fragments = new ArrayList<>();

            for (int i = 4; i < 4 + Na; i++) {
                fragments.add(Integer.parseInt(splitData[i]));
            }

            Demon p = new Demon(Id, Sc, Tr, Sr, Na, fragments);

            demonList.add(p);
            libIxd++;
        }
        challenge.setListDemons(demonList);


        scanner.close();
        return challenge;
    }

    public static void toFile(ChallengeModel challenge, String fileName) throws IOException {

        String prefix = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date(System.currentTimeMillis()));
        String outputFilename = prefix + "_" + fileName;

        File directory = new File(Constants.OUTPUT_FOLDER);
        if (!directory.exists()) {
            directory.mkdir(); //create output_folder if it doesn't exist
        }

        try (FileWriter writer = new FileWriter(new File(Constants.OUTPUT_FOLDER, outputFilename))) {

            writer.write(challenge.toChallengeOutput());

        } catch (IOException e) {
            log.error("error: ", e);
        }

        String out = Constants.OUTPUT_FOLDER + "/test.zip";
        zipSourceCode(out, "pom.xml",
                "java",
                "resources");
    }

    private static void zipSourceCode(final String zipFilePath, final String... sourceDirPaths) {

        Path filePath = Paths.get(zipFilePath);
        try (ZipOutputStream zs = new ZipOutputStream(Files.newOutputStream(filePath))) {
            for (String sourceDirPath : sourceDirPaths) {
                Files.walk(Paths.get(sourceDirPath))
                        .filter(path -> !Files.isDirectory(path) && !path.getFileName().toString().equals(".DS_store"))
                        .forEach(path -> {
                            try {
                                zs.putNextEntry(new ZipEntry(path.toString()));
                                Files.copy(path, zs);
                                zs.closeEntry();
                            } catch (IOException e) {
                                log.error("err");
                            }
                        });
            }
            zs.finish();
            Files.deleteIfExists(filePath);
            log.debug("file zipped, zipoutstream closed");
        } catch (IOException e) {
            log.error("err: exception of class {} , message: {}", e.getClass().getSimpleName(), e.getLocalizedMessage());
        }
    }

}
