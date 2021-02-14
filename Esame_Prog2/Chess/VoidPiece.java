package Esame_Prog2.Chess;

import java.util.List;
import java.util.Set;

public class VoidPiece implements Pieces {
    private String image;
    private ChessCoordinate ChessSquare;

    public VoidPiece(int i, int j) {
        ChessSquare = ChessCoordinate.coord(i, j);
        if ((i + j) % 2 == 0) {
            image = "\u25A1";
        } else {
            image = "\u25A0";
        }
    }

    @Override
    public boolean move(ChessCoordinate move, List<Pieces> AlivePices) throws InvalidMoveException {
        ChessSquare = move;
        if ((move.xCoord() + move.yCoord()) % 2 == 0) {
            image = "\u25A1";
        } else {
            image = "\u25A0";
        }
        return false;
    }

    @Override
    public void getEaten() {
    }

    @Override
    public boolean possibleMoves(ChessCoordinate move, List<Pieces> AlivePieces) {
        return false;
    }

    @Override
    public boolean isBlack() {
        return false;
    }

    @Override
    public int coordX() {
        return ChessSquare.xCoord();
    }

    @Override
    public int coordY() {
        return ChessSquare.yCoord();
    }

    @Override
    public String image() {
        return image;
    }

    @Override
    public Set<ChessCoordinate> listMove(List<Pieces> AlivePieces) {
        return null;
    }

    @Override
    public boolean isCheck(List<Pieces> AlivePieces) {
        return false;
    }

    @Override
    public boolean isPinned() {
        return false;
    }
}
