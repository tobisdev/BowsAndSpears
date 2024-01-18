package game.Main;

// Importing other Libraries
import game.CustomGUI.ActionEvent;
import game.CustomGUI.ActionListener;
import game.CustomGUI.GUIElements;
import game.FileManagers.LevelLoader;
import game.Overlays.*;
import game.Objects.*;
import game.soundLib.*;
import game.FileManagers.*;
import game.FileManagers.SpriteLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Vector;
import java.io.*;

////
//           Copyright©           //
// David Homolja und Huber Tobias //
//           20.12.2022           //
//                                //
//        Bows and Spears         //
//                                //
////

public class Game extends JPanel implements Runnable, KeyListener, ActionListener, MouseListener {
    // Private Game Variables and Methods//

    // Time Values
    private double Time_localTime;
    private double Time_DeltaTime;
    private double FramesPerSecond;
    private double SumofFrames;
    private int numberofFrames = 1;
    private long Time_GameOverTime;

    // Game Values
    private byte GameState = 0;
    private boolean LoadedNecessities = false;

    private boolean Movement_Up = false;
    private boolean Movement_Down = false;
    private boolean Movement_Left = false;
    private boolean Movement_Right = false;
    private boolean Shooting = false;

    private int Shooting_Speed;
    private int Movement_Speed;
    private double Shoot_Timeout;
    private double Bullet_Life_Time;
    private double Cloud_Speed;

    private double points = 0;
    private int displayPoints = 0;
    private int highScore = 0;

    private int difficulty = 3;

    // Explosion Object
    private Sprite Explosion_Carrier;

    // Game Objects
    private Player Player_Character;
    private Vector<Sprite> Game_Actors;
    private Vector<Sprite> Trash;

    //Custom Fonts
    private Font UndertaleFont;

    //GUI
    private Vector<GUIElements> Menu_Elements;
    private Vector<GUIElements> Difficulty_Elements;
    private Credits game_credits;

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

    //Sound
    soundLib slib;
    private MainMenu Menu;
    DifficultySelection Difficulty;
    private SpriteLoader Sp;

