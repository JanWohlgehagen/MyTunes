package gui.model;

import be.Song;
import bll.SongManager;
import javafx.collections.ObservableList;

import java.util.List;

public class SongModel {

    private SongManager songManager;

    private ObservableList<Song> songsToBeViewed;

    public SongModel(){
        songManager = new SongManager();

    }

    public void searchSong(String query) {
        List<Song> searchResults = songManager.searchSong(query);
        songsToBeViewed.clear();
        songsToBeViewed.addAll(searchResults);
    }

}
