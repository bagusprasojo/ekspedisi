/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.bprasojo.ekspedisi.dao;

import static com.bprasojo.ekspedisi.dao.PembayaranKasBonDAOIT.instance;
import com.bprasojo.ekspedisi.model.Bank;
import com.bprasojo.ekspedisi.model.Jurnal;
import com.bprasojo.ekspedisi.model.PembayaranTagihanCustomer;
import com.bprasojo.ekspedisi.model.TagihanCustomer;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
public class PembayaranTagihanCustomerDAOIT {
    static PembayaranTagihanCustomerDAO instance;
    static KasBonKaryawanDAO instanceKBK;
    static BankDAO instanceBank;
    static PerkiraanDAO instancePerkiraan;
    static JurnalDAO instanceJurnal;
    static StakeHolderDAO instanceSH;
    static TagihanCustomerDAO instanceTC;
    
    static int idSave;
    
    public PembayaranTagihanCustomerDAOIT() {
    }
    
    @BeforeAll
    public static void setUpClass() {
        instance = new PembayaranTagihanCustomerDAO();
        instanceKBK = new KasBonKaryawanDAO();
        instanceBank = new BankDAO();
        instancePerkiraan = new PerkiraanDAO();
        instanceJurnal = new JurnalDAO();
        instanceSH = new StakeHolderDAO();
        instanceTC = new TagihanCustomerDAO();
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
        PembayaranTagihanCustomer transaksi = new PembayaranTagihanCustomer();
        
        Bank bank = instanceBank.getById(instanceBank.getRandomID());
        transaksi.setBankId(bank.getId());
        transaksi.setKeterangan("keterangan");
        transaksi.setNominalKas(100);
        transaksi.setPerkiraanKasId(bank.getAkun().getId());
        transaksi.setPerkiraanPphId(instancePerkiraan.getRandomID());
        transaksi.setPph(10);
        transaksi.setPphPersen(2);
        transaksi.setPpn(10);
        transaksi.setSumberDana("sumberDana");
        
        TagihanCustomer tc = instanceTC.getById(instanceTC.getRandomIDBelumLunas());
        transaksi.setTagihanCustomerId(tc.getId());
        transaksi.setTanggal(AppUtils.now());
        transaksi.setTerbilang(AppUtils.terbilang(transaksi.getTotal()));
        transaksi.setUserCreate("userCreate");
        transaksi.setUserUpdate("userUpdate");
        
        instance.save(transaksi);
        idSave = transaksi.getId();
        
        PembayaranTagihanCustomer ptc = instance.getById(idSave);
        assertEquals(ptc.getId(), idSave);
        assertEquals(ptc.getBankId(), transaksi.getBankId());
        assertEquals(ptc.getNoRegister(), transaksi.getNoRegister());
        assertEquals(ptc.getPerkiraanKasId(), transaksi.getPerkiraanKasId());
        assertEquals(ptc.getTotal(), transaksi.getTotal());
        
        TagihanCustomer tcCek = instanceTC.getById(tc.getId());
        assertEquals(tcCek.getPelunasan(), tc.getPelunasan() + transaksi.getTotal());
        
        Jurnal jurnal = instanceJurnal.getByTransaksiId(idSave, transaksi.getClass().getName());
        assertEquals(transaksi.getNoRegister(), jurnal.getNoJurnal());
//        assertEquals(transaksi.getTanggal(), jurnal.getTanggal());
        assertEquals(transaksi.getTotal(), jurnal.getDebet());
        assertEquals(transaksi.getTotal(), jurnal.getKredit());
    }
    
    @Test
    @Order(5)
    public void testSaveUpdate() throws Exception {
        System.out.println(this.getClass().getName() + ": testSave");
        PembayaranTagihanCustomer transaksi = instance.getById(idSave);
        
        transaksi.setKeterangan("keterangan update");
        
        instance.save(transaksi);
        idSave = transaksi.getId();
        
        PembayaranTagihanCustomer ptc = instance.getById(idSave);
        assertEquals(ptc.getKeterangan(), "keterangan update");
        
        Jurnal jurnal = instanceJurnal.getByTransaksiId(idSave, transaksi.getClass().getName());
        assertEquals(transaksi.getNoRegister(), jurnal.getNoJurnal());
        assertEquals(transaksi.getTanggal(), jurnal.getTanggal());
        assertEquals(transaksi.getTotal(), jurnal.getDebet());
        assertEquals(transaksi.getTotal(), jurnal.getKredit());
    }

