package Model;

import java.util.Arrays;

public class TetrisDataModel{

    private TetrisColor[][] colors;

    public void setColors(TetrisColor[][] colors) {
        this.colors = new TetrisColor[ConstantValues.main_square_horizon_num.value]
                [ConstantValues.main_square_vertical_num.value];
        for(int i=0;i<colors.length;i++)
            System.arraycopy(colors[i], 0, this.colors[i], 0, colors[i].length);
    }

    public TetrisDataModel(TetrisColor[][] colors) {
        this.colors = new TetrisColor[ConstantValues.main_square_horizon_num.value]
                [ConstantValues.main_square_vertical_num.value];
        for(int i=0;i<colors.length;i++)
            System.arraycopy(colors[i], 0, this.colors[i], 0, colors[i].length);
    }

    public TetrisColor[][] getColors() {
        return colors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TetrisDataModel)) return false;
        TetrisColor [][]colors1=((TetrisDataModel)o).colors;
        return Arrays.deepEquals(this.colors,colors1);
        /*
        for(int i=0;i<colors.length;i++)
            for(int j=0;j<colors[i].length;j++){
                if(colors[i][j]==null&&colors1[i][j]==null){
                }
                else if(colors[i][j]!=null&&colors1[i][j]!=null){
                    if(colors[i][j]!=colors1[i][j])
                        return false;
                }
                else
                    return false;
            }

        return true;*/
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder=new StringBuilder();
        for (TetrisColor[] color : colors) {
            for (int j = 0; j < colors.length; j++) {
                if(color[j]==null)
                    stringBuilder.append("null");
                else
                    stringBuilder.append(color[j]);
                stringBuilder.append('\t');
            }
        }
        return stringBuilder.toString();
    }

//    @Override
//    public int hashCode() {
//        return Arrays.hashCode(colors);
//    }
}