package sample;

import SemaphoreLogic.Basket;
import SemaphoreLogic.Child;
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

    List<Child> list = new ArrayList<>();

    @FXML
    void addChild(ActionEvent event) throws IOException {
        Child newChild = new Child();
        getValues(newChild);
        list.add(newChild);
        cleanFields();
        updateList();
        newChild.start();
    }

    @FXML
    void updateTableRealTime(ActionEvent event) throws IOException {
        updateList();
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
        tblChildren.getItems().setAll(list);
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
