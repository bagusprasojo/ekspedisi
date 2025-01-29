/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bprasojo.ekspedisi.model;

/**
 *
 * @author USER
 */
public class Armada {
    private String nopol;
    private String kendaraan;
    private String pemilik;
    private String alamat;
    private String kota;
    private String telp;

    // Constructor
    public Armada(String nopol, String kendaraan, String pemilik, String alamat, String kota, String telp) {
        this.nopol = nopol;
        this.kendaraan = kendaraan;
        this.pemilik = pemilik;
        this.alamat = alamat;
        this.kota = kota;
        this.telp = telp;
    }

    // Getters and Setters
    public String getNopol() {
        return nopol;
    }

    public void setNopol(String nopol) {
        this.nopol = nopol;
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

    @Override
    public String toString() {
        return "Armada{" +
                "nopol='" + nopol + '\'' +
                ", kendaraan='" + kendaraan + '\'' +
                ", pemilik='" + pemilik + '\'' +
                ", alamat='" + alamat + '\'' +
                ", kota='" + kota + '\'' +
                ", telp='" + telp + '\'' +
                '}';
    }
}

