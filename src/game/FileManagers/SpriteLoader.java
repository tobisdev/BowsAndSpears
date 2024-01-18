package game.FileManagers;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;

public class SpriteLoader {
    //Images
    private BufferedImage[][] Level_Images;
    private BufferedImage[][] Player_Images;
    private BufferedImage[][] Enemy_Images;
    private BufferedImage Background_Image;
    private BufferedImage Title_Image;
    private BufferedImage[][] Bullet_Images;
    private BufferedImage[] Explosion_Images;
    private BufferedImage Heart_Image;
    private BufferedImage[] Cloud_Image;
    private BufferedImage Player_Profile_Image;


    public void loadSprites(){
        // Player Profile
        Player_Profile_Image = loadPics("images/Player_Profile.png", 1)[0];

        //Clouds
        Cloud_Image = this.loadPics("images/Cloud.png",1);

        //Hearts
        Heart_Image = loadPics("images/Heart.png", 1)[0];

        String[] Projectile_ImagePaths = {"images/ProjectileLeft.png","images/ProjectileRight.png"};
        Bullet_Images = this.loadPicsMulti(Projectile_ImagePaths, 4);

        // Explosion images
        Explosion_Images = this.loadPics("images/Explosion.png", 4);

        // Player Images
        String[] Player_ImagePaths = {"images/Player_Idle.png","images/Player_Fronflip.png",
                "images/Player_Walking Left.png","images/Player_Walking Right.png"};
        Player_Images = this.loadPicsMulti(Player_ImagePaths, 9);

        Background_Image = loadPics("images/Background5.png", 1)[0];
        Title_Image = loadPics("images/Title.png",1)[0];

        // Enemy Images
        String[] Enemy_ImagePaths = {"images/Enemy_Idle Left.png","images/Enemy_Idle Right.png","images/Enemy_Jump Left.png",
                "images/Enemy_Jump Right.png","images/Enemy_Walking Left.png","images/Enemy_Walking Right.png"};
        Enemy_Images = this.loadPicsMulti(Enemy_ImagePaths, 4);

        // Setup for Level Loader
        String[] Level_ImagePaths = {"images/Block_00.png","images/Block_01.png","images/Block_02.png","images/Block_03.png",
                "images/Block_04.png","images/Block_05.png","images/Block_06.png","images/Block_07.png",
                "images/Block_08.png","images/Block_09.png","images/Block_10.png","images/Block_11.png",
                "images/Block_12.png","images/Torch.png"
        };
        Level_Images = loadPicsMulti(Level_ImagePaths, 4); // Hard defining x Anim states for all Obstacles
    }

    // Method for loading multiple Sprites at once like for "LevelLoader"
    private BufferedImage[][] loadPicsMulti(String[] path, int pics){
        BufferedImage[][] imgs = new BufferedImage[path.length][pics];

        for (int i = 0; i < path.length; i++) {
            imgs[i] = loadPics(path[i], pics);
        }

        return imgs;
    }

    // Loading Sprites
    private BufferedImage[] loadPics(String path, int pics) {
        BufferedImage[] anim = new BufferedImage[pics];
        BufferedImage source = null;

        URL pic_url = getClass().getClassLoader().getResource(path);
        try {
            source = ImageIO.read(pic_url);
        } catch (Exception e) {
            System.out.println(e);
            System.out.println(path);
            System.out.println(pic_url);
            System.out.println("" + source);
        }

        for (int x = 0; x < pics; x++) {
            anim[x] = source.getSubimage(x * source.getWidth() / pics, 0, source.getWidth() / pics, source.getHeight());
        }

        return anim;
    }

    public BufferedImage getBackground_Image() {
        return Background_Image;
    }

    public BufferedImage getTitle_Image() {
        return Title_Image;
    }

    public BufferedImage[] getExplosion_Images() {
        return Explosion_Images;
    }

    public BufferedImage[][] getBullet_Images() {
        return Bullet_Images;
    }

    public BufferedImage[][] getEnemy_Images() {
        return Enemy_Images;
    }

    public BufferedImage[][] getLevel_Images() {
        return Level_Images;
    }

    public BufferedImage[][] getPlayer_Images() {
        return Player_Images;
    }

    public BufferedImage[] getCloud_Image() {
        return Cloud_Image;
    }

    public BufferedImage getHeart_Image() {
        return Heart_Image;
    }

    public BufferedImage getPlayer_Profile_Image() {
        return Player_Profile_Image;
    }
}
