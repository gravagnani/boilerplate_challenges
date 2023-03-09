package bootey.solvers;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

import org.javatuples.Pair;

import bootey.dto.Antenna;
import bootey.dto.Building;
import bootey.dto.ChallengeModel;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Solver {
    private Solver() {
    }

    // VECCHIA SOLUZIONE
    public static void solve1(ChallengeModel challenge) {

        // ordino Building per Bc (velocità) in maniera DESC
        challenge.getBuildingList().sort(comparator());
        // ordine Antenna per AC DESC
        challenge.getAntennaList().sort(comparatorAntenna());

        int valMin = Math.min(challenge.getM(), challenge.getN());

        for (int i = 0; i < valMin; i++) {

            Antenna antenna = challenge.getAntennaList().remove(0);
            Building building = challenge.getBuildingList().get(i);
            antenna.setAx(building.getBx());
            antenna.setAy(building.getBy());
            challenge.getPlacedAntennaList().add(antenna);
        }

        log.info("Solved");
    }

    // ORA QUA CAZZOOOO
    public static void solve(ChallengeModel challenge) {

        for (Building b : challenge.getBuildingList()) {
            challenge.getBuildingListMap().put(new Pair<Integer, Integer>(b.getBx(), b.getBy()), b);
        }

        for (int i = 0; i < challenge.getH(); i++) {
            for (int j = 0; j < challenge.getW(); j++) {
                challenge.getCoordinates().add(new Pair<Integer, Integer>(i, j));
            }
        }

        Collections.shuffle(challenge.getCoordinates());
        challenge.setCoordinates(challenge.getCoordinates().subList(0, challenge.getAntennaList().size() * 10));

        int counter = 0;
        for (Antenna a : challenge.getAntennaList()) {
            counter++;
            log.info("Antenna " + (counter) + "  / " + challenge.getAntennaList().size());

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

            a.setAx(challenge.getCoordinates().get(maxIndex).getValue0());
            a.setAy(challenge.getCoordinates().get(maxIndex).getValue1());

            challenge.getCoordinates().remove(maxIndex);
            challenge.getPlacedAntennaList().add(a);

        }

        log.info("Solved");
    }

    private static Comparator<Building> comparator() {
        return (d1, d2) -> {
            if (d2.getBc() > d1.getBc()) {
                return 1;
            } else if (d2.getBc() < d1.getBc()) {
                return -1;
            }
            return 0;
        };
    }

    private static Comparator<Antenna> comparatorAntenna() {
        return (d1, d2) -> {
            if (d2.getAc() * d2.getAr() > d1.getAc() * d1.getAr()) {
                return 1;
            } else if (d2.getAc() * d2.getAr() < d1.getAc() * d1.getAr()) {
                return -1;
            }
            return 0;
        };
    }

    public static int getNextChild() {
        return 0;
    }

    public static int scoreCalculator(Antenna antenna, Map<Pair<Integer, Integer>, Building> buildings) {
        if (!(antenna.getAx() != null && antenna.getAy() != null))
            throw new RuntimeException("Errore");
        int score = 0;
        for (int i = antenna.getAx() - antenna.getAr(); i < antenna.getAx() + antenna.getAr(); i++) {
            for (int j = antenna.getAy() - antenna.getAr(); j < antenna.getAy() + antenna.getAr(); j++) {
                Building bTemp = buildings.get(new Pair(i, j));
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
