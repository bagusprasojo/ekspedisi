/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bprasojo.ekspedisi.model;

/**
 *
 */
import com.bprasojo.ekspedisi.dao.PerkiraanDAO;
import com.bprasojo.ekspedisi.dao.StakeHolderDAO;
import java.util.Date;
import java.sql.SQLException;

public class TagihanCustomer {
    private int id;
    private int customerId;
    private String noInvoice;
    private Date tanggal;
    private String pekerjaan;
    private int nilaiPekerjaan;
    private int ppnPersen;
    private int ppn;
//    private int total;
    private String terbilang;
    private int pelunasan;
    private String statusLunas;
    private String keterangan;
    private int perkiraanPiutangId;
    private String userCreate;
    private String userUpdate;
    
    private transient StakeHolder customer;
    private transient Perkiraan perkiraanPiutang;

    // Constructor
    public TagihanCustomer() {}

    public TagihanCustomer(int id, int customerId, String noInvoice, Date tanggal, String pekerjaan,
                           int nilaiPekerjaan, int ppnPersen, int ppn, 
                           String terbilang, int pelunasan, String statusLunas, String keterangan, int perkiraanPiutangId, String userCreate, String userUpdate) {
        this.id = id;
        this.customerId = customerId;
        this.noInvoice = noInvoice;
        this.tanggal = tanggal;
        this.pekerjaan = pekerjaan;
        this.nilaiPekerjaan = nilaiPekerjaan;
        this.ppnPersen = ppnPersen;
        this.ppn = ppn;
//        this.total = total;
        this.terbilang = terbilang;
        this.pelunasan = pelunasan;
        this.statusLunas = statusLunas;
        this.keterangan = keterangan;
        this.perkiraanPiutangId = perkiraanPiutangId;
        
        this.customer = null;
        this.perkiraanPiutang = null;
        this.userCreate = userCreate;
        this.userUpdate = userUpdate;
    }

    // Getter & Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; customer = null;}

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { 
        this.customerId = customerId; 
        customer = null;
    }
    
    public int getPerkiraanPiutangId() { return perkiraanPiutangId; }
    public void setPerkiraanPiutangId(int perkiraanPiutangId) { 
        this.perkiraanPiutangId = perkiraanPiutangId; 
        perkiraanPiutang = null;
    }

    public String getNoInvoice() { return noInvoice; }
    public void setNoInvoice(String noInvoice) { this.noInvoice = noInvoice; }
    
    public String getKeterangan() { return keterangan; }
    public void setKeterangan(String keterangan) { this.keterangan = keterangan; }

    public Date getTanggal() { return tanggal; }
    public void setTanggal(Date tanggal) { this.tanggal = tanggal; }

    public String getPekerjaan() { return pekerjaan; }
    public void setPekerjaan(String pekerjaan) { this.pekerjaan = pekerjaan; }

    public int getNilaiPekerjaan() { return nilaiPekerjaan; }
    public void setNilaiPekerjaan(int nilaiPekerjaan) { this.nilaiPekerjaan = nilaiPekerjaan; }

    public int getPpnPersen() { return ppnPersen; }
    public void setPpnPersen(int ppnPersen) { this.ppnPersen = ppnPersen; }

    public int getPpn() { return ppn; }
    public void setPpn(int ppn) { this.ppn = ppn; }

    public int getTotal() { 
        return nilaiPekerjaan + ppn;
    }
    
    public String getTerbilang() { return terbilang; }
    public void setTerbilang(String terbilang) { this.terbilang = terbilang; }

    public int getPelunasan() { return pelunasan; }
    public void setPelunasan(int pelunasan) { this.pelunasan = pelunasan; }

    public String getStatusLunas() { return statusLunas; }
    public void setStatusLunas(String statusLunas) { this.statusLunas = statusLunas; }
    
    public StakeHolder getCustomer() throws SQLException{
        if (customer == null) {
            StakeHolderDAO stakeHolderDAO = new StakeHolderDAO();
            customer = stakeHolderDAO.getById(this.customerId);
        }
        return customer;
    }
    
    public Perkiraan getPerkiraanPiutang() throws SQLException{
        if (perkiraanPiutang == null) {
            PerkiraanDAO perkiraanDAO = new PerkiraanDAO();
            perkiraanPiutang = perkiraanDAO.getById(this.perkiraanPiutangId);
        }
        return perkiraanPiutang;
    }
    
    public Integer getSaldo(){
        return getTotal() - pelunasan;
    }
    
    public String getUserCreate() { return userCreate; }
    public void setUserCreate(String userCreate) { this.userCreate = userCreate; }
    
    public String getUserUpdate() { return userUpdate; }
    public void setUserUpdate(String userUpdate) { this.userUpdate = userUpdate; }
}

