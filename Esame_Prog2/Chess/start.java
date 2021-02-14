package Esame_Prog2.Chess;

public class start {
    public static void main(String[] args) {
        Chess game = new StandardChess();
        game.play(System.in, System.out);
    }
}