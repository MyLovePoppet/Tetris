package Model;

public enum TetrisMoveType {
    DOWN(Vec2.valueOf(0, -1)),
    LEFT(Vec2.valueOf(-1, 0)),
    RIGHT(Vec2.valueOf(1, 0)),
    Tetris_Default_Pawn_Location(Vec2.valueOf(4, 18)),
    Tetris_Default_Next_Location(Vec2.valueOf(2, 2));
    final public Vec2 vec;

    TetrisMoveType(Vec2 vec) {
        this.vec = vec;
    }

}
