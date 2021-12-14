package gui.model;

import be.Playlist;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import static be.DisplayMessage.displayMessage;

public class PlaylistModel {

    private SongModel songModel = new SongModel();

    private ObservableList<SongModel> allSongs = FXCollections.observableArrayList();
    private StringProperty name = new SimpleStringProperty();
    private DoubleProperty duration = new SimpleDoubleProperty();
    private IntegerProperty id = new SimpleIntegerProperty();
    private IntegerProperty totalSongs = new SimpleIntegerProperty();
    private StringProperty durationString = new SimpleStringProperty();


    public PlaylistModel(Playlist playlist) {
        this.getIdProperty().set(playlist.getId());
        this.getNameProperty().set(playlist.getName());
        this.getDurationProperty().set(getTotalTime());
        this.allSongs.addAll(songModel.convertSongToSongmodel(playlist.getSongList())); // adds all the songs to the list allSongs in a playlist.
        this.getTotalSongsProperty().set(allSongs.size());
        this.getDurationStringProperty().set(getDurationString());

    }

    public StringProperty getDurationStringProperty() {
        return durationString;
    }

    public StringProperty getNameProperty() {
        return name;
    }

    public void setNameProperty(String name) {
        getNameProperty().set(name);
    }

    public ObservableList<SongModel> getListOfSongs() {
        return allSongs;
    }

    public IntegerProperty getTotalSongsProperty() {
        return totalSongs;
    }

    public DoubleProperty getDurationProperty() {
        return duration;
    }

    public IntegerProperty getIdProperty() {
        return id;
    }


    /**
     *  ascend a song in a playlist.
     * @param selectedSongModel get a songmodel object.
     */
    public void AscendSongInPlaylist(SongModel selectedSongModel) {
        int indexOfSelectedSongModel = allSongs.indexOf(selectedSongModel);
        if (indexOfSelectedSongModel > 0) {
            int indexOfAboveSelectedSong = allSongs.indexOf(selectedSongModel) - 1;
            SongModel songModelOfAboveSelectedSong = allSongs.get(indexOfAboveSelectedSong);

            allSongs.set(indexOfAboveSelectedSong, selectedSongModel);
            allSongs.set(indexOfSelectedSongModel, songModelOfAboveSelectedSong);
        } else displayMessage("It is at the top");

    }

    /**
     * Descend a Song in a Playlist.
     * @param selectedSongModel gets a songmodel object.
     */
    public void DescendSongInPlaylist(SongModel selectedSongModel) {
        int indexOfSelectedSongModel = allSongs.indexOf(selectedSongModel);
        if (indexOfSelectedSongModel < allSongs.size() - 1) {
            int indexOfAboveSelectedSong = allSongs.indexOf(selectedSongModel) + 1;
            SongModel songModelOfAboveSelectedSong = allSongs.get(indexOfAboveSelectedSong);

            allSongs.set(indexOfAboveSelectedSong, selectedSongModel);
            allSongs.set(indexOfSelectedSongModel, songModelOfAboveSelectedSong);
        } else displayMessage("It is at the bottom");
    }

    /**
     * removes a song from Playlist.
     * @param songModel a songmodel object.
     */
    public void removeSongFromPlaylist(SongModel songModel) {
        allSongs.remove(songModel);
        getTotalSongsProperty().set(allSongs.size());
        getDurationStringProperty().set(getDurationString());
    }

    /**
     * add a song to a playlist.
     * @param song the object of a song
     */
    public void addSongToPlayList(SongModel song) {
        allSongs.add(song);
        getTotalSongsProperty().set(allSongs.size());
        getDurationStringProperty().set(getDurationString());
    }

    /**
     * converts total time into a presentable string.
     * @return a String with the minutes and seconds,
     */
    public String getDurationString() {
        double totalTime = getTotalTime();
        int timeAsIntegerInSeconds = (int) totalTime / 1000;
        int minutes = timeAsIntegerInSeconds / 60; // divide by 60 to get the minutes from seconds.
        int seconds = timeAsIntegerInSeconds % 60; // remaining seconds
        return minutes + ":" + seconds;
    }

    /**
     * gets the total time of all songs in a playlist.
     * @return the total time of all songs in a playlist. as double.
     */
    public double getTotalTime() {
        double totalTime = 0;
        for (SongModel songModel : allSongs) {
            totalTime += songModel.getDurationProperty().get();
        }
        return totalTime;
    }

    /**
     *  converts a playlistmodel to a playlist.
     * @return a new playlist.
     */
    public Playlist convertToPlaylist() {
        return new Playlist(this.getIdProperty().get(), this.getNameProperty().get());
    }
}
