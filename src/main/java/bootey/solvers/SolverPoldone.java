package bootey.solvers;

import bootey.dto.ChallengeModel;
import bootey.dto.GoldenPoint;
import bootey.dto.Point;
import bootey.dto.SilverPoint;
import bootey.utils.Logic;
import lombok.extern.log4j.Log4j2;
import org.javatuples.Pair;

import java.util.List;
import java.util.Random;

@Log4j2
public class SolverPoldone implements Solver {
    public static Integer scoreCalculator() {

        return null;
    }

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

    public void solve(ChallengeModel challenge) {


        /**
         * parto dal golden point + a dx

         */

        List<GoldenPoint> goldenPoints = challenge.getGoldenPoints();

        // starting golden point
        // FIXME: cambiare per ogni file. per il file 0 -> OK partire dal primo punto della lista
        GoldenPoint goldenPoint = goldenPoints.get(0);

        Point startPoint = new Point(goldenPoint.getX(), goldenPoint.getY());
        log.debug("Start point: {}", startPoint);

        Point endPoint = goldenPoints.get(goldenPoints.size() - 1);
        log.debug("End point: {}", endPoint);



        final String direction = "left";


        //SilverPoint firstClosest = Logic.getClosestSilverPoint(startPoint, challenge.getSilverPoints(), direction);

        Point current = startPoint;


        //while (current.getX() != endPoint.getX()) {
        while (current.getX() != endPoint.getX() || current.getY() != endPoint.getY()) {
            SilverPoint closestSilverPoint = Logic.getClosestSilverPoint(current, challenge.getSilverPoints(), direction);

            challenge.getSilverPoints().remove(closestSilverPoint);

            if (closestSilverPoint == null) {
                log.debug("OUT");
                break;
            }
            Logic.findPathToClosestPoint(current, closestSilverPoint, challenge.getTiles(), challenge, direction);
            current = new Point(closestSilverPoint.getX(), closestSilverPoint.getY());
            log.debug("Current point: {}", current);
        }

        Logic.findPathToClosestPoint(current, endPoint, challenge.getTiles(), challenge, direction);


        log.info("Solved");
    }



    /*

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
    */

    public Pair<Integer, Integer> nextWormhole(int[] coords, List<Pair<Integer, Integer>> wormholes) {
        while (true) {
            int nextInt = new Random().nextInt(wormholes.size());
            if (wormholes.get(nextInt).getValue0() != coords[0] || wormholes.get(nextInt).getValue1() != coords[1]) {
                return wormholes.get(nextInt);
            }
        }
    }


}
