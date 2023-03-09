package bootey.io;

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

import org.javatuples.Pair;

import bootey.dto.ChallengeModel;
import bootey.dto.Snake;
import bootey.utils.Constants;
import lombok.extern.log4j.Log4j2;

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
        challenge.setNCol(Integer.parseInt(splitData[0]));
        challenge.setNRow(Integer.parseInt(splitData[1]));
        challenge.setNSnakes(Integer.parseInt(splitData[2]));

        // second line
        data = scanner.nextLine();
        splitData = data.split(" ");
        List<Snake> snakeList = new ArrayList<>();
        for (int si = 0; si < challenge.getNSnakes(); si++) {
            snakeList.add(new Snake(si, Integer.parseInt(splitData[si])));
        }
        challenge.setSnakeList(snakeList);

        Integer[][] matrix = new Integer[challenge.getNRow()][challenge.getNCol()];
        List<Pair<Integer, Integer>> wormholeList = new ArrayList<>();
        for (int i = 0; i < challenge.getNRow(); i++) {

            data = scanner.nextLine();
            splitData = data.split(" ");
            for (int j = 0; j < challenge.getNCol(); j++) {
                Integer i1;
                try {
                    i1 = Integer.parseInt(splitData[j]);
                } catch (NumberFormatException e) {
                    // wormhole
                    i1 = null;
                    wormholeList.add(new Pair<>(i, j));
                }
                matrix[i][j] = i1;
            }

        }
        challenge.setMatrix(matrix);
        challenge.setWormholeList(wormholeList);

        scanner.close();
        // to print matrix
        /*
         * log.debug("Printing matrix");
         * for (int i = 0; i < challenge.getNRow(); i++) {
         * for (int j = 0; j < challenge.getNCol(); j++) {
         * System.out.print(matrix[i][j] + " ");
         * }
         * System.out.println("\n");
         * }
         */
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
        zipSourceCode(outZipFile, "pom.xml", "src");
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
