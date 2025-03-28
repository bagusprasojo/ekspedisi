/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.bprasojo.ekspedisi.dao;

import com.bprasojo.ekspedisi.model.Armada;
import java.sql.SQLException;
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
public class ArmadaDAOIT {
    
    static ArmadaDAO instance;
    static long kode_int;
    static int last_id;
    static String last_nopol;
    static StakeHolderDAO shDAO;
    static String test = "TEST";
    
    public ArmadaDAOIT() {
    }
    
    @BeforeAll
    public static void setUpClass() {
        instance = new ArmadaDAO();
        kode_int = System.currentTimeMillis()/1000;
        shDAO = new StakeHolderDAO();
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

    private Armada save() throws SQLException{
        kode_int = kode_int + 1;
        int driver_id = shDAO.getRandomID();
        String no_pol = "AD " + kode_int + " SH";
        
        Armada armada = new Armada();        
        armada.setAlamat(test);
        armada.setDriverId(driver_id);
        armada.setKendaraan(test);
        armada.setKota(test);
        armada.setNopol(no_pol);
        armada.setPemilik(test);
        armada.setTelp(test);
        armada.setUserCreate(test);
        armada.setUserUpdate(test);
               
                
        instance.save(armada);
        last_id = armada.getId();
        last_nopol = armada.getNopol();
        
        System.out.println(no_pol);
        return armada;
    }
    
    @Test
    @Order(1)
    public void testGetArmadaByPage_withoutFilter() throws SQLException {        
        List<Map<String, Object>> result = instance.getArmadaByPage(1, null, 10);

        
        assertEquals(instance.getAllDataCount(), result.size()); // Harus mengembalikan dua baris data
//        assertEquals(1, result.get(0).get("id"));
//        assertEquals("Car 1", result.get(0).get("kendaraan"));
//        assertEquals("Driver A", result.get(0).get("driver"));
    }
    
    @Test
    @Order(2)
    public void testGetArmadaByPage_withFilter() throws SQLException {        
        int idArmada = instance.getRandomID();
        Armada armada = instance.getArmadaById(idArmada);
        List<Map<String, Object>> result = instance.getArmadaByPage(1, armada.getNopol(), 10);

        
        assertEquals(1, result.size()); // Harus mengembalikan dua baris data
        assertEquals(armada.getId(), result.get(0).get("id"));
        assertEquals(armada.getNopol(), result.get(0).get("nopol"));
        assertEquals(armada.getKendaraan(), result.get(0).get("kendaraan"));
    }

    @Test
    @Order(3)
    public void testSave() throws Exception {
        System.out.println("Armada : Save");
        Armada armada = save();
        String no_pol = armada.getNopol();
        System.out.println(no_pol);
        
        Armada armadaTest = instance.getArmadaByNoPol(no_pol);
        
        assertEquals(armadaTest.getId(), armada.getId());
    }
    
    @Test
    @Order(4)
    public void testSaveDataDouble() throws Exception {
        System.out.println("Armada : Save data double");
        
        int idRandom = instance.getRandomID();
        Armada arm = instance.getArmadaById(idRandom);
        
        Armada armada = new Armada();
        armada.setNopol(arm.getNopol());
        armada.setAlamat(arm.getAlamat());
        armada.setDriverId(shDAO.getRandomID());
        armada.setKendaraan(arm.getKendaraan());
        armada.setKota(arm.getKota());
        armada.setPemilik(arm.getPemilik());
        armada.setTelp(arm.getTelp());
        armada.setUserCreate(arm.getUserCreate());
        armada.setUserUpdate(arm.getUserUpdate());
        
        // Memastikan data dengan nopol yang sama gagal disimpan
        SQLException exception = assertThrows(SQLException.class, () -> {
            instance.save(armada);
        });

        System.out.println("Message : " + exception.getMessage());
        // Verifikasi bahwa exception yang dilemparkan berhubungan dengan pelanggaran constraint UNIQUE
        assertTrue(exception.getMessage().contains("armada.idx_unique_nopol_armada"));
    }

    @Test
    @Order(6)
    public void testGetArmadaById() throws Exception {
        System.out.println("Armada : getArmadaById");
        Armada expResult = instance.getArmadaById(last_id);        
        assertEquals(expResult.getId(), last_id);
        
        expResult = instance.getArmadaById(-1 * last_id);        
        assertEquals(expResult, null);
        
        
    }

    @Test
    @Order(7)
    public void testGetArmadaByNoPol() throws Exception {        
        System.out.println("Armada : getArmadaByNoPol");
        Armada expResult = instance.getArmadaByNoPol(last_nopol);
        assertEquals(expResult.getId(), last_id);
        
        expResult = instance.getArmadaByNoPol("-" + last_nopol);
        assertEquals(expResult, null);
    }

    @Test
    @Order(8)
    public void testDelete() throws Exception {
        System.out.println("Armada : delete");
        instance.delete(last_id);
        Armada expResult = instance.getArmadaById(last_id);
        assertEquals(expResult,null);        
    }
    
}
