#深圳大学考试答题纸
学    号	2017152044	姓名	舒钦瑜	专业年级	2017级计算机科学与技术
##题目：	基于JavaFX的俄罗斯方块的实现	


目标：完成一个JAVA Application应用开发：俄罗斯方块

要求：实现以下功能
设计一个简化版的俄罗斯方块游戏。游戏窗口大小是20×10的方形棋盘格阵列，游戏中出现的方块共有7种，每种方块的旋转中心由黑点标出。
具体实现内容和步骤包括：
### 1.	方块/棋盘格和方块向下移动
创建窗口，然后绘制网格线来完成棋盘格。随机选择方块并赋上颜色，从窗口最上方中间开始往下自动移动，每次移动一个格子。初始的方块类型和方向也必须随机选择，另外可以通过键盘控制方块向下移动的速度，在方块移动到窗口底部的时候，新的方块出现并重复上述移动过程。
### 2.	方块叠加
不断下落的方块需要能够相互叠加在一起，即不同的方块之间不能相互碰撞和叠加。另外，所有方块移动不能超出窗口的边界。
### 3.	键盘控制方块的移动
通过方向键（上/下/左/右）来控制方块的移动。按“上”键使方块以旋转中心顺（逆）时针旋转，每次旋转90°，按“左”和“右”键分别将方块向左/右方向移动一格，按“下”键加速方块移动。
### 4.	游戏逻辑
当游戏窗口中的任意一行被方块占满，该行即被消除，所有上面的方块向下移动一格子。当整个窗口被占满而不能再出现新的方块时，游戏结束。通过按下“q”键结束游戏，和按下“r”键重新开始游戏。





## 正文






### 1.	界面设计

最后的运行界面如下图所示：![](https://i.niupic.com/images/2020/06/24/8jbX.png)

我们的界面主要有三部分组成：左边为主要的游戏区域，右上方为下一个方块的形状，右下方为一些信息的显示（由于时间问题暂时只实现了得分的信息显示，后续有时间的话会继续考虑进行功能的完善）。
在这里我们主要的部分游戏界面以及下一个方块信息的提示的界面我们主要是使用JavaFx内的绘图组件Canvas来进行绘制线和颜色方块，而对于我们的右下的游戏运行的信息提示我们主要使用JavaFx内的Label组件来进行信息的显示。

我们可以直接使用Scene Builder进行图形化的创建界面，我们使用其Anchor Pane作为根组件，然后在这个根组件上进行添加两个Canvas绘图组件进行绘制主要的游戏界面以及下一个方块的信息的界面，然后在右下角添加三个Label，用于游戏运行时的信息提示。
![](https://i.niupic.com/images/2020/06/24/8jc4.png)

如上图左框内为我们的组件布局，右框内为我们的预览界面。可以看到我们的预览界面是没有任何的线框的，所以我们需要使用Canvas组件进行线框的绘制。

### 2.	基本线框的绘制以及在某个位置进行放置方框的实现

首先我们保存一下所有的关于界面的信息，比如方块的边长，主界面的大小，我们使用的是一个枚举常量进行保存，这也体现了设计模式内的使用枚举来代替常量的设计原则，代码如下：
 
然后我们要绘制线的话可以使用Canvas组件内的绘图组件内的绘制线的选项进行绘制线即可。而绘制线的话我们可以根据我们的常量内的方块的边长信息以及Canvas的长和宽进行绘制线，绘制主游戏界面的横线的具体代码如下：
	
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

而绘制下一个方块信息的横线和这部分代码是类似的，具体只需要改一下绘制线的数目即可，具体代码可以在附件内进行查看。
接下来我们编写在特定位置进行绘制方块的函数。我们的函数声明如下：

	/**
	 * 在特定位置绘制一个方块
	 * @param i 第i行
	 * @param j 第j列 
	 * @param _color 绘制的颜色，null代表默认的背景色，即白色
	 * @param isMainFrame 是不是在主界面内进行绘制，false为下一个方块的canvas内进行绘制
	 */
	private void drawSquare(int i, int j, TetrisColorType _color, boolean isMainFrame)
 
如果我们能将该函数编写下来，后续我们只需要调用该函数进行方块的绘制即可。而实现这个函数主要将是将我们传入的位置i和j进行计算出Canvas组件内的绘制矩形的位置，然后使用Canvas内的绘制组件内的绘制矩形的方法即可在给定的位置进行绘制一个给定颜色的方块。代码实现如下：

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
    }

如果不是在我们的主界面进行绘制，那么就是在我们的下一个方块的显示界面进行绘制，那么只要将我们的传入函数的最后一个`isMainFrame`参数设置为false即可，然后我们的代码的实现也是和主界面的绘制方块的代码是相同的，具体不过多复述。

### 3.	俄罗斯方块的模型定义

#### 1）	Vec2的位置信息的实现
实现这个Vec2的类主要是方便后续进行记录方块的位置信息，类内主要有两个int类型的成员，一个是x，一个是y，用于保存位置信息。我们为其创建构造方法以及静态工厂方法，重载其`equals`和`hashCode`方法，方便为后续进行判断两个位置是否相等提供便利。以及我们重载其`toString`方法进行信息的输出，方便后续进行调试。具体的代码实现如下:

	package Model;
	
	import java.util.Objects;
	
	//Vec2表示一个位置
	public class Vec2 {
	    final public int x, y;
	
	    public Vec2(int x, int y) {
	        this.x = x;
	        this.y = y;
	    }
	
	    /**
	     * 静态工厂方法来创建
	     *
	     * @param x x
	     * @param y y
	     * @return 创建完成的Vec2
	     */
	    public static Vec2 valueOf(int x, int y) {
	        return new Vec2(x, y);
	    }
	
	    /**
	     * Vec2的相加的实现
	     *
	     * @param vec 被加的Vec2
	     * @return 创建一个新的Vec2对象
	     */
	    public Vec2 add(Vec2 vec) {
	        return Vec2.valueOf(x + vec.x, y + vec.y);
	    }
	
	    @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;
	        Vec2 vec2 = (Vec2) o;
	        return x == vec2.x &&
	                y == vec2.y;
	    }
	
	    @Override
	    public int hashCode() {
	        return Objects.hash(x, y);
	    }
	
	    @Override
	    public String toString() {
	        return "Vec2{" +
	                "x=" + x +
	                ", y=" + y +
	                '}';
	    }
	}

