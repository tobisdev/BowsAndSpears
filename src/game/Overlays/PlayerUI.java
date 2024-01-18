package game.Overlays;

import game.Main.Game;

import java.awt.*;

public class PlayerUI {
    public static void renderPlayerUI(Graphics g, Game p){
        String String_life_String = "Playerlife: " + p.getPlayer_Character().getLife();
        g.setColor(Color.red);
        g.setColor(Color.black);

        g.fillRect(p.getWidth() - 10 - 100, 10, 100, 100);
        g.drawImage(p.getPlayer_Profile_Image(), p.getWidth() - 105, 15, 90, 91, p);
        for (int i = 0; i < p.getPlayer_Character().getLife(); i++) {
            g.drawImage(p.getHeart_Image(), p.getWidth() - 10 - 30 - (i % 3) * 35, 120 + (35 * (int)(i / 3)), 30, 35, p);
        }

        g.setColor(Color.BLACK);
        g.fillRect(15, 47, 300, 64);
        g.setColor(Color.WHITE);

        // Point Counter
        if(p.getUndertaleFont() != null) {
            g.setFont(p.getUndertaleFont().deriveFont(0, 20));
        }
        g.drawString("Points:        " + p.getDisplayPoints(), 20, 72);
        if(p.getHighScore() == p.getDisplayPoints()){
            g.setColor(Color.ORANGE);
        }
        g.drawString("Highscore: " + p.getHighScore(), 20, 104);
    }
}
