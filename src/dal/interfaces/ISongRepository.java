package dal.interfaces;

import be.Song;
import dal.DALException;

import java.util.List;

public interface ISongRepository {

    public List<Song> getAllSongs() ;

    public Song createSong(String title, String artist, String genre, int duration, String pathToFile) throws DALException;

    public void updateSong(Song song) throws DALException;

    public void deleteSong(Song song) throws DALException;
}
