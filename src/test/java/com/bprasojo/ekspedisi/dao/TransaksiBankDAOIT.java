/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.bprasojo.ekspedisi.dao;

import com.bprasojo.ekspedisi.model.Bank;
import com.bprasojo.ekspedisi.model.JenisTransaksi;
import com.bprasojo.ekspedisi.model.Jurnal;
import com.bprasojo.ekspedisi.model.TransaksiBank;
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
public class TransaksiBankDAOIT {
    static TransaksiBankDAO instance;
    static BankDAO instanceBank;
//    static ArmadaDAO instanceArmada;
    static PerkiraanDAO instancePerkiraan;
    static JurnalDAO instanceJurnal;
    static JenisTransaksiDAO instanceJT;
    
    static int idSave;
    public TransaksiBankDAOIT() {
    }
    
    @BeforeAll
    public static void setUpClass() {
        instance = new TransaksiBankDAO();
        instanceBank = new BankDAO();
        instancePerkiraan = new PerkiraanDAO();
        instanceJurnal = new JurnalDAO();
        instanceJT = new JenisTransaksiDAO();
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
        
        TransaksiBank transaksi = new TransaksiBank();
        JenisTransaksi jt = instanceJT.getByKode("08");
        transaksi.setAkunTujuanId(jt.getAkunId());
        
        Bank bank = instanceBank.getById(instanceBank.getRandomID());
        transaksi.setBankUtamaId(bank.getId());
        transaksi.setAkunUtamaId(bank.getAkun().getId());
        transaksi.setBiayaAdmBank(0);
        transaksi.setDebet(6500);
        transaksi.setKredit(0);
        transaksi.setJenisTransaksiId(jt.getId());
        transaksi.setTanggal(AppUtils.now());
        transaksi.setUraian("Uraian");
        transaksi.setUserCreate("userCreate");
        transaksi.setUserUpdate("userUpdate");
        
        instance.save(transaksi);
        idSave = transaksi.getId();
        
        TransaksiBank tb = instance.getById(idSave);
        assertEquals(tb.getId(),idSave);
        assertEquals(tb.getUraian(),"Uraian");
        assertEquals(tb.getDebet(),6500);
        assertEquals(tb.getBankUtamaId(),bank.getId());
        
        Jurnal jurnal = instanceJurnal.getByTransaksiId(idSave, transaksi.getClass().getName());
        assertEquals(tb.getNoBukti(), jurnal.getNoJurnal());
        assertEquals(tb.getTanggal(), jurnal.getTanggal());
        assertEquals(tb.getDebet(), jurnal.getKredit());
        assertEquals(tb.getKredit(), 0);
    }
    
    @Test
    @Order(5)
    public void testSaveUpdate() throws Exception {
        System.out.println(this.getClass().getName() + ": testSaveUpdate");
        
        TransaksiBank transaksi = instance.getById(idSave);        
        transaksi.setDebet(65000);        
        transaksi.setUraian("Uraian Update");
        
        instance.save(transaksi);
        idSave = transaksi.getId();
        
        TransaksiBank tb = instance.getById(idSave);
        assertEquals(tb.getId(),idSave);
        assertEquals(tb.getUraian(),"Uraian Update");
        assertEquals(tb.getDebet(),65000);
        
        
        Jurnal jurnal = instanceJurnal.getByTransaksiId(idSave, transaksi.getClass().getName());
        assertEquals(tb.getNoBukti(), jurnal.getNoJurnal());
        assertEquals(tb.getTanggal(), jurnal.getTanggal());
        assertEquals(tb.getDebet(), jurnal.getKredit());
        assertEquals(tb.getKredit(), 0);
    }
    
    @Test
    @Order(10)
    public void testSaveClosing() throws Exception {
        System.out.println(this.getClass().getName() + ": testSaveClosing");
        
        TransaksiBank transaksi = instance.getById(idSave);        
        transaksi.setTanggal(instance.getLastClosingDate());
        
        SQLException exception = assertThrows(SQLException.class, () -> {
            instance.save(transaksi);
        });

        System.out.println("Message : " + exception.getMessage());
        assertTrue(exception.getMessage().contains("sudah closing"));        
    }

    
@Test
    @Order(15)
    public void testGetById() throws Exception {
        System.out.println(this.getClass().getName() +  ": testGetById");
        int id = instance.getRandomID();
        TransaksiBank result = instance.getById(id);
        assertEquals(result.getId(), id);
        
    }

    @Test
    @Order(20)
    public void testGetByNoBukti() throws Exception {
        System.out.println(this.getClass().getName() + ": testGetByNoBukti");
        int id = instance.getRandomID();
        TransaksiBank result = instance.getById(id);
        TransaksiBank expResult = instance.getByNoBukti(result.getNoBukti());
        assertEquals(expResult.getId(), result.getId());
        assertEquals(expResult.getNoBukti(), result.getNoBukti());
    }

    @Test
    @Order(25)
    public void testGetTransaksiPembelianBBMByPage_WithFilter() throws SQLException {
        System.out.println(this.getClass().getName() + ": testGetTransaksiPembelianBBMByPage_WithFilter");
        Integer page = 1;
        Date tglAwal = AppUtils.getTanggalAwalTahun();
        Date tglAkhir = AppUtils.getTanggalAkhirTahun();
        TransaksiBank tb = instance.getById(idSave);
        String filter = tb.getNoBukti();
        
        List<Map<String, Object>> result = instance.getTransaksiBankByPage(page, tglAwal, tglAkhir, filter);
        assertEquals(result.size(),1);
        assertEquals(result.get(0).get("no_bukti"),tb.getNoBukti());
    }
    
    @Test
    @Order(30)
    public void testGetTransaksiPembelianBBMByPage_WithoutFilter() throws SQLException {
        System.out.println(this.getClass().getName() + ": testGetTransaksiPembelianBBMByPage_WithoutFilter");
        Integer page = 1;
        Date tglAwal = AppUtils.getTanggalAwalTahun();
        Date tglAkhir = AppUtils.getTanggalAkhirTahun();
        int jmlData = instance.getAllDataCountPeriod(tglAwal, tglAkhir);
        if (jmlData > 20){
            jmlData = 20;
        }
        
        List<Map<String, Object>> result = instance.getTransaksiBankByPage(page, tglAwal, tglAkhir, null);
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
    @Order(1000)
    public void testDelete() throws Exception {
        System.out.println(this.getClass().getName() + ": testDelete");
        
        assertEquals(instance.getById(idSave).getId(), idSave);
        instance.delete(idSave);
        assertEquals(instance.getById(idSave), null);
    }
    
}
