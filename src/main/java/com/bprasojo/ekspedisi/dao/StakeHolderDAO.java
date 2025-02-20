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
import com.bprasojo.ekspedisi.model.StakeHolder;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StakeHolderDAO {
    private Connection conn;

    public StakeHolderDAO() {
        try {
            this.conn = DatabaseConnection.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(StakeHolderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Simpan atau Edit Data
    private String generateKode(String jenis){
        long kode_int = System.currentTimeMillis()/1000;
        if (jenis.equals("Customer")){
            return "CUS-" + kode_int;
        } else {
            return "KAR-" + kode_int;
        }
    }
    public void save(StakeHolder stakeholder) throws SQLException {
        String sql;
        boolean isInsert = stakeholder.getId() == 0;

        if (isInsert) {            
//            stakeholder.setKode(generateKode(stakeholder.getJenis()));
            sql = "INSERT INTO stake_holder (kode, nama, alamat, no_ktp, lokasi_kerja, jenis) VALUES (?, ?, ?, ?, ?, ?)";
        } else {
            sql = "UPDATE stake_holder SET kode = ?, nama = ?, alamat = ?, no_ktp = ?, lokasi_kerja = ?, jenis = ? WHERE id = ?";
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql, isInsert ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS)) {
            stmt.setString(1, stakeholder.getKode());
            stmt.setString(2, stakeholder.getNama());
            stmt.setString(3, stakeholder.getAlamat());
            stmt.setString(4, stakeholder.getNoKtp());
            stmt.setString(5, stakeholder.getLokasiKerja());
            stmt.setString(6, stakeholder.getJenis());

            if (!isInsert) {
                stmt.setInt(7, stakeholder.getId());
            }

            stmt.executeUpdate();

            if (isInsert) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        stakeholder.setId(rs.getInt(1));
                    }
                }
            }
        }
    }

    // Hapus Data berdasarkan ID
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM stake_holder WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // Helper untuk getById dan getByKode
    private StakeHolder getByField(String field, Object value) throws SQLException {
        String sql = "SELECT * FROM stake_holder WHERE " + field + " = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (value instanceof Integer) {
                stmt.setInt(1, (Integer) value);
            } else if (value instanceof String) {
                stmt.setString(1, (String) value);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new StakeHolder(
                        rs.getInt("id"),
                        rs.getString("kode"),
                        rs.getString("nama"),
                        rs.getString("alamat"),
                        rs.getString("no_ktp"),
                        rs.getString("lokasi_kerja"),
                        rs.getString("jenis")
                    );
                }
            }
        }
        return null;
    }

    // Ambil Data Berdasarkan ID
    public StakeHolder getById(int id) throws SQLException {
        return getByField("id", id);
    }

    // Ambil Data Berdasarkan Kode
    public StakeHolder getByKode(String kode) throws SQLException {
        return getByField("kode", kode);
    }
    
    public List<StakeHolder> getStakeHolderByPage(int page, String filter, String jenis) throws SQLException {
        List<StakeHolder> stakeHolderList = new ArrayList<>();
        
        // Query dengan WHERE jika ada filter
        String sql = "SELECT * FROM stake_holder  WHERE jenis = '" + jenis + "'";

        if (filter != null && !filter.trim().isEmpty()) {
            sql += " and (kode  like ? or nama like ? or alamat like ? or no_ktp like ? or lokasi_kerja like ? )";
        }

        sql += " LIMIT ? OFFSET ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            int paramIndex = 1;
        
            if (filter != null && !filter.trim().isEmpty()) {
                for (int i = 0; i < 5; i++) { // 6 kolom yang difilter
                    stmt.setString(paramIndex++, "%" + filter + "%");
                }
            }
            
            int pageSize = 20;
            stmt.setInt(paramIndex++, pageSize); // Parameter untuk LIMIT
            stmt.setInt(paramIndex, (page - 1) * pageSize); // Parameter untuk OFFSET

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    StakeHolder stakeHolder = new StakeHolder(
                        rs.getInt("id"),
                        rs.getString("kode"),
                        rs.getString("nama"),
                        rs.getString("alamat"),
                        rs.getString("no_ktp"),
                        rs.getString("lokasi_kerja"),
                        rs.getString("jenis")
                        
                    );
                    stakeHolderList.add(stakeHolder);
                }
            }
        }

        return stakeHolderList;
    }
}

