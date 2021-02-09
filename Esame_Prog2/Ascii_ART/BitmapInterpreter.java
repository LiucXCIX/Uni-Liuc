package Esame_Prog2.Ascii_ART;

import java.util.Scanner;

public class BitmapInterpreter {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        Bitmap b = new Bitmap(1, 1);
        int x, y;
        BitmapStamp stamp = new BitmapStamp();
        while (s.hasNext()) {
            Figures f;
            switch (s.next().charAt(0)) {
                case 'n':
                        b = new Bitmap(s.nextInt(), s.nextInt());
                        break;
                case 'c':
                        b.turnOffAll();
                        break;
                case 'i':
                        b.bitmapInversion();
                        break;
                case 'x':
                        b.turnOn(s.nextInt(), s.nextInt());
                        break;
                case 'o':
                        b.turnOff(s.nextInt(), s.nextInt());
                        break;
                case 'h':
                        f = Segment.horizontalSegment(s.nextInt(), s.nextInt(), s.nextInt());
                        f.draw(b);
                        break;
                case 'v':
                        f = Segment.verticalSegment(s.nextInt(), s.nextInt(), s.nextInt());
                        f.draw(b);
                        break;
                case 'r':
                        f = Rectangle.rect(s.nextInt(), s.nextInt(), s.nextInt(), s.nextInt());
                        f.draw(b);
                        break;
                case 's':
                        x = s.nextInt();
                        y = s.nextInt();
                        int length = s.nextInt();
                        int height = s.nextInt();
                        boolean[][] part = b.patrizione(x, y, length, height);
                        stamp = new BitmapStamp(part, length, height);
                        break;
                case 'd':
                        x = s.nextInt();
                        y = s.nextInt();
                        stamp.draw(b, x, y);
                        break;
                case 'p':
                        b.draw();
                        break;
            }
        }
        s.close();
    }
}