package todoList.controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import todoList.model.Task;
import todoList.util.Util;

public class TaskController {

    private MainController mainController;

    @FXML
    private Label taskContent;

    @FXML
    private CheckBox checkedBox;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    // fill the FXML task template with the task data
    public void setData(Task task) {
        taskContent.setText(task.getContent());

        if (task.getChecked()) {
            checkedBox.setSelected(true);
            taskContent.getStyleClass().add("disabled");
        }
    }

    public void deleteTask() throws IOException {
        // finding the task by the content
        int i = 0;
        while (taskContent.getText() != Util.taskListJson.getJSONObject(i).get("content")) {
            i++;
        }

        // removing the task from the list, either json file and application ui
        Util.taskListJson.remove(i);
        mainController.deleteItemFromTaskList(i);

        // saving the list with the updated task to the json file
        Util.updateTaskJsonFile();
    }

    public void toggleChecked() throws IOException {
        if (checkedBox.isSelected()) {
            taskContent.getStyleClass().add("disabled");
        } else {
            taskContent.getStyleClass().remove("disabled");
        }

        // finding the task by the content
        int i = 0;
        while (taskContent.getText() != Util.taskListJson.getJSONObject(i).get("content")) {
            i++;
        }

        // updating the checked attribute from the found task
        Util.taskListJson.getJSONObject(i).put("checked", checkedBox.isSelected());

        // saving the list with the updated task to the json file
        Util.updateTaskJsonFile();
    }

}
