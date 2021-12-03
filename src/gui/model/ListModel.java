package gui.model;

import be.Playlist;
import be.Song;
import bll.PlaylistManager;
import bll.SongManager;
import dal.DALException;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.List;

public class ListModel {

    private SongManager songManager;
    private PlaylistManager playlistManager;

    private ObservableList<SongModel> songsToBeViewed;
    private ObservableList<PlaylistModel> playListToBeViewed;
    private ObservableList<PlayListSongModel> playListSongsToBeViewed;

    private ObjectProperty<PlaylistModel> selectedPlayList;
    private ObjectProperty<SongModel> selectedSong;


    public ListModel() throws DALException, IOException {
        songManager = new SongManager();
        playlistManager = new PlaylistManager();
        selectedPlayList = new SimpleObjectProperty<>();
        selectedSong = new SimpleObjectProperty<>();



        playListToBeViewed = FXCollections.observableArrayList(playlistManager.getAllPlaylists().stream().map(playList ->
                new PlaylistModel(playList.getId(), playList.getName(), playList.getSongList().size(), String.valueOf(playList.getTotalTime()))).toList());

        songsToBeViewed = FXCollections.observableArrayList(songManager.getAllSongs().stream().map(song -> new SongModel(song)).toList());
    }

    public ObjectProperty<PlaylistModel> getSelectedPlayList(){
        return selectedPlayList;
    }

    public ObjectProperty<SongModel> getSelectedSong(){
        return selectedSong;
    }

    public ObservableList<PlaylistModel> getPlayLists() {
        return playListToBeViewed;
    }

    public ObservableList<PlayListSongModel> getPlayListSongs() throws DALException {
        playListSongsToBeViewed = FXCollections.observableArrayList(playlistManager.getSongsFromPlaylist(selectedPlayList.get().getIdProperty().get()).stream().map(playListSongs ->
                new PlayListSongModel(playListSongs.getTitleProperty().get())).toList());
        return playListSongsToBeViewed;
    }

    public ObservableList<SongModel> getSongs() {
        return songsToBeViewed;
    }

    public void addPlaylistToView(String playlistName) throws DALException {
        Playlist playlist = playlistManager.createPlaylist(playlistName);
        playListToBeViewed.add(new PlaylistModel(playlist.getId(), playlist.getName(),playlist.getSongList().size(), String.valueOf(playlist.getTotalTime())));
    }

    public void addSongToView(Song song) throws DALException, IOException {
        songsToBeViewed.add(new SongModel(song));
    }



    /**
     * Searches through song list, to find a song that matches the key word
     *
     * @param query the key word, to search for
     */

    public void searchSong(String query) {
        List<SongModel> searchResults = songManager.searchSong(query).stream().map(song ->
                new SongModel(song)).toList();

        songsToBeViewed.clear();
        songsToBeViewed.addAll((searchResults));
    }

    public void addSongToPlatlist(int songId, int playlistId) throws DALException {
        playlistManager.addSongToPLaylist(songId, playlistId);
    }
}
