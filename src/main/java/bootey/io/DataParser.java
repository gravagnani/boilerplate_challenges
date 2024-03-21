package bootey.io;

import bootey.dto.ChallengeModel;
import bootey.dto.GoldenPoint;
import bootey.dto.SilverPoint;
import bootey.dto.Tile;
import bootey.utils.Constants;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

        // for the input 00 there's a strange character at the beginning of the file, so we clean it.
        String nColString = splitData[0].replaceAll("\\D+", "");
        challenge.setNCol(Integer.parseInt(nColString));
        challenge.setNRow(Integer.parseInt(splitData[1]));
        challenge.setNGoldenPoints(Integer.parseInt(splitData[2]));
        challenge.setNSilverPoints(Integer.parseInt(splitData[3]));
        challenge.setNTiles(Integer.parseInt(splitData[4]));

        List<GoldenPoint> goldenPoints = new ArrayList<>();
        for (int si = 0; si < challenge.getNGoldenPoints(); si++) {
            data = scanner.nextLine();
            splitData = data.split(" ");
            GoldenPoint goldenPoint = new GoldenPoint(Integer.parseInt(splitData[0]), Integer.parseInt(splitData[1]));
            goldenPoints.add(goldenPoint);
        }
        challenge.setGoldenPoints(goldenPoints);

        List<SilverPoint> silverPoints = new ArrayList<>();
        for (int si = 0; si < challenge.getNSilverPoints(); si++) {
            data = scanner.nextLine();
            splitData = data.split(" ");
            SilverPoint silverPoint = new SilverPoint(Integer.parseInt(splitData[0]), Integer.parseInt(splitData[1]), Integer.parseInt(splitData[2]));
            silverPoints.add(silverPoint);
        }
        challenge.setSilverPoints(silverPoints);

        List<Tile> tiles = new ArrayList<>();
        for (int si = 0; si < challenge.getNTiles(); si++) {
            data = scanner.nextLine();
            splitData = data.split(" ");
            Tile tile = new Tile(splitData[0], Integer.parseInt(splitData[1]), Integer.parseInt(splitData[2]));
            tiles.add(tile);
        }
        challenge.setTiles(tiles);

        scanner.close();
        return challenge;
    }

    public static void toFile(ChallengeModel challenge, String outputDirPrefix, String fileName, String solverName) {
        String outputDirStr = Constants.OUTPUT_FOLDER + "/" + outputDirPrefix;

        File outputDir = new File(outputDirStr);
        if (!outputDir.exists()) {
            outputDir.mkdirs(); // create outputDir if it doesn't exist
        }

        try (FileWriter writer = new FileWriter(new File(outputDirStr, fileName))) {

            writer.write(challenge.toChallengeOutput());

        } catch (IOException e) {
            log.error("error: ", e);
        }

        String outZipFile = outputDirStr + "/bootey_" + solverName + ".zip";
        zipSourceCode(outZipFile, "pom.xml", "src/main/java",
                "src/main/resources/banner.txt",
                "src/main/resources/log4j2.properties",
                "src/main/resources/lombok.config");
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
