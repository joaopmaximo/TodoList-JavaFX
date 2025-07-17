package todoList.controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import todoList.model.Task;

public class TaskController {

    private MainController mainController;

    private int taskId;

    @FXML
    private HBox taskItem;

    @FXML
    private Label taskContent;

    @FXML
    private CheckBox checkedBox;


    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    // fill the FXML task template with the task data
    public void setData(Task task) {
        this.taskId = task.getId();
        taskContent.setText(task.getContent());

        if (task.getChecked()) {
            checkedBox.setSelected(true);
            taskItem.getStyleClass().add("disabled");
        }
    }

    public void deleteTask() throws IOException {
        mainController.deleteTask(this.taskId);
    }

    public void toggleChecked() throws IOException {
        if (checkedBox.isSelected()) {
            taskItem.getStyleClass().add("disabled");
        } else {
            taskItem.getStyleClass().remove("disabled");
        }

        mainController.toggleChecked(this.taskId, checkedBox.isSelected());
    }

}
