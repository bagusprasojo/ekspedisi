/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.bprasojo.ekspedisi.dao;

import com.bprasojo.ekspedisi.model.Bank;
import com.bprasojo.ekspedisi.model.Jurnal;
import com.bprasojo.ekspedisi.model.TransaksiKas;
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
public class TransaksiKasDAOIT {
    static TransaksiKasDAO instance;
    static BankDAO instanceBank;
    static ArmadaDAO instanceArmada;
    static PerkiraanDAO instancePerkiraan;
    static JurnalDAO instanceJurnal;
    
    static int idSave;
    public TransaksiKasDAOIT() {
    }
    
    @BeforeAll
    public static void setUpClass() {
        instance = new TransaksiKasDAO();
        instanceArmada = new ArmadaDAO();
        instanceBank = new BankDAO();
        instancePerkiraan = new PerkiraanDAO();
        instanceJurnal = new JurnalDAO();
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
        System.out.println("TransaksiKasDAOTest : save");
        
        TransaksiKas tk = new TransaksiKas();
        Bank bank = instanceBank.getRandomBank();
        
        tk.setBankId(bank.getId());
        tk.setAkunKasId(bank.getAkun().getId());
        tk.setAkunTransaksiId(instancePerkiraan.getRandomID());
        tk.setArmadaId(instanceArmada.getRandomID());
        tk.setKeterangan("keterangan");
        tk.setNominalKeluar(100000);
        tk.setNominalMasuk(0);
        tk.setTanggal(AppUtils.now());
        tk.setUserCreate("user create");
        tk.setUserUpdate("user update");
        
        instance.save(tk);
        idSave = tk.getId();
        System.out.println("TransaksiKasDAOTest_idSave :" + idSave);
        
        TransaksiKas tk2 = instance.getByNoBukti(tk.getNoBukti());
        Jurnal jurnal = instanceJurnal.getByTransaksiId(tk.getId(), tk.getClass().getName());
        
        assertEquals(tk.getId(), tk2.getId());
        assertEquals(tk.getNoBukti(), tk2.getNoBukti());
        assertEquals(tk.getNoBukti(), jurnal.getNoJurnal());
        assertEquals(tk.getNominalKeluar() + tk.getNominalMasuk(), jurnal.getKredit());
        assertEquals(tk.getNominalKeluar() + tk.getNominalMasuk(), jurnal.getDebet());
        
        
    }
    
    @Test
    @Order(2)
    public void testSaveUpdate() throws Exception {
        System.out.println("TransaksiKasDAOTest : testSaveUpdate");
        
        System.out.println("testSaveUpdate_idSave :" + idSave);
        TransaksiKas tk = instance.getById(idSave);
        Bank bank = instanceBank.getRandomBank();
        
        int akunTransaksiId = instancePerkiraan.getRandomID();
        int armadaId = instanceArmada.getRandomID();
        tk.setBankId(bank.getId());
        tk.setAkunKasId(bank.getAkun().getId());
        tk.setAkunTransaksiId(akunTransaksiId);
        tk.setArmadaId(armadaId);
        tk.setKeterangan("keterangan baru");
        tk.setNominalKeluar(5000);
        
        
        instance.save(tk);
        
        TransaksiKas tk2 = instance.getByNoBukti(tk.getNoBukti());
        Jurnal jurnal = instanceJurnal.getByTransaksiId(tk.getId(), tk.getClass().getName());
        
        assertEquals(tk.getId(), tk2.getId());
        assertEquals(tk.getNoBukti(), tk2.getNoBukti());
        assertEquals(tk.getNoBukti(), jurnal.getNoJurnal());
        assertEquals(tk.getNominalKeluar() + tk.getNominalMasuk(), jurnal.getKredit());
        assertEquals(tk.getNominalKeluar() + tk.getNominalMasuk(), jurnal.getDebet());
        assertEquals(tk2.getBankId(), bank.getId());
        assertEquals(tk2.getAkunTransaksiId(), akunTransaksiId);
        assertEquals(tk2.getArmadaId(), armadaId);
        assertEquals(tk2.getNominalKeluar(), 5000);
        
    }

