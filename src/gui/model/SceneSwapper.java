package gui.model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneSwapper {

    public void sceneSwitch(Stage window, String fxmlClassName) throws IOException {
        Parent hubView = FXMLLoader.load(getClass().getResource(fxmlClassName));
        Scene hubViewScene = new Scene(hubView);
        window.setScene(hubViewScene);
        window.show();
    }
}
