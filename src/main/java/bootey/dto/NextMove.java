package bootey.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.javatuples.Pair;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NextMove {

    private Pair<Integer, Integer> coordMove;
    private String action;
    private Integer pointsValue;
}
