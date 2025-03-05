/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bprasojo.ekspedisi.model;

/**
 *
 */
import com.bprasojo.ekspedisi.dao.BankDAO;
import com.bprasojo.ekspedisi.dao.PerkiraanDAO;
import com.bprasojo.ekspedisi.dao.StakeHolderDAO;
import java.util.Date;
import java.sql.SQLException;

public class KasBonKaryawan extends BaseClass{
    private int id;
    private Date tanggal;
    private int perkiraanPinjamanId;
    private int perkiraanKasId;
    private int nominal;
    private String keterangan;
    private int bankId;
    private String sumberDana;
    private int pelunasan;
    private String statusLunas;
    private String noRegister;
    private int karyawanID;
    private String userCreate;
    private String userUpdate;
    

    private transient Perkiraan perkiraanPinjaman; // Lazy loading
    private transient Perkiraan perkiraanKas; // Lazy loading
    private transient Bank bank; // Lazy loading
    private transient StakeHolder karyawan;

    public KasBonKaryawan() {}

    public KasBonKaryawan(int id, Date tanggal, int karyawanId, int perkiraanPinjamanId,int perkiraanKasId, int nominal, String keterangan, int bankId, String sumberDana, int pelunasan, String statusLunas, String noRegister, String userCreate, String userUpdate) {
        this.id = id;
        this.tanggal = tanggal;
        this.perkiraanPinjamanId = perkiraanPinjamanId;
        this.perkiraanKasId = perkiraanKasId;
        this.nominal = nominal;
        this.keterangan = keterangan;
        this.bankId = bankId;
        this.sumberDana = sumberDana;
        this.pelunasan = pelunasan;
        this.statusLunas = statusLunas;
        this.noRegister = noRegister;
        this.karyawanID = karyawanId;
    }

    // Getter & Setter
    @Override
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getKaryawanId() { return karyawanID; }
    public void setKaryawanId(int karyawanId) {
        this.karyawanID = karyawanId; 
        this.karyawan = null;
    }
    
    public int getPelunasan() { return pelunasan; }
    public void setPelunasan(int pelunasan) { this.pelunasan = pelunasan; }   
    

    public Date getTanggal() { return tanggal; }
    public void setTanggal(Date tanggal) { this.tanggal = tanggal; }

    
    public String getNoRegister() { return noRegister; }
    public void setNoRegister(String noRegister) { this.noRegister = noRegister; }
    
    public String getStatusLunas() { return statusLunas; }
    public void setStatuLunas(String statuLunas) { this.statusLunas = statuLunas; }
    
    
    public int getPerkiraanPinjamanId() { return perkiraanPinjamanId; }
    public void setPerkiraanPinjamanId(int perkiraanPinjamanId) { 
        this.perkiraanPinjamanId = perkiraanPinjamanId; 
        this.perkiraanPinjaman = null;
    }
    
    public int getPerkiraanKasId() { return perkiraanKasId; }
    public void setPerkiraanKasId(int perkiraanKasId) { 
        this.perkiraanKasId = perkiraanKasId; 
        this.perkiraanKas = null; 
    }

    public int getSaldo() { return nominal - pelunasan; }
    
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
    public Perkiraan getPerkiraanPinjaman() throws SQLException {
        if (perkiraanPinjaman == null) {
            PerkiraanDAO perkiraanDAO = new PerkiraanDAO();
            perkiraanPinjaman = perkiraanDAO.getById(this.perkiraanPinjamanId);
        }
        return perkiraanPinjaman;
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
    
    public StakeHolder getKaryawan() throws SQLException {
        if (karyawan == null) {
            StakeHolderDAO karyawanDAO = new StakeHolderDAO();
            karyawan = karyawanDAO.getById(this.karyawanID);
        }
        return karyawan;
    }
    
    public String getUserCreate() { return userCreate; }
    public void setUserCreate(String userCreate) { this.userCreate = userCreate; }
    
    public String getUserUpdate() { return userUpdate; }
    public void setUserUpdate(String userUpdate) { this.userUpdate = userUpdate; }
}

