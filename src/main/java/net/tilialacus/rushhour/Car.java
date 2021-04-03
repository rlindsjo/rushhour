package net.tilialacus.rushhour;

public enum Car {
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

    enum Direction {
        HORIZONTAL, VERTICAL;
    };

    private final char identifier;
    private final int size;

    Car(char identifier) {
        this(identifier, 2);
    }
    Car(char identifier, int size) {
        this.identifier = identifier;
        this.size = size;
    }

    char identifier() {
        return identifier;
    }

    public int getSize() {
        return size;
    }

    public Move left(int steps) {
        return new Move(this, '\u2190', steps);
    }

    public Move right(int steps) {
        return new Move(this, '\u2192', steps);
    }

    public Move up(int steps) {
        return new Move(this, '\u2191', steps);
    }

    public Move down(int steps) {
        return new Move(this, '\u2193', steps);
    }

    public static class Move {
        private final Car car;
        private final char direction;
        private final int steps;

        public static Move left(Car car, int steps) {
            return new Move(car, '\u2190', steps);
        }

        public static Move right(Car car, int steps) {
            return new Move(car, '\u2192', steps);
        }

        public static Move up(Car car, int steps) {
            return new Move(car, '\u2191', steps);
        }

        public static Move down(Car car, int steps) {
            return new Move(car, '\u2193', steps);
        }

        private Move(Car car, char direction, int steps) {
            this.car = car;
            this.direction = direction;
            this.steps = steps;
        }

        @Override
        public String toString() {
            return new StringBuilder().append(car.identifier()).append(direction).append(steps).toString();
        }
    }
}
