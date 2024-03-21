package bootey.enums;

import lombok.Getter;

@Getter
public enum TileType {

    ORIZZONTALE_3("3", 1, 0),
    VERTICALE_C("C", 0, 1),
    ANGOLO_5("5", -1, -1),
    ANGOLO_6("6", -1, -1),
    ANGOLO_9("9", -1, -1),
    ANGOLO_A("A", -1, -1),
    T_BASSO_7("7", 1, 0), //TODO: al momento solo in orizzontale
    T_ALTO_B("B", 1, 0), //TODO: al momento solo in orizzontale
    T_VERSO_SX_D("D", 0, 1), //TODO: al momento solo in verticale
    T_VERSO_DX_E("E",0, 1), //TODO: al momento solo in verticale
    INCROCIO_F("F", 1, 1),

    ;


    private final String definition;
    // NOTA: valore -1 se ignorati
    private final int canHorizontal; // 0 if not, 1 if yes, 2 if yes and it's the only one
    private final int canVertical; // 0 if not, 1 if yes, 2 if yes and it's the only one


    TileType(String definition, int canHorizontal, int canVertical) {
        this.definition = definition;
        this.canHorizontal = canHorizontal;
        this.canVertical = canVertical;
    }


}
