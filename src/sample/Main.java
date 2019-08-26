package sample;

import SemaphoreLogic.Child;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Brincadeira de Crianças");
        Scene scene = new Scene(root, 500, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Child c1 = new Child(0, true, 50, 5);
        Child c2 = new Child(1, true, 25, 5);
        c1.start();
        c2.start();
        launch(args);
    }
}
