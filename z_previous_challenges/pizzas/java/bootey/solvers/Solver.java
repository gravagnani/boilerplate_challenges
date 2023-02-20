package bootey.solvers;

import lombok.extern.log4j.Log4j2;
import bootey.dto.ChallengeModel;
import bootey.dto.Delivery;
import bootey.dto.Pizza;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Log4j2
public class Solver {
    private Solver() {
    }

    public static void solve(ChallengeModel challenge) {

        List<Pizza> listPizzas = challenge.getListPizzas();
        List<Delivery> listDelivers = challenge.getListDelivers();

        challenge.getListPizzas().sort(comparator());

        for (int t = 0; t < challenge.getNT4(); t++) {
            if (listPizzas.size() < 4) {
                break;
            }
            List<Pizza> lp = new ArrayList<>();
            for (int p = 0; p < 4; p++) {
                lp.add(listPizzas.remove(getNextPizza()));
            }
            Delivery d = new Delivery(4, lp);
            listDelivers.add(d);
        }

        for (int t = 0; t < challenge.getNT3(); t++) {
            if (listPizzas.size() < 3) {
                break;
            }
            List<Pizza> lp = new ArrayList<>();
            for (int p = 0; p < 3; p++) {
                lp.add(listPizzas.remove(getNextPizza()));
            }
            Delivery d = new Delivery(3, lp);
            listDelivers.add(d);
        }

        for (int t = 0; t < challenge.getNT2(); t++) {
            if (listPizzas.size() < 2) {
                break;
            }
            List<Pizza> lp = new ArrayList<>();
            for (int p = 0; p < 2; p++) {
                lp.add(listPizzas.remove(getNextPizza()));
            }
            Delivery d = new Delivery(2, lp);
            listDelivers.add(d);
        }

        log.info("Solved");
    }

    private static Comparator<Pizza> comparator() {
        return (o1, o2) -> o2.getNDiff() - o1.getNDiff();
    }

    public static int getNextPizza() {
        return 0;
    }

    public int scoreCalculator() {
        // calcola lo score del problema
        return 1;
    }
}
