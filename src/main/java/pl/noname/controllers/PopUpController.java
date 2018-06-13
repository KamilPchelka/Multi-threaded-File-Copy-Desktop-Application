package pl.noname.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class PopUpController {

    private String fileName;
    private final Stage stage;
    private final Scene scene;
    private final FileChooser fileChooser = new FileChooser();
    private final DirectoryChooser directoryChooser = new DirectoryChooser();

    @FXML
    private TextField source, destination;
    @FXML
    private CheckBox wantOverwrite;

    public PopUpController(FXMLLoader loader) throws IOException {
        loader.setController(this);
        this.stage = new Stage();
        this.scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void handleOkButton(){
        if (source != null && destination != null){
            System.out.println(destination.getText() + "\\" + fileName);
            System.out.println(source.getText());
            System.out.println(wantOverwrite.isSelected());
            stage.close();
        }
    }

    public void handleCancelButton(){
        stage.close();
    }

    public void handleChooseSource(){
        File file = fileChooser.showOpenDialog(stage);
        if(file != null){
            String filepath = file.getAbsolutePath();
            source.setText(filepath);
            fileName = file.getName();
        }
    }

    public void handleChooseDestination(){
        File selectedDirectory = directoryChooser.showDialog(stage);
        if(selectedDirectory != null){
            String filepath = selectedDirectory.getAbsolutePath();
            destination.setText(filepath);
        }
    }
}
