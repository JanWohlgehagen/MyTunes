package be;

import java.util.ArrayList;
import java.util.List;

public class Playlist {

    private String name;
    private int id;
    private List<Song> songList;

    /**
     * the concrustor of playlist to create a instance of a playlist.
     * @param id needs an id for the playlist
     * @param name needs a name of the playlist
     */
    public Playlist(int id, String name){
        this.id = id;
        this.name = name;
        this.songList = new ArrayList<>();

    }

    /**
     * used for getting the int id for the playlist
     * @return the int id for the playlist.
     */
    public int getId(){
        return this.id;
    }

    /**
     * used for getting the playlist name
     * @return the string name of the playlist
     */
    public String getName() {
        return this.name;
    }

    /**
     *  used for setting the name to playlist.
     * @param name gets the String name. of a playlist.
     */
    public void setName(String name) {
        this.name=name;
    }

    /**
     *  used for getting a list of songs on a playlist.
     * @return a list of song objects.
     */
    public List<Song> getSongList() {
        return this.songList;
    }

    /**
     * adds a list of songs to the playlist.
     * @param songs takes a list of songs.
     */
    public void addSongToPlayList(List<Song> songs) {
        this.songList.addAll(songs);
    }

}
