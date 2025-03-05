/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bprasojo.ekspedisi.model;

/**
 *
 */
public class StakeHolder {
    private int id;
    private String kode;
    private String nama;
    private String alamat;
    private String noKtp;
    private String lokasiKerja;
    private String jenis;
    private String kota;
    private String kodePos;
    private String telp;
    private String userCreate;
    private String userUpdate;

    // Constructor
    public StakeHolder() {}

    public StakeHolder(int id, String kode, String nama, String alamat, String noKtp, String lokasiKerja, String jenis, String kota, String kodePos, String telp, String userCreate, String userUpdate) {
        this.id = id;
        this.kode = kode;
        this.nama = nama;
        this.alamat = alamat;
        this.noKtp = noKtp;
        this.lokasiKerja = lokasiKerja;
        this.jenis = jenis;
        this.kota = kota;
        this.kodePos = kodePos;
        this.telp = telp;
    }

    // Getter dan Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getKode() { return kode; }
    public void setKode(String kode) { this.kode = kode; }
    
    public String getUserCreate() { return userCreate; }
    public void setUserCreate(String userCreate) { this.userCreate = userCreate; }
    
    public String getUserUpdate() { return userUpdate; }
    public void setUserUpdate(String userUpdate) { this.userUpdate = userUpdate; }
    
    public String getKodePos() { return kodePos; }
    public void setKodePos(String kodePos) { this.kodePos = kodePos; }
    
    public String getKota() { return kota; }
    public void setKota(String kota) { this.kota = kota; }
    
    public String getTelp() { return telp; }
    public void setTelp(String telp) { this.telp = telp; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }

    public String getNoKtp() { return noKtp; }
    public void setNoKtp(String noKtp) { this.noKtp = noKtp; }

    public String getLokasiKerja() { return lokasiKerja; }
    public void setLokasiKerja(String lokasiKerja) { this.lokasiKerja = lokasiKerja; }

    public String getJenis() { return jenis; }
    public void setJenis(String jenis) { this.jenis = jenis; }
}
