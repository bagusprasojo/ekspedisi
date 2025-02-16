package com.bprasojo.ekspedisi.dao;

import com.bprasojo.ekspedisi.database.DatabaseConnection;
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

    public List<JurnalDetail> getByJurnalId(int jurnalId) throws SQLException {
        List<JurnalDetail> list = new ArrayList<>();
        String sql = "SELECT * FROM JurnalDetail WHERE jurnal_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, jurnalId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(new JurnalDetail(
                        rs.getInt("id"),
                        rs.getInt("jurnal_id"),
                        rs.getInt("perkiraan_id"),
                        rs.getDouble("debet"),
                        rs.getDouble("kredit")
                    ));
                }
            }
        }
        return list;
    }
}
