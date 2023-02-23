package bootey.solvers;

import bootey.dto.ChallengeModel;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Solver {
    private Solver() {
    }

    public static void solve(ChallengeModel challenge) {

        // while T > 0

        // ho regali a bordo?
        // se no torno alla base

        // hai un bambino nelle vicinanze?

        // se si allora consegno il regalo

        // seleziono un bambino

        // calcolo la velocita necessaria

        // setto accelerzione

        // mi muovo di 1s

        // T+1

        log.info("Solved");
    }

    // private static Comparator<Child> comparator() {
    // return (d1, d2) -> {
    // if (d2.getWeight() > d1.getWeight()) {
    // return 1;
    // } else if (d2.getWeight() < d1.getWeight()) {
    // return -1;
    // }
    // return 0;
    // };
    // }

    public static int getNextChild() {
        return 0;
    }

    public int scoreCalculator() {
        // calcola lo score del problema
        return 1;
    }
}
