package net.tilialacus.rushhour;

public enum Cars {
    GRAY('G'),
    BROWN('J'),
    LIGHT_BLUE('C'),
    LIGHT_GREEN('A'),
    YELLOW('I'),
    RED('X'),
    PINK('D'),
    PURPLE('E'),
    OLIVE('K'),
    BEIGE('H'),
    ORANGE('B'),
    GREEN('F'),
    LAVENDEL('P', 3),
    BLUE('Q', 3),
    DARK_YELLOW('O', 3),
    CYAN('R', 3);

    private final char identifier;
    private final int size;

    Cars(char identifier) {
        this(identifier, 2);
    }
    Cars(char identifier, int size) {
        this.identifier = identifier;
        this.size = size;
    }

    public static Cars resolve(char c) {
        for (Cars car : Cars.values()) {
            if (car.identifier == c) {
                return car;
            }
        }
        throw new IllegalArgumentException("No car " +  c);
    }

    char identifier() {
        return identifier;
    }

    public int getSize() {
        return size;
    }
}
