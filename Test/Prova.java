package Test;

import java.util.Scanner;

public class Prova {
public static void main(final String[] args) {
    final Scanner s = new Scanner(System.in);
    while (s.hasNext()) {
        System.out.println(s.next());
    }
    s.close();
}
}