package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class InitController {

    @FXML
    private TextField txtMaxBalls;

    @FXML
    private Button button;

    public static int maxBalls;

    public void onClick(javafx.event.ActionEvent actionEvent) {
        maxBalls = Integer.parseInt(txtMaxBalls.getText());
        Stage stage = (Stage) button.getScene().getWindow();
        // do what you have to do
        stage.close();
        Stage primaryStage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setResizable(false);
        primaryStage.setTitle("Brincadeira de Crianças");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
