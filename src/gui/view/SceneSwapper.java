package gui.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneSwapper {

    public void sceneSwitch(Stage window, String fxmlClassName) throws IOException {
        Parent Scene = FXMLLoader.load(getClass().getResource(fxmlClassName));
        Scene ViewScene = new Scene(Scene);
        window.setScene(ViewScene);
        window.show();
    }
}
