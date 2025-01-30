/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bprasojo.ekspedisi.utils;

/**
 *
 * @author USER
 */
import com.bprasojo.ekspedisi.dao.ArmadaDAO;
import com.bprasojo.ekspedisi.database.DatabaseConnection;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LookupForm extends javax.swing.JDialog {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtFilter;
    private final JButton btnOK;
    private final JButton btnCancel;
    private Object selectedRecord;
    private Connection conn;

    public LookupForm(Frame parent, String sqlQuery) {
        super(parent, true); // Menggunakan JOptionPane untuk mengambil Frame
        try {
            this.conn = DatabaseConnection.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(ArmadaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        setLayout(new BorderLayout());

        // Panel untuk Filter
        JPanel filterPanel = new JPanel(new BorderLayout());
        txtFilter = new JTextField();
        filterPanel.add(new JLabel("Filter: "), BorderLayout.WEST);
        filterPanel.add(txtFilter, BorderLayout.CENTER);

        // Tombol Cancel dan OK
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnOK = new JButton("OK");
        btnCancel = new JButton("Cancel");
        buttonPanel.add(btnOK);
        buttonPanel.add(btnCancel);

        // Tabel untuk menampilkan data
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Tambahkan komponen ke form
        add(filterPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Event untuk Filter
        txtFilter.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String filterText = txtFilter.getText().toLowerCase();
                TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
                table.setRowSorter(sorter);
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + filterText));
            }
        });

        // Event untuk Tombol OK
        btnOK.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int modelRow = table.convertRowIndexToModel(selectedRow);
                selectedRecord = tableModel.getDataVector().get(modelRow);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Pilih baris terlebih dahulu.");
            }
        });

        // Event untuk Tombol Cancel
        btnCancel.addActionListener(e -> {
            selectedRecord = null;
            dispose();
        });

        // Load Data ke Tabel
        loadData(sqlQuery, conn);

        // Atur ukuran dan lokasi form
        setSize(800, 600);
        setLocationRelativeTo(parent);
    }

    private void loadData(String sqlQuery, Connection connection) {
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sqlQuery)) {
            // Atur kolom di tabel
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            Vector<String> columnNames = new Vector<>();
            for (int i = 1; i <= columnCount; i++) {
                columnNames.add(metaData.getColumnName(i));
            }
            tableModel.setColumnIdentifiers(columnNames);

            // Isi data ke tabel
            Vector<Vector<Object>> data = new Vector<>();
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(rs.getObject(i));
                }
                data.add(row);
            }
            for (Vector<Object> row : data) {
                tableModel.addRow(row);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Object getSelectedRecord() {
        return selectedRecord;
    }

//    public static void main(String[] args) {
//        // Contoh koneksi ke database
//        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database", "username", "password")) {
//            String sqlQuery = "SELECT * FROM your_table"; // Ganti dengan SQL Anda
//            LookupForm lookupForm = new LookupForm(null, sqlQuery);
//            lookupForm.setVisible(true);
//
//            Object selectedRecord = lookupForm.getSelectedRecord();
//            if (selectedRecord != null) {
//                System.out.println("Data Terpilih: " + selectedRecord);
//            } else {
//                System.out.println("Tidak ada data yang dipilih.");
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//    }
}

