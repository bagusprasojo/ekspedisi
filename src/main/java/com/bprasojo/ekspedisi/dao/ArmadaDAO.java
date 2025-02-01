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
    
    public List<Armada> getArmadaByPage(int page, int pageSize, String filter) throws SQLException {
        List<Armada> armadaList = new ArrayList<>();
        
        // Query dengan WHERE jika ada filter
        String sql = "SELECT * FROM armada";

        if (filter != null && !filter.trim().isEmpty()) {
            sql += " WHERE nopol LIKE ? OR kendaraan LIKE ? OR pemilik LIKE ? OR alamat LIKE ? OR kota LIKE ? OR telp LIKE ?";
        }

        sql += " LIMIT ? OFFSET ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            int paramIndex = 1;
        
            if (filter != null && !filter.trim().isEmpty()) {
                for (int i = 0; i < 6; i++) { // 6 kolom yang difilter
                    stmt.setString(paramIndex++, "%" + filter + "%");
                }
            }

            stmt.setInt(paramIndex++, pageSize); // Parameter untuk LIMIT
            stmt.setInt(paramIndex, (page - 1) * pageSize); // Parameter untuk OFFSET

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Armada armada = new Armada(
                        rs.getString("nopol"),
                        rs.getString("kendaraan"),
                        rs.getString("pemilik"),
                        rs.getString("alamat"),
                        rs.getString("kota"),
                        rs.getString("telp"),
                        rs.getInt("id")
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

    public Armada getArmadaById(int armadaId) throws SQLException {
        return getArmada("id", armadaId);
    }
    
    public Armada getArmadaByNoPol(String noPol) throws SQLException {
        return getArmada("nopol", noPol);
    }
    
    
    private Armada getArmada(String column, Object value) throws SQLException {
        String query = "SELECT * FROM armada WHERE " + column + " = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setObject(1, value);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Armada(
                        rs.getString("nopol"),
                        rs.getString("kendaraan"),
                        rs.getString("pemilik"),
                        rs.getString("alamat"),
                        rs.getString("kota"),                        
                        rs.getString("telp"),
                        rs.getInt("id")
                );
            }
        }
        return null;
    }
}

