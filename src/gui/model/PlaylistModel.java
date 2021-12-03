package gui.model;

import be.Playlist;
import bll.PlaylistManager;
import dal.DALException;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.IOException;


public class PlaylistModel {

    private StringProperty name = new SimpleStringProperty();
    private IntegerProperty totalSongs = new SimpleIntegerProperty();
    private StringProperty time = new SimpleStringProperty();
    private IntegerProperty id = new SimpleIntegerProperty();
    private PlaylistManager playlistManager;
    private ListModel listModel;

    public PlaylistModel(int id, String name, Integer totalSongs, String time){
        this.getIdProperty().set(id);
        this.getNameProperty().set(name);
        this.getTotalSongsProperty().set(totalSongs);
        this.getTimeProperty().set(time);
    }

    public PlaylistModel() throws IOException, DALException {
        playlistManager = new PlaylistManager();
        listModel = new ListModel();
    }



    public StringProperty getNameProperty() {
        return name;
    }
    public IntegerProperty getTotalSongsProperty() {
        return totalSongs;
    }

    public StringProperty getTimeProperty() {
        return time;
    }

    public IntegerProperty getIdProperty() {
        return id;
    }

    public void createPlaylist(String name) throws DALException {
        listModel.addPlaylistToView(playlistManager.createPlaylist(name));
    }

    public void updatePlaylist(String name) {

    }

    /**
     * Ask the listModel to remove the play list from the view
     * Ask the Playlist Manager to remove the playlist
     * @param playlist
     * @throws DALException
     */
    public void deletePlaylist(Playlist playlist) throws DALException {
        listModel.deletePlaylist(playlist);
        playlistManager.deletePlaylist(playlist);
    }

    public Playlist convertToPlaylist() {
        return new Playlist(this.getIdProperty().get(), this.getNameProperty().get());
    }


}
