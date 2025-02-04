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
import com.bprasojo.ekspedisi.model.TransaksiBank;
import java.sql.*;

public class TransaksiBankDAO {
    private Connection conn;

    public TransaksiBankDAO() {
        try {
            this.conn = DatabaseConnection.getConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Menyimpan atau memperbarui transaksi bank
    public void save(TransaksiBank transaksi) throws SQLException {
        String sql;
        boolean isInsert = transaksi.getId() == 0;

        if (isInsert) {
            sql = "INSERT INTO transaksi_bank (tanggal, bank_utama_id, jenis_transaksi, debet, kredit, bank_tujuan_id, akun_utama_id, akun_tujuan_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        } else {
            sql = "UPDATE transaksi_bank SET tanggal = ?, bank_utama_id = ?, jenis_transaksi = ?, debet = ?, kredit = ?, bank_tujuan_id = ?, akun_utama_id = ?, akun_tujuan_id = ? WHERE id = ?";
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql, isInsert ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS)) {
            stmt.setDate(1, transaksi.getTanggal());
            stmt.setInt(2, transaksi.getBankUtamaId());
            
            if (transaksi.getJenisTransaksi() != null){
                stmt.setInt(3, transaksi.getJenisTransaksi().getId());
            } else {
                stmt.setNull(3, Types.INTEGER);
            }
            stmt.setDouble(4, transaksi.getDebet());
            stmt.setDouble(5, transaksi.getKredit());

            if (transaksi.getBankTujuanId() != null) {
                stmt.setInt(6, transaksi.getBankTujuanId());
            } else {
                stmt.setNull(6, Types.INTEGER);
            }

            stmt.setInt(7, transaksi.getAkunUtamaId());

            if (transaksi.getAkunTujuanId() != null) {
                stmt.setInt(8, transaksi.getAkunTujuanId());
            } else {
                stmt.setNull(8, Types.INTEGER);
            }

            if (!isInsert) {
                stmt.setInt(9, transaksi.getId());
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
    }

    // Mengambil transaksi berdasarkan ID
    public TransaksiBank getById(int id) throws SQLException {
        String sql = "SELECT * FROM transaksi_bank WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new TransaksiBank(
                    rs.getInt("id"),
                    rs.getDate("tanggal"),
                    rs.getInt("bank_utama_id"),
                    rs.getInt("jenis_transaksi_id"),                        
                    rs.getDouble("debet"),
                    rs.getDouble("kredit"),
                    rs.getInt("bank_tujuan_id"),
                    rs.getInt("akun_utama_id"),
                    rs.getInt("akun_tujuan_id")
                );
            }
        }
        return null;
    }
}

