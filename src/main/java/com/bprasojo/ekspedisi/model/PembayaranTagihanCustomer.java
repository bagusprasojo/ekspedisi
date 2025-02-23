/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bprasojo.ekspedisi.model;

import com.bprasojo.ekspedisi.dao.BankDAO;
import com.bprasojo.ekspedisi.dao.TagihanCustomerDAO;
import java.sql.SQLException;
import java.util.Date;

/**
 *
 * @author USER
 */
public class PembayaranTagihanCustomer {
    private int id;
    private String noRegister;
    private int tagihanCustomerId;
    private Date tanggal;
    private int nominalKas;
    private int pph;
    private int pphPersen;
    private int perkiraanKasId;
    private int bankId;
    private int perkiraanPphId;
    private String keterangan;
    private String sumberDana; // Tambahan sumber dana
    private String terbilang;
    // Lazy Loading untuk objek terkait
    private TagihanCustomer tagihanCustomer;
    private Perkiraan perkiraanKas;
    private Bank bank;
    private Perkiraan perkiraanPph;

    public PembayaranTagihanCustomer() {}

    public PembayaranTagihanCustomer(int id, String noRegister, int tagihanCustomerId, Date tanggal, int nominalKas, int pph,int pphPersen, int perkiraanKasId, int bankId, int perkiraanPphId, String keterangan, String sumberDana, String terbilang) {
        this.id = id;
        this.noRegister = noRegister;
        this.tagihanCustomerId = tagihanCustomerId;
        this.tanggal = tanggal;
        this.nominalKas = nominalKas;
        this.pph = pph;
        this.pphPersen = pphPersen;
        this.perkiraanKasId = perkiraanKasId;
        this.bankId = bankId;
        this.perkiraanPphId = perkiraanPphId;
        this.keterangan = keterangan;
        this.sumberDana = sumberDana;
        this.terbilang = terbilang;
    }

    // Getter dan Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNoRegister() { return noRegister; }
    public void setNoRegister(String noRegister) { this.noRegister = noRegister; }

    public int getTagihanCustomerId() { return tagihanCustomerId; }
    public void setTagihanCustomerId(int tagihanCustomerId) { this.tagihanCustomerId = tagihanCustomerId; }

    public Date getTanggal() { return tanggal; }
    public void setTanggal(Date tanggal) { this.tanggal = tanggal; }

    public int getNominalKas() { return nominalKas; }
    public void setNominalKas(int nominalKas) { this.nominalKas = nominalKas; }

    public int getPph() { return pph; }
    public void setPph(int pph) { this.pph = pph; }
    
    public int getPphPersen() { return pphPersen; }
    public void setPphPersen(int pphPersen) { this.pphPersen = pphPersen; }

    public int getPerkiraanKasId() { return perkiraanKasId; }
    public void setPerkiraanKasId(int perkiraanKasId) { this.perkiraanKasId = perkiraanKasId; }

    public int getBankId() { return bankId; }
    public void setBankId(int bankId) { this.bankId = bankId; }

    public int getPerkiraanPphId() { return perkiraanPphId; }
    public void setPerkiraanPphId(int perkiraanPphId) { this.perkiraanPphId = perkiraanPphId; }

    public String getKeterangan() { return keterangan; }
    public void setKeterangan(String keterangan) { this.keterangan = keterangan; }

    public String getSumberDana() { return sumberDana; }
    public void setSumberDana(String sumberDana) { this.sumberDana = sumberDana; }

    public String getTerbilang() { return terbilang; }
    public void setTerbilang(String terbilang) { this.terbilang = terbilang; }

    public TagihanCustomer getTagihanCustomer() throws SQLException{
        if (tagihanCustomer == null) {
            TagihanCustomerDAO tagihanCustomerDAO = new TagihanCustomerDAO();
            tagihanCustomer = tagihanCustomerDAO.getById(tagihanCustomerId);
        }
        return tagihanCustomer;
    }
    
    public Bank getBank() throws SQLException{
        if (bank == null) {
            BankDAO bankDAO = new BankDAO();
            bank = bankDAO.getBankById(bankId);
        }
        return bank;
    }
    
    public Integer getTotal(){
        return nominalKas + pph;
    }
}

