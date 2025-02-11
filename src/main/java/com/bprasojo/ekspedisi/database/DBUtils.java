/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bprasojo.ekspedisi.database;

import java.sql.*;

/**
 *
 * @author USER
 */
public class DBUtils {
    public static ResultSet openQuery(Connection conn, String sql, Object[] params) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(sql);
        
        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);  // Menambahkan parameter ke PreparedStatement
        }
        
        return stmt.executeQuery();
    }
    
}
