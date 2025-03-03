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
import com.bprasojo.ekspedisi.model.PembayaranKasBon;
import com.bprasojo.ekspedisi.utils.AppUtils;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PembayaranKasBonDAO extends ParentDAO{
    private Connection conn;

    public PembayaranKasBonDAO() {
        try {
            this.conn = DatabaseConnection.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(PembayaranKasBonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        _nama_table_ = "pembayaran_kas_bon";
    }

    public void save(PembayaranKasBon pembayaranKasBon) throws SQLException {
        if (!validasiClosing(pembayaranKasBon.getId(), pembayaranKasBon.getTanggal())){
            throw new SQLException("Data tidak bisa disimpan karena sudah closing");
        }
        
        
        boolean previousAutoCommit = conn.getAutoCommit();
        conn.setAutoCommit(false);

        try {
            Date tanggal = new Date(pembayaranKasBon.getTanggal().getTime());

            // Menentukan SQL berdasarkan apakah ID ada atau tidak
            int kas_bon_old_id = 0;
            String sql;            
            if (pembayaranKasBon.getId() <= 0) {
                String noRegister = generateNoRegister(pembayaranKasBon.getTanggal());
                pembayaranKasBon.setNoRegister(noRegister);

                sql = "INSERT INTO " + _nama_table_ + " (tanggal, kas_bon_karyawan_id, perkiraan_kas_id, nominal, keterangan, bank_id, sumber_dana, no_register, user_create) " +
                      "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            } else {
                // kembalikan dulu pelunasan jika kas bon beda
                kas_bon_old_id = getById(pembayaranKasBon.getId()).getKasBonKaryawanId();
                sql = "UPDATE " + _nama_table_ + " SET tanggal=?, kas_bon_karyawan_id=?, perkiraan_kas_id=?, nominal=?, keterangan=?, bank_id=?, sumber_dana=?, no_register=?, user_update = ? " +
                      "WHERE id=?";
            }

            // Menggunakan PreparedStatement dengan parameter yang sama
            try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setDate(1, tanggal);
                stmt.setInt(2, pembayaranKasBon.getKasBonKaryawanId());
                stmt.setInt(3, pembayaranKasBon.getPerkiraanKasId());
                stmt.setInt(4, pembayaranKasBon.getNominal());
                stmt.setString(5, pembayaranKasBon.getKeterangan());
                stmt.setInt(6, pembayaranKasBon.getBankId());
                stmt.setString(7, pembayaranKasBon.getSumberDana());
                stmt.setString(8, pembayaranKasBon.getNoRegister());

                // Jika melakukan update, tambahkan ID sebagai parameter terakhir
                if (pembayaranKasBon.getId() <= 0) {
                    stmt.setString(9, pembayaranKasBon.getUserCreate());
                } else {
                    stmt.setString(9, pembayaranKasBon.getUserUpdate());
                    stmt.setInt(10, pembayaranKasBon.getId());
                }

                stmt.executeUpdate();

                // Jika insert, ambil ID yang dihasilkan
                if (pembayaranKasBon.getId() <= 0) {
                    try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            pembayaranKasBon.setId(generatedKeys.getInt(1));
                        }
                    }
                }
            }

            if (pembayaranKasBon.getKasBonKaryawanId() != kas_bon_old_id && kas_bon_old_id != 0){
                KasBonKaryawanDAO kbkDAO = new KasBonKaryawanDAO();
                KasBonKaryawan kbk = kbkDAO.getById(kas_bon_old_id);
                if (kbk != null){
                    updatePelunasanKasBonKaryawan(kbk);
                }
            }
            updatePelunasanKasBonKaryawan(pembayaranKasBon.getKasBonKaryawan());
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

    private Integer getTotalPelunasan(int kasBonID) throws SQLException{
        int pelunasan = 0;
        
        String sql = "select sum(nominal) as total_nominal "
                     + " from pembayaran_kas_bon "
                     + " where kas_bon_karyawan_id =? ";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, kasBonID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    pelunasan = pelunasan + rs.getInt("total_nominal");
                }
            }
        }
        
        return pelunasan;
    }
    private void updatePelunasanKasBonKaryawan(KasBonKaryawan kasBonKaryawan) throws SQLException{
        int pelunasan = getTotalPelunasan(kasBonKaryawan.getId());
        String statusLunas = "Belum";
        if (kasBonKaryawan.getNominal() <= pelunasan){
            statusLunas = "Lunas";
        }
        
        String sql = "update kas_bon_karyawan set pelunasan = ?, status_lunas=? where id = ?";
        
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, pelunasan);
        stmt.setString(2, statusLunas);
        stmt.setInt(3, kasBonKaryawan.getId());
        
        stmt.executeUpdate();
                
    }
    
    public PembayaranKasBon getById(int id) throws SQLException {
        String sql = "SELECT * FROM pembayaran_kas_bon WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new PembayaranKasBon(
                        rs.getInt("id"),
                        rs.getDate("tanggal"),
                        rs.getInt("kas_bon_karyawan_id"),                        
                        rs.getInt("perkiraan_kas_id"),
                        rs.getInt("nominal"),
                        rs.getString("keterangan"),
                        rs.getInt("bank_Id"),
                        rs.getString("sumber_dana"),
                        rs.getString("no_register"),
                        rs.getString("user_create"),
                        rs.getString("user_update")
                            
                    );
                }
            }
        }
        return null;
    }

    

    public void delete(int id) throws SQLException {
        if (!validasiClosing(id, AppUtils.now())){
            throw new SQLException("Data tidak bisa dihapus karena sudah closing");
        }
        
        boolean previousAutoCommit = conn.getAutoCommit();
        conn.setAutoCommit(false);
        
        
        String sql = "DELETE FROM " + _nama_table_ + " WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            PembayaranKasBon pembayaranKasBon = getById(id);
            
            KasBonKaryawan kbk = pembayaranKasBon.getKasBonKaryawan();           
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
            updatePelunasanKasBonKaryawan(kbk);
            
            
            
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
    
    public List<Map<String, Object>> getPembayaranKasBonByPage(Integer page, java.util.Date tglAwal, java.util.Date tglAkhir, String filter) {
        List<Map<String, Object>> resultList = new ArrayList<>();

        String sql = "select a.*, c.nama as nama_karyawan, c.alamat as alamat_karyawan, b.no_register as no_kas_bon, b.nominal as hutang, b.pelunasan "
                    + " from pembayaran_kas_bon a "
                    + " inner join kas_bon_karyawan b on a.kas_bon_karyawan_id = b.id "
                    + " inner join stake_holder c on b.karyawan_id = c.id "
                    + " WHERE a.tanggal BETWEEN ? AND ?"; 

        if (filter != null && !filter.trim().isEmpty()) {
            sql += " AND (a.no_register like ?, c.nama like ? or c.alamat like ? or b.sumber_dana like ? or b.keterangan LIKE ? or b.no_register LIKE ?)";
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
    
    public String generateNoRegister(java.util.Date inputDate) {
        // Format tanggal input menjadi "yyyyMM"
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        String yearMonth = sdf.format(inputDate);

        // Query untuk mencari nomor bukti tertinggi yang memiliki awalan "BON-" + tahun + bulan yang sama
        String sql = "SELECT MAX(SUBSTRING(no_register, 11)) AS last_number " +
                     "FROM pembayaran_kas_bon " +
                     "WHERE no_register LIKE 'BYR-" + yearMonth + "%'";

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
            String noBuktiBaru = "BYR-" + yearMonth + String.format("%04d", lastNumber);

            // Kembalikan nomor bukti baru
            return noBuktiBaru;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

