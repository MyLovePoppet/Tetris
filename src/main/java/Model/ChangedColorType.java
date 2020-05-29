package Model;

import java.util.ArrayList;
import java.util.List;

public class ChangedColorType {
    public final int i,j;
    public final TetrisColor oldColor;
    public final TetrisColor newColor;

    public ChangedColorType(int i, int j, TetrisColor oldColor, TetrisColor newColor) {
        this.i = i;
        this.j = j;
        this.oldColor = oldColor;
        this.newColor = newColor;
    }

    public static List<ChangedColorType>getChangedColorType(TetrisDataModel oldModel, TetrisDataModel newModel){
        List<ChangedColorType> changedColorTypes=new ArrayList<>();
        TetrisColor [][]colors_old=oldModel.getColors();
        TetrisColor [][]colors_new=newModel.getColors();
        for(int i=0;i<ConstantValues.main_square_horizon_num.value;i++){
            for(int j=0;j<ConstantValues.main_square_vertical_num.value;j++){
                if(colors_old[i][j]!=colors_new[i][j]) {
                    changedColorTypes.add(new ChangedColorType(i,j,colors_old[i][j],colors_new[i][j]));
                }
            }
        }
        return changedColorTypes;
    }
}
