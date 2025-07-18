# TodoList with JavaFX

![TodoList App](/src/main/resources/img/todo-print.png?raw=true "TodoList")

## How to distribute the application using Jpackage

Generate a runtime image, ensure that all modules are included in the module-info.java. I used javafx maven plugin in this

```console
mvn javafx:jlink
```

Finally use Jpackage to create the installer

```console
jpackage --name todoList  --app-version 3.0 --module todoList/todoList.Main --runtime-image target/image --icon src/main/resources/img/lista.ico --win-menu --win-shortcut
```
