package gui.model;


import be.Playlist;
import bll.PlaylistManager;
import dal.DALException;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;



public class PlaylistModel {

    private SongModel songModel = new SongModel();

    private ObservableList<SongModel> allSongs = FXCollections.observableArrayList();
    private StringProperty name = new SimpleStringProperty();
    private StringProperty time = new SimpleStringProperty();
    private IntegerProperty id = new SimpleIntegerProperty();
    private IntegerProperty totalSongs = new SimpleIntegerProperty();

    public PlaylistModel(Playlist playlist) throws IOException, DALException {
        this.getIdProperty().set(playlist.getId());
        this.getNameProperty().set(playlist.getName());
        this.getTotalTimeProperty().set(playlist.getDurationString());
        this.allSongs.addAll(songModel.convertSongToSongmodel(playlist.getSongList()));
        this.getTotalSongsProperty().set(allSongs.size());

    }

    public StringProperty getNameProperty() {
        return name;
    }

    public ObservableList<SongModel> getListOfSongs(){
        return allSongs;
    }

    public int getTotalTime(){return  1;}

    public String getDurationString(){return  null;}


    public IntegerProperty getTotalSongsProperty() {
        return totalSongs;
    }

    public StringProperty getTotalTimeProperty() {
        return time;
    }

    public IntegerProperty getIdProperty() {
        return id;
    }


    public void addSongToPlayList(SongModel song) {
        allSongs.add(song);
    }
}
