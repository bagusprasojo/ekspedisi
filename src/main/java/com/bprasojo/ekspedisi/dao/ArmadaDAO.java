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
import java.sql.Statement;
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
    
    protected Connection getConnection(){
        return this.conn;
    
    }
    public List<Armada> getArmadaByPage(int page, String filter) throws SQLException {
        List<Armada> armadaList = new ArrayList<>();
        
        // Query dengan WHERE jika ada filter
        String sql = "SELECT * FROM armada";

        if (filter != null && !filter.trim().isEmpty()) {
            sql += " WHERE nopol LIKE ? OR kendaraan LIKE ? OR pemilik LIKE ? OR alamat LIKE ? OR kota LIKE ? OR telp LIKE ?";
        }

        sql += " LIMIT ? OFFSET ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            int paramIndex = 1;
        
            if (filter != null && !filter.trim().isEmpty()) {
                for (int i = 0; i < 6; i++) { // 6 kolom yang difilter
                    stmt.setString(paramIndex++, "%" + filter + "%");
                }
            }
            
            int pageSize = 20;
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
    
    public void save(Armada armada) throws SQLException {
        String sql;
        
        if (armada.getId() <= 0){
            sql = "INSERT INTO armada (nopol, kendaraan, pemilik, alamat, kota, telp) VALUES (?, ?, ?, ?, ?, ?)";
        } else {
            sql = "update armada set nopol = ?, kendaraan=?, pemilik=?, alamat=?, kota=?, telp=? where id=?";
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Mengatur nilai parameter
            stmt.setString(1, armada.getNopol());
            stmt.setString(2, armada.getKendaraan());
            stmt.setString(3, armada.getPemilik());
            stmt.setString(4, armada.getAlamat());
            stmt.setString(5, armada.getKota());
            stmt.setString(6, armada.getTelp());
            
            if (armada.getId() > 0) {
                stmt.setInt(7, armada.getId());
            }
            
            stmt.executeUpdate();

            // Jika insert, ambil ID yang dihasilkan
            if (armada.getId() <= 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        armada.setId(generatedKeys.getInt(1));
                    }
                }
            }
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
        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
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
    
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM armada WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}

