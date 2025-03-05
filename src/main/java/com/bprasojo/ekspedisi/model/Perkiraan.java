/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bprasojo.ekspedisi.model;

/**
 *
 */
public class Perkiraan extends BaseClass{
    private String kode;
    private String nama;
    private Integer parent_Id;
    private String golongan;
    private String kelompok;
    private Integer level;
    private String saldo_normal;
    private Integer id;

    // Constructor
    public Perkiraan(String kode, String nama, Integer parentId, String golongan, String kelompok, Integer level, String saldo_normal, Integer id) {
        this.kode = kode;
        this.nama = nama;
        this.parent_Id = parentId;
        this.golongan = golongan;
        this.kelompok = kelompok;
        this.level = level;
        this.saldo_normal = saldo_normal;
        this.id = id;
    }

    // Getter dan Setter
    
    
    @Override
    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Integer getParent_Id() {
        return parent_Id;
    }

    public void setParent_Id(Integer parent_Id) {
        this.parent_Id = parent_Id;
    }

    public String getGolongan() {
        return golongan;
    }

    public void setGolongan(String golongan) {
        this.golongan = golongan;
    }

    public String getKelompok() {
        return kelompok;
    }

    public void setKelompok(String kelompok) {
        this.kelompok = kelompok;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }


    public String getSaldo_normal() {
        return saldo_normal;
    }

    public void setSaldo_normal(String saldo_normal) {
        this.saldo_normal = saldo_normal;
    }

    @Override
    public String toString() {        
        return kode + '-' + nama; // Agar JComboBox menampilkan hanya nama
    }
}

