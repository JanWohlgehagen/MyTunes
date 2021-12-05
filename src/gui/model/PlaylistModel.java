package gui.model;


import be.Playlist;
import be.Song;
import bll.PlaylistManager;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.List;


public class PlaylistModel {

    private PlaylistManager playlistManager;

    private ObservableList<SongModel> totalSongs;
    private StringProperty name = new SimpleStringProperty();
    private StringProperty time = new SimpleStringProperty();
    private IntegerProperty id = new SimpleIntegerProperty();
    private Playlist playlist;

    public PlaylistModel(Playlist playlist) throws IOException {
        this.playlist = playlist;
        this.getIdProperty().set(playlist.getId());
        this.getNameProperty().set(playlist.getName());
        this.getTotalTimeProperty().set(playlist.getDurationString());
        this.totalSongs = FXCollections.observableArrayList();

        playlistManager = new PlaylistManager();
    }



    public StringProperty getNameProperty() {
        return name;
    }

    public int getTotalTime(){return  1;}

    public String getDurationString(){return  null;}


    public IntegerProperty getTotalSongsProperty() {
        IntegerProperty size = new SimpleIntegerProperty();
        size.set(totalSongs.size());
        return size;
    }

    public StringProperty getTotalTimeProperty() {
        return time;
    }

    public IntegerProperty getIdProperty() {
        return id;
    }

    public void addSongToPlayList(List<Song> songs) {

    }
}
