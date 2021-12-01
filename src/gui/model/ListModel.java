package gui.model;

import bll.SongManager;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class ListModel {

    private SongManager songManager;

    private ObservableList<SongModel> songsToBeViewed;
    private ObservableList<PlaylistModel> playListToBeViewed;
    private ObservableList<PlayListSongModel> playSongsToBeViewed;

    private ObjectProperty<PlaylistModel> selectedPlayList = new SimpleObjectProperty<>();


    public ListModel() {
        songManager = new SongManager();
    }

    public ObjectProperty<PlaylistModel> getSelectedPlayList(){
        return selectedPlayList;
    }

    public ObservableList<PlaylistModel> getPlayLists(){
        playListToBeViewed = FXCollections.observableArrayList(songManager.getAllPlayLists().stream().map(playList ->
                new PlaylistModel(playList.getId(), playList.getName(), playList.getSongList().size(), String.valueOf(playList.getTotalTime()))).toList());
        return playListToBeViewed;
    }

    public ObservableList<PlayListSongModel> getPlayListSongs(){
        playSongsToBeViewed = FXCollections.observableArrayList(songManager.getPlayListSongs(selectedPlayList.get().getIdProperty().get()).stream().map(playListSongs ->
                new PlayListSongModel(playListSongs.getTitle())).toList());
        return playSongsToBeViewed;
    }

    public ObservableList<SongModel> getSongs(){
        songsToBeViewed = FXCollections.observableArrayList(songManager.getAllSongs().stream().map(song ->
                new SongModel(song.getTitle(), song.getArtist(), song.getGenre(), song.getDuration())).toList());
        return songsToBeViewed;
    }


    /**
     * Searches through song list, to find a song that matches the key word
     *
     * @param query the key word, to search for
     */

    public void searchSong(String query) {
        List<SongModel> searchResults = songManager.searchSong(query).stream().map(song ->
                new SongModel(song.getTitle(), song.getArtist(), song.getGenre(), song.getDuration())).toList();

        songsToBeViewed.clear();
        songsToBeViewed.addAll((searchResults));
    }
}
