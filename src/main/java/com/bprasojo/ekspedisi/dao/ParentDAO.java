/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bprasojo.ekspedisi.dao;

import com.bprasojo.ekspedisi.database.DatabaseConnection;
import com.bprasojo.ekspedisi.utils.AppUtils;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 *
 */
public class ParentDAO {
    protected Connection conn;
    protected String _nama_table_;
//    protected ConfigDAO configDAO;
//    protected JurnalDAO jurnalDAO;
    
    public ParentDAO(){
        try {
            this.conn = DatabaseConnection.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(ParentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
//        configDAO = new ConfigDAO();
//        jurnalDAO = new JurnalDAO();
    } 
    
    public Connection getConnection(){
        return conn;
    }
    
    public int getAllDataCount(){
        int count = 0;
        String sql = "SELECT count(id) as count FROM " + _nama_table_;        

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // Menyimpan semua id yang ditemukan ke dalam list
            while (rs.next()) {
                count = rs.getInt("count");
            }

            

        } catch (SQLException ex) {
            AppUtils.showErrorDialog("Gagal get random ID\n" + ex.getMessage());
        }

        return count;
    
    }
    
    public int getAllDataCountPeriod(Date tgl1, Date tgl2) {
        int count = 0;
        String sql = "SELECT count(id) as count FROM " + _nama_table_ 
                     + " WHERE date(tanggal) BETWEEN ? AND ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, new java.sql.Date(tgl1.getTime()));
            stmt.setDate(2, new java.sql.Date(tgl2.getTime()));

            try (ResultSet rs = stmt.executeQuery()) {
                // Menyimpan count yang ditemukan
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            }
        } catch (SQLException ex) {
            AppUtils.showErrorDialog("Gagal getAllDataCountPeriod\n" + ex.getMessage());
        }

        return count;
    }

    public int getRandomIDSudahClosing() throws SQLException {
        int id = 0;
        Date tglClosing = getLastClosingDate();
        String sql = "SELECT id FROM " + _nama_table_ + " where tanggal <= ?";
        List<Integer> ids = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, new java.sql.Date(tglClosing.getTime()));
        
             try (ResultSet rs = stmt.executeQuery()) {
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

            

        } catch (SQLException ex) {
            AppUtils.showErrorDialog("Gagal get random ID\n" + ex.getMessage());
        }

        return id;
    }
    
    public int getRandomID() {
        int id = 0;
        String sql = "SELECT id FROM " + _nama_table_;
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

        } catch (SQLException ex) {
            AppUtils.showErrorDialog("Gagal get random ID\n" + ex.getMessage());
        }

        return id;
    }
    
    public int getRandomIDPeriod(Date tgl1, Date tgl2) {
        int id = 0;
        String sql = "SELECT id FROM " + _nama_table_ + " where date(tanggal) between ? and ?";
        List<Integer> ids = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, new java.sql.Date(tgl1.getTime()));
            stmt.setDate(2, new java.sql.Date(tgl2.getTime()));
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ids.add(rs.getInt("id"));
                }
            }

            if (!ids.isEmpty()) {
                Random rand = new Random();
                id = ids.get(rand.nextInt(ids.size()));
            }

        } catch (SQLException ex) {
            AppUtils.showErrorDialog("Gagal get random ID\n" + ex.getMessage());
        }

        return id;
    }
    
    public Date getLastClosingDate() throws SQLException {
        String sql = "SELECT MAX(tanggal) AS last_closing FROM closing";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                Timestamp timestamp = rs.getTimestamp("last_closing");
                
                Calendar cal = Calendar.getInstance();
                cal.set(2024, 10, 30);
                return timestamp != null ? new Date(timestamp.getTime()) : cal.getTime();
            }
        }
        
        return null;
    }
    
    public Date getOldTransDate(int id) throws SQLException {        
        if (_nama_table_ == null){
            throw new SQLException("_nama_table_ belum diset");
        }
        
        String sql = "SELECT tanggal FROM " + _nama_table_ + " WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Timestamp timestamp = rs.getTimestamp("tanggal");
                    return timestamp != null ? new Date(timestamp.getTime()) : null;
                }
            }
        }
        return null; // Mengembalikan null jika tidak ada data yang ditemukan
    }
    
    public boolean validasiClosing(int id, Date tglTransaksi) throws SQLException{
        Date lastClosingDate = getLastClosingDate();
        
        int dateClsoingInt = AppUtils.dateToInt(lastClosingDate);
        int dateTransInt  = AppUtils.dateToInt(tglTransaksi);
                
        if (dateTransInt <= dateClsoingInt){
            return false;
        }
        
        if (id > 0){
            Date oldTransDate = getOldTransDate(id);
            int oldDateTransInt  = AppUtils.dateToInt(oldTransDate);
            
            if (oldDateTransInt <= dateClsoingInt){
               return false;
            }
        }
        
        return true;
    }
    
    public boolean isSudahJurnal(String noBukti) throws SQLException{
        if (noBukti.equals("")){
            return true;
        }
        
        String sql = "select count(1) as jml from jurnal where no_jurnal = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, noBukti);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    if (rs.getInt("jml") > 0){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
}
