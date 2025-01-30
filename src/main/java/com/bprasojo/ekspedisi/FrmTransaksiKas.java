/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.bprasojo.ekspedisi;

import com.bprasojo.ekspedisi.dao.PerkiraanDAO;
import com.bprasojo.ekspedisi.dao.TransaksiKasDAO;
import com.bprasojo.ekspedisi.model.Perkiraan;
import com.bprasojo.ekspedisi.model.TransaksiKas;
import com.bprasojo.ekspedisi.utils.LookupForm;
import java.awt.Component;
import java.awt.Frame;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author USER
 */
public class FrmTransaksiKas extends javax.swing.JInternalFrame {

    /**
     * Creates new form FrmTransaksiKas
     */
    public FrmTransaksiKas() {
        initComponents();
        
        btnNewActionPerformed(null);
//        InisiasiToolbar();
        
    }
    // </editor-fold>

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
        btnHapus = new javax.swing.JButton();
        Simpan = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        btnKeluar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        edKodeAkun = new javax.swing.JTextField();
        edNamaAkun = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        edKeterangan = new javax.swing.JTextField();
        edTanggal = new com.toedter.calendar.JDateChooser();
        edUangMasuk = new javax.swing.JFormattedTextField();
        edUangKeluar = new javax.swing.JFormattedTextField();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Transaksi Kas");

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

        btnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Delete32.png"))); // NOI18N
        btnHapus.setText("Hapus");
        btnHapus.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnHapus.setFocusable(false);
        btnHapus.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnHapus.setMaximumSize(new java.awt.Dimension(60, 70));
        btnHapus.setMinimumSize(new java.awt.Dimension(60, 70));
        btnHapus.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnHapus);

        Simpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Save32.png"))); // NOI18N
        Simpan.setText("Simpan");
        Simpan.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        Simpan.setFocusable(false);
        Simpan.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Simpan.setMaximumSize(new java.awt.Dimension(60, 70));
        Simpan.setMinimumSize(new java.awt.Dimension(60, 70));
        Simpan.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SimpanActionPerformed(evt);
            }
        });
        jToolBar1.add(Simpan);

        btnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Undo32.png"))); // NOI18N
        btnBatal.setText("Batal");
        btnBatal.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnBatal.setFocusable(false);
        btnBatal.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnBatal.setMaximumSize(new java.awt.Dimension(60, 70));
        btnBatal.setMinimumSize(new java.awt.Dimension(60, 70));
        btnBatal.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnBatal);

        btnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Close32.png"))); // NOI18N
        btnKeluar.setText("Keluar");
        btnKeluar.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnKeluar.setFocusable(false);
        btnKeluar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnKeluar.setMaximumSize(new java.awt.Dimension(60, 70));
        btnKeluar.setMinimumSize(new java.awt.Dimension(60, 70));
        btnKeluar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnKeluar);

        getContentPane().add(jToolBar1, java.awt.BorderLayout.PAGE_START);

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        edKodeAkun.setText("jTextField1");

        edNamaAkun.setText("jTextField1");
        edNamaAkun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edNamaAkunActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Akun Biaya / Pendapatan");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Akun Kas");

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Tanggal");

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Uang Masuk");

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Uang Keluar");

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Keterangan");

        edKeterangan.setText("jTextField3");

        edUangMasuk.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));

        edUangKeluar.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(edKodeAkun, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(edNamaAkun))
                    .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(edTanggal, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                            .addComponent(edUangMasuk, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(edUangKeluar, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
                    .addComponent(edKeterangan))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(edKodeAkun, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(edNamaAkun, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(edTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(edUangMasuk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(edUangKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(edKeterangan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, java.awt.BorderLayout.NORTH);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        edKodeAkun.setText("");
        edNamaAkun.setText("");
        
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime(); // Mendapatkan tanggal dalam format Date
        
        edTanggal.setDate(today);
        edUangMasuk.setText("0");
        edUangKeluar.setText("0");
        edKeterangan.setText("");
    }//GEN-LAST:event_btnNewActionPerformed

    private void SimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SimpanActionPerformed
        try {
            PerkiraanDAO perkiraanDao = new PerkiraanDAO();
            Perkiraan perkiraanTransaksi = perkiraanDao.getPerkiraanByKode(edNamaAkun.getText());
            Perkiraan perkiraanKas = perkiraanDao.getPerkiraanByKode(edNamaAkun.getText());
            Integer nominalMasuk = Integer.parseInt(edUangMasuk.getValue().toString());
            Integer nominalKeluar = Integer.parseInt(edUangKeluar.getValue().toString());
            String keterangan = edKeterangan.getText();
            
            
            TransaksiKas transaksiKas = new TransaksiKas();
            transaksiKas.setAkunKasId(perkiraanKas.getId());
            transaksiKas.setAkunTransaksiId(perkiraanTransaksi.getId());
            transaksiKas.setArmadaId(0);
            transaksiKas.setKeterangan(keterangan);
            transaksiKas.setNominalMasuk(nominalMasuk);
            transaksiKas.setNominalKeluar(nominalKeluar);
            
            java.sql.Date sqlDate;
            sqlDate = new java.sql.Date(edTanggal.getDate().getTime());
            transaksiKas.setTanggal(sqlDate);
            
            TransaksiKasDAO transaksiKasDAO = new TransaksiKasDAO();
            transaksiKasDAO.save(transaksiKas);
            
            Component frame = null;            
            JOptionPane.showMessageDialog(frame, "Data berhasil disimpan!");
            

        } catch (SQLException ex) {
            Logger.getLogger(FrmTransaksiKas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_SimpanActionPerformed

    private void edNamaAkunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edNamaAkunActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_edNamaAkunActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        String sqlQuery = "SELECT * FROM perkiraan"; // SQL untuk data lookup
        Frame parent = JOptionPane.getFrameForComponent(this);
        LookupForm lookupForm = new LookupForm(parent, sqlQuery);
        lookupForm.setVisible(true);

        // Ambil data yang dipilih
        Object selectedRecord = lookupForm.getSelectedRecord();
        if (selectedRecord != null) {
            // Tampilkan data terpilih
            JOptionPane.showMessageDialog(this, "Data Terpilih: " + selectedRecord);
        } else {
            JOptionPane.showMessageDialog(this, "Tidak ada data yang dipilih.");
        }
    }//GEN-LAST:event_btnEditActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Simpan;
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton btnNew;
    private javax.swing.JTextField edKeterangan;
    private javax.swing.JTextField edKodeAkun;
    private javax.swing.JTextField edNamaAkun;
    private com.toedter.calendar.JDateChooser edTanggal;
    private javax.swing.JFormattedTextField edUangKeluar;
    private javax.swing.JFormattedTextField edUangMasuk;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
