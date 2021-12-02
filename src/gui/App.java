package gui;

import gui.controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    private static FXMLLoader fxmlLoaderMain;

    @Override
    public void start(Stage stage) throws Exception {

        fxmlLoaderMain = new FXMLLoader((getClass().getResource("view/MainView.fxml")));
        stage.setScene(new Scene(fxmlLoaderMain.load()));
        stage.show();
        stage.setResizable(false);

    }

    public MainController getController(){
        return fxmlLoaderMain.getController();
    }

    public static void main(String[] args) {
        launch(args);
    }
}