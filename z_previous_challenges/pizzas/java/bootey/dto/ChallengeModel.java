package bootey.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChallengeModel {

    private Integer nPizzas;
    private Integer nT2;
    private Integer nT3;
    private Integer nT4;

    private List<Pizza> listPizzas;
    private List<Delivery> listDelivers;

    private int score;


    public String toChallengeOutput() {
        //TODO
        return null;
    }
}