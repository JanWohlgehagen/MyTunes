package gui.model;

import be.Playlist;
import be.Song;
import bll.PlaylistManager;
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

        songsToBeViewed = FXCollections.observableArrayList(songManager.getAllSongs().stream().map(song ->
                new SongModel(song.getId(), song.getTitle(), song.getArtist(), song.getGenre(), song.getDuration(), song.getPathToFile())).toList());
    }

    public ObjectProperty<SongModel> getSelectedSong() {
        return selectedSong;
    }

    public ObservableList<SongModel> getSongs() {
        return songsToBeViewed;
    }

    public void addSongToView(String title, String artist, String genre, int duration, String filePath) throws DALException, IOException {
        Song song = songManager.createSong(title, artist, genre, duration, filePath);

        songsToBeViewed.add(new SongModel(song.getId(), song.getTitle(), song.getArtist(), song.getGenre(), song.getDuration(), song.getPathToFile()));
    }


    public void updateSong(Song song) {
        for (SongModel sm : songsToBeViewed) {
            if (sm.getIdProperty().get() == song.getId()) {
                songsToBeViewed.remove(sm);
                break;
            }
        }

    }


    /**
     * Searches through song list, to find a song that matches the key word
     *
     * @param query the key word, to search for
     */

    public void searchSong(String query) {
        List<SongModel> searchResults = songManager.searchSong(query).stream().map(song ->
                new SongModel(song.getId(), song.getTitle(), song.getArtist(), song.getGenre(), song.getDuration(), song.getPathToFile())).toList();

        songsToBeViewed.clear();
        songsToBeViewed.addAll((searchResults));
    }

    public void deleteSong(Song song) {
        songsToBeViewed.remove(song);
    }


}
