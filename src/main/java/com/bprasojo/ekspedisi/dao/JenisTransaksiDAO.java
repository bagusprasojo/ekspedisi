/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bprasojo.ekspedisi.dao;

/**
 *
 */
import com.bprasojo.ekspedisi.database.DatabaseConnection;
import com.bprasojo.ekspedisi.model.JenisTransaksi;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JenisTransaksiDAO extends ParentDAO{
    public JenisTransaksiDAO() {
        super();
        _nama_table_ = "jenis_transaksi";
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM jenis_transaksi WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
    // Simpan atau update JenisTransaksi
    public void save(JenisTransaksi jenis) throws SQLException {
        String sql;
        boolean isInsert = jenis.getId() == 0;

        if (isInsert) {
            sql = "INSERT INTO jenis_transaksi (kode, nama, akun_id, user_create) VALUES (?, ?, ?, ?)";
        } else {
            sql = "UPDATE jenis_transaksi SET kode = ?, nama = ?, akun_id = ?, user_update = ? WHERE id = ?";
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql, isInsert ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS)) {
            stmt.setString(1, jenis.getKode());
            stmt.setString(2, jenis.getNama());
            stmt.setInt(3, jenis.getAkunId());            
            
            if (isInsert){
                stmt.setString(4, jenis.getUserCreate());
            } else {
                stmt.setString(4, jenis.getUserUpdate());
                stmt.setInt(5, jenis.getId());
            }

            stmt.executeUpdate();

            if (isInsert) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        jenis.setId(generatedKeys.getInt(1));
                    }
                }
            }
        }
    }

    // Fungsi umum untuk mengambil JenisTransaksi berdasarkan parameter tertentu (ID atau Kode)
    private JenisTransaksi getByParameter(String sql, Object param) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (param instanceof Integer) {
                stmt.setInt(1, (Integer) param);
            } else if (param instanceof String) {
                stmt.setString(1, (String) param);
            }
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new JenisTransaksi(
                    rs.getInt("id"),
                    rs.getString("kode"),
                    rs.getString("nama"),
                    rs.getInt("akun_id")
                );
            }
        }
        return null;
    }

    // Fungsi untuk mendapatkan JenisTransaksi berdasarkan ID
    public JenisTransaksi getById(int id) throws SQLException {
        String sql = "SELECT * FROM jenis_transaksi WHERE id = ?";
        return getByParameter(sql, id);
    }

    // Fungsi untuk mendapatkan JenisTransaksi berdasarkan Kode
    public JenisTransaksi getByKode(String kode) throws SQLException {
        String sql = "SELECT * FROM jenis_transaksi WHERE kode = ?";
        return getByParameter(sql, kode);
    }


    public List<Map<String, Object>> getJenisTransaksiByPage(int page, String filter, int pageSize) throws SQLException {
        List<Map<String, Object>> resultList = new ArrayList<>();
        
        // Query dengan WHERE jika ada filter
        String sql = "SELECT a.*, b.kode as kode_akun, b.nama as nama_akun from jenis_transaksi a " 
                     + " inner join perkiraan b on a.akun_id = b.id ";

        if (filter != null && !filter.trim().isEmpty()) {
            sql += " WHERE a.kode LIKE ? OR a.nama LIKE ? OR b.kode LIKE ? OR b.nama LIKE ?";
        }

        sql += "order by a.kode LIMIT ? OFFSET ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            int paramIndex = 1;
        
            if (filter != null && !filter.trim().isEmpty()) {
                for (int i = 0; i < 6; i++) { // 6 kolom yang difilter
                    stmt.setString(paramIndex++, "%" + filter + "%");
                }
            }
            
//            int pageSize = 20;
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
    
}

