/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bprasojo.ekspedisi.dao;

/**
 *
 */
import com.bprasojo.ekspedisi.model.Jurnal;
import com.bprasojo.ekspedisi.model.JurnalDetail;
import com.bprasojo.ekspedisi.model.TagihanCustomer;
import com.bprasojo.ekspedisi.utils.AppUtils;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class TagihanCustomerDAO extends ParentDAO{

    private final JurnalDAO jurnalDAO;
//    private Connection conn;

    private final ConfigDAO configDAO = new ConfigDAO();
    public TagihanCustomerDAO() {
        super();
        _nama_table_ = "tagihan_customer";
        
        jurnalDAO = new JurnalDAO();
    }

    public String generateNoInvoice(java.util.Date inputDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String year = sdf.format(inputDate);
        sdf = new SimpleDateFormat("MM");
        int month = Integer.parseInt(sdf.format(inputDate));
        
        String yearMonth = "/" + AppUtils.toRoman(month) + "/INV_TBL/" + year;

        // Query untuk mencari nomor bukti tertinggi yang memiliki awalan "BON-" + tahun + bulan yang sama
        String sql = "SELECT MAX(SUBSTR(no_invoice,1,3)) AS last_number  " +
                     " FROM " + _nama_table_ + 
                     " WHERE no_invoice LIKE '%" + yearMonth + "%'";

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
            String noBuktiBaru = String.format("%03d", lastNumber) + yearMonth;

            // Kembalikan nomor bukti baru
            return noBuktiBaru;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public void saveJurnal(TagihanCustomer transaksi) throws SQLException{
        Jurnal jurnal = new Jurnal();
        jurnal.setTransaksiId(transaksi.getId());
        
        jurnal.setNoJurnal(transaksi.getNoInvoice());
        jurnal.setTanggal(transaksi.getTanggal());
        jurnal.setTransaksi(transaksi.getClass().getName());
        jurnal.setKeterangan(transaksi.getKeterangan());
        jurnal.setUserCreate(transaksi.getUserCreate());
        jurnal.setUserUpdate(transaksi.getUserUpdate());        
        

        JurnalDetail jdD, jdK, jdPPN;
        int perkiraanPendapatanJasaId  = Integer.parseInt(configDAO.getByKode("AKUN_PENDAPATAN_JASA").getNilai());
        int perkiraanPPNId  = Integer.parseInt(configDAO.getByKode("AKUN_PPN_ID").getNilai());
        
        jdD = new JurnalDetail(0, transaksi.getPerkiraanPiutangId(), transaksi.getNilaiPekerjaan() + transaksi.getPpn(), 0);
        jdK = new JurnalDetail(0, perkiraanPendapatanJasaId, 0, transaksi.getNilaiPekerjaan());
        jdPPN = new JurnalDetail(0, perkiraanPPNId, 0, transaksi.getPpn());
        
        
        jurnal.getJurnalDetails().add(jdD);
        jurnal.getJurnalDetails().add(jdK);
        jurnal.getJurnalDetails().add(jdPPN);
        
        jurnalDAO.deleteByTransId(transaksi.getId(), transaksi.getClass().getName());
        jurnalDAO.save(jurnal);
        
    }
    // Simpan atau Update
    public void save(TagihanCustomer tagihan) throws SQLException {
        if (!validasiClosing(tagihan.getId(), tagihan.getTanggal())){
            throw new SQLException("Data tidak bisa disimpan karena sudah closing");
        }
        
        String sql;
        
        if (tagihan.getPelunasan() > 0){
            throw new SQLException("Invoice tidak bisa diubah karena sudah ada pembayaran");
        }
        
        boolean previousAutoCommit = conn.getAutoCommit();
        boolean isInsert = tagihan.getId() == 0; 
        conn.setAutoCommit(false);
        try {
            if (tagihan.getNilaiPekerjaan() <= tagihan.getPelunasan()){
                tagihan.setStatusLunas("Lunas");
            } else {
                tagihan.setStatusLunas("Belum");
            }

            
            if (isInsert) {
                tagihan.setNoInvoice(generateNoInvoice(tagihan.getTanggal()));
                sql = "INSERT INTO " + _nama_table_ + " (customer_id, no_invoice, tanggal, pekerjaan, nilai_pekerjaan, ppn_persen, ppn, total, terbilang, pelunasan, status_lunas, keterangan, perkiraan_piutang_id, user_create) " +
                      "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?)";
            } else {
                sql = "UPDATE " + _nama_table_ + " SET customer_id=?, no_invoice=?, tanggal=?, pekerjaan=?, nilai_pekerjaan=?, ppn_persen=?, ppn=?, total=?, terbilang=?, pelunasan=?, status_lunas=?, keterangan=?, perkiraan_piutang_id=?, user_update=? WHERE id=?";
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

                if (tagihan.getId() <= 0) {
                    stmt.setString(14, tagihan.getUserCreate());
                } else {
                    stmt.setString(14, tagihan.getUserUpdate());
                    stmt.setInt(15, tagihan.getId());
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
            saveJurnal(tagihan);
            
            conn.commit();
        } catch (SQLException ex) {
            if (isInsert){
                tagihan.setId(0);
            }
            conn.rollback();
            throw ex; // Rethrow exception setelah rollback
        } finally {
            // Mengembalikan auto-commit ke status semula
            conn.setAutoCommit(previousAutoCommit);
        }
    }

    // Hapus data
    public void delete(int id) throws SQLException {
        TagihanCustomer tc = getById(id);
        if (tc == null){
            return;
        }
        
        if (!validasiClosing(id, tc.getTanggal())){
            throw new SQLException("Data tidak bisa dihapus karena sudah closing");
        }
        
        if (tc.getPelunasan() > 0){
            throw new SQLException("Data sudah dibayar, tidak bisa dihapus/ubah");
        }
        
        boolean previousAutoCommit = conn.getAutoCommit();
        conn.setAutoCommit(false);
        try {
            TagihanCustomer invoice = getById(id);
            if (invoice == null){
                throw new SQLException("Data invoice tidak ditemukan");
            }

            if (invoice.getPelunasan() > 0){
                throw new SQLException("Data invoice tidak bisa dihapus karena sudah ada pembayaran");
            }

            String sql = "DELETE FROM " + _nama_table_ + " WHERE id=?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }
            
            jurnalDAO.deleteByTransId(invoice.getId(), invoice.getClass().getName());
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

    // Dapatkan data berdasarkan ID atau No Invoice
    private TagihanCustomer getByField(String field, Object value) throws SQLException {
        String sql = "SELECT * FROM " + _nama_table_ + " WHERE " + field + " = ?";
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
//                        rs.getInt("total"),
                        rs.getString("terbilang"),
                        rs.getInt("pelunasan"),
                        rs.getString("status_lunas"),
                        rs.getString("keterangan"),
                        rs.getInt("perkiraan_piutang_id"),
                        rs.getString("user_create"),
                        rs.getString("user_update")
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
        String sql = "SELECT a.*, b.nama as nama_customer FROM " + _nama_table_ + " a " 
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
    
    public int getRandomIDSudahDibayar() throws SQLException {
        int id = 0;
        java.util.Date tglClosing = getLastClosingDate();
        String sql = "SELECT id FROM " + _nama_table_ + " where tanggal > ? and pelunasan > 0";
        List<Integer> ids = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, new java.sql.Date(tglClosing.getTime()));
        
             try (ResultSet rs = stmt.executeQuery()) {
                // Menyimpan semua id yang ditemukan ke dalam list
                while (rs.next()) {
                    ids.add(rs.getInt("id"));
                }

                // Memilih id secara acak dari list jika ada id yang ditemukan
                if (!ids.isEmpty()) {
                    Random rand = new Random();
                    id = ids.get(rand.nextInt(ids.size()));
                }
            }

            

        } catch (SQLException ex) {
            AppUtils.showErrorDialog("Gagal get random ID\n" + ex.getMessage());
        }

        return id;
    }
    
    
    
    public int getRandomIDBelumLunas() throws SQLException {
        int id = 0;
        String sql = "SELECT id FROM " + _nama_table_ + " where status_lunas ='Belum' ";
        List<Integer> ids = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // Menyimpan semua id yang ditemukan ke dalam list
            while (rs.next()) {
                ids.add(rs.getInt("id"));
            }

            // Memilih id secara acak dari list jika ada id yang ditemukan
            if (!ids.isEmpty()) {
                Random rand = new Random();
                id = ids.get(rand.nextInt(ids.size()));
            }

        }

        return id;
    }

}

