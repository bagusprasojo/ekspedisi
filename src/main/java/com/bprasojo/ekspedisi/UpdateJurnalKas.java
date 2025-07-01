/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bprasojo.ekspedisi;

import com.bprasojo.ekspedisi.dao.ParentDAO;
import com.bprasojo.ekspedisi.dao.TransaksiKasDAO;
import com.bprasojo.ekspedisi.database.DatabaseConnection;
import com.bprasojo.ekspedisi.model.TransaksiKas;
import java.awt.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author USER
 */
public class UpdateJurnalKas extends ParentDAO{
    
    public UpdateJurnalKas() {
        super();
    }
    
    public List getNoTransaksiKas(int tahun, int bulan) throws SQLException{
        List list = new List();
        
        String sql = "select * from transaksi_kas order by id ";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) { // Menggunakan while untuk insert lebih dari satu bank_id
                    list.add(rs.getString("no_bukti"));
                }
            }
        }
        
        return list;
    }
    
    public static void main(String args[]) {
        try {
            UpdateJurnalKas update = new UpdateJurnalKas();
            List list = update.getNoTransaksiKas(2025, 5);
            
            TransaksiKasDAO tkdao = new TransaksiKasDAO();
            
            for (int i = 0; i < list.countItems(); i++) {
                String noBukti = list.getItem(i);
                
                System.out.println("Proses : " + noBukti);
                TransaksiKas tkas = tkdao.getByNoBukti(noBukti);
                
                tkdao.saveJurnal(tkas);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(UpdateJurnalKas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
