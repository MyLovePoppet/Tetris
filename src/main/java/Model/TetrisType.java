package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * 俄罗斯方块的类型
 */
public class TetrisType {
    //默认的俄罗斯方块类型的方块数目，目前都是4个，用于检测数据的有效性
    final static int TETIRS_TYPE_SIZE = 4;
    //不同的旋转的位置的存在
    List<Vec2[]> rotateTypes;
    //可以旋转的次数
    int rotateTimes;

    public TetrisType(List<Vec2[]> rotateTypes) {
        this.rotateTypes = new ArrayList<>(rotateTypes);
        this.rotateTimes = rotateTypes.size();
    }

    public Vec2[] getRotateShape(int index) {
        return rotateTypes.get(index);
    }

    public List<Vec2[]> getAllRotateShape() {
        return rotateTypes;
    }

    public void addRotateShape(Vec2[] values) {
        if (values.length != TETIRS_TYPE_SIZE)
            throw new IllegalArgumentException("Tetris size is not 4!");
        rotateTypes.add(values);
    }

    public int getRotateTimes() {
        return rotateTimes;
    }

    public void setRotateTimes(int rotateTimes) {
        this.rotateTimes = rotateTimes;
    }

    //7种不同的俄罗斯方块的数据进行生成

    /**
     * O类型的俄罗斯方块的数据生成，O类型只有一种旋转类型
     * @return 类型的俄罗斯
     */
    public static TetrisType TetrisType_O() {
        List<Vec2[]> rotateShapes = new ArrayList<>(1);
        rotateShapes.add(new Vec2[]{Vec2.valueOf(-1, 0), Vec2.valueOf(-1, -1),
                Vec2.valueOf(0, -1), Vec2.valueOf(0, 0)});
        return new TetrisType(rotateShapes);
    }

    /**
     * L类型的俄罗斯方块的数据生成，L类型有三种旋转类型
     * @return 类型的俄罗斯
     */
    public static TetrisType TetrisType_L() {
        List<Vec2[]> rotateShapes = new ArrayList<>(4);
        rotateShapes.add(new Vec2[]{Vec2.valueOf(0, 0), Vec2.valueOf(-1, 0),
                Vec2.valueOf(1, 0), Vec2.valueOf(-1, -1)});
        rotateShapes.add(new Vec2[]{Vec2.valueOf(0, 1), Vec2.valueOf(0, 0),
                Vec2.valueOf(0, -1), Vec2.valueOf(1, -1)});
        rotateShapes.add(new Vec2[]{Vec2.valueOf(1, 1), Vec2.valueOf(-1, 0),
                Vec2.valueOf(0, 0), Vec2.valueOf(1, 0)});
        rotateShapes.add(new Vec2[]{Vec2.valueOf(-1, 1), Vec2.valueOf(0, 1),
                Vec2.valueOf(0, 0), Vec2.valueOf(0, -1)});
        return new TetrisType(rotateShapes);
    }

    public static TetrisType TetrisType_I() {
        List<Vec2[]> rotateShapes = new ArrayList<>(2);
        rotateShapes.add(new Vec2[]{Vec2.valueOf(-2, 0), Vec2.valueOf(-1, 0),
                Vec2.valueOf(0, 0), Vec2.valueOf(1, 0)});
        rotateShapes.add(new Vec2[]{Vec2.valueOf(0, 1),
                Vec2.valueOf(0, 0), Vec2.valueOf(0, -1), Vec2.valueOf(0, -2)});
        return new TetrisType(rotateShapes);
    }

    public static TetrisType TetrisType_S() {
        List<Vec2[]> rotateShapes = new ArrayList<>(2);
        rotateShapes.add(new Vec2[]{Vec2.valueOf(-1, -1), Vec2.valueOf(0, -1),
                Vec2.valueOf(0, 0), Vec2.valueOf(1, 0)});
        rotateShapes.add(new Vec2[]{Vec2.valueOf(0, 1), Vec2.valueOf(0, 0),
                Vec2.valueOf(1, 0), Vec2.valueOf(1, -1)});
        return new TetrisType(rotateShapes);
    }

    public static TetrisType TetrisType_Z() {
        List<Vec2[]> rotateShapes = new ArrayList<>(2);
        rotateShapes.add(new Vec2[]{Vec2.valueOf(-1, 0), Vec2.valueOf(0, 0),
                Vec2.valueOf(0, -1), Vec2.valueOf(1, -1)});
        rotateShapes.add(new Vec2[]{Vec2.valueOf(1, 1), Vec2.valueOf(1, 0),
                Vec2.valueOf(0, 0), Vec2.valueOf(0, -1)});
        return new TetrisType(rotateShapes);
    }

    public static TetrisType TetrisType_J() {
        List<Vec2[]> rotateShapes = new ArrayList<>(4);
        rotateShapes.add(new Vec2[]{Vec2.valueOf(-1, 0), Vec2.valueOf(0, 0),
                Vec2.valueOf(1, 0), Vec2.valueOf(1, -1)});
        rotateShapes.add(new Vec2[]{Vec2.valueOf(0, 1), Vec2.valueOf(0, 0),
                Vec2.valueOf(0, -1), Vec2.valueOf(1, -1)});
        rotateShapes.add(new Vec2[]{Vec2.valueOf(-1, 1), Vec2.valueOf(-1, 0),
                Vec2.valueOf(0, 0), Vec2.valueOf(1, 0)});
        rotateShapes.add(new Vec2[]{Vec2.valueOf(-1, 1), Vec2.valueOf(0, 1),
                Vec2.valueOf(0, 0), Vec2.valueOf(0, -1)});
        return new TetrisType(rotateShapes);
    }

    public static TetrisType TetrisType_T() {
        List<Vec2[]> rotateShapes = new ArrayList<>(4);
        rotateShapes.add(new Vec2[]{Vec2.valueOf(-1, 0), Vec2.valueOf(0, 0),
                Vec2.valueOf(1, 0), Vec2.valueOf(0, -1)});
        rotateShapes.add(new Vec2[]{Vec2.valueOf(0, 1), Vec2.valueOf(0, 0),
                Vec2.valueOf(0, -1), Vec2.valueOf(1, 0)});
        rotateShapes.add(new Vec2[]{Vec2.valueOf(-1, 0), Vec2.valueOf(0, 0),
                Vec2.valueOf(1, 0), Vec2.valueOf(0, 1)});
        rotateShapes.add(new Vec2[]{Vec2.valueOf(0, 1), Vec2.valueOf(0, 0),
                Vec2.valueOf(0, -1), Vec2.valueOf(-1, 0)});
        return new TetrisType(rotateShapes);
    }
}

