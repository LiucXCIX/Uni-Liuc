package Esame_Prog2.Ascii_ART;

import java.util.ArrayList;
import java.util.List;

//OVERVIEW
/**
 * La classe Rectangle implementa un'interfaccia di tipo Figures, pertanto potrà essere disegnato su una Bitmap. 
 * Il rettangolo viene rappresentato tramite quattro segmenti (due orizzontali e due verticali) implementati dal tipo Segment, per inizializzarlo sono necessari quattro valori (length, height, row, column)
 * Ogni istanza di questo tipo è immutabile.
 */
public class Rectangle implements Figures {
    
    /**
     * Il rettangolo viene rappresentato tramite una collezione di quattro segmenti
     */
    private final List<Segment> rectangle;

    /**
     * Post-condizioni: inizializza un'istanza di tipo Rectangle
     * @param height altezza del rettangolo
     * @param length lunghezza della base
     * @param row la riga dove si troverà il primo punto
     * @param col la colonna dove si troverà il primo punto
     */
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

    /**
     * Post-condizioni: inizializza e restituisce un'istanza di tipo Rectangle
     * @param height altezza del rettangolo
     * @param length lunghezza della base
     * @param row la riga dove si troverà il primo punto
     * @param col la colonna dove si troverà il primo punto
     */
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