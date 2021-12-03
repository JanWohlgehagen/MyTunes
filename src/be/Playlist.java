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

    public int getTotalTime(){
        int totalTime = 0;
        for (Song song: getSongList()) {
            totalTime += song.getDuration();
        }
        return totalTime;
    }

    public String getDurationString(){
        int totalTime = getTotalTime();

        int minutes = totalTime  / 60; // divide by 60 to get the minutes from seconds.
        int seconds = totalTime  % 60; // remaining seconds

        return minutes + ":" + seconds;
    }
}
