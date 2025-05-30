package com.bprasojo.ekspedisi.model;

import com.bprasojo.ekspedisi.dao.JurnalDetailDAO;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class Jurnal {
    private int id;
    private String noJurnal;
    private Date tanggal;
    private int transaksiId;
    private String transaksi;
    private String keterangan;
    private String userCreate;
    private String userUpdate;

    private transient List<JurnalDetail> jurnalDetails; // Lazy loading

    public Jurnal() {}

    public Jurnal(int id, String noJurnal, Date tanggal, int idTransaksi, String transaksi, String keterangan, String userCreate, String userUpdate) {
        this.id = id;
        this.noJurnal = noJurnal;
        this.tanggal = tanggal;
        this.transaksiId = idTransaksi;
        this.transaksi = transaksi;
        this.keterangan = keterangan;
        this.userCreate = userCreate;
        this.userUpdate = userUpdate;
        
    }

    // Getter & Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNoJurnal() { return noJurnal; }
    public void setNoJurnal(String noJurnal) { this.noJurnal = noJurnal; }

    public Date getTanggal() { return tanggal; }
    public void setTanggal(Date tanggal) { this.tanggal = tanggal; }

    public int getTransaksiId() { return transaksiId; }
    public void setTransaksiId(int transaksiId) { this.transaksiId = transaksiId; }

    public String getTransaksi() { return transaksi; }
    public void setTransaksi(String transaksi) { this.transaksi = transaksi; }
    
    public String getKeterangan() { return keterangan; }
    public void setKeterangan(String keterangan) { this.keterangan = keterangan; }

    // Lazy Load JurnalDetails
    
    public int getDebet() throws SQLException {
        List<JurnalDetail> list = getJurnalDetails();
        
        int totalDebet = 0;
        for (JurnalDetail jurnalDetail : list) {
            totalDebet += jurnalDetail.getDebet(); // Misalnya getDebet() adalah method di JurnalDetail
        }

        return totalDebet;
    }
    
    public int getKredit() throws SQLException {
        List<JurnalDetail> list = getJurnalDetails();
        
        int totalKredit = 0;
        for (JurnalDetail jurnalDetail : list) {
            totalKredit += jurnalDetail.getKredit(); // Misalnya getDebet() adalah method di JurnalDetail
        }

        return totalKredit;
    }
    public List<JurnalDetail> getJurnalDetails() throws SQLException {
        if (jurnalDetails == null) {
            JurnalDetailDAO detailDAO = new JurnalDetailDAO();
            jurnalDetails = detailDAO.getByJurnalId(this.getId());
        }
        return jurnalDetails;
    }
    
    public String getUserCreate() { return userCreate; }
    public void setUserCreate(String userCreate) { this.userCreate = userCreate; }
    
    public String getUserUpdate() { return userUpdate; }
    public void setUserUpdate(String userUpdate) { this.userUpdate = userUpdate; }
}
