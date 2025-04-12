/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.bprasojo.ekspedisi;

import com.bprasojo.ekspedisi.dao.JurnalDAO;
import com.bprasojo.ekspedisi.dao.PerkiraanDAO;
import com.bprasojo.ekspedisi.model.Jurnal;
import com.bprasojo.ekspedisi.model.JurnalDetail;
import com.bprasojo.ekspedisi.model.Perkiraan;
import com.bprasojo.ekspedisi.model.User;
import com.bprasojo.ekspedisi.utils.AppUtils;
import com.bprasojo.ekspedisi.utils.CustomFocusTraversalPolicy;
import com.bprasojo.ekspedisi.utils.LookupForm;
import java.sql.SQLException;
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
 * @author USER
 */
public class FrmJurnalPenyesuaian extends FrmDefault {

    /**
     * Creates new form FrmJurnalPenyesuaian
     */
    
    private User user;
    private Jurnal jurnal;
    private JurnalDAO jurnalDAO;
    private Perkiraan perkiraanDebet;
    private Perkiraan perkiraanKredit;
    private PerkiraanDAO perkiraanDAO;
    
    private int currentPage = 1;
    private boolean SilakanLoadData = false;
    private DefaultTableModel tableModel;
    
    public FrmJurnalPenyesuaian(User user) {
        this();
        this.user = user;
    }
    public FrmJurnalPenyesuaian() {
        super();
        initComponents();
        
        jurnalDAO = new JurnalDAO();
        perkiraanDAO = new PerkiraanDAO();
        
        AppUtils.SetTanggalAwalBulan(edTglAwal);
        AppUtils.SetTanggalAkhirBulan(edTglAkhir);
        
        InisialisasiTableJurnal();        
        
        SilakanLoadData = true;
        LoadDataJurnal(currentPage);
        setStatusTombol("awal");        
        
        inisialisasiEventTableModel();        
        
        pnlInput.setFocusTraversalPolicy(new CustomFocusTraversalPolicy(edTanggal, btnAkunDebet, btnAkunKredit, edNominal, edKeterangan));
    }

