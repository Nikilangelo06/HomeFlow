package com.nikitos.homeflow.Controller;

import com.nikitos.homeflow.MainApp;
import com.nikitos.homeflow.Model.Form;
import com.nikitos.homeflow.Model.FormDAO;
import com.nikitos.homeflow.Util.AlertHelper;
import com.nikitos.homeflow.Util.PageHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

import static com.nikitos.homeflow.Util.AlertHelper.showAlert;

public class FormCreationController {
    @FXML
    private Button addForm_Button;
    @FXML
    private CheckBox electric_CheckBox;
    @FXML
    private CheckBox fitter_CheckBox;
    @FXML
    private CheckBox plumber_CheckBox;
    @FXML
    private CheckBox gasman_CheckBox;
    @FXML
    private TextField address_TextField;
    @FXML
    private TextField telephone_TextField;
    @FXML
    private TextField fullName_TextField;


    @FXML
    protected void handleSendRequest(ActionEvent event) throws SQLException {
        // Проверка на корректность данных
        if (!isDataOK()) {
            return;
        }

        String who_is_needed = "";
        if (gasman_CheckBox.isSelected()) { who_is_needed += "0"; }
        if (plumber_CheckBox.isSelected()) { who_is_needed += "1"; }
        if (fitter_CheckBox.isSelected()) { who_is_needed += "2"; }
        if (electric_CheckBox.isSelected()) { who_is_needed += "3"; }

        Form form = new Form(
                address_TextField.getText(),
                fullName_TextField.getText(),
                telephone_TextField.getText(),
                who_is_needed);

        System.out.println(form.getAddress());
        System.out.println(form.getFullName());
        System.out.println(form.getPhoneNumber());
        System.out.println(form.getWhoIsNeeded());

        FormDAO.addForm(form);

        //addForm_Button.setDisable(true);

        showAlert("Ваша заявка принята, ожидайте звонка от мастера.");
    }


    @FXML
    protected void handleClickBackButton(ActionEvent event) {
        // Возвращаемся на страницу входа
        PageHelper.goToPage("login_page.fxml", event);
    }


    private boolean isDataOK() {
        boolean isOK = true;

        if(address_TextField.getText().isEmpty()) {
            isOK = false;
            showAlert("Заполните поле адреса!");
        } else if (fullName_TextField.getText().isEmpty()) {
            isOK = false;
            showAlert("Заполните поле ФИО!");
        } else if (telephone_TextField.getText().isEmpty()) {
            isOK = false;
            showAlert("Заполните поле телефонного номера!");
        }

        if (!electric_CheckBox.isSelected() && !fitter_CheckBox.isSelected() && !plumber_CheckBox.isSelected() && !gasman_CheckBox.isSelected()) {
            isOK = false;
            showAlert("Выберите хотя бы одного специалиста!");
        }

        return isOK;
    }
}
