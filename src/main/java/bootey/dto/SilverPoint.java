package bootey.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SilverPoint extends Point {
    private Integer score;

    public SilverPoint(int x, int y, int score) {
        super(x, y);
        this.score = score;
    }

}
