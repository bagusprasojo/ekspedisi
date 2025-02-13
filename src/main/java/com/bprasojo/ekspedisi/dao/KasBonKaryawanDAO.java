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
import com.bprasojo.ekspedisi.model.KasBonKaryawan;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KasBonKaryawanDAO {
    private Connection conn;

    public KasBonKaryawanDAO() {
        try {
            this.conn = DatabaseConnection.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(KasBonKaryawanDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void save(KasBonKaryawan kasBonKaryawan) throws SQLException {
        
        if (kasBonKaryawan.getNominal() > kasBonKaryawan.getPelunasan()){
            kasBonKaryawan.setStatuLunas("Belum");
        } else {
            kasBonKaryawan.setStatuLunas("Lunas");
        }
        Date tanggal = new Date(kasBonKaryawan.getTanggal().getTime());
        if (kasBonKaryawan.getId() <= 0) {
            String sql = "INSERT INTO kas_bon_karyawan (tanggal, nama_karyawan, alamat_karyawan, perkiraan_pinjaman_id, perkiraan_kas_id, nominal, keterangan, bank_id, sumber_dana, pelunasan, status_lunas) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?,?,?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setDate(1, tanggal);
                stmt.setString(2, kasBonKaryawan.getNamaKaryawan());
                stmt.setString(3, kasBonKaryawan.getAlamatKaryawan());
                stmt.setInt(4, kasBonKaryawan.getPerkiraanPinjamanId());
                stmt.setInt(5, kasBonKaryawan.getPerkiraanKasId());
                stmt.setInt(6, kasBonKaryawan.getNominal());
                stmt.setString(7, kasBonKaryawan.getKeterangan());
                stmt.setInt(8, kasBonKaryawan.getBankId());
                stmt.setString(9, kasBonKaryawan.getSumberDana());
                stmt.setInt(10, kasBonKaryawan.getPelunasan());
                stmt.setString(11, kasBonKaryawan.getStatusLunas());
                stmt.executeUpdate();
                
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        kasBonKaryawan.setId(generatedKeys.getInt(1));
                    }
                }
            }
        } else {
            String sql = "UPDATE kas_bon_karyawan SET tanggal = ?, nama_karyawan = ?,alamat_karyawan = ?, perkiraan_pinjaman_id = ?, perkiraan_kas_id = ?, nominal = ?, keterangan = ?, bank_id = ?, sumber_dana = ?, pelunasan = ?, status_lunas = ? WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setDate(1, tanggal);
                stmt.setString(2, kasBonKaryawan.getNamaKaryawan());
                stmt.setString(3, kasBonKaryawan.getAlamatKaryawan());
                stmt.setInt(4, kasBonKaryawan.getPerkiraanPinjamanId());
                stmt.setInt(5, kasBonKaryawan.getPerkiraanKasId());
                stmt.setInt(6, kasBonKaryawan.getNominal());
                stmt.setString(7, kasBonKaryawan.getKeterangan());
                stmt.setInt(8, kasBonKaryawan.getBankId());
                stmt.setString(9, kasBonKaryawan.getSumberDana());
                stmt.setInt(10, kasBonKaryawan.getPelunasan());
                stmt.setString(11, kasBonKaryawan.getStatusLunas());
                stmt.setInt(12, kasBonKaryawan.getId());
                stmt.executeUpdate();
            }
        }
    }

    public KasBonKaryawan getById(int id) throws SQLException {
        String sql = "SELECT * FROM kas_bon_karyawan WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new KasBonKaryawan(
                        rs.getInt("id"),
                        rs.getDate("tanggal"),
                        rs.getString("nama_karyawan"),
                        rs.getString("alamat_karyawan"),
                        rs.getInt("perkiraan_pinjaman_id"),
                        rs.getInt("perkiraan_kas_id"),
                        rs.getInt("nominal"),
                        rs.getString("keterangan"),
                        rs.getInt("bank_id"),
                        rs.getString("sumber_dana"),
                        rs.getInt("pelunasan"),
                        rs.getString("status_lunas")
                    );
                }
            }
        }
        return null;
    }

    public List<KasBonKaryawan> getAll() throws SQLException {
        List<KasBonKaryawan> list = new ArrayList<>();
        String sql = "SELECT * FROM kas_bon_karyawan";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new KasBonKaryawan(
                    rs.getInt("id"),
                    rs.getDate("tanggal"),
                    rs.getString("nama_karyawan"),
                    rs.getString("alamat_karyawan"),
                    rs.getInt("perkiraan_pinjaman_id"),
                    rs.getInt("perkiraan_kas_id"),
                    rs.getInt("nominal"),
                    rs.getString("keterangan"),
                    rs.getInt("bank_id"),
                    rs.getString("sumber_dana"),
                    rs.getInt("pelunasan"),
                    rs.getString("status_lunas")
                ));
            }
        }
        return list;
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM kas_bon_karyawan WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
    
    public List<Map<String, Object>> getKasBonByPage(Integer page, java.util.Date tglAwal, java.util.Date tglAkhir, String filter) {
        List<Map<String, Object>> resultList = new ArrayList<>();

        String sql = "select a.*, b.kode, b.nama from kas_bon_karyawan a "
                    + " inner join perkiraan b on a.perkiraan_pinjaman_id = b.id "
                    + " WHERE a.tanggal BETWEEN ? AND ?"; 

        if (filter != null && !filter.trim().isEmpty()) {
            sql += " AND (a.status_lunas like ?, a.nama_karyawan like ? or a.alamat_karyawan like ? or a.sumber_dana like ? or a.keterangan LIKE ? OR b.kode LIKE ? OR b.nama LIKE ?)";
        }

        sql += " order by a.tanggal desc , a.id desc LIMIT ? OFFSET ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Set date parameters
            stmt.setDate(1, new java.sql.Date(tglAwal.getTime()));
            stmt.setDate(2, new java.sql.Date(tglAkhir.getTime()));

            int paramIndex = 3;

            // Set filter parameters if present
            if (filter != null && !filter.trim().isEmpty()) {
                for (int i = 0; i < 7; i++) { // Filter untuk 5 kolom
                    stmt.setString(paramIndex++, "%" + filter + "%");
                }
            }

            // Set limit and offset for pagination
            int pageSize = 10; // Sesuaikan dengan kebutuhan Anda
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
        } catch (SQLException e) {
            e.printStackTrace(); // Sesuaikan dengan penanganan error Anda
        }

        return resultList;
    }
}

