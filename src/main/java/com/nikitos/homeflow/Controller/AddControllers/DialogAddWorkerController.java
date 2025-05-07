package com.nikitos.homeflow.Controller.AddControllers;

import com.nikitos.homeflow.Model.Worker;
import com.nikitos.homeflow.Model.WorkerDAO;
import com.nikitos.homeflow.Util.PageHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

import static com.nikitos.homeflow.Util.AlertHelper.showAlert;

public class DialogAddWorkerController {

    @FXML
    private TextField telephone_TextField;
    @FXML
    private ComboBox profession_ComboBox;
    @FXML
    private TextField fullName_TextField;

    private Stage dialogStage;
    private boolean saveClicked = false;


    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }


    public boolean isSaveClicked() {
        return saveClicked;
    }

    @FXML
    private void handleClickSaveButton() throws SQLException {
        if (isInputValid()) {
            Worker newWorker = new Worker("", 0, "");
            newWorker.setFullName(fullName_TextField.getText());
            newWorker.setPhoneNumber(telephone_TextField.getText());
            newWorker.setProfession(profession_ComboBox.getVisibleRowCount());

            WorkerDAO.addWorker(newWorker);
            showAlert("Работник успешно добавлен!");

            saveClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        // Можно добавить проверку на корректность ввода
        return true;
    }


    @FXML
    protected void handleClickBackButton(ActionEvent event) {
        // Возвращаемся на страницу оператора
        PageHelper.goToPage("operator_main_page.fxml", event);
    }
}
