package gui.util;
import gui.App;
import gui.controller.MainController;
import gui.model.SongModel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.util.List;

public class SongPlayer {
    private MediaPlayer mediaPlayer;
    private int index;
    private List <SongModel> songModels;
    private Duration snapshot;
    private int id = -1;
    MainController mainController;

    /**
     * constructor for a song  creates a playable song
     * @param songModels
     */
    public SongPlayer(List<SongModel> songModels, int id){
        this.songModels = songModels;
        this.id = id;
        mainController = new App().getController();
        snapshot = new Duration(0.0);
        initializeSong();
    }

    public void play(int index){
        if(index != this.index){
            setIndex(index);
            initializeSong();
            System.out.println("Du har intialiseret en ny sang.");
        } else{
            System.out.println("Du har ramt et else statement. Tide er: " + snapshot.toSeconds());
            mediaPlayer.setStartTime(snapshot);
        }

        mainController.updateIsPlayingLabel(songModels.get(index).getTitleProperty().get());
        mediaPlayer.play();
    }

    public void pause(){
        snapshot = mediaPlayer.getCurrentTime();
        mediaPlayer.pause();
        System.out.println(snapshot.toSeconds());
    }

    public void nextSong(){
        mediaPlayer.stop();
        setIndex(index+1);
        snapshot = new Duration(0.0);
        initializeSong();
        play(getIndex());

    }

    public void barDragStart(){
        mediaPlayer.pause();
    }

    public void barDragEnd(){
        mediaPlayer.seek(Duration.seconds((mainController.getProgBarValue() / 100) * mediaPlayer.getTotalDuration().toSeconds()));
        mediaPlayer.play();
    }

    public void previousSong(){
        mediaPlayer.stop();
        setIndex(index-1);
        snapshot = new Duration(0.0);
        initializeSong();
        play(getIndex());
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

    private void initializeSong() {
        String path = songModels.get(index).getPathToFileProperty().get();
        File file = new File(path);
        String MEDIA_URL = file.toURI().toString();
        Media song = new Media(MEDIA_URL);
        mediaPlayer = new MediaPlayer(song);

        mediaPlayer.setOnEndOfMedia(() ->{
            nextSong();
            mainController.updateProgBar();
            mainController.updateSelection();
        });
    }

    public MediaPlayer getMediaPlayer(){
        return this.mediaPlayer;
    }

    public boolean setIndex(int index){
        if (index >= 0 && index < songModels.size()){
            this.index = index;
            return  true;
        }
        else setIndex(0);
        return false;
    }

    public int getIndex(){
        return index;
    }

    public SongModel getSongModel() {
       return songModels.get(index);
    }

    public int getId() {
        return id;
    }
}
