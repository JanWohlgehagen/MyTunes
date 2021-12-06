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
    private StringProperty filePath = new SimpleStringProperty();
    private IntegerProperty duration = new SimpleIntegerProperty();
    private IntegerProperty id = new SimpleIntegerProperty();
    private SongManager songManager;
    private SongListModel songListModel;



    public SongModel(int id, String title, String artist, String genre, int duration, String filePath){
        this.getTitleProperty().set(title);
        this.getArtistProperty().set(artist);
        this.getGenreProperty().set(genre);
        this.getTimeProperty().set(duration);
        this.getIdProperty().set(id);
        this.getFilePathProperty().set(filePath);
    }

    public SongModel() throws DALException, IOException {
        songManager = new SongManager();
        songListModel = new SongListModel();
    }

    public StringProperty getTitleProperty() {
        return title;
    }

    public StringProperty getFilePathProperty() {
        return filePath;
    }

    public StringProperty getArtistProperty() {
        return artist;
    }

    public StringProperty getGenreProperty() {
        return genre;
    }

    public IntegerProperty getTimeProperty() {
        return duration;
    }

    public IntegerProperty getIdProperty() {
        return id;
    }

    public void createSong(String title, String artist, String genre, int duration, String pathToFile) throws DALException, IOException {
        songListModel.addSongToView(title, artist, genre, duration, pathToFile);
    }

    public void updateSong(Song song) throws DALException{
        songListModel.updateSong(song);
    }

    public void deleteSong(Song song) throws DALException{
        songListModel.deleteSong(song);
        songManager.deleteSong(song);
    }

    public Song convertToSong(){
        return new Song(this.getIdProperty().get(),this.getTitleProperty().get(), this.getTitleProperty().get(),
                this.getGenreProperty().get(), this.getTimeProperty().get(), this.getFilePathProperty().get());
    }
}
