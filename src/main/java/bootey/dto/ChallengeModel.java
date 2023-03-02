package bootey.dto;

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

    private List<String> actions;

    public String toChallengeOutput() {
        StringBuilder s = new StringBuilder();
        s.append(actions.size()).append("\n");
        for (String d : actions) {
            s.append(d).append("\n");
        }
        return s.toString();
    }
}