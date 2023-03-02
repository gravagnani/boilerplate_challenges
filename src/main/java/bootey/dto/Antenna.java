package bootey.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Antenna {
    private Integer id; // index
    private Integer Ar; // range
    private Integer Ac; // connection

    private Integer Ax; // dove piazzo l'antenna
    private Integer Ay;

    public Antenna(Integer id, Integer Ar, Integer Ac) {
        this.id = id;
        this.Ar = Ar;
        this.Ac = Ac;
    }
}
