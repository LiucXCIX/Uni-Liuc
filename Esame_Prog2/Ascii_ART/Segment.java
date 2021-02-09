package Esame_Prog2.Ascii_ART;

import java.util.ArrayList;
import java.util.List;

public final class Segment implements Figures {

    private final List<Coordinate> segment;

    private Segment(int length, int row, int col, boolean isVertical){
        segment = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            Coordinate punto;
            if (isVertical) {
                punto = Coordinate.coord(row + i, col);
            } else {
                punto = Coordinate.coord(row, col + i);
            }
            segment.add(punto);
        }
    }

    public static Segment verticalSegment(int length, int row, int col){
        return new Segment(length, row, col, true);
    }

    public static Segment horizontalSegment(int length, int row, int col){
        return new Segment(length, row, col, false);
    }

    @Override
    public void draw(Bitmap b) {
        for (Coordinate punto : segment) {
            if (b.accettableCoord(punto.xCoord(), punto.yCoord())) {
                b.turnOn(punto.xCoord(), punto.yCoord());
            }
        }
    }
    
}