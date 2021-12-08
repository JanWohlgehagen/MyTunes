package gui.util;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public class SongPlayer {
    MediaPlayer mediaPlayer;

    /**
     * constructor for a song  creates a playable song
     * @param songUrl gets the file location off song
     */
    public SongPlayer(String songUrl){

        String path = songUrl.toString();
        //"c:/Users/Magnus Overgaard/Downloads/bip.mp3"
         File file = new File(path);
        String MEDIA_URL = file.toURI().toString();
        Media song = new Media(MEDIA_URL);
        MediaPlayer mediaPlayer = new MediaPlayer(song);
        this.mediaPlayer = mediaPlayer;
    }

    /**
     * simply plays the song :D
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
     * returns the volume the song is currently played on.
     * @return
     */
    public Double getVolume(){
       return mediaPlayer.getVolume();
    }

    /**
     * pause the music
     */
    public void pauseMusic(){
        mediaPlayer.pause();
    }

    public boolean isPlaying(){
        return mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING);
    }

    public MediaPlayer getMediaPlayer(){
    return this.mediaPlayer;
    }

    public void unPause(Duration time){
        mediaPlayer.setStartTime(time);
        mediaPlayer.play();

    }

}
