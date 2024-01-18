package game.CustomGUI;

import game.Main.Game;
import jdk.jfr.Enabled;

import java.awt.event.MouseEvent;
import java.awt.Graphics;

public abstract class GUIElements {
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private boolean enabled = true;
    private boolean pressed;
    private final Game parent;
    private final ActionListener Listener;


    public GUIElements(ActionListener Listener, int x, int y, int width, int height, Game parent){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.Listener = Listener;
        this.parent = parent;
    }

    public void mouseReleased(MouseEvent e){
        if(pressed && enabled){
            Listener.ActionPerformed(new ActionEvent(this));
        }
    }

    public boolean isPressed(int x, int y){
        return x >= this.x && x <= this.x+width && y >= this.y && y <= this.y+height;
    }

    public abstract void RenderGUI(Graphics g);

    public int getWidth() {
        return width;
    }

    public int getY() {
        return y;
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean getPressed(){
        return pressed;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Game getParent() {
        return parent;
    }
}
