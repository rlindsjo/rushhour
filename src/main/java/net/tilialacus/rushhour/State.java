package net.tilialacus.rushhour;

public class State {

    private static final int SIZE = 6;
    private char[] data = new char[SIZE * SIZE];

    public void set(int x, int y, char type) {
        data[x+y*SIZE] = type;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                char c = data[x + y * SIZE];
                if (c >= 'A' && c <= 'Z') {
                    b.append(c);
                } else {
                    b.append(' ');
                }
            }
            b.append('\n');
        }
        return b.toString();
    }
}
