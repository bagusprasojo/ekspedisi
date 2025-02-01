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
import com.bprasojo.ekspedisi.model.Perkiraan;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PerkiraanDAO {
    private Connection conn;

    // Konstruktor untuk menghubungkan ke database
    public PerkiraanDAO() {
        try {
            this.conn = DatabaseConnection.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(ArmadaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Menambah Perkiraan
    public void addPerkiraan(Perkiraan perkiraan) throws SQLException {
        String query = "INSERT INTO perkiraan (kode, nama, parent_id, golongan, kelompok, level, saldo_normal) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, perkiraan.getKode());
            stmt.setString(2, perkiraan.getNama());
            stmt.setInt(3, perkiraan.getParent_Id());
            stmt.setString(4, perkiraan.getGolongan());
            stmt.setString(5, perkiraan.getKelompok());
            stmt.setInt(6, perkiraan.getLevel());
            stmt.setString(7, perkiraan.getSaldo_normal());
            stmt.executeUpdate();
        }
    }

    private Perkiraan getPerkiraan(String column, Object value) throws SQLException {
        String query = "SELECT * FROM perkiraan WHERE " + column + " = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setObject(1, value);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Perkiraan(
                        rs.getString("kode"),
                        rs.getString("nama"),
                        rs.getInt("parent_id"),
                        rs.getString("golongan"),
                        rs.getString("kelompok"),
                        rs.getInt("level"),
                        rs.getString("saldo_normal"),
                        rs.getInt("id")
                );
            }
        }
        return null;
    }

    // Mengambil Perkiraan berdasarkan kode
    public Perkiraan getPerkiraanByKode(String kode) throws SQLException {
        return getPerkiraan("kode", kode);
    }

    // Mengambil Perkiraan berdasarkan ID
    public Perkiraan getPerkiraanById(Integer id) throws SQLException {
        return getPerkiraan("id", id);
    }


    public List<Perkiraan> getAllPerkiraanKas() throws SQLException {
        List<Perkiraan> perkiraans = new ArrayList<>();
        String query = "SELECT * FROM perkiraan where nama = 'KAS'";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                perkiraans.add(new Perkiraan(
                        rs.getString("kode"),
                        rs.getString("nama"),
                        rs.getInt("parent_id"),
                        rs.getString("golongan"),
                        rs.getString("kelompok"),
                        rs.getInt("level"),
                        rs.getString("saldo_normal"),
                        rs.getInt("id")
                ));
            }
        }
        return perkiraans;
    }
    // Mengambil seluruh data Perkiraan
    public List<Perkiraan> getAllPerkiraan() throws SQLException {
        List<Perkiraan> perkiraans = new ArrayList<>();
        String query = "SELECT * FROM perkiraan";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                perkiraans.add(new Perkiraan(
                        rs.getString("kode"),
                        rs.getString("nama"),
                        rs.getInt("parent_id"),
                        rs.getString("golongan"),
                        rs.getString("group"),
                        rs.getInt("level"),
                        rs.getString("saldo_normal"),
                        rs.getInt("it")
                ));
            }
        }
        return perkiraans;
    }

    // Memperbarui Perkiraan
    public void updatePerkiraan(Perkiraan perkiraan) throws SQLException {
        String query = "UPDATE perkiraan SET nama = ?, parent_id = ?, golongan = ?, kelompok = ?, level = ?, saldo_normal= ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, perkiraan.getNama());
            stmt.setInt(2, perkiraan.getParent_Id());
            stmt.setString(3, perkiraan.getGolongan());
            stmt.setString(4, perkiraan.getKelompok());
            stmt.setInt(5, perkiraan.getLevel());
            stmt.setString(6, perkiraan.getSaldo_normal());
            stmt.setInt(7, perkiraan.getId());
            stmt.executeUpdate();
        }
    }

    // Menghapus Perkiraan berdasarkan kode
    public void deletePerkiraanByKode(String kode) throws SQLException {
        String query = "DELETE FROM perkiraan WHERE kode = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, kode);
            stmt.executeUpdate();
        }
    }
    
    // Menghapus Perkiraan berdasarkan kode
    public void deletePerkiraan(Integer id) throws SQLException {
        String query = "DELETE FROM perkiraan WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}

