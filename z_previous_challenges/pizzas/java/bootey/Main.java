package bootey;

import lombok.extern.log4j.Log4j2;
import bootey.utils.Constants;

import java.io.File;
import java.util.Objects;


@Log4j2
public class Main {
    private static final boolean singleFile = true;
    private static final Integer indexFileToProcess = 1;

    public static void main(String[] args) {
        log.debug("Current directory: {}", System.getProperty("user.dir"));

        File inputFiles = new File(Constants.INPUT_FOLDER);
        File[] inputFilesList = inputFiles.listFiles();

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
}
