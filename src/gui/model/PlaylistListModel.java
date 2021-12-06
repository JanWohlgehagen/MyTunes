package gui.model;

import be.Playlist;
import be.Song;
import bll.PlaylistManager;
import dal.DALException;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;

public class PlaylistListModel {

    private PlaylistManager playlistManager;
    private ObservableList<PlaylistModel> playListToBeViewed;
    private ObservableList<SongModel> playListSongsToBeViewed;
    private ObjectProperty<PlaylistModel> selectedPlayList;

    public PlaylistListModel() throws IOException, DALException {
        playlistManager = new PlaylistManager();
        selectedPlayList = new SimpleObjectProperty<>();

        playListToBeViewed = FXCollections.observableArrayList(playlistManager.getAllPlaylists().stream().map(playList -> {
            try {
                return new PlaylistModel(playList);
            } catch (IOException | DALException e) {
                e.printStackTrace();
            }
            return null;
        }).toList());
    }

    public ObjectProperty<PlaylistModel> getSelectedPlayList(){
        return selectedPlayList;
    }

    public ObservableList<PlaylistModel> getPlayLists() {
        return playListToBeViewed;
    }


    public void addPlaylistToView(String playlistName) throws DALException, IOException {
        Playlist playlist = playlistManager.createPlaylist(playlistName);
        playListToBeViewed.add(new PlaylistModel(playlist));
    }

    public void addSongToPlaylist(Song song, Playlist playlist) throws DALException {
        playlistManager.addSongToPLaylist(song, playlist);
    }

    public void updatePlaylistToView(PlaylistModel playlistModel, String name) throws DALException {
        playlistModel.setNameProperty(name);
        playlistManager.updatePlaylist(playlistModel.convertToPlaylist());
    }
}
