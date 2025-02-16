package com.bprasojo.ekspedisi.model;

import com.bprasojo.ekspedisi.dao.JurnalDetailDAO;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class Jurnal {
    private int id;
    private String noJurnal;
    private Date tanggal;
    private int idTransaksi;
    private String transaksi;
    private String user;

    private transient List<JurnalDetail> jurnalDetails; // Lazy loading

    public Jurnal() {}

    public Jurnal(int id, String noJurnal, Date tanggal, int idTransaksi, String transaksi, String user) {
        this.id = id;
        this.noJurnal = noJurnal;
        this.tanggal = tanggal;
        this.idTransaksi = idTransaksi;
        this.transaksi = transaksi;
        this.user = user;
    }

    // Getter & Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNoJurnal() { return noJurnal; }
    public void setNoJurnal(String noJurnal) { this.noJurnal = noJurnal; }

    public Date getTanggal() { return tanggal; }
    public void setTanggal(Date tanggal) { this.tanggal = tanggal; }

    public int getIdTransaksi() { return idTransaksi; }
    public void setIdTransaksi(int idTransaksi) { this.idTransaksi = idTransaksi; }

    public String getTransaksi() { return transaksi; }
    public void setTransaksi(String transaksi) { this.transaksi = transaksi; }

    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }

    // Lazy Load JurnalDetails
    public List<JurnalDetail> getJurnalDetails() throws SQLException {
        if (jurnalDetails == null) {
            JurnalDetailDAO detailDAO = new JurnalDetailDAO();
            jurnalDetails = detailDAO.getByJurnalId(this.getId());
        }
        return jurnalDetails;
    }
}
