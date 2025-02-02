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
import com.bprasojo.ekspedisi.model.TransaksiPembelianBBM;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TransaksiPembelianBBMDAO {
    private Connection conn;

    public TransaksiPembelianBBMDAO() {
        try {
            this.conn = DatabaseConnection.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(ArmadaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Menyimpan atau memperbarui data transaksi pembelian BBM
    public void save(TransaksiPembelianBBM transaksi) throws SQLException {
        String sql;
        boolean isInsert = transaksi.getId() == 0;

        if (isInsert) {
            sql = "INSERT INTO transaksi_pembelian_bbm (armada_Id, tanggal, km_Terakhir, km_Sekarang, nominal_BBM, keterangan) VALUES (?, ?, ?, ?, ?, ?)";
        } else {
            sql = "UPDATE transaksi_pembelian_bbm SET armada_Id = ?, tanggal = ?, km_Terakhir = ?, km_Sekarang = ?, nominal_BBM = ?, keterangan = ? WHERE id = ?";
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql, isInsert ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS)) {
            stmt.setInt(1, transaksi.getArmadaId());
            stmt.setDate(2, new java.sql.Date(transaksi.getTanggal().getTime()));
            stmt.setInt(3, transaksi.getKmTerakhir());
            stmt.setInt(4, transaksi.getKmSekarang());
            stmt.setDouble(5, transaksi.getNominalBBM());
            stmt.setString(6, transaksi.getKeterangan());

            if (!isInsert) {
                stmt.setInt(7, transaksi.getId());
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
    public TransaksiPembelianBBM getById(int id) throws SQLException {
        String sql = "SELECT * FROM transaksi_pembelian_bbm WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new TransaksiPembelianBBM(
                    rs.getInt("id"),
                    rs.getInt("armada_Id"),
                    rs.getDate("tanggal"),
                    rs.getInt("km_terakhir"),
                    rs.getInt("km_sekarang"),
                    rs.getInt("nominal_BBM"),
                    rs.getString("keterangan")
                );
            }
        }
        return null;
    }
    
    public Integer getLastKM(String nopol) throws SQLException {
        String sql = "select a.km_sekarang as km_kemarin from transaksi_pembelian_bbm a" +
                     " inner join armada b on a.armada_id = b.id" +
                     " where b.nopol = ? " +
                     " order by a.tanggal desc limit 1";
        
        int lastKM = 0;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nopol);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                lastKM = rs.getInt("km_kemarin");
            }
        }
        
        return lastKM;
        
    }

    // Mengambil semua transaksi
    public List<TransaksiPembelianBBM> getAll() throws SQLException {
        List<TransaksiPembelianBBM> transaksiList = new ArrayList<>();
        String sql = "SELECT * FROM transaksi_pembelian_bbm";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                transaksiList.add(new TransaksiPembelianBBM(
                    rs.getInt("id"),
                    rs.getInt("armada_Id"),
                    rs.getDate("tanggal"),
                    rs.getInt("km_Terakhir"),
                    rs.getInt("km_Sekarang"),
                    rs.getInt("nominal_BBM"),
                    rs.getString("keterangan")
                ));
            }
        }
        return transaksiList;
    }
    
    public List<Map<String, Object>> getTransaksiPembelianBBMByPage(Integer page, java.util.Date tglAwal, java.util.Date tglAkhir, String filter) {
        List<Map<String, Object>> resultList = new ArrayList<>();

        String sql = "SELECT a.*, b.nopol, b.kendaraan, b.pemilik "
                     + "FROM transaksi_pembelian_bbm a "
                     + "LEFT JOIN armada b ON a.armada_id = b.id "
                     + "WHERE a.tanggal BETWEEN ? AND ?"; 

        if (filter != null && !filter.trim().isEmpty()) {
            sql += " AND (a.keterangan LIKE ? OR b.nopol LIKE ? OR b.kendaraan LIKE ? OR b.pemilik LIKE ?)";
        }

        sql += " order by a.tanggal desc , a.id desc LIMIT ? OFFSET ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Set date parameters
            stmt.setDate(1, new java.sql.Date(tglAwal.getTime()));
            stmt.setDate(2, new java.sql.Date(tglAkhir.getTime()));

            int paramIndex = 3;

            // Set filter parameters if present
            if (filter != null && !filter.trim().isEmpty()) {
                for (int i = 0; i < 4; i++) { // Filter untuk 4 kolom
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

    // Menghapus transaksi berdasarkan ID
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM transaksi_pembelian_bbm WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}

