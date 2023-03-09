package bootey;

import bootey.dto.ChallengeModel;
import bootey.io.DataParser;
import bootey.solvers.Solver;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Log4j2
@AllArgsConstructor
public class GameLauncher implements Runnable {
    private final File file;
    private final String solverName;

    @Override
    public void run() {
        log.info("--- Started thread with file [{}]", file.getName());
        long startTime = System.currentTimeMillis();

        final String completeSolverName = "bootey.solvers." + solverName;
        final String outputDirPrefix = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")
                .format(new Date(System.currentTimeMillis())) + "_" + solverName;

        try {

            // 1. parse input file and load challenge data into challenge model
            ChallengeModel challenge = DataParser.fromFile(file);

            // 2. retrieve solver and solve the challenge (updating challenge model)
            Constructor<?>[] solverConstructors = Class.forName(completeSolverName).getDeclaredConstructors();
            if (solverConstructors.length != 1) {
                log.info("Solver {} has more than one constructor", completeSolverName);
            }
            Solver solver = (Solver) solverConstructors[0].newInstance();
            solver.solve(challenge);

            // 3. print to file solution
            DataParser.toFile(challenge, outputDirPrefix, file.getName(), solverName);

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                 | InvocationTargetException e) {
            log.info("Solver {}  Not Found", completeSolverName);
        }

        log.info("--- Completed thread in {}ms ", (System.currentTimeMillis() - startTime));
    }
}