package bootey.dto;

import java.util.List;

import org.javatuples.Pair;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Santa {
    private Pair<Integer, Integer> position;
    private Integer c; // carrots
    private Pair<Integer, Integer> v; // speed X - Y
    private List<Child> listChild; // list child

    public int getW() {
        return c + listChild.stream().mapToInt(l -> l.getW()).sum();
    }
}
