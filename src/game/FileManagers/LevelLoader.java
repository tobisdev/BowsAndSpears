package game.FileManagers;
import game.Main.Game;
import game.Objects.Obstacle;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Vector;

// --Level Loader loads levels from .dat Files
public class LevelLoader {
    private int x = 0;
    private int y = 0;
    private int delay = 0;
    private int obs_ID = 0;
    private boolean collision = false;
    private boolean valid = false;

    private Game parent;
    private final BufferedImage[][] imgs;
    private final Vector<Obstacle> obs = new Vector<Obstacle>();

    public LevelLoader(BufferedImage[][] i){
        imgs = i;
    }

    // --Loading Level from File
    public void Load(String Level, Game pr){
        try{
            BufferedReader Br = new BufferedReader(new FileReader(Level));

            parent = pr;
            String line = Br.readLine();
            while(line!=null){
                String[] Split = line.split(" ");

                for (int i = 0; i < Split.length; i++){
                    valid = true;
                    switch (Split[i]){
                        case "[:x:]": x = Integer.parseInt(Split[i + 1]); i++; break;
                        case "[:y:]": y = Integer.parseInt(Split[i + 1]); i++; break;
                        case "[:d:]": delay = Integer.parseInt(Split[i + 1]); i++; break;
                        case "[:i:]": obs_ID = Integer.parseInt(Split[i + 1]); i++; break;
                        case "[:c:]": collision = Integer.parseInt(Split[i + 1]) > 0; i++; break;
                        default: i = Split.length; valid = false; break;
                    }
                }
                if(valid){
                    obs.add(new Obstacle(imgs[obs_ID], x, y, delay, pr, collision));
                }
                line = Br.readLine();
            }
            Br.close();
        }catch (IOException e){
            System.out.println("Fehler beim Lesen der Datei: " + e);
        }
    }


    // --Sending Level Array
    public Vector<Obstacle> getObstacles() {
        return obs;
    }
}