/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bprasojo.ekspedisi.model;

import com.bprasojo.ekspedisi.dao.ArmadaDAO;
import com.bprasojo.ekspedisi.dao.BankDAO;
import com.bprasojo.ekspedisi.dao.StakeHolderDAO;
import java.sql.SQLException;
import java.util.Date;

/**
 *
 */
public class TransaksiPembelianBBM extends BaseClass{
    private int id;
    private int armadaId;
    private Date tanggal;
    private int kmTerakhir;
    private int kmSekarang;
    private int nominalBBM;
    private String keterangan;
    private int driverId;
    private String userCreate;
    private String userUpdate;
    private int bankId;

    
    private transient Armada armada; // Lazy loading
    private transient StakeHolder driver;
    private transient Bank bank;
    

    public TransaksiPembelianBBM() {}

    public TransaksiPembelianBBM(int id, int armadaId, Date tanggal, int kmTerakhir, int kmSekarang, int nominalBBM, String keterangan, int driverId, String userCreate, String userUpdate, int bankId) {
        this.id = id;
        this.armadaId = armadaId;
        this.tanggal = tanggal;
        this.kmTerakhir = kmTerakhir;
        this.kmSekarang = kmSekarang;
        this.nominalBBM = nominalBBM;
        this.keterangan = keterangan;
        this.driverId = driverId;
        this.userCreate = userCreate;
        this.userUpdate = userUpdate;
        this.bankId = bankId;
    }

    // Getter & Setter
    @Override
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getArmadaId() { return armadaId; }
    public void setArmadaId(int armadaId) { 
        this.armadaId = armadaId; 
        this.armada  = null; 
    }
    
    public int getDriverId() { return driverId; }
    public void setDriverId(int driverId) { 
        this.driverId = driverId; 
        this.driver  = null; 
    }
    
    public int getBankId() { return bankId; }
    public void setBankId(int bankId) { 
        this.bankId = bankId; 
        this.bank  = null; 
    }

    public Date getTanggal() { return tanggal; }
    public void setTanggal(Date tanggal) { this.tanggal = tanggal; }

    public int getKmTerakhir() { return kmTerakhir; }
    public void setKmTerakhir(int kmTerakhir) { this.kmTerakhir = kmTerakhir; }

    public int getKmSekarang() { return kmSekarang; }
    public void setKmSekarang(int kmSekarang) { this.kmSekarang = kmSekarang; }

    public int getNominalBBM() { return nominalBBM; }
    public void setNominalBBM(int nominalBBM) { this.nominalBBM = nominalBBM; }

    public String getKeterangan() { return keterangan; }
    public void setKeterangan(String keterangan) { this.keterangan = keterangan; }


    public String getUserCreate() { return userCreate; }
    public void setUserCreate(String userCreate) { this.userCreate = userCreate; }
    
    public String getUserUpdate() { return userUpdate; }
    public void setUserUpdate(String userUpdate) { this.userUpdate = userUpdate; }

    // Lazy loading untuk objek Armada
    public Armada getArmada() throws SQLException {
        if (armada == null) {
            ArmadaDAO armadaDAO = new ArmadaDAO();
            armada = armadaDAO.getArmadaById(this.armadaId);
        }
        return armada;
    }
    
    public StakeHolder getDriver() throws SQLException {
        if (driver == null) {
            StakeHolderDAO driverDAO = new StakeHolderDAO();
            driver = driverDAO.getById(this.driverId);
        }
        return driver;
    }
    
    public Bank getBank() throws SQLException {
        if (bank == null) {
            BankDAO bankDAO = new BankDAO();
            bank = bankDAO.getById(this.bankId);
        }
        return bank;
    }
}

