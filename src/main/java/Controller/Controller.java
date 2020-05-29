package Controller;

import Model.ChangedColorType;
import Model.ConstantValues;
import Model.TetrisColor;
import Model.TetrisDataModel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableObjectValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
    Random random;
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
        //绘制矩形框细线
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
        //绑定俄罗斯方块数据
        tetrisColorMatrix=new TetrisColor[ConstantValues.main_square_horizon_num.value]
                [ConstantValues.main_square_vertical_num.value];
        tetrisColorModel=new TetrisDataModel(tetrisColorMatrix);
        tetrisDataModelProperty=new SimpleObjectProperty<>(null,"TetrisColorModel",tetrisColorModel);


        //如果俄罗斯方块数据颜色发生改变，增加重绘事件
        tetrisDataModelProperty.addListener((observableValue, tetris_old, tetris_new) -> {
            List<ChangedColorType> changedColorTypes=ChangedColorType.getChangedColorType(tetris_old,tetris_new);
            Platform.runLater(()->{
                for(ChangedColorType changedColorType:changedColorTypes){
                    drawSquare(changedColorType.i,changedColorType.j,changedColorType.newColor.color,true);
                }
            });
        });


        random=new Random();
        Timeline timeline=new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1.0), actionEvent -> {
            int i,j;
            i=random.nextInt(ConstantValues.main_square_horizon_num.value);
            j=random.nextInt(ConstantValues.main_square_vertical_num.value);
            TetrisColor []colors=TetrisColor.values();
            TetrisColor randomColor=colors[random.nextInt(colors.length)];
            tetrisColorMatrix[i][j]=randomColor;
            tetrisDataModelProperty.set(new TetrisDataModel(tetrisColorMatrix));
            Platform.runLater(()->{
                label_mode.setText(i+" "+j+" "+randomColor.name());
            });
        }));
        timeline.play();
    }
}
