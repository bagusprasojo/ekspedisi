/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bprasojo.ekspedisi.dao;

/**
 *
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

public class ClosingDAO extends ParentDAO{
//    private Connection conn;

    public ClosingDAO() {
        super();
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
    
    private int getLastClosingValue(int bank_id) throws SQLException{
        int saldo_akhir = 0;
        
        String sql = "select saldo_akhir from closing_bank a " +
                     " where a.bank_id = ? " +
                     " order by a.tanggal desc limit 1 ";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bank_id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    saldo_akhir = rs.getInt("saldo_akhir");
                }
               
            }
        }
            

            
        
        return saldo_akhir;
    }
    
    private void saveClosingBank(Closing closing) throws SQLException {
        Calendar calendar = Calendar.getInstance();

        // Pastikan closing.getTanggal() mengembalikan objek Date yang valid
        calendar.setTime(closing.getTanggal()); 
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Karena bulan dimulai dari 0 di Calendar

        String sql = "select a.id as bank_id, SUM(kredit - debet) AS mutasi from bank a " +
                     " left join v_mutasi_bank b on a.id = b.bank_id and YEAR(b.tanggal) = ? and MONTH(b.tanggal) = ? " +
                     " GROUP BY a.id ";

        Date tanggal = new Date(closing.getTanggal().getTime());
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, year);
            stmt.setInt(2, month);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) { // Menggunakan while untuk insert lebih dari satu bank_id
                    String sqlInsert = "INSERT INTO closing_bank (closing_id, bank_id, tanggal, saldo_akhir) VALUES (?, ?, ?, ?)";

                    // Gunakan objek PreparedStatement baru untuk query INSERT
                    try (PreparedStatement stmtInsert = conn.prepareStatement(sqlInsert)) {
                        int saldo_last_month = getLastClosingValue(rs.getInt("bank_id"));
                        int mutasi = rs.getInt("mutasi");
                        int saldo_akhir = saldo_last_month + mutasi;
                        
                        stmtInsert.setInt(1, closing.getId());
                        stmtInsert.setInt(2, rs.getInt("bank_id"));
                        stmtInsert.setDate(3, tanggal);
                        stmtInsert.setInt(4, saldo_akhir);

                        stmtInsert.executeUpdate(); // Menjalankan query INSERT
                    }
                }
            }
        }
    }

//    public java.util.Date getLastClosingDate() throws SQLException {
//        String lastClosingSql = "SELECT tanggal FROM closing ORDER BY tanggal DESC LIMIT 1";
//        java.util.Date lastClosingDate = null;
//
//        try (PreparedStatement stmt = conn.prepareStatement(lastClosingSql)) {
//            try (ResultSet rs = stmt.executeQuery()) {
//                if (rs.next()) {
//                    // Konversi dari java.sql.Date ke java.util.Date
//                    lastClosingDate = new java.util.Date(rs.getDate("tanggal").getTime());
//                }
//            }
//        }
//
//        return lastClosingDate; // Jika tidak ada data, akan mengembalikan null
//    }


    public void save(Closing closing) throws SQLException {
        boolean previousAutoCommit = conn.getAutoCommit();
        conn.setAutoCommit(false);
        try {
            String lastClosingSql = "SELECT tanggal FROM closing ORDER BY tanggal DESC LIMIT 1";
            Date lastClosingDate = null;

            try (PreparedStatement stmt = conn.prepareStatement(lastClosingSql)) {
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        lastClosingDate = rs.getDate("tanggal");
                    }
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
                stmt.setDate(1, new java.sql.Date(tanggal.getTime())); // Menggunakan java.sql.Date untuk setDate
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
                
                deleteClosingBank(closing.getId());
                saveClosingBank(closing);
            }
            
            conn.commit();
        } catch (SQLException ex) {
            // Jika terjadi kesalahan, rollback transaksi
            conn.rollback();
            throw ex; // Rethrow exception setelah rollback
        } finally {
            // Mengembalikan auto-commit ke status semula
            conn.setAutoCommit(previousAutoCommit);
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

        boolean previousAutoCommit = conn.getAutoCommit();
        conn.setAutoCommit(false);
        
        try {
            String deleteSql = "DELETE FROM closing WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(deleteSql)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }
            
            deleteClosingBank(id);
            conn.commit();
        } catch (SQLException ex) {
            conn.rollback();
            throw ex; 
        } finally {
            conn.setAutoCommit(previousAutoCommit);
        } 
    }

    private void deleteClosingBank(int closingID) throws SQLException {
        String deleteSql = "DELETE FROM closing_bank WHERE closing_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(deleteSql)) {
            stmt.setInt(1, closingID);
            stmt.executeUpdate();
        }
    }

}
