package Model;

import java.util.Arrays;

/**
 * Canvas内的俄罗斯方块数据类型，只要记录每个方块的颜色即可
 */
public class TetrisDataModel {
    //Canvas内的数据
    private TetrisColorType[][] colors;

    /**
     * 拷贝构造
     *
     * @param colors 被拷贝的数据
     */
    public TetrisDataModel(TetrisColorType[][] colors) {
        this.colors = new TetrisColorType[colors.length]
                [colors[0].length];
        for (int i = 0; i < colors.length; i++)
            System.arraycopy(colors[i], 0, this.colors[i], 0, colors[i].length);
    }

    public void setColors(TetrisColorType[][] colors) {
        this.colors = new TetrisColorType[colors.length]
                [colors[0].length];
        for (int i = 0; i < colors.length; i++)
            System.arraycopy(colors[i], 0, this.colors[i], 0, colors[i].length);
    }

    public TetrisColorType[][] getColors() {
        return colors;
    }

    /**
     * 重载equals方法，使用Arrays.deepEquals()来进行比较
     * @param o 比较的对象
     * @return 是否数据相等
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TetrisDataModel)) return false;
        TetrisColorType[][] colors1 = ((TetrisDataModel) o).colors;
        //使用Arrays.deepEquals()来进行比较
        return Arrays.deepEquals(this.colors, colors1);
    }

    /**
     * 重载hashCode方法，使用Arrays.hashCode()来完成
     * @return 对象的hashCode
     */
    @Override
    public int hashCode() {
        return Arrays.hashCode(colors);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (TetrisColorType[] color : colors) {
            for (int j = 0; j < colors.length; j++) {
                if (color[j] == null)
                    stringBuilder.append("null");
                else
                    stringBuilder.append(color[j]);
                stringBuilder.append('\t');
            }
        }
        return stringBuilder.toString();
    }

}