#### 2）	俄罗斯方块数据类型的定义
一个俄罗斯方块的数据主要有两个信息组成，一个是可以旋转的次数，另一个是旋转的数据类型的表示。我们可以一个`Vec2[4]`来表示一个俄罗斯方块的当前旋转类型的数据（每个方块的一个旋转类型有4个方块，我们使用4个Vec2来表示这四个方块的位置）。如下图我们的O型方块可以表示为`{(-1, -1), (-1, 0), (0, -1), (0, 0)}`，而其他的每种方块的每种旋转类型都可以这样来表示。

![](https://i.niupic.com/images/2020/06/24/8jce.png)

接着我们将一个俄罗斯方块的所有的旋转数据用一个List来进行存放，每个元素是当前俄罗斯方块的旋转的数据表示。这个List的大小就代表我们的可以进行旋转的次数。
所以我们的TetrisType类定义如下：
 
	/**
	 * 俄罗斯方块的类型
	 */
	public class TetrisType {
	    //默认的俄罗斯方块类型的方块数目，目前都是4个，用于检测数据的有效性
	    final static int TETIRS_TYPE_SIZE = 4;
	    //不同的旋转的位置的存在
	    List<Vec2[]> rotateTypes;
	    //可以旋转的次数
	    int rotateTimes;
		//...	
	}

接着我们为其创建几个静态方法，用于创建7个不同的类型的俄罗斯方块。其中部分的类型的方块代码实现如下：
 	
	//（O类型的俄罗斯方块的生成）
	/**
     * O类型的俄罗斯方块的数据生成，O类型只有一种旋转类型
     * @return 类型的俄罗斯
     */
    public static TetrisType TetrisType_O() {
        List<Vec2[]> rotateShapes = new ArrayList<>(1);
        rotateShapes.add(new Vec2[]{Vec2.valueOf(-1, 0), Vec2.valueOf(-1, -1), Vec2.valueOf(0, -1), Vec2.valueOf(0, 0)});
        return new TetrisType(rotateShapes);
    }

 
	//（L类型的俄罗斯方块的数据生成）
	/**
     * L类型的俄罗斯方块的数据生成，L类型有三种旋转类型
     * @return 类型的俄罗斯
     */
    public static TetrisType TetrisType_L() {
        List<Vec2[]> rotateShapes = new ArrayList<>(4);
        rotateShapes.add(new Vec2[]{Vec2.valueOf(0, 0), Vec2.valueOf(-1, 0), Vec2.valueOf(1, 0), Vec2.valueOf(-1, -1)});
        rotateShapes.add(new Vec2[]{Vec2.valueOf(0, 1), Vec2.valueOf(0, 0), Vec2.valueOf(0, -1), Vec2.valueOf(1, -1)});
        rotateShapes.add(new Vec2[]{Vec2.valueOf(1, 1), Vec2.valueOf(-1, 0), Vec2.valueOf(0, 0), Vec2.valueOf(1, 0)});
        rotateShapes.add(new Vec2[]{Vec2.valueOf(-1, 1), Vec2.valueOf(0, 1), Vec2.valueOf(0, 0), Vec2.valueOf(0, -1)});
        return new TetrisType(rotateShapes);
    }

其他类型的俄罗斯方块的数据类型定义这里就不过多叙述，实现的逻辑都比较类似，具体的代码可在附件内进行查看。
#### 3）	俄罗斯方块的颜色定义
我们有7种不同的俄罗斯方块，为其设置7种不同的颜色即可，我们可以使用枚举变量来进行保存这7种不同俄罗斯方块对应的颜色，我这里是使用的红橙黄绿蓝靛紫来进行颜色的定义，代码如下：

	/**
	 * 7种不同的俄罗斯方块的颜色定义
	 */
	public enum TetrisColorType {
	    Type_O(Color.RED), Type_I(Color.GREEN),
	    Type_Z(Color.YELLOW), Type_L(Color.BLUE),
	    Type_J(Color.ORANGE), Type_S(Color.CYAN),
	    Type_T(Color.PURPLE);
	
	    final public Color color;
	
	    TetrisColorType(Color color) {
	        this.color = color;
	    }
	}

#### 4）	俄罗斯方块的定义
有了前面的俄罗斯方块的数据和颜色定义后，我们将颜色与类型结合起来就是我们的俄罗斯方块的类型，这也是一个enum类型，主要是将前面的数据类型和颜色类型进行组合。代码如下：

	/**
	 * 7种不同的俄罗斯方块的数据类型的定义，由数据类型和颜色类型进行组合
	 */
	public enum TetrisTypes {
	    Type_O(TetrisType.TetrisType_O(), TetrisColorType.Type_O),
	    Type_I(TetrisType.TetrisType_I(), TetrisColorType.Type_I),
	    Type_J(TetrisType.TetrisType_J(), TetrisColorType.Type_J),
	    Type_L(TetrisType.TetrisType_L(), TetrisColorType.Type_L),
	    Type_Z(TetrisType.TetrisType_Z(), TetrisColorType.Type_Z),
	    Type_T(TetrisType.TetrisType_T(), TetrisColorType.Type_T),
	    Type_S(TetrisType.TetrisType_S(), TetrisColorType.Type_S);
	
	    final public TetrisType type;
	    final public TetrisColorType color;
	
	    TetrisTypes(TetrisType type, TetrisColorType color) {
	        this.type = type;
	        this.color = color;
	    }
	}

### 4.	棋盘数据类型定义
棋盘内的数据类型其实使用一个二维数组记录每个位置的方块的颜色即可（null表示为背景色，默认为白色），如下：

	/**
	 * Canvas内的俄罗斯方块数据类型，只要记录每个方块的颜色即可
	 */
	public class TetrisDataModel {
	    //Canvas内的数据
	    private TetrisColorType[][] colors;
	
	    /**
	     * 拷贝构造
	     *
	     * @param colors 被拷贝的数据
	     */
	    public TetrisDataModel(TetrisColorType[][] colors) {
	        this.colors = new TetrisColorType[colors.length]
	                [colors[0].length];
	        for (int i = 0; i < colors.length; i++)
	            System.arraycopy(colors[i], 0, this.colors[i], 0, colors[i].length);
	    }
		//...
	}

由于后续我们会使用到JavaFX内的属性绑定来检测数据的变化，而数据的变化就要求我们重写其equals方法，根据我们的书上的内容，重载了equals方法后我们也要重载其hashCode方法，所以我们重写这两个函数如下：
 
	/**
     * 重载equals方法，使用Arrays.deepEquals()来进行比较
     * @param o 比较的对象
     * @return 是否数据相等
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TetrisDataModel)) return false;
        TetrisColorType[][] colors1 = ((TetrisDataModel) o).colors;
        //使用Arrays.deepEquals()来进行比较
        return Arrays.deepEquals(this.colors, colors1);
    }

    /**
     * 重载hashCode方法，使用Arrays.hashCode()来完成
     * @return 对象的hashCode
     */
    @Override
    public int hashCode() {
        return Arrays.hashCode(colors);
    }


### 5.	棋盘数据变化的重绘事件
虽然我们已经定义了我们棋盘上的俄罗斯方块的数据，一个比较方便的绘制方法是我们将该模型内的数据都传入到之前的drawSquare()方法内进行绘制即可。但是由于这种方法需要重绘所有的方块数据，还是比较麻烦的，而且性能不高，所以我们利用JavaFX内的属性绑定来监测我们的棋盘内的数据变化，然后针对这部分变化的数据进行重新绘制即可。
JavaFX的绑定的解读如下：

	JavaFX绑定同步两个值：当依赖变量更改时，其他变量更改。
	要将属性绑定到另一个属性，请调用bind()方法，该方法在一个方向绑定值。 例如，当属性A绑定到属性B时，属性B的更改将更新属性A，但不可以反过来。
	（来源https://www.yiibai.com/javafx/javafx_binding.html） 

所以我们可以实现我们的绑定事件来将我们的之前定义的棋盘数据类型进行绑定到我们的Canvas绘图组件上，在我们更新棋盘数据时，代码会自动进行更新我们的Canvas的绘图界面，使其保持同步变化。
为此，我们需要定义一个新的数据类型来代表当前棋盘颜色变化信息，该类内有位置信息i，j以及该位置处变化前后的颜色数据，代码如下：

	/**
	 * 位置i,j变化前后的颜色数据
	 */
	public class ChangedColorType {
	    //在位置i,j处
	    public final int i, j;
	    //前后的颜色变化
	    public final TetrisColorType oldColor;
	    public final TetrisColorType newColor;
	
	    public ChangedColorType(int i, int j, TetrisColorType oldColor, TetrisColorType newColor) {
	        this.i = i;
	        this.j = j;
	        this.oldColor = oldColor;
	        this.newColor = newColor;
	    }
		//...
	}

接着我们除了为其编写toString等基本重载方法之外，我们还需要编写一个静态函数，该函数传入两个棋盘内的所有的颜色信息数据（即前面定义的棋盘内的方块信息数据），然后计算出所有的改变的位置信息，将其返回。函数的声明如下：

	/**
	 * 遍历所有的颜色值，找出变化前后的数据
	 * @param oldModel 旧的颜色数据类型
	 * @param newModel 新的颜色数据类型
	 * @return 所有的变化的颜色数据
	 */
	public static List<ChangedColorType> getChangedColorType(TetrisDataModel oldModel, TetrisDataModel newModel)

这个函数主要是遍历前后矩阵内所有的颜色数据，将变化前后的数据进行对比，如果发现数据类型发生了改变，那么则加入到我们的List内，最后返回这个List，作为前后两次颜色数据的差异值，代码实现如下：

    List<ChangedColorType> changedColorTypes = new ArrayList<>();
    TetrisColorType[][] colors_old = oldModel.getColors();
    TetrisColorType[][] colors_new = newModel.getColors();
    //进行遍历寻找
    for (int i = 0; i < colors_old.length; i++) {
        for (int j = 0; j < colors_old[0].length; j++) {
            //颜色不同，加入到最后的List内
            if (colors_old[i][j] != colors_new[i][j]) {
                changedColorTypes.add(new ChangedColorType(i, j, colors_old[i][j], colors_new[i][j]));
            }
        }
    }
    return changedColorTypes;

最后我们的Canvas只需要针对这部分数据进行重新绘制即可，相比较原来的重新绘制整个棋盘的数据来说可以说是大大简化了操作信息。具体的创建绑定以及数据发生了变化之后的重新绘制代码实现如下：

	//俄罗斯方块数据（二维数组）
    tetrisColorTypeMatrix = new TetrisColorType[ConstantValues.main_square_horizon_num.value][ConstantValues.main_square_vertical_num.value];
    //棋盘数据类型
    TetrisColorTypesModel = new TetrisDataModel(tetrisColorTypeMatrix);
    //创建绑定
    tetrisDataModelProperty = new SimpleObjectProperty<>(null, "TetrisColorTypesModel", TetrisColorTypesModel);

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
 
### 6.	随机生成俄罗斯方块数据
我们这部分可以先获取到所有的俄罗斯方块的数据，然后使用`Random.nextInt()`方法随机获取下一个俄罗斯方块的数据，除了我们要生称下一个俄罗斯方块的数据，我们还需要生成下下一个俄罗斯方块的数据，用于展示在我们的下一个俄罗斯方块的数据。下下一个俄罗斯方块的生成的代码和我们之前的代码是相同的，都是使用的`Random.nextInt()`来进行完成。具体的代码实现如下：

 	//获取所有的俄罗斯方块
    TetrisTypes[] values = TetrisTypes.values();
    tetrisType = values[random.nextInt(values.length)];
    currentRotate = random.nextInt(tetrisType.type.getRotateTimes());
    //下一个俄罗斯方块数据
    nextType = values[random.nextInt(values.length)];
    nextRotate = random.nextInt(nextType.type.getRotateTimes());

###7.	俄罗斯方块的移动事件
首先我们定义一下俄罗斯方块的移动类型，有向下，向左和向右，以及我们将我们的俄罗斯方块的出生点的位置数据进行设定，如下：
 
	/**
	 * 俄罗斯方块的移动类型
	 */
	public enum TetrisMoveType {
	    DOWN(Vec2.valueOf(0, -1)),
	    LEFT(Vec2.valueOf(-1, 0)),
	    RIGHT(Vec2.valueOf(1, 0)),
	    //主位置出生点
	    Tetris_Default_Pawn_Location(Vec2.valueOf(4, 18)),
	    //下一个俄罗斯方块的出生点
	    Tetris_Default_Next_Location(Vec2.valueOf(2, 2));
	    final public Vec2 vec;
	
	    TetrisMoveType(Vec2 vec) {
	        this.vec = vec;
	    }
	}

接下来我们编写我们的移动函数，我们定义如下：

	/**
	 * 尝试移动
	 * @param type 移动的类型
	 * @return 是否成功移动，如果为true，说明移动成功，如果为false，说明移动不成功
	 */
	public synchronized boolean tryMove(TetrisMoveType type) 

我们这部分主要是根据传入的移动类型，然后判断如果移动到下一个移动的位置是否会发生冲突（要的移动到的位置已经有数据的存在），但是这部分要排除自身的位置的重叠所带来的的影响，所以我们使用集合`Set`来进行存放当前俄罗斯方块的位置信息，以及计算下一个俄罗斯方块的位置信息，然后我们将下一个俄罗斯方块的位置信息减去当前俄罗斯方块的位置信息，就可以得到除了自身之外的所有的新的位置的俄罗斯方块的数据。具体的代码实现如下：

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

然后对这部分新的数据进行判断是否已经存在数据，即根据这些位置进行判断是否已经存在方块数据，如果存在那么就认为是不可以移动，否则我们将直接移动过去即可。具体的代码实现如下：
 
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

### 8.	俄罗斯方块的旋转事件
其实这部分的逻辑和之前的俄罗斯方块的移动事件的处理过程是类似的，都编写了一个尝试进行的操作，这里编写的尝试旋转的函数名声明如下：

	/**
	 * 尝试旋转
	 * @return 是否成功旋转，如果为true，说明旋转成功，如果为false，说明旋转不成功
	 */
	public synchronized boolean tryRotate() 

而我们内部的操作其实和移动事件的处理是类似的，都是进行计算出要旋转的下一个位置，然后判断这个位置是否已经存在其他的方块了，如果存在其他的方块，那么说明旋转是不成功的，否则我们的旋转是成功的，并且将我们的方块旋转过去，唯一和移动的操作不同的是我们获取下一个位置的方法是不同的，移动是通过位置进行加减，而我们的旋转则是通过获取下一个旋转的类型，然后进行判断，具体的代码实现如下：
 
	//获取下一个旋转的数据
    int nextRotate = (currentRotate + 1) % tetrisType.type.getRotateTimes();
    Vec2[] nextRotateShape = tetrisType.type.getRotateShape(nextRotate);

而其他的判断的逻辑和上面移动的逻辑是相同的，这里不过多复述，具体可在附件的代码内进行查看。
### 9.	下一个俄罗斯方块生成的逻辑判断
这里我们主要通过之前的tryMove()函数进行判断，我们将向下的参数进行传入，如果返回false，那么证明我们已经无法下落了，那么就生成一个新的方块，继续进行游戏，但是如果这个方块的位置在尝试进行下落之后的位置仍然为初始生成的位置，那么就认为我们的当前游戏其实是结束了的，那么就执行游戏结束的逻辑。代码实现如下：
 
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
	}

