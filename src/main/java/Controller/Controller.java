package Controller;

import Model.ConstantValues;
import Model.TetrisColor;
import Model.TetrisDataModel;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableObjectValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.Thread.sleep;

public class Controller implements Initializable {
    @FXML
    Canvas canvas_main_frame;
    @FXML
    Canvas canvas_next_frame;
    GraphicsContext main_frame_graphicsContext;
    GraphicsContext next_frame_graphicsContext;

    @FXML
    Label label_mode;
    @FXML
    Label label_time;
    @FXML
    Label label_score;
    @FXML
    Label label_high_score;
    TetrisDataModel tetrisColorModel;
    SimpleObjectProperty<TetrisDataModel>tetrisDataModelProperty;
    TetrisColor [][]tetrisColorMatrix;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        main_frame_graphicsContext = canvas_main_frame.getGraphicsContext2D();
        next_frame_graphicsContext = canvas_next_frame.getGraphicsContext2D();

        initCanvas();
        drawSquare(0,0,Color.RED,true);
        drawSquare(3,3,Color.BLUE,false);
    }

    private void drawSquare(int i,int j,Color color,boolean isMainFrame){
        if(isMainFrame){
            if(i>9||j>19)
                throw new IllegalArgumentException("draw square at"+i+" "+j+" error!");
            j= ConstantValues.main_square_vertical_num.value-j;
            main_frame_graphicsContext.setFill(color);
            main_frame_graphicsContext.fillRect(i*ConstantValues.square_length.value, (j-1)*ConstantValues.square_length.value,
                    ConstantValues.square_length.value,  ConstantValues.square_length.value);
        }else{
            if(i>3||j>3)
                throw new IllegalArgumentException("draw square at"+i+" "+j+" error!");
            j=ConstantValues.next_square_vertical_num.value-j;
            next_frame_graphicsContext.setFill(color);
            next_frame_graphicsContext.setStroke(color);
            next_frame_graphicsContext.fillRect(i*ConstantValues.square_length.value, (j-1)*ConstantValues.square_length.value,
                    ConstantValues.square_length.value, ConstantValues.square_length.value);
        }
    }
    public void drawColorMatrix(){
        TetrisColor [][]colors=tetrisColorModel.getColors();
        for(int i=0;i<ConstantValues.main_square_horizon_num.value;i++){
            for(int j=0;j<ConstantValues.main_square_vertical_num.value;j++){
                if(colors[i][j]!=null)
                    drawSquare(i,j,colors[i][j].color,true);
            }
        }
    }
    private void initCanvas() {
        main_frame_graphicsContext.setStroke(Color.BLACK);
        main_frame_graphicsContext.setLineWidth(2.0);
        for (int i = 0; i <= ConstantValues.main_square_horizon_num.value; i++)
            main_frame_graphicsContext.strokeLine(i * ConstantValues.square_length.value, 0,
                    i * ConstantValues.square_length.value, canvas_main_frame.getHeight());
        for (int i = 0; i <= ConstantValues.main_square_vertical_num.value; i++)
            main_frame_graphicsContext.strokeLine(0, i * ConstantValues.square_length.value,
                    canvas_main_frame.getWidth(), i * ConstantValues.square_length.value);

        next_frame_graphicsContext.setStroke(Color.BLACK);
        next_frame_graphicsContext.setLineWidth(2.0);
        for (int i = 0; i <= ConstantValues.next_square_horizon_num.value; i++) {
            next_frame_graphicsContext.strokeLine(i * ConstantValues.square_length.value, 0,
                    i * ConstantValues.square_length.value, canvas_next_frame.getHeight());
        }
        for (int i = 0; i <= ConstantValues.next_square_vertical_num.value; i++)
            next_frame_graphicsContext.strokeLine(0, i * ConstantValues.square_length.value,
                    canvas_next_frame.getWidth(), i * ConstantValues.square_length.value);
        tetrisColorMatrix=new TetrisColor[ConstantValues.main_square_horizon_num.value]
                [ConstantValues.main_square_vertical_num.value];
        tetrisColorModel=new TetrisDataModel(tetrisColorMatrix);
        tetrisDataModelProperty=new SimpleObjectProperty<>(null,"TetrisColorModel",tetrisColorModel);
        tetrisDataModelProperty.addListener((observableValue, tetris_old, tetris_new) -> {
            TetrisColor [][]colors_old=tetris_old.getColors();
            TetrisColor [][]colors_new=tetris_new.getColors();
            System.out.println(tetris_old);
            System.out.println(tetris_new);
            for(int i=0;i<ConstantValues.main_square_horizon_num.value;i++){
                for(int j=0;j<ConstantValues.main_square_vertical_num.value;j++){
                    if(colors_old[i][j]!=null&&!(colors_old[i][j].color.equals(colors_new[i][j].color)))
                    {
                        drawSquare(i,j,colors_new[i][j].color,true);
                        System.out.println("old"+i+" "+j+colors_old[i][j]+"\tnew "+i+" "+j+colors_new[i][j]);
                    }
                }
            }
        });
        tetrisColorMatrix[5][5]=TetrisColor.Type_T;
        tetrisDataModelProperty.set(new TetrisDataModel(tetrisColorMatrix));
        System.out.println("sleep");
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        tetrisColorMatrix[6][6]=TetrisColor.Type_Z;
        tetrisDataModelProperty.set(new TetrisDataModel(tetrisColorMatrix));
    }
}
