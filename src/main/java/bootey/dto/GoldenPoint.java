package bootey.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
public class GoldenPoint extends Point {

    public GoldenPoint(int x, int y) {
        super(x, y);
    }
}
