package Model;

public enum TetrisTypes {
    Type_O(TetrisType.TetrisType_O(), TetrisColorType.Type_O),
    Type_I(TetrisType.TetrisType_I(), TetrisColorType.Type_I),
    Type_J(TetrisType.TetrisType_J(), TetrisColorType.Type_J),
    Type_L(TetrisType.TetrisType_L(), TetrisColorType.Type_L),
    Type_Z(TetrisType.TetrisType_Z(), TetrisColorType.Type_Z),
    Type_T(TetrisType.TetrisType_T(), TetrisColorType.Type_T),
    Type_S(TetrisType.TetrisType_S(), TetrisColorType.Type_S);


    final public TetrisType type;
    final public TetrisColorType color;

    TetrisTypes(TetrisType type, TetrisColorType color) {
        this.type = type;
        this.color = color;
    }
}
