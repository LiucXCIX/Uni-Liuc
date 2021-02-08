package Esame_Prog2;

import java.util.Scanner;

public class test {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        Ricevitore ric = new Ricevitore();
        while (s.hasNextLine()) {
            ric.aggiungiPacchetto(s.nextLine());
        }
        System.out.println(ric.toString());
        s.close();
    }
}