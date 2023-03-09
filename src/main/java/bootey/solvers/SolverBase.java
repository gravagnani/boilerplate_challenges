package bootey.solvers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import bootey.dto.NextMove;
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

        challenge.getSnakeList().sort(Comparator.comparing(Snake::getLen).reversed());

        for (Snake s : challenge.getSnakeList()) {
            int[] coord = getMaxScoreCoords(challenge.getMatrix());
            s.getActions().add("" + coord[0]);
            s.getActions().add("" + coord[1]);
            challenge.getMatrix()[coord[0]][coord[1]] = Integer.MIN_VALUE;
            for (int i = 1; i < s.getLen(); i++) {
                Pair<Integer, Integer> c = getNextMove(coord, challenge.getMatrix());
                // TODO: conta quanti punti facciamo
                // aggiungi mossa a s

                // se numero
                // marchi casella visitata
                // aggiorna coord

                // se wormhole
            }

        }

        // NON TOCCARE!
        challenge.getSnakeList().sort(Comparator.comparing(Snake::getId));

        log.info("Solved");
    }

    public static Integer scoreCalculator() {

        return null;
    }

    public static int[] getMaxScoreCoords(Integer[][] matrix) {

        int maxVal = Integer.MIN_VALUE;
        int[] maxIndex = new int[2];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == null)
                    continue;
                if (matrix[i][j] > maxVal) {
                    maxVal = matrix[i][j];
                    maxIndex[0] = i;
                    maxIndex[1] = j;
                }
            }
        }

        return maxIndex;

    }

    public static NextMove getNextMove(int[] coord, Integer[][] matrix) {
        int x = coord[0];
        int y = coord[1];

        int width = matrix.length;
        int height = matrix[0].length;

        List<Pair<Integer, Integer>> coords = new ArrayList<>();

        coords.add(new Pair<>((x - 1 + width) % width, y));
        coords.add(new Pair<>((x + 1 + width) % width, y));
        coords.add(new Pair<>(x, (y - 1 + height) % height));
        coords.add(new Pair<>(x, (y + 1 + height) % height));

        int max = Integer.MAX_VALUE;
        Pair<Integer, Integer> maxCoords = null;
        for (Pair<Integer, Integer> c : coords) {
            Integer val = matrix[c.getValue0()][c.getValue1()];
            if (val == null) {
                val = 0;
            }
            if (val > max) {
                max = val;
                maxCoords = c;
            }
        }

        if (maxCoords == null)
            throw new RuntimeException();


        NextMove res = new NextMove();
        res.setCoordMove(maxCoords);
        res.setAction("TODO");
        res.setPointsValue(0); //TODO:
        return res;
    }

}
