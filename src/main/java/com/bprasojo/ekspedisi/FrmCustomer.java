/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.bprasojo.ekspedisi;

import com.bprasojo.ekspedisi.dao.StakeHolderDAO;
import com.bprasojo.ekspedisi.model.StakeHolder;
import com.bprasojo.ekspedisi.utils.AppUtils;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author USER
 */
public class FrmCustomer extends javax.swing.JInternalFrame {

    /**
     * Creates new form FrmArmada
     */
    
    private int currentPage = 1;
    
    private StakeHolder stakeHolder = null;
    private final StakeHolderDAO stakeHolderDAO = new StakeHolderDAO();
    private DefaultTableModel tableModel;
    private boolean SilakanLoadData = false;
    private String jenis;
    
    
    public FrmCustomer() {
        initComponents();
        
        tableModel = new DefaultTableModel(new String[]{"Id","Kode","Nama","Alamat","No KTP","Lokasi Kerja"}, 0);
        tblStakeHolder.setModel(tableModel);
        tblStakeHolder.removeColumn(tblStakeHolder.getColumnModel().getColumn(0));
        
        setStatusTombol("awal");
        SilakanLoadData = true;
        
        inisialisasiEventTableModel();
        
        
        
    }

    public void KonfigurasiForm(){
        if (this.jenis != null && this.jenis.equals("Customer")){
            edLokasiKerja.setVisible(false);
            lblLokasiKerja.setVisible(false);
        }
    }
    public void setJenis(String jenis){
        this.jenis = jenis;
    }
    public void loadDataStakeHolder(int page) {
        try {
            List<StakeHolder> stakeHolderList = stakeHolderDAO.getStakeHolderByPage(page, edSearch.getText(), this.jenis);
            tableModel.setRowCount(0); // Bersihkan tabel

            for (StakeHolder stakeHolder : stakeHolderList) {
                tableModel.addRow(new Object[]{
                        stakeHolder.getId(),
                        stakeHolder.getKode(),
                        stakeHolder.getNama(),
                        stakeHolder.getAlamat(),
                        stakeHolder.getNoKtp(),
                        stakeHolder.getLokasiKerja()
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
        pnlButton = new javax.swing.JPanel();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblStakeHolder = new javax.swing.JTable();
        pnlInput = new javax.swing.JPanel();
        edNama = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        edAlamat = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        edNoKTP = new javax.swing.JTextField();
        lblLokasiKerja = new javax.swing.JLabel();
        edLokasiKerja = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

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

        javax.swing.GroupLayout pnlSearchLayout = new javax.swing.GroupLayout(pnlSearch);
        pnlSearch.setLayout(pnlSearchLayout);
        pnlSearchLayout.setHorizontalGroup(
            pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSearchLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(edSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(430, Short.MAX_VALUE))
        );
        pnlSearchLayout.setVerticalGroup(
            pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSearchLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(edSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addContainerGap(529, Short.MAX_VALUE)
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

        tblStakeHolder.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblStakeHolder);
        tblStakeHolder.getAccessibleContext().setAccessibleParent(tblStakeHolder);

        pnlData.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        pnlDua.add(pnlData, java.awt.BorderLayout.CENTER);

        pnlInput.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Alamat");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("No KTP");

        lblLokasiKerja.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblLokasiKerja.setText("Lokasi Kerja");

        edLokasiKerja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edLokasiKerjaActionPerformed(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Nama");

        javax.swing.GroupLayout pnlInputLayout = new javax.swing.GroupLayout(pnlInput);
        pnlInput.setLayout(pnlInputLayout);
        pnlInputLayout.setHorizontalGroup(
            pnlInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInputLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(pnlInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlInputLayout.createSequentialGroup()
                        .addComponent(lblLokasiKerja, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(edLokasiKerja, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlInputLayout.createSequentialGroup()
                        .addGroup(pnlInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(edNama, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(edAlamat, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(edNoKTP, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(290, Short.MAX_VALUE))
        );
        pnlInputLayout.setVerticalGroup(
            pnlInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInputLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(edNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(edAlamat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(edNoKTP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(edLokasiKerja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblLokasiKerja))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlDua.add(pnlInput, java.awt.BorderLayout.NORTH);

        getContentPane().add(pnlDua, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        stakeHolder = new StakeHolder();
        
        edNama.setText("");
        edAlamat.setText("");
        edNoKTP.setText("");
        edLokasiKerja.setText("");
//        edKota.setText("");
//        edTelp.setText("");
        
        setStatusTombol("tambah");
        loadDataStakeHolder(currentPage);
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
            btnEdit.setEnabled(true);
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
            stakeHolder.setNama(edNama.getText());
            stakeHolder.setAlamat(edAlamat.getText());
            stakeHolder.setLokasiKerja(edLokasiKerja.getText());
            stakeHolder.setJenis(this.jenis);
            stakeHolder.setNoKtp(edNoKTP.getText());
//            stakeHolder.setTelp(telp);

            stakeHolderDAO.save(stakeHolder);
            AppUtils.showInfoDialog("Data berhasil disimpan");
            loadDataStakeHolder(currentPage);
        } catch (SQLException ex) {
            AppUtils.showErrorDialog("Gagal simpan data dengan error : \n" + ex.getMessage());
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void edLokasiKerjaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edLokasiKerjaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_edLokasiKerjaActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        setStatusTombol("edit");
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        currentPage++;
        loadDataStakeHolder(currentPage);
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        if (currentPage > 1) {
            currentPage--;
            loadDataStakeHolder(currentPage);
        }
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        setStatusTombol("awal");
    }//GEN-LAST:event_btnBatalActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        boolean userConfirmed = AppUtils.showConfirmDialog("Apakah Anda yakin akan menghapus data?");

        if (userConfirmed) {
            try {
                stakeHolderDAO.delete(stakeHolder.getId());
                setStatusTombol("awal");
                loadDataStakeHolder(currentPage);
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
            loadDataStakeHolder(currentPage);
        }
    }//GEN-LAST:event_edSearchKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JTextField edAlamat;
    private javax.swing.JTextField edLokasiKerja;
    private javax.swing.JTextField edNama;
    private javax.swing.JTextField edNoKTP;
    private javax.swing.JTextField edSearch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lblLokasiKerja;
    private javax.swing.JPanel pnlButton;
    private javax.swing.JPanel pnlData;
    private javax.swing.JPanel pnlDua;
    private javax.swing.JPanel pnlInput;
    private javax.swing.JPanel pnlSearch;
    private javax.swing.JTable tblStakeHolder;
    // End of variables declaration//GEN-END:variables
    
    private void SetEnableKomponenInput(boolean enable){
        edNama.setEnabled(enable);
        edAlamat.setEnabled(enable);
        edAlamat.setEnabled(enable);
//        edKota.setEnabled(enable);
        edNoKTP.setEnabled(enable);
//        edTelp.setEnabled(enable);
        edLokasiKerja.setEnabled(enable);
    }
    
    private void inisialisasiEventTableModel() {
        tblStakeHolder.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // Memeriksa apakah ada baris yang dipilih
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = tblStakeHolder.getSelectedRow(); // Mendapatkan baris yang dipilih
                    if (selectedRow != -1) {
                        
                        int id = (Integer) tblStakeHolder.getModel().getValueAt(tblStakeHolder.getSelectedRow(),0);
//                        Integer id = (Integer) tblStakeHolder.getValueAt(selectedRow, 0);
                        LoadStakeHolder(id);
                        setStatusTombol("selected");
                    }
                }
            }

            private void LoadStakeHolder(Integer id) {
                try {
                    stakeHolder = stakeHolderDAO.getById(id);
                    if (stakeHolder != null){
                        edLokasiKerja.setText(stakeHolder.getLokasiKerja());
                        edAlamat.setText(stakeHolder.getAlamat());
                        edNama.setText(stakeHolder.getNama());
                        edNoKTP.setText(stakeHolder.getNoKtp());
//                        edTelp.setText(stakeHolder.getTelp());                        
                    }
                } catch (SQLException ex) {
                    AppUtils.showWarningDialog("Ada kesalahan load data dengan pesan : \n" + ex.getMessage());
                }
            }
        });
    }

    private boolean validasiInput() {
        if (edNama.getText().equals("")){
            AppUtils.showWarningDialog("No Polisi belum diisi");
            edNama.requestFocus();
            return false;
        }
        
        if (edAlamat.getText().equals("")){
            AppUtils.showWarningDialog("Kendaraan belum diisi");
            edAlamat.requestFocus();
            return false;
        }
        
        if (edNoKTP.getText().equals("")){
            AppUtils.showWarningDialog("Pemilik belum diisi");
            edNoKTP.requestFocus();
            return false;
        }
        
        return true;
    }

}
