package Esame_Prog2.Ascii_ART;
//OVERVIEW
/**Figures rappresenta l'interfaccia di tutte le figure che possono essere disegnate su un'istanza di Bitmap
 */
public interface Figures {
    /**
     * Effetti collaterali: potrebbe modificare Bitmap se le coordinate di Figures possono essere contenute al suo interno
     * Post-condizioni: modifica Bitmap andando ad accendere tutti i bit che sono condivisi sia dalla figura che dalla Bitmap
     */
    public void draw(Bitmap b);
}