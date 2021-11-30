package bll;

import be.Song;
import bll.util.ISearcher;
import bll.util.SongSearcher;

import java.util.List;

public class SongManager {

    private ISearcher songSearcher;

    public SongManager(){
        songSearcher = new SongSearcher();
    }

    public List<Song> getAllSongs(){
        return null; // <- songDAO.getAllSongs();
    }

    /**
     * Searches through song list, to find a song that matches the key word
     *
     * @param query the key word, to search for
     * @return a list of songs that fit, the key word
     */
    public List<Song> searchSong(String query) {
        List<Song> allSongs = getAllSongs();
        List<Song> searchResult = songSearcher.search(allSongs, query);
        return searchResult;
    }
}
