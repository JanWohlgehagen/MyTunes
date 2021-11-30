package be;

import java.time.Duration;

public class Song {

    private String title;
    private String artist;
    private String genre;
    private Duration duration;
    private String pathToFile;

    public Song(String title, String artist, String genre, Duration duration, String pathToFile) {
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.duration = duration;
        this.pathToFile = pathToFile;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getGenre() {
        return genre;
    }

    public Duration getDuration() {
        return duration;
    }

    public String getPathToFile() {
        return pathToFile;
    }
}
