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

        for (Tile tile : solutionTileList) {

            s.append(tile.getId() + " " + tile.getX() + " " + tile.getY() + "\n");
        }
        return s.toString();
    }
}