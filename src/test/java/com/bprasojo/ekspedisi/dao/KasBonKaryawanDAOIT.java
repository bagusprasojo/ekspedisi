/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.bprasojo.ekspedisi.dao;

import com.bprasojo.ekspedisi.model.Bank;
import com.bprasojo.ekspedisi.model.Jurnal;
import com.bprasojo.ekspedisi.model.KasBonKaryawan;
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
public class KasBonKaryawanDAOIT {
    static KasBonKaryawanDAO instance;
    static BankDAO instanceBank;
    static PerkiraanDAO instancePerkiraan;
    static JurnalDAO instanceJurnal;
    static StakeHolderDAO instanceSH;
    
    static int idSave;
    
    public KasBonKaryawanDAOIT() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        instance = new KasBonKaryawanDAO();
        instanceBank = new BankDAO();
        instanceJurnal = new JurnalDAO();
        instanceSH = new StakeHolderDAO();
        instancePerkiraan = new PerkiraanDAO();
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    @Order(1)
    public void testSave() throws Exception {
        System.out.println(this.getClass().getName() + ": testSave");
        
        KasBonKaryawan transaksi = new KasBonKaryawan();
        
        Bank bank = instanceBank.getById(instanceBank.getRandomID());
        transaksi.setBankId(bank.getId());
        transaksi.setKaryawanId(instanceSH.getRandomID());
        transaksi.setKeterangan("keterangan");
        transaksi.setNominal(1000000);
        transaksi.setPerkiraanKasId(bank.getAkun().getId());
        transaksi.setPerkiraanPinjamanId(instancePerkiraan.getRandomID());
        transaksi.setSumberDana("sumberDana");
        transaksi.setTanggal(AppUtils.now());
        transaksi.setUserCreate("userCreate");
        transaksi.setUserUpdate("userUpdate");
        
        instance.save(transaksi);
        idSave = transaksi.getId();
        
        KasBonKaryawan kbk = instance.getById(idSave);
        assertEquals(kbk.getId(), idSave);
        assertEquals(kbk.getBankId(), transaksi.getBankId());
        assertEquals(kbk.getNoRegister(), transaksi.getNoRegister());
        assertEquals(kbk.getKaryawanId(), transaksi.getKaryawanId());
        assertEquals(kbk.getNominal(), transaksi.getNominal());
        
        Jurnal jurnal = instanceJurnal.getByTransaksiId(idSave, transaksi.getClass().getName());
        assertEquals(kbk.getNoRegister(), jurnal.getNoJurnal());
        assertEquals(kbk.getTanggal(), jurnal.getTanggal());
        assertEquals(kbk.getNominal(), jurnal.getDebet());
        assertEquals(kbk.getNominal(), jurnal.getKredit());
        
    }

@Test
    @Order(5)
    public void testSaveUpdate() throws Exception {
        System.out.println(this.getClass().getName() + ": testSaveUpdate");
        
        KasBonKaryawan transaksi = instance.getById(idSave);        
        transaksi.setNominal(65000);        
        transaksi.setKeterangan("Keterangan Update");
        
        instance.save(transaksi);
        idSave = transaksi.getId();
        
        KasBonKaryawan kbk = instance.getById(idSave);
        assertEquals(kbk.getId(),idSave);
        assertEquals(kbk.getKeterangan(),"Keterangan Update");
        assertEquals(kbk.getNominal(),65000);
        
        
        Jurnal jurnal = instanceJurnal.getByTransaksiId(idSave, transaksi.getClass().getName());
        assertEquals(kbk.getNoRegister(), jurnal.getNoJurnal());
        assertEquals(kbk.getTanggal().getTime(), jurnal.getTanggal().getTime());
        assertEquals(kbk.getNominal(), jurnal.getKredit());
        assertEquals(kbk.getNominal(), jurnal.getDebet());
    }
    
    @Test
    @Order(12)
    public void testSaveClosingUpdateSekarang() throws Exception {
        System.out.println(this.getClass().getName() + ": testSaveClosingUpdateSekarang");
        
        KasBonKaryawan transaksi = instance.getById(instance.getRandomIDSudahClosing());        
        transaksi.setTanggal(AppUtils.now());
        
        SQLException exception = assertThrows(SQLException.class, () -> {
            instance.save(transaksi);
        });

        System.out.println("Message : " + exception.getMessage());
        assertTrue(exception.getMessage().contains("sudah closing"));        
    }
    
