package bll;

import be.Playlist;
import be.Song;
import bll.util.ISearcher;
import bll.util.SongSearcher;
import dal.DALException;
import dal.db.PlaylistDAO;
import dal.db.SongDAO;
import dal.interfaces.IPlaylistRepository;
import dal.interfaces.ISongRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SongManager {

    private ISearcher songSearcher;
    private ISongRepository songDAO;

    public SongManager(){
        songSearcher = new SongSearcher();
        try {
            songDAO = new SongDAO();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public List<Song> getAllSongs() {
        return songDAO.getAllSongs();
    }

    public Song createSong(String title, String artist, String genre, int duration, String pathToFile) throws DALException{
        return  songDAO.createSong(title, artist, genre, duration, pathToFile);
    }

    public void updateSong(Song song) throws DALException{
        songDAO.updateSong(song);
    }

    public void deleteSong(Song song) throws DALException{
        songDAO.deleteSong(song);
    }

    /**
     * Searches through song list, to find a song that matches the key word
     *
     * @param query the key word, to search for
     * @return a list of songs that fit, the key word
     */
    public List<Song> searchSong(String query){
        List<Song> allSongs = songDAO.getAllSongs();
        List<Song> searchResult = songSearcher.search(allSongs, query);
        return searchResult;
    }




}
