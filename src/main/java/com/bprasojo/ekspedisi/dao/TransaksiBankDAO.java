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
import com.bprasojo.ekspedisi.model.TransaksiBank;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TransaksiBankDAO extends ParentDAO{
//    private Connection conn;

   JurnalDAO jurnalDAO = null;
   
    public TransaksiBankDAO() {
        super();
        _nama_table_ = "transaksi_bank";
        jurnalDAO = new JurnalDAO();
    }

    public void saveJurnal(TransaksiBank transaksi) throws SQLException{
        if (!validasiClosing(transaksi.getId(), transaksi.getTanggal())){
            throw new SQLException("Data tidak bisa dijurnal karena sudah closing");
        }
        
        Jurnal jurnal = new Jurnal();
        jurnal.setTransaksiId(transaksi.getId());
        jurnal.setNoJurnal(transaksi.getNoBukti());
        jurnal.setTanggal(transaksi.getTanggal());
        jurnal.setTransaksi(transaksi.getClass().getName());
        jurnal.setKeterangan(transaksi.getUraian());
        jurnal.setUserCreate(transaksi.getUserCreate());
        jurnal.setUserUpdate(transaksi.getUserUpdate());        
        
        JurnalDetail jdD, jdK, jdAdm;            
        if (transaksi.getJenisTransaksi().getKode().equals("20") ){ // Transfer antar bank
            jdD = new JurnalDetail(0, transaksi.getBankTujuan().getAkun().getId(), transaksi.getDebet(), 0);
            jdK = new JurnalDetail(0, transaksi.getBankUtama().getAkun().getId(), 0, transaksi.getDebet() + transaksi.getBiayaAdmBank());                    
            jdAdm = new JurnalDetail(0, transaksi.getJenisTransaksi().getAkunId(), transaksi.getBiayaAdmBank(),0);
            
            jurnal.getJurnalDetails().add(jdD);
            jurnal.getJurnalDetails().add(jdK);
            jurnal.getJurnalDetails().add(jdAdm);
        } else {
//            JurnalDetail jdD, jdK;
            if (transaksi.getKredit() > 0){
                jdD = new JurnalDetail(0, transaksi.getBankUtama().getAkun().getId(), transaksi.getKredit(), 0);
                jdK = new JurnalDetail(0, transaksi.getJenisTransaksi().getAkunId(), 0, transaksi.getKredit());
            } else {
                jdK = new JurnalDetail(0, transaksi.getJenisTransaksi().getAkunId(), transaksi.getDebet(), 0);
                jdD = new JurnalDetail(0, transaksi.getBankUtama().getAkun().getId(), 0, transaksi.getDebet());        
            } 

            jurnal.getJurnalDetails().add(jdD);
            jurnal.getJurnalDetails().add(jdK);
        }
        
        jurnalDAO.deleteByTransId(transaksi.getId(), transaksi.getClass().getName());
        jurnalDAO.save(jurnal);
        
    }
    // Menyimpan atau memperbarui transaksi bank
    public String generateNoBukti(java.util.Date inputDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        String yearMonth = sdf.format(inputDate);

        // Query untuk mencari nomor bukti tertinggi yang memiliki awalan "BON-" + tahun + bulan yang sama
        String sql = "SELECT MAX(SUBSTRING(no_bukti, 11)) AS last_number " +
                     "FROM " + _nama_table_ + " " +
                     "WHERE no_bukti LIKE 'BNK-" + yearMonth + "%'";

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
            String noBuktiBaru = "BNK-" + yearMonth + String.format("%04d", lastNumber);

            // Kembalikan nomor bukti baru
            return noBuktiBaru;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public void save(TransaksiBank transaksi) throws SQLException {
        if (transaksi.getId() > 0){
            TransaksiBank tb = getById(transaksi.getId());
            
            if (!validasiClosing(tb.getId(), tb.getTanggal())){
                throw new SQLException("Data tidak bisa disimpan karena sudah closing");
            }
        }
        
        if (!validasiClosing(transaksi.getId(), transaksi.getTanggal())){
            throw new SQLException("Data tidak bisa disimpan karena sudah closing");
        }
        
        boolean previousAutoCommit = conn.getAutoCommit();
        conn.setAutoCommit(false);        
        try {        
            String sql;
            boolean isInsert = transaksi.getId() == 0;

            if (isInsert) {
                sql = "INSERT INTO " + _nama_table_ + " (tanggal, bank_utama_id, jenis_transaksi_id, debet, kredit, bank_tujuan_id, akun_utama_id, akun_tujuan_id, biaya_adm_bank, uraian, no_bukti, user_create) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?)";
            } else {
                sql = "UPDATE " + _nama_table_ + " SET tanggal = ?, bank_utama_id = ?, jenis_transaksi_id = ?, debet = ?, kredit = ?, bank_tujuan_id = ?, akun_utama_id = ?, akun_tujuan_id = ?, biaya_adm_bank=?, uraian = ?, no_bukti=?, user_update =?  WHERE id = ?";
            }

            if (isInsert || transaksi.getNoBukti().equals("")){
                String noBukti = generateNoBukti(transaksi.getTanggal());
                transaksi.setNoBukti(noBukti);
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
                stmt.setString(11, transaksi.getNoBukti());

                if (isInsert) {
                    stmt.setString(12, transaksi.getUserCreate());
                } else {
                    stmt.setString(12, transaksi.getUserUpdate());
                    stmt.setInt(13, transaksi.getId());
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
            saveJurnal(transaksi);
            
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
    
    public void IsiNoBuktiNull() throws SQLException{
        String sql = "SELECT * FROM "+ _nama_table_ +" WHERE ifnull(no_bukti,'') = '' order by tanggal, id";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    TransaksiBank tb = getById(rs.getInt("id"));
                    if (tb != null){
                        save(tb);
                    }
                
                }
                
            }
        }
        
    }
    private TransaksiBank getByQuery(String query, Object param) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            if (param instanceof Integer) {
                stmt.setInt(1, (Integer) param);
            } else if (param instanceof String) {
                stmt.setString(1, (String) param);
            }

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
                    rs.getString("user_update"),
                    rs.getString("no_bukti")
                );
            }
        }
        return null;
    }

    
    public TransaksiBank getById(int id) throws SQLException {
        String sql = "SELECT * FROM transaksi_bank WHERE id = ?";
        return getByQuery(sql, id);
    }

    public TransaksiBank getByNoBukti(String noBukti) throws SQLException {
        String sql = "SELECT * FROM transaksi_bank WHERE no_bukti = ?";
        return getByQuery(sql, noBukti);
    }

    
    public List<Map<String, Object>> getTransaksiBankByPage(Integer page, java.util.Date tglAwal, java.util.Date tglAkhir, String filter) {
        List<Map<String, Object>> resultList = new ArrayList<>();

        String sql = "SELECT a.*, b.no_rekening, b.nama_bank, b.atas_nama, c.kode, c.nama, 'Lia' as pc   "
                     + " FROM transaksi_bank a "
                     + " inner join bank b ON a.bank_utama_id = b.id"
                     + " inner join jenis_transaksi c on a.jenis_transaksi_id = c.id"
                     + " WHERE a.tanggal BETWEEN ? AND ?"; 

        if (filter != null && !filter.trim().isEmpty()) {
            sql += " AND (a.no_bukti like ? or a.uraian LIKE ? OR b.no_rekening LIKE ? OR b.nama_bank LIKE ? OR b.atas_nama LIKE ? OR c.kode LIKE ? OR c.nama LIKE ?)";
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
    
    public void delete(int id) throws SQLException {
        TransaksiBank tb = getById(id);
        if (tb == null){
            return;
        }
        
        if (!validasiClosing(id, tb.getTanggal())){
            throw new SQLException("Data tidak bisa dihapus karena sudah closing");
        }
        
        boolean previousAutoCommit = conn.getAutoCommit();
        conn.setAutoCommit(false);
        try {
            String sql = "DELETE FROM " + _nama_table_ + " WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }
            
            jurnalDAO.deleteByTransId(tb.getId(), tb.getClass().getName());
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
}

