/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.bprasojo.ekspedisi.dao;

import com.bprasojo.ekspedisi.model.Bank;
import com.bprasojo.ekspedisi.model.Jurnal;
import com.bprasojo.ekspedisi.model.KasBonKaryawan;
import com.bprasojo.ekspedisi.model.PembayaranKasBon;
import com.bprasojo.ekspedisi.utils.AppUtils;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import static org.junit.jupiter.api.Assertions.*;
/**
 *
 * @author USER
 */
@TestMethodOrder(OrderAnnotation.class)
public class PembayaranKasBonDAOIT {
    static PembayaranKasBonDAO instance;
    static KasBonKaryawanDAO instanceKBK;
    static BankDAO instanceBank;
    static PerkiraanDAO instancePerkiraan;
    static JurnalDAO instanceJurnal;
    static StakeHolderDAO instanceSH;
    
    static int idSave;
    public PembayaranKasBonDAOIT() {
    }
    
    @BeforeAll
    public static void setUpClass() {
        instance = new PembayaranKasBonDAO();
        instanceKBK = new KasBonKaryawanDAO();
        instanceBank = new BankDAO();
        instancePerkiraan = new PerkiraanDAO();
        instanceJurnal = new JurnalDAO();
        instanceSH = new StakeHolderDAO();
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }
    
    @Test
    @Order(1)
    public void testSave() throws Exception {
        System.out.println(this.getClass().getName() + ": testSave");
        PembayaranKasBon transaksi = new PembayaranKasBon();
        
        Bank bank = instanceBank.getById(instanceBank.getRandomID());
        transaksi.setBankId(bank.getId());
        
        KasBonKaryawan kbk = instanceKBK.getById(instanceKBK.getRandomIDBelumLunas());
        int pelunasan = kbk.getPelunasan();
        
        transaksi.setKasBonKaryawanId(kbk.getId());        
        transaksi.setKeterangan("Keterangan");
        transaksi.setNominal(10);
        transaksi.setPerkiraanKasId(bank.getAkun().getId());
        transaksi.setSumberDana("Sumberdana");
        transaksi.setTanggal(AppUtils.now());
        transaksi.setUserCreate("userCreate");
        transaksi.setUserUpdate("userUpdate");
        
        instance.save(transaksi);
        idSave = transaksi.getId();
        
        PembayaranKasBon pkb = instance.getById(idSave);
        assertEquals(pkb.getId(), idSave);
        assertEquals(pkb.getBankId(), transaksi.getBankId());
        assertEquals(pkb.getNoRegister(), transaksi.getNoRegister());
        assertEquals(pkb.getPerkiraanKasId(), transaksi.getPerkiraanKasId());
        assertEquals(pkb.getNominal(), transaksi.getNominal());
        
        KasBonKaryawan kbkCek = instanceKBK.getById(kbk.getId());
        assertEquals(kbkCek.getPelunasan(), pelunasan + transaksi.getNominal());
        
        Jurnal jurnal = instanceJurnal.getByTransaksiId(idSave, transaksi.getClass().getName());
        assertEquals(pkb.getNoRegister(), jurnal.getNoJurnal());
        assertEquals(pkb.getTanggal(), jurnal.getTanggal());
        assertEquals(pkb.getNominal(), jurnal.getDebet());
        assertEquals(pkb.getNominal(), jurnal.getKredit());
        
        
    }
    
