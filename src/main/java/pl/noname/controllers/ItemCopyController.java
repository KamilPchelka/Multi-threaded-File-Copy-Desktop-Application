package pl.noname.controllers;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.io.*;

public class ItemCopyController{

    private final File src;
    private final String dest;
    private final MainController mainController;
    private Task copyTask;

    @FXML
    Label pathLabel;
    @FXML
    ProgressBar progressBar;
    @FXML
    Button stopButton;


    public ItemCopyController(File src, String dest, MainController mainController) {
        this.src = src;
        this.dest = dest;
        this.mainController = mainController;
    }

    public void start(){
        copyTask = new Task<Void>() {
            @Override
            public Void call() {
                try (InputStream inputStream
                             = new FileInputStream(src.getAbsolutePath());
                     OutputStream outputStream
                             = new FileOutputStream(new File(dest))) {

                    float written = 0;
                    long total = new File(src.getAbsolutePath()).length() / 1000000;
                    int read;
                    byte[] bytes = new byte[100];
                    while ((read = inputStream.read(bytes)) != -1) {
                        if(isCancelled()) break;
                        written += read;
                        float writenMb = written / 1000000;
                        System.out.println("Thread " + "is: " + ((writenMb / total) * 100) + "% done");
                        updateProgress(writenMb, total);
                        outputStream.write(bytes, 0, read);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public void cancelled(){
                new File(dest).delete();
            }
        };
        String pathLabelText = String.format("Copying from '%s' to '%s'", src.getAbsolutePath(), dest);
        pathLabel.setText(pathLabelText);
        progressBar.progressProperty().bind(copyTask.progressProperty());
        copyTask.setOnSucceeded(event -> stopButton.setText("Done"));
        new Thread(copyTask).start();
    }

    public void stop() {
        copyTask.cancel();
    }

    public void handleStopButton() {
        copyTask.cancel();
        mainController.removeFileCopyOperation(this);
    }
}
