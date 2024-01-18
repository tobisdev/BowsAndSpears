package game.Objects;
import game.Main.Game;

import java.awt.geom.Rectangle2D;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Vector;

public abstract class Sprite extends Rectangle2D.Double implements Drawable, Movable {

    private boolean Remove = false;
    private final long Animation_Delay;
    private double Animation_Frame = 0;
    private final Game Parent;
    private BufferedImage[] Animation_Pictures;
    private int Animation_CurrentPicture = 0;
    private double dx;
    private double dy;

    int Animation_Loop_From;
    int Animation_Loop_To;

    public Sprite(BufferedImage[] i, double x, double y, long delay, Game p){
        Animation_Pictures = i;
        this.x = x;
        this.y = y;
        this.Animation_Delay = delay;
        this.width = Animation_Pictures[0].getWidth();
        this.height = Animation_Pictures[0].getHeight();
        Parent = p;
        Animation_Loop_From = 0;
        Animation_Loop_To = Animation_Pictures.length;
    }

    @Override
    public void doLogic(long delta) {
        Animation_Frame += (double)(delta) / (long)(1e6);
        if (Animation_Frame > Animation_Delay){
            Animation_Frame = 0;
            computeAnimation();
        }
    }

    @Override
    public void move(long delta) {
        if(dx!=0){
            x += dx * (delta / 1e9);
        }

        if(dy != 0){
            y += dy * (delta/1e9);
        }
    }

    @Override
    public void drawObjects(Graphics g) {
        g.drawImage(Animation_Pictures[Animation_CurrentPicture], (int) x, (int) y, null);
    }

    private void computeAnimation(){
        Animation_CurrentPicture++;
        if(Animation_CurrentPicture >= Animation_Loop_To){
            Animation_CurrentPicture = Animation_Loop_From;
        }
    }

    public void setLoop(int from, int to){
        Animation_Loop_From = from;
        Animation_Loop_To = to;
        Animation_CurrentPicture = from;
    }
    
    public abstract void Collision_Update(Vector<Sprite> actors);

    public void setVerticalSpeed(double dy) {
        this.dy = dy;
    }

    public void setHorizontalSpeed(double dx) {
        this.dx = dx;
    }

    public double getVerticalSpeed() {
        return dy;
    }

    public double getHorizontalSpeed() {
        return dx;
    }

    public Game getParent() {
        return Parent;
    }

    public void setX(double i){
        x = i;
    }

    public void setY(double i){
        y = i;
    }

    public void setRemove(boolean remove) {
        Remove = remove;
    }

    public boolean getRemove(){
        return Remove;
    }

    public int getAnimation_CurrentPicture() {
        return Animation_CurrentPicture;
    }

    public void setAnimation_Pictures(BufferedImage[] animation_Pictures) {
        Animation_Pictures = animation_Pictures;
    }
}