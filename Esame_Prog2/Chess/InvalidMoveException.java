package Esame_Prog2.Chess;
/**OVERVIEW:
 * InvalidMoveException implementa un'eccezione checked che verrà lanciata qualora la mossa del utente non fosse considerata valida
 */
public class InvalidMoveException extends Exception {

    /**
     * Numero seriale dell'eccezione
     */
    private static final long serialVersionUID = 4199053906313235404L;

    public InvalidMoveException() {
        super();
    }

    public InvalidMoveException(String s) {
        super(s);
    }
}
