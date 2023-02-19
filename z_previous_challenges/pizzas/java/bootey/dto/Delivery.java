package bootey.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


@Data
@AllArgsConstructor
public class Delivery {
    private int nDeliveredPizzas;
    private List<Pizza> deliveredPizzas;
}
