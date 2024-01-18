package game.Overlays;

import game.Main.Game;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class Credits {

    private final Vector<String> text = new Vector<String>();
    private double dy = 900;
    private int y;
    private int x;
    private final Game parent;

    public Credits(Game parent){
        this.parent = parent;
    }

    public void loadCredits(String s){
        try{
            BufferedReader Br = new BufferedReader(new FileReader(s));

            String line = Br.readLine();
            while(line!=null){
                text.add(line);
                line = Br.readLine();
            }
            Br.close();
        }catch (IOException e){
            System.out.println("Fehler beim Lesen der Datei: " + e);
        }
    }

    public void drawCredits(Game p, long delta, int x, Graphics g){
        g.setFont(p.getUndertaleFont().deriveFont(0,30));

        for (int i = 0; i < text.size(); i++) {
            g.setColor(Color.blue);
            y = (int)(dy);
            g.drawString(text.get(i),x + 4,y + i * 45 + 4);
            g.setColor(Color.white);
            g.drawString(text.get(i),x,y + i * 45);
        }
    }
    public void updateY(double delta){
        dy -= (delta / (long)(1e6) / 8);
        if(dy <= -1700){
            dy = 900;
            parent.setGameState((byte)0);
        }
    }
}
