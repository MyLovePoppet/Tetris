package Model;

import java.util.Arrays;

public class TetrisDataModel {

    private TetrisColorType[][] colors;

    public void setColors(TetrisColorType[][] colors) {
        this.colors = new TetrisColorType[ConstantValues.main_square_horizon_num.value]
                [ConstantValues.main_square_vertical_num.value];
        for (int i = 0; i < colors.length; i++)
            System.arraycopy(colors[i], 0, this.colors[i], 0, colors[i].length);
    }

    public TetrisDataModel(TetrisColorType[][] colors) {
        this.colors = new TetrisColorType[ConstantValues.main_square_horizon_num.value]
                [ConstantValues.main_square_vertical_num.value];
        for (int i = 0; i < colors.length; i++)
            System.arraycopy(colors[i], 0, this.colors[i], 0, colors[i].length);
    }

    public TetrisColorType[][] getColors() {
        return colors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TetrisDataModel)) return false;
        TetrisColorType[][] colors1 = ((TetrisDataModel) o).colors;
        return Arrays.deepEquals(this.colors, colors1);
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