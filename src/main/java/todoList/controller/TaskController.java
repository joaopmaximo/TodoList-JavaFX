package todoList.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import todoList.model.Task;

public class TaskController {

    @FXML
    private Label text;

    public void setData(Task task) {
        text.setText(task.getText());
    }

}
