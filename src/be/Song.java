package be;

public class Song {

    private String title;
    private String artist;
    private String genre;
    private int duration;
    private int id;
    private String pathToFile;

    public Song(int id, String title, String artist, String genre, int duration, String pathToFile) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.duration = duration;
        this.pathToFile = pathToFile;


    }

    public Song createSong(int id, String title, String artist, String genre, int duration, String pathToFile) {
        return new Song(id, title, artist,genre, duration,pathToFile);
        //listModel.addSongToView(songManager.createSong(title, artist, genre, duration, pathToFile));
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

    public int getDuration() {
        return duration;
    }

    public String getPathToFile() {
        return pathToFile;
    }

    public int getId() {
        return id;
    }

    public String getDurationString() {
        int minutes = duration / 60; // divide by 60 to get the minutes from seconds.
        int seconds = duration % 60; // remaining seconds

        return minutes + ":" + seconds;

    }
}
