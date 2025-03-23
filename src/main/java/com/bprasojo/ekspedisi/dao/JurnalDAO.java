package com.bprasojo.ekspedisi.dao;

import com.bprasojo.ekspedisi.model.Jurnal;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class JurnalDAO extends ParentDAO{
    public JurnalDAO() {
        super();
        _nama_table_ = "jurnal";
    }

    public String generateNoJurnal(java.util.Date inputDate) {
        // Format tanggal input menjadi "yyyyMM"
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        String yearMonth = sdf.format(inputDate);

        // Query untuk mencari nomor bukti tertinggi yang memiliki awalan "BON-" + tahun + bulan yang sama
        String sql = "SELECT MAX(SUBSTRING(no_jurnal, 11)) AS last_number " +
                     "FROM " + _nama_table_ +
                     "WHERE no_jurnal LIKE 'JUR-" + yearMonth + "%'";

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
            String noBuktiBaru = "JUR-" + yearMonth + String.format("%04d", lastNumber);

            // Kembalikan nomor bukti baru
            return noBuktiBaru;
            
        } catch (SQLException e) {
            return null;
        }
    }
    
    private void deleteJurnalDetail(int jurnalId) throws SQLException{
        String sql = "DELETE FROM jurnal_detail WHERE jurnal_id = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, jurnalId);
            statement.executeUpdate();
        }
    }
    public void delete(int id) throws SQLException {
        Jurnal jur = getById(id);
        
        if (jur != null){
            if (!validasiClosing(id, jur.getTanggal())){
                throw new SQLException("Transaksi tidak bisa dihapus karena sudah closing");
            }

            String sql = "DELETE FROM " + _nama_table_ + " WHERE id = ?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setInt(1, id);
                statement.executeUpdate();
            }
            
            deleteJurnalDetail(jur.getId());
        }
    }
    
    public void deleteByTransId(int transId, String transaksi) throws SQLException {
        Jurnal jur = getByTransaksiId(transId, transaksi);
        
        if (jur != null){
            delete(jur.getId());
        }
    }
    
    public void save(Jurnal jurnal) throws SQLException {
        if (!validasiClosing(jurnal.getId(), jurnal.getTanggal())){
            throw new SQLException("Jurnal tidak bisa disimpan karena sudah closing");
        }
        
        String sql;
        boolean isInsert = jurnal.getId() == 0;

        if (isInsert ) {            
            sql = "INSERT INTO " + _nama_table_ + " (no_jurnal, tanggal, transaksi_id, transaksi, keterangan,  user_create) VALUES (?, ?, ?, ?, ?, ?)";
        } else {
            sql = "UPDATE " + _nama_table_ + " SET no_jurnal = ?, tanggal = ?, transaksi_id = ?, transaksi = ?, keterangan = ?, user_update = ? WHERE id = ?";
        }

        if (isInsert && jurnal.getNoJurnal().equals("")){
            String no_jurnal = generateNoJurnal(jurnal.getTanggal());
            jurnal.setNoJurnal(no_jurnal);            
        }
        
        try (PreparedStatement statement = conn.prepareStatement(sql, isInsert ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS)) {
            statement.setString(1, jurnal.getNoJurnal());
            statement.setDate(2, new java.sql.Date(jurnal.getTanggal().getTime()));
            statement.setInt(3, jurnal.getTransaksiId());
            statement.setString(4, jurnal.getTransaksi());
            statement.setString(5, jurnal.getKeterangan());
            
            if (isInsert){
                statement.setString(6, jurnal.getUserCreate());
            } else {
                statement.setString(7, jurnal.getUserUpdate());
                statement.setInt(8, jurnal.getId()); 
            }

            statement.executeUpdate();

            // Jika INSERT, ambil ID yang dihasilkan
            if (isInsert) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        jurnal.setId(generatedKeys.getInt(1));
                    }
                }
            }
        }
        
        deleteJurnalDetail(jurnal.getId());
        JurnalDetailDAO jdDAO = new JurnalDetailDAO();
        for (int i = 0; i < jurnal.getJurnalDetails().size(); i++) {
            jdDAO.save(jurnal.getId(), jurnal.getJurnalDetails().get(i));
        }
        
    }


    private Jurnal getJurnalByQuery(String sql, Object... params) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Set parameters dynamically based on the provided params
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Jurnal(
                        rs.getInt("id"),
                        rs.getString("no_jurnal"),
                        rs.getDate("tanggal"),
                        rs.getInt("transaksi_id"),
                        rs.getString("transaksi"),
                        rs.getString("keterangan"),
                        rs.getString("user_create"),
                        rs.getString("user_update")
                    );
                }
            }
        }
        return null;
    }

    public Jurnal getById(int id) throws SQLException {
        String sql = "SELECT * FROM Jurnal WHERE id = ?";
        return getJurnalByQuery(sql, id);
    }

    public Jurnal getByTransaksiId(int transaksiId, String transaksi) throws SQLException {
        String sql = "SELECT * FROM Jurnal WHERE transaksi_id = ? and transaksi = ?";
        return getJurnalByQuery(sql, transaksiId, transaksi);
    }

    public Jurnal getByNoJurnal(String noJurnal) throws SQLException {
        String sql = "SELECT * FROM Jurnal WHERE no_jurnal = ?";
        return getJurnalByQuery(sql, noJurnal);
    }

    
    

    public List<Jurnal> getAll() throws SQLException {
        List<Jurnal> list = new ArrayList<>();
        String sql = "SELECT * FROM Jurnal";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(new Jurnal(
                    rs.getInt("id"),
                    rs.getString("no_jurnal"),
                    rs.getDate("tanggal"),
                    rs.getInt("transaksi_id"),
                    rs.getString("transaksi"),
                    rs.getString("keterangan"),
                    rs.getString("user_create"),
                    rs.getString("user_update")
                ));
            }
        }
        return list;
    }
}
