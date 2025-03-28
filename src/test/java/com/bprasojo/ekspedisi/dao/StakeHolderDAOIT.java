/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.bprasojo.ekspedisi.dao;

import com.bprasojo.ekspedisi.model.StakeHolder;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(OrderAnnotation.class)
public class StakeHolderDAOIT {
    static StakeHolderDAO instance;
    static String kode_sh;

    public StakeHolderDAOIT() {
    }

    @BeforeAll
    public static void setUpClass() {
        instance = new StakeHolderDAO();
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

    private StakeHolder save() throws SQLException {
        StakeHolder stakeholder = new StakeHolder();
        stakeholder.setAlamat("alamat");
        stakeholder.setJenis("Customer");
        stakeholder.setKodePos("kode pos");
        stakeholder.setKota("kota");
        stakeholder.setLokasiKerja("Lokasi Kerja");
        stakeholder.setNama("nama");
        stakeholder.setNoKtp("nik");
        stakeholder.setTelp("telp");
        stakeholder.setUserCreate("user");
        stakeholder.setUserUpdate("user");

        instance.save(stakeholder);

        kode_sh = stakeholder.getKode();
        return stakeholder;
    }

    @Test
    @Order(1)
    public void testSave() throws Exception {
        System.out.println("Stake Holder : save");
        StakeHolder stakeholder = save();

        String kode = stakeholder.getKode();
        StakeHolder expResult = instance.getByKode(kode);

        assertEquals(expResult.getId(), stakeholder.getId());
    }

    @Test
    @Order(2)
    public void testSaveUpdate() throws Exception {
        System.out.println("Stake Holder : save update");
        
        StakeHolder sh = instance.getByKode(kode_sh);

        String alamatBaru = "Ini alamat baru";
        sh.setAlamat(alamatBaru);
        instance.save(sh);

        StakeHolder sh2 = instance.getByKode(kode_sh);

        assertEquals(sh2.getAlamat(), alamatBaru);
    }

    @Test
    @Order(3)
    public void testGetById() throws Exception {
        System.out.println("getById");
        int idSH = instance.getRandomID();

        StakeHolder expResult = instance.getById(idSH);

        assertEquals(expResult.getId(), idSH);
    }

    @Test
    @Order(4)
    public void testGetByKode() throws Exception {
        System.out.println("Stake Holder : get by code");
        StakeHolder expResult = instance.getByKode(kode_sh);

        assertEquals(expResult.getKode(), kode_sh);
    }

    @Test
    @Order(500)
    public void testZDelete() throws Exception {
        System.out.println("Stake Holder : delete");
        
        StakeHolder stakeholder = instance.getByKode(kode_sh);

        int id = stakeholder.getId();
        StakeHolder expResult = instance.getById(id);

        assertEquals(expResult.getId(), stakeholder.getId());
        instance.delete(id);
        expResult = instance.getById(id);
        assertEquals(expResult, null);
    }
    
    @Test
    @Order(6)
    public void getStakeHolderByPage_withoutFilter() throws Exception {
        System.out.println("Stake Holder : getStakeHolderByPage_withoutFilter");
        
        List<Map<String, Object>> result = instance.getStakeHolderByPage(1, null, "Customer");
        assertEquals(instance.getAllSHCount("Customer"), result.size());
        
        result = instance.getStakeHolderByPage(1, null, "Karyawan");
        assertEquals(instance.getAllSHCount("Karyawan"), result.size());
    }
    
    @Test
    @Order(7)
    public void getStakeHolderByPage_withFilter() throws Exception {
        System.out.println("Stake Holder : getStakeHolderByPage_withFilter");
        
        int idSH = instance.getRandomID();
        StakeHolder sh = instance.getById(idSH);
        
        List<Map<String, Object>> result = instance.getStakeHolderByPage(1, sh.getKode(), sh.getJenis());
        assertEquals(1, result.size());
        assertEquals(sh.getKode(), result.get(0).get("kode"));
        assertEquals(sh.getNama(), result.get(0).get("nama"));
        assertEquals(sh.getId(), result.get(0).get("id"));
        
        
    }
}
