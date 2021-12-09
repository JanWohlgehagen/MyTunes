package gui.model;

import bll.SongManager;
import be.MyTunesException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.IOException;
import java.util.List;

public class SongListModel {

    private SongManager songManager;
    private ObservableList<SongModel> songsToBeViewed;


    public SongListModel() throws MyTunesException, IOException {
        songManager = new SongManager();
        songsToBeViewed = FXCollections.observableArrayList(songManager.getAllSongs().stream().map(song -> new SongModel(song)).toList());
    }

    public ObservableList<SongModel> getSongs() {
        return songsToBeViewed;
    }

    /**
     * Searches through song list, to find a song that matches the key word
     *
     * @param query the key word, to search for
     */

    public void searchSong(String query) throws MyTunesException {
        List<SongModel> searchResults = songManager.searchSong(query).stream().map(song ->
                new SongModel(song)).toList();

        songsToBeViewed.clear();
        songsToBeViewed.addAll((searchResults));
    }

    public void createSong(String title, String artist, String genre, double duration, String pathToFile) throws MyTunesException {
        songsToBeViewed.add(new SongModel(songManager.createSong(title, artist, genre, duration, pathToFile)));
    }

    public void deleteSong(SongModel songModel) throws MyTunesException {
        songManager.deleteSong(songModel.convertToSong());
        songsToBeViewed.remove(songModel);
    }

    public void updateSongToView(SongModel songModel, String title, String artist, String genre) throws MyTunesException {
        songModel.setTitleProperty(title);
        songModel.setArtistProperty(artist);
        songModel.setGenreProperty(genre);
        songManager.updateSong(songModel.convertToSong());
    }
}
