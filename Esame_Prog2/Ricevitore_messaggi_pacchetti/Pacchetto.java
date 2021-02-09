package Esame_Prog2.Ricevitore_messaggi_pacchetti;
/**OVERVIEW:
 * La classe Pacchetto implementa un'entità di tipo pacchetto definita da una quadrupla = {id (id del messaggio), totale (quanti pacchetti servono per completare il messaggio), progressivo (il numero progressivo dello specifico pacchetto all'interno del messaggio) e testo (contenente il testo del pacchetto)}
 * Essa sarà un'istanza immutabile. 
 */
public final class Pacchetto implements Comparable<Pacchetto> {

// CAMPI
    /**
     * id rappresenta il numero identificativo del messaggio,
     * totale il numero totale di pacchetti del messaggio,
     * progressivo il numero progressivo del pacchetto all'interno del messaggio,
     * testo il testo del pacchetto.
     */
    private final int id, totale, progressivo;
    private final String testo;

    /*
     * Abstract function: AF(id, totale, progressivo, testo) -> "id \t totale \t progressivo \t testo"
     * Representation Invariant: testo != "\t"
     */

// COSTRUTTORE
    /**
     * Post-condizioni: restituisce un'istanza di Pacchetto, se testo = null solleva un'eccezione di tipo NullPointerException e se è equals a "\t" solleva un ulteriore eccezione, questa volta di tipo IllegalArgumentException
     * @param id numero identificativo del messaggio
     * @param totale numero totale di pacchetti del messaggio
     * @param progressivo numero progressivo del pacchetto all'interno del messaggio
     * @param testo testo del pacchetto
     * @throws NullPointerException se testo è un riferimento a null
     * @throws IllegalArgumentException se testo è equals a "\t"
     */
    private Pacchetto(int id, int totale, int progressivo, String testo) {
        if (testo == null) throw new NullPointerException("Il testo non deve essere un riferimento a null");
        if (testo.equals("\t")) throw new IllegalArgumentException("Il testo non deve esseere '\t'");
        this.id = id;
        this.totale = totale;
        this.progressivo = progressivo;
        this.testo = testo;
        assert repOk();
    }

// METODI    
    /**
     * Pre-condizioni: pacchetto deve essere una string nel formato: "(id)\t(totale)\t(progressivo)\t(testo)" 
     *                 e.g.: "5181  t6  0   Io ne ho viste cose che voi umani non potreste immaginarvi:" 
     * Post-condizioni: restituisce una nuova istanza di tipo Pacchetto usando le informazioni contenute nella stringa passata come argomento
     * @param pacchetto deve essere una string nel formato: "(id)\t(totale)\t(progressivo)\t(testo)" 
     * @return un'istanza di Pacchetto inizializzata tramite la String pacchetto
     */
    public static Pacchetto parsePacchetto(String pacchetto) {
        int i = 0, idP = 0, totP = 0, progP = 0;
        String txt = "";
        for (String s : pacchetto.split("\t")) {
            switch (i) {
                case 0: idP = Integer.parseInt(s);
                        break;
                case 1: totP = Integer.parseInt(s);
                        break;
                case 2: progP = Integer.parseInt(s);
                        break;
                case 3: txt = s;
                        break;
            }
            i++;
        }
        return new Pacchetto(idP, totP, progP, txt);
    }

    /**
     * Post-condizioni: restituisce il valore dell'attributo id di this
     * @return valore dell'attributo id
     */
    public int id(){
        return id;
    }

    /**
     * Post-condizioni: restituisce il valore dell'attributo totale di this
     * @return valore dell'attributo totale
     */
    public int totale(){
        return totale;
    }

    /**
     * Post-condizioni: restituisce il valore dell'attributo progressivo di this
     * @return valore dell'attributo progressivo
     */
    public int progressivo(){
        return progressivo;
    }

    /**
     * Post-condizioni: restituisce il valore dell'attributo testo di this
     * @return valore dell'attributo testo
     */
    public String testo(){
        return testo;
    }

    @Override
    public String toString(){
        return id + "\t" + totale + "\t" + progressivo + "\t" + testo;
    }

    @Override
    public boolean equals (Object o) {
        if (o == this) return true;
        if (!(o instanceof Pacchetto)) return false;
        Pacchetto obj = (Pacchetto) o;
        return ((obj.id == this.id) && (obj.totale == this.totale) && (obj.progressivo == this.progressivo) && (testo.equals(obj.testo())));
    }

    @Override
    public int hashCode() {
        return 31 * (31 * ((31 * Integer.hashCode(id)) + Integer.hashCode(totale)) + Integer.hashCode(progressivo)) + testo.hashCode();
    }

    /**
     * Post-condizioni: restituisce true se l'invariante di rappresentazione è valido, false altrimenti
     */
    private boolean repOk(){
        return testo.equals("\t");
    }

    @Override
    public int compareTo(Pacchetto o) {
        if (this.id > o.id) {
            return +1;
        } else if (this.id < o.id) {
            return -1;
        } else {
            // se l'id sarà lo stesso allora gli unici due campi che possono differenziare le due istanze saranno progressivo e testo perché id == o.id -> totale == o.totale (a meno di errori nel input)
            if (this.progressivo < o.progressivo) return -1; 
            if (this.progressivo > o.progressivo) return +1;
            return this.testo.compareTo(o.testo);
        }
    }
}