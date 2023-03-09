package bootey.solvers;

import java.util.Map;

import org.javatuples.Pair;

import bootey.dto.ChallengeModel;
import bootey.dto.Snake;
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

        log.info("Solved");
    }

    public static Integer scoreCalculator(Snake antenna, Map<Pair<Integer, Integer>, Building> buildings) {
        /*
         * if (!(antenna.getAx() != null && antenna.getAy() != null))
         * throw new RuntimeException("Errore");
         * int score = 0;
         * for (int i = antenna.getAx() - antenna.getAr(); i < antenna.getAx() +
         * antenna.getAr(); i++) {
         * for (int j = antenna.getAy() - antenna.getAr(); j < antenna.getAy() +
         * antenna.getAr(); j++) {
         * Building bTemp = buildings.get(new Pair<>(i, j));
         * if (bTemp == null)
         * continue;
         * int distance = Math.abs(antenna.getAx() - bTemp.getBx()) +
         * Math.abs(antenna.getAy() - bTemp.getBy());
         * if (distance > antenna.getAr())
         * continue;
         * int scoreTemp = bTemp.getBc() * antenna.getAc() - bTemp.getBl() * distance;
         * if (scoreTemp > bTemp.getMaxScore()) {
         * bTemp.setMaxScore(scoreTemp);
         * score += scoreTemp;
         * }
         * }
         * }
         * 
         */

        return null;
    }

}
