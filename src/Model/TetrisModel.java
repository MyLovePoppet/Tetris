package Model;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableObjectValue;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;

public class TetrisModel {
    SimpleObjectProperty<TetrisDataModel>dataModel;
    TetrisModel(){
        dataModel=new SimpleObjectProperty<>(null,null);
        dataModel.addListener(new ChangeListener<TetrisDataModel>() {
            @Override
            public void changed(ObservableValue<? extends TetrisDataModel> observableValue, TetrisDataModel tetrisDataModel, TetrisDataModel t1) {

            }
        });
    }
    public TetrisDataModel getDataModel() {
        return dataModel.get();
    }

    public void setDataModel(TetrisDataModel dataModel) {
        this.dataModel.set(dataModel);
    }
}
