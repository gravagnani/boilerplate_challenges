package bootey.solvers;

import java.util.Comparator;

import bootey.dto.ChallengeModel;
import bootey.dto.Child;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Solver {
    private Solver() {
    }

    public static void solve(ChallengeModel challenge) {

        Child[] solution = new Child[challenge.getT()];
        Integer[] stamina = new Integer[challenge.getT()];

        for (int i = 0; i < challenge.getT(); i++) {
            solution[i] = null;
            stamina[i] = 0;
        }

        challenge.getListDemons().sort(comparator());

        for (int t = 0; t < solution.length; t++) {
            if (t % 100 == 0) {
                log.info("t % 100 == 0 -> t={}, T={}", t, challenge.getT());
            }

            // aggiorno lista demon
            for (Child d : challenge.getListDemons()) {
                // d.computeWeight2(T - t);
                d.setWeight(challenge.getT() - t, challenge.getSi());
            }

            challenge.getListDemons().sort(comparator());

            // recupero stamina
            challenge.setSi(Math.min(challenge.getSi() + stamina[t], challenge.getSmax()));

            // scelgo demone
            for (int i = 0; i < challenge.getListDemons().size(); i++) {
                if (challenge.getSi() >= challenge.getListDemons().get(i).getS()) {
                    solution[t] = challenge.getListDemons().remove(i);
                    break;
                }
            }

            // rimozione stamina
            if (solution[t] == null) {
                continue;
            }

            challenge.setSi(challenge.getSi() - solution[t].getS());

            try {
                stamina[t + solution[t].getW()] += solution[t].getC();
            } catch (Exception e) {
                log.error("err: {}", e.getLocalizedMessage());
            }
        }
        challenge.setSolution(solution);
        challenge.setStamina(stamina);

        log.info("Solved");
    }

    private static Comparator<Child> comparator() {
        return (d1, d2) -> {
            if (d2.getWeight() > d1.getWeight()) {
                return 1;
            } else if (d2.getWeight() < d1.getWeight()) {
                return -1;
            }
            return 0;
        };
    }

    public static int getNextPizza() {
        return 0;
    }

    public int scoreCalculator() {
        // calcola lo score del problema
        return 1;
    }
}
