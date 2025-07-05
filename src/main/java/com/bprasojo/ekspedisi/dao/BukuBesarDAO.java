/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bprasojo.ekspedisi.dao;

import com.bprasojo.ekspedisi.model.Perkiraan;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author USER
 */
public class BukuBesarDAO extends ParentDAO{
    public BukuBesarDAO() {
        super();
    }
    
    public List<Map<String, Object>> getDataBukuBesar(Integer tahun, Integer bulan, Perkiraan perkiraan) {
        List<Map<String, Object>> resultList = new ArrayList<>();

        String sql = "select a.no_jurnal, a.tanggal, a.transaksi,  a.keterangan, b.debet, b.kredit " 
                    + " from jurnal a "
                    + " inner join jurnal_detail b on a.id = b.jurnal_id " 
                    + " inner join perkiraan c on b.perkiraan_id = c.id " 
                    + " inner join transaksi_kas d on a.no_jurnal = d.no_bukti " 
                    + " where year(a.tanggal) = ? " 
                    + " and month(a.tanggal)= ? " 
                    + " and b.perkiraan_id = ? "
                    + " order by a.tanggal, a.no_jurnal"; 

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Set date parameters
            stmt.setInt(1, tahun);
            stmt.setInt(2, bulan);
            stmt.setInt(3, 83);
            
            // Eksekusi query
            try (ResultSet rs = stmt.executeQuery()) {
                // Mendapatkan metadata kolom
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                // Proses setiap row hasil query
                while (rs.next()) {
                    Map<String, Object> rowMap = new LinkedHashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnLabel(i); // Nama kolom
                        Object columnValue = rs.getObject(i); // Nilai kolom
                        rowMap.put(columnName, columnValue); // Menyimpan dalam Map
                    }
                    resultList.add(rowMap); // Menambahkan baris ke dalam list
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Sesuaikan dengan penanganan error Anda
        }

        return resultList;
    }
    
}