    @Test
    @Order(8)
    public void testSaveUpdateNoBuktiSama() throws Exception {
        System.out.println(this.getClass().getName() + ": testSaveUpdateNoBuktiSama");
        
        PembayaranTagihanCustomer transaksi = instance.getById(idSave);        
        
        int idRandom = transaksi.getId(); 
        while (idRandom == transaksi.getId()) {            
            idRandom = instance.getRandomID();
        }
        
        PembayaranTagihanCustomer ptc = instance.getById(idRandom);        
        transaksi.setNoRegister(ptc.getNoRegister());
        
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
        PembayaranTagihanCustomer transaksi = instance.getById(idSave);
        
        TagihanCustomer tc = instanceTC.getById(transaksi.getTagihanCustomerId());
        int bayar = tc.getTotal()- tc.getPelunasan() + transaksi.getTotal();
        
        System.out.println(this.getClass().getName() + "Bayar Nominal TC " + tc.getTotal());
        System.out.println(this.getClass().getName() + "Bayar Pelunasan TC " + tc.getPelunasan());
        System.out.println(this.getClass().getName() + "Niminal PTC " + transaksi.getTotal());
        System.out.println(this.getClass().getName() + "Niminal Bayar " + bayar);
        
        transaksi.setNominalKas(bayar - transaksi.getPph());
        instance.save(transaksi);
        
        
        tc = instanceTC.getById(transaksi.getTagihanCustomerId());
        assertEquals(tc.getTotal(), tc.getPelunasan());
        assertEquals(tc.getStatusLunas(), "Lunas");
        
    }
    
    @Test
    @Order(15)
    public void testSaveClosing() throws Exception {
        System.out.println(this.getClass().getName() + ": testSaveClosing");
        
        PembayaranTagihanCustomer transaksi = instance.getById(idSave);        
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
        
        PembayaranTagihanCustomer transaksi = instance.getById(instance.getRandomIDSudahClosing());        
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
        PembayaranTagihanCustomer result = instance.getById(id);
        assertEquals(result.getId(), id);
    }
    
    @Test
    @Order(30)
    public void testGetByNoRegister() throws Exception {
        System.out.println(this.getClass().getName() + ": testGetByNoRegister");
        
        int id = instance.getRandomID();
        PembayaranTagihanCustomer result = instance.getById(id);
        PembayaranTagihanCustomer expResult = instance.getByNoRegister(result.getNoRegister());
        
        
        assertEquals(expResult.getId(), result.getId());
        assertEquals(expResult.getNoRegister(), result.getNoRegister());
        assertEquals(expResult.getTotal(), result.getTotal());
        
    }

    
    @Test
    @Order(35)
    public void testDetPembayaranTagihanCustomerByPage_WithFilter() throws SQLException {
        System.out.println(this.getClass().getName() + ": testDetPembayaranTagihanCustomerByPage_WithFilter");
        Integer page = 1;
        Date tglAwal = AppUtils.getTanggalAwalTahun();
        Date tglAkhir = AppUtils.getTanggalAkhirTahun();
        PembayaranTagihanCustomer ptc = instance.getById(idSave);
        String filter = ptc.getNoRegister();
        
        List<Map<String, Object>> result = instance.getPembayaranTagihanCustomerByPage(page, tglAwal, tglAkhir, filter);
        assertEquals(result.size(),1);
        assertEquals(result.get(0).get("no_register"),ptc.getNoRegister());
    }
    
    @Test
    @Order(40)
    public void testDetPembayaranTagihanCustomerByPage_WithoutFilter() throws SQLException {
        System.out.println(this.getClass().getName() + ": testDetPembayaranTagihanCustomerByPage_WithoutFilter");
        Integer page = 1;
        Date tglAwal = AppUtils.getTanggalAwalTahun();
        Date tglAkhir = AppUtils.getTanggalAkhirTahun();
        int jmlData = instance.getAllDataCountPeriod(tglAwal, tglAkhir);
        if (jmlData > 20){
            jmlData = 20;
        }
        
        List<Map<String, Object>> result = instance.getPembayaranTagihanCustomerByPage(page, tglAwal, tglAkhir, null);
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
        int idTC = instance.getById(idSave).getTagihanCustomerId();
        
        assertEquals(instance.getById(idSave).getId(), idSave);
        instance.delete(idSave);
        assertEquals(instance.getById(idSave), null);
        
        TagihanCustomer tc = instanceTC.getById(idTC);
        assertEquals(tc.getStatusLunas(), "Belum");
        assert(tc.getPelunasan() < tc.getTotal());
    }

    
}
