package game.Overlays;

import game.CustomGUI.Button;
import game.CustomGUI.GUIElements;
import game.Main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Vector;

public class DifficultySelection {
    private static final Vector<GUIElements> Menu_Elements = new Vector<GUIElements>();
    private static Font font;
    private static Game parent;
    private static Button ButtonEasy;
    private static Button ButtonMedium;
    private static Button ButtonHard;
    private static Button ButtonImpossible;
    private static Button ButtonBack;

    public DifficultySelection (Game parent){
        DifficultySelection.parent = parent;
    }

    public void CreateMenu(){
        // Difficulty Menu
        // Button Easy
        ButtonEasy = new game.CustomGUI.Button(parent, parent.getWidth() / 2 - 150, 200, 300, 120, parent);
        ButtonEasy.setBackroundImage(loadPics("images/Button.png", 1)[0]);
        ButtonEasy.setClickedImage(loadPics("images/Button_Click.png", 1)[0]);
        ButtonEasy.setText("Easy");
        ButtonEasy.setFont(font.deriveFont(0, 40));
        ButtonEasy.setColor_Text_Color(Color.BLACK);
        Menu_Elements.add(ButtonEasy);
        // Button Medium
        ButtonMedium = new game.CustomGUI.Button(parent, parent.getWidth() / 2 - 150, 300, 300, 120, parent);
        ButtonMedium.setBackroundImage(loadPics("images/Button.png", 1)[0]);
        ButtonMedium.setClickedImage(loadPics("images/Button_Click.png", 1)[0]);
        ButtonMedium.setText("Medium");
        ButtonMedium.setFont(font.deriveFont(0, 30));
        ButtonMedium.setColor_Text_Color(Color.BLACK);
        Menu_Elements.add(ButtonMedium);
        // Button Hard
        ButtonHard = new Button(parent, parent.getWidth() / 2 - 150, 400, 300, 120, parent);
        ButtonHard.setBackroundImage(loadPics("images/Button.png", 1)[0]);
        ButtonHard.setClickedImage(loadPics("images/Button_Click.png", 1)[0]);
        ButtonHard.setText("Hard");
        ButtonHard.setFont(font.deriveFont(0, 40));
        ButtonHard.setColor_Text_Color(Color.BLACK);
        Menu_Elements.add(ButtonHard);
        // Button Back
        ButtonBack = new Button(parent, parent.getWidth() / 2 - 150, 700, 300, 120, parent);
        ButtonBack.setBackroundImage(loadPics("images/Button.png", 1)[0]);
        ButtonBack.setClickedImage(loadPics("images/Button_Click.png", 1)[0]);
        ButtonBack.setText("Back");
        ButtonBack.setFont(font.deriveFont(0, 40));
        ButtonBack.setColor_Text_Color(Color.BLACK);
        Menu_Elements.add(ButtonBack);
        // Button Impossible
        ButtonImpossible = new Button(parent, parent.getWidth() / 2 - 150, 500, 300, 120, parent);
        ButtonImpossible.setBackroundImage(loadPics("images/Button.png", 1)[0]);
        ButtonImpossible.setClickedImage(loadPics("images/Button_Click.png", 1)[0]);
        ButtonImpossible.setText("Impossible");
        ButtonImpossible.setFont(font.deriveFont(0, 23));
        ButtonImpossible.setColor_Text_Color(Color.BLACK);
        Menu_Elements.add(ButtonImpossible);
    }

    public static Vector<GUIElements> getMenu(){
        return Menu_Elements;
    }

    public static void setFont(Font font) {
        DifficultySelection.font = font;
    }

    public static Button getButtonEasy() {
        return ButtonEasy;
    }

    public static Button getButtonHard() {
        return ButtonHard;
    }

    public static Button getButtonImpossible() {
        return ButtonImpossible;
    }

    public static Button getButtonMedium() {
        return ButtonMedium;
    }

    public static Button getButtonBack() {
        return ButtonBack;
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
