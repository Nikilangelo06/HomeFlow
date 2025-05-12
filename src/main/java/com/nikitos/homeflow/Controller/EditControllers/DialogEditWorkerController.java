package com.nikitos.homeflow.Controller.EditControllers;

import com.nikitos.homeflow.Model.Worker;
import com.nikitos.homeflow.Model.WorkerDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

import static com.nikitos.homeflow.Controller.OperatorMainController.getSelectedWorkerId;
import static com.nikitos.homeflow.Util.AlertHelper.showAlert;

public class DialogEditWorkerController {
    @FXML
    private ComboBox professionEdited_ComboBox;
    @FXML
    private TextField telephoneEdited_TextField;
    @FXML
    private TextField fullNameEdited_TextField;

    private Stage dialogStage;
    private boolean saveClicked = false;


    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private void handleClickSaveEditedWorker(ActionEvent actionEvent) throws SQLException {
        if (isInputValid()) {
            Worker newWorker = new Worker(getSelectedWorkerId(),"", 0, "");
            newWorker.setFullName(fullNameEdited_TextField.getText());
            newWorker.setPhoneNumber(telephoneEdited_TextField.getText());
            newWorker.setProfession(professionEdited_ComboBox.getSelectionModel().getSelectedIndex());

            WorkerDAO.editWorker(newWorker);
            showAlert("Работник успешно отредактирован!");

            saveClicked = true;
            dialogStage.close();
        }
    }

    private boolean isInputValid() {

        return true;
    }

    @FXML
    private void handleClickBackButton(ActionEvent actionEvent) {
    }

    public void setSelectedWorker(Worker worker) {
        fullNameEdited_TextField.setText(worker.getFullName());
        telephoneEdited_TextField.setText(worker.getPhoneNumber());
        professionEdited_ComboBox.getSelectionModel().select(worker.getProfession());
    }
}
