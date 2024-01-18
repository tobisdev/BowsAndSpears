package game.Overlays;

import game.CustomGUI.Button;
import game.CustomGUI.GUIElements;
import game.Main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Vector;

public class MainMenu {
    private static final Vector<GUIElements> Menu_Elements = new Vector<GUIElements>();
    private static Font font;
    private static Game parent;
    private static Button ButtonPlay;
    private static Button ButtonCredits;
    private static Button QuitButton;
    
    public MainMenu(Game parent){
        MainMenu.parent = parent;
    }
    
    public void CreateMenu(){
        //Menu
        //PlayButton
        ButtonPlay = new game.CustomGUI.Button(parent, parent.getWidth() / 2 - 150, 400, 300, 120, parent);
        ButtonPlay.setBackroundImage(loadPics("images/Button.png", 1)[0]);
        ButtonPlay.setClickedImage(loadPics("images/Button_Click.png", 1)[0]);
        ButtonPlay.setText("Play");
        ButtonPlay.setFont(font.deriveFont(0, 40));
        ButtonPlay.setColor_Text_Color(Color.BLACK);
        Menu_Elements.add(ButtonPlay);
        //Credits
        ButtonCredits = new game.CustomGUI.Button(parent, parent.getWidth() / 2 - 150, 500, 300, 120, parent);
        ButtonCredits.setBackroundImage(loadPics("images/Button.png", 1)[0]);
        ButtonCredits.setClickedImage(loadPics("images/Button_Click.png", 1)[0]);
        ButtonCredits.setText("Credits");
        ButtonCredits.setFont(font.deriveFont(0, 30));
        ButtonCredits.setColor_Text_Color(Color.BLACK);
        Menu_Elements.add(ButtonCredits);
        //QuitButton
        QuitButton = new Button(parent, parent.getWidth() / 2 - 150, 600, 300, 120, parent);
        QuitButton.setBackroundImage(loadPics("images/Button.png", 1)[0]);
        QuitButton.setClickedImage(loadPics("images/Button_Click.png", 1)[0]);
        QuitButton.setText("Quit");
        QuitButton.setFont(font.deriveFont(0, 40));
        QuitButton.setColor_Text_Color(Color.BLACK);
        Menu_Elements.add(QuitButton);
    }
    
    public static Vector<GUIElements> getMenu(){
        return Menu_Elements;
    }

    public static Button getButtonCredits() {
        return ButtonCredits;
    }

    public static Button getButtonPlay() {
        return ButtonPlay;
    }

    public static Button getQuitButton() {
        return QuitButton;
    }

    public static void setFont(Font font) {
        MainMenu.font = font;
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
}
