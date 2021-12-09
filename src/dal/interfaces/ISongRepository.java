package dal.interfaces;

import be.Song;
import dal.MyTunesException;
import java.util.List;

public interface ISongRepository {

    String ERROR_STRING = "Error: Cannot access database.";

    public List<Song> getAllSongs() throws MyTunesException;

    public Song createSong(String title, String artist, String genre, double duration, String pathToFile) throws MyTunesException;

    public void updateSong(Song song) throws MyTunesException;

    public void deleteSong(Song song) throws MyTunesException;
}
