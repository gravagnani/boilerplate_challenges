package bootey.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pizza {
    private int id;
    private int nDiff;
    private String[] ingredients;

}
