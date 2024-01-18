package game.CustomGUI;

import game.Main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Button extends GUIElements{
    private Font font;
    private Color Color_Text_Color;
    private BufferedImage BackroundImage;
    private BufferedImage ClickedImage;

    private String Text = "test";

    public Button(ActionListener Listener, int x, int y, int width, int height, Game parent){
        super(Listener, x, y, width, height, parent);
    }

    @Override
    public void RenderGUI(Graphics g){
        if(isEnabled()){
            if(super.getPressed()){
                g.drawImage(BackroundImage, getX(), getY(), getWidth(), getHeight(), getParent());
            }else{
                g.drawImage(ClickedImage, getX(), getY(), getWidth(), getHeight(), getParent());
            }
            g.setColor(Color_Text_Color);
            g.setFont(font);

            int Stringwidth = g.getFontMetrics().stringWidth(Text);
            g.drawString(Text, super.getX() + super.getWidth() / 2 - Stringwidth / 2, super.getY() + super.getHeight() / 2 +18);
        }
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public void setColor_Text_Color(Color color_Text_Color) {
        Color_Text_Color = color_Text_Color;
    }

    public void setBackroundImage(BufferedImage backroundImage) {
        BackroundImage = backroundImage;
    }

    public void setClickedImage(BufferedImage clickedImage) {
        ClickedImage = clickedImage;
    }
}
