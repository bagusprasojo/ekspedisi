/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bprasojo.ekspedisi.database;

/**
 *
 * @author USER
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/ekspedisi";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection connection;

    // Singleton untuk mendapatkan koneksi
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Koneksi ke database berhasil!");
            } catch (SQLException e) {
                System.err.println("Koneksi ke database gagal: " + e.getMessage());
                throw e;
            }
        }
        return connection;
    }

    // Metode untuk menutup koneksi
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Koneksi ke database ditutup.");
            } catch (SQLException e) {
                System.err.println("Gagal menutup koneksi: " + e.getMessage());
            }
        }
    }
}

