package game.Objects;
import game.Main.Game;

import java.awt.image.BufferedImage;
import java.util.Vector;
import game.soundLib.*;

public class Player extends Sprite {

    private final Game Parent;

    //Collision Variables
    private boolean inAir;
    private boolean Stuck_from_Left;
    private boolean Stuck_from_Right;
    private boolean Stuck_from_Top;

    //Physics Variables
    private double Time_Fall_Time;
    private double Jump_Force;
    private double Vertical_Speed;

    //Movement Variables
    private boolean Facing_Right = true;
    private boolean Walking = false;

    private final BufferedImage[][] Animation_Pictures;

    private double Time_Shoot_Timeout = 0.0;

    private BufferedImage[][] Animations;

    private int life;

    private final soundLib slib;

    // Constructor
    public Player(BufferedImage[][] i, double x, double y, long delay, Game p){
        super(i[0], x, y, delay, p);
        Animation_Pictures = i;
        Parent = p;
        slib = p.getSlib();
    }

    // Movement with Gravity
    @Override
    public void move(long delta) {
        if(super.getHorizontalSpeed() != 0){
            x += super.getHorizontalSpeed() * (delta / 1e9);
        }

        if(super.getVerticalSpeed() != 0){
            y+= super.getVerticalSpeed();
        }


        //gravity
        if(Vertical_Speed != 0){
            y += (Time_Fall_Time * Time_Fall_Time / (long)(1e18) * (3.91)) * ((double) delta) / (long)(1e9) * 500 - Jump_Force * delta / (long)(1e9);
        }
    }

    public void shoot(double timeout){
        if(Time_Shoot_Timeout / (long)(1e6) > timeout){
            Time_Shoot_Timeout = 0.0;
            super.getParent().createBullet(Facing_Right,(int)(this.x + this. width / 2), (int)this.y + (int)this.height / 4, this);
        }
    }

    // Logic
    @Override
    public void doLogic(long delta) {
        // Gravity Function
        Vertical_Speed = (Time_Fall_Time * Time_Fall_Time / (long)(1e18) * (3.91)) * ((double) delta) / (long)(1e9) * 500 - Jump_Force * delta / (long)(1e9);
        Time_Shoot_Timeout += delta;
        super.setVerticalSpeed(Vertical_Speed);

        //Set animations
        if(inAir){
            super.setAnimation_Pictures(Animation_Pictures[1]);
        }else{
            if(Walking){
                if(Facing_Right){
                    super.setAnimation_Pictures(Animation_Pictures[3]);
                }else{
                    super.setAnimation_Pictures(Animation_Pictures[2]);
                }
            }else{
                super.setAnimation_Pictures(Animation_Pictures[0]);
            }
        }

        super.doLogic(delta);
        //Collision Check
        if (Stuck_from_Right && super.getHorizontalSpeed() < 0) {
            setHorizontalSpeed(0);
        }
        if (Stuck_from_Left && super.getHorizontalSpeed() > 0) {
            setHorizontalSpeed(0);
        }
        if (Stuck_from_Top && super.getVerticalSpeed() < 0) {
            setJump_Force(0);
            setVerticalSpeed(0);
        }
        if (!inAir && super.getVerticalSpeed() > 0) {
            setVerticalSpeed(0);
        }

        // Activate / Deactivate Gravity
        if (!inAir){
            Time_Fall_Time = 0;
            Jump_Force = 0;
        }else{
            Time_Fall_Time += delta;
        }

        //Getting if is faced in right direction
        Walking = getHorizontalSpeed() != 0;
        if(Walking){
            Facing_Right = (getHorizontalSpeed() > 0);
        }

        //Out of bounds Check
        if (getX() < 0 && super.getHorizontalSpeed() < 0) {
            setHorizontalSpeed(0);
        }
        if (getX() + getWidth() > super.getParent().getWidth()&& super.getHorizontalSpeed() > 0) {
            setHorizontalSpeed(0);
        }
        if (getY() < 0&& super.getVerticalSpeed() < 0) {
            setVerticalSpeed(0);
        }
        if (getY() + getHeight() > super.getParent().getHeight()&& super.getVerticalSpeed() > 0) {
            setVerticalSpeed(0);
        }
    }

    @Override
    public void Collision_Update(Vector<Sprite> actors){
        inAir = true;
        Stuck_from_Top = false;
        Stuck_from_Left = false;
        Stuck_from_Right = false;

        if (actors != null) {
            for (int i = 0; i < actors.size(); i++) {
                Sprite s = actors.get(i);

                //Collision Update
                if (this.intersects(s)) {

                    if(s instanceof Bullet){
                        if(!(((Bullet) s).getOwner() instanceof Player)){
                            damage();
                            s.setRemove(true);
                            ((Bullet) s).setCreate_Explosion(true);
                        }
                    }

                    if (s instanceof Obstacle && ((Obstacle) s).getCollision()) {
                        if (this.x + this.width > s.x + s.width / 6 && this.x < s.x + s.width -s.width /6&&
                                this.y + this.height > s.y - s.height / 3 && this.y + this.height < s.y + s.height / 3) {
                            inAir = false;

                            if(this.y + this.height > s.y + 1){
                                bottomUnstuck();
                            }
                        }
                        if (this.y > s.y + s.height / 2 && this.y < s.y + s.height && this.x + this.width > s.x + 10 && this.x < s.x + s.width - 10) {
                            Stuck_from_Top = true;
                        }
                        if (this.x + this.width / 2 > s.x + s.width / 2 && this.y + this.height > s.y + s.height / 3) {
                            Stuck_from_Right = true;
                        }
                        if (this.x + this.width / 2 < s.x + s.width / 2 && this.y + this.height > s.y + s.height / 3) {
                            Stuck_from_Left = true;
                        }
                    }
                }
            }
        }
    }

    public void damage(){
        int dmg = 1;
        life = life-dmg;

        if (life < 1 && super.getParent().getTime_GameOverTime() == 0){
            super.getParent().setTime_GameOverTime(System.currentTimeMillis());
        }
    }

    public int getLife() {
        return life;
    }

    private void bottomUnstuck(){
        this.y--;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public void setJump_Force(double jump_Force) {
        Jump_Force = jump_Force;
    }
}