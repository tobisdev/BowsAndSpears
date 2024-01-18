package game.Objects;
import game.Main.Game;

import java.awt.image.BufferedImage;
import java.util.Vector;

public class Explosion extends Sprite {
    int old_pic = 0;

    @Override
    public void Collision_Update(Vector<Sprite> actors) {
    }

    public Explosion(BufferedImage[] i, double x, double y, long delay, Game p) {
        super(i, x, y, delay, p);
    }

    @Override
    public void doLogic(long delta) {
        old_pic = getAnimation_CurrentPicture();
        super.doLogic(delta);
        if (super.getAnimation_CurrentPicture() == 0 && old_pic != 0) {
            super.setRemove(true);
        }
    }
}
