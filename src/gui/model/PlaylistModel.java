package gui.model;

import be.Song;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.List;

public class PlaylistModel {

    private StringProperty name = new SimpleStringProperty();
    private IntegerProperty totalSongs = new SimpleIntegerProperty();
    private StringProperty time = new SimpleStringProperty();
    private int id;

    public PlaylistModel(int id, String name, Integer totalSongs, String time){
        this.getNameProperty().set(name);
        this.getTotalSongsProperty().set(totalSongs);
        this.getTimeProperty().set(time);
        this.id = id;

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

    public int getId(){
        return id;
    }


}
