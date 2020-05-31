package Model;

import java.util.Objects;

public class Vec2 {
    final public int x, y;
    ;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vec2 vec2 = (Vec2) o;
        return x == vec2.x &&
                y == vec2.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
