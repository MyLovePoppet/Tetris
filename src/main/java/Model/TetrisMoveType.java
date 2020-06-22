package Model;

/**
 * 俄罗斯方块的移动类型
 */
public enum TetrisMoveType {
    DOWN(Vec2.valueOf(0, -1)),
    LEFT(Vec2.valueOf(-1, 0)),
    RIGHT(Vec2.valueOf(1, 0)),
    //主位置出生点
    Tetris_Default_Pawn_Location(Vec2.valueOf(4, 18)),
    //下一个俄罗斯方块的出生点
    Tetris_Default_Next_Location(Vec2.valueOf(2, 2));
    final public Vec2 vec;

    TetrisMoveType(Vec2 vec) {
        this.vec = vec;
    }
}
