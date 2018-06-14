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


    private final Stage stage;
    private final Scene scene;
    @FXML
    VBox containerForFileCopyAnchorPanes;
    Map<ItemCopyController, Node> itemCopyControllerMap = new HashMap<>();

    public MainController(FXMLLoader loader, Stage stage) throws IOException {
        loader.setController(this);
        this.stage = stage;
        this.scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    public void initializeNewFileCopyOperation(File src, String dest, boolean overwrite) throws IOException {
        System.out.println(src.toString() + "     " + dest);
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("file_copy_anchor_pane.fxml"));
        ItemCopyController controller = new ItemCopyController(src, dest, overwrite, this);
        loader.setController(controller);
        Node fileCopyAnchorPane = loader.load();
        itemCopyControllerMap.put(controller, fileCopyAnchorPane);
        containerForFileCopyAnchorPanes.getChildren().add(fileCopyAnchorPane);
        controller.start();

    }

    public void handleStopAllButton() {
        itemCopyControllerMap.forEach((key, value) -> containerForFileCopyAnchorPanes.getChildren().remove(value));
        itemCopyControllerMap.forEach((key, value) -> key.stop());
        itemCopyControllerMap.clear();
    }

    public void removeFileCopyOperation(ItemCopyController itemCopyController) {

        Node node = itemCopyControllerMap.get(itemCopyController);
        containerForFileCopyAnchorPanes.getChildren().remove(node);
        itemCopyControllerMap.remove(itemCopyController);
    }

    public void handleCopyButton() throws IOException {
        FXMLLoader popUpLoader = new FXMLLoader(getClass().getClassLoader().getResource("popUpView.fxml"));
        PopUpController popUp = new PopUpController(popUpLoader, this);
    }
}
