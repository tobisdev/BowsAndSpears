package game.Objects;

import game.Main.Game;

import java.awt.image.BufferedImage;
import java.util.Vector;

public class Cloud extends Sprite {
    private boolean Facing_Right = false;

    public void Collision_Update(Vector<Sprite> actors) {
    }

    @Override
    public void move(long delta) {
        if(Facing_Right){
            this.x += super.getParent().getCloud_Speed() * (delta) / (1e9);
        }else{
            this.x -= super.getParent().getCloud_Speed() * (delta) / (1e9);
        }
    }

    @Override
    public void doLogic(long delta) {
        if(this.x + this.width < 0 && !Facing_Right){
            Facing_Right = true;
        }else if(this.x > super.getParent().getWidth() && Facing_Right){
            Facing_Right = false;
        }
    }

    public Cloud(BufferedImage[] i, double x, double y, long delay, Game p) {
        super(i, x, y, delay, p);
        if(Math.random() > 0.5){
            Facing_Right = true;
        }
    }

    public void setFacing_Right(boolean facing_Right) {
        Facing_Right = facing_Right;
    }
}
