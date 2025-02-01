package com.bprasojo.ekspedisi.model;

/**
 * Model Bank
 */
public class Bank extends BaseClass{
    private String noRekening;
    private String namaBank;
    private String atasNama;
    private String keterangan;
    private Integer id;
    private Perkiraan akun; 

    // Constructor
    public Bank(String noRekening, String namaBank, String atasNama, String keterangan, Integer id, Perkiraan akun) {
        this.noRekening = noRekening;
        this.namaBank = namaBank;
        this.atasNama = atasNama;
        this.keterangan = keterangan;
        this.id = id;
        this.akun = akun;
    }

    // Getter dan Setter
    public Perkiraan getAkun() {
        return akun;
    }
    
    public void setAkun(Perkiraan akun) {
        this.akun = akun;
    }
    
    @Override
    public int getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getNoRekening() {
        return noRekening;
    }

    public void setNoRekening(String noRekening) {
        this.noRekening = noRekening;
    }

    public String getNamaBank() {
        return namaBank;
    }

    public void setNamaBank(String namaBank) {
        this.namaBank = namaBank;
    }

    public String getAtasNama() {
        return atasNama;
    }

    public void setAtasNama(String atasNama) {
        this.atasNama = atasNama;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    @Override
    public String toString() {
        return namaBank + '-' + noRekening ;
    }
}
