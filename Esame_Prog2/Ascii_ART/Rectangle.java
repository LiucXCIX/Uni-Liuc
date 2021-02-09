package Esame_Prog2.Ascii_ART;

import java.util.ArrayList;
import java.util.List;

public class Rectangle implements Figures {
    
    private final List<Segment> rectangle;

    private Rectangle(int height, int length, int row, int col){
        rectangle = new ArrayList<>();
        Segment s;
        s = Segment.horizontalSegment(length, row, col);
        rectangle.add(s);
        s = Segment.horizontalSegment(length, row + height - 1, col);
        rectangle.add(s);
        s = Segment.verticalSegment(height, row, col);
        rectangle.add(s);
        s = Segment.verticalSegment(height, row, col + length - 1);
        rectangle.add(s);
    }

    public static Rectangle rect(int height, int length, int row, int col){
        return new Rectangle(height, length, row, col);
    }

    @Override
    public void draw(Bitmap b) {
        for (Segment s : rectangle) {
            s.draw(b);
        }
    }

}