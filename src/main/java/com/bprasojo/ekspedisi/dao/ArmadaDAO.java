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
import com.bprasojo.ekspedisi.model.Armada;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;

public class ArmadaDAO {
    private Connection conn;

    public ArmadaDAO() {
        try {
            this.conn = DatabaseConnection.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(ArmadaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Armada> getArmadaByPage(int page, int pageSize) throws SQLException {
        List<Armada> armadaList = new ArrayList<>();
        String sql = "SELECT * FROM armada LIMIT ? OFFSET ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pageSize); // Jumlah data per halaman
            stmt.setInt(2, (page - 1) * pageSize); // Offset = (page - 1) * pageSize

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Armada armada = new Armada(
                        rs.getString("nopol"),
                        rs.getString("kendaraan"),
                        rs.getString("pemilik"),
                        rs.getString("alamat"),
                        rs.getString("kota"),
                        rs.getString("telp")
                    );
                    armadaList.add(armada);
                }
            }
        }

        return armadaList;
    }
    
    public boolean saveArmada(Armada armada) {
        String sql = "INSERT INTO armada (nopol, kendaraan, pemilik, alamat, kota, telp) VALUES (?, ?, ?, ?, ?, ?)";

        try (
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Mengatur nilai parameter
            stmt.setString(1, armada.getNopol());
            stmt.setString(2, armada.getKendaraan());
            stmt.setString(3, armada.getPemilik());
            stmt.setString(4, armada.getAlamat());
            stmt.setString(5, armada.getKota());
            stmt.setString(6, armada.getTelp());

            // Eksekusi query
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.err.println("Gagal menyimpan armada: " + e.getMessage());
            return false;
        }
    }
}

