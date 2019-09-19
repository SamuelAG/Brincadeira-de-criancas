package sample;

import SemaphoreLogic.Basket;
import SemaphoreLogic.CallBack;
import SemaphoreLogic.Child;
import SemaphoreLogic.Park;
import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.*;
import javafx.util.Duration;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    private HBox childsBox;

    @FXML
    private TextArea log;
    private StringBuilder buffer;

    @FXML
    private ImageView basket;
    private String[] images = new String[11];
    private int count;

    private int maxBalls = InitController.maxBalls;
    private Park park = new Park(maxBalls);

    private Path playingPath;
    private Path quietPath;
    private Path blockNoBallPath;
    private Path blockBasketFullPath;

    @FXML
    void addChild(ActionEvent event) {
        // Instancia uma nova criança, newChild
        Child newChild = new Child();
                        CallBack callBack = new CallBack() {
            @Override
            public void methodToCallBack() {
                updateList();
            }

            @Override
            public void addToLog(String message) {
                Platform.runLater(() -> {
                    buffer.append(message);
                    log.setText(buffer.toString());
                    log.positionCaret(log.getLength());
                });
            }

            @Override
            public void putABall() {
                count++;
                try {
                    basket.setImage(new Image(new FileInputStream(images[count])));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void getABall() {
                count--;
                try {
                    basket.setImage(new Image(new FileInputStream(images[count])));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void playing(Child child) {
                updatePath(child, playingPath);
            }

            @Override
            public void quiet(Child child) {
                updatePath(child, quietPath);
            }

            @Override
            public void blockNoBall(Child child) {
                updatePath(child, blockNoBallPath);
            }

            @Override
            public void blockFullBasket(Child child) {
                updatePath(child, blockBasketFullPath);
            }
        };

        // Pega os valores dos campos de texto e passa para dentro do objeto newChild
        getValues(newChild);
        newChild.setCallBack(callBack);
        // Adiciona na lista de crianças
        park.addChild(newChild);


        // Cria uma nova imagem
        ImageView image = new ImageView();
        try {
            // Pega a imagem no diretorio e seta a altura e largura
            image.setImage(new Image(new FileInputStream(newChild.isBall() ?
                    "/home/samuel-alves/Downloads/Brincadeira-de-criancas/src/sample/resources/child/playing_1.png" :
                    "/home/samuel-alves/Downloads/Brincadeira-de-criancas/src/sample/resources/block.jpeg")));
            image.setFitHeight(70);
            image.setFitWidth(70);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Adiciona a nova imagem na Vertical Box
        childsBox.getChildren().add(image);
        // Chama a função que realiza a animação
        pathTransition(image, newChild.isBall() ? playingPath : quietPath);
        // Limpa os campos de texto
        cleanFields();
        // Atualiza a lista
        updateList();
    }

    private void updatePath(Child child, Path path) {
        Platform.runLater(() -> {
            int index = park.getList().indexOf(child);
            childsBox.getChildren().remove(0);
            ImageView image = new ImageView();
            try {
                String imageURL = "";
                if(path == playingPath) {
                    imageURL = "/home/samuel-alves/Downloads/Brincadeira-de-criancas/src/sample/resources/child/playing_1.png";
                } else if(path == quietPath) {
                    imageURL = "/home/samuel-alves/Downloads/Brincadeira-de-criancas/src/sample/resources/block.jpeg";
                } else if(path == blockNoBallPath) {
                    imageURL = "/home/samuel-alves/Downloads/Brincadeira-de-criancas/src/sample/resources/block.jpeg";
                } else if(path == blockBasketFullPath) {
                    imageURL = "/home/samuel-alves/Downloads/Brincadeira-de-criancas/src/sample/resources/child/blockBasketFull.png";
                }
                image.setImage(new Image(new FileInputStream(imageURL)));
                image.setFitHeight(70);
                image.setFitWidth(70);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            childsBox.getChildren().add(index, image);
            pathTransition(image, path);
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(maxBalls);
        playingPath = new Path();
        playingPath.getElements().addAll(new MoveTo(50, 280), new LineTo(50, 260));
        quietPath = new Path();
        quietPath.getElements().addAll(new MoveTo(50,280), new LineTo(70, 280));
        blockNoBallPath = new Path();
        blockNoBallPath.getElements().addAll(new MoveTo(50, 280), new LineTo(50, 280.001));
        blockBasketFullPath = new Path();
        blockBasketFullPath.getElements().addAll(new MoveTo(50, 280), new LineTo(50, 280.001));

        count = 0;
        images[0] = "/home/samuel-alves/Downloads/Brincadeira-de-criancas/src/sample/resources/basket/basket_0.png";
        images[1] = "/home/samuel-alves/Downloads/Brincadeira-de-criancas/src/sample/resources/basket/basket_1.png";
        images[2] = "/home/samuel-alves/Downloads/Brincadeira-de-criancas/src/sample/resources/basket/basket_2.png";
        images[3] = "/home/samuel-alves/Downloads/Brincadeira-de-criancas/src/sample/resources/basket/basket_3.png";
        images[4] = "/home/samuel-alves/Downloads/Brincadeira-de-criancas/src/sample/resources/basket/basket_4.png";
        images[5] = "/home/samuel-alves/Downloads/Brincadeira-de-criancas/src/sample/resources/basket/basket_5.png";
        images[6] = "/home/samuel-alves/Downloads/Brincadeira-de-criancas/src/sample/resources/basket/basket_6.png";
        images[7] = "/home/samuel-alves/Downloads/Brincadeira-de-criancas/src/sample/resources/basket/basket_7.png";
        images[8] = "/home/samuel-alves/Downloads/Brincadeira-de-criancas/src/sample/resources/basket/basket_8.png";
        images[9] = "/home/samuel-alves/Downloads/Brincadeira-de-criancas/src/sample/resources/basket/basket_9.png";
        images[10] = "/home/samuel-alves/Downloads/Brincadeira-de-criancas/src/sample/resources/basket/basket_10.png";

        basket.setFitWidth(200);
        basket.setFitHeight(190);

        try {
            basket.setImage(new Image(new FileInputStream(images[Basket.balls])));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        buffer = new StringBuilder();
        log.setText(buffer.toString());
        configureList();
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

    private void pathTransition(ImageView image, Path path) {
        // Cria um novo PathTransition passando como parametro a duração, caminho e um elemento (Nesse caso a imagem)
        PathTransition pt = new PathTransition(Duration.millis(1000), path, image);
        pt.setCycleCount(Animation.INDEFINITE);
        pt.setAutoReverse(true);
        pt.play();
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
        Platform.runLater(() -> tblChildren.getItems().setAll(park.getList()));
    }

    private void cleanFields() {
        txtIdChild.setText("");
        txtTimePlaying.setText("");
        txtTimeQuiet.setText("");
        checkBoxBall.setSelected(false);
    }
}
