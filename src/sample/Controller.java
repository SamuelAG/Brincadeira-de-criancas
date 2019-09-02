package sample;

import SemaphoreLogic.Basket;
import SemaphoreLogic.Child;
import SemaphoreLogic.Park;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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

    private Park park = new Park(5);

    @FXML
    void addChild(ActionEvent event) throws IOException {
        Child newChild = new Child();
        getValues(newChild);
        park.addChild(newChild);
        cleanFields();
        updateList();
    }

    @FXML
    void updateTableRealTime(ActionEvent event) throws IOException {
        updateList();
        System.out.println("Tamanho da lista: " + park.getList().size());
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
        timePlayingColumn.setCellValueFactory(new PropertyValueFactory<>("timePlaying"));
        timeQuietColumn.setCellValueFactory(new PropertyValueFactory<>("timeQuiet"));
    }

    private void updateList() {
        tblChildren.getItems().setAll(park.getList());
    }

    private void cleanFields() {
        txtIdChild.setText("");
        txtTimePlaying.setText("");
        timePlayingColumn.setText("");
        checkBoxBall.setSelected(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configureList();
        updateList();
    }
}