### 10.	重新开始游戏
重新开始游戏的逻辑就比较简单了，主要是将我们的所有的颜色设置为背景色，即为白色即可。对应到我们的数据模型内，我们主要将矩阵内的颜色值设置为`null`就可以在后续的更新中将颜色设置为白色。然后我们还需要更新我们的下一个俄罗斯方块的数据，以及更新右上角下一个方块的数据。具体的代码实现如下：
 
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

### 11.	处理键盘事件
这里我们将键盘事件的ASD和方向键的下左右都设置为我们的方块的左右和向下移动操作，以及我们的W和方向上键以及空格键都设置为旋转，我们主要对这些操作执行对应的`tryMove()`操作以及`tryRotate()`操作即可。而传入的参数就根据我们的键盘按下的按键进行操作即可。例如按下A键执行的操作是`tryMove(TetrisMoveType.LEFT)`。部分代码实现如下：
 
	//设置键盘事件
    scene.setOnKeyPressed(keyEvent -> {
        switch (keyEvent.getCode()) {
            case W:
            case UP:
            case SPACE:
                //label_high_score.setText("rotate");
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
 
### 12.	俄罗斯方块自动下落事件
这里使用的是JavaFX自带的Timeline类，其可以按照人为的设定，每隔一段事件进行用户给的的操作。在这里我们主要将我们的时间间隔设置为1s，执行的事件就是`tryMove(TetrisMoveType.DOWN)`，表示俄罗斯方块的下落，如果不能继续下落，则表示生成下一个俄罗斯方块的数据或者是游戏结束即可。需要注意的是Timeline是自己实现了一套在另一个线程内执行的操作，但是我们更新Canvas来绘制界面是需要在事件派发线程，即EDT内进行操作的，所以我们执行的操作其实要使用事件派发线程来进行完成的。反应到代码里面是需要将我们的函数包装成一个线程，然后传给`Platform.runLater()`函数内进行操作即可。代码实现如下：
 
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


后续的GameOver之后我们要将该timeline停止，以及重新开始游戏后要将该timeline重新启动。

### 13.	消除操作

我们在每次下落完成之后都要进行检测是否能进行消除，检测能消除的条件是这一行没有空的方块，如果能进行消除操作的话我们将所有的上面的一行进行整体的下移，然后从该行开始继续向上进行检测是否能消除和进行消除操作。具体的代码实现如下：
 
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

到此，我们的俄罗斯方块的逻辑已经全部编写完成了。最后的运行结果如下：
![](https://i.niupic.com/images/2020/06/24/8jcy.png)
 
## 实验体会：
这次的大作业也用上了许多设计模式，如静态工厂方法（如Vec2内的`valueOf`，俄罗斯方块的7种数据类型的生成），使用枚举代替静态常量（常量定义，俄罗斯方块的数据类型，颜色类型等都用到了枚举变量），习惯重写其`equals`，`hashCode`和`toString`方法（这些大部分类都有做到），重载`clone`方法（在棋盘的俄罗斯方块数据内进行拷贝构造时使用`clone`方法），坚持使用`Override`注解（重写父类的`equals`，`hashCode`和`toString`等方法），基本类型优于装箱类型等，优先使用`for-each`循环等，了解和使用类库（在随机生成俄罗斯方块类型时使用的是`Math.Random`类，而不是简单的使用`Math.radom()`函数），以及为了类的设计尽量将数据私有化，提供get和set方法给外部进行访问等，也用上了JavaFX的经典设计模式-MVC模式，由于很多值都使用的枚举变量，这也让后来的代码维护变得更加简单。
而且经过这个大作业报告的编写，也对自己编写的代码有了更加深入的了解，也有一些之前没有考虑到的东西在编写文档的时候考虑到了，然后进行修改优化等，所以感觉这一次的大作业的代码和文档收获也挺丰富的。原本还想完成这个的网络对战版本，但是由于时间有限，后续在暑假如果有时间可以继续进行完成。

Github链接：[https://github.com/MyLovePoppet/Tetris](https://github.com/MyLovePoppet/Tetris)

