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
    private ObservableList<SongModel> playListSongsToBeViewed;

    private ObjectProperty<PlaylistModel> selectedPlayList;
    private ObjectProperty<SongModel> selectedSong;


    public ListModel() throws DALException, IOException {
        songManager = new SongManager();
        playlistManager = new PlaylistManager();
        selectedPlayList = new SimpleObjectProperty<>();
        selectedSong = new SimpleObjectProperty<>();

        playListToBeViewed = FXCollections.observableArrayList(playlistManager.getAllPlaylists().stream().map(playList ->
                new PlaylistModel(playList.getId(), playList.getName(), playList.getSongList().size(), String.valueOf(playList.getTotalTime()))).toList());

        songsToBeViewed = FXCollections.observableArrayList(songManager.getAllSongs().stream().map(song ->
                new SongModel(song.getId(), song.getTitle(), song.getArtist(), song.getGenre(), song.getDuration(), song.getPathToFile())).toList());
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

    public ObservableList<SongModel> getPlayListSongs() throws DALException {
        playListSongsToBeViewed = FXCollections.observableArrayList(playlistManager.getSongsFromPlaylist(selectedPlayList.get().getIdProperty().get()).stream().map(playListSongs ->
                new SongModel(playListSongs.getId(), playListSongs.getTitle(), playListSongs.getArtist(), playListSongs.getGenre(), playListSongs.getDuration(), playListSongs.getPathToFile())).toList());
        return playListSongsToBeViewed;
    }


    public ObservableList<SongModel> getSongs() {
        return songsToBeViewed;
    }

    public void addPlaylistToView(String playlistName) throws DALException {
        Playlist playlist = playlistManager.createPlaylist(playlistName);

        playListToBeViewed.add(new PlaylistModel(playlist.getId(), playlist.getName(),playlist.getSongList().size(), String.valueOf(playlist.getTotalTime())));
    }

    public void updatePlaylistToView(Playlist playlist) throws DALException {
        playListToBeViewed.remove(playlist);
        playListToBeViewed.add(new PlaylistModel(playlist.getId(), playlist.getName(),playlist.getSongList().size(), String.valueOf(playlist.getTotalTime())));
    }

    public void addSongToView(String title, String artist, String genre, int duration, String filePath) throws DALException, IOException {
        Song song = songManager.createSong(title, artist, genre, duration, filePath);

        songsToBeViewed.add(new SongModel(song.getId(), song.getTitle(), song.getArtist(), song.getGenre(), song.getDuration(), song.getPathToFile()));
    }

    public void updateSong(Song song) {
        for (SongModel sm: songsToBeViewed) {
            if (sm.getIdProperty().get() == song.getId()){
                songsToBeViewed.remove(sm);
                break;
            }
        }
    }



    /**
     * Searches through song list, to find a song that matches the key word
     *
     * @param query the key word, to search for
     */

    public void searchSong(String query) {
        List<SongModel> searchResults = songManager.searchSong(query).stream().map(song ->
                new SongModel(song.getId(), song.getTitle(), song.getArtist(), song.getGenre(), song.getDuration(), song.getPathToFile())).toList();

        songsToBeViewed.clear();
        songsToBeViewed.addAll((searchResults));
    }


    public void addSongToPlaylist(int songId, int playlistId) throws DALException {
        playlistManager.addSongToPLaylist(songId, playlistId);
    }


    public void deleteSong(Song song) {
        songsToBeViewed.remove(song);
    }

    public void deletePlaylist(Playlist playlist) {playListToBeViewed.remove(playlist);

    }

    public void removeSongFromPlaylist(SongModel songModel, int playlistId) throws DALException {
        playlistManager.removeSongFromPLaylist(songModel.getIdProperty().get(), playlistId);

        playListSongsToBeViewed.remove(songModel);
    }
}
