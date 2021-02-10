package Esame_Prog2.Ascii_ART;

import java.util.ArrayList;
import java.util.List;

//OVERVIEW
/**
 * La classe Segmento è un implementazione della interfaccia Figures, pertanto potrà essere stampata su una bitmap. 
 * Il segmento potrà essere orizzontale o verticale e viene definito attraverso una tripla di valori (length, row, col).
 * Questa istanza è immutabile
 */
public final class Segment implements Figures {

    /**
     * Il segmento è rappresentato come un'insieme di coordinate inserite nella collezione segment
     */
    private final List<Coordinate> segment;

    /**
     * Post-condizioni: inizializza un'istanza di segment (verticale o orizzontale)
     * @param length
     * @param row
     * @param col
     * @param isVertical
     */
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

    /**
     * Post-condizioni: inizializza e restituisce un'istanza di tipo segmento verticale
     * @param length lunghezza
     * @param row la riga di partenza
     * @param col la colonna di partenza
     * @return un'istanza di tipo segmento verticale
     */
    public static Segment verticalSegment(int length, int row, int col){
        return new Segment(length, row, col, true);
    }

    /**
     * Post-condizioni: inizializza e restituisce un'istanza di tipo segmento orizzontale
     * @param length lunghezza
     * @param row la riga di partenza
     * @param col la colonna di partenza
     * @return un'istanza di tipo segmento orizzontale
     */
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