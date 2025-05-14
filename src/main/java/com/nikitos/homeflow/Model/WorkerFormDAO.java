package com.nikitos.homeflow.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkerFormDAO {
    public static void deleteWorkerFormById(int workerFormId) throws SQLException {
        String sql = "DELETE FROM worker_form WHERE worker_form_id = ?";

        try (Connection conn = DataBaseHandler.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, workerFormId);
            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Запись успешно удалена!");
            } else {
                throw new SQLException("Не удалось найти запись с ID: " + workerFormId);
            }
        }
    }


    public static void addWorkerForm(WorkerForm workerForm) {
        String sql = "INSERT INTO worker_form (worker_id, form_id,) VALUES (?, ?)";

        try (Connection conn = DataBaseHandler.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, workerForm.getWorker_id());
            pstmt.setInt(2, workerForm.getFormId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static List<WorkerForm> getAllWorkerForms() throws SQLException {
        List<WorkerForm> workerForms = new ArrayList<>();
        String sql = "SELECT * FROM worker_form";

        try (Connection conn = DataBaseHandler.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                WorkerForm workerForm = new WorkerForm(
                        rs.getInt("worker_form_id"),
                        rs.getInt("worker_id"), // ID водителя
                        rs.getInt("form_id") // ID автомобиля
                );
                workerForms.add(workerForm);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return workerForms;
    }
}
