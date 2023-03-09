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
public class Solver1 implements Solver {
    public void solve(ChallengeModel challenge) {

        /*
         * per ogni serpente
         * prendo il serpente più lungo
         * posiziono il serpente nel punto della mappa con maggiore score
         * mi muovo nella direzione di maggiore score
         * in caso di warmhole conto 0
         */

        challenge.getSnakeList().sort(Comparator.comparing(Snake::getLen).reversed());

        Integer[][] matrix = challenge.getMatrix();
        for (Snake s : challenge.getSnakeList()) {
            // start point
            Pair<Integer, Integer> coord = getMaxScoreCoords(matrix);
            s.getMoves().add(new NextMove(coord, "S", matrix[coord.getValue0()][coord.getValue1()]));
            matrix[coord.getValue0()][coord.getValue1()] = Integer.MIN_VALUE;

            for (int i = 1; i < s.getLen(); i++) {
                NextMove nm = getNextMove(coord, matrix);
                if (nm.getPointsValue() == Integer.MIN_VALUE) {
                    for (NextMove nmTemp : s.getMoves()) {
                        if (nmTemp.getPointsValue() != null) {
                            matrix[nmTemp.getCoordMove().getValue0()][nmTemp.getCoordMove().getValue1()] = nmTemp
                                    .getPointsValue();
                        }
                    }
                    s.setMoves(new ArrayList<>());
                    break;
                }

                // update current pos
                coord = nm.getCoordMove();
                s.getActions().add(nm.getAction());
                s.getMoves().add(nm);

                if (nm.getPointsValue() == null) {
                    // scegli destination wh
                    coord = nextWormhole(coord, challenge.getWormholeList());
                    s.getMoves().add(new NextMove(coord, "W", matrix[coord.getValue0()][coord.getValue1()]));
                } else {
                    matrix[coord.getValue0()][coord.getValue1()] = Integer.MIN_VALUE;
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

    public static Pair<Integer, Integer> getMaxScoreCoords(Integer[][] matrix) {

        int maxVal = Integer.MIN_VALUE;
        Pair<Integer, Integer> maxIndex = null;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == null)
                    continue;
                if (matrix[i][j] > maxVal) {
                    maxVal = matrix[i][j];
                    maxIndex = new Pair<>(i, j);
                }
            }
        }

        return maxIndex;

    }

    public static NextMove getNextMove(Pair<Integer, Integer> coord, Integer[][] matrix) {
        int x = coord.getValue0();
        int y = coord.getValue1();

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

    public Pair<Integer, Integer> nextWormhole(Pair<Integer, Integer> coords, List<Pair<Integer, Integer>> wormholes) {
        while (true) {
            int nextInt = new Random().nextInt(wormholes.size());
            if (wormholes.get(nextInt).getValue0() != coords.getValue0()
                    || wormholes.get(nextInt).getValue1() != coords.getValue1()) {
                return wormholes.get(nextInt);
            }
        }
    }

}
