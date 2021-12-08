package gui.model;

import be.Playlist;
import be.Song;
import bll.PlaylistManager;
import dal.DALException;

import be.Playlist;
import dal.DALException;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;

import java.io.IOException;

import static be.DisplayMessage.displayMessage;


public class PlaylistModel {

    private SongModel songModel = new SongModel();

    private ObservableList<SongModel> allSongs = FXCollections.observableArrayList();
    private StringProperty name = new SimpleStringProperty();
    private IntegerProperty duration = new SimpleIntegerProperty();
    private IntegerProperty id = new SimpleIntegerProperty();
    private IntegerProperty totalSongs = new SimpleIntegerProperty();
    private StringProperty durationString = new SimpleStringProperty();


    public PlaylistModel(Playlist playlist) throws IOException, DALException {
        this.getIdProperty().set(playlist.getId());
        this.getNameProperty().set(playlist.getName());
        this.getDurationProperty().set(getTotalTime());
        this.allSongs.addAll(songModel.convertSongToSongmodel(playlist.getSongList()));
        this.getTotalSongsProperty().set(allSongs.size());
        this.getDurationStringProperty().set(getDurationString());

    }

    public StringProperty getDurationStringProperty() {
        return durationString;
    }


    public StringProperty getNameProperty() {
        return name;
    }

    public ObservableList<SongModel> getListOfSongs(){
        return allSongs;
    }

    public void AscendSongInPlaylist(SongModel selectedSongModel){
        int indexOfSelectedSongModel = allSongs.indexOf(selectedSongModel);
        if(indexOfSelectedSongModel > 0 ){
            int indexOfAboveSelectedSong = allSongs.indexOf(selectedSongModel) - 1;
            SongModel songModelOfAboveSelectedSong = allSongs.get(indexOfAboveSelectedSong);

            allSongs.set(indexOfAboveSelectedSong, selectedSongModel);
            allSongs.set(indexOfSelectedSongModel, songModelOfAboveSelectedSong);
        }else displayMessage("It is at the top");

    }

    public void DescendSongInPlaylist(SongModel selectedSongModel){
        int indexOfSelectedSongModel = allSongs.indexOf(selectedSongModel);
        if(indexOfSelectedSongModel < allSongs.size() - 1) {
            int indexOfAboveSelectedSong = allSongs.indexOf(selectedSongModel) + 1;
            SongModel songModelOfAboveSelectedSong = allSongs.get(indexOfAboveSelectedSong);

            allSongs.set(indexOfAboveSelectedSong, selectedSongModel);
            allSongs.set(indexOfSelectedSongModel, songModelOfAboveSelectedSong);
        }else displayMessage("It is at the bottom");
    }

    public IntegerProperty getTotalSongsProperty() {
        return totalSongs;
    }

    public IntegerProperty getDurationProperty() {
        return duration;
    }

    public IntegerProperty getIdProperty() {
        return id;
    }

    public void removeSongFromList(SongModel song){
        allSongs.remove(song);
    }

    public void addSongToPlayList(SongModel song) {
        allSongs.add(song);
        getTotalSongsProperty().set(allSongs.size());
        getDurationStringProperty().set(getDurationString());
    }

    public String getDurationString(){
        int totalTime = getTotalTime();
        int minutes = totalTime / 60; // divide by 60 to get the minutes from seconds.
        int seconds = totalTime % 60; // remaining seconds
        return minutes + ":" + seconds;
    }

    public int getTotalTime(){
        int totalTime = 0;
        for (SongModel songModel: allSongs) {
            totalTime += songModel.getDurationProperty().get();
        }
        return totalTime;
    }

    public void updatePlaylist(PlaylistModel playlistModel, String newName) {
        playlistModel.setNameProperty(newName);
    }

    public void setNameProperty(String name) {
        getNameProperty().set(name);
    }

    public Playlist convertToPlaylist() {
        return new Playlist(this.getIdProperty().get(), this.getNameProperty().get());
    }

}
