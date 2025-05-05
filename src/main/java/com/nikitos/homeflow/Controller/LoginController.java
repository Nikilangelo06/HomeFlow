package com.nikitos.homeflow.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LoginController {
    @FXML
    private Label login_Label;

    @FXML
    protected void handleClientLogin() {
        login_Label.setText("NikitOS");
    }

    @FXML
    protected void handleOperatorLogin() {
        login_Label.setText("KingArtur1000");
    }
}