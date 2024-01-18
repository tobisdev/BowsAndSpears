package game.FileManagers;

import java.io.*;

public class ScoreSaver {

    public static void saveScore(String FileName, int Score){
        try{
            BufferedWriter Bw = new BufferedWriter(new FileWriter(FileName));
            Bw.write(String.valueOf(Score));
            Bw.close();
            return;
        }catch (IOException e){
            System.out.println("Fehler beim Schreiben der Datei: " + e);
        }
    }
}