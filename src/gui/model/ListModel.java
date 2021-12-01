package gui.model;

import be.Song;
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

        songsToBeViewed = FXCollections.observableArrayList(songManager.getAllSongs().stream().map(song ->
                new SongModel(song.getTitle(), song.getArtist(), song.getGenre(), song.getDuration())).toList());

        playListToBeViewed = FXCollections.observableArrayList(songManager.getAllPlayLists().stream().map(playList ->
                new PlaylistModel(playList.getId(), playList.getName(), playList.getSongList().size(), String.valueOf(playList.getTotalTime()))).toList());


        //
    }

    public ObservableList<PlayListSongModel> test(){
        playSongsToBeViewed = FXCollections.observableArrayList(songManager.getPlayListSongs(selectedPlayList.get().getId()).stream().map(playListSongs -> new PlayListSongModel(playListSongs.getTitle())).toList());
        return playSongsToBeViewed;
    }

    public ObservableList<PlaylistModel> getPlayLists(){
        return playListToBeViewed;
    }

    public ObservableList<SongModel> getSongs(){
        return songsToBeViewed;
    }

    public ObservableList<PlayListSongModel> getPlayListSongs(){
        return playSongsToBeViewed;
    }

    public ObjectProperty<PlaylistModel> getselectedPlayList(){
        return selectedPlayList;
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
