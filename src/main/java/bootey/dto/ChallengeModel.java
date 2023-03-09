package bootey.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private Integer[][] matrix;
    private List<Building> buildingList;
    private Map<Pair<Integer, Integer>, Building> buildingListMap = new HashMap<>();
    List<Pair<Integer, Integer>> coordinates = new ArrayList<>();

    private List<Snake> placedAntennaList = new ArrayList<>();

    public String toChallengeOutput() {

        return "";
        /*
        StringBuilder s = new StringBuilder();
        s.append(placedAntennaList.size()).append("\n");
        for (Snake d : placedAntennaList) {
            s.append(d.getId() + " " + d.getAx() + " " + d.getAy()).append("\n");
        }
        return s.toString();

         */
    }
}