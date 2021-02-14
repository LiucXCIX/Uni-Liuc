package Esame_Prog2.Chess;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Knight implements Pieces {

    private ChessCoordinate ChessSquare;
    private final boolean isBlack;
    private boolean isPinned;
    private boolean isAlive;
    private String image;

    /*
     * Rep inv: la posizione del pezzo deve essere valida Abs function:
     * rappresentazione del pezzo, se isAlive sarà considerato un pezzo valido con i
     * suoi comportamenti, altrimenti no Abs inv: la posizione del pezzo deve essere
     * valida se isAlive = false esso non si potrà muovere
     */

    /**
     * Post-condizioni: inizializza e restituisce un'istanza di Knight
     * 
     * @param isBlack definisce se il cavallo è nero o meno
     */
    public Knight(boolean isBlack, int j) {
        if (isBlack) {
            ChessSquare = ChessCoordinate.coord(7, j);
            this.image = "\u265E";
        } else {
            ChessSquare = ChessCoordinate.coord(0, j);
            this.image = "\u2658";
        }
        this.isBlack = isBlack;
        this.isAlive = true;
        this.isPinned = false;
    }

    /**
     * Post-condizioni: inizializza e restituisce un'istanza di Knight (viene
     * principalmente usata per le promozione dei pedoni)
     * 
     * @param isBlack definisce se il cavallo è nero o meno
     * @param square  la posizione dove verrà creato il cavallo
     */
    public Knight(boolean isBlack, ChessCoordinate square) {
        this.ChessSquare = square;
        this.isBlack = isBlack;
        this.isAlive = true;
        this.isPinned = false;
    }

    @Override
    public boolean move(ChessCoordinate move, List<Pieces> AlivePieces) throws InvalidMoveException {
        if (isPinned)
            throw new InvalidMoveException("The piece is pinned");
        if (!isAlive)
            throw new InvalidMoveException("The piece was eaten");
        if (!possibleMoves(move, AlivePieces))
            throw new InvalidMoveException("This move is not valid");
        this.ChessSquare = move;
        for (Pieces p : AlivePieces) {
            if ((p.coordX() == this.coordX()) && (p.coordY() == this.coordY())) {
                p.getEaten(); // ATTENZIONE, se i controlli di check non funzionano potresti perfino mangiare
                              // un Re, non male!
                AlivePieces.remove(p);
            }
        }
        return isCheck(AlivePieces);
    }

    @Override
    public void getEaten() {
        isAlive = false;
    }

    @Override
    public boolean possibleMoves(ChessCoordinate move, List<Pieces> AlivePieces) {
        if ((move.xCoord() >= 8) || (move.yCoord() >= 8) || (move.yCoord() < 0) || (move.xCoord() < 0))
            return false;
        for (Pieces p : AlivePieces) {
            if (((p.coordX() == move.xCoord()) && (p.coordY() == move.yCoord()))
                    && ((p.isBlack() == this.isBlack()))) {
                return false;
            }
        }
        if ((((move.xCoord() == ChessSquare.xCoord() + 2) && (move.yCoord() == ChessSquare.yCoord() + 1)))
                || (((move.xCoord() == ChessSquare.xCoord() + 2) && (move.yCoord() == ChessSquare.yCoord() - 1)))
                || (((move.xCoord() == ChessSquare.xCoord() - 2) && (move.yCoord() == ChessSquare.yCoord() + 1)))
                || (((move.xCoord() == ChessSquare.xCoord() - 2) && (move.yCoord() == ChessSquare.yCoord() - 1)))
                || (((move.xCoord() == ChessSquare.xCoord() + 1) && (move.yCoord() == ChessSquare.yCoord() + 2)))
                || (((move.xCoord() == ChessSquare.xCoord() + 1) && (move.yCoord() == ChessSquare.yCoord() - 2)))
                || (((move.xCoord() == ChessSquare.xCoord() - 1) && (move.yCoord() == ChessSquare.yCoord() + 2)))
                || (((move.xCoord() == ChessSquare.xCoord() - 1) && (move.yCoord() == ChessSquare.yCoord() - 2))))
            return true;
        return false;
    }

    @Override
    public int coordX() {
        return this.ChessSquare.xCoord();
    }

    @Override
    public int coordY() {
        return this.ChessSquare.yCoord();
    }

    @Override
    public boolean isBlack() {
        return this.isBlack;
    }

    @Override
    public String image() {
        return this.image;
    }

    //DA CONTROLLARE IL CAVALLO SALTA FUORI DALLA SCACCHIERA!
    @Override
    public Set<ChessCoordinate> listMove(List<Pieces> AlivePieces) {
        Set<ChessCoordinate> moves = new HashSet<>();
        moves.add(ChessCoordinate.coord(ChessSquare.xCoord() + 2,  ChessSquare.yCoord() + 1));
        moves.add(ChessCoordinate.coord(ChessSquare.xCoord() + 2,  ChessSquare.yCoord() - 1));
        moves.add(ChessCoordinate.coord(ChessSquare.xCoord() - 2,  ChessSquare.yCoord() + 1));
        moves.add(ChessCoordinate.coord(ChessSquare.xCoord() - 2,  ChessSquare.yCoord() - 1));
        moves.add(ChessCoordinate.coord(ChessSquare.xCoord() + 1,  ChessSquare.yCoord() + 2));
        moves.add(ChessCoordinate.coord(ChessSquare.xCoord() + 1,  ChessSquare.yCoord() - 2));
        moves.add(ChessCoordinate.coord(ChessSquare.xCoord() - 1,  ChessSquare.yCoord() + 2));
        moves.add(ChessCoordinate.coord(ChessSquare.xCoord() - 1,  ChessSquare.yCoord() - 2));
        return moves;
    }

    @Override
    public boolean isCheck(List<Pieces> AlivePieces) {
        Set<ChessCoordinate> futureMoves = this.listMove(AlivePieces);
        for (Pieces p : AlivePieces){
            if ((p instanceof King) && (p.isBlack() == !(this.isBlack()))) {
                if (futureMoves.contains(ChessCoordinate.coord(p.coordX(), p.coordY()))){
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isPinned() {
        return isPinned;
    }
}