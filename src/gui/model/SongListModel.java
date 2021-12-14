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

    /**
     * Get all the songs there should be display in view.
     *
     * @return ObservableList of Songmodel
     */
    public ObservableList<SongModel> getSongs() {
        return songsToBeViewed;
    }

    /**
     * Create new song to be display in main view
     *
     * @param title
     * @param artist
     * @param genre
     * @param duration
     * @param pathToFile
     * @throws MyTunesException
     */

    public void createSong(String title, String artist, String genre, double duration, String pathToFile) throws MyTunesException {
        songsToBeViewed.add(new SongModel(songManager.createSong(title, artist, genre, duration, pathToFile)));
    }

    /**
     * Delete the song in mainview and database
     *
     * @param songModel
     * @throws MyTunesException
     */

    public void deleteSong(SongModel songModel) throws MyTunesException {
        songManager.deleteSong(songModel.convertToSong());
        songsToBeViewed.remove(songModel);
    }

    /**
     * Update the song in mainview and database
     *
     * @param songModel
     * @param title
     * @param artist
     * @param genre
     * @throws MyTunesException
     */
    public void updateSongToView(SongModel songModel, String title, String artist, String genre) throws MyTunesException {
        songModel.setTitleProperty(title);
        songModel.setArtistProperty(artist);
        songModel.setGenreProperty(genre);
        songManager.updateSong(songModel.convertToSong());
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
}
