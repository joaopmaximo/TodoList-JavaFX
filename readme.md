jlink --module-path $Env:JAVA_HOME/jmods/javafx-jmods-17.0.12 --add-modules ALL-MODULE-PATH --output runtime

mvn package

jpackage --main-jar TodoList-1.0.jar --runtime-image runtime/ --name TodoList --input target/ --icon src/main/resources/img/lista.ico