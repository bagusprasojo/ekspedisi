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
import java.util.Date;

/**
 *
 * @author USER
 */
public class ParentDAO {
    protected Connection conn;
    protected String _nama_table_;
    
    public ParentDAO(){
        try {
            this.conn = DatabaseConnection.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(ParentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    
    public Date getLastClosingDate() throws SQLException {
        String sql = "SELECT MAX(tanggal) AS last_closing FROM closing";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                Timestamp timestamp = rs.getTimestamp("last_closing");
                
                return timestamp != null ? new Date(timestamp.getTime()) : null;
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
    
}
