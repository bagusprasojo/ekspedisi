/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bprasojo.ekspedisi.dao;

/**
 *
 */
import com.bprasojo.ekspedisi.database.DatabaseConnection;
import com.bprasojo.ekspedisi.model.JenisTransaksi;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JenisTransaksiDAO {
    private Connection conn;

    public JenisTransaksiDAO() {
        try {
            this.conn = DatabaseConnection.getConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Simpan atau update JenisTransaksi
    public void save(JenisTransaksi jenis) throws SQLException {
        String sql;
        boolean isInsert = jenis.getId() == 0;

        if (isInsert) {
            sql = "INSERT INTO jenis_transaksi (kode, nama, akun_id) VALUES (?, ?, ?)";
        } else {
            sql = "UPDATE jenis_transaksi SET kode = ?, nama = ?, akun_id = ? WHERE id = ?";
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql, isInsert ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS)) {
            stmt.setString(1, jenis.getKode());
            stmt.setString(2, jenis.getNama());
            stmt.setInt(3, jenis.getAkunId());

            if (!isInsert) {
                stmt.setInt(4, jenis.getId());
            }

            stmt.executeUpdate();

            if (isInsert) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        jenis.setId(generatedKeys.getInt(1));
                    }
                }
            }
        }
    }

    // Ambil JenisTransaksi berdasarkan ID
    public JenisTransaksi getById(int id) throws SQLException {
        String sql = "SELECT * FROM jenis_transaksi WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new JenisTransaksi(
                    rs.getInt("id"),
                    rs.getString("kode"),
                    rs.getString("nama"),
                    rs.getInt("akun_id")
                );
            }
        }
        return null;
    }

    // Ambil semua JenisTransaksi
    public List<JenisTransaksi> getAll() throws SQLException {
        List<JenisTransaksi> list = new ArrayList<>();
        String sql = "SELECT * FROM jenis_transaksi";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(new JenisTransaksi(
                    rs.getInt("id"),
                    rs.getString("kode"),
                    rs.getString("nama"),
                    rs.getInt("akun_id")
                ));
            }
        }
        return list;
    }
}

