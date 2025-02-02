/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bprasojo.ekspedisi.model;

import com.bprasojo.ekspedisi.dao.ArmadaDAO;
import java.sql.SQLException;
import java.util.Date;

/**
 *
 * @author USER
 */
public class TransaksiPembelianBBM extends BaseClass{
    private int id;
    private int armadaId;
    private Date tanggal;
    private int kmTerakhir;
    private int kmSekarang;
    private int nominalBBM;
    private String keterangan;
    
    private transient Armada armada; // Lazy loading

    public TransaksiPembelianBBM() {}

    public TransaksiPembelianBBM(int id, int armadaId, Date tanggal, int kmTerakhir, int kmSekarang, int nominalBBM, String keterangan) {
        this.id = id;
        this.armadaId = armadaId;
        this.tanggal = tanggal;
        this.kmTerakhir = kmTerakhir;
        this.kmSekarang = kmSekarang;
        this.nominalBBM = nominalBBM;
        this.keterangan = keterangan;
    }

    // Getter & Setter
    @Override
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getArmadaId() { return armadaId; }
    public void setArmadaId(int armadaId) { this.armadaId = armadaId; }

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

    // Lazy loading untuk objek Armada
    public Armada getArmada() throws SQLException {
        if (armada == null) {
            ArmadaDAO armadaDAO = new ArmadaDAO();
            armada = armadaDAO.getArmadaById(this.armadaId);
        }
        return armada;
    }
}

