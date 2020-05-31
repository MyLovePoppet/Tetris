package Controller;

import Model.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;


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
    Scene scene;

    TetrisColorType[][] tetrisColorTypeMatrix;
    TetrisDataModel TetrisColorTypesModel;
    SimpleObjectProperty<TetrisDataModel> tetrisDataModelProperty;
    TetrisTypes nextType;
    int nextRotate;

    TetrisColorType[][] nextTetrisColorTypeMatrix;
    TetrisDataModel nextTetrisColorTypesModel;
    SimpleObjectProperty<TetrisDataModel> nextTetrisDataModelProperty;
    TetrisTypes tetrisType;
    int currentRotate;
    Vec2 currentPos;
    Vec2[] currentRotateShape;


    Timeline playTimeline;

    Random random;

    int score;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        main_frame_graphicsContext = canvas_main_frame.getGraphicsContext2D();
        next_frame_graphicsContext = canvas_next_frame.getGraphicsContext2D();
        random = new Random();
        score = 0;
        initCanvas();
        initTetrisFrame();
        initTimeline();
        initKeyEvent();

        newGame();
        newTile();
        playTimeline.play();
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

    public synchronized void tryRotate() {
        int nextRotate = (currentRotate + 1) % tetrisType.type.getRotateTimes();
        Vec2[] nextRotateShape = tetrisType.type.getRotateShape(nextRotate);
        Set<Vec2> currentShapes = new HashSet<>(4);
        for (Vec2 tmp : currentRotateShape) {
            tmp = currentPos.add(tmp);
            currentShapes.add(tmp);
        }
        Set<Vec2> nextShapes = new HashSet<>(4);
        for (Vec2 tmp : nextRotateShape) {
            tmp = currentPos.add(tmp);
            if (currentShapes.contains(tmp)) {
                currentShapes.remove(tmp);
            } else {
                nextShapes.add(tmp);
            }
        }

        for (Vec2 vec2 : nextShapes) {
            if (vec2.x < 0 || vec2.x >= ConstantValues.main_square_horizon_num.value
                    || vec2.y < 0 || vec2.y >= ConstantValues.main_square_vertical_num.value) {
                return;
            }
            if (tetrisColorTypeMatrix[vec2.x][vec2.y] != null) {
                return;
            }
        }
        currentShapes.forEach(vec2 -> tetrisColorTypeMatrix[vec2.x][vec2.y] = null);
        nextShapes.forEach(vec2 -> tetrisColorTypeMatrix[vec2.x][vec2.y] = tetrisType.color);

        tetrisDataModelProperty.set(new TetrisDataModel(tetrisColorTypeMatrix));
        currentRotate = nextRotate;
        currentRotateShape = nextRotateShape;

    }

    public synchronized boolean tryMove(TetrisMoveType type) {
        Vec2 movePos = type.vec;

        Set<Vec2> currentShapes = new HashSet<>(4);
        for (Vec2 tmp : currentRotateShape) {
            tmp = currentPos.add(tmp);
            currentShapes.add(tmp);
        }
        Vec2 nextPos = currentPos.add(movePos);
        Set<Vec2> nextShapes = new HashSet<>(4);
        for (Vec2 tmp : currentRotateShape) {
            tmp = nextPos.add(tmp);
            if (currentShapes.contains(tmp)) {
                currentShapes.remove(tmp);
            } else {
                nextShapes.add(tmp);
            }
        }


        for (Vec2 tmp : nextShapes) {
            if (tmp.x < 0 || tmp.x >= ConstantValues.main_square_horizon_num.value
                    || tmp.y < 0 || tmp.y >= ConstantValues.main_square_vertical_num.value)
                return false;
            if (tetrisColorTypeMatrix[tmp.x][tmp.y] != null)
                return false;
        }
        currentShapes.forEach(vec2 -> tetrisColorTypeMatrix[vec2.x][vec2.y] = null);
        nextShapes.forEach(vec2 -> tetrisColorTypeMatrix[vec2.x][vec2.y] = tetrisType.color);
        tetrisDataModelProperty.set(new TetrisDataModel(tetrisColorTypeMatrix));
        currentPos = nextPos;
        return true;
    }

    public int tryClear() {
        int clearLine = 0;
        synchronized (tetrisColorTypeMatrix) {
            for (int j = 0; j < ConstantValues.main_square_vertical_num.value; j++) {
                boolean isClear = true;
                for (int i = 0; i < ConstantValues.main_square_horizon_num.value; i++) {
                    if (tetrisColorTypeMatrix[i][j] == null) {
                        isClear = false;
                        break;
                    }
                }
                if (isClear) {
                    for (int jj = j; jj < ConstantValues.main_square_vertical_num.value - 1; jj++)
                        for (int ii = 0; ii < ConstantValues.main_square_horizon_num.value; ii++) {
                            tetrisColorTypeMatrix[ii][jj] = tetrisColorTypeMatrix[ii][jj + 1];
                        }
                    for (int ii = 0; ii < ConstantValues.main_square_horizon_num.value; ii++) {
                        tetrisColorTypeMatrix[ii][ConstantValues.main_square_vertical_num.value - 1] = null;
                    }
                    j--;
                    clearLine++;
                }
            }
        }
        return clearLine;
    }

    public void drawNextTile() {
        nextTetrisColorTypeMatrix = new TetrisColorType[ConstantValues.next_square_horizon_num.value]
                [ConstantValues.next_square_vertical_num.value];
        Vec2[] nextTetrisShape = nextType.type.getRotateShape(0);
        for (Vec2 vec2 : nextTetrisShape) {
            vec2 = vec2.add(TetrisMoveType.Tetris_Default_Next_Location.vec);
            nextTetrisColorTypeMatrix[vec2.x][vec2.y] = nextType.color;
        }
        nextTetrisDataModelProperty.set(new TetrisDataModel(nextTetrisColorTypeMatrix));
    }

    public void newTile() {
        drawNextTile();
        currentPos = TetrisMoveType.Tetris_Default_Pawn_Location.vec;
        currentRotateShape = tetrisType.type.getRotateShape(currentRotate);
        Vec2[] rotateShape = tetrisType.type.getRotateShape(currentRotate);
        synchronized (tetrisColorTypeMatrix) {
            for (Vec2 vec : rotateShape) {
                Vec2 tmp = currentPos.add(vec);
                tetrisColorTypeMatrix[tmp.x][tmp.y] = tetrisType.color;
            }
        }
        tetrisDataModelProperty.set(new TetrisDataModel(tetrisColorTypeMatrix));
    }

    public void newGame() {
        TetrisTypes[] values = TetrisTypes.values();
        tetrisType = values[random.nextInt(values.length)];
        currentRotate = random.nextInt(tetrisType.type.getRotateTimes());

        nextType = values[random.nextInt(values.length)];
        nextRotate = random.nextInt(nextType.type.getRotateTimes());

        tetrisColorTypeMatrix = new TetrisColorType[ConstantValues.main_square_horizon_num.value]
                [ConstantValues.main_square_vertical_num.value];
        tetrisDataModelProperty.set(new TetrisDataModel(tetrisColorTypeMatrix));
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


        //绑定俄罗斯方块数据
        nextTetrisColorTypeMatrix = new TetrisColorType[ConstantValues.next_square_horizon_num.value]
                [ConstantValues.next_square_vertical_num.value];
        nextTetrisColorTypesModel = new TetrisDataModel(nextTetrisColorTypeMatrix);
        nextTetrisDataModelProperty = new SimpleObjectProperty<>(null, "NextTetrisColorTypesModel", nextTetrisColorTypesModel);


        //如果俄罗斯方块数据颜色发生改变，增加重绘事件
        nextTetrisDataModelProperty.addListener((observableValue, tetris_old, tetris_new) -> {
            List<ChangedColorType> changedColorTypes = ChangedColorType.getChangedColorType(tetris_old, tetris_new);
            Platform.runLater(() -> {
                for (ChangedColorType changedColorType : changedColorTypes) {
                    drawSquare(changedColorType.i, changedColorType.j, changedColorType.newColor, false);
                    drawLine();
                }
            });
        });
    }

    private void initTimeline() {
        playTimeline = new Timeline();
        playTimeline.setCycleCount(Timeline.INDEFINITE);
        playTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1.0), actionEvent -> {
            Platform.runLater(() -> {
                if (!tryMove(TetrisMoveType.DOWN)) {
                    TetrisTypes[] values = TetrisTypes.values();
                    tetrisType = nextType;
                    currentRotate = nextRotate;
                    nextType = values[random.nextInt(values.length)];
                    nextRotate = random.nextInt(nextType.type.getRotateTimes());

                    if (currentPos.y == TetrisMoveType.Tetris_Default_Pawn_Location.vec.y) {
                        playTimeline.stop();
                        label_mode.setText("GameOver");
                        return;
                    }
                    int clearLine = tryClear();
                    score += clearLine;
                    label_score.setText("得分:" + score);
                    newTile();
                }
            });

        }));
        playTimeline.play();
    }

    public void initKeyEvent() {
        scene.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()) {
                case W:
                case UP:
                case SPACE:
                    label_high_score.setText("rotate");
                    tryRotate();
                    break;

                case A:
                case LEFT:
                    tryMove(TetrisMoveType.LEFT);
                    break;

                case D:
                case RIGHT:
                    tryMove(TetrisMoveType.RIGHT);
                    break;

                case S:
                case DOWN:
                    tryMove(TetrisMoveType.DOWN);
                    break;

                case R:
                    newGame();
                    newTile();
                    playTimeline.play();
            }
        });
    }
}
