package Model;

import javafx.scene.paint.Color;

public enum ConstantValues{
    main_square_vertical_num(20),
    main_square_horizon_num(10),
    next_square_vertical_num(4),
    next_square_horizon_num(4),
    square_length(25);
    public final int value;

    ConstantValues(int value) {
        this.value = value;
    }
}
enum TetrisColor{
    Type_O(Color.RED),
    Type_I(Color.GREEN),
    Type_Z(Color.YELLOW),
    Type_L(Color.BLUE),
    Type_J(Color.ORANGE),
    Type_T(Color.PURPLE);

    TetrisColor(Color color) {
        this.color = color;
    }

    public final Color color;

}