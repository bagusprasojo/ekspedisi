/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bprasojo.ekspedisi.model;

/**
 *
 */
import com.bprasojo.ekspedisi.dao.BankDAO;
import com.bprasojo.ekspedisi.dao.KasBonKaryawanDAO;
import com.bprasojo.ekspedisi.dao.PerkiraanDAO;
import java.util.Date;
import java.sql.SQLException;

public class PembayaranKasBon extends BaseClass{
    private int id;
    private Date tanggal;
    private int kasBonKaryawanId;
    private int perkiraanKasId;
    private int nominal;
    private String keterangan;
    private int bankId;
    private String sumberDana;
    private String noRegister;
    private String userCreate;
    private String userUpdate;

    private transient Perkiraan perkiraanKas; // Lazy loading
    private transient Bank bank; // Lazy loading
    private transient KasBonKaryawan kasBonKaryawan;

    public PembayaranKasBon() {}

    public PembayaranKasBon(int id, Date tanggal, int kasBonKaryawanId,int perkiraanKasId, int nominal, String keterangan, int bankId, String sumberDana, String noRegister, String userCreate, String userUpdate) {
        this.id = id;
        this.tanggal = tanggal;
        this.kasBonKaryawanId = kasBonKaryawanId;
        this.perkiraanKasId = perkiraanKasId;
        this.nominal = nominal;
        this.keterangan = keterangan;
        this.bankId = bankId;
        this.sumberDana = sumberDana;
        this.noRegister = noRegister;
        this.userCreate = userCreate;
        this.userUpdate = userUpdate;
    }

    // Getter & Setter
    @Override
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    
    public Date getTanggal() { return tanggal; }
    public void setTanggal(Date tanggal) { this.tanggal = tanggal; }

    public String getNoRegister() { return noRegister; }
    public void setNoRegister(String noRegister) { this.noRegister = noRegister; }
    
    public int getKasBonKaryawanId() { return kasBonKaryawanId; }
    public void setKasBonKaryawanId(int kasBonKaryawanId) { 
        this.kasBonKaryawanId = kasBonKaryawanId; 
        this.kasBonKaryawan = null;
    }
    
    public int getPerkiraanKasId() { return perkiraanKasId; }
    public void setPerkiraanKasId(int perkiraanKasId) { 
        this.perkiraanKasId = perkiraanKasId; 
        this.perkiraanKas = null; 
    }

    public int getNominal() { return nominal; }
    public void setNominal(int nominal) { this.nominal = nominal; }

    public String getKeterangan() { return keterangan; }
    public void setKeterangan(String keterangan) { this.keterangan = keterangan; }
    
    public String getSumberDana() { return sumberDana; }
    public void setSumberDana(String sumberDana) { this.sumberDana = sumberDana; }

    public int getBankId() { return bankId; }
    public void setBankId(int bankId) {
        this.bankId = bankId; 
        this.bank = null;
    }

    // Lazy Loading PerkiraanPinjaman
    public KasBonKaryawan getKasBonKaryawan() throws SQLException {
        if (kasBonKaryawan == null) {
            KasBonKaryawanDAO kasBonKaryawanDAO = new KasBonKaryawanDAO();
            kasBonKaryawan = kasBonKaryawanDAO.getById(this.kasBonKaryawanId);
        }
        return kasBonKaryawan;
    }
    
    public Perkiraan getPerkiraanKas() throws SQLException {
        if (perkiraanKas == null) {
            PerkiraanDAO perkiraanDAO = new PerkiraanDAO();
            perkiraanKas = perkiraanDAO.getById(this.perkiraanKasId);
        }
        return perkiraanKas;
    }

    // Lazy Loading Bank
    public Bank getBank() throws SQLException {
        if (bank == null) {
            BankDAO bankDAO = new BankDAO();
            bank = bankDAO.getById(this.bankId);
        }
        return bank;
    }
    
    public String getUserCreate() { return userCreate; }
    public void setUserCreate(String userCreate) { this.userCreate = userCreate; }
    
    public String getUserUpdate() { return userUpdate; }
    public void setUserUpdate(String userUpdate) { this.userUpdate = userUpdate; }
}

