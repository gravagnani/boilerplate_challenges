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
}
