package Esame_Prog2.Chess;

import java.util.List;
import java.util.Set;

/**
 * OVERVIEW: L'interfaccia Pieces elenca i comportamenti possibili da un pezzo
 * degli scacchi.
 */
public interface Pieces {

    /*
    *   Rep inv: la posizione del pezzo deve essere valida
    *   Abs function: rappresentazione del pezzo, se isAlive sarà considerato un pezzo valido con i suoi comportamenti, altrimenti no
    *   Abs inv: la posizione del pezzo deve essere valida
    *            se isAlive = false esso non si potrà muovere
    */

    /**
    * Effetti collaterali: potrebbe modificare this se la mossa è considerata valida!
    * Post-condizioni: se move rappresenta una mossa valida, allora il pezzo verrà mosso in quella specifica casella, altrimenti verrà
    *                  sollevata un'eccezione controllata di tipo InvalidMoveException. [potrebbe restituire un valore booleano per capire se la mossa abbia portato ad un check, mentre per il re vorrebbe dire che è un checkmate]
    * @param AlivePieces una lista di pezzi ancora presenti sulla scacchiera
    * @param move la casella dove verrà spostato il singolo pezzo
    * @return un valore booleano che specifica se il pezzo sta minacciando il re avversario
    * @throws InvalidMoveException se la mossa non è considerata valida
    */
    public boolean move(ChessCoordinate move, List<Pieces> AlivePices) throws InvalidMoveException;

    /**
     * Effetti collateerali: potrebbe modificare this se il pezzo è ancora "in vita"
     * Post-condizioni: modifica this se il pezzo è "in vita" rendendolo così "mangiato"
     */
    public void getEaten();

    /**
     * Post-condizioni: controlla la validità della mossa e restituirà un valore affermativo se valida, o negativo alteernativamente
     * @param move la mossa che si ha intenzione di fare
     * @param AlivePieces una lista di pezzi ancora presenti sulla scacchiera
     * @return un valore booleano che ci permette di capire se la mossa è possibile o meno
     */
    public boolean possibleMoves(ChessCoordinate move, List<Pieces> AlivePieces);
    /**
     * Post-condizioni: restituisce una lista di mosse possibili per il singolo pezzo
     * @param AlivePieces una lista di pezzi ancora presenti sulla scacchiera
     * @return una lista di mosse possibili per il pezzo
     */
    public Set<ChessCoordinate> listMove(List<Pieces> AlivePieces);
    public boolean isCheck(List<Pieces> AlivePieces);
    public boolean isBlack();
    public boolean isPinned();
    public int coordX();
    public int coordY();
    public String image();
}