    // Game Window
    public Game(int w, int h) {
        // Frame Settings
        this.setBackground(Color.black);
        JFrame frame = new JFrame("Bows and Spears");
        frame.setUndecorated(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(this);
        frame.addMouseListener(this);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((int) screenSize.getWidth() / 2 - 1536 / 2, (int) screenSize.getHeight() / 2 - 864 / 2);
        this.setPreferredSize(new Dimension(1536, 864));
        frame.add(this);
        frame.pack();
        frame.setVisible(true);

        start();
    }

    // Initialize Game Window on Run
    public static void main(String[] args) {
        new Game(1536, 864);
    }

    // Game Initialasations
    private void start() {
        // Necessities
        if (!LoadedNecessities) {
            // Sound Lib
            slib = new soundLib();
            // Sprite Lib
            Sp = new SpriteLoader();
            Sp.loadSprites();
            // Register Fonts
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            try {
                UndertaleFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/fonts/UndertaleFont.ttf")).deriveFont(10);
                ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/fonts/UndertaleFont.ttf")).deriveFont(10));
            } catch (Exception e) {
            }
            Menu = new MainMenu(this);
            Difficulty = new DifficultySelection(this);
        }

        //Setting Variables
        Shooting_Speed = 600;
        Movement_Speed = 500;
        Shoot_Timeout = 1000;
        Bullet_Life_Time = 1200;
        Cloud_Speed = 50;

        // Reset Arraylists
        Game_Actors = new Vector<Sprite>();
        Trash = new Vector<Sprite>();
        Menu_Elements = new Vector<GUIElements>();

        //Getting Images
        Player_Images = Sp.getPlayer_Images();
        Enemy_Images = Sp.getEnemy_Images();
        Heart_Image = Sp.getHeart_Image();
        Bullet_Images = Sp.getBullet_Images();
        Cloud_Image = Sp.getCloud_Image();
        Level_Images = Sp.getLevel_Images();
        Background_Image = Sp.getBackground_Image();
        Title_Image = Sp.getTitle_Image();
        Explosion_Images = Sp.getExplosion_Images();
        Player_Profile_Image = Sp.getPlayer_Profile_Image();

        // Reset points
        points = 0;

        // Load Scores
        highScore = ScoreLoader.loadScore("src/files/playerdata.txt");

        // Load Menu
        if (UndertaleFont != null) {
            MainMenu.setFont(UndertaleFont);
            DifficultySelection.setFont(UndertaleFont);
        }
        Difficulty.CreateMenu();
        Difficulty_Elements = DifficultySelection.getMenu();
        Menu.CreateMenu();
        Menu_Elements = MainMenu.getMenu();

        // Load Credit
        game_credits = new Credits(this);
        game_credits.loadCredits("src/files/Credits.txt");

        // Create Clouds
        createClouds();

        //Load Audio
        slib.loadSound("GameSong", "audio/gamesong.wav");
        slib.loadSound("MenuSong", "audio/gamesong1.wav");
        slib.loadSound("CreditSong", "audio/creditsong.wav");
        slib.stopLoopingSound();
        if (GameState == 0) {
            slib.loopSound("MenuSong");
        }
        if (GameState == 1) {
            slib.loopSound("GameSong");
        }
        if (GameState == 2) {
            slib.loopSound("CreditSong");
        }

        Player_Character = new Player(Player_Images, 400, 250, 95, this);
        Game_Actors.add(Player_Character); // Player needs to be highest Priority!!!

        LevelLoader Level = new LevelLoader(Level_Images);
        Level.Load("src/levels/l0.dat", this);

        Vector<Obstacle> Obstacles = Level.getObstacles();
        for (Obstacle obs : Obstacles) {
            Game_Actors.add(obs);
        }

        // Set Player Life
        if(difficulty == 1){
            getPlayer_Character().setLife(6);
        }
        if(difficulty == 2){
            getPlayer_Character().setLife(4);
        }
        if(difficulty == 3){
            getPlayer_Character().setLife(3);
        }
        if(difficulty == 4){
            getPlayer_Character().setLife(1);
        }

        if (!LoadedNecessities) {
            Thread localThread = new Thread(this);
            localThread.start();
            LoadedNecessities = true;
        }
    }

    // Game Loop that starts on Run
    public void run() {
        while (true) {
            // Do Stuff
            getlocalFrames();
            checkKeys();
            doLogic();
            if (GameState == 1) {
                moveObjects();
            } else {

            }
            repaint();
            try {
                Thread.sleep(0);
            } catch (Exception e) {
            }
        }
    }


    //         //
    // Methods //
    //         //

    // Movement
    private void moveObjects() {
        for (int i = 0; i < Game_Actors.size(); i++) {
            if (GameState > 0 && Time_GameOverTime == 0) {
                Game_Actors.get(i).move((long) Time_DeltaTime);
            }
        }
    }

    // Game Logic
    private void doLogic() {
        // Difficulty Menu
        if (GameState == 3) {
            if (Difficulty_Elements.size() > 0) {
                int MouseX = 0;
                int MouseY = 0;
                try {
                    MouseX = (int) this.getMousePosition().getX();
                    MouseY = (int) this.getMousePosition().getY();
                } catch (Exception e) {
                }
                for (int i = 0; i < Difficulty_Elements.size(); i++) {
                    if (GameState == 3) {
                        GUIElements Element = Difficulty_Elements.get(i);
                        Element.setPressed(Element.getX() < MouseX && Element.getX() + Element.getWidth() > MouseX && Element.getY() < MouseY && Element.getY() + Element.getHeight() > MouseY);
                    }
                }
            }
        }

        // Game Updates
        if (GameState == 1) {
            if (Time_GameOverTime == 0) {
                if(highScore <= displayPoints){
                    highScore = displayPoints;
                }

                // Spawn Enemies
                if (displayPoints % 10 == 0) {
                    for (int i = 0; i < displayPoints / (15 - difficulty * 2) + 1; i++) {
                        Game_Actors.add(new Enemy(Enemy_Images, Math.random() * (this.getWidth() - 46), -100, 500, this));
                    }
                    points++;
                }
                points += (Time_DeltaTime / (long) (1e9)) * 1;
                displayPoints = (int) points;

                // Update Player Physics
                Player_Character.Collision_Update(Game_Actors);

                // Update Sprites
                if (Game_Actors != null) {
                    for (int i = 0; i < Game_Actors.size(); i++) {
                        if (GameState > 0 && Game_Actors.get(i) != null) {
                            Sprite s = Game_Actors.get(i);
                            s.doLogic((long) Time_DeltaTime);
                            if (s instanceof Enemy) {
                                s.Collision_Update(Game_Actors);
                            }

                            // Update Bullets
                            if (s instanceof Bullet) {
                                s.Collision_Update(Game_Actors);
                                if (((Bullet) s).getCreate_Explosion()) {
                                    Explosion_Carrier = s;
                                    ((Bullet) s).setCreate_Explosion(false);
                                }
                            }

                            if (s.getRemove()) {
                                Trash.add(s);
                            }
                        }
                    }
                }

                if (Trash.size() > 0) {
                    for (Sprite s : Trash) {
                        Game_Actors.remove(s);
                    }
                }
                Trash.clear();

                // Ask to create Explosion
                if (Explosion_Carrier != null) {
                    createExplosion((int) Explosion_Carrier.getX(), (int) Explosion_Carrier.getY());
                    Explosion_Carrier = null;
                }
            }

            if (Time_GameOverTime > 0) {
                if (System.currentTimeMillis() - Time_GameOverTime > 2300) {
                    stopGame();
                }
            }
        }

        // Credits
        if (GameState == 2) {
            game_credits.updateY((long) Time_DeltaTime);
        }


        //Main Menu
        if (GameState == 0) {
            if (Menu_Elements.size() > 0) {
                int MouseX = 0;
                int MouseY = 0;
                try {
                    MouseX = (int) this.getMousePosition().getX();
                    MouseY = (int) this.getMousePosition().getY();
                } catch (Exception e) {
                }
                for (int i = 0; i < Menu_Elements.size(); i++) {
                    if (GameState == 0) {
                        GUIElements Element = Menu_Elements.get(i);
                        Element.setPressed(Element.getX() < MouseX && Element.getX() + Element.getWidth() > MouseX && Element.getY() < MouseY && Element.getY() + Element.getHeight() > MouseY);
                    }
                }
            }
        }
    }

    // Stop Game
    public void stopGame() {
        GameState = 0;
        Time_GameOverTime = 0;
        if (GameState == 0 || GameState == 2) {
            slib.stopLoopingSound();
            slib.loopSound("MenuSong");
        }
        for (int i = 0; i < Menu_Elements.size(); i++) {
            if (GameState == 0) {
                Menu_Elements.get(i).setEnabled(true);
            }
        }
        if (highScore == displayPoints) {
            ScoreSaver.saveScore("src/files/playerdata.txt", highScore);
        }
    }

    // Get Frames
    private void getlocalFrames() {
        Time_DeltaTime = System.nanoTime() - Time_localTime;
        Time_localTime = System.nanoTime();
        try {
            SumofFrames += (long) (1e9) / Time_DeltaTime;
            FramesPerSecond = SumofFrames / numberofFrames;
            numberofFrames++;

            if(numberofFrames > (long) (1e9) / Time_DeltaTime * 0.1){
                numberofFrames = 1;
                SumofFrames = 0;
            }
        } catch (Exception e) {
            FramesPerSecond = 0.0;
        }
    }

    // Draw Function
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Background
        g.drawImage(Background_Image, 0, 0, this.getWidth(), this.getHeight(), this);

        // Credits
        if (GameState == 2) {
            game_credits.drawCredits(this, (long) Time_DeltaTime, 50, g);

        }

        // Difficulty
        if (GameState == 3) {

            if (Difficulty_Elements != null) {
                for (int i = 0; i < Difficulty_Elements.size(); i++) {
                    if (GameState == 3) {
                        Difficulty_Elements.get(i).RenderGUI(g);
                    }
                }
            }
        }

        // Game Objects
        if (Game_Actors != null && GameState == 1) {
            for (int i = 0; i < Game_Actors.size(); i++) {
                if (Game_Actors.get(i) != null) {
                    if (GameState > 0) {
                        Game_Actors.get(i).drawObjects(g);
                    }
                }
            }
        }

        // Fps Counter
        g.setColor(Color.BLACK);
        g.fillRect(15, 15, 175, 32);
        g.setColor(Color.RED);
        if (UndertaleFont != null) {
            g.setFont(UndertaleFont.deriveFont(0, 20));
        }
        g.drawString("Fps: " + (long) FramesPerSecond, 20, 40);
        g.setColor(Color.white);

        // Player UI
        if (GameState == 1) {
            PlayerUI.renderPlayerUI(g, this);

            // Death Screen
            if (Time_GameOverTime > 0) {
                DeathScreen.renderDeathScreen(g, this);
            }
        }

        // Menu UI
        if (GameState == 0) {
            g.drawImage(Title_Image, this.getWidth() / 2 - 600, -120, 1200, 700, this);
            if (Menu_Elements != null) {
                for (int i = 0; i < Menu_Elements.size(); i++) {
                    if (GameState == 0) {
                        Menu_Elements.get(i).RenderGUI(g);
                    }
                }
            }
            if (UndertaleFont != null) {
                g.setFont(UndertaleFont.deriveFont(0, 20));
            }
            g.setColor(Color.WHITE);
            int Stringwidth = g.getFontMetrics().stringWidth("Choose Difficulty: ENTER");
            g.drawString("Choose Difficulty: ENTER", this.getWidth() / 2 - Stringwidth / 2, 750);
        }

    }


