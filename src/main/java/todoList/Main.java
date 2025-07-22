package todoList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import todoList.controller.FileController;
import todoList.controller.MainController;
import todoList.util.LogConfig;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main.fxml"));
            Parent root = loader.load(); // root node, required by scene
            Scene scene = new Scene(root); // scene, required by stage
            
            FileController fileController = new FileController();

            Image icon = new Image(fileController.getIconFilePath());

            MainController mainController = loader.getController();
            mainController.initColorMode();
            mainController.getTasks();

            stage.setTitle("To Do List");
            stage.getIcons().add(icon);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            FileController.resetFiles();
            LogConfig.logAndShowError("Erro ao iniciar", e);
        }
    }

}