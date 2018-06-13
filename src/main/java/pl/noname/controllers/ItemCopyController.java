package pl.noname.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.io.File;

public class ItemCopyController {

    private final File src;
    private final File dest;
    private final MainController mainController;

    @FXML
    Label pathLabel;
    @FXML
    ProgressBar progressBar;

    public ItemCopyController(File src, File dest, MainController mainController) {
        this.src = src;
        this.dest = dest;
        this.mainController = mainController;
    }


    public void start() {
        String pathLabelText = String.format("Copying from '%s' to '%s'", src.getAbsolutePath(), dest.getAbsolutePath());
        pathLabel.setText(pathLabelText);
        progressBar.setProgress(0.8);
    }

    public void stop() {
    }

    public void handleStopButton() {
        mainController.removeFileCopyOperation(this);
    }
}
