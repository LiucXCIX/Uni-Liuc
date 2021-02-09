package Esame_Prog2.Ascii_ART;

public final class Coordinate {
    private int x, y;

    private Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Coordinate coord(int x, int y) {
        return new Coordinate(x, y);
    }

    public int xCoord() {
        return this.x;
    }

    public int yCoord() {
        return this.y;
    }
}