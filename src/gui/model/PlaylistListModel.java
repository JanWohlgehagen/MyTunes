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

    /**
     *  the constructor of PlaylistlistModel.
     * @throws MyTunesException
     */
    public PlaylistListModel() throws MyTunesException {
        playlistManager = new PlaylistManager();

        playListToBeViewed = FXCollections.observableArrayList(playlistManager.getAllPlaylists().stream().map(playList ->
                new PlaylistModel(playList)).toList());

    }

    /**
     *  gives a list of all playlists.
     * @return the observablelist playlisttobeViewed.
     */
    public ObservableList<PlaylistModel> getPlayLists() {
        return playListToBeViewed;
    }

    /**
     * creates a new playlist
     * @param playlistName the name of the new playlist.
     * @throws MyTunesException
     */
    public void createPlaylist(String playlistName) throws MyTunesException {
        Playlist playlist = playlistManager.createPlaylist(playlistName);
        playListToBeViewed.add(new PlaylistModel(playlist));
    }

    /**
     *  adds a song to a playlist.
     * @param song a songmodel object
     * @param playlist a playlistmodel objecgt
     * @throws MyTunesException
     */
    public void addSongToPlaylist(Song song, Playlist playlist) throws MyTunesException {
        playlistManager.addSongToPLaylist(song, playlist);
    }

    /**
     * Updates a playlist´s name.
     * @param playlistModel a playlistmodel object.
     * @param name a name of playlist.
     * @throws MyTunesException
     */
    public void updatePlaylistToView(PlaylistModel playlistModel, String name) throws MyTunesException {
        playlistModel.setNameProperty(name);
        playlistManager.updatePlaylist(playlistModel.convertToPlaylist());
    }

    /**
     * remove song from a playlist.
     * @param songModel a song object
     * @param playlistModel a playlist object
     * @throws MyTunesException if no songmodel or playlistModel object.
     */
    public void removeSongFromPlaylist(SongModel songModel, PlaylistModel playlistModel) throws MyTunesException {
        playlistManager.removeSongFromPLaylist(songModel.convertToSong(), playlistModel.convertToPlaylist());
        playlistModel.removeSongFromPlaylist(songModel);
    }

    /**
     * removes a playlist
     * @param playlistModel gets a playlistmodel object.
     * @throws MyTunesException if there is no playlist.
     */
    public void deletePlaylist(PlaylistModel playlistModel) throws MyTunesException {
        playlistManager.deletePlaylist(playlistModel.convertToPlaylist());
        playListToBeViewed.remove(playlistModel);
    }

    /**
     *  ascends a Song in a Playlist
     * @param playlistModel a playlistmodel object
     * @param songModel a songmodel object
     */
    public void AscendSongInPlaylist(PlaylistModel playlistModel, SongModel songModel){
        playlistModel.AscendSongInPlaylist(songModel);
    }

    /**
     * descends a song in a playlist
     * @param playlistModel a playlistmodel object
     * @param songModel a songmodel object
     */
    public void DescendSongInPlaylist(PlaylistModel playlistModel, SongModel songModel){
        playlistModel.DescendSongInPlaylist(songModel);
    }
}
