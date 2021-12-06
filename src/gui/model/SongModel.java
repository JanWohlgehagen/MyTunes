package gui.model;

import be.Song;
import bll.SongManager;
import dal.DALException;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.List;

public class SongModel {

    private StringProperty title = new SimpleStringProperty();
    private StringProperty artist = new SimpleStringProperty();
    private StringProperty genre = new SimpleStringProperty();
    private StringProperty duration = new SimpleStringProperty();
    private  IntegerProperty id = new SimpleIntegerProperty();
    private SongManager songManager;



    public SongModel(Song song) {
        this.getIdProperty().set(song.getId());
        this.getTitleProperty().set(song.getTitle());
        this.getArtistProperty().set(song.getArtist());
        this.getGenreProperty().set(song.getGenre());
        this.getDurationProperty().set(song.getDurationString());

    }

    public SongModel() throws DALException, IOException {
        songManager = new SongManager();
    }

    public ObservableList<SongModel> convertSongToSongmodel(List<Song> songs){
        return FXCollections.observableArrayList(songs.stream().map(song -> new SongModel(song)).toList());
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
}
