package game.soundLib;
import javax.sound.sampled.AudioFileFormat;
import java.applet.*;
import java.net.*;
import java.util.*;

public class soundLib {
    Hashtable<String, AudioClip> sounds;
    Vector<AudioClip> loopingClips;

    public soundLib(){
        sounds = new Hashtable<String,AudioClip>();
        loopingClips = new Vector<AudioClip>();
    }

    public void loadSound(String name, String path) {
        if (sounds.containsKey(name)) {
            return;
        }

        URL sound_url = getClass().getClassLoader().getResource(path);
        System.out.println(path);
       sounds.put(name, Applet.newAudioClip(sound_url));
    }

    public void playSound(String name){
        AudioClip audio = sounds.get(name);
        loopingClips.add(audio);
        audio.loop();
    }


    public void loopSound(String name){
        AudioClip audio = sounds.get(name);
        loopingClips.add(audio);
        audio.loop();
    }

    public void stopLoopingSound(){
        for(AudioClip c:loopingClips){
            c.stop();
        }
    }
}
