package com.nikitos.homeflow.Controller;

import com.nikitos.homeflow.Util.AlertHelper;
import com.nikitos.homeflow.Util.PageHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class PasswordOperatorController {
    @FXML
    protected void handleClickBackButton(ActionEvent event) {
        PageHelper.goToPage("login_page.fxml", event);
    }

    @FXML
    protected void handleClickEnterButton(ActionEvent event) {
        AlertHelper.showAlert("Вход в систему...");
    }
}
