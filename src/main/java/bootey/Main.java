package bootey;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import bootey.utils.Constants;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Main {

    private static boolean precessAll = false; // if enabled all input files are processed
    private static List<Integer> indexFileToProcess = Arrays.asList(0); // indexes of input files to be processed
    private static String solverName = "SolverBase"; // name of the solver to use, must match the solver class name

    public static void main(String[] args) {
        includeHighLevelSkill();
        log.debug("Current directory: {}", System.getProperty("user.dir"));

        File inputFiles = new File(Constants.INPUT_FOLDER);
        File[] inputFilesList = Objects.requireNonNull(inputFiles.listFiles());
        Arrays.sort(inputFilesList);

        if (precessAll) {
            indexFileToProcess = IntStream.range(0, inputFilesList.length).boxed().toList();
        }

        indexFileToProcess.stream().forEach(i -> {
            File file = inputFilesList[i];
            if (!file.isFile()) {
                log.warn("Skipped element [{}] of indexFileToProcess since it was not a file .", file.getName());
                return;
            }
            new Thread(new GameLauncher(file, solverName)).start();
        });
    }

    private static void includeHighLevelSkill() {
        InputStream inputStream = Main.class.getResourceAsStream("/banner.txt");
        if (inputStream == null) {
            log.error("Unable to print super cool banner cause resourceStream is null.");
            return;
        }
        String banner = new BufferedReader(new InputStreamReader(inputStream))
                .lines().collect(Collectors.joining("\n"));
        System.err.println(banner); // print in red
    }
}