    @Test
    @Order(5)
    public void testSaveUpdate() throws Exception {
        System.out.println(this.getClass().getName() + ": testSaveUpdate");
        PembayaranKasBon transaksi = instance.getById(idSave);
        int nominalLama = transaksi.getNominal();
        
        KasBonKaryawan kbkLama = instanceKBK.getById(transaksi.getKasBonKaryawanId());
        int pelunasanLama = kbkLama.getPelunasan();
        
        int idKBKBaru = kbkLama.getId();
        while (idKBKBaru == kbkLama.getId()) {            
            idKBKBaru = instanceKBK.getRandomIDBelumLunas();
        }
        KasBonKaryawan kbkBaru = instanceKBK.getById(idKBKBaru);
        int pelunasanBaru = kbkBaru.getPelunasan();
        
        
        Bank bank = instanceBank.getById(instanceBank.getRandomID());
        transaksi.setBankId(bank.getId());
        transaksi.setKasBonKaryawanId(idKBKBaru);
        transaksi.setKeterangan("Keterangan Update");
        transaksi.setNominal(100);
        transaksi.setPerkiraanKasId(bank.getAkun().getId());
        
        instance.save(transaksi);
        idSave = transaksi.getId();
        
        PembayaranKasBon pkb = instance.getById(idSave);
        assertEquals(pkb.getId(), idSave);
        assertEquals(pkb.getBankId(), transaksi.getBankId());
        assertEquals(pkb.getNoRegister(), transaksi.getNoRegister());
        assertEquals(pkb.getPerkiraanKasId(), transaksi.getPerkiraanKasId());
        assertEquals(pkb.getNominal(), 100);
        assertEquals(pkb.getKeterangan(), "Keterangan Update");
        
        // cek pelunasan;
        kbkLama = instanceKBK.getById(kbkLama.getId());
        kbkBaru = instanceKBK.getById(idKBKBaru);
        assertEquals(kbkBaru.getPelunasan(), pelunasanBaru + transaksi.getNominal());
        assertEquals(kbkLama.getPelunasan(), pelunasanLama - nominalLama);
        
        
        Jurnal jurnal = instanceJurnal.getByTransaksiId(idSave, transaksi.getClass().getName());
        assertEquals(pkb.getNoRegister(), jurnal.getNoJurnal());
        assertEquals(pkb.getTanggal(), jurnal.getTanggal());
        assertEquals(pkb.getNominal(), jurnal.getDebet());
        assertEquals(pkb.getNominal(), jurnal.getKredit());
        
        
    }
    
    @Test
    @Order(8)
    public void testSaveUpdateNoBuktiSama() throws Exception {
        System.out.println(this.getClass().getName() + ": testSaveUpdateNoBuktiSama");
        
        PembayaranKasBon transaksi = instance.getById(idSave);        
        
        int idRandom = transaksi.getId(); 
        while (idRandom == transaksi.getId()) {            
            idRandom = instance.getRandomID();
        }
        
        PembayaranKasBon pkb = instance.getById(idRandom);        
        transaksi.setNoRegister(pkb.getNoRegister());
        
        SQLException exception = assertThrows(SQLException.class, () -> {
            instance.save(transaksi);
        });

        System.out.println("Message : " + exception.getMessage());
        assertTrue(exception.getMessage().contains("Duplicate entry"));        
    }
    
    @Test
    @Order(10)
    public void testSaveUpdateLunas() throws Exception {
        System.out.println(this.getClass().getName() + ": testSaveUpdate");
        PembayaranKasBon transaksi = instance.getById(idSave);
        
        KasBonKaryawan kbk = instanceKBK.getById(transaksi.getKasBonKaryawanId());
        int bayar = kbk.getNominal() - kbk.getPelunasan() + transaksi.getNominal();
        System.out.println(this.getClass().getName() + "Bayar Nominal KBK " + kbk.getNominal());
        System.out.println(this.getClass().getName() + "Bayar Pelunasan KBK " + kbk.getPelunasan());
        System.out.println(this.getClass().getName() + "Niminal PKB " + transaksi.getNominal());
        System.out.println(this.getClass().getName() + "Niminal Bayar " + bayar);
        
        transaksi.setNominal(bayar);
        instance.save(transaksi);
        
        
        kbk = instanceKBK.getById(transaksi.getKasBonKaryawanId());
        assertEquals(kbk.getNominal(), kbk.getPelunasan());
        assertEquals(kbk.getStatusLunas(), "Lunas");
        
    }

    @Test
    @Order(15)
    public void testSaveClosing() throws Exception {
        System.out.println(this.getClass().getName() + ": testSaveClosing");
        
        PembayaranKasBon transaksi = instance.getById(idSave);        
        transaksi.setTanggal(instance.getLastClosingDate());
        
        SQLException exception = assertThrows(SQLException.class, () -> {
            instance.save(transaksi);
        });

        System.out.println("Message : " + exception.getMessage());
        assertTrue(exception.getMessage().contains("sudah closing"));        
    }
    
