package gui.util;

import gui.App;
import gui.controller.MainController;
import gui.model.SongModel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.util.*;

import static be.DisplayMessage.displayError;

/**
 * Class SongPlayer plays a list of songs, and holds the logic for playing, pausing and navigating through a playlist.
 */
public class SongPlayer {
    private MediaPlayer mediaPlayer;
    private int index;
    private List<SongModel> songModels;
    private Duration snapshot;
    private int id = 0;
    MainController mainController;
    private List<SongModel> songsToBePlayed = new ArrayList<>();

    /**
     * constructor for a song takes a list of SongModels
     *
     * @param songModels gets a list of SongModel and an id
     */
    public SongPlayer(List<SongModel> songModels, int id) {
        this.songModels = songModels;
        this.songsToBePlayed = songModels;
        this.id = id;
        mainController = new App().getController();
        snapshot = new Duration(0.0);
        initializeSong();
    }

    /**
     * Plays a song
     * <p>
     * checks for if the song is the same song
     * checks if the btntoggleshuffle is selected.
     *
     * @param index revices the index of the song, than is wanted to be played.
     */
    public void play(int index) {
        try {
            if (index != this.index) {
                setIndex(index);
                initializeSong();
            } else {
                mediaPlayer.setStartTime(snapshot);
            }

            mainController.updateIsPlayingLabel(songsToBePlayed.get(index).getTitleProperty().get());
            mediaPlayer.play();
        } catch (Exception ex) {

            ex.fillInStackTrace();
            displayError(ex);
        }
    }

    /**
     * pause the music and takes a snapshot of the time where the Media Player was.
     */
    public void pause() {
        snapshot = mediaPlayer.getCurrentTime();
        mediaPlayer.pause();
    }

    /**
     * goes one song forward
     * by using the set index to go one song forward. if possible.
     */
    public void nextSong() {
        mediaPlayer.stop();
        setIndex(index + 1);
        snapshot = new Duration(0.0);
        initializeSong();
        play(getIndex());

    }

    /**
     * goes one song back.
     * by using the set index to go one song backwards, if possible.
     */
    public void previousSong() {
        mediaPlayer.stop();
        setIndex(index - 1);
        snapshot = new Duration(0.0);
        initializeSong();
        play(getIndex());
    }

    /**
     * shuffles all the songs into a ShuffledSongs List.
     */
    public void shufflePlaylist() {
        Collections.shuffle(songsToBePlayed);
    }

    /**
     * sets the playlist to the unruffled list
     */
    public void unshufflePlaylist() {
        songsToBePlayed = songModels;
    }


    /**
     * set the volume of the song
     *
     * @param volume the double volume of the song needs to between 0.0 to 1.0.
     */
    public void setVolume(Double volume) {
        if (volume == 0.00) mediaPlayer.setMute(true);
        else {
            mediaPlayer.setMute(false);
            mediaPlayer.setVolume(volume / 100);
        }
    }

    /**
     * pauses the Song / Media Player.
     * used for when user have started a drag the music will pause.
     */
    public void barDragStart() {
        mediaPlayer.pause();
    }

    /**
     * start the music at specific time in the song.
     * uses for when user end a drag event we will resume the Media Player where the user dropped slider.
     */
    public void barDragEnd() {
        mediaPlayer.seek(Duration.seconds((mainController.getProgBarValue() / 100) * mediaPlayer.getTotalDuration().toSeconds()));
        mediaPlayer.play();
    }

    /**
     * we initialize the a song ready to be played. we also use a lambda to check for when the song is finished playing
     */
    private void initializeSong() {
        try {

            String path = songsToBePlayed.get(index).getPathToFileProperty().get();
            File file = new File(path);
            String MEDIA_URL = file.toURI().toString();
            Media song = new Media(MEDIA_URL);
            mediaPlayer = new MediaPlayer(song);

            mediaPlayer.setOnEndOfMedia(() -> {
                nextSong();
                mainController.updateProgBar();
                mainController.updateSelection();
                mainController.setSongVolume();
            });
        } catch (Exception ex) {
            displayError(ex);
        }
    }

    /**
     * to access the media Player from Main controller.
     *
     * @return the mediaplayer we are currently using.
     */
    public MediaPlayer getMediaPlayer() {
        return this.mediaPlayer;
    }

    /**
     * @param index, gets an index for the song
     *               checks wether its possible to place the index.
     * @return returns true if the setindex passes otherwise it returns false and set the index to 0.
     * first song in.
     */
    public boolean setIndex(int index) {
        if (index >= 0 && index < songModels.size()) {
            this.index = index;
            return true;
        } else setIndex(0);
        return false;
    }

    /**
     * @return the int index of the song, what position the song has on the tableview.
     */
    public int getIndex() {
        return index;
    }

    /**
     * @return SongModel Instance of the song we are currently playing
     */
    public SongModel getSongModel() {
        return songModels.get(index);
    }

    /**
     * @return the id.
     */
    public int getId() {
        return id;
    }
}
