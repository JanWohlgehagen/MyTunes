package gui.model;

import bll.SongManager;
import dal.DALException;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.IOException;
import java.util.List;

public class SongListModel {

    private SongManager songManager;
    private ObservableList<SongModel> songsToBeViewed;
    private ObjectProperty<SongModel> selectedSong;


    public SongListModel() throws DALException, IOException {
        songManager = new SongManager();
        selectedSong = new SimpleObjectProperty<>();
        songsToBeViewed = FXCollections.observableArrayList(songManager.getAllSongs().stream().map(song -> new SongModel(song)).toList());
    }

    public ObjectProperty<SongModel> getSelectedSong() {
        return selectedSong;
    }

    public ObservableList<SongModel> getSongs() {
        return songsToBeViewed;
    }

    /**
     * Searches through song list, to find a song that matches the key word
     *
     * @param query the key word, to search for
     */

    public void searchSong(String query) throws DALException {
        List<SongModel> searchResults = songManager.searchSong(query).stream().map(song ->
                new SongModel(song)).toList();

        songsToBeViewed.clear();
        songsToBeViewed.addAll((searchResults));
    }

    public void createSong(String title, String artist, String genre, double duration, String pathToFile) throws DALException {
        songsToBeViewed.add(new SongModel(songManager.createSong(title, artist, genre, duration, pathToFile)));
    }

    public void deleteSong(SongModel songModel) throws DALException {
        songManager.deleteSong(songModel.convertToSong());
        songsToBeViewed.remove(songModel);
    }

    public void updateSongToView(SongModel songModel, String title, String artist, String genre) throws DALException {
        songModel.setTitleProperty(title);
        songModel.setArtistProperty(artist);
        songModel.setGenreProperty(genre);
        songManager.updateSong(songModel.convertToSong());
    }
}
