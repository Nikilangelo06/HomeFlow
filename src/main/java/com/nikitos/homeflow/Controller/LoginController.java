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
import javafx.stage.Stage;

public class LoginController {
    @FXML
    protected void handleClientLogin(ActionEvent event) {
        // Переходим на страницу "Составления заявки"
        PageHelper.goToPage("form_creation_page.fxml", event);
    }

    @FXML
    protected void handleOperatorLogin(ActionEvent event) {
        PageHelper.goToPage("password_operator_page.fxml", event);
    }
}