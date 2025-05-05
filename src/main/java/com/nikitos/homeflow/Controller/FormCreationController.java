package com.nikitos.homeflow.Controller;

import com.nikitos.homeflow.MainApp;
import com.nikitos.homeflow.Util.AlertHelper;
import com.nikitos.homeflow.Util.PageHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FormCreationController {
    @FXML
    private CheckBox electric_CheckBox;
    @FXML
    private CheckBox fitter_CheckBox;
    @FXML
    private CheckBox plumber_CheckBox;
    @FXML
    private CheckBox gasman_CheckBox;
    @FXML
    private TextField address_TextField;
    @FXML
    private TextField telephone_TextField;
    @FXML
    private TextField fullName_TextField;


    @FXML
    protected void handleSendRequest(ActionEvent event) {
        AlertHelper.showAlert("Ваша заявка принята, ожидайте звонка от мастера.");
    }


    @FXML
    protected void handleClickBackButton(ActionEvent event) {
        // Возвращаемся на страницу входа
        PageHelper.goToPage("login_page.fxml", event);
    }
}
