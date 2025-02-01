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
import com.bprasojo.ekspedisi.model.TransaksiKas;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TransaksiKasDAO {
    private Connection conn;

    // Constructor untuk inisialisasi koneksi database
    public TransaksiKasDAO() {
        try {
            this.conn = DatabaseConnection.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(ArmadaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Map<String, Object>> getTransaksiKasByPage(Integer page, java.util.Date tglAwal, java.util.Date tglAkhir, String filter) {
        List<Map<String, Object>> resultList = new ArrayList<>();

        String sql = "SELECT a.*, b.kode, b.nama, c.no_rekening, d.nopol "
                     + "FROM transaksi_kas a "
                     + "LEFT JOIN perkiraan b ON a.akun_transaksi_id = b.id "
                     + "LEFT JOIN bank c ON a.bank_id = c.id "
                     + "LEFT JOIN armada d ON a.armada_id = d.id "
                     + "WHERE a.tanggal BETWEEN ? AND ?"; 

        if (filter != null && !filter.trim().isEmpty()) {
            sql += " AND (a.keterangan LIKE ? OR b.kode LIKE ? OR b.nama LIKE ? OR c.no_rekening LIKE ? OR d.nopol LIKE ?)";
        }

        sql += " LIMIT ? OFFSET ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Set date parameters
            stmt.setDate(1, new java.sql.Date(tglAwal.getTime()));
            stmt.setDate(2, new java.sql.Date(tglAkhir.getTime()));

            int paramIndex = 3;

            // Set filter parameters if present
            if (filter != null && !filter.trim().isEmpty()) {
                for (int i = 0; i < 5; i++) { // Filter untuk 5 kolom
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

    public void save(TransaksiKas transaksiKas) throws SQLException {
    String sql;
    boolean isInsert = transaksiKas.getId() == 0;

    if (isInsert) {
        sql = "INSERT INTO transaksi_kas (akun_kas_Id, akun_transaksi_id, tanggal, nominal_masuk, nominal_keluar, keterangan, armada_id, bank_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    } else {
        sql = "UPDATE transaksi_kas SET akun_kas_Id = ?, akun_transaksi_id = ?, tanggal = ?, nominal_masuk = ?, nominal_keluar = ?, keterangan = ?, armada_id = ?, bank_id = ? WHERE id = ?";
    }

    try (PreparedStatement statement = conn.prepareStatement(sql, isInsert ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS)) {
        statement.setInt(1, transaksiKas.getAkunKasId());
        statement.setInt(2, transaksiKas.getAkunTransaksiId());
        
        Date tanggal = new Date(transaksiKas.getTanggal().getTime());
        statement.setDate(3, tanggal);
        statement.setInt(4, transaksiKas.getNominalMasuk());
        statement.setInt(5, transaksiKas.getNominalKeluar());
        statement.setString(6, transaksiKas.getKeterangan());
        statement.setInt(7, transaksiKas.getArmadaId());
        statement.setInt(8, transaksiKas.getBankId());

        if (!isInsert) {
            statement.setInt(9, transaksiKas.getId()); // ID hanya ditambahkan jika UPDATE
        }

        statement.executeUpdate();

        // Jika INSERT, ambil ID yang dihasilkan
        if (isInsert) {
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    transaksiKas.setId(generatedKeys.getInt(1));
                }
            }
        }
    }
}

    

    // Menghapus data TransaksiKas
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM transaksi_kas WHERE id = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    // Mendapatkan satu data TransaksiKas berdasarkan ID
    public TransaksiKas getById(int id) throws SQLException {
        String sql = "SELECT * FROM transaksi_kas WHERE id = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    TransaksiKas transaksiKas = new TransaksiKas();
                    transaksiKas.setId(resultSet.getInt("id"));
                    transaksiKas.setAkunKasId(resultSet.getInt("akun_Kas_Id"));
                    transaksiKas.setAkunTransaksiId(resultSet.getInt("akun_Transaksi_Id"));
                    transaksiKas.setTanggal(resultSet.getDate("tanggal"));
                    transaksiKas.setNominalMasuk(resultSet.getInt("nominal_Masuk"));
                    transaksiKas.setNominalKeluar(resultSet.getInt("nominal_Keluar"));
                    transaksiKas.setKeterangan(resultSet.getString("keterangan"));
                    transaksiKas.setArmadaId(resultSet.getInt("armada_Id"));
                    transaksiKas.setBankId(resultSet.getInt("bank_Id"));
                    
                    return transaksiKas;
                }
            }
        }
        return null;
    }

    // Mendapatkan semua data TransaksiKas
    public List<TransaksiKas> getAll(PerkiraanDAO perkiraanDAO, ArmadaDAO armadaDAO) throws SQLException {
        List<TransaksiKas> transaksiKasList = new ArrayList<>();
        String sql = "SELECT * FROM transaksi_kas";
        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                TransaksiKas transaksiKas = new TransaksiKas();
                transaksiKas.setId(resultSet.getInt("id"));
                transaksiKas.setAkunKasId(resultSet.getInt("akun_Kas_Id"));
                transaksiKas.setAkunTransaksiId(resultSet.getInt("akun_Transaksi_Id"));
                transaksiKas.setTanggal(resultSet.getDate("tanggal"));
                transaksiKas.setNominalMasuk(resultSet.getInt("nominal_Masuk"));
                transaksiKas.setNominalKeluar(resultSet.getInt("nominal_Keluar"));
                transaksiKas.setKeterangan(resultSet.getString("keterangan"));
                transaksiKas.setArmadaId(resultSet.getInt("armada_Id"));
                transaksiKas.setBankId(resultSet.getInt("bank_Id"));
                transaksiKasList.add(transaksiKas);
            }
        }
        return transaksiKasList;
    }
}

