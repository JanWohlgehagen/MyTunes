package dal.db;

import be.Song;
import be.MyTunesException;
import dal.interfaces.ISongRepository;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SongDAO implements ISongRepository {

    private MyConnection databaseConnector;

    public SongDAO() throws IOException {
        databaseConnector = new MyConnection();
    }

    /**
     * Get all songs in DB
     *
     * @return
     * @throws MyTunesException
     */
    @Override
    public List<Song> getAllSongs() throws MyTunesException {
        List<Song> allSongsList = new ArrayList<>();

        //Create a connection
        try(Connection connection = databaseConnector.getConnection()){
            String sql = "SELECT * FROM Song;"; //sql command
            Statement statement = connection.createStatement(); //Create statement

            //Extract data from DB
            if(statement.execute(sql)){
                ResultSet resultSet = statement.getResultSet();
                while(resultSet.next()){
                    int id = resultSet.getInt("id");
                    String title = resultSet.getString("title");
                    String artist = resultSet.getString("artist");
                    String genre = resultSet.getString("genre");
                    double duration = resultSet.getDouble("duration");
                    String pathToFile = resultSet.getString("filePath");

                    allSongsList.add(new Song(id, title, artist, genre, duration, pathToFile));
                }
            }
        }  catch (SQLException SQLex) {
           throw new MyTunesException(ERROR_STRING, SQLex.fillInStackTrace());
        }
        return allSongsList;
    }

    /**
     * create a song in DB
     *
     * @param title
     * @param artist
     * @param genre
     * @param duration
     * @param pathToFile
     * @return The new song
     * @throws MyTunesException
     */

    @Override
    public Song createSong(String title, String artist, String genre, double duration, String pathToFile) throws MyTunesException {

        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "INSERT INTO Song VALUES (?,?,?,?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, pathToFile);
            preparedStatement.setString(3, artist);
            preparedStatement.setString(4, genre);
            preparedStatement.setDouble(5, duration);
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 1) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);

                    return new Song(id, title, artist, genre, duration, pathToFile);
                }
            }
        } catch (SQLException SQLex) {
            throw new MyTunesException(ERROR_STRING, SQLex.fillInStackTrace());
        }
        return null;
    }

    /**
     * update a song in DB
     *
     * @param song
     * @throws MyTunesException
     */

    @Override
    public void updateSong(Song song) throws MyTunesException {
        try(Connection connection = databaseConnector.getConnection()) {
            String sql = "UPDATE Song SET title = ?, filePath=?, artist=?, genre=?, duration=? WHERE Id=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, song.getTitle());
            preparedStatement.setString(2, song.getPathToFile());
            preparedStatement.setString(3, song.getArtist());
            preparedStatement.setString(4, song.getGenre());
            preparedStatement.setDouble(5, song.getDuration());
            preparedStatement.setInt(6, song.getId());

            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows != 1) {
                throw new MyTunesException(affectedRows + " affectedRows  in DAL");
            }
        } catch (SQLException SQLex) {
            throw new MyTunesException(ERROR_STRING, SQLex.fillInStackTrace());
        }
    }

    /**
     * Delete song in DB
     *
     * @param song
     * @throws MyTunesException
     */

    @Override
    public void deleteSong(Song song) throws MyTunesException {
        try(Connection connection = databaseConnector.getConnection()){
            String sql = "DELETE FROM Song WHERE Id=(?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, song.getId());
            int affectedRows = preparedStatement.executeUpdate();

            if(affectedRows != 1){
                throw new MyTunesException(affectedRows + " affectedRows in DAL");
            }
        } catch (SQLException SQLex) {
            throw new MyTunesException(ERROR_STRING, SQLex.fillInStackTrace());
        }
    }
}