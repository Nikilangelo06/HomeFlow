package com.nikitos.homeflow;

import com.nikitos.homeflow.Model.DataBaseHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    private static Stage primaryStage;

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    @Override
    public void start(Stage stage) throws IOException {
        // Инициализация БД
        DataBaseHandler.initializeDatabase();

        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("login_page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 720);
        stage.setTitle("HomeFlow - система жилищно-коммунальных услуг");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}