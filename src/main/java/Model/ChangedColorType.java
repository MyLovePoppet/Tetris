package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * 位置i,j变化前后的颜色数据
 */
public class ChangedColorType {
    //在位置i,j处
    public final int i, j;
    //前后的颜色变化
    public final TetrisColorType oldColor;
    public final TetrisColorType newColor;

    public ChangedColorType(int i, int j, TetrisColorType oldColor, TetrisColorType newColor) {
        this.i = i;
        this.j = j;
        this.oldColor = oldColor;
        this.newColor = newColor;
    }

    /**
     * 遍历所有的颜色值，找出变化前后的数据
     * @param oldModel 旧的颜色数据类型
     * @param newModel 新的颜色数据类型
     * @return 所有的变化的颜色数据
     */
    public static List<ChangedColorType> getChangedColorType(TetrisDataModel oldModel, TetrisDataModel newModel) {
        List<ChangedColorType> changedColorTypes = new ArrayList<>();
        TetrisColorType[][] colors_old = oldModel.getColors();
        TetrisColorType[][] colors_new = newModel.getColors();
        //进行遍历寻找
        for (int i = 0; i < colors_old.length; i++) {
            for (int j = 0; j < colors_old[0].length; j++) {
                //颜色不同，加入到最后的List内
                if (colors_old[i][j] != colors_new[i][j]) {
                    changedColorTypes.add(new ChangedColorType(i, j, colors_old[i][j], colors_new[i][j]));
                }
            }
        }
        return changedColorTypes;
    }

    @Override
    public String toString() {
        return "ChangedColorType{" +
                "i=" + i +
                ", j=" + j +
                ", oldColor=" + oldColor +
                ", newColor=" + newColor +
                '}';
    }
}
