/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.bprasojo.ekspedisi;

import com.bprasojo.ekspedisi.dao.ArmadaDAO;
import com.bprasojo.ekspedisi.model.Armada;
import com.bprasojo.ekspedisi.utils.AppUtils;
import com.bprasojo.ekspedisi.utils.LookupForm;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;

/**
 *
 */
public class FrmRptRiwayatPembelianBBM extends FrmDefault {

    private ArmadaDAO armadaDAO;
    private Armada armada;

    /**
     * Creates new form FrmRptRiwayatPembelianBBM
     */
    public FrmRptRiwayatPembelianBBM() {
        super();
        initComponents();
        
        armadaDAO = new ArmadaDAO();
        
        edNoPolisi.setText("");
        edKendaraan.setText("");
        edPemilik.setText("");
        
        AppUtils.SetTanggalAwalBulan(edTglAwal);
        AppUtils.SetTanggalAkhirBulan(edTglAkhir);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        edTglAwal = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        edTglAkhir = new com.toedter.calendar.JDateChooser();
        btnTampilkan = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        edNoPolisi = new javax.swing.JTextField();
        btnNoPolisi = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        edKendaraan = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        edPemilik = new javax.swing.JTextField();

        setClosable(true);
        setTitle("Riwayat Pembelian BBM");

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Periode");

        jLabel2.setText("s.d.");

        btnTampilkan.setText("Tampilkan");
        btnTampilkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTampilkanActionPerformed(evt);
            }
        });

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("No Polisi");

        btnNoPolisi.setText("...");
        btnNoPolisi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNoPolisiActionPerformed(evt);
            }
        });

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Kendaraan");

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Pemilik");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(edKendaraan)
                        .addComponent(edPemilik, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(edNoPolisi, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNoPolisi, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(edTglAwal, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(edTglAkhir, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTampilkan)))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(edNoPolisi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNoPolisi))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(edKendaraan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(edPemilik, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(edTglAwal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(edTglAkhir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTampilkan)
                    .addComponent(jLabel1))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTampilkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTampilkanActionPerformed
        InputStream jasperStream = getClass().getClassLoader().getResourceAsStream("reports/RiwayatPembelianBBM.jasper");

        //        params.put("p_periode", periode);

        try {
            Map<String, Object> params = new HashMap<>();
            params.put("p_nopolisi", armada.getNopol());
            params.put("p_kendaraan", armada.getKendaraan());
            params.put("p_driver", armada.getDriver().getNama());
            params.put("p_tglawal", edTglAwal.getDate());
            params.put("p_tglakhir", edTglAkhir.getDate());
            params.put("REPORT_LOGO", "reports/logo.png");

            AppUtils.showReport(jasperStream, params);
        } catch (JRException ex) {
            AppUtils.showErrorDialog("Gagal generate laporan dengan error:\n" + ex.getMessage());
        } catch (SQLException ex) {
            AppUtils.showErrorDialog("Gagal generate laporan dengan error:\n" + ex.getMessage());
        } catch (Exception ex) {
            AppUtils.showErrorDialog("Gagal generate laporan dengan error:\n" + ex.getMessage());
        }
    }//GEN-LAST:event_btnTampilkanActionPerformed

    private void btnNoPolisiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNoPolisiActionPerformed
        String sqlQuery = "select a.nopol, a.kendaraan, a.pemilik from armada a";
        LookupForm lookupForm = new LookupForm(this, sqlQuery, true);
        Map<String, Object> selectedRecord = lookupForm.getSelectedRecord();
        if (selectedRecord != null) {
            try {
                // Mengambil nilai dengan nama kolom
                String nopol = selectedRecord.get("nopol").toString();
                armada = armadaDAO.getArmadaByNoPol(nopol);
                
                if (armada != null){
                    edNoPolisi.setText(armada.getNopol());
                    edKendaraan.setText(armada.getKendaraan());
                    edPemilik.setText(armada.getPemilik());
                }
            } catch (SQLException ex) {
                Logger.getLogger(FrmTransaksiKas.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } else {
            JOptionPane.showMessageDialog(this, "Tidak ada data yang dipilih.");
        }
    }//GEN-LAST:event_btnNoPolisiActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNoPolisi;
    private javax.swing.JButton btnTampilkan;
    private javax.swing.JTextField edKendaraan;
    private javax.swing.JTextField edNoPolisi;
    private javax.swing.JTextField edPemilik;
    private com.toedter.calendar.JDateChooser edTglAkhir;
    private com.toedter.calendar.JDateChooser edTglAwal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    // End of variables declaration//GEN-END:variables
}
