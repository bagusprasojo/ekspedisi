/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bprasojo.ekspedisi.dao;

/**
 *
 */
import com.bprasojo.ekspedisi.model.Perkiraan;
import com.bprasojo.ekspedisi.utils.AppUtils;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PerkiraanDAO extends ParentDAO{
    
    public PerkiraanDAO() {
        super();
        _nama_table_ = "perkiraan";
    }

    public Perkiraan getRandomPerkiraan(){
        try {
            return getById(getRandomID());
        } catch (SQLException ex) {
            AppUtils.showErrorDialog(ex.getMessage());
        }
        
        return null;
    }
    // Menambah Perkiraan
    public void addPerkiraan(Perkiraan perkiraan) throws SQLException {
        String query = "INSERT INTO perkiraan (kode, nama, parent_id, golongan, kelompok, level, saldo_normal) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, perkiraan.getKode());
            stmt.setString(2, perkiraan.getNama());
            stmt.setInt(3, perkiraan.getParent_Id());
            stmt.setString(4, perkiraan.getGolongan());
            stmt.setString(5, perkiraan.getKelompok());
            stmt.setInt(6, perkiraan.getLevel());
            stmt.setString(7, perkiraan.getSaldo_normal());
            stmt.executeUpdate();
        }
    }

    private Perkiraan getPerkiraan(String column, Object value) throws SQLException {
        String query = "SELECT * FROM perkiraan WHERE " + column + " = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setObject(1, value);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Perkiraan(
                        rs.getString("kode"),
                        rs.getString("nama"),
                        rs.getInt("parent_id"),
                        rs.getString("golongan"),
                        rs.getString("kelompok"),
                        rs.getInt("level"),
                        rs.getString("saldo_normal"),
                        rs.getInt("id")
                );
            }
        }
        return null;
    }
    
    @Override
    public int getRandomID() throws SQLException {
        int id = 0;
        String sql = "SELECT a.id FROM " + _nama_table_ + " a "
                     + " left join perkiraan b on a.id = b.parent_id "
                     + " where b.id is null";
        
        List<Integer> ids = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // Menyimpan semua id yang ditemukan ke dalam list
            while (rs.next()) {
                ids.add(rs.getInt("id"));
            }

            // Memilih id secara acak dari list jika ada id yang ditemukan
            if (!ids.isEmpty()) {
                Random rand = new Random();
                id = ids.get(rand.nextInt(ids.size()));
            }

        }

        return id;
    }
    
    public boolean isPunyaAnak(String kodeAkun) throws SQLException {
        String query = "SELECT count(1) as jml FROM perkiraan a "
                       + " inner join perkiraan b on a.id = b.parent_id "
                       + " WHERE a.kode = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, kodeAkun);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                if (rs.getInt("jml") > 0){
                    return true;
                }
            }
        }
        
        return false;
    }

    // Mengambil Perkiraan berdasarkan kode
    public Perkiraan getPerkiraanByKode(String kode) throws SQLException {
        return getPerkiraan("kode", kode);
    }

    // Mengambil Perkiraan berdasarkan ID
    public Perkiraan getById(Integer id) throws SQLException {
        return getPerkiraan("id", id);
    }

    public Perkiraan getPerkiraanKas() throws SQLException{
        return getPerkiraan("kode", "1010100");
    }

    public List<Perkiraan> getAllPerkiraanPinjaman() throws SQLException {
        List<Perkiraan> perkiraans = new ArrayList<>();
        String query = "SELECT * FROM perkiraan where kode in ('1030100','1030200')";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                perkiraans.add(new Perkiraan(
                        rs.getString("kode"),
                        rs.getString("nama"),
                        rs.getInt("parent_id"),
                        rs.getString("golongan"),
                        rs.getString("kelompok"),
                        rs.getInt("level"),
                        rs.getString("saldo_normal"),
                        rs.getInt("id")
                ));
            }
        }
        return perkiraans;
    }
    
    public List<Perkiraan> getAllPerkiraanKas() throws SQLException {
        List<Perkiraan> perkiraans = new ArrayList<>();
        String query = "SELECT * FROM perkiraan where nama = 'KAS'";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                perkiraans.add(new Perkiraan(
                        rs.getString("kode"),
                        rs.getString("nama"),
                        rs.getInt("parent_id"),
                        rs.getString("golongan"),
                        rs.getString("kelompok"),
                        rs.getInt("level"),
                        rs.getString("saldo_normal"),
                        rs.getInt("id")
                ));
            }
        }
        return perkiraans;
    }
    // Mengambil seluruh data Perkiraan
    public List<Perkiraan> getAllPerkiraan() throws SQLException {
        List<Perkiraan> perkiraans = new ArrayList<>();
        String query = "SELECT * FROM perkiraan";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                perkiraans.add(new Perkiraan(
                        rs.getString("kode"),
                        rs.getString("nama"),
                        rs.getInt("parent_id"),
                        rs.getString("golongan"),
                        rs.getString("kelompok"),
                        rs.getInt("level"),
                        rs.getString("saldo_normal"),
                        rs.getInt("id")
                ));
            }
        }
        return perkiraans;
    }

    // Memperbarui Perkiraan
    public void updatePerkiraan(Perkiraan perkiraan) throws SQLException {
        String query = "UPDATE perkiraan SET nama = ?, parent_id = ?, golongan = ?, kelompok = ?, level = ?, saldo_normal= ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, perkiraan.getNama());
            stmt.setInt(2, perkiraan.getParent_Id());
            stmt.setString(3, perkiraan.getGolongan());
            stmt.setString(4, perkiraan.getKelompok());
            stmt.setInt(5, perkiraan.getLevel());
            stmt.setString(6, perkiraan.getSaldo_normal());
            stmt.setInt(7, perkiraan.getId());
            stmt.executeUpdate();
        }
    }

    // Menghapus Perkiraan berdasarkan kode
    public void deletePerkiraanByKode(String kode) throws SQLException {
        String query = "DELETE FROM perkiraan WHERE kode = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, kode);
            stmt.executeUpdate();
        }
    }
    
    // Menghapus Perkiraan berdasarkan kode
    public void deletePerkiraan(Integer id) throws SQLException {
        String query = "DELETE FROM perkiraan WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}

