package Model;


public enum ConstantValues {
    main_square_vertical_num(20),
    main_square_horizon_num(10),
    next_square_vertical_num(4),
    next_square_horizon_num(4),
    square_length(25);
    public final int value;

    ConstantValues(int value) {
        this.value = value;
    }
}

