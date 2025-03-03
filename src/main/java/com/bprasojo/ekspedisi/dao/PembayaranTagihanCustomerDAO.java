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
import com.bprasojo.ekspedisi.model.PembayaranTagihanCustomer;
import com.bprasojo.ekspedisi.model.TagihanCustomer;
import com.bprasojo.ekspedisi.utils.AppUtils;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PembayaranTagihanCustomerDAO extends ParentDAO{
    

    public PembayaranTagihanCustomerDAO() {
        super();
        _nama_table_ = "pembayaran_tagihan_customer";
    }

    public String generateNoRegister(java.util.Date inputDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        String yearMonth = sdf.format(inputDate);

        // Query untuk mencari nomor bukti tertinggi yang memiliki awalan "BON-" + tahun + bulan yang sama
        String sql = "SELECT MAX(SUBSTRING(no_register, 11)) AS last_number " +
                     "FROM " + _nama_table_ + " " +
                     "WHERE no_register LIKE 'BKM-" + yearMonth + "%'";

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
            String noBuktiBaru = "BKM-" + yearMonth + String.format("%04d", lastNumber);

            // Kembalikan nomor bukti baru
            return noBuktiBaru;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Integer getTotalPelunasan(int tagihanCustomerId) throws SQLException{
        int pelunasan = 0;
        
        String sql = "select sum(nominal_kas + pph) as total_nominal "
                     + " from " + _nama_table_ + " "
                     + " where tagihan_customer_id =? ";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, tagihanCustomerId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    pelunasan = pelunasan + rs.getInt("total_nominal");
                }
            }
        }
        
        return pelunasan;
    }
    
    private void updatePelunasanTagihanCustomer(TagihanCustomer tagihanCustomer) throws SQLException{
        int pelunasan = getTotalPelunasan(tagihanCustomer.getId());
        String statusLunas = "Belum";
        if (tagihanCustomer.getTotal() <= pelunasan){
            statusLunas = "Lunas";
        }
        
        String sql = "update tagihan_customer set pelunasan = ?, status_lunas=? where id = ?";
        
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, pelunasan);
        stmt.setString(2, statusLunas);
        stmt.setInt(3, tagihanCustomer.getId());
        
        stmt.executeUpdate();
                
    }
    
    public void delete(int id) throws SQLException {
        if (!validasiClosing(id, AppUtils.now())){
            throw new SQLException("Data tidak bisa dihapus karena sudah closing");
        }
        
        boolean previousAutoCommit = conn.getAutoCommit();

        // Mulai transaksi dengan menonaktifkan auto-commit
        conn.setAutoCommit(false);
        
        
        String sql = "DELETE FROM " + _nama_table_ + " WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            PembayaranTagihanCustomer pembayaranTagihanCustomer = getById(id);
            
            TagihanCustomer tc = pembayaranTagihanCustomer.getTagihanCustomer();
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
            updatePelunasanTagihanCustomer(tc);
            
            
            
            conn.commit();
        } catch (SQLException ex) {
            // Jika terjadi kesalahan, rollback transaksi
            conn.rollback();
            throw ex; // Rethrow exception setelah rollback
        } finally {
            // Mengembalikan auto-commit ke status semula
            conn.setAutoCommit(previousAutoCommit);
        }
    }
   
    public PembayaranTagihanCustomer getById(int id) throws SQLException {
        return getByField("id", id);
    }

    public PembayaranTagihanCustomer getByNoRegister(String noRegister) throws SQLException {
        return getByField("no_register", noRegister);
    }

    // 🔹 METHOD PRIVATE untuk menghindari duplikasi
    private PembayaranTagihanCustomer getByField(String field, Object value) throws SQLException {
        PembayaranTagihanCustomer pembayaran = null;
        
        String sql = "SELECT * FROM " + _nama_table_ + " WHERE " + field + " = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (value instanceof Integer) {
                stmt.setInt(1, (Integer) value);
            } else if (value instanceof String) {
                stmt.setString(1, (String) value);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    pembayaran = new PembayaranTagihanCustomer();
                    pembayaran.setId(rs.getInt("id"));
                    pembayaran.setNoRegister(rs.getString("no_register"));
                    pembayaran.setTagihanCustomerId(rs.getInt("tagihan_customer_id"));
                    pembayaran.setTanggal(rs.getDate("tanggal"));
                    pembayaran.setNominalKas(rs.getInt("nominal_kas"));
                    pembayaran.setPph(rs.getInt("pph"));
                    pembayaran.setPphPersen(rs.getInt("pph_persen"));
                    pembayaran.setPerkiraanKasId(rs.getInt("perkiraan_kas_id"));
                    pembayaran.setBankId(rs.getInt("bank_id"));
                    pembayaran.setPerkiraanPphId(rs.getInt("perkiraan_pph_id"));
                    pembayaran.setKeterangan(rs.getString("keterangan"));
                    pembayaran.setSumberDana(rs.getString("sumber_dana"));
                    pembayaran.setTerbilang(rs.getString("terbilang"));
                }
            }
        }
        return pembayaran;
    }
    
    public void save(PembayaranTagihanCustomer pembayaran) throws SQLException {
        if (!validasiClosing(pembayaran.getId(), pembayaran.getTanggal())){
            throw new SQLException("Data tidak bisa dihapus karena sudah closing");
        }
        
        boolean previousAutoCommit = conn.getAutoCommit();
        conn.setAutoCommit(false);
        
        try {
            int tagihan_customer_old_id = 0;
            String sql;
            if (pembayaran.getId() == 0) {
                String no_register = generateNoRegister(pembayaran.getTanggal());
                pembayaran.setNoRegister(no_register);
                
                sql = "INSERT INTO " + _nama_table_ + " (no_register, tagihan_customer_id, tanggal, nominal_kas, pph,pph_persen, perkiraan_kas_id, bank_id, perkiraan_pph_id, keterangan, sumber_dana, terbilang, user_create) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?)";
            } else {
                tagihan_customer_old_id = getById(pembayaran.getId()).getTagihanCustomerId();
                sql = "UPDATE " + _nama_table_ + " SET no_register = ?, tagihan_customer_id = ?, tanggal = ?, nominal_kas = ?, pph = ?,pph_persen = ?, perkiraan_kas_id = ?, bank_id = ?, perkiraan_pph_id = ?, keterangan = ?, sumber_dana = ?, terbilang = ?, user_update = ?  WHERE id = ?";
            }
            
            try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, pembayaran.getNoRegister());
                stmt.setInt(2, pembayaran.getTagihanCustomerId());
                stmt.setDate(3, new java.sql.Date(pembayaran.getTanggal().getTime()));
                stmt.setInt(4, pembayaran.getNominalKas());
                stmt.setInt(5, pembayaran.getPph());
                stmt.setInt(6, pembayaran.getPphPersen());
                stmt.setInt(7, pembayaran.getPerkiraanKasId());
                stmt.setInt(8, pembayaran.getBankId());
                stmt.setInt(9, pembayaran.getPerkiraanPphId());
                stmt.setString(10, pembayaran.getKeterangan());
                stmt.setString(11, pembayaran.getSumberDana());
                stmt.setString(12, pembayaran.getTerbilang());
                
                if (pembayaran.getId() <= 0) {
                    stmt.setString(13, pembayaran.getUserCreate());
                } else {
                    stmt.setString(13, pembayaran.getUserUpdate());
                    stmt.setInt(14, pembayaran.getId());
                }
                
                int affectedRows = stmt.executeUpdate();
                
                if (pembayaran.getId() == 0 && affectedRows > 0) {
                    try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            pembayaran.setId(generatedKeys.getInt(1));
                        }
                    }
                }
            }
            
            if (pembayaran.getTagihanCustomerId() != tagihan_customer_old_id && tagihan_customer_old_id != 0){
                TagihanCustomerDAO tcDAO = new TagihanCustomerDAO();
                TagihanCustomer tc = tcDAO.getById(tagihan_customer_old_id);
                if (tc != null){
                    updatePelunasanTagihanCustomer(tc);
                }
            }
            updatePelunasanTagihanCustomer(pembayaran.getTagihanCustomer());
            
        } catch (SQLException ex) {
            // Jika terjadi kesalahan, rollback transaksi
            conn.rollback();
            throw ex; // Rethrow exception setelah rollback
        } finally {
            // Mengembalikan auto-commit ke status semula
            conn.setAutoCommit(previousAutoCommit);
        }
    }
    
    public List<Map<String, Object>> getPembayaranTagihanCustomerByPage(Integer page, java.util.Date tglAwal, java.util.Date tglAkhir, String filter) {
        List<Map<String, Object>> resultList = new ArrayList<>();

        String sql = "select a.*, (a.nominal_kas + a.pph) as pembayaran, b.no_invoice, b.total as piutang, c.nama, c.alamat" 
                     + " from " + _nama_table_ + " a " 
                     + " inner join tagihan_customer b on a.tagihan_customer_id = b.id" 
                     + " inner join stake_holder c on b.customer_id = c.id "
                     + " WHERE a.tanggal BETWEEN ? AND ?"; 

        if (filter != null && !filter.trim().isEmpty()) {
            sql += " AND (a.no_register like ? or a.keterangan like ?  or a.sumber_dana like ? or b.no_invoice like ? or c.nama like ? or c.alamat like ?)";
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
}

