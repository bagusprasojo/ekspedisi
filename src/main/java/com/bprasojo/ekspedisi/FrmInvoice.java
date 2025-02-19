/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.bprasojo.ekspedisi;

import com.bprasojo.ekspedisi.dao.StakeHolderDAO;
import com.bprasojo.ekspedisi.dao.TagihanCustomerDAO;
import com.bprasojo.ekspedisi.model.StakeHolder;
import com.bprasojo.ekspedisi.model.TagihanCustomer;
import com.bprasojo.ekspedisi.utils.AppUtils;
import com.bprasojo.ekspedisi.utils.LookupForm;
import java.sql.SQLException;
import java.util.Map;

/**
 *
 * @author USER
 */
public class FrmInvoice extends javax.swing.JInternalFrame {

    /**
     * Creates new form FrmInvoice
     */
    
    TagihanCustomer invoice;
    TagihanCustomerDAO invoiceDAO;
    
    StakeHolder customer;
    StakeHolderDAO customerDAO;
    
    public FrmInvoice() {
        initComponents();
        
        invoiceDAO = new TagihanCustomerDAO();
        customerDAO = new StakeHolderDAO();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        btnNew = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnKeluar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        edTanggal = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        edNoInvoice = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        edCustomer = new javax.swing.JTextField();
        btnCustomer = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        edNilaiPekerjaan = new javax.swing.JFormattedTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        edPekerjaan = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        edKeterangan = new javax.swing.JTextArea();
        edPPN = new javax.swing.JFormattedTextField();
        edTotal = new javax.swing.JFormattedTextField();
        jPanel3 = new javax.swing.JPanel();
        pnlFilter = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        edTglAwal = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        edTglAkhir = new com.toedter.calendar.JDateChooser();
        jLabel10 = new javax.swing.JLabel();
        edFilter = new javax.swing.JTextField();
        pnlNextPrev = new javax.swing.JPanel();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPembayaranKasBon = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Invoice Customer");

        jPanel1.setLayout(new java.awt.BorderLayout());

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

        jPanel1.add(jToolBar1, java.awt.BorderLayout.NORTH);

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("No Invoice");

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Tanggal");

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Pekerjaan");

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("PPN 12%");

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Keterangan");

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Total");

        edNoInvoice.setEditable(false);
        edNoInvoice.setText("Otomatis");
        edNoInvoice.setEnabled(false);

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Customer");

        edCustomer.setFocusCycleRoot(true);

        btnCustomer.setText("...");
        btnCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCustomerActionPerformed(evt);
            }
        });

        jLabel3.setText("Nilai Pekerjaan");

        edNilaiPekerjaan.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        edNilaiPekerjaan.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jScrollPane2.setViewportView(edPekerjaan);

        jScrollPane3.setViewportView(edKeterangan);

        edPPN.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        edPPN.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        edTotal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        edTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(edCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(edNoInvoice, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(edTanggal, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addGap(38, 38, 38)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(edNilaiPekerjaan, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
                    .addComponent(jScrollPane3)
                    .addComponent(edPPN, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
                    .addComponent(edTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE))
                .addGap(0, 82, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(edNoInvoice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(edNilaiPekerjaan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(edCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCustomer)
                    .addComponent(jLabel8)
                    .addComponent(edPPN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel7))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(edTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(edTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jLabel1.setText("Periode");

        edTglAwal.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                edTglAwalPropertyChange(evt);
            }
        });

        jLabel2.setText("s.d.");

        edTglAkhir.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                edTglAkhirPropertyChange(evt);
            }
        });

        jLabel10.setText("Filter");

        edFilter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                edFilterKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout pnlFilterLayout = new javax.swing.GroupLayout(pnlFilter);
        pnlFilter.setLayout(pnlFilterLayout);
        pnlFilterLayout.setHorizontalGroup(
            pnlFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFilterLayout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(edTglAwal, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addGap(12, 12, 12)
                .addComponent(edTglAkhir, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(edFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );
        pnlFilterLayout.setVerticalGroup(
            pnlFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFilterLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(pnlFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(edFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(edTglAkhir, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(edTglAwal, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );

        jPanel3.add(pnlFilter, java.awt.BorderLayout.NORTH);

        pnlNextPrev.setBorder(javax.swing.BorderFactory.createEtchedBorder());

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

        javax.swing.GroupLayout pnlNextPrevLayout = new javax.swing.GroupLayout(pnlNextPrev);
        pnlNextPrev.setLayout(pnlNextPrevLayout);
        pnlNextPrevLayout.setHorizontalGroup(
            pnlNextPrevLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlNextPrevLayout.createSequentialGroup()
                .addContainerGap(542, Short.MAX_VALUE)
                .addComponent(btnPrev)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNext)
                .addContainerGap())
        );
        pnlNextPrevLayout.setVerticalGroup(
            pnlNextPrevLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlNextPrevLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlNextPrevLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNext)
                    .addComponent(btnPrev))
                .addContainerGap())
        );

        jPanel3.add(pnlNextPrev, java.awt.BorderLayout.PAGE_END);

        tblPembayaranKasBon.setModel(new javax.swing.table.DefaultTableModel(
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
        tblPembayaranKasBon.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tblPembayaranKasBonFocusGained(evt);
            }
        });
        jScrollPane1.setViewportView(tblPembayaranKasBon);

        jPanel3.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel3, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
//        LoadDataPembayaranKasBon(currentPage);
//
        invoice = new TagihanCustomer();
        
        customer = null;
        edCustomer.setText("");
        
        AppUtils.SetTanggalToday(edTanggal);
        edPekerjaan.setText("");
        edNilaiPekerjaan.setValue(0);
        edPPN.setValue(0);
        edKeterangan.setText("");
        
        setStatusTombol("tambah");
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        setStatusTombol("edit");
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        if (validasiInput() == false){
            return;
        }

        try {
            invoice.setCustomerId(customer.getId());
            invoice.setNilaiPekerjaan(((Number) edNilaiPekerjaan.getValue()).intValue());
            invoice.setPpn(((Number) edPPN.getValue()).intValue());
            invoice.setTotal(((Number) edTotal.getValue()).intValue());
            invoice.setTanggal(edTanggal.getDate());
            invoice.setPekerjaan(edPekerjaan.getText().trim());
            
            String terbilang = AppUtils.terbilang(invoice.getTotal());
            invoice.setTerbilang(terbilang);
            
            invoiceDAO.save(invoice);
            AppUtils.showInfoDialog("Data berhasil disimpan dengan no register : " + invoice.getNoInvoice());
//            LoadDataPembayaranKasBon(currentPage);

        } catch (SQLException ex) {
            AppUtils.showErrorDialog("Gagal menyimpan data dengan error " + ex.getMessage());
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        setStatusTombol("awal");
    }//GEN-LAST:event_btnBatalActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
//        boolean userConfirmed = AppUtils.showConfirmDialog("Apakah Anda yakin akan menghapus data?");
//
//        if (userConfirmed) {
//            try {
//                pembayaranKasBonDAO.delete(pembayaranKasBon.getId());
//                setStatusTombol("awal");
//                LoadDataPembayaranKasBon(currentPage);
//            } catch (SQLException ex) {
//                AppUtils.showErrorDialog("Gagal menghapus data dengan error: \n" + ex.getMessage());
//            }
//        }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_btnKeluarActionPerformed

    private void edTglAwalPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_edTglAwalPropertyChange
//        if (SilakanLoadData){
//            currentPage = 1;
//            LoadDataPembayaranKasBon(currentPage);
//        }
    }//GEN-LAST:event_edTglAwalPropertyChange

    private void edTglAkhirPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_edTglAkhirPropertyChange
//        if (SilakanLoadData){
//            currentPage = 1;
//            LoadDataPembayaranKasBon(currentPage);
//        }
    }//GEN-LAST:event_edTglAkhirPropertyChange

    private void edFilterKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edFilterKeyReleased
//        if (SilakanLoadData){
//            currentPage = 1;
//            LoadDataPembayaranKasBon(currentPage);
//        }
    }//GEN-LAST:event_edFilterKeyReleased

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
//        if (currentPage > 1) {
//            currentPage--;
//            LoadDataPembayaranKasBon(currentPage);
//        }
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
//        currentPage++;
//        LoadDataPembayaranKasBon(currentPage);
    }//GEN-LAST:event_btnNextActionPerformed

    private void tblPembayaranKasBonFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tblPembayaranKasBonFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_tblPembayaranKasBonFocusGained

    private void btnCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCustomerActionPerformed
        String sqlQuery = "select kode, nama, alamat from stake_holder "
                          + " where jenis = 'customer'";

        LookupForm lookupForm = new LookupForm(this, sqlQuery, true);
        Map<String, Object> selectedRecord = lookupForm.getSelectedRecord();
        if (selectedRecord != null) {
            try {
                // Mengambil nilai dengan nama kolom
                String kode = selectedRecord.get("kode").toString();
                customer = customerDAO.getByKode(kode);

                if (customer != null){
                    edCustomer.setText(customer.getNama());
                } else {
                    edCustomer.setText("");
                }
            } catch (SQLException ex) {
                AppUtils.showWarningDialog("Ada kesalahan load data customer dengan error \n" + ex.getMessage());
            }

        }
    }//GEN-LAST:event_btnCustomerActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnCustomer;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JTextField edCustomer;
    private javax.swing.JTextField edFilter;
    private javax.swing.JTextArea edKeterangan;
    private javax.swing.JFormattedTextField edNilaiPekerjaan;
    private javax.swing.JTextField edNoInvoice;
    private javax.swing.JFormattedTextField edPPN;
    private javax.swing.JTextArea edPekerjaan;
    private com.toedter.calendar.JDateChooser edTanggal;
    private com.toedter.calendar.JDateChooser edTglAkhir;
    private com.toedter.calendar.JDateChooser edTglAwal;
    private javax.swing.JFormattedTextField edTotal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JPanel pnlFilter;
    private javax.swing.JPanel pnlNextPrev;
    private javax.swing.JTable tblPembayaranKasBon;
    // End of variables declaration//GEN-END:variables
    
    private void setStatusTombol(String mode){
        if (mode == "awal"){
            btnNew.setEnabled(true);
            btnEdit.setEnabled(false);
            btnSimpan.setEnabled(false);
            btnBatal.setEnabled(false);
            btnHapus.setEnabled(false);
            
            SetEnableKomponenInput(false);
        } else if (mode == "tambah"){
            btnNew.setEnabled(false);
            btnEdit.setEnabled(false);
            btnSimpan.setEnabled(true);
            btnBatal.setEnabled(true);
            btnHapus.setEnabled(false);
            SetEnableKomponenInput(true);
        } else if (mode == "edit"){
            btnNew.setEnabled(false);
            btnEdit.setEnabled(false);
            btnSimpan.setEnabled(true);
            btnBatal.setEnabled(true);
            btnHapus.setEnabled(true);
            SetEnableKomponenInput(true);
        } else if (mode == "selected"){
            btnNew.setEnabled(true);
            btnEdit.setEnabled(true);
            btnSimpan.setEnabled(false);
            btnBatal.setEnabled(false);
            btnHapus.setEnabled(true);
            SetEnableKomponenInput(false);
        }
    }
    
    private void SetEnableKomponenInput(boolean enable) {
        edCustomer.setEnabled(enable);
        btnCustomer.setEnabled(enable);
        edTanggal.setEnabled(enable);
        edPekerjaan.setEnabled(enable);
        edNilaiPekerjaan.setEnabled(enable);
        edPPN.setEnabled(enable);
        edKeterangan.setEnabled(enable);
    }

    private boolean validasiInput() {
        if (customer == null){
            AppUtils.showWarningDialog("Customer belum dipilih");
            edCustomer.requestFocus();
            return false;
        }
        
        if (edPekerjaan.getText().equals("")){
            AppUtils.showWarningDialog("Nama pekerjaan belum diisi");
            edPekerjaan.requestFocus();
        }
        
        int nilaiPekerjaan = ((Number) edNilaiPekerjaan.getValue()).intValue();
        int PPN = ((Number) edPPN.getValue()).intValue();
        
        if (nilaiPekerjaan <= 0){
            AppUtils.showWarningDialog("Nilai pekerjaan belum diisi");
            edNilaiPekerjaan.requestFocus();
            return false;
        }
        
        if (PPN <= 0){
            AppUtils.showWarningDialog("Nilai PPN belum diisi");
            edPPN.requestFocus();
            return false;
        }
        
        return true;
    }
}

    
