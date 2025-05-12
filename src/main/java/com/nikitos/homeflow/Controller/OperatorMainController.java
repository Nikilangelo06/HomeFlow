package com.nikitos.homeflow.Controller;

import com.nikitos.homeflow.Controller.AddControllers.DialogAddWorkerController;
import com.nikitos.homeflow.Controller.EditControllers.DialogEditWorkerController;
import com.nikitos.homeflow.Model.Form;
import com.nikitos.homeflow.Model.FormDAO;
import com.nikitos.homeflow.Model.Worker;
import com.nikitos.homeflow.Model.WorkerDAO;
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
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.nikitos.homeflow.MainApp;

import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.nikitos.homeflow.Util.AlertHelper.showAlert;

public class OperatorMainController {
    @FXML
    private Tab workers_Tab;
    @FXML
    private VBox workers_VBox;
    @FXML
    private Tab forms_Tab;
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

            // Перезагружаем страницу с заявками
            Event.fireEvent(workers_Tab, new Event(Tab.SELECTION_CHANGED_EVENT));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            showAlert("Не удалось загрузить окно: " + e.getMessage());
        }
    }


    private void loadForms(ObservableList<Form> forms) {
        try {
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
                address_TextArea.getStyleClass().add("text-area-operator-main");
                address_TextArea.setWrapText(true);

                TextArea fullName_TextArea = new TextArea(form.getFullName());
                fullName_TextArea.setEditable(false);
                fullName_TextArea.getStyleClass().add("text-area-operator-main");
                fullName_TextArea.setWrapText(true);

                TextArea telephone_TextArea = new TextArea(form.getPhoneNumber());
                telephone_TextArea.setEditable(false);
                telephone_TextArea.getStyleClass().add("text-area-operator-main");
                telephone_TextArea.wrapTextProperty().set(true);

                TextArea whoIsNeeded_TextArea = new TextArea(DataParser.parseWhoIsNeeded(form.getWhoIsNeeded()));;
                whoIsNeeded_TextArea.setEditable(false);
                whoIsNeeded_TextArea.getStyleClass().add("text-area-operator-main");
                whoIsNeeded_TextArea.wrapTextProperty().set(true);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy"); // Формат: день-месяц-год
                String formattedDate = form.getDateCreated().format(formatter); // Преобразуем в строку
                TextArea dateCreated_TextArea = new TextArea(formattedDate);;
                dateCreated_TextArea.setEditable(false);
                dateCreated_TextArea.getStyleClass().add("text-area-operator-main");
                dateCreated_TextArea.wrapTextProperty().set(true);

                Button deleteForm_Button = new Button("X");
                deleteForm_Button.getStyleClass().add("button-delete-form");
                deleteForm_Button.setOnAction(actionEvent -> {
                    // Подтверждение удаления
                    Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmation.setTitle("Подтверждение");
                    confirmation.setHeaderText("Вы уверены, что хотите удалить эту заявку?");
                    Optional<ButtonType> result = confirmation.showAndWait();
                    if (result.isEmpty() || result.get() != ButtonType.OK) {
                        return;
                    }

                    int formId = form.getId();
                    try {
                        // Удаляем заявку из БД
                        FormDAO.deleteForm(formId);

                        forms_VBox.getChildren().remove(formMap.get(formId)); // Удаляем GridPane
                        formMap.remove(formId); // Удаляем из Map

                        // Перезагружаем страницу с заявками
                        Event.fireEvent(forms_Tab, new Event(Tab.SELECTION_CHANGED_EVENT));

                        showAlert("Заявка успешно удалена!");
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

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
        ObservableList<Form> allForms = FormDAO.getAllForms();

        // Фильтруем только доступные заявки
        FilteredList<Form> availableForms = allForms.filtered(form -> !form.isProcessed());

        loadForms(availableForms);
    }


    private static int selectedWorkerId;
    public static int getSelectedWorkerId() { return selectedWorkerId;}

    private void loadWorkers(ObservableList<Worker> workers) {
        try {
            Map<Integer, GridPane> workerMap = new HashMap<>();

            // Очищаем VBox, в котором лежат GridPane-ы с записями
            workers_VBox.getChildren().clear();

            int rowIndex = 0;
            for (Worker worker : workers) {
                GridPane gridPane = new GridPane();
                gridPane.setGridLinesVisible(true);

                // Для Связи Id Form и GridPane (для последующей обработки кнопки cancel)
                workerMap.put(worker.getId(), gridPane);

                // Настройка RowConstraints
                RowConstraints dataRow = new RowConstraints();
                dataRow.setPercentHeight(100);
                dataRow.setMaxHeight(75);
                gridPane.getRowConstraints().add(dataRow);

                // Настройка ColumnConstraints
                ColumnConstraints col1 = new ColumnConstraints();
                col1.setPercentWidth(30);
                col1.setHgrow(Priority.ALWAYS);

                ColumnConstraints col2 = new ColumnConstraints();
                col2.setPercentWidth(30);
                col2.setHgrow(Priority.ALWAYS);

                ColumnConstraints col3 = new ColumnConstraints();
                col3.setPercentWidth(30);
                col3.setHgrow(Priority.ALWAYS);

                ColumnConstraints col4 = new ColumnConstraints();
                col4.setPercentWidth(5);
                col4.setHgrow(Priority.ALWAYS);

                ColumnConstraints col5 = new ColumnConstraints();
                col5.setPercentWidth(5);
                col5.setHgrow(Priority.ALWAYS);
                col5.setHalignment(HPos.CENTER);

                gridPane.getColumnConstraints().addAll(col1, col2, col3, col4, col5);


                TextArea fullName_TextArea = new TextArea(worker.getFullName());
                fullName_TextArea.setEditable(false);
                fullName_TextArea.getStyleClass().add("text-area-operator-main");
                fullName_TextArea.setWrapText(true);

                TextArea profession_TextArea = new TextArea(DataParser.parseWhoIsNeeded(String.valueOf(worker.getProfession())));
                profession_TextArea.setEditable(false);
                profession_TextArea.getStyleClass().add("text-area-operator-main");
                profession_TextArea.setWrapText(true);

                TextArea telephone_TextArea = new TextArea(worker.getPhoneNumber());
                telephone_TextArea.setEditable(false);
                telephone_TextArea.getStyleClass().add("text-area-operator-main");
                telephone_TextArea.wrapTextProperty().set(true);

                Button editWorker_Button = new Button("→");
                editWorker_Button.getStyleClass().add("button-edit-worker");
                editWorker_Button.setOnAction(event -> {
                    selectedWorkerId = worker.getId();

                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/nikitos/homeflow/dialog_edit_worker.fxml"));
                        GridPane page = loader.load();

                        Stage dialogStage = new Stage();
                        dialogStage.setTitle("Редактирование работника");
                        dialogStage.initModality(Modality.WINDOW_MODAL);
                        dialogStage.initOwner(MainApp.getPrimaryStage());
                        Scene scene = new Scene(page);
                        dialogStage.setScene(scene);

                        DialogEditWorkerController controller = loader.getController();
                        controller.setDialogStage(dialogStage);
                        controller.setSelectedWorker(worker);

                        dialogStage.showAndWait();

                        // Перезагружаем страницу с заявками
                        Event.fireEvent(workers_Tab, new Event(Tab.SELECTION_CHANGED_EVENT));

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

                Button deleteWorker_Button = new Button("X");
                deleteWorker_Button.getStyleClass().add("button-delete-worker");
                deleteWorker_Button.setOnAction(actionEvent -> {
                    // Подтверждение удаления
                    Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmation.setTitle("Подтверждение");
                    confirmation.setHeaderText("Вы уверены, что хотите удалить этого работника?");
                    Optional<ButtonType> result = confirmation.showAndWait();
                    if (result.isEmpty() || result.get() != ButtonType.OK) {
                        return;
                    }

                    selectedWorkerId = worker.getId();

                    try {
                        // Удаляем заявку из БД
                        WorkerDAO.deleteWorker(selectedWorkerId);

                        workers_VBox.getChildren().remove(workerMap.get(selectedWorkerId)); // Удаляем GridPane
                        workerMap.remove(selectedWorkerId); // Удаляем из Map

                        // Перезагружаем страницу с заявками
                        Event.fireEvent(workers_Tab, new Event(Tab.SELECTION_CHANGED_EVENT));

                        showAlert("Работник успешно удален!");
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                });


                gridPane.add(fullName_TextArea, 0, rowIndex);
                gridPane.add(profession_TextArea, 1, rowIndex);
                gridPane.add(telephone_TextArea, 2, rowIndex);
                gridPane.add(editWorker_Button, 3, rowIndex);
                gridPane.add(deleteWorker_Button, 4, rowIndex);


                workers_VBox.getChildren().add(gridPane);
            }

        } catch (Exception e) {
            showAlert("Не удалось загрузить работников: " + e.getMessage());
        }
    }


    @FXML
    private void handleWorkersTabSelected(Event event) throws SQLException {
        ObservableList<Worker> allWorkers = WorkerDAO.getAllWorkers();
        loadWorkers(allWorkers);
    }
}
