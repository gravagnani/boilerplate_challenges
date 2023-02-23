package bootey.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Child {
    private String name;
    private Integer s; // score
    private Integer w; // weight
    private Integer c; // col location
    private Integer r; // row location

}
