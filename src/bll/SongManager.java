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

    public List<Song> searchSong(String query) {
        List<Song> allSongs = getAllSongs();
        List<Song> searchResult = songSearcher.search(allSongs, query);
        return searchResult;
    }
}
