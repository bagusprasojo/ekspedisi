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
import com.bprasojo.ekspedisi.model.TagihanCustomer;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TagihanCustomerDAO {
    private Connection conn;

    public TagihanCustomerDAO() {
        try {
            this.conn = DatabaseConnection.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(TagihanCustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String generateNoInvoice(java.util.Date inputDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        String yearMonth = sdf.format(inputDate);

        // Query untuk mencari nomor bukti tertinggi yang memiliki awalan "BON-" + tahun + bulan yang sama
        String sql = "SELECT MAX(SUBSTRING(no_invoice, 11)) AS last_number " +
                     "FROM tagihan_customer " +
                     "WHERE no_invoice LIKE 'INV-" + yearMonth + "%'";

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
            String noBuktiBaru = "INV-" + yearMonth + String.format("%04d", lastNumber);

            // Kembalikan nomor bukti baru
            return noBuktiBaru;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    // Simpan atau Update
    public void save(TagihanCustomer tagihan) throws SQLException {
        String sql;
        
        if (tagihan.getPelunasan() > 0){
            throw new SQLException("Invoice tidak bisa diubah karena sudah ada pembayaran");
        }
        
        if (tagihan.getNilaiPekerjaan() <= tagihan.getPelunasan()){
            tagihan.setStatusLunas("Lunas");
        } else {
            tagihan.setStatusLunas("Belum");
        }
        
        boolean isInsert = tagihan.getId() == 0; 
        if (isInsert) {
            tagihan.setNoInvoice(generateNoInvoice(tagihan.getTanggal()));
            sql = "INSERT INTO tagihan_customer (customer_id, no_invoice, tanggal, pekerjaan, nilai_pekerjaan, ppn_persen, ppn, total, terbilang, pelunasan, status_lunas, keterangan, perkiraan_piutang_id) " +
                  "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";
        } else {
            sql = "UPDATE tagihan_customer SET customer_id=?, no_invoice=?, tanggal=?, pekerjaan=?, nilai_pekerjaan=?, ppn_persen=?, ppn=?, total=?, terbilang=?, pelunasan=?, status_lunas=?, keterangan=?, perkiraan_piutang_id=? WHERE id=?";
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, tagihan.getCustomerId());
            stmt.setString(2, tagihan.getNoInvoice());
            
            Date tanggal = new Date(tagihan.getTanggal().getTime());
            stmt.setDate(3, tanggal);
            stmt.setString(4, tagihan.getPekerjaan());
            stmt.setInt(5, tagihan.getNilaiPekerjaan());
            stmt.setInt(6, tagihan.getPpnPersen());
            stmt.setInt(7, tagihan.getPpn());
            stmt.setInt(8, tagihan.getTotal());
            stmt.setString(9, tagihan.getTerbilang());
            stmt.setInt(10, tagihan.getPelunasan());
            stmt.setString(11, tagihan.getStatusLunas());
            stmt.setString(12, tagihan.getKeterangan());
            stmt.setInt(13, tagihan.getPerkiraanPiutangId());
            
            if (tagihan.getId() != 0) {
                stmt.setInt(14, tagihan.getId());
            }

            stmt.executeUpdate();
            if (isInsert) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        tagihan.setId(generatedKeys.getInt(1));
                    }
                }
            }
        }
    }

    // Hapus data
    public void delete(int id) throws SQLException {
        TagihanCustomer invoice = getById(id);
        if (invoice == null){
            throw new SQLException("Data invoice tidak ditemukan");
        }
        
        if (invoice.getPelunasan() > 0){
            throw new SQLException("Data invoice tidak bisa dihapus karena sudah ada pembayaran");
        }
        
        String sql = "DELETE FROM tagihan_customer WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // Dapatkan data berdasarkan ID atau No Invoice
    private TagihanCustomer getByField(String field, Object value) throws SQLException {
        String sql = "SELECT * FROM tagihan_customer WHERE " + field + " = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (value instanceof Integer) {
                stmt.setInt(1, (Integer) value);
            } else if (value instanceof String) {
                stmt.setString(1, (String) value);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new TagihanCustomer(
                        rs.getInt("id"),
                        rs.getInt("customer_id"),
                        rs.getString("no_invoice"),
                        rs.getDate("tanggal"),
                        rs.getString("pekerjaan"),
                        rs.getInt("nilai_pekerjaan"),
                        rs.getInt("ppn_persen"),
                        rs.getInt("ppn"),
                        rs.getInt("total"),
                        rs.getString("terbilang"),
                        rs.getInt("pelunasan"),
                        rs.getString("status_lunas"),
                        rs.getString("keterangan"),
                        rs.getInt("perkiraan_piutang_id")
                    );
                }
            }
        }
        return null;
    }

    public TagihanCustomer getById(int id) throws SQLException {
        return getByField("id", id);
    }

    public TagihanCustomer getByNoInvoice(String noInvoice) throws SQLException {
        return getByField("no_invoice", noInvoice);
    }
    
    public List<Map<String, Object>> getTagihanCustomerByPage(Integer page, java.util.Date tglAwal, java.util.Date tglAkhir, String filter) throws SQLException {
        List<Map<String, Object>> resultList = new ArrayList<>();

        // Query dasar
        String sql = "SELECT a.*, b.nama as nama_customer, 'Lia' as pc FROM tagihan_customer a " 
                     + " inner join stake_holder b on a.customer_id = b.id "
                     + " WHERE a.tanggal BETWEEN ? AND ? "; 

        if (filter != null && !filter.trim().isEmpty()) {
            sql += " and (no_invoice LIKE ? OR pekerjaan LIKE ? or keterangan like ? or b.nama like ? or a.keterangan like ?)";
        }

        sql += " LIMIT ? OFFSET ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
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
        }

        return resultList;
    }

}

