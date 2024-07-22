package todoList.controller;

import java.io.FileWriter;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import todoList.model.Task;

public class TaskController {

    @FXML
    private Label text;

    public void setData(Task task) {
        text.setText(task.getText());
    }

    public void saveTask(Task task) throws IOException {
        FileWriter fileWriter = new FileWriter("tasks.txt", true);
        fileWriter.write(task.getText() + "\n");
        fileWriter.close();
    }

}
