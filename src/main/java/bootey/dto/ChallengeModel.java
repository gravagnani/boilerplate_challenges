package bootey.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.javatuples.Pair;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChallengeModel {

    private Integer nCol; // numero colonne
    private Integer nRow; // numero righe
    private Integer nSnakes; // numero snakes

    private List<Snake> snakeList;

    private List<Pair<Integer, Integer>> wormholeList;

    private Integer[][] matrix;

    public String toChallengeOutput() {

        StringBuilder s = new StringBuilder();
        for (Snake snake : snakeList) {
            List<String> actions = snake.getActions();
            if (actions.isEmpty()) {
                s.append("\n");
            } else {
                for (int i = 0; i < actions.size(); i++) {
                    s.append(actions.get(0) + " ");
                }
                s.append("\n");
            }
        }
        return s.toString();
    }
}