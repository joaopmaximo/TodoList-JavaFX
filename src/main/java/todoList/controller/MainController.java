package todoList.controller;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.logging.*;

import org.json.JSONArray;
import org.json.JSONObject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import todoList.model.Task;
import todoList.util.LogConfig;

import java.awt.Desktop;

public class MainController {

    private FileController fileController;
    private JSONArray tasksListJson;
    private int tasksCurrentId = 0;
    private static final Logger logger = LogConfig.getLogger();

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
            logger.log(Level.SEVERE, "Erro inesperado", e);
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
            logger.log(Level.SEVERE, "Erro inesperado", e);
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
        String darkModeCss = fileController.getDarkModeStyleFilePath();

        if (scene.getStylesheets().contains(darkModeCss)) {
            scene.getStylesheets().remove(darkModeCss);
            return;
        }

        scene.getStylesheets().add(darkModeCss);
    }

    public void showConfig() {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Selecione o diretório");
        chooser.setInitialDirectory(fileController.getdefaultPath().toFile());
        File directory = chooser.showDialog(mainPane.getScene().getWindow());

        if (directory != null) {
            fileController.setTasksFilePath(directory.toString());
            getTasks();
        }
    }

    public void openGithub() {
        try {
            Desktop.getDesktop().browse(new URI("https://github.com/joaopmaximo"));
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro inesperado", e);
        }
    }

    public void openLinkedin() {
        try {
            Desktop.getDesktop().browse(new URI("https://www.linkedin.com/in/joao-pedro-maximo-da-silva/"));
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro inesperado", e);
        }
    }

    public void changeColor() {
        Color mainColor = Color.web(fileController.getMainColor());
        String mainColorHex = fileController.getMainColor();
        String mainColorCss = fileController.getMainColorFilePath().toUri().toString();

        Stage colorPickerStage = new Stage();
        ColorPicker colorPicker = new ColorPicker(mainColor);

        StackPane root = new StackPane(colorPicker);

        Scene scene = new Scene(root, 400, 50);
        Image icon = new Image(fileController.getIconFilePath());

        colorPicker.setOnAction(e -> {
            Color newColor = colorPicker.getValue();

            // converting to hex values, so the css can read
            String newColorHex = String.format("#%02X%02X%02X",
                    (int) (newColor.getRed() * 255),
                    (int) (newColor.getGreen() * 255),
                    (int) (newColor.getBlue() * 255));

            fileController.setMainColor(newColorHex);

            // updating the scene styles
            mainPane.getScene().getStylesheets().remove(mainColorCss);
            mainPane.getScene().getStylesheets().add(mainColorCss);

            // getting the main color and filling the window after changing
            root.setStyle("-fx-background-color: " + newColorHex);
            colorPicker.setStyle("-fx-color-label-visible: false; -fx-background-color: " + newColorHex);
        });

        // getting the main color and filling the window before change anything
        root.setStyle("-fx-background-color: " + mainColorHex);
        colorPicker.setStyle("-fx-color-label-visible: false; -fx-background-color: " + mainColorHex);

        colorPickerStage.setTitle("Selecionar cor");
        colorPickerStage.setScene(scene);
        colorPickerStage.getIcons().add(icon);
        colorPickerStage.setResizable(false);
        colorPickerStage.show();

    }
}
