package Esame_Prog2.Ascii_ART;

//OVERVIEW
/**
 * La classe bitmap rappresenta un'istanza di bitmap di 0 o 1, essa sarà una matrice di dimensione b * h, su di essa potranno venir disegnate le figure
 * facenti parte dell'interfaccia 'figures'.
 * 
 * E.g.: 	0	1	2	3	4
        0	1	0	0	0	0
        1	1	0	1	1	0
        2	1	0	0	0	0
        3	1	0	0	0	0 
 * Questa è una bitmap di dimensione 5 * 4!
 *
 * Essa è un'entità mutabile.
 */
public class Bitmap {
//CAMPI
    /**
     * bitmap rappresenterà la matrice di b * n bit necessari a rappresentare la nostra Bitmap
     */
    private boolean[][] bitmap;

    /**
     * b e h saranno i valori rispettivamente della base e della altezza della bitmap
     */
    private int b, h;

    /**
     * Post-condizioni: restituisce un'istanza di bitmap se b e h sono maggiori di 0, 
     *                  altrimenti solleva un'eccezione di tipo IllegalArgumentException
     * @param b la base della nostra bitmap
     * @param h l'altezza della nostra bitmap
     * @throws IllegalArgumentException se b e h sono minori o uguali a 0
     */
    public Bitmap(int b, int h) {
        if ((b < 1) || (h < 1)) throw new IllegalArgumentException();
        bitmap = new boolean[h][b];
        this.b = b;
        this.h = h;
    } 

    /**
     * Effetti collaterali: potrebbe modificare this se x e y sono compresi rispettivamente nell'intervallo tra 0 e this.b o 0 e this.h (non compresi)
     * Post-condizioni: se x è compreso nell'intervallo tra 0 e this.b && y è compreso nell'intervallo tra 0 e this.h (this.b e this.h sono limiti superiori, non sono compresi) allora modifica il valore del bit di coordinata (x, y), portandolo nello stato "acceso";
     *                  alternativamente restituisce un'eccezione di tipo IllegalArgumentException
     * @param x la coordinata x del bit che dovrà essere acceso
     * @param y la coordinata y del bit che dovrà essere acceso
     * @throws IllegalArgumentException se x e y sono minori di 0 o maggiori di this.b o this.h rispettivamente
     */
    public void turnOn(int x, int y){
        if (((x < 0) || (x >= this.b)) || ((y < 0) || (y >= this.h))) throw new IllegalArgumentException();
        bitmap[x][y] = true;
    }

    /**
     * Effetti collaterali: potrebbe modificare this se x e y sono compresi rispettivamente nell'intervallo tra 0 e this.b o 0 e this.h (non compresi)
     * Post-condizioni: se x è compreso nell'intervallo tra 0 e this.b && y è compreso nell'intervallo tra 0 e this.h (this.b e this.h sono limiti superiori, non sono compresi) allora modifica il valore del bit di coordinata (x, y), portandolo nello stato "spento";
     *                  alternativamente restituisce un'eccezione di tipo IllegalArgumentException
     * @param x la coordinata x del bit che dovrà essere spento
     * @param y la coordinata y del bit che dovrà essere spento
     * @throws IllegalArgumentException se x e y sono minori di 0 o maggiori di this.b o this.h rispettivamente
     */
    public void turnOff(int x, int y){
        if (((x < 0) || (x >= this.b)) || ((y < 0) || (y >= this.h))) throw new IllegalArgumentException();
        bitmap[x][y] = false;
    }

    public void turnOffAll(){
        for (int i = 0; i < b; i++) {
            for (int j = 0; j < h; j++) {
                turnOff(i, j);
            }
        }
    }

    public void bitmapInversion() {
        for (int i = 0; i < b; i++) {
            for (int j = 0; j < h; j++) {
                if (bitmap[i][j])
                    turnOff(i, j);
                else 
                    turnOn(i, j);
            }
        }
    }

    public boolean[][] patrizione(int b, int h, int i, int j){
        if ((i + b >= this.b) || (j + h >= this.h)) throw new IllegalArgumentException();
        boolean[][] partizione = new boolean[i][j];
        for (int I = 0; I < i; I++) {
            for (int J = 0; J < j; J++) {
                partizione[I][J] = bitmap[I + b][J + h];
            }
        }
        return partizione;
    }

    public boolean accettableCoord(int x, int y) {
        if (((x < 0) || (x >= this.b)) || ((y < 0) || (y >= this.h))) return false;
        return true;
    }

    /**
     * Effetti collaterali: potrebbe modificare System.out se bitmap è inizializzata
     * Post-condizioni: stampa a terminale il contenuto della bitmap
     */
    public void draw() {
        for (int i = 0; i < this.b; i++) {
            for (int j = 0; j < this.h; j++) {
                char pixel = bitmap[i][j] ? '*' : '.';
                System.out.print(pixel);
            }
            System.out.println();            
        }
    }
}