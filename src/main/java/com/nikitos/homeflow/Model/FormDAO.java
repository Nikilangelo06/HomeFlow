package com.nikitos.homeflow.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class FormDAO {
    // Добавление заявки
    public static void addForm(Form form) throws SQLException {
        String sql = "INSERT INTO forms(address, full_name, phone_number, who_is_needed, date_created) VALUES(?, ?, ?, ?, ?)";

        try (Connection conn = DataBaseHandler.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, form.getAddress());
            pstmt.setString(2, form.getFullName());
            pstmt.setString(3, form.getPhoneNumber());
            pstmt.setString(4, form.getWhoIsNeeded());
            pstmt.setTimestamp(5, Timestamp.valueOf(form.getDateCreated())); // Устанавливаем дату/время
            pstmt.executeUpdate();
        }
    }


    // Получение всех заявок
    public static ObservableList<Form> getAllForms() throws SQLException {
        ObservableList<Form> forms = FXCollections.observableArrayList();
        String sql = "SELECT * FROM forms";

        try (Connection conn = DataBaseHandler.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                forms.add(new Form(
                        rs.getInt("form_id"),
                        rs.getString("address"),
                        rs.getString("full_name"),
                        rs.getString("phone_number"),
                        rs.getString("who_is_needed"),
                        rs.getTimestamp("date_created").toLocalDateTime(), // Преобразование TIMESTAMP -> LocalDateTime
                        rs.getBoolean("is_processed")
                ));
            }
        }
        return forms;
    }


    // Удаление заявки
    public static void deleteForm(int formId) throws SQLException {
        String sql = "DELETE FROM forms WHERE form_id = ?";
        try (Connection conn = DataBaseHandler.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, formId);
            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Заявка успешно удалена!");
            } else {
                throw new SQLException("Не удалось найти заявку с ID: " + formId);
            }
        }
    }


    // Обновить данные заявки
    public static void editForm(Form form) throws SQLException {
        // SQL-запрос для обновления данных водителя
        String sql = "UPDATE forms SET address = ?, full_name = ?, phone_number = ?, who_is_needed = ?, date_created = ? WHERE form_id = ?";

        // Использование PreparedStatement для безопасного выполнения SQL-запроса
        try (Connection conn = DataBaseHandler.connect();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(1, form.getAddress());
            preparedStatement.setString(2, form.getFullName());
            preparedStatement.setString(3, form.getPhoneNumber());
            preparedStatement.setString(4, form.getWhoIsNeeded());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(form.getDateCreated())); // Устанавливаем дату/время
            preparedStatement.setInt(6, form.getId());

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Данные заявки успешно обновлены.");
            } else {
                throw new SQLException("Не удалось найти заявку с ID: " + form.getId());
            }
        }
    }


    /* Обновить статус заявки */
    public static void updateFormStatus(int formId, boolean isProcessed) throws SQLException {
        // SQL-запрос для обновления статуса водителя
        String sql = "UPDATE workers SET is_processed = ? WHERE form_id = ?";

        // Использование PreparedStatement для безопасного выполнения SQL-запроса
        try (Connection conn = DataBaseHandler.connect();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setBoolean(1, isProcessed);
            preparedStatement.setInt(2, formId);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Статус заявки успешно обновлен.");
            } else {
                throw new SQLException("Не удалось найти заявку с ID: " + formId);
            }
        }
    }


    // Получение объекта заявки по её ID
    public static Form getFormById(int id) {
        String sql = "SELECT * FROM forms WHERE form_id = ?";
        Form form = null;

        try (Connection conn = DataBaseHandler.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    form = new Form(
                            rs.getInt("form_id"),
                            rs.getString("address"),
                            rs.getString("full_name"),
                            rs.getString("phone_number"),
                            rs.getString("who_is_needed"),
                            rs.getTimestamp("date_created").toLocalDateTime(), // Преобразование TIMESTAMP -> LocalDateTime
                            rs.getBoolean("is_processed")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return form;
    }
}
