package todoList.controller;

import java.io.File;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import todoList.model.Task;

public class MainController {

    private FileController fileController;
    private JSONArray tasksListJson;
    private int tasksCurrentId = 0;

    private double mouseX = 0;
    private double mouseY = 0;

    @FXML
    private BorderPane mainPane;

    @FXML
    private VBox taskList;

    @FXML
    private TextField newTaskField;

    public MainController() {
        fileController = new FileController();
        tasksListJson = new JSONArray();
    }

    // get all the persisted tasks in the json file to the app
    public void getTasks() {
        try {
            taskList.getChildren().clear();

            // get the content from the json file and put in a jsonArray
            tasksListJson = new JSONArray(fileController.getTasksFileContent());

            // for each jsonObject in the jsonArray, create a task in the application using
            // the FXML file
            for (int i = 0; i < tasksListJson.length(); i++) {
                if (tasksCurrentId < tasksListJson.getJSONObject(i).getInt("id")) {
                    tasksCurrentId += tasksListJson.getJSONObject(i).getInt("id");
                }

                JSONObject taskJson = new JSONObject(tasksListJson.getJSONObject(i).toMap());
                Task task = new Task(taskJson.getInt("id"), taskJson.getString("content"),
                        taskJson.getBoolean("checked"));
                addItemToTaskList(task);
            }
        } catch (IOException e) {
            e.printStackTrace();
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
            Task task = new Task(tasksCurrentId++, newTaskField.getText());
            addItemToTaskList(task);
            saveTaskJson(task);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // cleans the textField for the next input
        newTaskField.clear();
    }

    public void deleteTask(int taskId) {
        for (int i = tasksListJson.length() - 1; i >= 0; i--) {
            if (taskId == tasksListJson.getJSONObject(i).getInt("id")) {
                tasksListJson.remove(i);
                taskList.getChildren().remove(i);
                fileController.updateTasksFile(tasksListJson);
                break;
            }
        }
    }

    public void toggleChecked(int taskId, boolean isSelected) {
        for (int i = tasksListJson.length() - 1; i >= 0; i--) {
            if (taskId == tasksListJson.getJSONObject(i).getInt("id")) {
                tasksListJson.getJSONObject(i).put("checked", isSelected);
                fileController.updateTasksFile(tasksListJson);
                break;
            }
        }
    }

    // saves the task in json file, it uses the library org.json
    public void saveTaskJson(Task task) throws IOException {
        JSONObject taskJson = new JSONObject();
        taskJson.put("id", task.getId());
        taskJson.put("content", task.getContent());
        taskJson.put("checked", task.getChecked());

        tasksListJson.put(taskJson);
        fileController.updateTasksFile(tasksListJson);
    }

    public void addItemToTaskList(Task task) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/task.fxml"));
        HBox taskItem = loader.load();
        TaskController taskController = loader.getController();
        taskController.setMainController(this);
        taskController.setData(task);
        taskList.getChildren().add(taskItem);
    }

    public void switchMode() {
        Scene scene = mainPane.getScene();
        String darkModeCss = getClass().getResource("/css/dark-mode.css").toExternalForm();

        if (scene.getStylesheets().size() > 1) {
            scene.getStylesheets().remove(darkModeCss);
            return;
        }

        scene.getStylesheets().add(darkModeCss);
    }

    public void showConfig() {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Selecione o diretório");
        chooser.setInitialDirectory(fileController.getDefaultTasksFilePath().toFile());
        File directory = chooser.showDialog(mainPane.getScene().getWindow());

        if (directory != null) {
            fileController.setTasksFilePath(directory.toString());
            getTasks();
        }
    }

    public void closeProgram() {
        Stage stage = (Stage) mainPane.getScene().getWindow();
        stage.close();
    }

    public void minimizeProgram() {
        Stage stage = (Stage) mainPane.getScene().getWindow();
        stage.setIconified(true);
    }

    public void getMouseLocation(MouseEvent e) {
        mouseX = e.getSceneX();
        mouseY = e.getSceneY();
    }

    public void moveWindow(MouseEvent e) {
        Stage stage = (Stage) mainPane.getScene().getWindow();
        stage.setX(e.getScreenX() - mouseX);
        stage.setY(e.getScreenY() - mouseY);
    }

}
