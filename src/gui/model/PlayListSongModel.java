package gui.model;

import be.Song;
import bll.PlaylistManager;
import dal.DALException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.IOException;
import java.util.List;


public class PlayListSongModel {

    private StringProperty title = new SimpleStringProperty();

    public PlayListSongModel(String title) throws IOException {
        this.getTitleProperty().set(title);
    }

    public StringProperty getTitleProperty() {
        return title;
    }

}
