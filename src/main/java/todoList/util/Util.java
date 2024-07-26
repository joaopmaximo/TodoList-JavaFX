package todoList.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Util {
    public static String appData = System.getenv("APPDATA");
    public static Path path = Paths.get(Util.appData, "TodoList", "tasks.json");
    public static File file = new File(Util.appData + "/TodoList/tasks.json");

    public static Boolean isFileEmptyOrNoExists(File file) {
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            if (bufferedReader.readLine() == null) {
                fileReader.close();
                bufferedReader.close();
                return true;
            }

            if (bufferedReader.readLine().isBlank()) {
                fileReader.close();
                bufferedReader.close();
                return true;
            }

            fileReader.close();
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            return true;
        } catch (IOException e) {
            return true;
        }

        return false;
    }

}
