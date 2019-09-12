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
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
        // Instancia uma nova criança, newChild
        Child newChild = new Child();
        // Pega os valores dos campos de texto e passa para dentro do objeto newChild
        getValues(newChild);
        // Adiciona na lista de crianças
        park.addChild(newChild);

        // Cria uma nova imagem
        ImageView image = new ImageView();
        try {
            // Pega a imagem no diretorio e seta a altura e largura
            image.setImage(new Image(new FileInputStream("/home/samuel-alves/Documentos/Brincadeira-de-criancas/src/resources/frente.png")));
            image.setFitHeight(70);
            image.setFitWidth(70);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Adiciona a nova imagem na Vertical Box
        childsBox.getChildren().add(image);
        // Chama a função que realiza a animação
        pathTransition(image, newChild.getTimePlaying(), newChild.getTimeQuiet());
        // Limpa os campos de texto
        cleanFields();
        // Atualiza a lista
        updateList();
    }

    @FXML
    void removeChild(ActionEvent event) {
        // Remove a animação da thread
        childsBox.getChildren().remove(park.getList().indexOf(tblChildren.getSelectionModel().getSelectedItem()));
        // Para a execução da thread
        tblChildren.getSelectionModel().getSelectedItem().stop();
        // Remove a thread da lista
        park.getList().remove(tblChildren.getSelectionModel().getSelectedItem());
        // Atualiza a lista
        updateList();
    }

    private void pathTransition(ImageView image, int timePlaying, int timeQuiet) {
        // Instancia um novo caminho
        Path path = new Path();
        // Adiciona elemento a esse caminho
        path.getElements().addAll(new MoveTo(50, 50), new HLineTo(350));
        // Não sei o que faz quando eu copiei já tava ai
        path.setFill(null);

        // Cria um novo PathTransition passando como parametro a duração, caminho e um elemento (Nesse caso a imagem)
        PathTransition pt = new PathTransition(Duration.millis(timePlaying * 100), path, image);
        pt.setCycleCount(Animation.INDEFINITE);
        pt.setAutoReverse(true);
        pt.play();
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

    private void updateList() {
        tblChildren.getItems().setAll(park.getList());
    }

    private void cleanFields() {
        txtIdChild.setText("");
        txtTimePlaying.setText("");
        txtTimeQuiet.setText("");
        checkBoxBall.setSelected(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Thread update = new Thread(() -> {
            while (true) {
                //System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                for(int i = 0; i < park.getList().size(); i++) {

                    System.out.println(park.getList().get(i).getId());
                }
            }
        });
        update.start();
        configureList();
        updateList();
        balls.setText(String.valueOf(Basket.balls));
    }

}
