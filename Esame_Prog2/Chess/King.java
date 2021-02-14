package Esame_Prog2.Chess;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class King implements Pieces {
        
    private ChessCoordinate ChessSquare;
    private final boolean isBlack;
    private boolean isAlive;
    private String image;

    /*
    *   Rep inv: la posizione del pezzo deve essere valida
    *   Abs function: rappresentazione del pezzo, se isAlive sarà considerato un pezzo valido con i suoi comportamenti, altrimenti no
    *   Abs inv: la posizione del pezzo deve essere valida
    *            se isAlive = false esso non si potrà muovere
    */

    /**
     * Post-condizioni: inizializza e restituisce un'istanza di King
     * @param isBlack definisce se il re è nero o meno
     */
    public King(boolean isBlack){
        if (isBlack) {
            ChessSquare = ChessCoordinate.coord(7, 4);
            this.image = "\u265A";
        } else {
            ChessSquare = ChessCoordinate.coord(0, 4);
            this.image = "\u2654";
        }
        this.isBlack = isBlack;
        this.isAlive = true;
    }

    @Override
    public boolean move(ChessCoordinate move, List<Pieces> AlivePieces) throws InvalidMoveException {
        Set<ChessCoordinate> possibleMoves = listMove(AlivePieces);
        if (possibleMoves.isEmpty()) {
            return true;
        }
        if (!isAlive) throw new InvalidMoveException("The piece was eaten");
        if (!possibleMoves(move, AlivePieces)) throw new InvalidMoveException("This move is not valid");
        this.ChessSquare = move;
        for (Pieces p : AlivePieces) {
            if ((p.coordX() == this.coordX()) && (p.coordY() == this.coordY())) {
                p.getEaten(); //ATTENZIONE, se i controlli di check non funzionano potresti perfino mangiare un Re, non male!
                AlivePieces.remove(p);
            }
        }
        return false;
    }

    @Override
    public void getEaten() {
        isAlive = false;
    }

    @Override
    public boolean possibleMoves(ChessCoordinate move, List<Pieces> AlivePieces) {
        Set<ChessCoordinate> protectedSquares = new HashSet<>();
        if ((move.xCoord() >= 8) || (move.yCoord() >= 8)) return false;
        for (Pieces p : AlivePieces) {
            if (((p.coordX() == move.xCoord()) && (p.coordY() == move.yCoord())) && ((p.isBlack() == this.isBlack()))) return false;
            if (p.isBlack() == !(this.isBlack())) {
                protectedSquares.addAll(p.listMove(AlivePieces));
            }
        }
        if (protectedSquares.contains(move)) return false;
        if ((((move.xCoord() == ChessSquare.xCoord() + 1) && (move.yCoord() == ChessSquare.yCoord() + 1))) || (((move.xCoord() == ChessSquare.xCoord()) && (move.yCoord() == ChessSquare.yCoord() + 1))) || (((move.xCoord() == ChessSquare.xCoord() - 1) && (move.yCoord() == ChessSquare.yCoord() + 1))) || (((move.xCoord() == ChessSquare.xCoord() - 1) && (move.yCoord() == ChessSquare.yCoord()))) || (((move.xCoord() == ChessSquare.xCoord() - 1) && (move.yCoord() == ChessSquare.yCoord() - 1))) || (((move.xCoord() == ChessSquare.xCoord()) && (move.yCoord() == ChessSquare.yCoord() - 1))) || (((move.xCoord() == ChessSquare.xCoord() + 1) && (move.yCoord() == ChessSquare.yCoord() - 1))) || (((move.xCoord() == ChessSquare.xCoord() + 1) && (move.yCoord() == ChessSquare.yCoord())))) return true;
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

    @Override
    public Set<ChessCoordinate> listMove(List<Pieces> AlivePieces) {
        Set<ChessCoordinate> moves = new HashSet<>();
        Set<ChessCoordinate> protectedSquares = new HashSet<>();
        for (Pieces p : AlivePieces) {
            if (p.isBlack() == !(this.isBlack())) {
                protectedSquares.addAll(p.listMove(AlivePieces));
            } else {
                protectedSquares.add(ChessCoordinate.coord(p.coordX(), p.coordY()));
            }
        }
        for (int i = 0; i < 8; i++) {
            ChessCoordinate p;
            switch (i) {
                case 0: p = ChessCoordinate.coord(ChessSquare.xCoord() + 1,  ChessSquare.yCoord() + 1);
                        break;
                case 1: p = ChessCoordinate.coord(ChessSquare.xCoord() + 1,  ChessSquare.yCoord() - 1);
                        break;
                case 2: p = ChessCoordinate.coord(ChessSquare.xCoord() - 1,  ChessSquare.yCoord() + 1);
                        break;
                case 3: p = ChessCoordinate.coord(ChessSquare.xCoord() - 1,  ChessSquare.yCoord() - 1);
                        break;
                case 4: p = ChessCoordinate.coord(ChessSquare.xCoord() + 1,  ChessSquare.yCoord());
                        break;
                case 5: p = ChessCoordinate.coord(ChessSquare.xCoord() - 1,  ChessSquare.yCoord());
                        break;
                case 6: p = ChessCoordinate.coord(ChessSquare.xCoord(),  ChessSquare.yCoord() + 1);
                        break;
                case 7: p = ChessCoordinate.coord(ChessSquare.xCoord(),  ChessSquare.yCoord() - 1);
                        break;
                default:
                        p = ChessCoordinate.coord(ChessSquare.xCoord(),  ChessSquare.yCoord() - 1);
            }
            if (!protectedSquares.contains(p)) moves.add(p);
        }
        return moves;
    }

    @Override
    public boolean isCheck(List<Pieces> AlivePieces) {
        return false;
    }

    @Override
    public boolean isPinned() {
        return false;
    }

    public boolean isCheckmate(List<Pieces> AlivePieces){
        Set<ChessCoordinate> possibleMoves = listMove(AlivePieces);
        if (possibleMoves.isEmpty()) {
            return true;
        }
        return false;
    }

    //DA IMPLEMENTARE
    public boolean isDraw(List<Pieces> AlivePieces) {
        return false;
    }
}
