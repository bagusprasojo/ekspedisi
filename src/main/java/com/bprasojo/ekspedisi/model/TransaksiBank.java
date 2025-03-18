/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bprasojo.ekspedisi.model;

/**
 *
 */
import com.bprasojo.ekspedisi.dao.BankDAO;
import com.bprasojo.ekspedisi.dao.JenisTransaksiDAO;
import com.bprasojo.ekspedisi.dao.PerkiraanDAO;
import java.util.Date;
import java.sql.SQLException;

public class TransaksiBank extends BaseClass{
    private int id;
    private Date tanggal;
    private int bankUtamaId;
    private JenisTransaksi jenisTransaksi;
    private int debet;
    private int kredit;
    private int bankTujuanId;
    private int akunUtamaId;
    private int akunTujuanId;
    private int jenisTransaksiId;
    private String uraian;
    private int biayaAdmBank;
    private String userCreate;
    private String userUpdate;
    private String noBukti;
    
    private transient Bank bankUtama; // Lazy loading
    private transient Bank bankTujuan; // Lazy loading
    private transient Perkiraan akunUtama; // Lazy loading
    private transient Perkiraan akunTujuan; // Lazy loading
    

    public TransaksiBank() {}

    public TransaksiBank(int id, Date tanggal, int bankUtamaId, int jenisTransaksiid, int debet, int kredit, int bankTujuanId, int akunUtamaId, int akunTujuanId, int biayaAdmBank, String uraian, String userCreate, String userUpdate, String noBukti) {
        this.id = id;
        this.tanggal = tanggal;
        this.bankUtamaId = bankUtamaId;
        this.jenisTransaksiId = jenisTransaksiid;
        this.debet = debet;
        this.kredit = kredit;
        this.bankTujuanId = bankTujuanId;
        this.akunUtamaId = akunUtamaId;
        this.akunTujuanId = akunTujuanId;
        this.biayaAdmBank = biayaAdmBank;
        this.uraian = uraian;
        this.userCreate = userCreate;
        this.userUpdate = userUpdate;
        this.noBukti = noBukti;
    }

    // Getter & Setter
    @Override
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getBiayaAdmBank() { return biayaAdmBank; }
    public void setBiayaAdmBank(int biayaAdmBank) { this.biayaAdmBank = biayaAdmBank; }
    
    public String getUraian() { return uraian; }
    public void setUraian(String uraian) { this.uraian = uraian; }
    
    public String getNoBukti() { 
        if (noBukti == null){
            noBukti = "";
        }
        return noBukti; 
    }
    public void setNoBukti(String noBukti) { this.noBukti = noBukti; }

    public Date getTanggal() { return tanggal; }
    public void setTanggal(Date tanggal) { this.tanggal = tanggal; }

    public int getBankUtamaId() { return bankUtamaId; }
    public void setBankUtamaId(int bankUtamaId) { 
        this.bankUtamaId = bankUtamaId; 
        this.bankUtama = null; 
    }

    public int getJenisTransaksiId() { return jenisTransaksiId; }
    public void setJenisTransaksiId(int jenisTransaksiId) { 
        this.jenisTransaksiId = jenisTransaksiId; 
        this.jenisTransaksi = null;
    
    }

    public int getDebet() { return debet; }
    public void setDebet(int debet) { this.debet = debet; }

    public int getKredit() { return kredit; }
    public void setKredit(int kredit) { this.kredit = kredit; }

    public Integer getBankTujuanId() { return bankTujuanId; }
    public void setBankTujuanId(Integer bankTujuanId) { 
        this.bankTujuanId = bankTujuanId; 
        this.bankTujuan = null; 
    }

    public int getAkunUtamaId() { return akunUtamaId; }
    public void setAkunUtamaId(int akunUtamaId) { 
        this.akunUtamaId = akunUtamaId; 
        this.akunTujuan = null;
    }

    public Integer getAkunTujuanId() { return akunTujuanId; }
    public void setAkunTujuanId(Integer akunTujuanId) { 
        this.akunTujuanId = akunTujuanId; 
        this.akunTujuan = null;
    }

    // Lazy Loading Bank Utama
    public Bank getBankUtama() throws SQLException {
        if ((bankUtama == null) && (bankUtamaId > 0)) {
            BankDAO bankDAO = new BankDAO();
            bankUtama = bankDAO.getById(this.bankUtamaId);
        }
        return bankUtama;
    }

    // Lazy Loading Bank Tujuan
    public Bank getBankTujuan() throws SQLException {
        if (bankTujuan == null && bankTujuanId > 0) {
            BankDAO bankDAO = new BankDAO();
            bankTujuan = bankDAO.getById(this.bankTujuanId);
        }
        return bankTujuan;
    }

    // Lazy Loading Akun Utama
    public Perkiraan getAkunUtama() throws SQLException {
        if (akunUtama == null && akunUtamaId > 0) {
            PerkiraanDAO perkiraanDAO = new PerkiraanDAO();
            akunUtama = perkiraanDAO.getById(this.akunUtamaId);
        }
        return akunUtama;
    }

    // Lazy Loading Akun Tujuan
    public Perkiraan getAkunTujuan() throws SQLException {
        if (akunTujuan == null && akunTujuanId > 0) {
            PerkiraanDAO perkiraanDAO = new PerkiraanDAO();
            akunTujuan = perkiraanDAO.getById(this.akunTujuanId);
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
    
    public String getUserCreate() { return userCreate; }
    public void setUserCreate(String userCreate) { this.userCreate = userCreate; }
    
    public String getUserUpdate() { return userUpdate; }
    public void setUserUpdate(String userUpdate) { this.userUpdate = userUpdate; }
}

