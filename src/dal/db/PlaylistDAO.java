package dal.db;

import be.Playlist;
import be.Song;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.DALException;
import dal.interfaces.IPlaylistRepository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAO implements IPlaylistRepository {

    private MyConnection databaseConnector;

    public PlaylistDAO() throws IOException {
        databaseConnector = new MyConnection();
    }

    @Override
    public List<Song> getAllPlaylists() throws DALException {
        List<Song> allPlaylists = new ArrayList<>();

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
                    allPlaylists.add(song);
                }
            }
        }  catch (SQLException SQLex) {
            throw new DALException("test", SQLex.getCause());
        }
        return allPlaylists;
    }

    @Override
    public void addSongToPLaylist(Song song) throws DALException {

    }

    @Override
    public List<Song> getSongsFromPlaylist(Playlist playlist) throws DALException {
        return null;
    }

    @Override
    public Song createPlaylist(String name) throws DALException {
        return null;
    }

    @Override
    public void updatePlaylist(Playlist playlist) throws DALException {

    }

    @Override
    public void deletePlaylist(Playlist playlist) throws DALException {

    }
}
