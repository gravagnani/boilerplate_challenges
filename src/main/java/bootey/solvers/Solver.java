package bootey.solvers;

import bootey.dto.Antenna;
import bootey.dto.Building;
import bootey.dto.ChallengeModel;
import lombok.extern.log4j.Log4j2;

import java.util.Comparator;
import java.util.stream.Stream;

@Log4j2
public class Solver {
    private Solver() {
    }

    public static void solve(ChallengeModel challenge) {

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
            if (d2.getAc() > d1.getAc()) {
                return 1;
            } else if (d2.getAc() < d1.getAc()) {
                return -1;
            }
            return 0;
        };
    }

    public static int getNextChild() {
        return 0;
    }

    public int scoreCalculator() {
        // calcola lo score del problema
        return 1;
    }
}
