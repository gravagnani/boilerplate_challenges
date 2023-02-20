package bootey.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChallengeModel {

    private Integer Si;
    private Integer Smax;
    private Integer T;
    private Integer D;

    private List<Demon> listDemons;

    private Demon[] solution;
    private Integer[] stamina;

    private int score;


    public String toChallengeOutput() {
        StringBuilder s = new StringBuilder();
        for (Demon d : solution) {
            if (d != null) {
                s.append(d.getId())
                        .append("\n");
            }
        }
        return s.toString();
    }
}