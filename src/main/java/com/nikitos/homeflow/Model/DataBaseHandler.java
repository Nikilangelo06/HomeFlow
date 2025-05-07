package com.nikitos.homeflow.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseHandler {
    private static final String DB_URL = "jdbc:sqlite:homeflow.db";  // Файл БД будет в корне проекта

    // Подключение к БД
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    // Инициализация БД (создание таблиц)
    public static void initializeDatabase() {
        String sql = """
            CREATE TABLE IF NOT EXISTS workers (
                worker_id INTEGER PRIMARY KEY AUTOINCREMENT,
                full_name TEXT NOT NULL,
                profession INTEGER NOT NULL,
                phone_number TEXT NOT NULL,
                is_available BOOLEAN DEFAULT 1
            );
            
            CREATE TABLE IF NOT EXISTS forms (
                form_id INTEGER PRIMARY KEY AUTOINCREMENT,
                address TEXT NOT NULL,
                full_name TEXT UNIQUE NOT NULL,
                phone_number TEXT NOT NULL,
                who_is_needed TEXT NOT NULL,
                is_processed BOOLEAN DEFAULT 0
            );
            
            
            CREATE TABLE IF NOT EXISTS worker_form (
                worker_form_id INTEGER PRIMARY KEY AUTOINCREMENT,
                worker_id INTEGER,
                form_id INTEGER,
                is_completed BOOLEAN DEFAULT 0,
                FOREIGN KEY (worker_id) REFERENCES workers(worker_id),
                FOREIGN KEY (form_id) REFERENCES forms(form_id)
            );
            """;

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("База данных инициализирована.");
        } catch (SQLException e) {
            System.err.println("Ошибка при создании БД: " + e.getMessage());
        }
    }
}
