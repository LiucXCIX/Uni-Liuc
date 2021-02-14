package Esame_Prog2.Chess;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Tower implements Pieces {
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
     * Post-condizioni: inizializza e restituisce un'istanza di Tower
     * 
     * @param isBlack definisce se la torre è nera o meno
     */
    public Tower(boolean isBlack, int j) {
        if (isBlack) {
            ChessSquare = ChessCoordinate.coord(7, j);
            this.image = "\u265C";
        } else {
            ChessSquare = ChessCoordinate.coord(0, j);
            this.image = "\u2656";
        }
        this.isBlack = isBlack;
        this.isAlive = true;
        this.isPinned = false;
    }

    /**
     * Post-condizioni: inizializza e restituisce un'istanza di Tower (viene
     * principalmente usata per le promozione dei pedoni)
     * 
     * @param isBlack definisce se la torre è nera o meno
     * @param square  la posizione dove verrà creata la torre
     */
    public Tower(boolean isBlack, ChessCoordinate square) {
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
        if ((move.xCoord() >= 8) || (move.yCoord() >= 8) || (move.yCoord() < 0) || (move.xCoord() < 0)) {
            return false;
        }
        for (Pieces p : AlivePieces) {
            if (((p.coordX() == move.xCoord()) && (p.coordY() == move.yCoord()))
                    && ((p.isBlack() == this.isBlack()))) {
                return false;
            }
        }
        for (int i = -8; i < 8; i++) {
            if (((move.xCoord() == ChessSquare.xCoord() + i) && (move.yCoord() == ChessSquare.yCoord())))
                return true;
            if (((move.xCoord() == ChessSquare.xCoord()) && (move.yCoord() == ChessSquare.yCoord() + i)))
                return true;
        }
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

    private void listMoveUpHelper(Set<ChessCoordinate> enemyPieces, List<Pieces> AlivePieces, Set<ChessCoordinate> moves) {
        for (int i = 0; i < 8; i++){
            if (ChessSquare.yCoord() + i >= 8) break;
            ChessCoordinate move = ChessCoordinate.coord(ChessSquare.xCoord(), ChessSquare.yCoord() + i);
            moves.add(move);
            if (enemyPieces.contains(move)) {
                for (Pieces p : AlivePieces) {
                    if ((p.coordX() == move.xCoord()) && (p.coordY() == move.yCoord()) && (this.isBlack() == p.isBlack())) {
                        moves.remove(move);
                    }
                }
                break;
            }
        }
    }

    private void listMoveDownHelper(Set<ChessCoordinate> enemyPieces, List<Pieces> AlivePieces, Set<ChessCoordinate> moves) {
        for (int i = 0; i < 8; i++){
            if (ChessSquare.yCoord() - i < 0) break;
            ChessCoordinate move = ChessCoordinate.coord(ChessSquare.xCoord(), ChessSquare.yCoord() - i);
            moves.add(move);
            if (enemyPieces.contains(move)) {
                for (Pieces p : AlivePieces) {
                    if ((p.coordX() == move.xCoord()) && (p.coordY() == move.yCoord()) && (this.isBlack() == p.isBlack())) {
                        moves.remove(move);
                    }
                }
                break;
            }
        }
    }

    private void listMoveLeftHelper(Set<ChessCoordinate> enemyPieces, List<Pieces> AlivePieces, Set<ChessCoordinate> moves) {
        for (int i = 0; i < 8; i++){
            if (ChessSquare.xCoord() - i < 0) break;
            ChessCoordinate move = ChessCoordinate.coord(ChessSquare.xCoord() - i, ChessSquare.yCoord());
            moves.add(move);
            if (enemyPieces.contains(move)) {
                for (Pieces p : AlivePieces) {
                    if ((p.coordX() == move.xCoord()) && (p.coordY() == move.yCoord()) && (this.isBlack() == p.isBlack())) {
                        moves.remove(move);
                    }
                }
                break;
            }
        }
    }

    private void listMoveRightHelper(Set<ChessCoordinate> enemyPieces, List<Pieces> AlivePieces, Set<ChessCoordinate> moves) {
        for (int i = 0; i < 8; i++){
            if (ChessSquare.xCoord() + i >= 8) break;
            ChessCoordinate move = ChessCoordinate.coord(ChessSquare.xCoord() + i, ChessSquare.yCoord());
            moves.add(move);
            if (enemyPieces.contains(move)) {
                for (Pieces p : AlivePieces) {
                    if ((p.coordX() == move.xCoord()) && (p.coordY() == move.yCoord()) && (this.isBlack() == p.isBlack())) {
                        moves.remove(move);
                    }
                }
                break;
            }
        }
    }

    @Override
    public Set<ChessCoordinate> listMove(List<Pieces> AlivePieces) {
        Set<ChessCoordinate> moves = new HashSet<>();
        Set<ChessCoordinate> piecesPlacement = new HashSet<>();
        for (Pieces p : AlivePieces) {
            piecesPlacement.add(ChessCoordinate.coord(p.coordX(), p.coordY()));
        }
        listMoveDownHelper(piecesPlacement, AlivePieces, moves);
        listMoveLeftHelper(piecesPlacement, AlivePieces, moves);
        listMoveRightHelper(piecesPlacement, AlivePieces, moves);
        listMoveUpHelper(piecesPlacement, AlivePieces, moves);
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