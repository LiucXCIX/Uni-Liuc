package Esame_Prog2.Chess;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StandardChess implements Chess {

    private int turn;
    private Pieces[][] scacchiera;
    private List<Pieces> AlivePieces;
    private boolean isNotCheckmate;
    private boolean isCheck = false;
    private boolean whiteWon = false;
    private boolean blackWon = false;
    private ChessCoordinate blackKingPos;
    private ChessCoordinate whiteKingPos;

    public Pieces initializeBlackBackrankPieces(int j, List<Pieces> AlivePieces) {
        Pieces p;
        switch (j) {
            case 0: 
                p = new Tower(true, j);
                break;
            case 1:
                p = new Knight(true, j);
                break;
            case 2:
                p = new Bishop(true, j);
                break;
            case 3:
                p = new Queen(true);
                break;
            case 4:
                p = new King(true);
                break;
            case 5:
                p = new Bishop(true, j);
                break;
            case 6:
                p = new Knight(true, j);
                break;
            case 7:
                p = new Tower(true, j);
                break;
            default:
                p = new VoidPiece(0, j);
        }
        AlivePieces.add(p);
        return p;
    }

    public Pieces initializeWhiteBackrankPieces(int j, List<Pieces> AlivePieces) {
        Pieces p;
        switch (j) {
            case 0: 
                p = new Tower(false, j);
                break;
            case 1:
                p = new Knight(false, j);
                break;
            case 2:
                p = new Bishop(false, j);
                break;
            case 3:
                p = new Queen(false);
                break;
            case 4:
                p = new King(false);
                break;
            case 5:
                p = new Bishop(false, j);
                break;
            case 6:
                p = new Knight(false, j);
                break;
            case 7:
                p = new Tower(false, j);
                break;
            default:
                p = new VoidPiece(0, j);
        }
        AlivePieces.add(p);
        return p;
    }

    public Pieces initializeBlackFrontrankPieces(int j, List<Pieces> AlivePieces) {
        //da implementare assieme alle nozioni di pin e all'istanza pawn che implementa pieces
        //AlivePieces.add(p);
        return null;
    }
    public Pieces initializeWhiteFrontrankPieces(int j, List<Pieces> AlivePieces) {
        //da implementare assieme alle nozioni di pin e all'istanza pawn che implementa pieces
        //AlivePieces.add(p);
        return null;
    }

    public Pieces initializePieces(int i, int j, List<Pieces> AlivePieces) {
        Pieces p;
        switch (i) {
            case 0:
                p = initializeWhiteBackrankPieces(j, AlivePieces);
                break;
            case 1:
                //p = initializeWhiteFrontrankPieces(j, AlivePieces);
                p = new VoidPiece(i, j); //da cancellare poi
                break;
            case 6:
                //p = initializeBlackFrontrankPieces(j, AlivePieces);
                p = new VoidPiece(i, j); //da cancellare poi
                break;
            case 7:
                p = initializeBlackBackrankPieces(j, AlivePieces);
                break;
            default:
                p = new VoidPiece(i, j);                
        }
        return p;
    }

    public StandardChess(){
        this.blackKingPos = ChessCoordinate.coord(7, 3);
        this.whiteKingPos = ChessCoordinate.coord(0, 3);
        this.turn = 1;
        this.isNotCheckmate = true;
        scacchiera = new Pieces[8][8];
        AlivePieces = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Pieces p = initializePieces(i, j, AlivePieces);
                scacchiera[i][j] = p;
            }
        }
    }

    public int translateChessNotation(char c) throws NumberFormatException{
        int i = 0;
        switch (c) {
            case 'a':   i = 0;
                        break;
            case 'b':   i = 1;
                        break;
            case 'c':   i = 2;
                        break;
            case 'd':   i = 3;
                        break;
            case 'e':   i = 4;
                        break;
            case 'f':   i = 5;
                        break;
            case 'g':   i = 6;
                        break;
            case 'h':   i = 7;
                        break;
            default:
                    throw new NumberFormatException();
        }
        return i;
    }

    public void move(String cmd){
        //qua sono da spostare tutte le linee di codice relative al muovere un pezzo
    }

    @Override
    public void play(InputStream in, PrintStream out) {
        Scanner s = new Scanner(in);
        while (isNotCheckmate){
            String blackOrWhite = ((turn % 2) == 0) ? "nero" : "bianco";
            System.out.printf("Turno n^%d: Tocca al %s!\n", turn, blackOrWhite);
            System.out.println(this.toString());
            String cmd = s.next();
            if (cmd.length() != 5) {
                System.out.println("\n > Scusami, non ho capito.\n > Il comando deve essere nel formato [posizione iniziale]-[posizione finale]\n");
                continue;
            }
            //inizio procedura move
            int iniX, iniY, finX, finY;
            //se sei in check, solo il re può essere mosso, poi isCheck sarà false!
            //ci sono dei gravi errori sulla rappresentazione, guarda il telefono per avere più informazioni a riguardo
            try {
            iniX = translateChessNotation(cmd.charAt(0));
            iniY = Integer.valueOf(String.valueOf(cmd.charAt(1))) - 1;
            finX = translateChessNotation(cmd.charAt(3));
            finY = Integer.valueOf(String.valueOf(cmd.charAt(4))) - 1;
            } catch (NumberFormatException e) {
                System.out.println("\n > Scusami, non ho capito.\n > Il comando deve essere nel formato [posizione iniziale]-[posizione finale]\n");
                continue;
            }
            if ((iniY < 0) || (iniY >= 8) || (finY < 0) || (finY >= 8)) {
                System.out.println("\n > Scusami, non ho capito.\n > Il comando deve essere nel formato [posizione iniziale]-[posizione finale]\n");
                continue; 
            }
            Pieces piece = scacchiera[iniY][iniX]; //ho invertito l'ordine per vedere se il risultato cambia lel
            Pieces pieceEaten = scacchiera[finY][finX];
            ChessCoordinate firstSquare = ChessCoordinate.coord(iniX, iniY);
            ChessCoordinate lastSquare = ChessCoordinate.coord(finX, finY);
            if (((turn % 2 == 0)) != (piece.isBlack())) {
                System.out.println("\n > Scusami, ma non è stato possibile muovere il pezzo, questo perché il pezzo che hai tentato di muovere non era il tuo!");
                continue;
            }
            if (piece instanceof VoidPiece) {
                System.out.printf("\n > Scusami, ma non è stato possibile muovere il pezzo, questo perché non ho trovato nessun pezzo nella casella %c%d\n\n", cmd.charAt(0), firstSquare.yCoord() + 1);
                continue;
            }
            if (piece.isPinned()) {
                System.out.println("\n > Scusami, ma non è stato possibile muovere il pezzo, questo perché il pezzo che hai tentato di muovere era inchiodato! (Stava proteggendo il re)");
                continue;
            }
            try {
                isCheck = piece.move(lastSquare, AlivePieces);
                if (pieceEaten instanceof VoidPiece) {
                    pieceEaten.move(firstSquare, AlivePieces);
                }
            } catch (InvalidMoveException e) {
                System.out.print("\n > Scusami, la mossa che hai tentato di fare era illegale\n\n");
                continue;
            }
            scacchiera[firstSquare.yCoord()][firstSquare.xCoord()] = new VoidPiece(firstSquare.xCoord(), firstSquare.yCoord());
            scacchiera[lastSquare.yCoord()][lastSquare.xCoord()] = piece;
            if ((piece instanceof King) && (piece.isBlack())) this.blackKingPos = lastSquare;
            if ((piece instanceof King) && (!piece.isBlack())) this.whiteKingPos = lastSquare;
            //fine procedura move
            //procedura checkmate
            //genera nullPointerException
            King blackKing = null;
            King whiteKing = null;
            if (scacchiera[blackKingPos.xCoord()][blackKingPos.yCoord()] instanceof King){
                blackKing = (King) scacchiera[blackKingPos.xCoord()][blackKingPos.yCoord()];
            }
            if (scacchiera[whiteKingPos.xCoord()][whiteKingPos.yCoord()] instanceof King){
                whiteKing = (King) scacchiera[whiteKingPos.xCoord()][whiteKingPos.yCoord()];
            }
            if (blackKing.isCheckmate(AlivePieces) && isCheck){
                isNotCheckmate = false;
                whiteWon = true;
                break;
            }
            if (whiteKing.isCheckmate(AlivePieces) && isCheck){
                isNotCheckmate = false;
                blackWon = true;
                break;
            }
            if (whiteKing.isDraw(AlivePieces) || blackKing.isDraw(AlivePieces)) break;
            
            turn++;
        }
        if (blackWon) {
            System.out.println("Il nero ha vinto!");
        } else if (whiteWon) {
            System.out.println("Il bianco ha vinto!");
        } else {
            System.out.println("Questa è stata proprio una bella partita, sfortunatamente però è finita con una patta!");
        }
        s.close();
    }

    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        for(int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++) {
                s.append(scacchiera[i][j].image());
            }
            s.append("\n");
        }
        return s.toString();
    }
    
}