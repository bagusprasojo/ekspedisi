/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.bprasojo.ekspedisi;

import com.bprasojo.ekspedisi.dao.ClosingDAO;
import com.bprasojo.ekspedisi.model.Closing;
import com.bprasojo.ekspedisi.utils.AppUtils;
import com.bprasojo.ekspedisi.utils.CustomFocusTraversalPolicy;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 */
public class FrmClosing extends javax.swing.JInternalFrame {

    /**
     * Creates new form FrmArmada
     */
    
    private int currentPage = 1;
    
    private Closing closing = null;
    private final ClosingDAO closingDAO = new ClosingDAO();
    private DefaultTableModel tableModel;
    private boolean SilakanLoadData = false;
    
    
    
    public FrmClosing() {
        initComponents();

        tableModel = new DefaultTableModel(new String[]{"Id","Tanggal","Keterangan"}, 0);
        tblClosing.setModel(tableModel);
        tblClosing.removeColumn(tblClosing.getColumnModel().getColumn(0));

        AppUtils.SetTanggalAwalTahun(edTglAwal);
        AppUtils.SetTanggalAkhirTahun(edTglAkhir);



        setStatusTombol("awal");
        SilakanLoadData = true;

        inisialisasiEventTableModel();
        pnlInput.setFocusTraversalPolicy(new CustomFocusTraversalPolicy(edTanggal,edKeterangan));
        
    }

