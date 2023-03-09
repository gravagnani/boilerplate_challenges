package bootey.solvers;

import bootey.dto.ChallengeModel;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class SolverBase implements Solver {
    public void solve(ChallengeModel challenge) {

        /*
         * per ogni serpente
         * prendo il serpente più lungo
         * posiziono il serpente nel punto della mappa con maggiore score
         * mi muovo nella direzione di maggiore score
         * in caso di warmhole conto 0
         */

        challenge.getSnakeList();

        log.info("Solved");
    }

    public static Integer scoreCalculator() {

        return null;
    }

}
