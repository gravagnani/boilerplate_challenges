package bootey.dto;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChallengeModel {

    private Integer W; // asse x
    private Integer H; // asse Y
    private Integer N; // numero Building
    private Integer M; // numero antenne
    private Integer R; // reward // non ci serve

    private List<Antenna> antennaList;
    private List<Building> buildingList;

    private List<Antenna> placedAntennaList = new ArrayList<>();

    public String toChallengeOutput() {
        StringBuilder s = new StringBuilder();
        s.append(placedAntennaList.size()).append("\n");
        for (Antenna d : placedAntennaList) {
            s.append(d.getId() + " " + d.getAx() + " " + d.getAy()).append("\n");
        }
        return s.toString();
    }
}