    @Test
    @Order(10)
    public void testSaveClosing() throws Exception {
        System.out.println(this.getClass().getName() + ": testSaveClosing");
        
        KasBonKaryawan transaksi = instance.getById(idSave);        
        transaksi.setTanggal(instance.getLastClosingDate());
        
        SQLException exception = assertThrows(SQLException.class, () -> {
            instance.save(transaksi);
        });

        System.out.println("Message : " + exception.getMessage());
        assertTrue(exception.getMessage().contains("sudah closing"));        
    }
    
    @Test
    @Order(13)
    public void testSaveDibayar() throws Exception {
        System.out.println(this.getClass().getName() + ": testSaveDibayar");
        
        
        KasBonKaryawan transaksi = instance.getById(instance.getRandomIDSudahDibayar());        
        
        SQLException exception = assertThrows(SQLException.class, () -> {
            instance.save(transaksi);
        });

        System.out.println("Message : " + exception.getMessage());
        assertTrue(exception.getMessage().contains("sudah dibayar"));        
    }

    
    @Test
    @Order(15)
    public void testGetById() throws Exception {
        System.out.println(this.getClass().getName() +  ": testGetById");
        int id = instance.getRandomID();
        KasBonKaryawan result = instance.getById(id);
        assertEquals(result.getId(), id);
        
    }

    @Test
    @Order(20)
    public void testGetByNoBukti() throws Exception {
        System.out.println(this.getClass().getName() + ": testGetByNoBukti");
        int id = instance.getRandomID();
        KasBonKaryawan result = instance.getById(id);
        KasBonKaryawan expResult = instance.getByNoRegister(result.getNoRegister());
        assertEquals(expResult.getId(), result.getId());
        assertEquals(expResult.getNoRegister(), result.getNoRegister());
    }

    @Test
    @Order(25)
    public void testGetKasBonByPage_WithFilter() throws SQLException {
        System.out.println(this.getClass().getName() + ": testGetKasBonByPage_WithFilter");
        Integer page = 1;
        Date tglAwal = AppUtils.getTanggalAwalTahun();
        Date tglAkhir = AppUtils.getTanggalAkhirTahun();
        KasBonKaryawan kbk = instance.getById(idSave);
        String filter = kbk.getNoRegister();
        
        List<Map<String, Object>> result = instance.getKasBonByPage(page, tglAwal, tglAkhir, filter);
        assertEquals(result.size(),1);
        assertEquals(result.get(0).get("no_register"),kbk.getNoRegister());
    }
    
    @Test
    @Order(30)
    public void testGetKasBonByPage_WithoutFilter() throws SQLException {
        System.out.println(this.getClass().getName() + ": testGetKasBonByPage_WithoutFilter");
        Integer page = 1;
        Date tglAwal = AppUtils.getTanggalAwalTahun();
        Date tglAkhir = AppUtils.getTanggalAkhirTahun();
        int jmlData = instance.getAllDataCountPeriod(tglAwal, tglAkhir);
        if (jmlData > 20){
            jmlData = 20;
        }
        
        List<Map<String, Object>> result = instance.getKasBonByPage(page, tglAwal, tglAkhir, null);
        assertEquals(result.size(),jmlData);
    }

    @Test
    @Order(35)
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
    @Order(40)
    public void testDeleteDibayar() throws Exception {
        System.out.println(this.getClass().getName() + ": testDeleteDibayar");
        int id = instance.getRandomIDSudahDibayar();
        
        SQLException exception = assertThrows(SQLException.class, () -> {
            instance.delete(id);
        });

        System.out.println("Message : " + exception.getMessage());
        assertTrue(exception.getMessage().contains("sudah dibayar"));        
        
    }    
    
    @Test
    @Order(1000)
    public void testDelete() throws Exception {
        System.out.println(this.getClass().getName() + ": testDelete");
        assertEquals(instance.getById(idSave).getId(), idSave);
        instance.delete(idSave);
        assertEquals(instance.getById(idSave), null);
    }


    
    
}
