package todoList.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import todoList.model.Task;

public class MainController {

    @FXML
    private Pane mainPane;

    @FXML
    private VBox taskList;

    @FXML
    private TextField newTaskField;

    public void addTask() {
        try {
            Task task = new Task(newTaskField.getText());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/task.fxml"));
            HBox taskItem = loader.load();
            TaskController taskController = loader.getController();
            taskController.setData(task);
            taskList.getChildren().add(taskItem);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
