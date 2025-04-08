/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.bprasojo.ekspedisi.dao;

import com.bprasojo.ekspedisi.model.Jurnal;
import com.bprasojo.ekspedisi.model.TransaksiPembelianBBM;
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
public class TransaksiPembelianBBMDAOIT {
    static TransaksiPembelianBBMDAO instance;
    static BankDAO instanceBank;
    static ArmadaDAO instanceArmada;
    static JurnalDAO instanceJurnal;
    static StakeHolderDAO instanceSH;
    
    static int idSave;
    public TransaksiPembelianBBMDAOIT() {
    }
    
    @BeforeAll
    public static void setUpClass() {
        instance = new TransaksiPembelianBBMDAO();
        instanceArmada = new ArmadaDAO();
        instanceBank = new BankDAO();
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
        TransaksiPembelianBBM transaksi = new TransaksiPembelianBBM();
        transaksi.setArmadaId(instanceArmada.getRandomID());
        transaksi.setBankId(instanceBank.getRandomID());
        transaksi.setDriverId(instanceSH.getRandomID());
        transaksi.setKeterangan("Keterangan");
        transaksi.setKmSekarang(100);
        transaksi.setKmTerakhir(110);
        transaksi.setNominalBBM(100000);
        transaksi.setTanggal(AppUtils.now());
        transaksi.setUserCreate("userCreate");
        transaksi.setUserUpdate("userUpdate");
        
        instance.save(transaksi);
        idSave = transaksi.getId();
        
        TransaksiPembelianBBM bbm = instance.getById(idSave);
        assertEquals(bbm.getId(), idSave);
        assertEquals(bbm.getArmadaId(), transaksi.getArmadaId());
        assertEquals(bbm.getBankId(), transaksi.getBankId());
        assertEquals(bbm.getDriverId(), transaksi.getDriverId());
        assertEquals(bbm.getNoBukti(), transaksi.getNoBukti());
        
        Jurnal jurnal = instanceJurnal.getByTransaksiId(idSave, transaksi.getClass().getName());
        assertEquals(bbm.getNoBukti(), jurnal.getNoJurnal());
        assertEquals(bbm.getTanggal(), jurnal.getTanggal());
        assertEquals(bbm.getNominalBBM(), jurnal.getDebet());
        assertEquals(bbm.getNominalBBM(), jurnal.getKredit());
    }
    
    @Test
    @Order(5)
    public void testSaveClosing() throws Exception {
        System.out.println(this.getClass().getName() + ": testSaveClosing");
        TransaksiPembelianBBM transaksi = new TransaksiPembelianBBM();
        transaksi.setArmadaId(instanceArmada.getRandomID());
        transaksi.setBankId(instanceBank.getRandomID());
        transaksi.setDriverId(instanceSH.getRandomID());
        transaksi.setKeterangan("Keterangan");
        transaksi.setKmSekarang(100);
        transaksi.setKmTerakhir(110);
        transaksi.setNominalBBM(100000);
        transaksi.setTanggal(instance.getLastClosingDate());
        transaksi.setUserCreate("userCreate");
        transaksi.setUserUpdate("userUpdate");
        
        SQLException exception = assertThrows(SQLException.class, () -> {
            instance.save(transaksi);
        });

        System.out.println("Message : " + exception.getMessage());
        assertTrue(exception.getMessage().contains("sudah closing"));        
    }
    
    @Test
    @Order(8)
    public void testSaveClosingUpdateSekarang() throws Exception {
        System.out.println(this.getClass().getName() + ": testSaveClosingUpdateSekarang");
        TransaksiPembelianBBM transaksi = instance.getById(instance.getRandomIDSudahClosing());
        transaksi.setTanggal(AppUtils.now());
        
        SQLException exception = assertThrows(SQLException.class, () -> {
            instance.save(transaksi);
        });

        System.out.println("Message : " + exception.getMessage());
        assertTrue(exception.getMessage().contains("sudah closing"));        
    }
    
