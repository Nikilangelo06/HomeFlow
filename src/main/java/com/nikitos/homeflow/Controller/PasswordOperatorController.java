package com.nikitos.homeflow.Controller;

import com.nikitos.homeflow.Util.AlertHelper;
import com.nikitos.homeflow.Util.Config;
import com.nikitos.homeflow.Util.PageHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class PasswordOperatorController {
    @FXML
    private TextField password_TextField;

    @FXML
    protected void handleClickBackButton(ActionEvent event) {
        PageHelper.goToPage("login_page.fxml", event);
    }

    @FXML
    protected void handleClickEnterButton(ActionEvent event) {
        if (password_TextField.getText().equals(Config.passwordForOperator)) {
            PageHelper.goToPage("operator_main_page.fxml", event);
        }
        else {
            AlertHelper.showAlert("Неверный пароль!");
        }
    }
}
