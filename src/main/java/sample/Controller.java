package sample;

import bot.Bot;
import bot.Storage;
import com.jfoenix.controls.*;
import config.Config;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gard on 21.08.2017.
 */
public class Controller {
    private Bot bot;
    private Storage storage;

    @FXML
    private Pane dashboardPane;

    @FXML
    private JFXComboBox<String> listOfCompanies;

    @FXML
    private JFXButton testRunBot;

    @FXML
    private JFXToggleButton runBotOnTime;

    @FXML
    private JFXTextField newCompeny;

    @FXML
    private JFXButton screenshot;

    @FXML
    private JFXButton untreated;

    @FXML
    private JFXButton addcompany;

    @FXML
    private JFXButton removeCompany;

    @FXML
    private Pane rootPane;

    @FXML
    private JFXTextField username;

    @FXML
    private JFXTextField password;

    @FXML
    private JFXButton login;

    public Controller() {
        this.bot = new Bot();
        this.storage = new Storage();
    }

    public void initialize() {

        getStoredCompanies();
    }

    private void getStoredCompanies() {
        listOfCompanies.setItems(FXCollections.observableList(storage.getStoredCompanies()));
    }

    @FXML
    void addcompanyAction(ActionEvent event) {

    }

    @FXML
    void goToScreenshotAction(ActionEvent event) {

    }

    @FXML
    void goToUntreatedAction(ActionEvent event) {

    }

    @FXML
    void removeCompanyAction(ActionEvent event) {

    }

    @FXML
    void runBotOnTimeAction(ActionEvent event) {

    }

    @FXML
    void testRunAction(ActionEvent event) {
        bot.startBot(1);
    }







    @FXML
        void loginAction(ActionEvent event) {
            if(username.getText().isEmpty() || password.getText().isEmpty()){
                //do nothing
            }else {
                Config config = new Config();
                config.writeToProp("username2",username.getText());
                config.writeToProp("password2",password.getText());
                //navigate to panel
                showDashboard();
            }

        }

    private void showDashboard() {

        System.out.println("switch");
        rootPane.setVisible(false);
       // rootPane.setDisable(true);
        dashboardPane.setVisible(true);
    }
}
