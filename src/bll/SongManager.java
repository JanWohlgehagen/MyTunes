package bll;

import be.Song;
import bll.util.ISearcher;
import bll.util.SongSearcher;
import be.MyTunesException;
import dal.db.SongDAO;
import dal.interfaces.ISongRepository;
import java.io.IOException;
import java.util.List;

import static be.DisplayMessage.displayError;

public class SongManager {

    private ISearcher songSearcher;
    private ISongRepository songDAO;

    public SongManager(){
        songSearcher = new SongSearcher();
        try {
            songDAO = new SongDAO();
        } catch (IOException e) {
            displayError(new MyTunesException(songDAO.ERROR_STRING, e.fillInStackTrace()));
        }
    }

    public List<Song> getAllSongs() throws MyTunesException {
        return songDAO.getAllSongs();
    }

    public Song createSong(String title, String artist, String genre, double duration, String pathToFile) throws MyTunesException {
        return  songDAO.createSong(title, artist, genre, duration, pathToFile);
    }

    public void updateSong(Song song) throws MyTunesException {
        songDAO.updateSong(song);
    }

    public void deleteSong(Song song) throws MyTunesException {
        songDAO.deleteSong(song);
    }

    /**
     * Searches through song list, to find a song that matches the key word
     *
     * @param query the key word, to search for
     * @return a list of songs that fit, the key word
     */
    public List<Song> searchSong(String query) throws MyTunesException {
        List<Song> allSongs = songDAO.getAllSongs();
        List<Song> searchResult = songSearcher.search(allSongs, query);
        return searchResult;
    }
}
