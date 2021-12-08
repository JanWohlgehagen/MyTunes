package gui.util;

import be.Song;
import gui.model.SongModel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.util.List;

public class SongPlayer {
    MediaPlayer mediaPlayer;
    SongModel songModel;

    /**
     * constructor for a song  creates a playable song
     * @param songModel
     */
    public SongPlayer(SongModel songModel){
        this.songModel = songModel;
        String path = songModel.getPathToFileProperty().get();
        File file = new File(path);
        String MEDIA_URL = file.toURI().toString();
        Media song = new Media(MEDIA_URL);
        MediaPlayer mediaPlayer = new MediaPlayer(song);
        this.mediaPlayer = mediaPlayer;
    }

    public SongModel getSongModel(){
        return this.songModel;
    }

    /**
     * simply plays the song
     */
    public void playSong(){
        mediaPlayer.play();
    }

    /**
     * set the volume of the song
     * @param volume the double volume of the song needs to between 0.0 to 1.0.
     */
    public void setVolume(Double volume){
        if(volume == 0.00)mediaPlayer.setMute(true);
        else {
            mediaPlayer.setMute(false);
            mediaPlayer.setVolume(volume/100);

        }
    }

    /**
     * pause the music
     */
    public void pauseMusic(){
        mediaPlayer.pause();
    }

    public boolean isPlaying(){
        return mediaPlayer.getStatus().equals(MediaPlayer.Status.STOPPED);
    }

    public MediaPlayer getMediaPlayer(){
    return this.mediaPlayer;
    }

    public void unPause(Duration time){
        mediaPlayer.setStartTime(time);
        playSong();
    }
}
