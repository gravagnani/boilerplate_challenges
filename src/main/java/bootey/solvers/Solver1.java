package bootey.solvers;

import java.util.Map;

import org.javatuples.Pair;

import bootey.dto.Antenna;
import bootey.dto.Building;
import bootey.dto.ChallengeModel;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Solver1 implements Solver {
    public void solve(ChallengeModel challenge) {
        for (Building b : challenge.getBuildingList()) {
            challenge.getBuildingListMap().put(new Pair<Integer, Integer>(b.getBx(), b.getBy()), b);
        }

        for (int i = 0; i < challenge.getH(); i += 15) {
            for (int j = 0; j < challenge.getW(); j += 15) {
                challenge.getCoordinates().add(new Pair<Integer, Integer>(i, j));
            }
        }

        // Collections.shuffle(challenge.getCoordinates());
        // challenge.setCoordinates(challenge.getCoordinates().subList(0,
        // challenge.getAntennaList().size() * 5));

        int counter = 0;
        for (Antenna a : challenge.getAntennaList()) {
            counter++;
            if (counter % 10 == 0) {
                log.info("Antenna " + (counter) + "  / " + challenge.getAntennaList().size());
            }

            int[] scores = challenge.getCoordinates().parallelStream().mapToInt(el -> {
                a.setAx(el.getValue0());
                a.setAy(el.getValue1());
                return scoreCalculator(a, challenge.getBuildingListMap());
            }).toArray();

            int maxIndex = 0;
            for (int i = 1; i < scores.length; i++) {
                if (scores[i] > scores[maxIndex]) {
                    maxIndex = i;
                }
            }

            if (scores.length > 0) {
                a.setAx(challenge.getCoordinates().get(maxIndex).getValue0());
                a.setAy(challenge.getCoordinates().get(maxIndex).getValue1());

                challenge.getCoordinates().remove(maxIndex);
                challenge.getPlacedAntennaList().add(a);
            }
        }

        log.info("Solved");
    }

    public static int scoreCalculator(Antenna antenna, Map<Pair<Integer, Integer>, Building> buildings) {
        if (!(antenna.getAx() != null && antenna.getAy() != null))
            throw new RuntimeException("Errore");
        int score = 0;
        for (int i = antenna.getAx() - antenna.getAr(); i < antenna.getAx() + antenna.getAr(); i++) {
            for (int j = antenna.getAy() - antenna.getAr(); j < antenna.getAy() + antenna.getAr(); j++) {
                Building bTemp = buildings.get(new Pair<>(i, j));
                if (bTemp == null)
                    continue;
                int distance = Math.abs(antenna.getAx() - bTemp.getBx()) + Math.abs(antenna.getAy() - bTemp.getBy());
                if (distance > antenna.getAr())
                    continue;
                int scoreTemp = bTemp.getBc() * antenna.getAc() - bTemp.getBl() * distance;
                if (scoreTemp > bTemp.getMaxScore()) {
                    bTemp.setMaxScore(scoreTemp);
                    score += scoreTemp;
                }
            }
        }

        return score;
    }

}
