package Model;

import javafx.scene.paint.Color;

/**
 * 7种不同的俄罗斯方块的颜色定义
 */
public enum TetrisColorType {
    Type_O(Color.RED), Type_I(Color.GREEN),
    Type_Z(Color.YELLOW), Type_L(Color.BLUE),
    Type_J(Color.ORANGE), Type_S(Color.CYAN),
    Type_T(Color.PURPLE);

    final public Color color;

    TetrisColorType(Color color) {
        this.color = color;
    }
}
