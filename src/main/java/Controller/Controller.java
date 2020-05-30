package Controller;

import Model.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;


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
    @FXML
    AnchorPane anchor_pane;

    TetrisDataModel TetrisColorTypesModel;
    SimpleObjectProperty<TetrisDataModel> tetrisDataModelProperty;
    TetrisColorType[][] tetrisColorTypeMatrix;
    Random random;


    int currentRotate;
    Vec2 currentPos;
    Vec2[] currentRotateShape;

    TetrisTypes tetrisType;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        main_frame_graphicsContext = canvas_main_frame.getGraphicsContext2D();
        next_frame_graphicsContext = canvas_next_frame.getGraphicsContext2D();

        initCanvas();

        initTetrisFrame();

    }

    private void drawSquare(int i, int j, TetrisColorType _color, boolean isMainFrame) {
        Color color;
        if (_color == null)
            color = Color.WHITE;
        else
            color = _color.color;
        if (isMainFrame) {
            if (i > 9 || j > 19)
                throw new IllegalArgumentException("draw square at" + i + " " + j + " error!");
            j = ConstantValues.main_square_vertical_num.value - j;
            main_frame_graphicsContext.setFill(color);
            main_frame_graphicsContext.fillRect(i * ConstantValues.square_length.value, (j - 1) * ConstantValues.square_length.value,
                    ConstantValues.square_length.value, ConstantValues.square_length.value);
        } else {
            if (i > 3 || j > 3)
                throw new IllegalArgumentException("draw square at" + i + " " + j + " error!");
            j = ConstantValues.next_square_vertical_num.value - j;
            next_frame_graphicsContext.setFill(color);
            next_frame_graphicsContext.setStroke(color);
            next_frame_graphicsContext.fillRect(i * ConstantValues.square_length.value, (j - 1) * ConstantValues.square_length.value,
                    ConstantValues.square_length.value, ConstantValues.square_length.value);
        }
    }

    public void drawColorMatrix() {
        TetrisColorType[][] colors = TetrisColorTypesModel.getColors();
        for (int i = 0; i < ConstantValues.main_square_horizon_num.value; i++) {
            for (int j = 0; j < ConstantValues.main_square_vertical_num.value; j++) {
                if (colors[i][j] != null)
                    drawSquare(i, j, colors[i][j], true);
            }
        }
    }

    public void rotate() {

    }

    public void updateTile() {
        int nextRotate = (currentRotate + 1) % tetrisType.type.getRotateTimes();
        Vec2[] nextRotateShape = tetrisType.type.getRotateShape(nextRotate);
        if (rotateValidCheck()) {
            for (Vec2 tmp : currentRotateShape) {
                tetrisColorTypeMatrix[tmp.x][tmp.y] = null;
            }
            for (Vec2 tmp : nextRotateShape) {
                tetrisColorTypeMatrix[tmp.x][tmp.y] = tetrisType.color;
            }
            tetrisDataModelProperty.set(new TetrisDataModel(tetrisColorTypeMatrix));
            currentRotate = nextRotate;
            currentRotateShape = nextRotateShape;
        }
    }

    public boolean rotateValidCheck() {
        return true;
    }

    public boolean collisionValidCheck(int index) {

        return true;
    }

    public void moveDown() {
        for (int i = 0; i < ConstantValues.main_square_vertical_num.value - 1; i++) {
            if (collisionValidCheck(i)) {
                for (int j = 0; j < ConstantValues.main_square_horizon_num.value; j++) {
                    tetrisColorTypeMatrix[j][i] = tetrisColorTypeMatrix[j][i + 1];
                }
            }
        }
        for (int i = 0; i < ConstantValues.main_square_horizon_num.value; i++)
            tetrisColorTypeMatrix[i][ConstantValues.main_square_vertical_num.value - 1] = null;
        tetrisDataModelProperty.set(new TetrisDataModel(tetrisColorTypeMatrix));
    }

    public void newTile() {
        currentPos = Vec2.Tetris_Default_Pawn_Location;
        currentRotate = 0;
        TetrisTypes[] values = TetrisTypes.values();
        tetrisType = values[random.nextInt(values.length)];
        Vec2[] rotateShape = tetrisType.type.getRotateShape(currentRotate);
        for (Vec2 vec : rotateShape) {
            Vec2 tmp = currentPos.add(vec);
            tetrisColorTypeMatrix[tmp.x][tmp.y] = tetrisType.color;
        }
        tetrisDataModelProperty.set(new TetrisDataModel(tetrisColorTypeMatrix));
        Platform.runLater(() -> {
            label_mode.setText(tetrisType.name());
        });
    }

    private void drawLine() {
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
    }

    private void initCanvas() {
        drawLine();
    }

    private void initTetrisFrame() {
        //绑定俄罗斯方块数据
        tetrisColorTypeMatrix = new TetrisColorType[ConstantValues.main_square_horizon_num.value]
                [ConstantValues.main_square_vertical_num.value];
        TetrisColorTypesModel = new TetrisDataModel(tetrisColorTypeMatrix);
        tetrisDataModelProperty = new SimpleObjectProperty<>(null, "TetrisColorTypesModel", TetrisColorTypesModel);


        //如果俄罗斯方块数据颜色发生改变，增加重绘事件
        tetrisDataModelProperty.addListener((observableValue, tetris_old, tetris_new) -> {
            List<ChangedColorType> changedColorTypes = ChangedColorType.getChangedColorType(tetris_old, tetris_new);
            Platform.runLater(() -> {
                for (ChangedColorType changedColorType : changedColorTypes) {
                    drawSquare(changedColorType.i, changedColorType.j, changedColorType.newColor, true);
                    drawLine();
                }
            });
        });
        random = new Random();
        newTile();

        Timeline timeline = new Timeline();
        timeline.setCycleCount(18);
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1.0), actionEvent -> moveDown()));
        timeline.play();
    }
}
