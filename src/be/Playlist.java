package be;

import java.util.ArrayList;
import java.util.List;

public class Playlist {

    private String name;
    private int id;
    private List<Song> songList;

    public Playlist(int id, String name){
        this.id = id;
        this.name = name;
        this.songList = new ArrayList<>();

    }

    public int getId(){
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public List<Song> getSongList() {
        return this.songList;
    }

    public void addSongToPlayList(Song song){
        this.songList.add(song);
    }

    public void addSongToPlayList(List<Song> songs) {
        this.songList.addAll(songs);
    }

    public Playlist createPlaylist(int id, String name){
        return new Playlist(id, name);
    }
}
