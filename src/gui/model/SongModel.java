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
    private Song song;
    private SongManager songManager;
    private ListModel listModel;


    public SongModel(Song song) {
        this.song = song;
    }

    public SongModel() throws DALException, IOException {
        songManager = new SongManager();
        listModel = new ListModel();
    }

    public StringProperty getTitleProperty() {
        return song.getTitleProperty();
    }

    public StringProperty getArtistProperty() {
        return song.getArtistProperty();
    }

    public StringProperty getGenreProperty() {
        return song.getGenreProperty();
    }

    public StringProperty getDurationProperty() {
        return song.getDurationStringProperty();
    }

    public IntegerProperty getIdProperty() {
        return song.getIdProperty();
    }

    public void createSong(String title, String artist, String genre, int duration, String pathToFile) throws DALException, IOException {
        listModel.addSongToView(songManager.createSong(title, artist, genre, duration, pathToFile));
    }
}
