package gui.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PlaylistModel {

    private StringProperty name = new SimpleStringProperty();
    private IntegerProperty songs = new SimpleIntegerProperty();
    private StringProperty time = new SimpleStringProperty();

    public PlaylistModel(String name, Integer songs, String time){
        this.getNameProperty().set(name);
        this.getSongsProperty().set(songs);
        this.getTimeProperty().set(time);

    }

    public StringProperty getNameProperty() {
        return name;
    }

    public IntegerProperty getSongsProperty() {
        return songs;
    }

    public StringProperty getTimeProperty() {
        return time;
    }

}
