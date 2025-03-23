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
import com.bprasojo.ekspedisi.model.TransaksiKas;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;


public class TransaksiKasDAO extends ParentDAO{

    private final JurnalDAO jurnalDAO;
    

    // Constructor untuk inisialisasi koneksi database
    public TransaksiKasDAO() {
        super();
        _nama_table_ = "transaksi_kas";
        
        jurnalDAO = new JurnalDAO();
    }
    
    public void saveJurnal(TransaksiKas transaksi) throws SQLException{
        Jurnal jurnal = new Jurnal();
        jurnal.setTransaksiId(transaksi.getId());
        
        jurnal.setNoJurnal(transaksi.getNoBukti());
        jurnal.setTanggal(transaksi.getTanggal());
        jurnal.setTransaksi(transaksi.getClass().getName());
        jurnal.setKeterangan(transaksi.getKeterangan());
        jurnal.setUserCreate(transaksi.getUserCreate());
        jurnal.setUserUpdate(transaksi.getUserUpdate());        
        
        JurnalDetail jdD, jdK;
        if (transaksi.getNominalMasuk() > 0){
            jdD = new JurnalDetail(0, transaksi.getAkunKasId(), transaksi.getNominalMasuk(), 0);
            jdK = new JurnalDetail(0, transaksi.getAkunTransaksiId(), 0, transaksi.getNominalMasuk());
        } else {
            jdK = new JurnalDetail(0, transaksi.getAkunKasId(), transaksi.getNominalKeluar(), 0);
            jdD = new JurnalDetail(0, transaksi.getAkunTransaksiId(), 0, transaksi.getNominalKeluar());        
        } 
        
        jurnal.getJurnalDetails().add(jdD);
        jurnal.getJurnalDetails().add(jdK);
        
        jurnalDAO.deleteByTransId(transaksi.getId(), transaksi.getClass().getName());
        jurnalDAO.save(jurnal);
        
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
            sql += " AND (a.no_bukti like ? or a.keterangan LIKE ? OR b.kode LIKE ? OR b.nama LIKE ? OR c.no_rekening LIKE ? OR d.nopol LIKE ?)";
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

    public String generateNoBukti(java.util.Date inputDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        String yearMonth = sdf.format(inputDate);

        // Query untuk mencari nomor bukti tertinggi yang memiliki awalan "BON-" + tahun + bulan yang sama
        String sql = "SELECT MAX(SUBSTRING(no_bukti, 11)) AS last_number " +
                     "FROM " + _nama_table_ + " " +
                     "WHERE no_bukti LIKE 'KAS-" + yearMonth + "%'";

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
            String noBuktiBaru = "KAS-" + yearMonth + String.format("%04d", lastNumber);

            // Kembalikan nomor bukti baru
            return noBuktiBaru;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public void IsiNoBuktiNull() throws SQLException{
        String sql = "SELECT * FROM transaksi_kas WHERE ifnull(no_bukti,'') = '' order by tanggal, id";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    TransaksiKas tk = getById(rs.getInt("id"));
                    if (tk != null){
                        save(tk);
                    }
                
                }
                
            }
        }
        
    }
    
    public void save(TransaksiKas transaksiKas) throws SQLException {        
        if (!validasiClosing(transaksiKas.getId(), transaksiKas.getTanggal())){
            throw new SQLException("Transaksi tidak bisa disimpan karena sudah closing");
        }
        
        boolean previousAutoCommit = conn.getAutoCommit();
        conn.setAutoCommit(false);
        try {
            String sql;
            boolean isInsert = transaksiKas.getId() == 0;

            if (isInsert) {
                sql = "INSERT INTO " + _nama_table_ + " (akun_kas_Id, akun_transaksi_id, tanggal, nominal_masuk, nominal_keluar, keterangan, armada_id, bank_id, no_bukti, user_create) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?,?)";
            } else {
                sql = "UPDATE " + _nama_table_ + " SET akun_kas_Id = ?, akun_transaksi_id = ?, tanggal = ?, nominal_masuk = ?, nominal_keluar = ?, keterangan = ?, armada_id = ?, bank_id = ?, no_bukti=?, user_update=? WHERE id = ?";
            }

            if (isInsert  || transaksiKas.getNoBukti().equals("")){
                String noBukti = generateNoBukti(transaksiKas.getTanggal());
                transaksiKas.setNoBukti(noBukti);
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
                statement.setString(9, transaksiKas.getNoBukti());

                if (isInsert) {
                    statement.setString(10, transaksiKas.getUserCreate());
                } else {
                    statement.setString(10, transaksiKas.getUserUpdate());
                    statement.setInt(11, transaksiKas.getId()); // ID hanya ditambahkan jika UPDATE
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
            
            saveJurnal(transaksiKas);
            
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

    

    // Menghapus data TransaksiKas
    
    
    public void delete(int id) throws SQLException {
        TransaksiKas tk = getById(id);
        
        boolean previousAutoCommit = conn.getAutoCommit();
        conn.setAutoCommit(false);
        try {
            if (tk != null){
                if (!validasiClosing(id, tk.getTanggal())){
                    throw new SQLException("Transaksi tidak bisa dihapus karena sudah closing");
                }

                String sql = "DELETE FROM " + _nama_table_ + " WHERE id = ?";
                try (PreparedStatement statement = conn.prepareStatement(sql)) {
                    statement.setInt(1, id);
                    statement.executeUpdate();
                }
                
                jurnalDAO.deleteByTransId(tk.getId(), tk.getClass().getName());
            }
            
            
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

    // Mendapatkan satu data TransaksiKas berdasarkan ID
    // Method untuk memetakan ResultSet ke objek TransaksiKas
    private TransaksiKas mapResultSetToTransaksiKas(ResultSet resultSet) throws SQLException {
        TransaksiKas transaksiKas = new TransaksiKas();
        transaksiKas.setId(resultSet.getInt("id"));
        transaksiKas.setAkunKasId(resultSet.getInt("akun_Kas_Id"));
        transaksiKas.setAkunTransaksiId(resultSet.getInt("akun_Transaksi_Id"));
        transaksiKas.setTanggal(resultSet.getDate("tanggal"));
        transaksiKas.setNominalMasuk(resultSet.getInt("nominal_Masuk"));
        transaksiKas.setNominalKeluar(resultSet.getInt("nominal_Keluar"));
        transaksiKas.setKeterangan(resultSet.getString("keterangan"));
        transaksiKas.setNoBukti(resultSet.getString("no_bukti"));
        transaksiKas.setArmadaId(resultSet.getInt("armada_Id"));
        transaksiKas.setBankId(resultSet.getInt("bank_Id"));
        return transaksiKas;
    }

    // Method untuk mengambil data berdasarkan ID
    public TransaksiKas getById(int id) throws SQLException {
        String sql = "SELECT * FROM transaksi_kas WHERE id = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToTransaksiKas(resultSet); // Menggunakan metode pemetaan
                }
            }
        }
        return null;
    }

    // Method untuk mengambil data berdasarkan NoBukti
    public TransaksiKas getByNoBukti(String noBukti) throws SQLException {
        String sql = "SELECT * FROM transaksi_kas WHERE no_bukti = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, noBukti);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToTransaksiKas(resultSet); // Menggunakan metode pemetaan
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

