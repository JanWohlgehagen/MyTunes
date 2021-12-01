package gui.model;

import bll.SongManager;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PlayListSongModel {

    private SongManager songManager;

    private StringProperty title = new SimpleStringProperty();
    private ObjectProperty<PlaylistModel> selectedPlayList = new SimpleObjectProperty<>();
    private ObservableList<PlayListSongModel> test = FXCollections.observableArrayList();

    public PlayListSongModel(String title){
        //this.getTitleProperty().set(title);
        songManager = new SongManager();
    }

    public ObservableList<PlayListSongModel>  getTitleProperty() {
        return songManager.getPlayListSongs(selectedPlayList.getValue().getId());
    }

}
