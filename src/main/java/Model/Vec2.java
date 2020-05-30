package Model;

public class Vec2 {
    final public int x, y;
    public final static Vec2 Tetris_Default_Pawn_Location = Vec2.valueOf(4, 18);

    public Vec2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Vec2 valueOf(int x, int y) {
        return new Vec2(x, y);
    }

    public Vec2 add(Vec2 vec) {
        return Vec2.valueOf(x + vec.x, y + vec.y);
    }
}
