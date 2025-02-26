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
import com.bprasojo.ekspedisi.model.Closing;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClosingDAO {
    private Connection conn;

    public ClosingDAO() {
        try {
            this.conn = DatabaseConnection.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(ClosingDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Cek apakah tanggal adalah akhir bulan
    private boolean isLastDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        return cal.get(Calendar.DAY_OF_MONTH) == lastDay;
    }

    // Cek apakah tanggal baru adalah bulan berikutnya dari tanggal terakhir closing
    public boolean isNextMonth(Date lastClosing, Date newClosing) {
        Calendar lastCal = Calendar.getInstance();
        lastCal.setTime(lastClosing);

        Calendar newCal = Calendar.getInstance();
        newCal.setTime(newClosing);

        return (newCal.get(Calendar.YEAR) == lastCal.get(Calendar.YEAR) &&
                newCal.get(Calendar.MONTH) == lastCal.get(Calendar.MONTH) + 1) ||
               (newCal.get(Calendar.YEAR) == lastCal.get(Calendar.YEAR) + 1 &&
                lastCal.get(Calendar.MONTH) == Calendar.DECEMBER &&
                newCal.get(Calendar.MONTH) == Calendar.JANUARY);
    }

    // 🔹 Save (Insert/Update)
    public void save(Closing closing) throws SQLException {
        // 1️⃣ Ambil closing terakhir
        String lastClosingSql = "SELECT tanggal FROM closing ORDER BY tanggal DESC LIMIT 1";
        Date lastClosingDate = null;

        try (PreparedStatement stmt = conn.prepareStatement(lastClosingSql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                lastClosingDate = rs.getDate("tanggal");
            }
        }

        Date tanggal = new Date(closing.getTanggal().getTime());
        // 2️⃣ Cek apakah tanggal yang diinput adalah akhir bulan
        if (!isLastDayOfMonth(tanggal)) {
            throw new SQLException("Tanggal closing harus pada akhir bulan!");
        }

        // 3️⃣ Cek apakah bulan berurutan dengan closing terakhir
        if (lastClosingDate != null && !isNextMonth(lastClosingDate, tanggal)) {
            throw new SQLException("Closing harus dilakukan secara berurutan tiap bulan!");
        }

        // 4️⃣ Lakukan INSERT atau UPDATE jika valid
        String sql;
        if (closing.getId() == 0) {
            sql = "INSERT INTO closing (tanggal, keterangan) VALUES (?, ?)";
        } else {
            sql = "UPDATE closing SET tanggal = ?, keterangan = ? WHERE id = ?";
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setDate(1, tanggal);
            stmt.setString(2, closing.getKeterangan());

            if (closing.getId() != 0) {
                stmt.setInt(3, closing.getId());
            }

            int affectedRows = stmt.executeUpdate();

            if (closing.getId() == 0 && affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        closing.setId(generatedKeys.getInt(1));
                    }
                }
            }
        }
    }


    // 🔹 Get by ID
    public Closing getById(int id) throws SQLException {
        return getBy("id", String.valueOf(id));
    }

    // 🔹 Get by Tanggal
    public Closing getByTanggal(Date tanggal) throws SQLException {
        return getBy("tanggal", tanggal.toString());
    }

    // 🔹 Private method untuk menghindari kode duplikasi
    private Closing getBy(String field, String value) throws SQLException {
        String sql = "SELECT * FROM closing WHERE " + field + " = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, value);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Closing(
                        rs.getInt("id"),
                        rs.getDate("tanggal"),
                        rs.getString("keterangan")
                    );
                }
            }
        }
        return null;
    }

    public List<Map<String, Object>> getClosingByPage(Integer page, java.util.Date tglAwal, java.util.Date tglAkhir, String filter) throws SQLException {
        List<Map<String, Object>> resultList = new ArrayList<>();

        String sql = "select a.*" 
                     + " from closing a " 
                     + " WHERE a.tanggal BETWEEN ? AND ?"; 

        if (filter != null && !filter.trim().isEmpty()) {
            sql += " AND (a.keterangan like ?)";
        }

        sql += " order by a.tanggal desc , a.id desc LIMIT ? OFFSET ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Set date parameters
            stmt.setDate(1, new java.sql.Date(tglAwal.getTime()));
            stmt.setDate(2, new java.sql.Date(tglAkhir.getTime()));

            int paramIndex = 3;

            // Set filter parameters if present
            if (filter != null && !filter.trim().isEmpty()) {
                for (int i = 0; i < 1; i++) { // Filter untuk 5 kolom
                    stmt.setString(paramIndex++, "%" + filter + "%");
                }
            }

            // Set limit and offset for pagination
            int pageSize = 20; // Sesuaikan dengan kebutuhan Anda
            stmt.setInt(paramIndex++, pageSize); // Parameter untuk LIMIT
            stmt.setInt(paramIndex, (page - 1) * pageSize); // Parameter untuk OFFSET

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
        }

        return resultList;
    }

    // 🔹 Delete
    public void delete(int id) throws SQLException {
        // 1️⃣ Cek apakah ID yang akan dihapus adalah data closing terakhir
        String lastClosingSql = "SELECT id FROM closing ORDER BY tanggal DESC LIMIT 1";
        int lastClosingId = -1;

        try (PreparedStatement stmt = conn.prepareStatement(lastClosingSql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                lastClosingId = rs.getInt("id");
            }
        }

        // 2️⃣ Jika ID bukan closing terakhir, throw exception
        if (id != lastClosingId) {
            throw new SQLException("Hanya data closing terakhir yang dapat dihapus!");
        }

        // 3️⃣ Lakukan DELETE jika valid
        String deleteSql = "DELETE FROM closing WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(deleteSql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

}
