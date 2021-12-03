package gui.model;

import be.Song;
import bll.SongManager;
import dal.DALException;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.IOException;

public class SongModel {

    private StringProperty title = new SimpleStringProperty();
    private StringProperty artist = new SimpleStringProperty();
    private StringProperty genre = new SimpleStringProperty();
    private StringProperty duration = new SimpleStringProperty();
    private  IntegerProperty id = new SimpleIntegerProperty();
    private SongManager songManager;
    private ListModel listModel;


    public SongModel(Song song) {
        this.getIdProperty().set(song.getId());
        this.getTitleProperty().set(song.getTitle());
        this.getArtistProperty().set(song.getArtist());
        this.getGenreProperty().set(song.getGenre());
        this.getDurationProperty().set(song.getDurationString());

    }

    public SongModel() throws DALException, IOException {
        songManager = new SongManager();
        listModel = new ListModel();
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

    public StringProperty getDurationProperty() {
        return duration;
    }



    public void createSong(String title, String artist, String genre, int duration, String pathToFile) throws DALException, IOException {
        listModel.addSongToView(songManager.createSong(title, artist, genre, duration, pathToFile));
    }
}
