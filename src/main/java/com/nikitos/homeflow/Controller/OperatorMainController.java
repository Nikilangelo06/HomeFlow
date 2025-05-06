package com.nikitos.homeflow.Controller;

import com.nikitos.homeflow.Util.AlertHelper;
import com.nikitos.homeflow.Util.PageHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class OperatorMainController {
    @FXML
    protected void handleClickBackButton(ActionEvent event) {
        PageHelper.goToPage("password_operator_page.fxml", event);
    }

    @FXML
    protected void handleClickAddWorker(ActionEvent event) {
        AlertHelper.showAlert("Ага");
    }
}
