package gui.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class SceneSwapper {

    public void sceneSwitch(Stage window, String fxmlClassName) throws IOException {
        URL url = new File("src/gui/view/" + fxmlClassName).toURI().toURL();
        Parent Scene = FXMLLoader.load(url);
        Scene ViewScene = new Scene(Scene);
        window.setScene(ViewScene);
        window.show();
    }
}
