package com.nikitos.homeflow.Model;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkerDAO {
    // Добавление работника
    public static void addWorker(Worker worker) throws SQLException {
        String sql = "INSERT INTO workers(full_name, profession, phone_number) VALUES(?, ?, ?)";

        try (Connection conn = DataBaseHandler.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, worker.getFullName());
            pstmt.setInt(2, worker.getProfession());
            pstmt.setString(3, worker.getPhoneNumber());
            pstmt.executeUpdate();
        }
    }


    // Получение всех работников
    public static ObservableList<Worker> getAllWorkers() throws SQLException {
        ObservableList<Worker> workers = FXCollections.observableArrayList();
        String sql = "SELECT * FROM workers";

        try (Connection conn = DataBaseHandler.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                workers.add(new Worker(
                        rs.getInt("worker_id"),
                        rs.getString("full_name"),
                        rs.getInt("profession"),
                        rs.getString("phone_number"),
                        rs.getBoolean("is_available")
                ));
            }
        }
        return workers;
    }


    // Удаление работника
    public static void deleteWorker(int workerId) throws SQLException {
        String sql = "DELETE FROM workers WHERE worker_id = ?";
        try (Connection conn = DataBaseHandler.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, workerId);
            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Работник успешно удален!");
            } else {
                throw new SQLException("Не удалось найти работника с ID: " + workerId);
            }
        }
    }


    // Обновить данные работника
    public static void editWorker(Worker worker) throws SQLException {
        // SQL-запрос для обновления данных работника
        String sql = "UPDATE workers SET full_name = ?, profession = ?, phone_number = ? WHERE worker_id = ?";

        // Использование PreparedStatement для безопасного выполнения SQL-запроса
        try (Connection conn = DataBaseHandler.connect();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(1, worker.getFullName());
            preparedStatement.setInt(2, worker.getProfession());
            preparedStatement.setString(3, worker.getPhoneNumber());
            preparedStatement.setInt(4, worker.getId());

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Данные работника успешно обновлены.");
            } else {
                throw new SQLException("Не удалось найти работника с ID: " + worker.getId());
            }
        }
    }


    /* Обновить статус работника */
    public static void updateWorkerStatus(int workerId, boolean isAvailable) throws SQLException {
        // SQL-запрос для обновления статуса водителя
        String sql = "UPDATE workers SET is_available = ? WHERE worker_id = ?";

        // Использование PreparedStatement для безопасного выполнения SQL-запроса
        try (Connection conn = DataBaseHandler.connect();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setBoolean(1, isAvailable);
            preparedStatement.setInt(2, workerId);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Статус работника успешно обновлен.");
            } else {
                throw new SQLException("Не удалось найти работника с ID: " + workerId);
            }
        }
    }


    public static Worker getWorkerById(int id) {
        String sql = "SELECT * FROM workers WHERE worker_id = ?";
        Worker worker = null;

        try (Connection conn = DataBaseHandler.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    worker = new Worker(
                            rs.getInt("worker_id"),
                            rs.getString("full_name"),
                            rs.getInt("profession"),
                            rs.getString("phone_number"),
                            rs.getBoolean("is_available")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return worker;
    }
}
