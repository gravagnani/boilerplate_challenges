package bootey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import bootey.dto.ChallengeModel;
import bootey.io.DataParser;
import bootey.solvers.Solver;

import java.io.File;
import java.io.IOException;

@Data
@AllArgsConstructor
@Log4j2
public class GameLauncher implements Runnable {
    private File file;

    @Override
    public void run() {
        log.info("--- Started thread with file [{}]", file.getName());
        long startTime = System.currentTimeMillis();
        try {
            ChallengeModel challenge = DataParser.fromFile(file);

            Solver.solve(challenge);

            DataParser.toFile(challenge, file.getName());

            log.info("--- Completed thread in {}ms ", (System.currentTimeMillis() - startTime));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}