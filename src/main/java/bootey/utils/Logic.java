package bootey.utils;

import bootey.dto.ChallengeModel;
import bootey.dto.GoldenPoint;
import bootey.dto.SilverPoint;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Logic {

    public GoldenPoint getClosesGoldenPoint(ChallengeModel challenge, SilverPoint sp){
        GoldenPoint closesGoldenPoint=null;
        long lowestDistance=10000000000000l;
        for (GoldenPoint gp : challenge.getGoldenPoints()) {
                int distance = getDistance(sp,gp);
                if (distance<lowestDistance) {
                    closesGoldenPoint=gp;
                }

        }
        return closesGoldenPoint;
    }

    private int getDistance(SilverPoint sp, GoldenPoint gp) {
        return Math.abs(sp.getX()-gp.getX())+Math.abs(sp.getY()-gp.getY());
    }

}