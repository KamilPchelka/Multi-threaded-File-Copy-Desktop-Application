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

    private final Stage stage;
    private final Scene scene;
    private final FileChooser fileChooser = new FileChooser();
    private final DirectoryChooser directoryChooser = new DirectoryChooser();
    private final MainController mainController;
    private String fileName;
    private File sourceFile;
    private File destinationDirectory;
    @FXML
    private TextField source, destination;
    @FXML
    private CheckBox wantOverwrite, copyFolder;

    public PopUpController(FXMLLoader loader, MainController mainController) throws IOException {
        loader.setController(this);
        this.stage = new Stage();
        this.scene = new Scene(loader.load());
        this.mainController = mainController;
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void handleOkButton() throws IOException {
        if (sourceFile != null && destinationDirectory != null) {
            String srcPath = source.getText();
            String destPath = destination.getText();
            stage.close();
            if(copyFolder.isSelected()){
               copyFilesFromFolder(sourceFile);
            }
            else{
                mainController.initializeNewFileCopyOperation(new File(srcPath), destPath, wantOverwrite.isSelected());
            }
        }
    }

    public void handleCopyFolder(){
        sourceFile = null;
        destinationDirectory = null;
        source.setText("");
        destination.setText("");
    }

    public void handleCancelButton() {
        stage.close();
    }

    public void copyFilesFromFolder(final File folder) throws IOException {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                copyFilesFromFolder(fileEntry);
            } else {
                mainController.initializeNewFileCopyOperation(fileEntry, destination.getText(), wantOverwrite.isSelected());
            }
        }
    }

    public void handleChooseSource() {
        sourceFile = copyFolder.isSelected() ? directoryChooser.showDialog(stage) : fileChooser.showOpenDialog(stage);
        if (sourceFile != null) {
            String filepath = sourceFile.getAbsolutePath();
            source.setText(filepath);
            fileName = sourceFile.getName();
        }
    }

    public void handleChooseDestination() {
       destinationDirectory = directoryChooser.showDialog(stage);
        if (destinationDirectory != null) {
            String filepath = destinationDirectory.getAbsolutePath();
            destination.setText(filepath);
        }
    }
}
