package pl.noname.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainController {


    @FXML
    VBox containerForFileCopyAnchorPanes;

    Map<ItemCopyController, Node> itemCopyControllerMap = new HashMap<>();

    private final Stage stage;
    private final Scene scene;

    public MainController(FXMLLoader loader, Stage stage) throws IOException {
        loader.setController(this);
        this.stage = stage;
        this.scene = new Scene(loader.load());
        stage.setScene(scene);
        initializeNewFileCopyOperation(new File("lala"), new File("lala"));

        stage.show();
    }

    public void initializeNewFileCopyOperation(File src, File dest) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("file_copy_anchor_pane.fxml"));
        ItemCopyController controller = new ItemCopyController(src, dest, this);
        controller.start();
        loader.setController(controller);
        Node fileCopyAnchorPane = loader.load();
        itemCopyControllerMap.put(controller, fileCopyAnchorPane);
        containerForFileCopyAnchorPanes.getChildren().add(fileCopyAnchorPane);

    }

    public void handleStopAllButton() {
        itemCopyControllerMap.forEach((key, value) -> removeFileCopyAnchorPane(key));
        itemCopyControllerMap.clear();
    }

    public void removeFileCopyAnchorPane(ItemCopyController itemCopyController) {

        Node node = itemCopyControllerMap.get(itemCopyController);
        containerForFileCopyAnchorPanes.getChildren().remove(node);
    public void handleCopyButton() throws IOException {
        FXMLLoader popUpLoader = new FXMLLoader(getClass().getClassLoader().getResource("popUpView.fxml"));
        PopUpController popUp = new PopUpController(popUpLoader);
    }
}
