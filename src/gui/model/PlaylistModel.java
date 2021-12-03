package gui.model;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;



public class PlaylistModel {

    private StringProperty name = new SimpleStringProperty();
    private IntegerProperty totalSongs = new SimpleIntegerProperty();
    private StringProperty time = new SimpleStringProperty();
    private IntegerProperty id = new SimpleIntegerProperty();

    public PlaylistModel(int id, String name, Integer totalSongs, String time){
        this.getIdProperty().set(id);
        this.getNameProperty().set(name);
        this.getTotalSongsProperty().set(totalSongs);
        this.getTimeProperty().set(time);
    }

    public PlaylistModel() {
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

}