    // Creating Explosion
    private void createClouds() {
        for (int y = 10; y < getHeight() / 2; y += 35) {
            int x = (int) (Math.random() * getWidth());
            Cloud cloud = new Cloud(Cloud_Image, x, y, 1000, this);
            Game_Actors.add(cloud);
        }
    }

    public void createExplosion(int x, int y) {
        Explosion ex = new Explosion(Explosion_Images, x, y, 150, this);
        Game_Actors.add(ex);
    }

    // Creating Explosion
    public void createBullet(boolean Facing_Right, int x, int y, Sprite Owner) {
        Bullet Projectile = new Bullet(Bullet_Images, x, y, 100, this);
        Projectile.setOwner(Owner);
        Projectile.setFacing_Right(Facing_Right);
        Projectile.setBullet_Speed(Shooting_Speed);
        Projectile.setLifetime(Bullet_Life_Time);
        Game_Actors.add(Projectile);
    }

    // Listeners
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(Menu_Elements != null) {
            for (int i = 0; i < Menu_Elements.size(); i++) {
                Menu_Elements.get(i).mouseReleased(e);
            }
            for (int i = 0; i < Difficulty_Elements.size(); i++) {
                Difficulty_Elements.get(i).mouseReleased(e);
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void ActionPerformed(ActionEvent e) {
        if(GameState ==0) {
            if (e.getSource() == MainMenu.getButtonPlay()) {
                GameState = 1;
                difficulty = 3;
                start();
            }
            if (e.getSource() == MainMenu.getButtonCredits()) {
                slib.stopLoopingSound();
                slib.loopSound("CreditSong");
                GameState = 2;
            }
            if (e.getSource() == MainMenu.getQuitButton()) {
                System.exit(0);
            }
            return;
        }
        if (GameState == 3) {
            if (e.getSource() == DifficultySelection.getButtonBack()) {
                for (int i = 0; i < Menu_Elements.size(); i++) {
                    Menu_Elements.get(i).setEnabled(true);
                }
                for (int i = 0; i < Difficulty_Elements.size(); i++) {
                    Difficulty_Elements.get(i).setEnabled(false);
                }
                GameState = 0;
            }
            if (e.getSource() == DifficultySelection.getButtonEasy()) {
                GameState = 1;
                difficulty = 1;
                start();
            }
            if (e.getSource() == DifficultySelection.getButtonMedium()) {
                GameState = 1;
                difficulty = 2;
                start();
            }
            if (e.getSource() == DifficultySelection.getButtonHard()) {
                GameState = 1;
                difficulty = 3;
                start();
            }
            if (e.getSource() == DifficultySelection.getButtonImpossible()) {
                GameState = 1;
                difficulty = 4;
                start();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
            Movement_Up = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
            Movement_Down = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
            Movement_Left = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
            Movement_Right = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_Z || e.getKeyCode() == KeyEvent.VK_Q || e.getKeyCode() == KeyEvent.VK_SPACE) {
            Shooting = true;
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
            Movement_Up = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
            Movement_Down = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
            Movement_Left = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
            Movement_Right = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if (GameState > 0) {
                stopGame();
                GameState = 0;
            } else {
                GameState = 0;
                System.exit(0);
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if(GameState == 3){
                GameState = 1;
                difficulty = 3;
                start();
            }
            if(GameState == 0) {
                GameState = 3;
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_Z || e.getKeyCode() == KeyEvent.VK_Q  || e.getKeyCode() == KeyEvent.VK_SPACE){
            Shooting = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_P){
            Shooting_Speed = 1000;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    // Check Key input and control Movement -Sending movement Commands to Player
    private void checkKeys() {
        if (Movement_Up) {
            Player_Character.setJump_Force(Movement_Speed / 2);
        }
        if (Movement_Down) {
            Player_Character.setJump_Force(-1 * Movement_Speed / 2);
        }
        if (Movement_Right) {
            Player_Character.setHorizontalSpeed(Movement_Speed);
        }
        if (Movement_Left) {
            Player_Character.setHorizontalSpeed(-Movement_Speed);
        }
        if (!Movement_Up && !Movement_Down) {
            Player_Character.setVerticalSpeed(0);
        }
        if (!Movement_Left && !Movement_Right) {
            Player_Character.setHorizontalSpeed(0);
        }
        if (Shooting) {
            Player_Character.shoot(Shoot_Timeout);
        }
    }


    //                   //
    // Getter und Setter //
    //                   //

    public Player getPlayer_Character() {
        return Player_Character;
    }
    public int getMovement_Speed() {
        return Movement_Speed;
    }
    public double getShoot_Timeout() {
        return Shoot_Timeout;
    }
    public void setTime_GameOverTime(long time_GameOverTime) {
        Time_GameOverTime = time_GameOverTime;
    }
    public long getTime_GameOverTime() {
        return Time_GameOverTime;
    }
    public double getCloud_Speed() {
        return Cloud_Speed;
    }
    public Font getUndertaleFont() {
        return UndertaleFont;
    }
    public double getPoints() {
        return points;
    }
    public void setPoints(double points) {
        this.points = points;
    }
    public void setGameState(byte gameState) {
        GameState = gameState;
    }
    public soundLib getSlib() {
        return slib;
    }
    public BufferedImage getHeart_Image() {
        return Heart_Image;
    }
    public int getHighScore() {
        return highScore;
    }
    public int getDisplayPoints() {
        return displayPoints;
    }
    public BufferedImage getPlayer_Profile_Image() {
        return Player_Profile_Image;
    }
    public int getDifficulty() {
        return difficulty;
    }
}