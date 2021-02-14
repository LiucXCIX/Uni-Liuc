package Esame_Prog2.Chess;

//OVERVIEW
/**
 * La classe ChessCoordinate permette la rappresentazione di un punto bidimensionale descritto tramite una componente orizzontale (x) e una verticale (y)
 * Ogni istanza di quessto tipo è immutabile. 
 */
public final class ChessCoordinate {
    /**
     * x rappresenta le coordinate sulle ascisse, y sulle ordinate
     */
    private int x, y;
    private boolean isBlack;

    /*
    *   REP INV: nessuno, ogni valore intero >= 0 è accettato
    *   ABS FUN: AF(x, y) -> (x, y)
    *   ABS INV: il punto deve essere sul primo quadrante
    */

    /**
     * Post-condizioni: inizializza un'istanza di tipo coordinata
     * @param x componente orizzontale
     * @param y componente verticale
     * @throws IllegalArgumentException
     */
    private ChessCoordinate(int x, int y) {
        if (x < 0) throw new IllegalArgumentException("x can only assume positive values");
        if (y < 0) throw new IllegalArgumentException("y can only assume positive values");
        if (x >= 8) throw new IllegalArgumentException("x it's outside the Chess board!");
        if (y >= 8) throw new IllegalArgumentException("y it's outside the Chess board!");
        this.x = x;
        this.y = y;
        this.isBlack = !((x + y) % 2 == 0);
    }

    /**
     * Post-condizioni: inizializza un'istanza di tipo coordinata e la restituisce al chiamante
     * @param x componente orizzontale
     * @param y componente verticale
     * @return un'istanza di tipo coordinata
     */
    public static ChessCoordinate coord(int x, int y) {
        return new ChessCoordinate(x, y);
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

    /**
     * Post-condizioni: restituisce il colore della casella.
     * @return se nero true, se bianco false
     */
    public boolean isBlack() {
        return this.isBlack;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (!(o instanceof ChessCoordinate)) return false;
        ChessCoordinate obj = (ChessCoordinate) o;
        return ((obj.x == this.x) && (obj.y == this.y));
    }
}