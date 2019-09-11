package sample;

import SemaphoreLogic.Basket;
import SemaphoreLogic.Child;
import SemaphoreLogic.Park;
import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private TableView<Child> tblChildren;

    @FXML
    private TableColumn<Child, String> idColumn;

    @FXML
    private TableColumn<Child, String> ballColumn;

    @FXML
    private TableColumn<Child, String> timePlayingColumn;

    @FXML
    private TableColumn<Child, String> timeQuietColumn;

    @FXML
    private TextField txtIdChild;

    @FXML
    private CheckBox checkBoxBall;

    @FXML
    private TextField txtTimePlaying;

    @FXML
    private TextField txtTimeQuiet;

    @FXML
    private Label balls;

    @FXML
    private VBox childsBox;

    private Park park = new Park(3);

    @FXML
    void addChild(ActionEvent event) throws MalformedURLException {
        ImageView image = new ImageView();
        try {
            image.setImage(new Image(new FileInputStream("/home/samuel-alves/Documentos/Brincadeira-de-criancas/src/resources/frente.png")));
            image.setFitHeight(70);
            image.setFitWidth(70);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Child newChild = new Child();
        getValues(newChild);
        park.addChild(newChild);
        Circle circle = new Circle(40, Color.BLUE);
        //childsBox.getChildren().add(circle);
        childsBox.getChildren().add(image);
        pathTransition(image, newChild.getTimePlaying(), newChild.getTimeQuiet());
        cleanFields();
        updateList();
    }

    @FXML
    void updateTableRealTime(ActionEvent event) {
        updateList();
        System.out.println("Tamanho da lista: " + park.getList().size());
        System.out.println("BOLAS: " + Basket.balls);
        balls.setText(String.valueOf(Basket.balls));
    }

    private void getValues(Child child) {
        child.setIdChild(Integer.parseInt(txtIdChild.getText()));
        child.setTimePlaying(Integer.parseInt(txtTimePlaying.getText()));
        child.setTimeQuiet(Integer.parseInt(txtTimeQuiet.getText()));
        child.setBall(checkBoxBall.isSelected());
    }

    private void configureList() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idChild"));
        ballColumn.setCellValueFactory(new PropertyValueFactory<>("ball"));
        timePlayingColumn.setCellValueFactory(new PropertyValueFactory<>("timePlayingCounter"));
        timeQuietColumn.setCellValueFactory(new PropertyValueFactory<>("timeQuietCounter"));
    }

    private void pathTransition(ImageView image, int timePlaying, int timeQuiet) {
        Path path = new Path();
        path.getElements().addAll(new MoveTo(50, 50), new HLineTo(350));
        path.setFill(null);

        PathTransition pt = new PathTransition(Duration.millis(timePlaying * 100), path, image);
        pt.setCycleCount(Animation.INDEFINITE);
        pt.setAutoReverse(true);
        pt.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configureList();
        updateList();
        balls.setText(String.valueOf(Basket.balls));
    }

    private void updateList() {
        tblChildren.getItems().setAll(park.getList());
    }

    private void cleanFields() {
        txtIdChild.setText("");
        txtTimePlaying.setText("");
        txtTimeQuiet.setText("");
        checkBoxBall.setSelected(false);
    }

}
