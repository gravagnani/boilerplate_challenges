package bootey.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChallengeModel {

    private Integer nCol; // numero colonne
    private Integer nRow; // numero righe
    private Integer nGoldenPoints; // numero golden
    private Integer nSilverPoints; // numero silver
    private Integer nTiles; // numero silver

    private List<GoldenPoint> goldenPoints;
    private List<SilverPoint> silverPoints;
    private List<Tile> tiles;

    private List<Tile> solutionTileList;


    public String toChallengeOutput() {


        StringBuilder s = new StringBuilder();
           /*
        int S = 0;
        for (Tile tile : solutionTileList) {

            // List<String> actions = snake.getActions();
            List<NextMove> actions = tile.getMoves();
            if (actions.isEmpty()) {
                s.append("\n");
            } else {
                for (int i = 0; i < actions.size(); i++) {
                    if (actions.get(i).getAction().equals("S")) {
                        s.append(actions.get(i).getCoordMove().getValue1() + " ");
                        s.append(actions.get(i).getCoordMove().getValue0() + " ");
                    } else if (actions.get(i).getAction().equals("W")) {
                        s.append(actions.get(i).getCoordMove().getValue1() + " ");
                        s.append(actions.get(i).getCoordMove().getValue0() + " ");
                    } else {
                        s.append(actions.get(i).getAction() + " ");
                    }
                }
                if (S < goldenPoints.size() - 1) {
                    s.append("\n");
                }
            }
            S++;
        }


         */
        return s.toString();
    }
}