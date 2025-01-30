package com.bprasojo.ekspedisi.dao;

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
        String query = "INSERT INTO bank (no_rekening, nama_bank, atas_nama, keterangan) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, bank.getNoRekening());
            stmt.setString(2, bank.getNamaBank());
            stmt.setString(3, bank.getAtasNama());
            stmt.setString(4, bank.getKeterangan());
            stmt.setInt(5, bank.getAkun().getId());
            stmt.executeUpdate();
        }
    }

    // Ambil Bank berdasarkan No Rekening
    public Bank getBankByNoRekening(String noRekening) throws SQLException {
        String query = "SELECT * FROM bank WHERE no_rekening = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, noRekening);
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
        String query = "UPDATE bank SET nama_bank = ?, atas_nama = ?, keterangan = ? WHERE no_rekening = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, bank.getNamaBank());
            stmt.setString(2, bank.getAtasNama());
            stmt.setString(3, bank.getKeterangan());
            stmt.setString(4, bank.getNoRekening());
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
}
