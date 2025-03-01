/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bprasojo.ekspedisi.model;

import com.bprasojo.ekspedisi.dao.StakeHolderDAO;
import com.bprasojo.ekspedisi.model.StakeHolder;
import java.sql.SQLException;

/**
 *
 * @author USER
 */
public class Armada extends BaseClass{
    private String nopol;
    private String kendaraan;
    private String pemilik;
    private String alamat;
    private String kota;
    private String telp;
    private int driver_id;
    private int id;
    private String userCreate;
    private String userUpdate;

    
    private transient StakeHolder driver;

    // Constructor
    public Armada(){}
    
    public Armada(String nopol, String kendaraan, String pemilik, String alamat, String kota, String telp, int driver_id, int id, String userCreate, String userUpdate) {
        this.nopol = nopol;
        this.kendaraan = kendaraan;
        this.pemilik = pemilik;
        this.alamat = alamat;
        this.kota = kota;
        this.telp = telp;
        this.driver_id = driver_id;
        this.id = id;
        this.userCreate = userCreate;
        this.userUpdate = userUpdate;
        
    }

    // Getters and Setters
    public int getDriverId() {
        return driver_id;
    }

    public void setDriverId(int driver_id) {
        this.driver_id = driver_id;
        this.driver = null;
    }
    
    public StakeHolder getDriver() throws SQLException{
        if (driver == null) {
            StakeHolderDAO stakeHolderDAO = new StakeHolderDAO();
            driver = stakeHolderDAO.getById(this.driver_id);
        }
        return driver;
    }
    
    public String getNopol() {
        return nopol;
    }

    public void setNopol(String nopol) {
        this.nopol = nopol;
    }
    
    @Override
    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKendaraan() {
        return kendaraan;
    }

    public void setKendaraan(String kendaraan) {
        this.kendaraan = kendaraan;
    }

    public String getPemilik() {
        return pemilik;
    }

    public void setPemilik(String pemilik) {
        this.pemilik = pemilik;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getTelp() {
        return telp;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }
    
    public String getUserCreate() { return userCreate; }
    public void setUserCreate(String userCreate) { this.userCreate = userCreate; }
    
    public String getUserUpdate() { return userUpdate; }
    public void setUserUpdate(String userUpdate) { this.userUpdate = userUpdate; }

    @Override
    public String toString() {
        return nopol + '-' +kendaraan;
    }
}

