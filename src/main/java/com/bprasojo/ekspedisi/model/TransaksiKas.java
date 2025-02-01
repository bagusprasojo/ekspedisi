/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bprasojo.ekspedisi.model;

import com.bprasojo.ekspedisi.dao.ArmadaDAO;
import com.bprasojo.ekspedisi.dao.BankDAO;
import com.bprasojo.ekspedisi.dao.PerkiraanDAO;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author USER
 */
public class TransaksiKas extends  BaseClass{
    private int id;
    private int akunKasId; // ID untuk lazy loading
    private Perkiraan akunKas; // Properti lazy
    private int akunTransaksiId; // ID untuk lazy loading
    private Perkiraan akunTransaksi; // Properti lazy
    
    private int bankId;
    private Bank bank;
    private Date tanggal;
    private int nominalMasuk;
    private int nominalKeluar;
    private String keterangan;
    private int armadaId; // ID untuk lazy loading
    private Armada armada; // Properti lazy

    private final PerkiraanDAO perkiraanDAO; // DAO untuk lazy loading
    private final ArmadaDAO armadaDAO; // DAO untuk lazy loading
    private final BankDAO bankDAO; // DAO untuk lazy loading

    // Constructor untuk DAO injection
    public TransaksiKas() {
        this.perkiraanDAO = new PerkiraanDAO();
        this.armadaDAO = new ArmadaDAO();
        this.bankDAO = new BankDAO();
    }

    // Getter dan Setter biasa
    @Override
    public int getId() { return id; }
    
    public void setId(int id) { this.id = id; }
    public int getAkunKasId() { return akunKasId; }
    public void setAkunKasId(int akunKasId) { this.akunKasId = akunKasId; }
    
    public int getBankId() { return bankId; }
    public void setBankId(int bankId) { this.bankId = bankId; }
    
    public int getAkunTransaksiId() { return akunTransaksiId; }
    public void setAkunTransaksiId(int akunTransaksiId) { this.akunTransaksiId = akunTransaksiId; }
    public Date getTanggal() { return tanggal; }
    public void setTanggal(Date tanggal) { this.tanggal = tanggal; }
    public int getNominalMasuk() { return nominalMasuk; }
    public void setNominalMasuk(int nominalMasuk) { this.nominalMasuk = nominalMasuk; }
    public int getNominalKeluar() { return nominalKeluar; }
    public void setNominalKeluar(int nominalKeluar) { this.nominalKeluar = nominalKeluar; }
    public String getKeterangan() { return keterangan; }
    public void setKeterangan(String keterangan) { this.keterangan = keterangan; }
    public int getArmadaId() { return armadaId; }
    public void setArmadaId(int armadaId) { this.armadaId = armadaId; }

    // Lazy loading untuk akunKas
    public Perkiraan getAkunKas() {
        if (akunKas == null && akunKasId != 0) {
            try {
                akunKas = perkiraanDAO.getPerkiraanById(akunKasId);
            } catch (SQLException ex) {
                Logger.getLogger(TransaksiKas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return akunKas;
    }

    // Lazy loading untuk akunTransaksi
    public Perkiraan getAkunTransaksi() {
        if (akunTransaksi == null && akunTransaksiId != 0) {
            try {
                akunTransaksi = perkiraanDAO.getPerkiraanById(akunTransaksiId);
            } catch (SQLException ex) {
                Logger.getLogger(TransaksiKas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return akunTransaksi;
    }

    // Lazy loading untuk armada
    public Armada getArmada() throws SQLException {
        if (armada == null && armadaId != 0) {
            armada = armadaDAO.getArmadaById(armadaId);
        }
        return armada;
    }
    
    // Lazy loading untuk bank
    public Bank getBank() throws SQLException {
        if (bank == null && bankId != 0) {
            bank = bankDAO.getBankById(bankId);
        }
        return bank;
    }
}

