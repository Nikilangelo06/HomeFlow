package com.nikitos.homeflow.Controller;

import com.nikitos.homeflow.Controller.AddControllers.DialogAddWorkerController;
import com.nikitos.homeflow.Model.Form;
import com.nikitos.homeflow.Model.FormDAO;
import com.nikitos.homeflow.Util.DataParser;
import com.nikitos.homeflow.Util.PageHelper;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.nikitos.homeflow.MainApp;

import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.nikitos.homeflow.Util.AlertHelper.showAlert;

public class OperatorMainController {

    @FXML
    private VBox forms_VBox;

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

            dialogStage.showAndWait();

        } catch (IOException e) {
            showAlert("Не удалось загрузить окно: " + e.getMessage());
        }
    }


    private void loadFormsNotProcessed() {
        try {
            ObservableList<Form> forms = FormDAO.getAllForms();

            // Фильтруем только доступные заявки
            FilteredList<Form> availableForms = forms.filtered(form -> !form.isProcessed());

            Map<Integer, GridPane> formMap = new HashMap<>();

            // Очищаем VBox, в котором лежат GridPane-ы с записями
            forms_VBox.getChildren().clear();

            int rowIndex = 0;
            for (Form form : forms) {
                GridPane gridPane = new GridPane();
                gridPane.setGridLinesVisible(true);

                // Для Связи Id Form и GridPane (для последующей обработки кнопки cancel)
                formMap.put(form.getId(), gridPane);

                // Настройка RowConstraints
                RowConstraints dataRow = new RowConstraints();
                dataRow.setPercentHeight(100);
                dataRow.setMaxHeight(75);
                gridPane.getRowConstraints().add(dataRow);

                // Настройка ColumnConstraints
                ColumnConstraints col1 = new ColumnConstraints();
                col1.setPercentWidth(19);
                col1.setHgrow(Priority.ALWAYS);

                ColumnConstraints col2 = new ColumnConstraints();
                col2.setPercentWidth(19);
                col2.setHgrow(Priority.ALWAYS);

                ColumnConstraints col3 = new ColumnConstraints();
                col3.setPercentWidth(19);
                col3.setHgrow(Priority.ALWAYS);

                ColumnConstraints col4 = new ColumnConstraints();
                col4.setPercentWidth(19);
                col4.setHgrow(Priority.ALWAYS);

                ColumnConstraints col5 = new ColumnConstraints();
                col5.setPercentWidth(19);
                col5.setHgrow(Priority.ALWAYS);
                col5.setHalignment(HPos.CENTER);

                ColumnConstraints col6 = new ColumnConstraints();
                col6.setPercentWidth(5);
                col6.setHgrow(Priority.ALWAYS);
                col6.setHalignment(HPos.CENTER);

                ColumnConstraints col7 = new ColumnConstraints();
                col7.setPercentWidth(5);
                col7.setHgrow(Priority.ALWAYS);
                col7.setHalignment(HPos.CENTER);

                gridPane.getColumnConstraints().addAll(col1, col2, col3, col4, col5, col6, col7);


                TextArea address_TextArea = new TextArea(form.getAddress());
                address_TextArea.setEditable(false);
                //address_TextArea.getStyleClass().add("grid-label");

                TextArea fullName_TextArea = new TextArea(form.getFullName());
                fullName_TextArea.setEditable(false);
                //fullName_TextArea.getStyleClass().add("grid-label");

                TextArea telephone_TextArea = new TextArea(form.getPhoneNumber());
                telephone_TextArea.setEditable(false);
                //telephone_TextArea.getStyleClass().add("grid-label");

                TextArea whoIsNeeded_TextArea = new TextArea(DataParser.parseWhoIsNeeded(form.getWhoIsNeeded()));;
                whoIsNeeded_TextArea.setEditable(false);
                //whoIsNeeded_TextArea.getStyleClass().add("grid-label");

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy"); // Формат: день-месяц-год
                String formattedDate = form.getDateCreated().format(formatter); // Преобразуем в строку
                TextArea dateCreated_TextArea = new TextArea(formattedDate);;
                dateCreated_TextArea.setEditable(false);

                Button deleteForm_Button = new Button("X");
                deleteForm_Button.getStyleClass().add("button-delete-form");
                deleteForm_Button.setOnAction(actionEvent -> {
                    int formId = form.getId();
                    try {
                        // Удаляем заявку из БД
                        FormDAO.deleteForm(formId);
                        // Перезагружаем страницу с заявками
                        loadFormsNotProcessed();

                        showAlert("Заявка успешно удалена!");
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                    forms_VBox.getChildren().remove(formMap.get(formId)); // Удаляем GridPane
                    formMap.remove(formId); // Удаляем из Map
                });

                Button completeForm_Button = new Button("→");
                completeForm_Button.getStyleClass().add("button-complete-brigade");
                completeForm_Button.setOnAction(event -> {
                    int formId = form.getId();

                    try {
                        FormDAO.updateFormStatus(formId, true);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                    // Переходим на страницу связывания заявки и мастера
                    PageHelper.goToPage("", event);

                    forms_VBox.getChildren().remove(formMap.get(formId)); // Удаляем GridPane
                    formMap.remove(formId); // Удаляем из Map
                });


                gridPane.add(address_TextArea, 0, rowIndex);
                gridPane.add(fullName_TextArea, 1, rowIndex);
                gridPane.add(telephone_TextArea, 2, rowIndex);
                gridPane.add(whoIsNeeded_TextArea, 3, rowIndex);
                gridPane.add(dateCreated_TextArea, 4, rowIndex);
                gridPane.add(deleteForm_Button, 5, rowIndex);
                gridPane.add(completeForm_Button, 6, rowIndex);


                forms_VBox.getChildren().add(gridPane);
            }

        } catch (Exception e) {
            showAlert("Не удалось загрузить заявки: " + e.getMessage());
        }
    }


    @FXML
    private void handleTabFormsSelected(Event event) throws SQLException {
        loadFormsNotProcessed();



    }
}
