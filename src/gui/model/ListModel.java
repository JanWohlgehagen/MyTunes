package gui.model;

import bll.SongManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class ListModel {

    private SongManager songManager;

    private ObservableList<SongModel> songsToBeViewed;
    private ObservableList<PlaylistModel> playListToBeViewed;


    public ListModel() {
        songManager = new SongManager();

        songsToBeViewed = FXCollections.observableArrayList(songManager.getAllSongs().stream().map(song ->
                new SongModel(song.getTitle(), song.getArtist(), song.getGenre(), song.getDuration())).toList());

        playListToBeViewed = FXCollections.observableArrayList(songManager.getAllPlayLists().stream().map(playList ->
                new PlaylistModel(playList.getName(), playList.getSongList().size(), String.valueOf(playList.getTotalTime()))).toList());

    }

    public ObservableList<PlaylistModel> getPlayLists(){
        return playListToBeViewed;
    }

    public ObservableList<SongModel> getSongs(){
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
