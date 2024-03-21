package bootey.utils;

import bootey.dto.ChallengeModel;
import bootey.dto.GoldenPoint;
import bootey.dto.Point;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Logic {

    public Point getClosesGoldenPoint(ChallengeModel challenge, Point sp) {
        Point closesGoldenPoint = null;
        long lowestDistance = 10000000000000l;
        for (GoldenPoint gp : challenge.getGoldenPoints()) {
            int distance = getDistance(sp, gp);
            if (distance < lowestDistance) {
                closesGoldenPoint = gp;
            }

        }
        return closesGoldenPoint;
    }

    private int getDistance(Point sp, Point gp) {
        return Math.abs(sp.getX() - gp.getX()) + Math.abs(sp.getY() - gp.getY());
    }

}