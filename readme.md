# TodoList with JavaFX

![TodoList App](/src/main/resources/img/todo-print.png?raw=true "TodoList")

## How to distribute the application using Jpackage

Create a runtime image, ensure that JavaFX modules are included

```console
jlink --module-path $Env:JAVA_HOME/jmods/javafx-jmods-17.0.12 --add-modules ALL-MODULE-PATH --output runtime
```


Package the application (using Maven in this case)

```console
mvn package
```

Finally use Jpackage to create the installer

```console
jpackage --main-jar TodoList-2.0.jar --runtime-image runtime/ --name TodoList --input target/ --icon src/main/resources/img/lista.ico --win-menu --win-shortcut
```
