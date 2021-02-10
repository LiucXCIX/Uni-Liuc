package Esame_Prog2.Ascii_ART;

//OVERVIEW
/**
 * La classe Coordinate permette la rappresentazione di un ounto bidimensionale descritto tramite una componente orizzontale (x) e una verticale (y)
 * Ogni istanza di quessto tipo è immutabile. 
 */
public final class Coordinate {
    /**
     * x rappresenta le coordinate sulle ascisse, y sulle ordinate
     */
    private int x, y;

    /*
    *   REP INV: nessuno, ogni valore intero è accettato
    *   ABS FUN: AF(x, y) -> (x, y)
    *   ABS INV: nessuno, ogni ascissa o ordinata è accettata sulla coordinata
    */

    /**
     * Post-condizioni: inizializza un'istanza di tipo coordinata
     * @param x componente orizzontale
     * @param y componente verticale
     */
    private Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Post-condizioni: inizializza un'istanza di tipo coordinata e la restituisce al chiamante
     * @param x componente orizzontale
     * @param y componente verticale
     * @return un'istanza di tipo coordinata
     */
    public static Coordinate coord(int x, int y) {
        return new Coordinate(x, y);
    }

    /**
     * Post-condizioni: restituisce la componente orizzontale del punto.
     * @return coordinata sulle ascisse
     */
    public int xCoord() {
        return this.x;
    }

    /**
     * Post-condizioni: restituisce la componente verticale del punto.
     * @return coordinata sulle ordinate
     */
    public int yCoord() {
        return this.y;
    }
}