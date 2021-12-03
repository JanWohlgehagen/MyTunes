package be;


import bll.SongManager;
import dal.DALException;
import gui.model.ListModel;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.IOException;

public class Song {

    private StringProperty title = new SimpleStringProperty();
    private StringProperty artist = new SimpleStringProperty();
    private StringProperty genre = new SimpleStringProperty();
    private IntegerProperty duration = new SimpleIntegerProperty();
    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty pathToFile = new SimpleStringProperty();

    private SongManager songManager;
    private ListModel listModel;


    public Song(int id, String title, String artist, String genre, int duration, String pathToFile){
        this.getIdProperty().set(id);
        this.getTitleProperty().set(title);
        this.getArtistProperty().set(artist);
        this.getGenreProperty().set(genre);
        this.getDurationProperty().set(duration);
        this.getPathToFileProperty().set(pathToFile);


    }

    public Song() throws DALException, IOException {
        songManager = new SongManager();
        listModel = new ListModel();
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

    public IntegerProperty getDurationProperty() {
        return duration;
    }

    public IntegerProperty getIdProperty() {
        return id;
    }

    public StringProperty getPathToFileProperty() {
        return pathToFile;
    }

    public StringProperty getDurationStringProperty(){
        StringProperty time = new SimpleStringProperty();

        int minutes = duration.get() / 60; // divide by 60 to get the minutes from seconds.
        int seconds = duration.get() % 60; // remaining seconds
        time.set(minutes + ":" + seconds);

        return time;
    }

    public void createSong(String title, String artist, String genre, int duration, String pathToFile) throws DALException, IOException {
        listModel.addSongToView(songManager.createSong(title, artist, genre, duration, pathToFile));
    }
}
