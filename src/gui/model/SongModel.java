package gui.model;

import be.Song;
import bll.SongManager;;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;

public class SongModel {

    private StringProperty title = new SimpleStringProperty();
    private StringProperty artist = new SimpleStringProperty();
    private StringProperty genre = new SimpleStringProperty();
    private StringProperty pathToFile = new SimpleStringProperty();
    private DoubleProperty duration = new SimpleDoubleProperty();
    private IntegerProperty id = new SimpleIntegerProperty();
    private SongManager songManager;


    public SongModel(Song song) {
        this.getIdProperty().set(song.getId());
        this.getTitleProperty().set(song.getTitle());
        this.getArtistProperty().set(song.getArtist());
        this.getGenreProperty().set(song.getGenre());
        this.getDurationProperty().set(song.getDuration());
        this.getPathToFileProperty().set(song.getPathToFile());
    }

    public SongModel() {
        songManager = new SongManager();
    }

    public ObservableList<SongModel> convertSongToSongmodel(List<Song> songs) {
        return FXCollections.observableArrayList(songs.stream().map(song -> new SongModel(song)).toList());
    }

    public Song convertToSong() {
        return new Song(this.getIdProperty().get(), this.getTitleProperty().get(), this.getTitleProperty().get(),
                this.getGenreProperty().get(), this.getDurationProperty().get(), this.getPathToFileProperty().get());
    }

    public StringProperty getPathToFileProperty() {
        return pathToFile;
    }

    public IntegerProperty getIdProperty() {
        return id;
    }

    public StringProperty getTitleProperty() {
        return title;
    }

    public StringProperty getArtistProperty() {
        return artist;
    }

    public StringProperty getGenreProperty() {
        return genre;
    }

    public DoubleProperty getDurationProperty() {
        return duration;
    }

    public void setTitleProperty(String title) {
        getTitleProperty().set(title);
    }

    public void setArtistProperty(String artist) {
        getArtistProperty().set(artist);
    }

    public void setGenreProperty(String genre) {
        getGenreProperty().set(genre);
    }

    public StringProperty getDurationString() {
        int timeAsIntegerInSeconds = (int) duration.get() / 1000;
        int minutes = timeAsIntegerInSeconds / 60; // divide by 60 to get the minutes from seconds.
        int seconds = timeAsIntegerInSeconds % 60; // remaining seconds
        return new SimpleStringProperty(minutes + ":" + seconds);
    }

    public String getDurationString(double DurationInSeconds) {
        int timeAsIntegerInSeconds = (int) DurationInSeconds / 1000;
        int minutes = timeAsIntegerInSeconds / 60; // divide by 60 to get the minutes from seconds.
        int seconds = timeAsIntegerInSeconds % 60; // remaining seconds
        String returnSeconds;

        if (seconds < 10)
            returnSeconds = "0" + seconds;
        else returnSeconds = String.valueOf(seconds);

        return minutes + ":" + returnSeconds;
    }
}
