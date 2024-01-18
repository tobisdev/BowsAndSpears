package game.FileManagers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ScoreLoader {

    public static int loadScore(String FileName){
        try{
            BufferedReader Br = new BufferedReader(new FileReader(FileName));
            String line = Br.readLine();
            if (line == null){
                Br.close();
                return 0;
            }else{
                Br.close();
                if(line != ""){
                    return Integer.parseInt(line);
                }
                return 0;
            }
        }catch (IOException e){
            System.out.println("Fehler beim Lesen der Datei: " + e);
        }
        return 0;
    }
}
