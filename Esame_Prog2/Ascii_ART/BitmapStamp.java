package Esame_Prog2.Ascii_ART;

//OVERVIEW
/**
 * Una classe di tipo BitmapStamp rappresenta il timbro di una specifica istanza di Bitmap. 
 * Partendo da una partizione di quest'ultima, potremo stamparla in un punto differente di un'istanza di tipo Bitmap (può essere la stessa come una differente!)
 */
public class BitmapStamp {

    /**
     * b e h rappresentano la grandezza del timbro
     */
    private int b, h;
    /**
     * bitmapStamp rappresenta la struttura di dati ove verrà salvata la partizione di Bitmap che verrà usata come timbro!
     */
    private boolean[][] bitmapStamp;

    /* REP INV: b e h devono essere rispettivamente la base e l'altezza della matrice bitmap 
    *  ABS FUN: AF(bitmap, b, h) -> *...** (dimensione b)
                                    .*.*..
                                    ***...
                                   (dimensione h)
    *  ABS INV: ogni valore true deve essere un '*', mentre ogni false un '.'
    */

    /**
     * Post-condizioni: inizializza e restituisce un'istanza di tipo Bitmap se b e h sono >= 0, altrimenti solleva un'eccezione di tipo IllegalArgumentException
     * @param stamp il timbro della bitmap
     * @param b lunghezza della base del timbro
     * @param h lunghezza della altezza del timbro
     */
    public BitmapStamp(boolean[][] stamp, int b, int h) {
        this.b = b;
        this.h = h;
        bitmapStamp = new boolean[b][h];
        for (int i = 0; i < b; i++) {
            for (int j = 0; j < h; j++) {
                bitmapStamp[i][j] = stamp[i][j];
            }
        }
    }

    /**
     * Post-condizioni: inizializza e restituisce un'istanza vuota di tipo Bitmap
     */
    public BitmapStamp(){
        this.b = 0;
        this.h = 0;
        bitmapStamp = new boolean[1][1];
    }

    /**
     * Effetti collaterali: potrebbe modificare Bitmap se i punti possono venire inseriti in essa
     * Post-condizioni: modifica Bitmap andando ad accendere tutti i bit presenti sul timbro partendo dalla posizione indicata da x e y, 
     */
    public void draw(Bitmap bit, int x, int y) {
        if ((this.b == 0) && (this.h == 0)) return;
        for (int i = 0; i < b; i++) {
            for (int j = 0; j < h; j++) {
                if ((bit.accettableCoord(x + i, y + j)) && (bitmapStamp[i][j])) {
                    bit.turnOn(x + i, y + j);
                }
            }
        }
    }
}
