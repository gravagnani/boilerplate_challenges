package bootey.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Snake {
    private Integer id;
    private Integer len;
    private List<String> actions = new ArrayList<>();
    private List<NextMove> moves = new ArrayList<>();

    public Snake(Integer id, Integer len) {
        this.id = id;
        this.len = len;
    }

}
