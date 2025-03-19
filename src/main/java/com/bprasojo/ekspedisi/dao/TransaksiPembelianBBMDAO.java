/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bprasojo.ekspedisi.dao;

/**
 *
 */
import com.bprasojo.ekspedisi.model.Config;
import com.bprasojo.ekspedisi.model.Jurnal;
import com.bprasojo.ekspedisi.model.JurnalDetail;
import com.bprasojo.ekspedisi.model.TransaksiPembelianBBM;
import com.bprasojo.ekspedisi.utils.AppUtils;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TransaksiPembelianBBMDAO extends ParentDAO {

    private final JurnalDAO jurnalDAO;
    private final ConfigDAO configDAO;
    

    public TransaksiPembelianBBMDAO() {
        super();
        _nama_table_ = "transaksi_pembelian_bbm";
        
        jurnalDAO = new JurnalDAO();
        configDAO = new ConfigDAO();
    }

    public void saveJurnal(TransaksiPembelianBBM transaksi) throws SQLException{
        Jurnal jurnal = new Jurnal();
        jurnal.setTransaksiId(transaksi.getId());
        jurnal.setNoJurnal(transaksi.getNoBukti());
        jurnal.setNoJurnal(transaksi.getNoBukti());
        jurnal.setTanggal(transaksi.getTanggal());
        jurnal.setTransaksi(transaksi.getClass().getName());
        jurnal.setUserCreate(transaksi.getUserCreate());
        jurnal.setUserUpdate(transaksi.getUserUpdate());        
        
        JurnalDetail jdD, jdK;
        
        int perkiraanBBMId = Integer.parseInt(configDAO.getByKode("AKUN_BBM_ID").getNilai());
        jdD = new JurnalDetail(0, perkiraanBBMId, transaksi.getNominalBBM(), 0);
        jdK = new JurnalDetail(0, transaksi.getBank().getAkun().getId(), 0, transaksi.getNominalBBM());
        
        jurnal.getJurnalDetails().add(jdD);
        jurnal.getJurnalDetails().add(jdK);
        
        jurnalDAO.deleteByTransId(transaksi.getId(), transaksi.getClass().getName());
        jurnalDAO.save(jurnal);
        
    }
    public String generateNoBukti(java.util.Date inputDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        String yearMonth = sdf.format(inputDate);

        // Query untuk mencari nomor bukti tertinggi yang memiliki awalan "BON-" + tahun + bulan yang sama
        String sql = "SELECT MAX(SUBSTRING(no_bukti, 11)) AS last_number " +
                     "FROM " + _nama_table_ + " " +
                     "WHERE no_bukti LIKE 'BBM-" + yearMonth + "%'";

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
            String noBuktiBaru = "BBM-" + yearMonth + String.format("%04d", lastNumber);

            // Kembalikan nomor bukti baru
            return noBuktiBaru;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public void IsiNoBuktiNull() throws SQLException{
        String sql = "SELECT * FROM " + _nama_table_ + " WHERE ifnull(no_bukti,'') = '' order by tanggal, id";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    TransaksiPembelianBBM bbm = getById(rs.getInt("id"));
                    if (bbm != null){
                        save(bbm);
                    }
                
                }
                
            }
        }
        
    }
    // Menyimpan atau memperbarui data transaksi pembelian BBM
    public void save(TransaksiPembelianBBM transaksi) throws SQLException {
        if (!validasiClosing(transaksi.getId(), transaksi.getTanggal())){
            throw new SQLException("Data tidak bisa disimpan karena sudah closing");
        }
        
        boolean previousAutoCommit = conn.getAutoCommit();
        conn.setAutoCommit(false);
        try {
            String sql;
        
            boolean isInsert = transaksi.getId() == 0;

            if (isInsert) {
                sql = "INSERT INTO " + _nama_table_ + " (armada_Id, tanggal, km_Terakhir, km_Sekarang, nominal_BBM, keterangan, driver_id, bank_id , no_bukti, user_create) VALUES (?, ?, ?, ?, ?, ?, ?,?,?,?)";
            } else {
                sql = "UPDATE " + _nama_table_ + " SET armada_Id = ?, tanggal = ?, km_Terakhir = ?, km_Sekarang = ?, nominal_BBM = ?, keterangan = ?, driver_id=?, bank_id=? , no_bukti=?, user_update =?  WHERE id = ?";
            }

            if (isInsert  || transaksi.getNoBukti().equals("")){
                String noBukti = generateNoBukti(transaksi.getTanggal());
                transaksi.setNoBukti(noBukti);            

            }
            try (PreparedStatement stmt = conn.prepareStatement(sql, isInsert ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS)) {
                stmt.setInt(1, transaksi.getArmadaId());
                stmt.setDate(2, new java.sql.Date(transaksi.getTanggal().getTime()));
                stmt.setInt(3, transaksi.getKmTerakhir());
                stmt.setInt(4, transaksi.getKmSekarang());
                stmt.setDouble(5, transaksi.getNominalBBM());
                stmt.setString(6, transaksi.getKeterangan());
                stmt.setInt(7, transaksi.getDriverId());
                stmt.setInt(8, transaksi.getBankId());
                stmt.setString(9, transaksi.getNoBukti());

                if (isInsert) {
                    stmt.setString(10, transaksi.getUserCreate());
                } else {
                    stmt.setString(10, transaksi.getUserUpdate());
                    stmt.setInt(11, transaksi.getId());
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

    // Method untuk memetakan ResultSet ke objek TransaksiPembelianBBM
    private TransaksiPembelianBBM mapResultSetToTransaksiPembelianBBM(ResultSet rs) throws SQLException {
        return new TransaksiPembelianBBM(
            rs.getInt("id"),
            rs.getInt("armada_Id"),
            rs.getDate("tanggal"),
            rs.getInt("km_terakhir"),
            rs.getInt("km_sekarang"),
            rs.getInt("nominal_BBM"),
            rs.getString("keterangan"),
            rs.getInt("driver_id"),
            rs.getString("user_create"),
            rs.getString("user_update"),
            rs.getInt("bank_id"),
            rs.getString("no_bukti")
        );
    }

    // Method untuk mengambil data berdasarkan ID
    public TransaksiPembelianBBM getById(int id) throws SQLException {
        String sql = "SELECT * FROM transaksi_pembelian_bbm WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToTransaksiPembelianBBM(rs); // Menggunakan metode pemetaan
            }
        }
        return null;
    }

    // Method untuk mengambil data berdasarkan NoBukti
    public TransaksiPembelianBBM getByNoBukti(String noBukti) throws SQLException {
        String sql = "SELECT * FROM transaksi_pembelian_bbm WHERE no_bukti = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, noBukti);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToTransaksiPembelianBBM(rs); // Menggunakan metode pemetaan
            }
        }
        return null;
    }

    
    public Integer getLastKM(String nopol) throws SQLException {
        String sql = "select a.km_sekarang as km_kemarin from transaksi_pembelian_bbm a" +
                     " inner join armada b on a.armada_id = b.id" +
                     " where b.nopol = ? " +
                     " order by a.tanggal desc, a.km_sekarang desc limit 1";
        
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
//    public List<TransaksiPembelianBBM> getAll() throws SQLException {
//        List<TransaksiPembelianBBM> transaksiList = new ArrayList<>();
//        String sql = "SELECT * FROM transaksi_pembelian_bbm";
//        try (Statement stmt = conn.createStatement()) {
//            ResultSet rs = stmt.executeQuery(sql);
//            while (rs.next()) {
//                transaksiList.add(new TransaksiPembelianBBM(
//                    rs.getInt("id"),
//                    rs.getInt("armada_Id"),
//                    rs.getDate("tanggal"),
//                    rs.getInt("km_Terakhir"),
//                    rs.getInt("km_Sekarang"),
//                    rs.getInt("nominal_BBM"),
//                    rs.getString("keterangan")
//                ));
//            }
//        }
//        return transaksiList;
//    }
    
    public List<Map<String, Object>> getTransaksiPembelianBBMByPage(Integer page, java.util.Date tglAwal, java.util.Date tglAkhir, String filter) {
        List<Map<String, Object>> resultList = new ArrayList<>();

        String sql = "SELECT a.*, b.nopol, b.kendaraan, b.pemilik, c.nama as driver, d.nama_bank "
                     + " FROM transaksi_pembelian_bbm a "
                     + " LEFT JOIN armada b ON a.armada_id = b.id "
                     + " LEFT JOIN stake_holder c ON a.driver_id = c.id "
                     + " left join bank d on a.bank_id = d.id "
                     + " WHERE a.tanggal BETWEEN ? AND ?"; 

        if (filter != null && !filter.trim().isEmpty()) {
            sql += " AND (a.no_bukti like ? or a.keterangan LIKE ? OR b.nopol LIKE ? OR b.kendaraan LIKE ? OR b.pemilik LIKE ? or c.nama like ?)";
        }

        sql += " order by a.tanggal desc , a.id desc LIMIT ? OFFSET ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Set date parameters
            stmt.setDate(1, new java.sql.Date(tglAwal.getTime()));
            stmt.setDate(2, new java.sql.Date(tglAkhir.getTime()));

            int paramIndex = 3;

            // Set filter parameters if present
            if (filter != null && !filter.trim().isEmpty()) {
                for (int i = 0; i < 6; i++) { // Filter untuk 4 kolom
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

    // Menghapus transaksi berdasarkan ID
    public void delete(int id) throws SQLException {
        TransaksiPembelianBBM tpb = getById(id);
        if (tpb == null){
            return;
        }
        
        if (!validasiClosing(id, tpb.getTanggal())){
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
            
            jurnalDAO.deleteByTransId(id, tpb.getClass().getName());
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