    public void loadDataClosing(int page) {
        try {
            tableModel.setRowCount(0); // Bersihkan tabel
            List<Map<String, Object>> result = closingDAO.getClosingByPage(page, edTglAwal.getDate(), edTglAkhir.getDate(), edSearch.getText());
            for (Map<String, Object> row : result) {
                tableModel.addRow(new Object[]{
                    (Integer) row.get("id"),
                    AppUtils.DateFormatShort((Date) row.get("tanggal")),
                    (String) row.get("keterangan")
                });            
            }
            
            tblClosing.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        } catch (SQLException ex) {
            AppUtils.showWarningDialog("Gagal load daftar closing dengan error \n" + ex.getMessage());
        }
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        btnNew = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnKeluar = new javax.swing.JButton();
        pnlDua = new javax.swing.JPanel();
        pnlData = new javax.swing.JPanel();
        pnlSearch = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        edSearch = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        edTglAwal = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        edTglAkhir = new com.toedter.calendar.JDateChooser();
        pnlButton = new javax.swing.JPanel();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblClosing = new javax.swing.JTable();
        pnlInput = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        edKeterangan = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        edTanggal = new com.toedter.calendar.JDateChooser();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Daftar Armada");

        jToolBar1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jToolBar1.setRollover(true);

        btnNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Add32.png"))); // NOI18N
        btnNew.setText("Tambah");
        btnNew.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnNew.setFocusable(false);
        btnNew.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNew.setMaximumSize(new java.awt.Dimension(60, 70));
        btnNew.setMinimumSize(new java.awt.Dimension(60, 70));
        btnNew.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });
        jToolBar1.add(btnNew);

        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Edit32.png"))); // NOI18N
        btnEdit.setText("Edit");
        btnEdit.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnEdit.setFocusable(false);
        btnEdit.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEdit.setMaximumSize(new java.awt.Dimension(60, 70));
        btnEdit.setMinimumSize(new java.awt.Dimension(60, 70));
        btnEdit.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        jToolBar1.add(btnEdit);

        btnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Save32.png"))); // NOI18N
        btnSimpan.setText("Simpan");
        btnSimpan.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnSimpan.setFocusable(false);
        btnSimpan.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSimpan.setMaximumSize(new java.awt.Dimension(60, 70));
        btnSimpan.setMinimumSize(new java.awt.Dimension(60, 70));
        btnSimpan.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });
        jToolBar1.add(btnSimpan);

        btnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Undo32.png"))); // NOI18N
        btnBatal.setText("Batal");
        btnBatal.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnBatal.setFocusable(false);
        btnBatal.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnBatal.setMaximumSize(new java.awt.Dimension(60, 70));
        btnBatal.setMinimumSize(new java.awt.Dimension(60, 70));
        btnBatal.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });
        jToolBar1.add(btnBatal);

        btnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Delete32.png"))); // NOI18N
        btnHapus.setText("Hapus");
        btnHapus.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnHapus.setFocusable(false);
        btnHapus.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnHapus.setMaximumSize(new java.awt.Dimension(60, 70));
        btnHapus.setMinimumSize(new java.awt.Dimension(60, 70));
        btnHapus.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });
        jToolBar1.add(btnHapus);

        btnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Close32.png"))); // NOI18N
        btnKeluar.setText("Keluar");
        btnKeluar.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnKeluar.setFocusable(false);
        btnKeluar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnKeluar.setMaximumSize(new java.awt.Dimension(60, 70));
        btnKeluar.setMinimumSize(new java.awt.Dimension(60, 70));
        btnKeluar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluarActionPerformed(evt);
            }
        });
        jToolBar1.add(btnKeluar);

        getContentPane().add(jToolBar1, java.awt.BorderLayout.NORTH);

        pnlDua.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnlDua.setLayout(new java.awt.BorderLayout());

        pnlData.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnlData.setLayout(new java.awt.BorderLayout());

        pnlSearch.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel7.setText("Search Data");

        edSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                edSearchKeyReleased(evt);
            }
        });

        jLabel3.setText("Tanggal");

        edTglAwal.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                edTglAwalPropertyChange(evt);
            }
        });

        jLabel4.setText("s.d.");

        edTglAkhir.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                edTglAkhirPropertyChange(evt);
            }
        });

        javax.swing.GroupLayout pnlSearchLayout = new javax.swing.GroupLayout(pnlSearch);
        pnlSearch.setLayout(pnlSearchLayout);
        pnlSearchLayout.setHorizontalGroup(
            pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSearchLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(edTglAwal, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(edTglAkhir, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(edSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(120, Short.MAX_VALUE))
        );
        pnlSearchLayout.setVerticalGroup(
            pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSearchLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(edTglAkhir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(edSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)
                        .addComponent(jLabel4))
                    .addComponent(edTglAwal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlData.add(pnlSearch, java.awt.BorderLayout.NORTH);

        pnlButton.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnPrev.setText("<< Prev");
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        btnNext.setText("Next >>");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlButtonLayout = new javax.swing.GroupLayout(pnlButton);
        pnlButton.setLayout(pnlButtonLayout);
        pnlButtonLayout.setHorizontalGroup(
            pnlButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlButtonLayout.createSequentialGroup()
                .addContainerGap(576, Short.MAX_VALUE)
                .addComponent(btnPrev)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNext)
                .addContainerGap())
        );
        pnlButtonLayout.setVerticalGroup(
            pnlButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlButtonLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNext)
                    .addComponent(btnPrev))
                .addContainerGap())
        );

        pnlData.add(pnlButton, java.awt.BorderLayout.SOUTH);

        tblClosing.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tblClosing);
        tblClosing.getAccessibleContext().setAccessibleParent(tblClosing);

        pnlData.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        pnlDua.add(pnlData, java.awt.BorderLayout.CENTER);

        pnlInput.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnlInput.setFocusCycleRoot(true);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Keterangan");

        edKeterangan.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Tanggal");

        javax.swing.GroupLayout pnlInputLayout = new javax.swing.GroupLayout(pnlInput);
        pnlInput.setLayout(pnlInputLayout);
        pnlInputLayout.setHorizontalGroup(
            pnlInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInputLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(edKeterangan, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(edTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(362, Short.MAX_VALUE))
        );

        pnlInputLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel1, jLabel2});

        pnlInputLayout.setVerticalGroup(
            pnlInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInputLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlInputLayout.createSequentialGroup()
                        .addComponent(edTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(edKeterangan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)))
                    .addComponent(jLabel1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlDua.add(pnlInput, java.awt.BorderLayout.NORTH);

        getContentPane().add(pnlDua, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        try {
            closing = new Closing();
            
            Date lastClosingDate = closingDAO.getLastClosingDate();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(lastClosingDate);
            
            // Mendapatkan tahun dari Calendar
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH); // JAN = 0
            if (month == 11){
                month = 0;
                year = year + 1;
            } else {
                month = month + 1;
            }
            
            AppUtils.SetTanggalAkhirBulan(edTanggal, year, month);
            
            edKeterangan.setText("");
            
            setStatusTombol("tambah");
            loadDataClosing(currentPage);
        } catch (SQLException ex) {
            AppUtils.showErrorDialog(ex.getMessage());
        }
    }//GEN-LAST:event_btnNewActionPerformed

    private void setStatusTombol(String mode){
        if (mode == "awal"){
            btnNew.setEnabled(true);
            btnEdit.setEnabled(false);
            btnSimpan.setEnabled(false);
            btnBatal.setEnabled(false);
            btnHapus.setEnabled(false);
            pnlInput.setEnabled(false);
            
            SetEnableKomponenInput(false);
        } else if (mode == "tambah"){
            btnNew.setEnabled(false);
            btnEdit.setEnabled(false);
            btnSimpan.setEnabled(true);
            btnBatal.setEnabled(true);
            btnHapus.setEnabled(false);
            pnlInput.setEnabled(true);
            
            SetEnableKomponenInput(true);
        } else if (mode == "edit"){
            btnNew.setEnabled(false);
            btnEdit.setEnabled(false);
            btnSimpan.setEnabled(true);
            btnBatal.setEnabled(true);
            btnHapus.setEnabled(true);
            pnlInput.setEnabled(true);
            
            SetEnableKomponenInput(true);
        } else if (mode == "selected"){
            btnNew.setEnabled(true);
            btnEdit.setEnabled(false);
            btnSimpan.setEnabled(false);
            btnBatal.setEnabled(false);
            btnHapus.setEnabled(true);
            pnlInput.setEnabled(false);
            
            SetEnableKomponenInput(false);
        }
    }
    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        if (validasiInput() == false){
            return;
        }
        try {
            closing.setTanggal(edTanggal.getDate());
            closing.setKeterangan(edKeterangan.getText());
            closingDAO.save(closing);
            AppUtils.showInfoDialog("Data berhasil disimpan");
            setStatusTombol("awal");
            loadDataClosing(currentPage);
        } catch (SQLException ex) {
            AppUtils.showErrorDialog("Gagal simpan data dengan error : \n" + ex.getMessage());
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        setStatusTombol("edit");
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        currentPage++;
        loadDataClosing(currentPage);
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        if (currentPage > 1) {
            currentPage--;
            loadDataClosing(currentPage);
        }
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        setStatusTombol("awal");
    }//GEN-LAST:event_btnBatalActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        boolean userConfirmed = AppUtils.showConfirmDialog("Apakah Anda yakin akan menghapus data?");

        if (userConfirmed) {
            try {
                closingDAO.delete(closing.getId());
                setStatusTombol("awal");
                loadDataClosing(currentPage);
            } catch (SQLException ex) {
                AppUtils.showWarningDialog("Ada kesalahan dengan error : \n" + ex.getMessage());
            }
        }
        
        
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_btnKeluarActionPerformed

    private void edSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edSearchKeyReleased
        if (SilakanLoadData){
            currentPage = 1;
            loadDataClosing(currentPage);
        }
    }//GEN-LAST:event_edSearchKeyReleased

    private void edTglAwalPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_edTglAwalPropertyChange
        if (SilakanLoadData){
            currentPage = 1;
            loadDataClosing(currentPage);
        }
    }//GEN-LAST:event_edTglAwalPropertyChange

    private void edTglAkhirPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_edTglAkhirPropertyChange
        if (SilakanLoadData){
            currentPage = 1;
            loadDataClosing(currentPage);
        }
    }//GEN-LAST:event_edTglAkhirPropertyChange


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JTextField edKeterangan;
    private javax.swing.JTextField edSearch;
    private com.toedter.calendar.JDateChooser edTanggal;
    private com.toedter.calendar.JDateChooser edTglAkhir;
    private com.toedter.calendar.JDateChooser edTglAwal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JPanel pnlButton;
    private javax.swing.JPanel pnlData;
    private javax.swing.JPanel pnlDua;
    private javax.swing.JPanel pnlInput;
    private javax.swing.JPanel pnlSearch;
    private javax.swing.JTable tblClosing;
    // End of variables declaration//GEN-END:variables
    
    private void SetEnableKomponenInput(boolean enable){
        edTanggal.setEnabled(enable);
        edKeterangan.setEnabled(enable);
    }
    
    private void inisialisasiEventTableModel() {
        tblClosing.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // Memeriksa apakah ada baris yang dipilih
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = tblClosing.getSelectedRow(); // Mendapatkan baris yang dipilih
                    if (selectedRow != -1) {
                        
                        int id = (Integer) tblClosing.getModel().getValueAt(tblClosing.getSelectedRow(),0);
//                        Integer id = (Integer) tblStakeHolder.getValueAt(selectedRow, 0);
                        LoadClosing(id);
                        setStatusTombol("selected");
                    }
                }
            }

            private void LoadClosing(Integer id) {
                try {
                    closing = closingDAO.getById(id);
                    if (closing != null){
                        edKeterangan.setText(closing.getKeterangan());
                        edTanggal.setDate(closing.getTanggal());
                        
                    } else {
                        edKeterangan.setText("");
                        AppUtils.SetTanggalAkhirBulan(edTanggal);
                    }
                } catch (SQLException ex) {
                    AppUtils.showWarningDialog("Ada kesalahan load data dengan pesan : \n" + ex.getMessage());
                }
            }
        });
    }

    private boolean validasiInput() {
        return true;
    }

}
