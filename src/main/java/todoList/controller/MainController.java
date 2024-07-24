package todoList.controller;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import todoList.model.Task;
import todoList.util.Util;

public class MainController {

    @FXML
    private Pane mainPane;

    @FXML
    private VBox taskList;

    @FXML
    private TextField newTaskField;

    // get all the persisted tasks in the json file to the app
    public void getTasks() {
        File file = new File("tasks.json");

        if (Util.isFileEmptyOrNoExists(file)) {
            return;
        }

        try {
            // get the content from the json file and put in a jsonArray
            Path path = Paths.get("tasks.json");
            String fileContent = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
            JSONArray tasksListJson = new JSONArray(fileContent);

            // for each jsonObject in the jsonArray, create a task in the application using
            // the FXML file
            for (int i = 0; i < tasksListJson.length(); i++) {
                JSONObject taskJson = new JSONObject(tasksListJson.getJSONObject(i).toMap());
                Task task = new Task(taskJson.getString("content"), taskJson.getBoolean("checked"));
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/task.fxml"));
                HBox taskItem = loader.load();
                TaskController taskController = loader.getController();
                taskController.setData(task);
                taskList.getChildren().add(taskItem);
            }
        } catch (IOException e) {
            return;
        }
    }

    // add a new task into the application and persists into a json file
    public void addTask() {

        // prevent empty tasks, checking if the textfield is empty
        if (newTaskField.getText().isBlank()) {
            return;
        }

        // create a new task, uses a FXML file as template in the application. the task
        // will be saved into a json file
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

        // cleans the textField for the next input
        newTaskField.clear();
    }

}
