package bootey.io;

import bootey.dto.Antenna;
import bootey.dto.Building;
import bootey.dto.ChallengeModel;
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

        // first line
        data = scanner.nextLine();
        splitData = data.split(" ");
        challenge.setW(Integer.parseInt(splitData[0]));
        challenge.setH(Integer.parseInt(splitData[1]));

        // second line
        data = scanner.nextLine();
        splitData = data.split(" ");
        challenge.setN(Integer.parseInt(splitData[0]));
        challenge.setM(Integer.parseInt(splitData[1]));
        challenge.setR(Integer.parseInt(splitData[2]));


        List<Building> buildings = new ArrayList<>();
        for (int i = 0; i < challenge.getN(); i++) {
            // first line
            data = scanner.nextLine();
            splitData = data.split(" ");
            // log.debug("Demon {}", libIxd);

            Integer x = Integer.parseInt(splitData[0]);
            Integer y = Integer.parseInt(splitData[1]);
            Integer l = Integer.parseInt(splitData[2]);
            Integer c = Integer.parseInt(splitData[3]);

            buildings.add(new Building(x, y, l, c));

        }
        challenge.setBuildingList(buildings);

        List<Antenna> antennas = new ArrayList<>();
        for (int i = 0; i < challenge.getM(); i++) {
            // first line
            data = scanner.nextLine();
            splitData = data.split(" ");
            // log.debug("Demon {}", libIxd);

            Integer r = Integer.parseInt(splitData[0]);
            Integer c = Integer.parseInt(splitData[1]);

            antennas.add(new Antenna(i, r, c));

        }
        challenge.setAntennaList(antennas);

        scanner.close();
        return challenge;
    }

    public static void toFile(ChallengeModel challenge, String fileName) {

        String prefix = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date(System.currentTimeMillis()));
        String outputFilename = prefix + "_" + fileName;

        File directory = new File(Constants.OUTPUT_FOLDER);
        if (!directory.exists()) {
            directory.mkdir(); // create output_folder if it doesn't exist
        }

        try (FileWriter writer = new FileWriter(new File(Constants.OUTPUT_FOLDER, outputFilename))) {

            writer.write(challenge.toChallengeOutput());

        } catch (IOException e) {
            log.error("error: ", e);
        }

        String out = Constants.OUTPUT_FOLDER + "/" + prefix + "_test.zip";
        zipSourceCode(out, "pom.xml", "src");
    }

    private static void zipSourceCode(final String zipFilePath, final String... sourceDirPaths) {

        // TODO: migliorare

        Path filePath = Paths.get(zipFilePath);
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            log.warn("zipSourceCode: caught exception of class [{}] when trying to perform 'deleteIfExists'",
                    e.getClass().getSimpleName());
        }
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
            log.debug("file zipped, zipoutstream closed");
        } catch (IOException e) {
            log.error("err: exception of class {} , message: {}", e.getClass().getSimpleName(),
                    e.getLocalizedMessage());
        }
    }

}
