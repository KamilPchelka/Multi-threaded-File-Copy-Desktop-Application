package pl.noname.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {


    private final Stage stage;
    private final Scene scene;

    public MainController(FXMLLoader loader, Stage stage) throws IOException {
        loader.setController(this);
        this.stage = stage;
        this.scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }


    public void handleCopyButton() {
        System.out.println("lalala");
    }
}
