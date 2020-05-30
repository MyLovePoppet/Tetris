package Model;

import java.util.ArrayList;
import java.util.List;

public class ChangedColorType {
    public final int i, j;
    public final TetrisColorType oldColor;
    public final TetrisColorType newColor;

    public ChangedColorType(int i, int j, TetrisColorType oldColor, TetrisColorType newColor) {
        this.i = i;
        this.j = j;
        this.oldColor = oldColor;
        this.newColor = newColor;
    }

    public static List<ChangedColorType> getChangedColorType(TetrisDataModel oldModel, TetrisDataModel newModel) {
        List<ChangedColorType> changedColorTypes = new ArrayList<>();
        TetrisColorType[][] colors_old = oldModel.getColors();
        TetrisColorType[][] colors_new = newModel.getColors();
        for (int i = 0; i < ConstantValues.main_square_horizon_num.value; i++) {
            for (int j = 0; j < ConstantValues.main_square_vertical_num.value; j++) {
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
