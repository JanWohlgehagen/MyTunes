package dal.db;

import be.Playlist;
import be.Song;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.DALException;
import dal.interfaces.IPlaylistRepository;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAO implements IPlaylistRepository {

    private MyConnection databaseConnector;

    public PlaylistDAO() throws IOException {
        databaseConnector = new MyConnection();
    }

    @Override
    public List<Playlist> getAllPlaylists() throws DALException {
        List<Playlist> allPlaylists = new ArrayList<>();
        List<Song> allsongs = new ArrayList<>();

        //Create a connection
        try(Connection connection = databaseConnector.getConnection()){
            String sql = "SELECT * FROM Playlist;";
            String sql1 = "SELECT * FROM Song FULL JOIN PlaylistSongs ON Song.id = songId WHERE  PlaylistSongs.playlistId = (?);"; //1. sql command
            PreparedStatement preparedStatement1 = connection.prepareStatement(sql1); //Create statement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);


            //Extract data from DB
            if(preparedStatement.execute()){
                ResultSet resultSet = preparedStatement.getResultSet();
                while(resultSet.next()){
                    allsongs.clear();
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("Name");


                    preparedStatement1.setInt(1,id);
                    if(preparedStatement1.execute()){
                        ResultSet resultSet1 = preparedStatement1.getResultSet();
                        while(resultSet1.next()) {
                            int idSong = resultSet1.getInt("id");
                            String title = resultSet1.getString("title");
                            String artist = resultSet1.getString("artist");
                            String genre = resultSet1.getString("genre");
                            int duration = resultSet1.getInt("duration");
                            String pathToFile = resultSet1.getString("filePath");
                            allsongs.add(new Song(idSong, title, artist, genre, duration, pathToFile));
                        }
                    }
                        Playlist playlist = new Playlist(id, name);
                        playlist.addSongToPlayList(allsongs);
                    allPlaylists.add(playlist);

                }
            }
        } catch (SQLException SQLex) {
            SQLex.printStackTrace();
           throw new DALException("Error: Can not 'getAllPlaylist' in Databases");
        }
        return allPlaylists;
    }

    @Override
    public List<Song> getSongsFromPlaylist(int playlistId) throws DALException {
        List<Song> songsInPlaylist = new ArrayList<>();

        //Create a connection
        try(Connection connection = databaseConnector.getConnection()){
            String sql = "SELECT id, title, filePath, artist, genre, duration FROM Song INNER JOIN PlaylistSongs ON PlaylistSongs.songId = Song.id WHERE playlistId =(?);"; //sql command
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, playlistId);

            //Extract data from DB
            if(preparedStatement.execute()){
                ResultSet resultSet = preparedStatement.getResultSet();
                while(resultSet.next()){
                    int id = resultSet.getInt("id");
                    String title = resultSet.getString("title");
                    String artist = resultSet.getString("artist");
                    String genre = resultSet.getString("genre");
                    int duration = resultSet.getInt("duration");
                    String pathToFile = resultSet.getString("filePath");

                    songsInPlaylist.add(new Song(id, title, artist, genre, duration, pathToFile));
                }
            }
        } catch (SQLException SQLex) {
            SQLex.printStackTrace();
            throw new DALException("Error: Can not 'getSongsFromPlaylist' in Databases");
        }
        return songsInPlaylist;
    }

    @Override
    public void addSongToPLaylist(Song song, Playlist playlist) throws DALException {
        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "INSERT INTO PlaylistSongs VALUES (?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, song.getId());
            preparedStatement.setInt(2, playlist.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException SQLex) {
            throw new DALException("Error: Can not 'addSongToPLaylist' in Databases");
        }
    }

    @Override
    public void removeSongFromPlaylist(Song song, Playlist playlist) throws DALException {
        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "DELETE FROM PlaylistSongs WHERE songId = (?) AND playlistId = (?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, song.getId());
            preparedStatement.setInt(2, playlist.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException SQLex) {
            throw new DALException("Error");
        }
    }


    @Override
    public Playlist createPlaylist(String name) throws DALException {
        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "INSERT INTO Playlist VALUES (?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, name);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 1) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);

                    return new Playlist(id, name);
                }
            }
        } catch (SQLException SQLex) {
            throw new DALException("Error: Can not 'createPlaylist' in Databases");
        }
        return null;
    }

    @Override
    public void updatePlaylist(Playlist playlist) throws DALException {
        try(Connection connection = databaseConnector.getConnection()){
            String sql = "UPDATE Playlist SET name=? WHERE Id=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, playlist.getName());
            preparedStatement.setInt(2, playlist.getId());

            int affectedRows = preparedStatement.executeUpdate();

            if(affectedRows != 1){
                throw new DALException("Error: There are too many rows affected");
            }
        } catch (SQLException SQLex) {
            throw new DALException("Error: Can not 'updatePlaylist' in Databases");
        }
    }

    @Override
    public void deletePlaylist(Playlist playlist) throws DALException {
        try(Connection connection = databaseConnector.getConnection()){
            String sql = "DELETE FROM Playlist WHERE Id=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, playlist.getId());
            int affectedRows = preparedStatement.executeUpdate();

            if(affectedRows != 1){
                throw new DALException("Error: There are too many rows affected");
            }
        } catch (SQLException SQLex) {
            throw new DALException("Error: Can not 'deletePlaylist' in Databases");
        }
    }


    public static void main(String[] args) throws IOException, DALException {
        PlaylistDAO playlistDao = new PlaylistDAO();
        List <Song> theList = playlistDao.getSongsFromPlaylist(1);
        //System.out.println(playlistDao.test());


        for (Playlist playlist: playlistDao.getAllPlaylists()) {
            System.out.println("new " + playlist.getName());
            for (Song song: playlist.getSongList()) {
                System.out.println(song.getTitle());
            }

        }


        System.out.println("Done");
    }


}

