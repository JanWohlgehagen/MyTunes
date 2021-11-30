package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/view/MainView.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
        stage.setResizable(false);
    }
    // testestetsetsetsetse

    /**
     * This is a test comment
     */
    public static void main(String[] args) {
        launch(args);
    }
}