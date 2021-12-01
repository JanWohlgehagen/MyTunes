package gui.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class PlayListSongModel {

    private StringProperty title = new SimpleStringProperty();

    public PlayListSongModel(String title){
        this.getTitleProperty().set(title);
    }

    public StringProperty getTitleProperty() {
        return title;
    }
}
