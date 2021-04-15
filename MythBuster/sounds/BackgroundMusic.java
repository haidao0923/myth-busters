package sounds;

import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;

import java.io.File;

public class BackgroundMusic {
    /**
     * Credit
     * 
     * Escape by Admiral Bob (c) copyright 2021 
     * Licensed under a Creative Commons Attribution Noncommercial (3.0) license. 
     * http://dig.ccmixter.org/files/admiralbob77/63310 
     * Ft: copperhead, 7OOP3D, Martijn de Boer (NiGiD)
     */

    private static MediaPlayer track;

    public static void initialize() {
        String soundPath = "MythBuster/sounds/BackgroundMusic.mp3";
        //Instantiating Media class  
        Media media = new Media(new File(soundPath).toURI().toString());  
        //Instantiating MediaPlayer class   
        track = new MediaPlayer(media);
        track.setCycleCount(MediaPlayer.INDEFINITE);
        track.setVolume(.125);
    }

    public static MediaPlayer getTrack() {
        return track;
    }
}
