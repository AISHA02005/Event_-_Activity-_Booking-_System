package org.example;

import com.bookingsystem.event.service.DataIntitalizer;
import com.bookingsystem.event.service.EventService;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {

        // 1. init scene manager
        SceneManager.getInstance().init(stage);

        // 2. 🔥 هنا بنجهز الداتا
        EventService eventService = new EventService();
        DataIntitalizer initializer = new DataIntitalizer(eventService);
        initializer.loadSampleData();

        // 3. نعرض البداية
        SceneManager.getInstance().showSplash();

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}