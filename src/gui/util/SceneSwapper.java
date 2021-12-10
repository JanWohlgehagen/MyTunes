package gui.util;

import be.MyTunesException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static be.DisplayMessage.displayError;

public class SceneSwapper {

    /**
     *
     * @param stage
     * @param fxmlClassName
     * @throws MyTunesException if cant find the stage.
     */
    public void sceneSwitch(Stage stage, String fxmlClassName){
        try {
            URL url = new File("src/gui/view/" + fxmlClassName).toURI().toURL();
            Parent Scene = FXMLLoader.load(url);
            Scene ViewScene = new Scene(Scene);
            stage.setTitle("MyTunes");
            Image image = new Image("/gui/Images/MyTunesLogo.png");
            stage.getIcons().add(image);
            stage.setScene(ViewScene);
            stage.show();
        }catch (IOException IOex){
            displayError(new MyTunesException("Cant find the stage -> " + fxmlClassName));
        }
    }
}
