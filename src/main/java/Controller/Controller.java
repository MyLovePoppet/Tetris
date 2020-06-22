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

    /**
     * 在特定位置绘制一个方块
     * @param i 第i行
     * @param j 第j列
     * @param _color 绘制的颜色，null代表默认的背景色，即白色
     * @param isMainFrame 是不是在主界面内进行绘制，false为下一个方块的canvas内进行绘制
     */
    private void drawSquare(int i, int j, TetrisColorType _color, boolean isMainFrame) {
        Color color;
        if (_color == null)
            color = Color.WHITE;
        else
            color = _color.color;
        if (isMainFrame) {
            //在界面之外的数据，抛出异常
            if (i > ConstantValues.main_square_horizon_num.value - 1 ||
                    j > ConstantValues.main_square_vertical_num.value - 1)
                throw new IllegalArgumentException("draw square at" + i + " " + j + " error!");
            j = ConstantValues.main_square_vertical_num.value - j;
            main_frame_graphicsContext.setFill(color);
            //fillRect来进行填充一个矩形块，位置计算由i,j和方块的边长来进行计算
            main_frame_graphicsContext.fillRect(i * ConstantValues.square_length.value,
                    (j - 1) * ConstantValues.square_length.value, ConstantValues.square_length.value,
                    ConstantValues.square_length.value);
        } else {
            //与主界面绘制相同的逻辑
            if (i > ConstantValues.next_square_horizon_num.value - 1 || j > ConstantValues.next_square_vertical_num.value - 1)
                throw new IllegalArgumentException("draw square at" + i + " " + j + " error!");
            j = ConstantValues.next_square_vertical_num.value - j;
            next_frame_graphicsContext.setFill(color);
            next_frame_graphicsContext.setStroke(color);
            next_frame_graphicsContext.fillRect(i * ConstantValues.square_length.value, (j - 1) * ConstantValues.square_length.value,
                    ConstantValues.square_length.value, ConstantValues.square_length.value);
        }
    }

    /**
     * 尝试移动
     * @return 是否成功旋转，如果为true，说明旋转成功，如果为false，说明旋转不成功
     */
    public synchronized boolean tryRotate() {
        //获取下一个旋转的数据
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
                return false;
            }
            if (tetrisColorTypeMatrix[vec2.x][vec2.y] != null) {
                return false;
            }
        }
        currentShapes.forEach(vec2 -> tetrisColorTypeMatrix[vec2.x][vec2.y] = null);
        nextShapes.forEach(vec2 -> tetrisColorTypeMatrix[vec2.x][vec2.y] = tetrisType.color);

        tetrisDataModelProperty.set(new TetrisDataModel(tetrisColorTypeMatrix));
        currentRotate = nextRotate;
        currentRotateShape = nextRotateShape;
        return true;
    }

    /**
     * 尝试移动
     * @param type 移动的类型
     * @return 是否成功移动，如果为true，说明移动成功，如果为false，说明移动不成功
     */
    public synchronized boolean tryMove(TetrisMoveType type) {

        Vec2 movePos = type.vec;
        //当前的位置信息
        Set<Vec2> currentShapes = new HashSet<>(4);
        for (Vec2 tmp : currentRotateShape) {
            tmp = currentPos.add(tmp);
            currentShapes.add(tmp);
        }
        Vec2 nextPos = currentPos.add(movePos);
        //需要进行检测的位置信息（新的位置信息除去当前的位置信息）
        Set<Vec2> nextShapes = new HashSet<>(4);
        for (Vec2 tmp : currentRotateShape) {
            tmp = nextPos.add(tmp);
            if (currentShapes.contains(tmp)) {
                currentShapes.remove(tmp);
            } else {
                nextShapes.add(tmp);
            }
        }

        //检测是否可移动
        for (Vec2 tmp : nextShapes) {
            if (tmp.x < 0 || tmp.x >= ConstantValues.main_square_horizon_num.value
                    || tmp.y < 0 || tmp.y >= ConstantValues.main_square_vertical_num.value)
                return false;
            if (tetrisColorTypeMatrix[tmp.x][tmp.y] != null)
                return false;
        }
        //如果可移动，那么将移动过去
        currentShapes.forEach(vec2 -> tetrisColorTypeMatrix[vec2.x][vec2.y] = null);
        nextShapes.forEach(vec2 -> tetrisColorTypeMatrix[vec2.x][vec2.y] = tetrisType.color);
        //更新数据
        tetrisDataModelProperty.set(new TetrisDataModel(tetrisColorTypeMatrix));
        currentPos = nextPos;
        return true;
    }

    /**
     * 尝试进行消除
     * @return 消除的行数
     */
    public int tryClear() {
        int clearLine = 0;
        //锁住
        synchronized (tetrisColorTypeMatrix) {
            for (int j = 0; j < ConstantValues.main_square_vertical_num.value; j++) {
                boolean isClear = true;
                for (int i = 0; i < ConstantValues.main_square_horizon_num.value; i++) {
                    //如果有空的位置就消除不了
                    if (tetrisColorTypeMatrix[i][j] == null) {
                        isClear = false;
                        break;
                    }
                }
                //进行整体的行下移
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
        //获取所有的俄罗斯方块
        TetrisTypes[] values = TetrisTypes.values();
        tetrisType = values[random.nextInt(values.length)];
        currentRotate = random.nextInt(tetrisType.type.getRotateTimes());
        //下一个俄罗斯方块数据
        nextType = values[random.nextInt(values.length)];
        nextRotate = random.nextInt(nextType.type.getRotateTimes());
        //全为null表示背景色为白色
        tetrisColorTypeMatrix = new TetrisColorType[ConstantValues.main_square_horizon_num.value]
                [ConstantValues.main_square_vertical_num.value];
        tetrisDataModelProperty.set(new TetrisDataModel(tetrisColorTypeMatrix));
        label_mode.setText("");
    }

    private void drawLine() {
        //绘制矩形框细线
        main_frame_graphicsContext.setStroke(Color.BLACK);
        main_frame_graphicsContext.setLineWidth(2.0);
        //主界面
        //绘制竖线
        for (int i = 0; i <= ConstantValues.main_square_horizon_num.value; i++)
            main_frame_graphicsContext.strokeLine(i * ConstantValues.square_length.value, 0,
                    i * ConstantValues.square_length.value, canvas_main_frame.getHeight());
        //绘制横线
        for (int i = 0; i <= ConstantValues.main_square_vertical_num.value; i++)
            main_frame_graphicsContext.strokeLine(0, i * ConstantValues.square_length.value,
                    canvas_main_frame.getWidth(), i * ConstantValues.square_length.value);

        //下一个方块的界面
        next_frame_graphicsContext.setStroke(Color.BLACK);
        next_frame_graphicsContext.setLineWidth(2.0);
        //绘制竖线
        for (int i = 0; i <= ConstantValues.next_square_horizon_num.value; i++) {
            next_frame_graphicsContext.strokeLine(i * ConstantValues.square_length.value, 0,
                    i * ConstantValues.square_length.value, canvas_next_frame.getHeight());
        }
        //绘制横线
        for (int i = 0; i <= ConstantValues.next_square_vertical_num.value; i++)
            next_frame_graphicsContext.strokeLine(0, i * ConstantValues.square_length.value,
                    canvas_next_frame.getWidth(), i * ConstantValues.square_length.value);
    }

    private void initCanvas() {
        drawLine();
    }
    private void initTetrisFrame() {

        //俄罗斯方块数据（二维数组）
        tetrisColorTypeMatrix = new TetrisColorType[ConstantValues.main_square_horizon_num.value]
                [ConstantValues.main_square_vertical_num.value];
        //棋盘数据类型
        TetrisColorTypesModel = new TetrisDataModel(tetrisColorTypeMatrix);
        //创建绑定
        tetrisDataModelProperty = new SimpleObjectProperty<>(null,
                "TetrisColorTypesModel", TetrisColorTypesModel);

        //如果俄罗斯方块数据颜色发生改变，增加重绘事件
        tetrisDataModelProperty.addListener((observableValue, tetris_old, tetris_new) -> {
            //获取所有的发生改变了的颜色数据
            List<ChangedColorType> changedColorTypes = ChangedColorType.getChangedColorType(tetris_old, tetris_new);
            //针对变化的数据重新绘制颜色
            for (ChangedColorType changedColorType : changedColorTypes) {
                drawSquare(changedColorType.i, changedColorType.j, changedColorType.newColor, true);
                drawLine();
            }
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
        //设置为无线循环
        playTimeline.setCycleCount(Timeline.INDEFINITE);
        //1s下落一次
        playTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1.0), actionEvent -> {
            //放到事件派发线程内进行执行
            Platform.runLater(() -> {
                //不能继续下落
                if (!tryMove(TetrisMoveType.DOWN)) {
                    //生成下一个方块
                    TetrisTypes[] values = TetrisTypes.values();
                    tetrisType = nextType;
                    currentRotate = nextRotate;
                    nextType = values[random.nextInt(values.length)];
                    nextRotate = random.nextInt(nextType.type.getRotateTimes());
                    //当前位置为起始位置，表示游戏结束
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
        //设置键盘事件
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
