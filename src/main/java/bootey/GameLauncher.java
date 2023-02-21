package bootey;

import bootey.dto.ChallengeModel;
import bootey.io.DataParser;
import bootey.solvers.Solver;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.io.File;

@Data
@AllArgsConstructor
@Log4j2
public class GameLauncher implements Runnable {
    private File file;

    @Override
    public void run() {
        log.info("--- Started thread with file [{}]", file.getName());
        long startTime = System.currentTimeMillis();

        // 1. parse input file and load challenge data into challenge model
        ChallengeModel challenge = DataParser.fromFile(file);
        // 2. solve challenge (updating challenge model)
        Solver.solve(challenge);
        // 3. print to file solution
        DataParser.toFile(challenge, file.getName());

        log.info("--- Completed thread in {}ms ", (System.currentTimeMillis() - startTime));
    }
}