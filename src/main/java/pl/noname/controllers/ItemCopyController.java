package pl.noname.controllers;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import org.controlsfx.control.Notifications;
import java.io.*;

public class ItemCopyController{

    private final File src;
    private final MainController mainController;
    private final boolean overwrite;
    private Task copyTask;
    private String dest;

    @FXML
    Label pathLabel;
    @FXML
    ProgressBar progressBar;
    @FXML
    Button stopButton;


    public ItemCopyController(File src, String dest, boolean overwrite, MainController mainController) {
        this.src = src;
        this.dest = dest;
        this.overwrite = overwrite;
        this.mainController = mainController;
    }

    public void start(){
        copyTask = new Task<Void>() {
            @Override
            public Void call() {
                final int bytesInMegabyte = 1_000_000;
                final int buffor = 2048;
                int newFileValue = 0;
                String additionalNameValue = "";
                String destinationToCheck = dest;
                String fileName = src.getName();

                if(!overwrite){
                    while(new File(destinationToCheck + "\\" + additionalNameValue + fileName).isFile()){
                        newFileValue++;
                        additionalNameValue = "(" + newFileValue + ")";
                    }
                }
                dest = destinationToCheck + "\\" + additionalNameValue + fileName;

                try (InputStream inputStream
                             = new FileInputStream(src.getAbsolutePath());
                     OutputStream outputStream
                             = new FileOutputStream(new File(dest))) {
                    float written = 0;
                    long total = new File(src.getAbsolutePath()).length() / bytesInMegabyte;
                    int read;
                    byte[] bytes = new byte[buffor];

                    while ((read = inputStream.read(bytes)) != -1) {
                        if(isCancelled()) break;
                        written += read;
                        float writtenMb = written / bytesInMegabyte;
                        updateProgress(writtenMb, total);
                        outputStream.write(bytes, 0, read);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public void succeeded(){
                Notifications.create().text("Copying file " + src + " completed successfully!").show();
            }

            @Override
            public void cancelled(){
                Notifications.create().text("Copying file " + src + " has been cancelled!").show();
                new File(dest).delete();
            }

        };
        String pathLabelText = String.format("Copying from '%s' to '%s'", src.getAbsolutePath(), dest);
        pathLabel.setText(pathLabelText);
        progressBar.progressProperty().bind(copyTask.progressProperty());
        copyTask.setOnSucceeded(event -> stopButton.setText("Done"));
        new Thread(copyTask).start();
    }

    public void handleStopButton() {
        copyTask.cancel();
        mainController.removeFileCopyOperation(this);
    }
}
