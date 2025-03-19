package com.bprasojo.ekspedisi.dao;

import com.bprasojo.ekspedisi.database.DatabaseConnection;
//import com.bprasojo.ekspedisi.model.Jurnal;
import com.bprasojo.ekspedisi.model.JurnalDetail;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JurnalDetailDAO {
    private Connection conn;

    public JurnalDetailDAO() {
        try {
            this.conn = DatabaseConnection.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(JurnalDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void save(int jurnalId, JurnalDetail jurnalDetail) throws SQLException {
        String sql = "INSERT INTO jurnal_detail (jurnal_id, perkiraan_id, debet, kredit) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement statement = conn.prepareStatement(sql, Statement.NO_GENERATED_KEYS)) {
            statement.setInt(1, jurnalId);
            statement.setInt(2, jurnalDetail.getPerkiraanId());
            statement.setInt(3, jurnalDetail.getDebet());
            statement.setInt(4, jurnalDetail.getKredit());
            
            statement.executeUpdate();
        }
    }
    public List<JurnalDetail> getByJurnalId(int jurnalId) throws SQLException {
        List<JurnalDetail> list = new ArrayList<>();
        String sql = "SELECT * FROM Jurnal_Detail WHERE jurnal_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, jurnalId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(new JurnalDetail(
                        rs.getInt("id"),
//                        rs.getInt("jurnal_id"),
                        rs.getInt("perkiraan_id"),
                        rs.getInt("debet"),
                        rs.getInt("kredit")
                    ));
                    
                    list.getLast().setJurnalId(jurnalId);
                }
            }
        }
        return list;
    }
}
