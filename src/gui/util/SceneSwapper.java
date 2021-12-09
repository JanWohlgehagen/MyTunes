package gui.util;

import com.sun.tools.javac.Main;
import dal.DALException;
import gui.controller.EditPlaylistController;
import gui.model.PlaylistModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class SceneSwapper {

    public void sceneSwitch(Stage stage, String fxmlClassName) throws IOException {
        URL url = new File("src/gui/view/" + fxmlClassName).toURI().toURL();
        Parent Scene = FXMLLoader.load(url);
        Scene ViewScene = new Scene(Scene);
        stage.setTitle("MyTunes");
        Image image = new Image("/gui/Images/MyTunesLogo.png");
        stage.getIcons().add(image);
        stage.setScene(ViewScene);
        stage.show();
    }
}
