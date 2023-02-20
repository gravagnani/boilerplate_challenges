package bootey;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import bootey.utils.Constants;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Main {
    private static final boolean singleFile = true;
    private static final Integer indexFileToProcess = 1;

    public static void main(String[] args) {
        includeHighLevelSkill();
        log.debug("Current directory: {}", System.getProperty("user.dir"));

        File inputFiles = new File(Constants.INPUT_FOLDER);
        File[] inputFilesList = inputFiles.listFiles();
        Arrays.sort(inputFilesList);

        if (singleFile) {
            File file = Objects.requireNonNull(inputFilesList)[indexFileToProcess];
            new Thread(new GameLauncher(file)).start();
            return;
        }

        for (File fileEntry : Objects.requireNonNull(inputFilesList)) {
            if (!fileEntry.isFile()) {
                log.warn("Skipped element [{}] of fileList since it was not a file.", fileEntry.getName());
                continue;
            }
            new Thread(new GameLauncher(fileEntry)).start();
        }
    }

    private static void includeHighLevelSkill() {
        InputStream inputStream = Main.class.getResourceAsStream("/banner.txt");
        if (inputStream == null) {
            log.error("inputStream null");
        }
        assert inputStream != null;
        String banner = new BufferedReader(new InputStreamReader(inputStream))
                .lines().collect(Collectors.joining("\n"));
        System.err.println(banner);
    }
}
