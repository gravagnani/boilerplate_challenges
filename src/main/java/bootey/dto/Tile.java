package bootey.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tile {

    private String id;
    private Integer cost;
    private Integer amount; // da diminuire ogni volta

    // only for solutions
    private Integer x;
    private Integer y;

    public Tile(String id, Integer cost, Integer amount) {
        this.id = id;
        this.cost = cost;
        this.amount = amount;
    }

}
