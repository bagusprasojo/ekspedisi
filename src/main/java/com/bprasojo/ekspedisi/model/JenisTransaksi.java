/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bprasojo.ekspedisi.model;

/**
 *
 * @author USER
 */
import com.bprasojo.ekspedisi.dao.PerkiraanDAO;
import java.sql.SQLException;

public class JenisTransaksi extends BaseClass{
    private int id;
    private String kode;
    private String nama;
    private int akunId;

    private transient Perkiraan akun; // Lazy loading

    public JenisTransaksi() {}

    public JenisTransaksi(int id, String kode, String nama, int akunId) {
        this.id = id;
        this.kode = kode;
        this.nama = nama;
        this.akunId = akunId;
    }

    // Getter & Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getKode() { return kode; }
    public void setKode(String kode) { this.kode = kode; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public int getAkunId() { return akunId; }
    public void setAkunId(int akunId) { 
        this.akunId = akunId; 
        this.akun = null;
    }

    // Lazy Loading Akun
    public Perkiraan getAkun() throws SQLException {
        if (akun == null) {
            PerkiraanDAO perkiraanDAO = new PerkiraanDAO();
            akun = perkiraanDAO.getById(this.akunId);
        }
        return akun;
    }
    
    @Override
    public String toString() {
        return kode + '-' + nama ;
    }
}

