package net.tilialacus.rushhour;

public enum Cars {
    GRAY,
    BROWN,
    LIGHT_BLE,
    LIGHT_GREEN,
    YELLOW,
    RED,
    PINK,
    PURPLE,
    OLIVE,
    BEIGE,
    ORANGE,
    GREEN,
    LAVENDEL(3),
    BLUE(3),
    DARK_YELLOW(3),
    AZURE(3);

    private final int size;

    Cars() {
        this(2);
    }
    Cars(int size) {
        this.size = size;
    }

    char identifier() {
        return (char) ('A' + ordinal());
    }

    public int getSize() {
        return size;
    }
}
