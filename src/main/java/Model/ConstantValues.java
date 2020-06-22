package Model;


public enum ConstantValues {
    //主界面高的方块的数目
    main_square_vertical_num(20),
    //主界面宽的方块的数目
    main_square_horizon_num(10),
    //下一个方块界面的高的方块的数目
    next_square_vertical_num(4),
    //下一个方块界面的宽的方块的数目
    next_square_horizon_num(4),
    //默认方块的大小
    square_length(25);
    public final int value;

    ConstantValues(int value) {
        this.value = value;
    }
}

