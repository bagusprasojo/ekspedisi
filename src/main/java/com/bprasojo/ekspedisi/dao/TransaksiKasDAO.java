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
import com.bprasojo.ekspedisi.model.TransaksiKas;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TransaksiKasDAO {
    private Connection conn;

    // Constructor untuk inisialisasi koneksi database
    public TransaksiKasDAO() {
        try {
            this.conn = DatabaseConnection.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(ArmadaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Menyimpan data TransaksiKas ke database
    public void save(TransaksiKas transaksiKas) throws SQLException {
        String sql = "INSERT INTO transaksi_kas (akunKasId, akunTransaksiId, tanggal, nominalMasuk, nominalKeluar, keterangan, armadaId) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, transaksiKas.getAkunKasId());
            statement.setInt(2, transaksiKas.getAkunTransaksiId());
            statement.setDate(3, transaksiKas.getTanggal());
            statement.setInt(4, transaksiKas.getNominalMasuk());
            statement.setInt(5, transaksiKas.getNominalKeluar());
            statement.setString(6, transaksiKas.getKeterangan());
            statement.setInt(7, transaksiKas.getArmadaId());
            statement.executeUpdate();

            // Mengambil ID yang di-generate secara otomatis
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    transaksiKas.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    // Memperbarui data TransaksiKas
    public void update(TransaksiKas transaksiKas) throws SQLException {
        String sql = "UPDATE transaksi_kas SET akunKasId = ?, akunTransaksiId = ?, tanggal = ?, nominalMasuk = ?, nominalKeluar = ?, keterangan = ?, armadaId = ? WHERE id = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, transaksiKas.getAkunKasId());
            statement.setInt(2, transaksiKas.getAkunTransaksiId());
            statement.setDate(3, transaksiKas.getTanggal());
            statement.setInt(4, transaksiKas.getNominalMasuk());
            statement.setInt(5, transaksiKas.getNominalKeluar());
            statement.setString(6, transaksiKas.getKeterangan());
            statement.setInt(7, transaksiKas.getArmadaId());
            statement.setInt(8, transaksiKas.getId());
            statement.executeUpdate();
        }
    }

    // Menghapus data TransaksiKas
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM transaksi_kas WHERE id = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    // Mendapatkan satu data TransaksiKas berdasarkan ID
    public TransaksiKas getById(int id, PerkiraanDAO perkiraanDAO, ArmadaDAO armadaDAO) throws SQLException {
        String sql = "SELECT * FROM transaksi_kas WHERE id = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    TransaksiKas transaksiKas = new TransaksiKas();
                    transaksiKas.setId(resultSet.getInt("id"));
                    transaksiKas.setAkunKasId(resultSet.getInt("akunKasId"));
                    transaksiKas.setAkunTransaksiId(resultSet.getInt("akunTransaksiId"));
                    transaksiKas.setTanggal(resultSet.getDate("tanggal"));
                    transaksiKas.setNominalMasuk(resultSet.getInt("nominalMasuk"));
                    transaksiKas.setNominalKeluar(resultSet.getInt("nominalKeluar"));
                    transaksiKas.setKeterangan(resultSet.getString("keterangan"));
                    transaksiKas.setArmadaId(resultSet.getInt("armadaId"));
                    return transaksiKas;
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
                transaksiKas.setAkunKasId(resultSet.getInt("akunKasId"));
                transaksiKas.setAkunTransaksiId(resultSet.getInt("akunTransaksiId"));
                transaksiKas.setTanggal(resultSet.getDate("tanggal"));
                transaksiKas.setNominalMasuk(resultSet.getInt("nominalMasuk"));
                transaksiKas.setNominalKeluar(resultSet.getInt("nominalKeluar"));
                transaksiKas.setKeterangan(resultSet.getString("keterangan"));
                transaksiKas.setArmadaId(resultSet.getInt("armadaId"));
                transaksiKasList.add(transaksiKas);
            }
        }
        return transaksiKasList;
    }
}

