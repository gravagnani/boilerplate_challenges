package bootey.solvers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.javatuples.Pair;

import bootey.dto.ChallengeModel;
import bootey.dto.NextMove;
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
            // int[] coord = getMaxScoreCoords(challenge.getMatrix());
            int[] coord = getMaxScoreCoords(challenge.getMatrix(), s.getLen());
            s.getActions().add("" + coord[0]);
            s.getActions().add("" + coord[1]);
            s.getMoves().add(new NextMove(new Pair<Integer, Integer>(coord[0], coord[1]), "S",
                    challenge.getMatrix()[coord[0]][coord[1]]));
            challenge.getMatrix()[coord[0]][coord[1]] = Integer.MIN_VALUE;
            for (int i = 1; i < s.getLen(); i++) {
                NextMove nm = getNextMove(coord, challenge.getMatrix());
                // TODO: conta quanti punti facciamo

                if (nm.getPointsValue() != null && nm.getPointsValue() == Integer.MIN_VALUE) {
                    s.getMoves().remove(0);
                    for (NextMove nmTemp : s.getMoves()) {
                        if (nmTemp.getPointsValue() != null) {
                            challenge.getMatrix()[nmTemp.getCoordMove().getValue0()][nmTemp.getCoordMove()
                                    .getValue1()] = nmTemp
                                            .getPointsValue();
                        }
                    }
                    s.setActions(new ArrayList<>()); // svuotiamo
                    s.setMoves(new ArrayList<>()); // svuotiamo
                    // throw new RuntimeException("da fare svuotamento");
                    break;
                }

                coord[0] = nm.getCoordMove().getValue0();
                coord[1] = nm.getCoordMove().getValue1();
                s.getActions().add(nm.getAction());
                s.getMoves().add(nm);
                if (nm.getPointsValue() == null) {
                    // scegli wh
                    Pair<Integer, Integer> w = nextWormhole(coord, challenge.getWormholeList());
                    coord[0] = w.getValue0();
                    coord[1] = w.getValue1();
                    s.getActions().add("" + coord[0]);
                    s.getActions().add("" + coord[1]);
                    s.getMoves().add(new NextMove(new Pair<Integer, Integer>(coord[0], coord[1]), "W",
                            challenge.getMatrix()[coord[0]][coord[1]]));
                } else {
                    challenge.getMatrix()[coord[0]][coord[1]] = Integer.MIN_VALUE;
                }
            }

        }

        // NON TOCCARE!
        challenge.getSnakeList().sort(Comparator.comparing(Snake::getId));

        log.info("Solved");
    }

    public static Integer scoreCalculator() {

        return null;
    }

    // public static int[] getMaxScoreCoords(Integer[][] matrix) {

    // int maxVal = Integer.MIN_VALUE;
    // int[] maxIndex = new int[2];

    // for (int i = 0; i < matrix.length; i++) {
    // for (int j = 0; j < matrix[0].length; j++) {
    // if (matrix[i][j] == null)
    // continue;
    // if (matrix[i][j] > maxVal) {
    // maxVal = matrix[i][j];
    // maxIndex[0] = i;
    // maxIndex[1] = j;
    // }
    // }
    // }

    // return maxIndex;

    // }

    public static int[] getMaxScoreCoords(Integer[][] matrix, Integer len) {
        int maxVal = Integer.MIN_VALUE;
        int[] maxIndex = new int[2];
        int width = matrix.length;
        int height = matrix[0].length;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == null || matrix[i][j] == Integer.MIN_VALUE)
                    continue;
                var total = 0;
                for (int x = 0; x < 3; x++) {
                    for (int y = 0; y < 3; y++) {
                        if (matrix[(i - x + width) % width][(j - y + height) % height] == null
                                || matrix[(i + x + width) % width][(j + y + height) % height] == null)
                            continue;
                        total += matrix[(i - x + width) % width][(j - y + height) % height] / (x + y + 1);
                        total += matrix[(i + x + width) % width][(j + y + height) % height] / (x + y + 1);
                    }
                }
                if (total > maxVal) {
                    maxVal = total;
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

        List<NextMove> coords = new ArrayList<>();

        coords.add(new NextMove(new Pair<>((x - 1 + width) % width, y), "U", 0));
        coords.add(new NextMove(new Pair<>((x + 1 + width) % width, y), "D", 0));
        coords.add(new NextMove(new Pair<>(x, (y - 1 + height) % height), "L", 0));
        coords.add(new NextMove(new Pair<>(x, (y + 1 + height) % height), "R", 0));

        int max = Integer.MIN_VALUE;
        NextMove maxCoords = null;
        for (NextMove nm : coords) {
            Pair<Integer, Integer> c = nm.getCoordMove();
            Integer val = matrix[c.getValue0()][c.getValue1()];
            Boolean isWH = false;
            if (val == null) {
                isWH = true;
                val = 0;
            }
            if (val >= max) {
                max = val;
                maxCoords = nm;
                maxCoords.setPointsValue(isWH ? null : val);
            }
        }

        if (maxCoords == null)
            throw new RuntimeException();

        return maxCoords;
    }

    public Pair<Integer, Integer> nextWormhole(int[] coords, List<Pair<Integer, Integer>> wormholes) {
        while (true) {
            int nextInt = new Random().nextInt(wormholes.size());
            if (wormholes.get(nextInt).getValue0() != coords[0] || wormholes.get(nextInt).getValue1() != coords[1]) {
                return wormholes.get(nextInt);
            }
        }
    }

}
