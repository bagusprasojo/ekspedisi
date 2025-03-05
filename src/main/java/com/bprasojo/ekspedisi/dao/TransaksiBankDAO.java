/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bprasojo.ekspedisi.dao;

/**
 *
 */
import com.bprasojo.ekspedisi.model.TransaksiBank;
import com.bprasojo.ekspedisi.utils.AppUtils;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TransaksiBankDAO extends ParentDAO{
//    private Connection conn;

    public TransaksiBankDAO() {
        super();
        _nama_table_ = "transaksi_bank";
    }

    // Menyimpan atau memperbarui transaksi bank
    public void save(TransaksiBank transaksi) throws SQLException {
        if (!validasiClosing(transaksi.getId(), transaksi.getTanggal())){
            throw new SQLException("Data tidak bisa disimpan karena sudah closing");
        }
        
        String sql;
        boolean isInsert = transaksi.getId() == 0;

        if (isInsert) {
            sql = "INSERT INTO " + _nama_table_ + " (tanggal, bank_utama_id, jenis_transaksi_id, debet, kredit, bank_tujuan_id, akun_utama_id, akun_tujuan_id, biaya_adm_bank, uraian, user_create) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?,?,?)";
        } else {
            sql = "UPDATE " + _nama_table_ + " SET tanggal = ?, bank_utama_id = ?, jenis_transaksi_id = ?, debet = ?, kredit = ?, bank_tujuan_id = ?, akun_utama_id = ?, akun_tujuan_id = ?, biaya_adm_bank=?, uraian = ?, user_update =?  WHERE id = ?";
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql, isInsert ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS)) {
            stmt.setDate(1, new java.sql.Date(transaksi.getTanggal().getTime()));
            stmt.setInt(2, transaksi.getBankUtamaId());
            stmt.setInt(3, transaksi.getJenisTransaksiId());
            stmt.setDouble(4, transaksi.getDebet());
            stmt.setDouble(5, transaksi.getKredit());
            stmt.setInt(6, transaksi.getBankTujuanId());
            stmt.setInt(7, transaksi.getAkunUtamaId());
            stmt.setInt(8, transaksi.getAkunTujuanId());
            stmt.setInt(9, transaksi.getBiayaAdmBank());
            stmt.setString(10, transaksi.getUraian());

            if (isInsert) {
                stmt.setString(11, transaksi.getUserCreate());
            } else {
                stmt.setString(11, transaksi.getUserUpdate());
                stmt.setInt(12, transaksi.getId());
            }

            stmt.executeUpdate();

            if (isInsert) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        transaksi.setId(generatedKeys.getInt(1));
                    }
                }
            }
        }
    }

    // Mengambil transaksi berdasarkan ID
    public TransaksiBank getById(int id) throws SQLException {
        String sql = "SELECT * FROM transaksi_bank WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new TransaksiBank(
                    rs.getInt("id"),
                    rs.getDate("tanggal"),
                    rs.getInt("bank_utama_id"),
                    rs.getInt("jenis_transaksi_id"),                        
                    rs.getInt("debet"),
                    rs.getInt("kredit"),
                    rs.getInt("bank_tujuan_id"),
                    rs.getInt("akun_utama_id"),
                    rs.getInt("akun_tujuan_id"),
                    rs.getInt("biaya_adm_bank"),
                    rs.getString("uraian"),
                    rs.getString("user_create"),
                    rs.getString("user_update")
                );
            }
        }
        return null;
    }
    
    public List<Map<String, Object>> getTransaksiBankByPage(Integer page, java.util.Date tglAwal, java.util.Date tglAkhir, String filter) {
        List<Map<String, Object>> resultList = new ArrayList<>();

        String sql = "SELECT a.*, b.no_rekening, b.nama_bank, b.atas_nama, c.kode, c.nama, 'Lia' as pc   "
                     + " FROM transaksi_bank a "
                     + " inner join bank b ON a.bank_utama_id = b.id"
                     + " inner join jenis_transaksi c on a.jenis_transaksi_id = c.id"
                     + " WHERE a.tanggal BETWEEN ? AND ?"; 

        if (filter != null && !filter.trim().isEmpty()) {
            sql += " AND (a.uraian LIKE ? OR b.no_rekening LIKE ? OR b.nama_bank LIKE ? OR b.atas_nama LIKE ? OR c.kode LIKE ? OR c.nama LIKE ?)";
        }

        sql += " order by a.tanggal desc , a.id desc LIMIT ? OFFSET ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Set date parameters
            stmt.setDate(1, new java.sql.Date(tglAwal.getTime()));
            stmt.setDate(2, new java.sql.Date(tglAkhir.getTime()));

            int paramIndex = 3;

            // Set filter parameters if present
            if (filter != null && !filter.trim().isEmpty()) {
                for (int i = 0; i < 6; i++) { // Filter untuk 5 kolom
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
}

