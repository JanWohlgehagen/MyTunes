package gui.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PlayListSongsModel {

    private StringProperty title = new SimpleStringProperty();


    public PlayListSongsModel(String title){
        this.getTitleProperty().set(title);
    }

    public StringProperty getTitleProperty() {
        return title;
    }



}
