package pl.noname;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import pl.noname.controllers.MainController;

import java.io.*;

/**
 * Hello world!
 */
public class App extends Application {
    public static void main(String[] args) {

        launch();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("main_view.fxml"));
        new MainController(loader, primaryStage);
    }
}
