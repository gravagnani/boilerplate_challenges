package bootey.dto;

import java.util.List;

import org.javatuples.Pair;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
        int S = 0;
        for (Snake snake : snakeList) {
            // List<String> actions = snake.getActions();
            List<NextMove> actions = snake.getMoves();
            if (actions.isEmpty()) {
                s.append("\n");
            } else {
                for (int i = 0; i < actions.size(); i++) {
                    if (actions.get(i).getAction().equals("S")) {
                        s.append(actions.get(i).getCoordMove().getValue0() + " ");
                        s.append(actions.get(i).getCoordMove().getValue1() + " ");
                    } else if (actions.get(i).getAction().equals("W")) {
                        s.append(actions.get(i).getCoordMove().getValue0() + " ");
                        s.append(actions.get(i).getCoordMove().getValue1() + " ");
                    } else {
                        s.append(actions.get(i).getAction() + " ");
                    }
                }
                if (S < snakeList.size() - 1) {
                    s.append("\n");
                }
            }
            S++;
        }
        return s.toString();
    }
}