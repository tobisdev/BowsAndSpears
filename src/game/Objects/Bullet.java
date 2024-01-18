package game.Objects;

import game.Main.Game;
import game.soundLib.*;

import java.awt.image.BufferedImage;
import java.util.Vector;

public class Bullet extends Sprite {
    private int Bullet_Speed;
    private boolean Facing_Right = false;
    private double Lifetime = 0.0;
    private double Time_Existing = 0.0;

    private boolean Create_Explosion = false;
    private final soundLib slib;

    private final BufferedImage[][] Animations;

    private Sprite Owner;

    public void Collision_Update(Vector<Sprite> actors) {

        if (actors != null) {
            for (int i = 0; i < actors.size(); i++) {
                Sprite s = actors.get(i);

                if (this.intersects(s)) {
                    if (s instanceof Obstacle && ((Obstacle) s).getCollision()) {
                        Create_Explosion = true;
                        super.setRemove(true);
                    }
                }
            }
        }
    }

    @Override
    public void move(long delta) {
        if (Facing_Right) {
            this.x += Bullet_Speed  * (double) delta / (long) (1e9);
        } else {
            this.x -= Bullet_Speed * (double)  delta / (long) (1e9);
        }

    }

    @Override
    public void doLogic(long delta) {
        if(Facing_Right){
            super.setAnimation_Pictures(Animations[1]);
        }else{
            super.setAnimation_Pictures(Animations[0]);
        }

        super.doLogic(delta);
        this.Time_Existing += delta;

        if (this.Time_Existing > Lifetime * 1000000) {
            super.setRemove(true);
        }
    }

    public Bullet(BufferedImage[][] i, double x, double y, long delay, Game p) {
        super(i[0], x, y, delay, p);
        Animations = i;
        slib = p.getSlib();
    }

    public void setBullet_Speed(int bullet_Speed) {
        Bullet_Speed = bullet_Speed;
    }

    public void setFacing_Right(boolean facing_Right) {
        Facing_Right = facing_Right;
    }

    public void setLifetime(double l) {
        this.Lifetime = l;
    }

    public void setCreate_Explosion(boolean create_Explosion) {
        Create_Explosion = create_Explosion;
    }

    public boolean getCreate_Explosion() {
        return Create_Explosion;
    }

    public void setOwner(Sprite owner) {
        Owner = owner;
    }

    public Sprite getOwner() {
        return Owner;
    }
}
