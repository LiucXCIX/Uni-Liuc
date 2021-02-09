package Esame_Prog2.Ascii_ART;

import java.util.ArrayList;
import java.util.List;

public class Stamp implements Figures {

    List<Figures> stamp;

    public Stamp() {
        stamp = new ArrayList<>();
    }

    public void addFigure(Figures f){
        stamp.add(f);
    }

    @Override
    public void draw(Bitmap b) {
        for (Figures f : stamp) {
            f.draw(b);
        }
    }
    
}