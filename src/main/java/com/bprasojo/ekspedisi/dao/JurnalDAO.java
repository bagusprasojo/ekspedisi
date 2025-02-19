package com.bprasojo.ekspedisi.dao;

import com.bprasojo.ekspedisi.database.DatabaseConnection;
import com.bprasojo.ekspedisi.model.Jurnal;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JurnalDAO {
    private Connection conn;

    public JurnalDAO() {
        try {
            this.conn = DatabaseConnection.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(JurnalDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String generateNoJurnal(java.util.Date inputDate) {
        // Format tanggal input menjadi "yyyyMM"
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        String yearMonth = sdf.format(inputDate);

        // Query untuk mencari nomor bukti tertinggi yang memiliki awalan "BON-" + tahun + bulan yang sama
        String sql = "SELECT MAX(SUBSTRING(no_jurnal, 11)) AS last_number " +
                     "FROM jurnal " +
                     "WHERE no_jurnal LIKE 'JUR-" + yearMonth + "%'";

        try (Statement stmt = conn.createStatement(); 
            ResultSet rs = stmt.executeQuery(sql)) {
            // Inisialisasi nomor urut
            int lastNumber = 0;
            
            // Jika ada data terakhir, ambil nomor urut terakhir
            if (rs.next()) {
                String lastNumberStr = rs.getString("last_number");
                if (lastNumberStr != null) {
                    lastNumber = Integer.parseInt(lastNumberStr);
                }
            }
            
            // Increment nomor urut terakhir
            lastNumber++;

            // Format nomor bukti baru
            String noBuktiBaru = "JUR-" + yearMonth + String.format("%04d", lastNumber);

            // Kembalikan nomor bukti baru
            return noBuktiBaru;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public void save(Jurnal jurnal) throws SQLException {
        String sql;
        boolean isInsert = jurnal.getId() == 0;

        if (isInsert) {
            String no_jurnal = generateNoJurnal(jurnal.getTanggal());
            jurnal.setNoJurnal(no_jurnal);
            
            sql = "INSERT INTO jurnal (no_jurnal, tanggal, id_transaksi, transaksi, user) VALUES (?, ?, ?, ?, ?)";
        } else {
            sql = "UPDATE jurnal SET no_jurnal = ?, tanggal = ?, id_transaksi = ?, transaksi = ?, user = ? WHERE id = ?";
        }

        try (PreparedStatement statement = conn.prepareStatement(sql, isInsert ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS)) {
            statement.setString(1, jurnal.getNoJurnal());
            statement.setDate(2, new java.sql.Date(jurnal.getTanggal().getTime()));
            statement.setInt(3, jurnal.getIdTransaksi());
            statement.setString(4, jurnal.getTransaksi());
            statement.setString(5, jurnal.getUser());

            if (!isInsert) {
                statement.setInt(6, jurnal.getId()); // ID hanya diperlukan saat UPDATE
            }

            statement.executeUpdate();

            // Jika INSERT, ambil ID yang dihasilkan
            if (isInsert) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        jurnal.setId(generatedKeys.getInt(1));
                    }
                }
            }
        }
    }


    public Jurnal getById(int id) throws SQLException {
        String sql = "SELECT * FROM Jurnal WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Jurnal(
                        rs.getInt("id"),
                        rs.getString("no_jurnal"),
                        rs.getDate("tanggal"),
                        rs.getInt("id_transaksi"),
                        rs.getString("transaksi"),
                        rs.getString("user")
                    );
                }
            }
        }
        return null;
    }

    public List<Jurnal> getAll() throws SQLException {
        List<Jurnal> list = new ArrayList<>();
        String sql = "SELECT * FROM Jurnal";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(new Jurnal(
                    rs.getInt("id"),
                    rs.getString("no_jurnal"),
                    rs.getDate("tanggal"),
                    rs.getInt("id_transaksi"),
                    rs.getString("transaksi"),
                    rs.getString("user")
                ));
            }
        }
        return list;
    }
}
