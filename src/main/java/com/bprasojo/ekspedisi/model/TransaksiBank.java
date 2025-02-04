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
import com.bprasojo.ekspedisi.dao.JenisTransaksiDAO;
import com.bprasojo.ekspedisi.dao.PerkiraanDAO;
import java.sql.Date;
import java.sql.SQLException;

public class TransaksiBank {
    private int id;
    private Date tanggal;
    private int bankUtamaId;
    private JenisTransaksi jenisTransaksi;
    private double debet;
    private double kredit;
    private int bankTujuanId;
    private int akunUtamaId;
    private int akunTujuanId;
    private int jenisTransaksiId;

    private transient Bank bankUtama; // Lazy loading
    private transient Bank bankTujuan; // Lazy loading
    private transient Perkiraan akunUtama; // Lazy loading
    private transient Perkiraan akunTujuan; // Lazy loading
    

    public TransaksiBank() {}

    public TransaksiBank(int id, Date tanggal, int bankUtamaId, int jenisTransaksiid, double debet, double kredit, int bankTujuanId, int akunUtamaId, int akunTujuanId) {
        this.id = id;
        this.tanggal = tanggal;
        this.bankUtamaId = bankUtamaId;
        this.jenisTransaksiId = jenisTransaksiid;
        this.debet = debet;
        this.kredit = kredit;
        this.bankTujuanId = bankTujuanId;
        this.akunUtamaId = akunUtamaId;
        this.akunTujuanId = akunTujuanId;
    }

    // Getter & Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Date getTanggal() { return tanggal; }
    public void setTanggal(Date tanggal) { this.tanggal = tanggal; }

    public int getBankUtamaId() { return bankUtamaId; }
    public void setBankUtamaId(int bankUtamaId) { this.bankUtamaId = bankUtamaId; }

    public int getJenisTransaksiId() { return jenisTransaksiId; }
    public void setJenisTransaksiId(int jenisTransaksiId) { this.jenisTransaksiId = jenisTransaksiId; }

    public double getDebet() { return debet; }
    public void setDebet(double debet) { this.debet = debet; }

    public double getKredit() { return kredit; }
    public void setKredit(double kredit) { this.kredit = kredit; }

    public Integer getBankTujuanId() { return bankTujuanId; }
    public void setBankTujuanId(Integer bankTujuanId) { this.bankTujuanId = bankTujuanId; }

    public int getAkunUtamaId() { return akunUtamaId; }
    public void setAkunUtamaId(int akunUtamaId) { this.akunUtamaId = akunUtamaId; }

    public Integer getAkunTujuanId() { return akunTujuanId; }
    public void setAkunTujuanId(Integer akunTujuanId) { this.akunTujuanId = akunTujuanId; }

    // Lazy Loading Bank Utama
    public Bank getBankUtama() throws SQLException {
        if ((bankUtama == null) && (bankUtamaId > 0)) {
            BankDAO bankDAO = new BankDAO();
            bankUtama = bankDAO.getBankById(this.bankUtamaId);
        }
        return bankUtama;
    }

    // Lazy Loading Bank Tujuan
    public Bank getBankTujuan() throws SQLException {
        if (bankTujuan == null && bankTujuanId > 0) {
            BankDAO bankDAO = new BankDAO();
            bankTujuan = bankDAO.getBankById(this.bankTujuanId);
        }
        return bankTujuan;
    }

    // Lazy Loading Akun Utama
    public Perkiraan getAkunUtama() throws SQLException {
        if (akunUtama == null && akunUtamaId > 0) {
            PerkiraanDAO perkiraanDAO = new PerkiraanDAO();
            akunUtama = perkiraanDAO.getPerkiraanById(this.akunUtamaId);
        }
        return akunUtama;
    }

    // Lazy Loading Akun Tujuan
    public Perkiraan getAkunTujuan() throws SQLException {
        if (akunTujuan == null && akunTujuanId > 0) {
            PerkiraanDAO perkiraanDAO = new PerkiraanDAO();
            akunTujuan = perkiraanDAO.getPerkiraanById(this.akunTujuanId);
        }
        return akunTujuan;
    }
    
    public JenisTransaksi getJenisTransaksi() throws SQLException {
        if (jenisTransaksi == null && jenisTransaksiId > 0) {
            JenisTransaksiDAO jenisTransaksiDAO = new JenisTransaksiDAO();
            jenisTransaksi = jenisTransaksiDAO.getById(this.jenisTransaksiId);
        }
        return jenisTransaksi;
    }
}

