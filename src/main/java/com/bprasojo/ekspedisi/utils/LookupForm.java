package com.bprasojo.ekspedisi.utils;

import com.bprasojo.ekspedisi.dao.ArmadaDAO;
import com.bprasojo.ekspedisi.database.DatabaseConnection;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LookupForm extends javax.swing.JDialog {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtFilter;
    private final JButton btnOK;
    private final JButton btnCancel;
    private Map<String, Object> selectedRecord;
    private Connection conn;
    private List<String> columnNames;

//	 Tambahkan MouseListener ke tabel
    

    public LookupForm(javax.swing.JInternalFrame owner, String sqlQuery, Boolean setVisible) {
        super(JOptionPane.getFrameForComponent(owner), true); // Menggunakan JOptionPane untuk mengambil Frame
        try {
            this.conn = DatabaseConnection.getConnection();
        } catch (SQLException ex) {
            AppUtils.showErrorDialog("Gagal Load Data di LookUpForm dengan error \n" + ex.getMessage());
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
                selectedRecord = getSelectedRowData(modelRow);
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
        
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Cek apakah ini adalah double-click (klik dua kali)
                if (e.getClickCount() == 2) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        int modelRow = table.convertRowIndexToModel(selectedRow);
                        selectedRecord = getSelectedRowData(modelRow);
                        dispose(); // Menutup dialog setelah memilih data
                    } else {
                        JOptionPane.showMessageDialog(LookupForm.this, "Pilih baris terlebih dahulu.");
                    }
                }
            }
        });

        // Load Data ke Tabel
        loadData(sqlQuery, conn);

        // Atur ukuran dan lokasi form
        setSize(800, 600);
        setLocationRelativeTo(JOptionPane.getFrameForComponent(owner));
        
        
        this.setVisible(setVisible);
    }

    private void loadData(String sqlQuery, Connection connection) {
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sqlQuery)) {
            // Atur kolom di tabel
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            columnNames = new ArrayList<>();

            Vector<String> columnHeaders = new Vector<>();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                columnNames.add(columnName);
                columnHeaders.add(columnName);
            }
            tableModel.setColumnIdentifiers(columnHeaders);

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

    private Map<String, Object> getSelectedRowData(int modelRow) {
        Map<String, Object> rowData = new LinkedHashMap<>();
        for (int i = 0; i < columnNames.size(); i++) {
            rowData.put(columnNames.get(i), tableModel.getValueAt(modelRow, i));
        }
        return rowData;
    }

    public Map<String, Object> getSelectedRecord() {
        return selectedRecord;
    }
}
