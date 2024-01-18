package game.Overlays;

import game.Main.Game;

import java.awt.*;

public class DeathScreen {
    public static void renderDeathScreen(Graphics g, Game p){
        g.setColor(Color.GRAY);
        g.fillRect(p.getWidth() / 2 - 400, p.getHeight() / 2 - 200, 800, 400);
        g.setColor(Color.DARK_GRAY);
        g.fillRect(p.getWidth() / 2 - 390, p.getHeight() / 2 - 190, 780, 380);


        if(p.getUndertaleFont() != null) {
            g.setFont(p.getUndertaleFont().deriveFont(0, 40));
        }
        int Stringwidth = g.getFontMetrics().stringWidth("You Died!");
        g.setColor(Color.RED);
        g.drawString("You Died!", p.getX() + p.getWidth() / 2 - Stringwidth / 2, p.getY() + p.getHeight() / 2 - 50);
        if(p.getUndertaleFont() != null) {
            g.setFont(p.getUndertaleFont().deriveFont(0, 30));
        }
        Stringwidth = g.getFontMetrics().stringWidth("Your Points: " + p.getDisplayPoints());
        if(p.getDisplayPoints() == p.getHighScore()){
            g.setColor(Color.ORANGE);
        }else{
            g.setColor(Color.WHITE);
        }
        g.drawString("Your Points: " + p.getDisplayPoints(), p.getX() + p.getWidth() / 2 - Stringwidth / 2, p.getY() + p.getHeight() / 2 + 50);
    }
}
