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
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
    public List<Map<String, Object>> getArmadaByPage(int page, String filter, int pageSize) throws SQLException {
        List<Map<String, Object>> resultList = new ArrayList<>();
        
        // Query dengan WHERE jika ada filter
        String sql = "SELECT a.*, b.nama as driver FROM armada a " 
                     + " left outer join stake_holder b on a.driver_id = b.id ";

        if (filter != null && !filter.trim().isEmpty()) {
            sql += " WHERE a.nopol LIKE ? OR a.kendaraan LIKE ? OR a.pemilik LIKE ? OR a.alamat LIKE ? OR a.kota LIKE ? OR a.telp LIKE ? or b.nama like ?";
        }

        sql += " LIMIT ? OFFSET ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            int paramIndex = 1;
        
            if (filter != null && !filter.trim().isEmpty()) {
                for (int i = 0; i < 7; i++) { // 6 kolom yang difilter
                    stmt.setString(paramIndex++, "%" + filter + "%");
                }
            }
            
//            int pageSize = 200;
            stmt.setInt(paramIndex++, pageSize); // Parameter untuk LIMIT
            stmt.setInt(paramIndex, (page - 1) * pageSize); // Parameter untuk OFFSET

            // Eksekusi query
            try (ResultSet rs = stmt.executeQuery()) {
                // Mendapatkan metadata kolom
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                // Proses setiap row hasil query
                while (rs.next()) {
                    Map<String, Object> rowMap = new LinkedHashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnLabel(i); // Nama kolom
                        Object columnValue = rs.getObject(i); // Nilai kolom
                        rowMap.put(columnName, columnValue); // Menyimpan dalam Map
                    }
                    resultList.add(rowMap); // Menambahkan baris ke dalam list
                }
            }
        }

        return resultList;
    }
    
    public void save(Armada armada) throws SQLException {
        String sql;
        
        if (armada.getId() <= 0){
            sql = "INSERT INTO armada (nopol, kendaraan, pemilik, alamat, kota, telp, driver_id) VALUES (?, ?, ?, ?, ?, ?,?)";
        } else {
            sql = "update armada set nopol = ?, kendaraan=?, pemilik=?, alamat=?, kota=?, telp=?, driver_id=? where id=?";
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Mengatur nilai parameter
            stmt.setString(1, armada.getNopol());
            stmt.setString(2, armada.getKendaraan());
            stmt.setString(3, armada.getPemilik());
            stmt.setString(4, armada.getAlamat());
            stmt.setString(5, armada.getKota());
            stmt.setString(6, armada.getTelp());
            stmt.setInt(7, armada.getDriverId());
            
            if (armada.getId() > 0) {
                stmt.setInt(8, armada.getId());
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
                        rs.getInt("driver_id"),
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

