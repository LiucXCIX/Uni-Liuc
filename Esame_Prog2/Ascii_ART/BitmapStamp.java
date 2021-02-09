package Esame_Prog2.Ascii_ART;

public class BitmapStamp {

    private int b, h;
    private boolean[][] bitmap;

    public BitmapStamp(boolean[][] stamp, int b, int h) {
        this.b = b;
        this.h = h;
        bitmap = new boolean[b][h];
        for (int i = 0; i < b; i++) {
            for (int j = 0; j < h; j++) {
                bitmap[i][j] = stamp[i][j];
            }
        }
    }

    public BitmapStamp(){
        this.b = 0;
        this.h = 0;
        bitmap = new boolean[1][1];
    }

    public void draw(Bitmap bit, int x, int y) {
        if ((this.b == 0) && (this.h == 0)) return;
        for (int i = 0; i < b; i++) {
            for (int j = 0; j < h; j++) {
                if ((bit.accettableCoord(x + i, y + j)) && (bitmap[i][j])) {
                    bit.turnOn(x + i, y + j);
                }
            }
        }
    }
}
