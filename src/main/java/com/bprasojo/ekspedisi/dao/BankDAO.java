package com.bprasojo.ekspedisi.dao;

import com.bprasojo.ekspedisi.database.DBUtils;
import com.bprasojo.ekspedisi.database.DatabaseConnection;
import com.bprasojo.ekspedisi.model.Bank;
import com.bprasojo.ekspedisi.model.Perkiraan;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data Access Object untuk Bank
 */
public class BankDAO {
    private Connection conn;

    // Konstruktor
    public BankDAO() {
        try {
            this.conn = DatabaseConnection.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(ArmadaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Tambah Bank
    public void addBank(Bank bank) throws SQLException {
        String query = "INSERT INTO bank (no_rekening, nama_bank, atas_nama, keterangan, akun_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, bank.getNoRekening());
            stmt.setString(2, bank.getNamaBank());
            stmt.setString(3, bank.getAtasNama());
            stmt.setString(4, bank.getKeterangan());
            stmt.setInt(5, bank.getAkun().getId());
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    bank.setId(rs.getInt(1));
                }
            }
        }
    }

    private Bank getBank(String column, Object value) throws SQLException {
        String query = "SELECT * FROM bank WHERE " + column + " = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setObject(1, value);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                PerkiraanDAO perkiraanDao = new PerkiraanDAO();
                Perkiraan akun = perkiraanDao.getPerkiraanById(rs.getInt("akun_id"));
                return new Bank(
                        rs.getString("no_rekening"),
                        rs.getString("nama_bank"),
                        rs.getString("atas_nama"),
                        rs.getString("keterangan"),
                        rs.getInt("id"),
                        akun
                );
            }
        }
        return null;
    }
    
    // Ambil Bank berdasarkan No Rekening
    public Bank getBankByNoRekening(String noRekening) throws SQLException {
        return getBank("no_Rekening", noRekening);
    }
    
    public Bank getBankById(Integer id) throws SQLException {
        return getBank("id", id);
    }

    // Ambil semua Bank
    public List<Bank> getAllBank() throws SQLException {
        List<Bank> bankList = new ArrayList<>();
        String query = "SELECT * FROM bank a";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                PerkiraanDAO perkiraanDao = new PerkiraanDAO();
                Perkiraan akun = perkiraanDao.getPerkiraanById(rs.getInt("akun_id"));
                bankList.add(new Bank(
                        rs.getString("no_rekening"),
                        rs.getString("nama_bank"),
                        rs.getString("atas_nama"),
                        rs.getString("keterangan"),
                        rs.getInt("id"),
                        akun
                ));
            }
        }
        return bankList;
    }

    // Update Bank
    public void updateBank(Bank bank) throws SQLException {
        String query = "UPDATE bank SET nama_bank = ?, atas_nama = ?, keterangan = ? , no_rekening = ? where id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, bank.getNamaBank());
            stmt.setString(2, bank.getAtasNama());
            stmt.setString(3, bank.getKeterangan());
            stmt.setString(4, bank.getNoRekening());
            stmt.setInt(5, bank.getId());
            stmt.executeUpdate();
        }
    }

    // Hapus Bank berdasarkan No Rekening
    public void deleteBank(String noRekening) throws SQLException {
        String query = "DELETE FROM bank WHERE no_rekening = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, noRekening);
            stmt.executeUpdate();
        }
    }
    
    
    public Integer getSaldoAkhir(int bank_id, java.util.Date tanggal) {
        int saldoAkhir = 0;
        Date tanggal_acuan = new Date(2000,1,1);

        // Konversi java.util.Date ke java.sql.Date
        java.sql.Date sqlTanggal = new java.sql.Date(tanggal.getTime());
        

        // Query pertama untuk mendapatkan saldo akhir dan tanggal acuan
        String sql = "select a.saldo_akhir, a.tanggal "
                   + " from closing_bank a " 
                   + " where a.bank_id = ? "
                   + " and date(a.tanggal) <= ? "
                   + " order by tanggal desc limit 1"; 

        Object[] params = {bank_id, sqlTanggal};

        try {
            ResultSet rs = DBUtils.openQuery(conn, sql, params);
            while (rs.next()) {
                saldoAkhir = rs.getInt("saldo_akhir");
                tanggal_acuan = rs.getDate("tanggal");
            }
        } catch (SQLException ex) {
            Logger.getLogger(BankDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Query kedua untuk menghitung saldo akhir berdasarkan transaksi
        sql = "select sum(a.kredit - a.debet) as saldo_akhir"
                + " from v_mutasi_bank a "
                + " where a.bank_id = ? "
                + " and date(a.tanggal) > ?"
                + " and date(a.tanggal) <= ?";

        params = new Object[] {bank_id, tanggal_acuan, sqlTanggal};

        try {
            ResultSet rs = DBUtils.openQuery(conn, sql, params);
            while (rs.next()) {
                saldoAkhir = saldoAkhir + rs.getInt("saldo_akhir");
            }
        } catch (SQLException ex) {
            Logger.getLogger(BankDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return saldoAkhir;
    }

}
