/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.bprasojo.ekspedisi.dao;

import com.bprasojo.ekspedisi.model.Jurnal;
import com.bprasojo.ekspedisi.model.TagihanCustomer;
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
public class TagihanCustomerDAOIT {
    static TagihanCustomerDAO instance;
    static PerkiraanDAO instancePerkiraan;
    static JurnalDAO instanceJurnal;
    static StakeHolderDAO instanceSH;
    
    static int idSave;
    public TagihanCustomerDAOIT() {
    }
    
    @BeforeAll
    public static void setUpClass() {
        instance = new TagihanCustomerDAO();
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
        TagihanCustomer transaksi = new TagihanCustomer();
        
        transaksi.setCustomerId(instanceSH.getRandomID());
        transaksi.setKeterangan("Keterangan");
        transaksi.setNilaiPekerjaan(2000000);
        transaksi.setPekerjaan("Angkutan LPG Januari 2025 kota surakarta");
        transaksi.setPerkiraanPiutangId(instancePerkiraan.getRandomID());
        transaksi.setPpn((int) (transaksi.getNilaiPekerjaan() * 0.11));
        transaksi.setPpnPersen(12);
        transaksi.setTanggal(AppUtils.now());
        transaksi.setTerbilang(AppUtils.terbilang(transaksi.getTotal()));
        transaksi.setUserCreate("userCreate");
        transaksi.setUserUpdate("userUpdate");
                
        instance.save(transaksi);
        idSave = transaksi.getId();
        
        TagihanCustomer tc = instance.getById(idSave);
        assertEquals(tc.getId(), idSave);
        assertEquals(tc.getPerkiraanPiutangId(), transaksi.getPerkiraanPiutangId());
        assertEquals(tc.getNoInvoice(), transaksi.getNoInvoice());
        assertEquals(tc.getNilaiPekerjaan(), transaksi.getNilaiPekerjaan());
        assertEquals(tc.getPpn(), transaksi.getPpn());
        assertEquals(tc.getStatusLunas(), "Belum");
        assertEquals(tc.getPelunasan(), 0);
        
        Jurnal jurnal = instanceJurnal.getByTransaksiId(idSave, transaksi.getClass().getName());
        assertEquals(tc.getNoInvoice(), jurnal.getNoJurnal());
        assertEquals(tc.getTanggal(), jurnal.getTanggal());
        assertEquals(tc.getTotal(), jurnal.getDebet());
        assertEquals(tc.getTotal(), jurnal.getKredit());
    }
    
    @Test
    @Order(5)
    public void testSaveUpdate() throws Exception {
        System.out.println(this.getClass().getName() + ": testSave");
        TagihanCustomer transaksi = instance.getById(idSave);
        
        transaksi.setCustomerId(instanceSH.getRandomID());
        transaksi.setKeterangan("Keterangan Update");
        transaksi.setNilaiPekerjaan(200000);
        transaksi.setPekerjaan("Angkutan LPG Januari 2025 kota Surabaya");
        transaksi.setPerkiraanPiutangId(instancePerkiraan.getRandomID());
        transaksi.setPpn((int) (transaksi.getNilaiPekerjaan() * 0.11));
        transaksi.setPpnPersen(12);
        transaksi.setTanggal(AppUtils.now());
        transaksi.setTerbilang(AppUtils.terbilang(transaksi.getTotal()));
        transaksi.setUserCreate("userCreate");
        transaksi.setUserUpdate("userUpdate");
                
        instance.save(transaksi);
        idSave = transaksi.getId();
        
        TagihanCustomer tc = instance.getById(idSave);
        assertEquals(tc.getId(), idSave);
        assertEquals(tc.getKeterangan(), transaksi.getKeterangan());
        assertEquals(tc.getNoInvoice(), transaksi.getNoInvoice());
        assertEquals(tc.getNilaiPekerjaan(), transaksi.getNilaiPekerjaan());
        assertEquals(tc.getPpn(), transaksi.getPpn());
        assertEquals(tc.getStatusLunas(), "Belum");
        assertEquals(tc.getPelunasan(), 0);
        
        Jurnal jurnal = instanceJurnal.getByTransaksiId(idSave, transaksi.getClass().getName());
        assertEquals(tc.getNoInvoice(), jurnal.getNoJurnal());
        assertEquals(tc.getTanggal(), jurnal.getTanggal());
        assertEquals(tc.getTotal(), jurnal.getDebet());
        assertEquals(tc.getTotal(), jurnal.getKredit());
    }

