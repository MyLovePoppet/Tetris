package Model;

import java.util.ArrayList;
import java.util.List;

public class TetrisType {
    final static int TETIRS_TYPE_SIZE = 4;
    List<Vec2[]> rotateTypes;
    int rotateTimes;

    public TetrisType(List<Vec2[]> rotateTypes) {
        this.rotateTypes = new ArrayList<>(rotateTypes);
        this.rotateTimes = rotateTypes.size();
    }

    public Vec2[] getRotateShape(int index) {
        return rotateTypes.get(index);
    }

    public List<Vec2[]> getAllRotateShape(int index) {
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

    public static TetrisType TetrisType_O() {
        List<Vec2[]> rotateShapes = new ArrayList<>(1);
        rotateShapes.add(new Vec2[]{Vec2.valueOf(-1, 0), Vec2.valueOf(-1, -1),
                Vec2.valueOf(0, -1), Vec2.valueOf(0, 0)});
        return new TetrisType(rotateShapes);
    }

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

