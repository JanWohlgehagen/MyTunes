package gui.model;

import be.Playlist;
import bll.PlaylistManager;
import dal.DALException;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PlaylistListModel {

    private PlaylistManager playlistManager;

    private ObservableList<PlaylistModel> playListToBeViewed;
    private ObservableList<SongModel> playListSongsToBeViewed;

    private ObjectProperty<PlaylistModel> selectedPlayList;

    public PlaylistListModel() throws DALException {
        selectedPlayList = new SimpleObjectProperty<>();
        playlistManager = new PlaylistManager();


        playListToBeViewed = FXCollections.observableArrayList(playlistManager.getAllPlaylists().stream().map(playList ->
                new PlaylistModel(playList.getId(), playList.getName(), playList.getSongList().size(), String.valueOf(playList.getTotalTime()))).toList());
    }


    public ObservableList<PlaylistModel> getPlayLists() {
        return playListToBeViewed;
    }

    public ObjectProperty<PlaylistModel> getSelectedPlayList(){
        return selectedPlayList;
    }

    public ObservableList<SongModel> getPlayListSongs() throws DALException {
        playListSongsToBeViewed = FXCollections.observableArrayList(playlistManager.getSongsFromPlaylist(selectedPlayList.get().getIdProperty().get()).stream().map(playListSongs ->
                new SongModel(playListSongs.getId(), playListSongs.getTitle(), playListSongs.getArtist(), playListSongs.getGenre(), playListSongs.getDuration(), playListSongs.getPathToFile())).toList());
        return playListSongsToBeViewed;
    }

    public void addPlaylistToView(String playlistName) throws DALException {
        Playlist playlist = playlistManager.createPlaylist(playlistName);

        playListToBeViewed.add(new PlaylistModel(playlist.getId(), playlist.getName(),playlist.getSongList().size(), String.valueOf(playlist.getTotalTime())));
    }

    public void updatePlaylistToView(Playlist playlist) throws DALException {
        playListToBeViewed.remove(playlist);
        playListToBeViewed.add(new PlaylistModel(playlist.getId(), playlist.getName(),playlist.getSongList().size(), String.valueOf(playlist.getTotalTime())));
    }

    public void addSongToPlaylist(int songId, int playlistId) throws DALException {
        playlistManager.addSongToPLaylist(songId, playlistId);
    }

    public void deletePlaylist(Playlist playlist) {playListToBeViewed.remove(playlist);

    }

    public void removeSongFromPlaylist(SongModel songModel, int playlistId) throws DALException {
        playlistManager.removeSongFromPLaylist(songModel.getIdProperty().get(), playlistId);

        playListSongsToBeViewed.remove(songModel);
    }
}
