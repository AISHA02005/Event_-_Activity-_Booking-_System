package org.example;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        SceneManager.getInstance().init(stage);
        SceneManager.getInstance().showSplash();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}