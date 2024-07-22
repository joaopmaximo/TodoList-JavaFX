package todoList.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
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

    public void getTasks() throws FileNotFoundException {
        FileReader fileReader = new FileReader("tasks.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        @SuppressWarnings("unused")
        String line;

        try {
            while ((line = bufferedReader.readLine()) != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/task.fxml"));
                HBox taskItem = loader.load();
                TaskController taskController = loader.getController();
                Task task = new Task(line);
                taskController.setData(task);
                taskList.getChildren().add(taskItem);
            }
            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addTask() {
        try {
            Task task = new Task(newTaskField.getText());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/task.fxml"));
            HBox taskItem = loader.load();
            TaskController taskController = loader.getController();
            taskController.setData(task);
            taskController.saveTask(task);
            taskList.getChildren().add(taskItem);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
