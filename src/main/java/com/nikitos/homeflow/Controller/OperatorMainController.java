package com.nikitos.homeflow.Controller;

import com.nikitos.homeflow.Controller.AddControllers.DialogAddWorkerController;
import com.nikitos.homeflow.Util.PageHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.nikitos.homeflow.MainApp;

import java.io.IOException;

import static com.nikitos.homeflow.Util.AlertHelper.showAlert;

public class OperatorMainController {

    @FXML
    protected void handleClickBackButton(ActionEvent event) {
        PageHelper.goToPage("password_operator_page.fxml", event);
    }

    @FXML
    protected void handleClickAddWorker(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/nikitos/homeflow/dialog_add_worker.fxml"));
            GridPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Добавление работника");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(MainApp.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            DialogAddWorkerController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Ждём пока пользователь введет данные и нажмем на кнопку -Сохранить-
            dialogStage.showAndWait();

        } catch (IOException e) {
            showAlert("Не удалось загрузить окно: " + e.getMessage());
        }
    }
}
