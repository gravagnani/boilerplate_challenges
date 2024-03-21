package bootey.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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

    private List<Tile> solutionTileList = new ArrayList<>();


    public String toChallengeOutput() {
        StringBuilder s = new StringBuilder();

        solutionTileList.remove(0);

        for (Tile tile : solutionTileList) {

            s.append(tile.getType().getDefinition() + " " + tile.getX() + " " + tile.getY() + "\n");
        }
        return s.toString();
    }
}