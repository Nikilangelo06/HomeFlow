package com.nikitos.homeflow.Util;

import com.nikitos.homeflow.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class PageHelper {
    public static void goToPage(String page, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource(page));
            Parent root = loader.load();

            // Получаем текущую сцену и заменяем её содержимое
            Scene scene = ((Node) event.getSource()).getScene();
            scene.setRoot(root);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
