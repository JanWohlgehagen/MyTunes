package gui.model;

import be.Playlist;
import be.Song;
import bll.PlaylistManager;
import be.MyTunesException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PlaylistListModel {
    private PlaylistManager playlistManager;
    private ObservableList<PlaylistModel> playListToBeViewed;

    public PlaylistListModel() throws MyTunesException {
        playlistManager = new PlaylistManager();

        playListToBeViewed = FXCollections.observableArrayList(playlistManager.getAllPlaylists().stream().map(playList ->
                new PlaylistModel(playList)).toList());

    }

    public ObservableList<PlaylistModel> getPlayLists() {
        return playListToBeViewed;
    }

    public void createPlaylist(String playlistName) throws MyTunesException {
        Playlist playlist = playlistManager.createPlaylist(playlistName);
        playListToBeViewed.add(new PlaylistModel(playlist));
    }

    public void addSongToPlaylist(Song song, Playlist playlist) throws MyTunesException {
        playlistManager.addSongToPLaylist(song, playlist);
    }

    public void updatePlaylistToView(PlaylistModel playlistModel, String name) throws MyTunesException {
        playlistModel.setNameProperty(name);
        playlistManager.updatePlaylist(playlistModel.convertToPlaylist());
    }

    public void removeSongFromPlaylist(SongModel songModel, PlaylistModel playlistModel) throws MyTunesException {
        playlistManager.removeSongFromPLaylist(songModel.convertToSong(), playlistModel.convertToPlaylist());
        playlistModel.removeSongFromList(songModel);
    }

    public void deletePlaylist(PlaylistModel playlistModel) throws MyTunesException {
        playlistManager.deletePlaylist(playlistModel.convertToPlaylist());
        playListToBeViewed.remove(playlistModel);
    }

    public void AscendSongInPlaylist(PlaylistModel playlistModel, SongModel songModel){
        playlistModel.AscendSongInPlaylist(songModel);
    }

    public void DescendSongInPlaylist(PlaylistModel playlistModel, SongModel songModel){
        playlistModel.DescendSongInPlaylist(songModel);
    }
}
