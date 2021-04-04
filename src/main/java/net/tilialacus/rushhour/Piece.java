package net.tilialacus.rushhour;

import static net.tilialacus.rushhour.Piece.Type.CAR;

public enum Piece {
    GRAY('G', CAR),
    BROWN('J', CAR),
    LIGHT_BLUE('C', CAR),
    LIGHT_GREEN('A', CAR),
    YELLOW('I', CAR),
    RED('X', CAR),
    PINK('D', CAR),
    PURPLE('E', CAR),
    OLIVE('K', CAR),
    BEIGE('H', CAR),
    ORANGE('B', CAR),
    GREEN('F', CAR),
    LAVENDEL('P', Type.TRUCK),
    BLUE('Q', Type.TRUCK),
    DARK_YELLOW('O', Type.TRUCK),
    CYAN('R', Type.TRUCK);

    enum Direction {
        HORIZONTAL, VERTICAL;
    };

    private final char identifier;
    private final int size;

    Piece(char identifier, Type type) {
        this.identifier = identifier;
        this.size = type == CAR ? 2 : 3;
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

    public enum Type {CAR, TRUCK}

    public static class Move {
        private final Piece piece;
        private final char direction;
        private final int steps;

        private Move(Piece piece, char direction, int steps) {
            this.piece = piece;
            this.direction = direction;
            this.steps = steps;
        }

        @Override
        public String toString() {
            return new StringBuilder().append(piece.identifier()).append(direction).append(steps).toString();
        }
    }
}
