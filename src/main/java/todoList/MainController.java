package todoList;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MainController { 

    @FXML
    private Pane mainPane;

    @FXML
    VBox tasksList;

    public void addTask() {
        taskPane task = new taskPane("beleza");
        tasksList.getChildren().add(task);
    }

}