    @Test
    @Order(3)    
    public void testSaveClosing() throws Exception {
        System.out.println("TransaksiKasDAOTest : saveClosing");
        
        TransaksiKas tk = new TransaksiKas();
        Bank bank = instanceBank.getRandomBank();
        
        tk.setBankId(bank.getId());
        tk.setAkunKasId(bank.getAkun().getId());
        tk.setAkunTransaksiId(instancePerkiraan.getRandomID());
        tk.setArmadaId(instanceArmada.getRandomID());
        tk.setKeterangan("keterangan");
        tk.setNominalKeluar(100000);
        tk.setNominalMasuk(0);
        
        Date tgl = instance.getLastClosingDate();
        tk.setTanggal(tgl);
        tk.setUserCreate("user create");
        tk.setUserUpdate("user update");
        
        SQLException exception = assertThrows(SQLException.class, () -> {
            instance.save(tk);
        });

        System.out.println("Message : " + exception.getMessage());
        assertTrue(exception.getMessage().contains("sudah closing"));        
    }
    
    @Test
    @Order(4)    
    public void testSaveClosingUpdateSekarang() throws Exception {
        System.out.println("TransaksiKasDAOTest : testSaveClosingUpdateSekarang");
        
        TransaksiKas tk = instance.getById(instance.getRandomIDSudahClosing());
        tk.setTanggal(AppUtils.now());
        
        SQLException exception = assertThrows(SQLException.class, () -> {
            instance.save(tk);
        });

        System.out.println("Message : " + exception.getMessage());
        assertTrue(exception.getMessage().contains("sudah closing"));        
        
        
        
    }
    
    @Test
    @Order(5)
    public void testGetTransaksiKasByPage_withoutFilter() {
        System.out.println("testGetTransaksiKasByPage_withoutFilter");
        Integer page = 1;
        Date tglAwal = AppUtils.getTanggalAwalTahun();
        Date tglAkhir = AppUtils.getTanggalAkhirTahun();
        String filter = "";
        
        List<Map<String, Object>> result = instance.getTransaksiKasByPage(page, tglAwal, tglAkhir, filter);
        int count = instance.getAllDataCountPeriod(tglAwal, tglAkhir);
        
        if (count > 0){
            count = 20;
        }
        assertEquals(result.size(), count);
        
    }
    
    @Test
    @Order(10)
    public void testGetTransaksiKasByPage_withFilter() throws SQLException {
        System.out.println("testGetTransaksiKasByPage_withFilter");
        Integer page = 1;
        Date tglAwal = AppUtils.getTanggalAwalTahun();
        Date tglAkhir = AppUtils.getTanggalAkhirTahun();
        int idRandom = instance.getRandomIDPeriod(tglAwal, tglAkhir);
        TransaksiKas tk = instance.getById(idRandom);
        String filter = tk.getNoBukti();
        
        List<Map<String, Object>> result = instance.getTransaksiKasByPage(page, tglAwal, tglAkhir, filter);
        
        assertEquals(result.size(), 1);
        assertEquals(result.get(0).get("no_bukti"), tk.getNoBukti());
        assertEquals(result.get(0).get("keterangan"), tk.getKeterangan());
        
    }
    

    @Test
    @Order(15)
    public void testGetById() throws Exception {
        System.out.println(this.getClass().getName() + ": getById");
        int id = instance.getRandomID();
        TransaksiKas result = instance.getById(id);
        assertEquals(id, result.getId());
    }

    @Test
    @Order(20)
    public void testGetByNoBukti() throws Exception {
        System.out.println(this.getClass().getName() + ": getByNoBukti");
        
        TransaksiKas result = instance.getById(instance.getRandomID());
        
        TransaksiKas expResult = instance.getByNoBukti(result.getNoBukti());
        assertEquals(expResult.getId(), result.getId());
        assertEquals(expResult.getTanggal(), result.getTanggal());
        assertEquals(expResult.getNoBukti(), result.getNoBukti());
    }
     
    @Test
    @Order(25)
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
        
        
        instance.delete(idSave);
        TransaksiKas tk = instance.getById(idSave);
        assertEquals(tk, null);
    }
}
