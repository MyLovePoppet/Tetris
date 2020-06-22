package Model;

import java.util.Objects;

//Vec2表示一个位置
public class Vec2 {
    final public int x, y;

    public Vec2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * 静态工厂方法来创建
     *
     * @param x x
     * @param y y
     * @return 创建完成的Vec2
     */
    public static Vec2 valueOf(int x, int y) {
        return new Vec2(x, y);
    }

    /**
     * Vec2的相加的实现
     *
     * @param vec 被加的Vec2
     * @return 创建一个新的Vec2对象
     */
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

    @Override
    public String toString() {
        return "Vec2{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
