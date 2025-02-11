/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bprasojo.ekspedisi.model;

/**
 *
 * @author USER
 */
import com.bprasojo.ekspedisi.dao.BankDAO;
import com.bprasojo.ekspedisi.dao.PerkiraanDAO;
import java.sql.Date;
import java.sql.SQLException;

public class KasBonKaryawan {
    private int id;
    private Date tanggal;
    private String namaKaryawan;
    private int perkiraanPinjamanId;
    private int perkiraanKasId;
    private int nominal;
    private String keterangan;
    private int bankId;
    private String sumberDana;

    private transient Perkiraan perkiraanPinjaman; // Lazy loading
    private transient Perkiraan perkiraanKas; // Lazy loading
    private transient Bank bank; // Lazy loading

    public KasBonKaryawan() {}

    public KasBonKaryawan(int id, Date tanggal, String namaKaryawan, int perkiraanPinjamanId,int perkiraanKasId, int nominal, String keterangan, int bankId, String sumberDana) {
        this.id = id;
        this.tanggal = tanggal;
        this.namaKaryawan = namaKaryawan;
        this.perkiraanPinjamanId = perkiraanPinjamanId;
        this.perkiraanKasId = perkiraanKasId;
        this.nominal = nominal;
        this.keterangan = keterangan;
        this.bankId = bankId;
        this.sumberDana = sumberDana;
    }

    // Getter & Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Date getTanggal() { return tanggal; }
    public void setTanggal(Date tanggal) { this.tanggal = tanggal; }

    public String getNamaKaryawan() { return namaKaryawan; }
    public void setNamaKaryawan(String namaKaryawan) { this.namaKaryawan = namaKaryawan; }

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
            perkiraanPinjaman = perkiraanDAO.getPerkiraanById(this.perkiraanPinjamanId);
        }
        return perkiraanPinjaman;
    }
    
    public Perkiraan getPerkiraanKas() throws SQLException {
        if (perkiraanKas == null) {
            PerkiraanDAO perkiraanDAO = new PerkiraanDAO();
            perkiraanKas = perkiraanDAO.getPerkiraanById(this.perkiraanKasId);
        }
        return perkiraanKas;
    }

    // Lazy Loading Bank
    public Bank getBank() throws SQLException {
        if (bank == null) {
            BankDAO bankDAO = new BankDAO();
            bank = bankDAO.getBankById(this.bankId);
        }
        return bank;
    }
}

