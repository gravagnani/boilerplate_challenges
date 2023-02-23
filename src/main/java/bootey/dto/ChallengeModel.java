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

    private Integer T; // T the time limit in seconds, (1≤T≤10,000)
    private Integer D; // the reach range (0≤D≤100), that is maximum distance from sleigh for gift
                       // delivery and gift/carrot pick up
    private Integer W; // the number of acceleration ranges sleigh has (1≤W≤10)
    private Integer G; // the number of gifts in the dataset, (1≤G≤10,000)

    private List<Pair<Integer, Integer>> listRanges;
    private List<Child> listChild;

    private Pair<Integer, Integer> santa;

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