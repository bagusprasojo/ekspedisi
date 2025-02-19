/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bprasojo.ekspedisi.model;

/**
 *
 * @author USER
 */
public class StakeHolder {
    private int id;
    private String kode;
    private String nama;
    private String alamat;
    private String noKtp;
    private String lokasiKerja;
    private String jenis;

    // Constructor
    public StakeHolder() {}

    public StakeHolder(int id, String kode, String nama, String alamat, String noKtp, String lokasiKerja, String jenis) {
        this.id = id;
        this.kode = kode;
        this.nama = nama;
        this.alamat = alamat;
        this.noKtp = noKtp;
        this.lokasiKerja = lokasiKerja;
        this.jenis = jenis;
    }

    // Getter dan Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getKode() { return kode; }
    public void setKode(String kode) { this.kode = kode; }

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
