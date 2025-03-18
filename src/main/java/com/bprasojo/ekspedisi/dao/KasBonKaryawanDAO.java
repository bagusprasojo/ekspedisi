/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bprasojo.ekspedisi.dao;

/**
 *
 */
import com.bprasojo.ekspedisi.database.DatabaseConnection;
import com.bprasojo.ekspedisi.model.KasBonKaryawan;
import com.bprasojo.ekspedisi.utils.AppUtils;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KasBonKaryawanDAO extends ParentDAO{
    private Connection conn;

    public KasBonKaryawanDAO() {
        try {
            this.conn = DatabaseConnection.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(KasBonKaryawanDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        _nama_table_ = "kas_bon_karyawan";
    }

    public void save(KasBonKaryawan kasBonKaryawan) throws SQLException {
        if (!validasiClosing(kasBonKaryawan.getId(), kasBonKaryawan.getTanggal())){
            throw new SQLException("Data tidak bisa disimpan karena sudah closing");
        }
        
        kasBonKaryawan.setStatuLunas(kasBonKaryawan.getNominal() > kasBonKaryawan.getPelunasan() ? "Belum" : "Lunas");

        Date tanggal = new Date(kasBonKaryawan.getTanggal().getTime());
        boolean isInsert = kasBonKaryawan.getId() <= 0;

        if (isInsert) {
            kasBonKaryawan.setNoRegister(generateNoRegister(kasBonKaryawan.getTanggal()));
        }

        String sql;
        if (isInsert){ 
            sql = "INSERT INTO " + _nama_table_ + " (tanggal, karyawan_id, perkiraan_pinjaman_id, perkiraan_kas_id, nominal, keterangan, bank_id, sumber_dana, pelunasan, status_lunas, no_register, user_create) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        } else {    
            sql = "UPDATE " + _nama_table_ + " SET tanggal = ?, karyawan_id = ?, perkiraan_pinjaman_id = ?, perkiraan_kas_id = ?, nominal = ?, keterangan = ?, bank_id = ?, sumber_dana = ?, pelunasan = ?, status_lunas = ?, no_register = ?, user_update = ? WHERE id = ?";            
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql, isInsert ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS)) {
            stmt.setDate(1, tanggal);
            stmt.setInt(2, kasBonKaryawan.getKaryawanId());
            stmt.setInt(3, kasBonKaryawan.getPerkiraanPinjamanId());
            stmt.setInt(4, kasBonKaryawan.getPerkiraanKasId());
            stmt.setInt(5, kasBonKaryawan.getNominal());
            stmt.setString(6, kasBonKaryawan.getKeterangan());
            stmt.setInt(7, kasBonKaryawan.getBankId());
            stmt.setString(8, kasBonKaryawan.getSumberDana());
            stmt.setInt(9, kasBonKaryawan.getPelunasan());
            stmt.setString(10, kasBonKaryawan.getStatusLunas());
            stmt.setString(11, kasBonKaryawan.getNoRegister());

            if (isInsert) {
                stmt.setString(12, kasBonKaryawan.getUserCreate());
            } else {
                stmt.setString(12, kasBonKaryawan.getUserUpdate());
                stmt.setInt(13, kasBonKaryawan.getId());
            }

            stmt.executeUpdate();

            if (isInsert) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        kasBonKaryawan.setId(generatedKeys.getInt(1));
                    }
                }
            }
        }
    }
    
    private KasBonKaryawan getByField(String fieldName, Object value) throws SQLException {
        String sql = "SELECT * FROM kas_bon_karyawan WHERE " + fieldName + " = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (value instanceof Integer) {
                stmt.setInt(1, (Integer) value);
            } else if (value instanceof String) {
                stmt.setString(1, (String) value);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new KasBonKaryawan(
                        rs.getInt("id"),
                        rs.getDate("tanggal"),
                        rs.getInt("karyawan_id"),
                        rs.getInt("perkiraan_pinjaman_id"),
                        rs.getInt("perkiraan_kas_id"),
                        rs.getInt("nominal"),
                        rs.getString("keterangan"),
                        rs.getInt("bank_id"),
                        rs.getString("sumber_dana"),
                        rs.getInt("pelunasan"),
                        rs.getString("status_lunas"),
                        rs.getString("no_register"),
                        rs.getString("user_create"),
                        rs.getString("user_update")
                    );
                }
            }
        }
        return null;
    }

    public KasBonKaryawan getById(int id) throws SQLException {
        return getByField("id", id);
    }
    
    public KasBonKaryawan getByNoRegister(String no_register) throws SQLException {
        return getByField("no_register", no_register);
    }    

    public void delete(int id) throws SQLException {
        if (!validasiClosing(id, AppUtils.now())){
            throw new SQLException("Data tidak bisa dihapus karena sudah closing");
        }
        
        String sql = "DELETE FROM " + _nama_table_ + " WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
    
    public List<Map<String, Object>> getKasBonByPage(Integer page, java.util.Date tglAwal, java.util.Date tglAkhir, String filter) {
        List<Map<String, Object>> resultList = new ArrayList<>();

        String sql = "select a.*, b.kode, b.nama, c.nama as nama_karyawan, c.alamat as alamat_karyawan from kas_bon_karyawan a "
                    + " inner join perkiraan b on a.perkiraan_pinjaman_id = b.id "
                    + " inner join stake_holder c on a.karyawan_id = c.id "
                    + " WHERE a.tanggal BETWEEN ? AND ? "; 

        if (filter != null && !filter.trim().isEmpty()) {
            sql += " AND (a.status_lunas like ? or c.nama like ? or c.alamat like ? or a.sumber_dana like ? or a.keterangan LIKE ? OR b.kode LIKE ? OR b.nama LIKE ?)";
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
            int pageSize = 20; // Sesuaikan dengan kebutuhan Anda
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
    
    public String generateNoRegister(java.util.Date inputDate) {
        // Format tanggal input menjadi "yyyyMM"
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        String yearMonth = sdf.format(inputDate);

        // Query untuk mencari nomor bukti tertinggi yang memiliki awalan "BON-" + tahun + bulan yang sama
        String sql = "SELECT MAX(SUBSTRING(no_register, 11)) AS last_number " +
                     "FROM kas_bon_karyawan " +
                     "WHERE no_register LIKE 'BON-" + yearMonth + "%'";

        try (Statement stmt = conn.createStatement(); 
            ResultSet rs = stmt.executeQuery(sql)) {
            // Inisialisasi nomor urut
            int lastNumber = 0;
            
            // Jika ada data terakhir, ambil nomor urut terakhir
            if (rs.next()) {
                String lastNumberStr = rs.getString("last_number");
                if (lastNumberStr != null) {
                    lastNumber = Integer.parseInt(lastNumberStr);
                }
            }
            
            // Increment nomor urut terakhir
            lastNumber++;

            // Format nomor bukti baru
            String noBuktiBaru = "BON-" + yearMonth + String.format("%04d", lastNumber);

            // Kembalikan nomor bukti baru
            return noBuktiBaru;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

