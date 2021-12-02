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

    private ObjectProperty<PlaylistModel> selectedPlayList = new SimpleObjectProperty<>();


    public ListModel() throws DALException, IOException {
        songManager = new SongManager();
        playlistManager = new PlaylistManager();
        playListToBeViewed = FXCollections.observableArrayList();
        songsToBeViewed = FXCollections.observableArrayList();
    }

    public ObjectProperty<PlaylistModel> getSelectedPlayList(){
        return selectedPlayList;
    }

    public ObservableList<PlaylistModel> getPlayLists() throws DALException {
        playListToBeViewed = FXCollections.observableArrayList(playlistManager.getAllPlaylists().stream().map(playList ->
                new PlaylistModel(playList.getId(), playList.getName(), playList.getSongList().size(), String.valueOf(playList.getTotalTime()))).toList());
        return playListToBeViewed;
    }

    public ObservableList<PlayListSongModel> getPlayListSongs() throws DALException {
        playListSongsToBeViewed = FXCollections.observableArrayList(playlistManager.getSongsFromPlaylist(selectedPlayList.get().getIdProperty().get()).stream().map(playListSongs ->
                new PlayListSongModel(playListSongs.getTitle())).toList());
        return playListSongsToBeViewed;
    }

    public ObservableList<SongModel> getSongs() throws DALException {
        songsToBeViewed = FXCollections.observableArrayList(songManager.getAllSongs().stream().map(song ->
                new SongModel(song.getTitle(), song.getArtist(), song.getGenre(), song.getDuration())).toList());
        return songsToBeViewed;
    }

    public void addPlaylistToView(Playlist playlist){
       playListToBeViewed.add(new PlaylistModel(playlist.getId(), playlist.getName(),playlist.getSongList().size(), String.valueOf(playlist.getTotalTime())));
    }

    public void addSongToView(Song song) throws DALException, IOException {
        songsToBeViewed.add(new SongModel(song.getTitle(), song.getArtist(), song.getGenre(), song.getDuration()));
    }


    /**
     * Searches through song list, to find a song that matches the key word
     *
     * @param query the key word, to search for
     */

    public void searchSong(String query) throws DALException {
        List<SongModel> searchResults = songManager.searchSong(query).stream().map(song ->
                new SongModel(song.getTitle(), song.getArtist(), song.getGenre(), song.getDuration())).toList();

        songsToBeViewed.clear();
        songsToBeViewed.addAll((searchResults));
    }


}
