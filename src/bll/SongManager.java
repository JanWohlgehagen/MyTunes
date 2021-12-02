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
    private IPlaylistRepository playListDAO;
    private ISongRepository songDAO;

    public SongManager() throws DALException, IOException {
        songSearcher = new SongSearcher();
        playListDAO = new PlaylistDAO();
        songDAO = new SongDAO();

    }

    public List<Song> getPlayListSongs(int playListId) throws DALException{
        List<Song> playListSongs = new ArrayList<>();


        playListSongs.add(new Song(1,"test1", "test1", "test1", 0, "test½"));
        playListSongs.add(new Song(2,"dtest2", "test2", "test2", 0, "test½"));

        return playListSongs;
    }

    public List<Song> getAllSongs() throws DALException {
        /*
        List<Song> allSongstest = new ArrayList<>();
        allSongstest.add(new Song(1,"test1", "test1", "test1", 0, "test½"));
        allSongstest.add(new Song(2,"dtest2", "test2", "test2", 0, "test½"));
        allSongstest.add(new Song(3,"tfest3", "test1", "test1", 0, "test½"));
        allSongstest.add(new Song(4,"gtest4", "test1", "test1", 0, "test½"));
        allSongstest.add(new Song(5,"gtest5", "test1", "test1", 0, "test½"));
         */

        return songDAO.getAllSongs(); // <- songDAO.getAllSongs();
    }

    /**
     * Searches through song list, to find a song that matches the key word
     *
     * @param query the key word, to search for
     * @return a list of songs that fit, the key word
     */
    public List<Song> searchSong(String query) throws DALException {
        List<Song> allSongs = songDAO.getAllSongs();
        List<Song> searchResult = songSearcher.search(allSongs, query);
        return searchResult;
    }



    // til at få alle PlayList i databassen

    public List<Playlist> getAllPlayLists() throws DALException {
        /*
        List<Playlist> allPlayList = new ArrayList<>();
        Playlist test = new Playlist(1,"testtetestests");
        allPlayList.add(test);
        allPlayList.add(new Playlist(2,"test"));
        allPlayList.add(new Playlist(3, "tobias"));
        allPlayList.add(new Playlist(4, "træt"));
        allPlayList.add(new Playlist(5, "arbejde"));
        test.addSongToPlayList(new Song(6,"tfest33333", "test1", "test1", 5, "test½"));

         */

        return playListDAO.getAllPlaylists();
    }


}