    private void inisialisasiEventTableModel() {
        tblJurnal.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // Memeriksa apakah ada baris yang dipilih
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = tblJurnal.getSelectedRow(); // Mendapatkan baris yang dipilih
                    if (selectedRow != -1) {
                        Integer id = (Integer) tblJurnal.getModel().getValueAt(selectedRow, 0);
                        LoadJurnal(id);
                        setStatusTombol("selected");
                    }
                }
            }

            private void LoadJurnal(Integer id) {
                try {
                    jurnal = jurnalDAO.getById(id);
                    if (jurnal != null){
                        edNoBukti.setText(jurnal.getNoJurnal());
                        edTanggal.setDate(jurnal.getTanggal());
                        
                        int nominal = 0;
                        if (jurnal.getJurnalDetails().size() > 0){
                            if (jurnal.getJurnalDetails().get(0).getDebet() > 0){
                                perkiraanDebet = perkiraanDAO.getById(jurnal.getJurnalDetails().get(0).getPerkiraanId());
                                nominal = jurnal.getJurnalDetails().get(0).getDebet();
                            } else {
                                perkiraanKredit = perkiraanDAO.getById(jurnal.getJurnalDetails().get(0).getPerkiraanId());
                                nominal = jurnal.getJurnalDetails().get(0).getKredit();
                            }

                            if (jurnal.getJurnalDetails().get(1).getDebet() > 0){
                                perkiraanDebet = perkiraanDAO.getById(jurnal.getJurnalDetails().get(1).getPerkiraanId());
                            } else {
                                perkiraanKredit = perkiraanDAO.getById(jurnal.getJurnalDetails().get(1).getPerkiraanId());
                            }
                        
                        
                            edKodeAkunDebet.setText(perkiraanDebet.getKode());
                            edNamaAkunDebet.setText(perkiraanDebet.getNama());

                            edKodeAkunKredit.setText(perkiraanKredit.getKode());
                            edNamaAkunKredit.setText(perkiraanKredit.getNama());

                            edNominal.setValue(nominal);
                        }
                        edKeterangan.setText(jurnal.getKeterangan());
                        
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(FrmJurnalPenyesuaian.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
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
        btnJurnal = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        pnlInput = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        edNoBukti = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnAkunKredit = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        edNominal = new javax.swing.JFormattedTextField();
        edKodeAkunDebet = new javax.swing.JTextField();
        btnAkunDebet = new javax.swing.JButton();
        edNamaAkunDebet = new javax.swing.JTextField();
        edNamaAkunKredit = new javax.swing.JTextField();
        edTanggal = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        edKeterangan = new javax.swing.JTextField();
        edKodeAkunKredit = new javax.swing.JTextField();
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
        tblJurnal = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Jurnal Penyesuaian");

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

        btnJurnal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/jurnal.png"))); // NOI18N
        btnJurnal.setText("Jurnal");
        btnJurnal.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnJurnal.setFocusable(false);
        btnJurnal.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnJurnal.setMaximumSize(new java.awt.Dimension(60, 70));
        btnJurnal.setMinimumSize(new java.awt.Dimension(60, 70));
        btnJurnal.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnJurnal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJurnalActionPerformed(evt);
            }
        });
        jToolBar1.add(btnJurnal);

        getContentPane().add(jToolBar1, java.awt.BorderLayout.NORTH);

        jPanel1.setLayout(new java.awt.BorderLayout());

        pnlInput.setFocusCycleRoot(true);

        jLabel3.setText("No Bukti");

        edNoBukti.setEditable(false);
        edNoBukti.setEnabled(false);

        jLabel4.setText("Tanggal");

        jLabel5.setText("Akun Debet");

        jLabel6.setText("Akun Kredit");

        btnAkunKredit.setText("...");
        btnAkunKredit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAkunKreditActionPerformed(evt);
            }
        });

        jLabel7.setText("Nominal");

        edNominal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        btnAkunDebet.setText("...");
        btnAkunDebet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAkunDebetActionPerformed(evt);
            }
        });

        edNamaAkunDebet.setEditable(false);
        edNamaAkunDebet.setEnabled(false);

        edNamaAkunKredit.setEditable(false);
        edNamaAkunKredit.setEnabled(false);

        jLabel8.setText("Keterangan");

        edKodeAkunKredit.setEnabled(false);

        javax.swing.GroupLayout pnlInputLayout = new javax.swing.GroupLayout(pnlInput);
        pnlInput.setLayout(pnlInputLayout);
        pnlInputLayout.setHorizontalGroup(
            pnlInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInputLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(pnlInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(edKeterangan, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                    .addGroup(pnlInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(edKodeAkunDebet, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(edTanggal, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(edNoBukti, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(edNominal, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(edKodeAkunKredit))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlInputLayout.createSequentialGroup()
                        .addComponent(btnAkunKredit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(edNamaAkunKredit))
                    .addGroup(pnlInputLayout.createSequentialGroup()
                        .addComponent(btnAkunDebet)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(edNamaAkunDebet, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(10, Short.MAX_VALUE))
        );
        pnlInputLayout.setVerticalGroup(
            pnlInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInputLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(edNoBukti, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(edTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(edKodeAkunDebet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAkunDebet)
                    .addComponent(jLabel5)
                    .addComponent(edNamaAkunDebet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnAkunKredit)
                    .addComponent(jLabel6)
                    .addComponent(edNamaAkunKredit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(edKodeAkunKredit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(edNominal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(edKeterangan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(pnlInput, java.awt.BorderLayout.NORTH);

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
                .addContainerGap(548, Short.MAX_VALUE)
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

        tblJurnal.setModel(new javax.swing.table.DefaultTableModel(
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
        tblJurnal.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tblJurnalFocusGained(evt);
            }
        });
        jScrollPane1.setViewportView(tblJurnal);

        jPanel3.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void setStatusTombol(String mode){
        btnJurnal.setEnabled(false);
        if (null != mode)switch (mode) {
            case "awal" -> {
                btnNew.setEnabled(true);
                btnEdit.setEnabled(false);
                btnSimpan.setEnabled(false);
                btnBatal.setEnabled(false);
                btnHapus.setEnabled(false);
                SetEnableKomponenInput(false);
            }
            case "tambah" -> {
                btnNew.setEnabled(false);
                btnEdit.setEnabled(false);
                btnSimpan.setEnabled(true);
                btnBatal.setEnabled(true);
                btnHapus.setEnabled(false);
                SetEnableKomponenInput(true);
            }
            case "edit" -> {
                btnNew.setEnabled(false);
                btnEdit.setEnabled(false);
                btnSimpan.setEnabled(true);
                btnBatal.setEnabled(true);
                btnHapus.setEnabled(true);
                btnJurnal.setEnabled(true);
                SetEnableKomponenInput(true);
            }
            case "selected" -> {
                btnNew.setEnabled(true);
                btnEdit.setEnabled(true);
                btnSimpan.setEnabled(false);
                btnBatal.setEnabled(false);
                btnHapus.setEnabled(true);
                SetEnableKomponenInput(false);
            }
            default -> {
            }
        }
    }
    
    private void SetEnableKomponenInput(boolean enable) {
        
        edTanggal.setEnabled(enable);
        edKodeAkunDebet.setEnabled(enable);
        edKodeAkunKredit.setEnabled(enable);
        edNominal.setEnabled(enable);
        edKeterangan.setEnabled(enable);
        
        btnAkunDebet.setEnabled(enable);
        btnAkunKredit.setEnabled(enable);
        
    }
    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        jurnal = new Jurnal();
        
        edNoBukti.setText("Otomatis");
        AppUtils.setDefaultValues(edTanggal, edKodeAkunDebet, edKodeAkunKredit, edNamaAkunDebet, edNamaAkunKredit, edNominal, edKeterangan);

        setStatusTombol("tambah");
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        setStatusTombol("edit");
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        try {
            
            if (validasiInput() == false){
                return;
            }
        
            jurnal.setTanggal(edTanggal.getDate());
            jurnal.setKeterangan(edKeterangan.getText());
            jurnal.setTransaksi("jurnal_memorial");
            jurnal.setUserCreate(user.getUsername());
            jurnal.setUserUpdate(user.getUsername());
            
            JurnalDetail jdDebet = new JurnalDetail();
            jdDebet.setDebet((int) edNominal.getValue());
            jdDebet.setKredit(0);
            jdDebet.setPerkiraanId(perkiraanDebet.getId());
            
            JurnalDetail jdKredit = new JurnalDetail();
            jdKredit.setDebet(0);
            jdKredit.setKredit((int) edNominal.getValue());
            jdKredit.setPerkiraanId(perkiraanKredit.getId());
            
            jurnal.getJurnalDetails().clear();
            jurnal.getJurnalDetails().add(jdDebet);
            jurnal.getJurnalDetails().add(jdKredit);
            
            jurnalDAO.save(jurnal);
            AppUtils.showInfoDialog("Data berhasil disimpan dengan no register : " + jurnal.getNoJurnal());
            LoadDataJurnal(currentPage);

            setStatusTombol("selected");

        } catch (SQLException ex) {
            AppUtils.showErrorDialog("Gagal menyimpan data dengan error :\n" + ex.getMessage());
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void LoadDataJurnal(int page){
        tableModel.setRowCount(0); // Bersihkan tabel
        
        List<Map<String, Object>> result = jurnalDAO.getJurnalByPage(currentPage, edTglAwal.getDate(), edTglAkhir.getDate(), edFilter.getText());
        for (Map<String, Object> row : result) {
            
            Number debet = 0;
            if (row.get("debet") != null){
                debet = (Number) row.get("debet");
            }

            Number kredit = 0;
            if (row.get("kredit") != null){
                kredit = (Number) row.get("debet");
            }

            tableModel.addRow(new Object[]{
                    (Integer) row.get("id"),
                    (String) row.get("no_jurnal"),
                    AppUtils.DateFormatShort((Date) row.get("tanggal")),
                    (String) row.get("keterangan"),
                    AppUtils.NumericFormat(debet),
                    AppUtils.NumericFormat(kredit),
                    (String) row.get("user_create")
            });
        }
        
        tblJurnal.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    }
    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        setStatusTombol("awal");
    }//GEN-LAST:event_btnBatalActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        if (jurnal == null){
            AppUtils.showWarningDialog("Tidak ada data yang dihapus");
            return;
        }

        boolean userConfirmed = AppUtils.showConfirmDialog("Apakah Anda yakin akan menghapus data?");

        if (userConfirmed) {
            try {
                jurnalDAO.delete(jurnal.getId());
                setStatusTombol("awal");
                LoadDataJurnal(currentPage);
            } catch (SQLException ex) {
                AppUtils.showErrorDialog("Ada kesalahan hapus data dengan error :\n" + ex.getMessage());
            }
        }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_btnKeluarActionPerformed

    private void btnJurnalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJurnalActionPerformed
//        if (kasBonKaryawan == null) {
//            AppUtils.showErrorDialog("Tidak ada transaksi yang akan dijurnal");
//            return;
//        }
//
//        try {
//            // Cek apakah transaksi sudah dijurnal
//            if (kasBonKaryawanDAO.isSudahJurnal(kasBonKaryawan.getNoRegister())) {
//                boolean userConfirmed = AppUtils.showConfirmDialog(
//                    "Transaksi ini sudah dijurnal. \nApakah anda yakin akan menjurnal ulang?");
//
//                if (!userConfirmed) return;
//            }
//
//            // Proses jurnal
//            Connection conn = kasBonKaryawanDAO.getConnection();
//            boolean previousAutoCommit = conn.getAutoCommit();
//            conn.setAutoCommit(false);
//
//            try {
//                kasBonKaryawanDAO.saveJurnal(kasBonKaryawan);
//                conn.commit();
//                setStatusTombol("selected");
//                AppUtils.showInfoDialog("Berhasil simpan jurnal");
//            } catch (SQLException ex) {
//                conn.rollback();
//                AppUtils.showErrorDialogSimpan(ex);
//            } finally {
//                conn.setAutoCommit(previousAutoCommit);
//            }
//        } catch (SQLException ex) {
//            AppUtils.showErrorDialogSimpan(ex);
//        }
    }//GEN-LAST:event_btnJurnalActionPerformed

    private void InisialisasiTableJurnal() {
        tableModel = new DefaultTableModel(new String[]{"ID","No Jurnal", "Tanggal", "Keterangan", "Debet", "Kredit", "User"}, 0);
        tblJurnal.setModel(tableModel);
        AppUtils.SetTableAligmentRight(tblJurnal, 4);
        AppUtils.SetTableAligmentRight(tblJurnal, 5);
        tblJurnal.removeColumn(tblJurnal.getColumnModel().getColumn(0));
        
    }
    private void edTglAwalPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_edTglAwalPropertyChange
        if (SilakanLoadData){
            currentPage = 1;
            LoadDataJurnal(currentPage);
        }
    }//GEN-LAST:event_edTglAwalPropertyChange

    private void edTglAkhirPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_edTglAkhirPropertyChange
        if (SilakanLoadData){
            currentPage = 1;
            LoadDataJurnal(currentPage);
        }
    }//GEN-LAST:event_edTglAkhirPropertyChange

    private void edFilterKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edFilterKeyReleased
        if (SilakanLoadData){
            currentPage = 1;
            LoadDataJurnal(currentPage);
        }
    }//GEN-LAST:event_edFilterKeyReleased

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        if (currentPage > 1) {
            currentPage--;
            LoadDataJurnal(currentPage);
        }
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        currentPage++;
        LoadDataJurnal(currentPage);
    }//GEN-LAST:event_btnNextActionPerformed

    private void tblJurnalFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tblJurnalFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_tblJurnalFocusGained

    private void btnAkunKreditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAkunKreditActionPerformed
        String sqlQuery = "SELECT " +
                            " concat(REPEAT(' ', level * 4), kode) as kode_akun, " +
                            " concat(REPEAT(' ', level * 4), nama) as nama_akun, id " +
                            " FROM perkiraan " +
                            " where nama not like '%KAS%'" +
                            " ORDER BY kode";
        
        LookupForm lookupForm = new LookupForm(this, sqlQuery, true);
        Map<String, Object> selectedRecord = lookupForm.getSelectedRecord();
        if (selectedRecord != null) {
            try {
                String kode = selectedRecord.get("kode_akun").toString().trim();
                perkiraanKredit = perkiraanDAO.getPerkiraanByKode(kode);
                edKodeAkunKredit.setText(perkiraanKredit.getKode());
                edNamaAkunKredit.setText(perkiraanKredit.getNama());
            } catch (SQLException ex) {
                AppUtils.showErrorDialog(ex.getMessage());
            }
            
        }
    }//GEN-LAST:event_btnAkunKreditActionPerformed

    private void btnAkunDebetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAkunDebetActionPerformed
        String sqlQuery = "SELECT " +
                            " concat(REPEAT(' ', level * 4), kode) as kode_akun, " +
                            " concat(REPEAT(' ', level * 4), nama) as nama_akun, id " +
                            " FROM perkiraan " +
                            " where nama not like '%KAS%'" +
                            " ORDER BY kode";
        
        LookupForm lookupForm = new LookupForm(this, sqlQuery, true);
        Map<String, Object> selectedRecord = lookupForm.getSelectedRecord();
        if (selectedRecord != null) {
            try {
                String kode = selectedRecord.get("kode_akun").toString().trim();
                perkiraanDebet = perkiraanDAO.getPerkiraanByKode(kode);
                edKodeAkunDebet.setText(perkiraanDebet.getKode());
                edNamaAkunDebet.setText(perkiraanDebet.getNama());
            } catch (SQLException ex) {
                AppUtils.showErrorDialog(ex.getMessage());
            }
            
        }
    }//GEN-LAST:event_btnAkunDebetActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAkunDebet;
    private javax.swing.JButton btnAkunKredit;
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnJurnal;
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JTextField edFilter;
    private javax.swing.JTextField edKeterangan;
    private javax.swing.JTextField edKodeAkunDebet;
    private javax.swing.JTextField edKodeAkunKredit;
    private javax.swing.JTextField edNamaAkunDebet;
    private javax.swing.JTextField edNamaAkunKredit;
    private javax.swing.JTextField edNoBukti;
    private javax.swing.JFormattedTextField edNominal;
    private com.toedter.calendar.JDateChooser edTanggal;
    private com.toedter.calendar.JDateChooser edTglAkhir;
    private com.toedter.calendar.JDateChooser edTglAwal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JPanel pnlFilter;
    private javax.swing.JPanel pnlInput;
    private javax.swing.JPanel pnlNextPrev;
    private javax.swing.JTable tblJurnal;
    // End of variables declaration//GEN-END:variables

    private boolean validasiInput() throws SQLException {
        if (jurnal == null){
            AppUtils.showWarningDialog("Tidak ada data yang disimpan");
            return false;
        }
        
        if (perkiraanDebet == null){
            AppUtils.showWarningDialog("Akun debet belum dipilih");
            btnAkunDebet.requestFocus();
            return false;
        }
        
        if (perkiraanDAO.isPunyaAnak(perkiraanDebet.getKode())){
            AppUtils.showErrorDialog("Akun " + perkiraanDebet.getKode() + " Tidak bisa digunakan untuk jurnal karena akun induk");
            btnAkunDebet.requestFocus();
            return false;
        }
        
        if (perkiraanKredit == null){
            AppUtils.showWarningDialog("Akun kredit belum dipilih");
            btnAkunKredit.requestFocus();
            return false;
        }
        
        if (perkiraanDAO.isPunyaAnak(perkiraanKredit.getKode())){
            AppUtils.showErrorDialog("Akun " + perkiraanKredit.getKode() + " Tidak bisa digunakan untuk jurnal karena akun induk");
            btnAkunKredit.requestFocus();
            return false;
        }
        
        if (edNominal.getValue() == null || ((Number) edNominal.getValue()).intValue() <= 0){
            AppUtils.showWarningDialog("Nominal belum diisi");
            edNominal.requestFocus();
            return false;
        }
        
        
        
        return true;
    }
}
