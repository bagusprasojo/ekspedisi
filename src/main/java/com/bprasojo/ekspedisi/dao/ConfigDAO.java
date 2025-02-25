/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bprasojo.ekspedisi.dao;

/**
 *
 * @author USER
 */
import com.bprasojo.ekspedisi.database.DatabaseConnection;
import com.bprasojo.ekspedisi.model.Config;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigDAO {
    private Connection conn;

    public ConfigDAO() {
        try {
            this.conn = DatabaseConnection.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(ConfigDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // 🔹 Save (Insert/Update)
    public void save(Config config) throws SQLException {
        String sql;
        if (config.getId() == 0) {
            sql = "INSERT INTO config (kode, nilai, keterangan) VALUES (?, ?, ?)";
        } else {
            sql = "UPDATE config SET kode = ?, nilai = ?, keterangan = ? WHERE id = ?";
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, config.getKode());
            stmt.setString(2, config.getNilai()); // Menggunakan String
            stmt.setString(3, config.getKeterangan());

            if (config.getId() != 0) {
                stmt.setInt(4, config.getId());
            }

            int affectedRows = stmt.executeUpdate();

            if (config.getId() == 0 && affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        config.setId(generatedKeys.getInt(1));
                    }
                }
            }
        }
    }

    // 🔹 Get by ID
    public Config getById(int id) throws SQLException {
        return getBy("id", String.valueOf(id));
    }

    // 🔹 Get by Kode
    public Config getByKode(String kode) throws SQLException {
        return getBy("kode", kode);
    }

    // 🔹 Private method untuk menghindari kode duplikasi
    private Config getBy(String field, String value) throws SQLException {
        String sql = "SELECT * FROM config WHERE " + field + " = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, value);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Config(
                        rs.getInt("id"),
                        rs.getString("kode"),
                        rs.getString("nilai"),
                        rs.getString("keterangan")
                    );
                }
            }
        }
        return null;
    }

    // 🔹 Get All
    public List<Config> getAll() throws SQLException {
        List<Config> configList = new ArrayList<>();
        String sql = "SELECT * FROM config";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                configList.add(new Config(
                    rs.getInt("id"),
                    rs.getString("kode"),
                    rs.getString("nilai"),
                    rs.getString("keterangan")
                ));
            }
        }
        return configList;
    }

    // 🔹 Delete
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM config WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}

