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
        this.colors = colors;
    }

    public TetrisColor[][] getColors() {
        return colors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TetrisDataModel)) return false;
        System.out.println(this.hashCode()+"\t"+o.hashCode());
        TetrisColor [][]colors1=((TetrisDataModel)o).colors;
        System.out.println(this.toString());
        System.out.println(o.toString());
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
        System.out.println(true);
        return true;
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
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }

//    @Override
//    public int hashCode() {
//        return Arrays.hashCode(colors);
//    }
}