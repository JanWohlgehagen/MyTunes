package dal.interfaces;

import be.Song;

import java.util.List;

public interface ISongRepository {

    public List<Song> getAllSongs() throws Exception;

    public Song createSong(String title, String artist, String genre, int duration, String pathToFile) throws Exception;

    public void updateSong(Song song) throws Exception;

    public void deleteSong(Song song) throws Exception;
}
