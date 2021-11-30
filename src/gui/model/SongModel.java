package gui.model;

import be.Song;
import bll.SongManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.Duration;
import java.util.List;

public class SongModel {

    private StringProperty title = new SimpleStringProperty();
    private StringProperty artist = new SimpleStringProperty();
    private StringProperty genre = new SimpleStringProperty();
    private StringProperty time = new SimpleStringProperty();


    public SongModel(String title, String artist, String genre, String duration){
        this.getTitleProperty().set(title);
        this.getArtistProperty().set(artist);
        this.getGenreProperty().set(genre);
        this.getTimeProperty().set(duration);
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

    public StringProperty getTimeProperty() {
        return time;
    }

}
