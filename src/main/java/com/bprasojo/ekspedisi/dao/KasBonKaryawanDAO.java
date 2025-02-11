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
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KasBonKaryawanDAO {
    private Connection conn;

    public KasBonKaryawanDAO() {
        try {
            this.conn = DatabaseConnection.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(KasBonKaryawanDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void save(KasBonKaryawan kasBonKaryawan) throws SQLException {
        if (kasBonKaryawan.getId() <= 0) {
            String sql = "INSERT INTO kas_bon_karyawan (tanggal, nama_karyawan, perkiraan_pinjaman_id, perkiraan_kas_id, nominal, keterangan, bank_id, sumber_dana) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setDate(1, kasBonKaryawan.getTanggal());
                stmt.setString(2, kasBonKaryawan.getNamaKaryawan());
                stmt.setInt(3, kasBonKaryawan.getPerkiraanPinjamanId());
                stmt.setInt(4, kasBonKaryawan.getPerkiraanKasId());
                stmt.setInt(5, kasBonKaryawan.getNominal());
                stmt.setString(6, kasBonKaryawan.getKeterangan());
                stmt.setInt(7, kasBonKaryawan.getBankId());
                stmt.setString(8, kasBonKaryawan.getSumberDana());
                stmt.executeUpdate();
                
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        kasBonKaryawan.setId(generatedKeys.getInt(1));
                    }
                }
            }
        } else {
            String sql = "UPDATE kas_bon_karyawan SET tanggal = ?, nama_karyawan = ?, perkiraan_pinjaman_id = ?, perkiraan_kas_id = ?, nominal = ?, keterangan = ?, bank_id = ?, sumber_dana = ? WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setDate(1, kasBonKaryawan.getTanggal());
                stmt.setString(2, kasBonKaryawan.getNamaKaryawan());
                stmt.setInt(3, kasBonKaryawan.getPerkiraanPinjamanId());
                stmt.setInt(4, kasBonKaryawan.getPerkiraanKasId());
                stmt.setInt(5, kasBonKaryawan.getNominal());
                stmt.setString(6, kasBonKaryawan.getKeterangan());
                stmt.setInt(7, kasBonKaryawan.getBankId());
                stmt.setString(8, kasBonKaryawan.getSumberDana());
                stmt.setInt(9, kasBonKaryawan.getId());
                stmt.executeUpdate();
            }
        }
    }

    public KasBonKaryawan getById(int id) throws SQLException {
        String sql = "SELECT * FROM kas_bon_karyawan WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new KasBonKaryawan(
                        rs.getInt("id"),
                        rs.getDate("tanggal"),
                        rs.getString("nama_karyawan"),
                        rs.getInt("perkiraan_pinjaman_id"),
                        rs.getInt("perkiraan_kas_id"),
                        rs.getInt("nominal"),
                        rs.getString("keterangan"),
                        rs.getInt("bank_id"),
                        rs.getString("sumber_dana")
                    );
                }
            }
        }
        return null;
    }

    public List<KasBonKaryawan> getAll() throws SQLException {
        List<KasBonKaryawan> list = new ArrayList<>();
        String sql = "SELECT * FROM kas_bon_karyawan";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new KasBonKaryawan(
                    rs.getInt("id"),
                    rs.getDate("tanggal"),
                    rs.getString("nama_karyawan"),
                    rs.getInt("perkiraan_pinjaman_id"),
                    rs.getInt("perkiraan_kas_id"),
                    rs.getInt("nominal"),
                    rs.getString("keterangan"),
                    rs.getInt("bank_id"),
                    rs.getString("sumber_dana")
                ));
            }
        }
        return list;
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM kas_bon_karyawan WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}

