package dal.db;

import be.Song;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.DALException;
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

    @Override
    public List<Song> getAllSongs()  {
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
                    int duration = resultSet.getInt("duration");
                    String pathToFile = resultSet.getString("filePath");
                    Song song = new Song(id, title, artist, genre, duration, pathToFile);
                    allSongsList.add(song);
                }
            }
        }  catch (SQLException SQLex) {
           //throw new DALException("Error Error Error Error", SQLex.getCause());
        }
        return allSongsList;
    }

    @Override
    public Song createSong(String title, String artist, String genre, int duration, String pathToFile) throws DALException {
        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "INSERT INTO Song VALUES (?,?,?,?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, pathToFile);
            preparedStatement.setString(3, artist);
            preparedStatement.setString(4, genre);
            preparedStatement.setInt(5, duration);
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 1) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    Song song = new Song(id, title, artist, genre, duration, pathToFile);
                    return song;
                }
            }
        } catch (SQLException SQLex) {
            throw new DALException("Error");
        }
        return null;
    }

    @Override
    public void updateSong(Song song) throws DALException {
        try(Connection connection = databaseConnector.getConnection()){
            String sql = "UPDATE Song SET title = ?, filePath=?, artist=?, genre=?, duration=? WHERE Id=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, song.getTitleProperty().get());
            preparedStatement.setString(2, song.getPathToFileProperty().get());
            preparedStatement.setString(3, song.getArtistProperty().get());
            preparedStatement.setString(4, song.getGenreProperty().get());
            preparedStatement.setInt(5, song.getDurationProperty().get());
            preparedStatement.setInt(6, song.getIdProperty().get());

            int affectedRows = preparedStatement.executeUpdate();

            if(affectedRows != 1){
                throw new DALException();
            }
        } catch (SQLException SQLex) {
            throw new DALException("Error");
        }
    }

    @Override
    public void deleteSong(Song song) throws DALException {
        try(Connection connection = databaseConnector.getConnection()){
            String sql = "DELETE FROM Song WHERE Id=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, song.getIdProperty().get());
            int affectedRows = preparedStatement.executeUpdate();

            if(affectedRows != 1){
                throw new DALException();
            }
        } catch (SQLException SQLex) {
            throw new DALException("Error");
        }
    }
}