    @Test
    @Order(20)
    public void testSaveClosingUpdateSekarang() throws Exception {
        System.out.println(this.getClass().getName() + ": testSaveClosingUpdateSekarang");
        
        PembayaranKasBon transaksi = instance.getById(instance.getRandomIDSudahClosing());        
        transaksi.setTanggal(AppUtils.now());
        
        SQLException exception = assertThrows(SQLException.class, () -> {
            instance.save(transaksi);
        });

        System.out.println("Message : " + exception.getMessage());
        assertTrue(exception.getMessage().contains("sudah closing"));        
    }
    
    @Test
    @Order(25)
    public void testGetById() throws Exception {
        System.out.println(this.getClass().getName() + ": testGetById");
        
        int id = instance.getRandomID();
        PembayaranKasBon result = instance.getById(id);
        assertEquals(result.getId(), id);
    }
    
    @Test
    @Order(30)
    public void testGetByNoRegister() throws Exception {
        System.out.println(this.getClass().getName() + ": testGetByNoRegister");
        
        int id = instance.getRandomID();
        PembayaranKasBon result = instance.getById(id);
        PembayaranKasBon expResult = instance.getByNoRegister(result.getNoRegister());
        
        
        assertEquals(expResult.getId(), result.getId());
        assertEquals(expResult.getNoRegister(), result.getNoRegister());
        assertEquals(expResult.getNominal(), result.getNominal());
        
    }    

    @Test
    @Order(35)
    public void testGetPembayaranKasBonByPage_WithFilter() throws SQLException {
        System.out.println(this.getClass().getName() + ": testGetPembayaranKasBonByPage_WithFilter");
        Integer page = 1;
        Date tglAwal = AppUtils.getTanggalAwalTahun();
        Date tglAkhir = AppUtils.getTanggalAkhirTahun();
        PembayaranKasBon pkb = instance.getById(idSave);
        String filter = pkb.getNoRegister();
        
        List<Map<String, Object>> result = instance.getPembayaranKasBonByPage(page, tglAwal, tglAkhir, filter);
        assertEquals(result.size(),1);
        assertEquals(result.get(0).get("no_register"),pkb.getNoRegister());
    }
    
    @Test
    @Order(40)
    public void testGetPembayaranKasBonByPage_WithoutFilter() throws SQLException {
        System.out.println(this.getClass().getName() + ": testGetPembayaranKasBonByPage_WithoutFilter");
        Integer page = 1;
        Date tglAwal = AppUtils.getTanggalAwalTahun();
        Date tglAkhir = AppUtils.getTanggalAkhirTahun();
        int jmlData = instance.getAllDataCountPeriod(tglAwal, tglAkhir);
        if (jmlData > 20){
            jmlData = 20;
        }
        
        List<Map<String, Object>> result = instance.getPembayaranKasBonByPage(page, tglAwal, tglAkhir, null);
        assertEquals(result.size(),jmlData);
    }    
    
    @Test
    @Order(995)
    public void testDeleteClosing() throws Exception {
        System.out.println(this.getClass().getName() + ": testDeleteClosing");
        
        int idClosing = instance.getRandomIDSudahClosing();
        
        SQLException exception = assertThrows(SQLException.class, () -> {
            instance.delete(idClosing);
        });

        System.out.println("Message : " + exception.getMessage());
        assertTrue(exception.getMessage().contains("sudah closing"));  
        
    }
    
    @Test
    @Order(1000)
    public void testDelete() throws Exception {
        System.out.println(this.getClass().getName() + ": testDelete");
        
        int idKbk = instance.getById(idSave).getKasBonKaryawanId();
        
        assertEquals(instance.getById(idSave).getId(), idSave);
        instance.delete(idSave);
        assertEquals(instance.getById(idSave), null);
        
        KasBonKaryawan kbk = instanceKBK.getById(idKbk);
        assertEquals(kbk.getStatusLunas(), "Belum");
        assert(kbk.getPelunasan() < kbk.getNominal());
    }
    
}