    @Test
    @Order(10)
    public void testSaveClosing() throws Exception {
        System.out.println(this.getClass().getName() + ": testSaveClosing");
        
        TagihanCustomer transaksi = instance.getById(idSave);        
        transaksi.setTanggal(instance.getLastClosingDate());
        
        SQLException exception = assertThrows(SQLException.class, () -> {
            instance.save(transaksi);
        });

        System.out.println("Message : " + exception.getMessage());
        assertTrue(exception.getMessage().contains("sudah closing"));        
    }
    
    @Test
    @Order(15)
    public void testSaveClosingUpdateSekarang() throws Exception {
        System.out.println(this.getClass().getName() + ": testSaveClosingUpdateSekarang");
        
        TagihanCustomer transaksi = instance.getById(instance.getRandomIDSudahClosing());        
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
        TagihanCustomer result = instance.getById(id);
        assertEquals(result.getId(), id);
    }
    
    @Test
    @Order(30)
    public void testGetByNoInvoice() throws Exception {
        System.out.println(this.getClass().getName() + ": testGetByNoInvoice");
        
        int id = instance.getRandomID();
        TagihanCustomer result = instance.getById(id);
        TagihanCustomer expResult = instance.getByNoInvoice(result.getNoInvoice());
        
        
        assertEquals(expResult.getId(), result.getId());
        assertEquals(expResult.getNoInvoice(), result.getNoInvoice());
        assertEquals(expResult.getNilaiPekerjaan(), result.getNilaiPekerjaan());
        assertEquals(expResult.getPpn(), result.getPpn());
        assertEquals(expResult.getTotal(), result.getTotal());
    }

    @Test
    @Order(35)
    public void testGetTagihanCustomerByPage_WithFilter() throws SQLException {
        System.out.println(this.getClass().getName() + ": testGetTagihanCustomerByPage_WithFilter");
        Integer page = 1;
        Date tglAwal = AppUtils.getTanggalAwalTahun();
        Date tglAkhir = AppUtils.getTanggalAkhirTahun();
        TagihanCustomer tc = instance.getById(idSave);
        String filter = tc.getNoInvoice();
        
        List<Map<String, Object>> result = instance.getTagihanCustomerByPage(page, tglAwal, tglAkhir, filter);
        assertEquals(result.size(),1);
        assertEquals(result.get(0).get("no_invoice"),tc.getNoInvoice());
    }
    
    @Test
    @Order(40)
    public void testGetTagihanCustomerByPage_WithoutFilter() throws SQLException {
        System.out.println(this.getClass().getName() + ": testGetTagihanCustomerByPage_WithoutFilter");
        Integer page = 1;
        Date tglAwal = AppUtils.getTanggalAwalTahun();
        Date tglAkhir = AppUtils.getTanggalAkhirTahun();
        int jmlData = instance.getAllDataCountPeriod(tglAwal, tglAkhir);
        if (jmlData > 20){
            jmlData = 20;
        }
        
        List<Map<String, Object>> result = instance.getTagihanCustomerByPage(page, tglAwal, tglAkhir, null);
        assertEquals(result.size(),jmlData);
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
        assertEquals(instance.getById(idSave).getId(), idSave);
        instance.delete(idSave);
        assertEquals(instance.getById(idSave), null);
    }
    
}