    @Test
    @Order(10)
    public void testSaveUpdate() throws Exception {
        System.out.println(this.getClass().getName() + ": testSaveUpdate");
        TransaksiPembelianBBM transaksi = instance.getById(idSave);
        transaksi.setArmadaId(instanceArmada.getRandomID());
        transaksi.setBankId(instanceBank.getRandomID());
        transaksi.setDriverId(instanceSH.getRandomID());
        transaksi.setKeterangan("Keterangan Update");
        transaksi.setKmSekarang(1000);
        transaksi.setKmTerakhir(1100);
        transaksi.setNominalBBM(1000000);
        transaksi.setTanggal(AppUtils.now());
        transaksi.setUserCreate("userCreate");
        transaksi.setUserUpdate("userUpdate");
        
        instance.save(transaksi);
        idSave = transaksi.getId();
        
        TransaksiPembelianBBM bbm = instance.getById(idSave);
        assertEquals(bbm.getId(), idSave);
        assertEquals(bbm.getKeterangan(), "Keterangan Update");
        assertEquals(bbm.getKmSekarang(), 1000);
        assertEquals(bbm.getKmTerakhir(), 1100);
        assertEquals(bbm.getNominalBBM(), 1000000);
        
        Jurnal jurnal = instanceJurnal.getByTransaksiId(idSave, transaksi.getClass().getName());
        assertEquals(bbm.getNoBukti(), jurnal.getNoJurnal());
        assertEquals(bbm.getTanggal(), jurnal.getTanggal());
        assertEquals(bbm.getNominalBBM(), jurnal.getDebet());
        assertEquals(bbm.getNominalBBM(), jurnal.getKredit());
    }

    @Test
    @Order(12)
    public void testSaveUpdateNoBuktiSama() throws Exception {
        System.out.println(this.getClass().getName() + ": testSaveUpdateNoBuktiSama");
        
        TransaksiPembelianBBM transaksi = instance.getById(idSave);        
        
        int idRandom = transaksi.getId(); 
        while (idRandom == transaksi.getId()) {            
            idRandom = instance.getRandomID();
        }
        
        TransaksiPembelianBBM tpb = instance.getById(idRandom);        
        transaksi.setNoBukti(tpb.getNoBukti());
        
        SQLException exception = assertThrows(SQLException.class, () -> {
            instance.save(transaksi);
        });

        System.out.println("Message : " + exception.getMessage());
        assertTrue(exception.getMessage().contains("Duplicate entry"));        
        
    }
    
    @Test
    @Order(15)
    public void testGetById() throws Exception {
        System.out.println(this.getClass().getName() +  ": testGetById");
        int id = instance.getRandomID();
        TransaksiPembelianBBM result = instance.getById(id);
        assertEquals(result.getId(), id);
        
    }

    @Test
    @Order(20)
    public void testGetByNoBukti() throws Exception {
        System.out.println(this.getClass().getName() + ": testGetByNoBukti");
        int id = instance.getRandomID();
        TransaksiPembelianBBM result = instance.getById(id);
        TransaksiPembelianBBM expResult = instance.getByNoBukti(result.getNoBukti());
        assertEquals(expResult.getId(), result.getId());
        assertEquals(expResult.getNoBukti(), result.getNoBukti());
    }

    @Test
    @Order(25)
    public void testGetLastKM() throws Exception {
        System.out.println(this.getClass().getName() + ": testGetLastKM");
        TransaksiPembelianBBM bbm = instance.getById(idSave);
        Integer expResult = bbm.getKmSekarang();        
        Integer result = instance.getLastKM(bbm.getArmada().getNopol());
        assertEquals(expResult, result);        
    }

    @Test
    @Order(30)
    public void testGetTransaksiPembelianBBMByPage_WithFilter() throws SQLException {
        System.out.println(this.getClass().getName() + ": testGetTransaksiPembelianBBMByPage_WithFilter");
        Integer page = 1;
        Date tglAwal = AppUtils.getTanggalAwalTahun();
        Date tglAkhir = AppUtils.getTanggalAkhirTahun();
        TransaksiPembelianBBM bbm = instance.getById(idSave);
        String filter = bbm.getNoBukti();
        
        List<Map<String, Object>> result = instance.getTransaksiPembelianBBMByPage(page, tglAwal, tglAkhir, filter);
        assertEquals(result.size(),1);
        assertEquals(result.get(0).get("no_bukti"),bbm.getNoBukti());
    }
    
    @Test
    @Order(35)
    public void testGetTransaksiPembelianBBMByPage_WithoutFilter() throws SQLException {
        System.out.println(this.getClass().getName() + ": testGetTransaksiPembelianBBMByPage_WithoutFilter");
        Integer page = 1;
        Date tglAwal = AppUtils.getTanggalAwalTahun();
        Date tglAkhir = AppUtils.getTanggalAkhirTahun();
        int jmlData = instance.getAllDataCountPeriod(tglAwal, tglAkhir);
        if (jmlData > 20){
            jmlData = 20;
        }
        
        List<Map<String, Object>> result = instance.getTransaksiPembelianBBMByPage(page, tglAwal, tglAkhir, null);
        assertEquals(result.size(),jmlData);
    }

    @Test
    @Order(40)
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
        assertEquals(instance.getById(idSave).getId(), idSave);
        instance.delete(idSave);
        assertEquals(instance.getById(idSave), null);
    }
    
}
