package be;

public class Song {

    private String title;
    private String artist;
    private String genre;
    private double duration;
    private int id;
    private String pathToFile;

    public Song(int id, String title, String artist, String genre, double duration, String pathToFile) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.duration = duration;
        this.pathToFile = pathToFile;
    }
    public int getId() {
        return id;
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

    public double getDuration() {
        return duration;
    }

    public String getPathToFile() {
        return pathToFile;
    }
}
