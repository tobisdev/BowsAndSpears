package game.Objects;

import game.Main.Game;

import java.awt.image.BufferedImage;
import java.util.Vector;

public class Obstacle extends Sprite {
    private final boolean collision;

    public boolean Object_Intersection(Sprite s){
        return false;
    }
    public boolean Object_Collision(Sprite s){
        return false;
    }
    public boolean Object_Grounded(Sprite s){
        return false;
    }
    public boolean Object_Collision_Left(Sprite s){
        return false;
    }
    public boolean Object_Collision_Right(Sprite s){
        return false;
    }
    public boolean Object_Collision_Below(Sprite s){
        return false;
    }

    @Override
    public void Collision_Update(Vector<Sprite> actors) {}

    public Obstacle(BufferedImage[] i, double x, double y, long delay, Game p, boolean c){
        super(i, x, y, delay, p);
        collision = c;
    }

    public boolean getCollision(){
        return collision;
    }
}
