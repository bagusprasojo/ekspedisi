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
    private int isKas;

    // Constructor
    public Bank(String noRekening, String namaBank, String atasNama, String keterangan, Integer id, Perkiraan akun, int isKas) {
        this.noRekening = noRekening;
        this.namaBank = namaBank;
        this.atasNama = atasNama;
        this.keterangan = keterangan;
        this.id = id;
        this.akun = akun;
        this.isKas = isKas;
    }

    public Bank() {
        this.noRekening = "";
        this.namaBank = "";
        this.atasNama = "";
        this.keterangan = "";
        this.id = 0;
        this.akun = null;
        this.isKas = 0;
        
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
    
    public int getIsKas() {
        return isKas;
    }
    
    public void setIsKas(Integer isKas) {
        this.isKas = isKas;
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
        if (isKas == 1){
            return namaBank;
        } else {
            return namaBank + '-' + noRekening ;
        }
    }
}
