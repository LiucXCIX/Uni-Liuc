package Esame_Prog2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/** OVERVIEW:
 *      Implementa un'istanza di Ricevitore, un'entità che ricorderà tutti i pacchetti ricevuti, ricomporrà il messaggio e, qualora riuscisse a completarlo, lo stamperà a terminale. 
 *      Le istanze di Ricevitore sono mutabili.
 */
public class Ricevitore {

// CAMPI
    /**
     * ricevitore associerà un'istanza di pacchetti al loro specifico id al fine di permettere la decriptazione del messaggio completo
     * messaggi conterrà i messaggi ottenuti componendo assieme tutti i testi presenti nei pacchetti
     */
    private Map<Integer, List<Pacchetto>> ricevitore;
    private Map<Integer, String> messaggi;

/* AF FUN: AF(ricevitore) = {id_1 -> {*id_1*    *tot*    *prog*    *t1*, *id_1*    *tot*    *prog + 1*    *t2*, ...,  *id_1*    *tot*    *prog + tot - 1*    *t_fin*}, id_2 -> ..., ..., id_fin -> ...};
        AF(messaggi) = {id_1 -> *t1* + *t2* + *t3* + ... + *t_fin*, id_2 -> *txt* + ..., ..., id_k -> ...}  
 * REP INV: Un elemento dovrà essere inserito in messaggi solo se la ricezione dei pacchetti è terminata, quindi dovrò aver ottenuto tutti i pacchetti necessari a ricomporre il messaggio. Inoltre in ricevitore dovranno essere associati gli id con le specifiche istanze di pacchetto con lo stesso id
 * AF INV: Il messaggio deve essere completo e i messaggi devono avere lo stesso id 
 */

// COSTRUTTORE
    /**
     * Post-condizioni = inizializza e restituisce una nuova istanza di this.
     */
    public Ricevitore(){
        ricevitore = new HashMap<>();
        messaggi = new HashMap<>();
    }
    
//METODI DI ISTANZA
    /**
     * Pre-condizioni: s deve essere una string nel formato: "(id)\t(totale)\t(progressivo)\t(testo)" 
     *                 e.g.: "5181  t6  0   Io ne ho viste cose che voi umani non potreste immaginarvi:" 
     * Effetti collaterali: modifica this e potrebbe modificare System.out qualora il messaggio fosse completo
     * Post-condizioni: inserisce il pacchetto codificato dalla stringa s nella corrispettiva collezione di pacchetti con lo stesso id e
     *                  qualora fossero stati ricevuti tutti i messaggi dello specifico pacchetto, allora essi verranno stampati a terminale
     * @param s string nel formato: "(id)\t(totale)\t(progressivo)\t(testo)" 
     *          e.g.: "5181  t6  0   Io ne ho viste cose che voi umani non potreste immaginarvi:" 
     */
    public void aggiungiPacchetto(String s) {
        Pacchetto p = Pacchetto.parsePacchetto(s);
        List<Pacchetto> pacchetti;
        if (ricevitore.containsKey(p.id())) {
            pacchetti = ricevitore.get(p.id());
            pacchetti.add(p);
            pacchetti.sort(null);
        } else {
            pacchetti = new ArrayList<>();
            pacchetti.add(p);
            ricevitore.put(p.id(), pacchetti);
        }
        if (pacchetti.size() >= p.totale()) { // vorrà dire che il messaggio sarà completo
            ricomposizioneMessaggio(pacchetti, p.id());
        }
        ricevitore.put(p.id(), pacchetti);
        assert repOk();
    }

    /**
     * Effetti collaterali: modifica this
     * Post-condizioni: ottiene il messaggio completo codificato dai pacchetti ricevuti e lo inserisce in una collezione
     * @param pacchetti la lista di pacchetti sulla quale iterare per ricomporre il messaggio completo
     * @param id l'id dei pacchetti contenenti lo specifico messaggio
     */
    public void ricomposizioneMessaggio(List<Pacchetto> pacchetti, int id) {
        StringBuilder s = new StringBuilder();
        for (Pacchetto p : pacchetti) {
            s.append(p.testo() + "\n");
        }
        System.out.println("\n" + s.toString());
        messaggi.put(id, s.toString());
        assert repOk();
    }

    /**
     * Post-condizioni: restituisce il messaggio specifico per l'id del pacchetto, se esso non fosse ancora presente allora verrà sollevata un'eccezione di tipo NoSuchElementException
     * @param id è l'id dei pacchetti che contenevano il messaggio
     * @return il messaggio completo in formato String
     * @throws NoSuchElementException se il messaggio completo non è ancora presente in this
     */
    public String messaggio (int id) {
        String s = messaggi.get(id);
        if (s == null) throw new NoSuchElementException("Non è stato possibile trovare lo specifico elemento");
        return s;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Ricevitore:");
        Set<Integer> key = ricevitore.keySet();
        for (int k : key) {
            s.append(" " + k + " " + ricevitore.get(k).toString());
        }
        s.append("\n" + "Messaggi:");
        for (int k : key) {
            s.append(" " + k + " " +  messaggi.get(k).toString());
        }
        return s.toString();
    } 

    @Override
    public boolean equals (Object o) {
        if (o == this) return true;
        if (!(o instanceof Ricevitore)) return false;
        Ricevitore ob = (Ricevitore) o;
        return ((this.ricevitore.equals(ob.ricevitore)) && (this.messaggi.equals(ob.messaggi)));
    }

    private boolean repOk() {
        Set<Integer> key = messaggi.keySet();
        for (int k : key) {
            List<Pacchetto> p = ricevitore.get(k);
            if (p.size() < p.get(0).totale()) return false;
        }
        key = ricevitore.keySet();
        for (int k : key) {
            List<Pacchetto> p = ricevitore.get(k);
            for (Pacchetto pa : p) {
                if (k != pa.id()) return false;
            }
        }
        return true;
    }
}