package bootey.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Building {
    // private Integer id; // index
    private Integer Bx; // x
    private Integer By; // y

    private Integer Bl; // latency -> peso
    private Integer Bc; // connection -> speed

    private Integer maxScore = Integer.MIN_VALUE;

    public Building(Integer bx, Integer by, Integer bl, Integer bc) {
        Bx = bx;
        By = by;
        Bl = bl;
        Bc = bc;
    }

}
