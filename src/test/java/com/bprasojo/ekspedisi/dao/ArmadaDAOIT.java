/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.bprasojo.ekspedisi.dao;

import com.bprasojo.ekspedisi.model.Armada;
import java.sql.Connection;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author USER
 */
public class ArmadaDAOIT {
    
    public ArmadaDAOIT() {
    }
    
    @BeforeAll
    public static void setUpClass() {
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

    /**
     * Test of getConnection method, of class ArmadaDAO.
     */
//    @Test
//    public void testGetConnection() {
//        System.out.println("getConnection");
//        ArmadaDAO instance = new ArmadaDAO();
//        Connection expResult = null;
//        Connection result = instance.getConnection();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of getArmadaByPage method, of class ArmadaDAO.
     */
    @Test
    public void testGetArmadaByPage() throws Exception {
        System.out.println("getArmadaByPage");
        int page = 0;
        int pageSize = 0;
        String filter = "";
        ArmadaDAO instance = new ArmadaDAO();
        List<Armada> expResult = null;
        List<Armada> result = instance.getArmadaByPage(page, pageSize, filter);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of saveArmada method, of class ArmadaDAO.
     */
    @Test
    public void testSaveArmada() {
        System.out.println("saveArmada");
        Armada armada = null;
        ArmadaDAO instance = new ArmadaDAO();
        boolean expResult = false;
        boolean result = instance.saveArmada(armada);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getArmadaById method, of class ArmadaDAO.
     */
    @Test
    public void testGetArmadaById() throws Exception {
        System.out.println("getArmadaById");
        int armadaId = 0;
        ArmadaDAO instance = new ArmadaDAO();
        Armada expResult = null;
        Armada result = instance.getArmadaById(armadaId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getArmadaByNoPol method, of class ArmadaDAO.
     */
    @Test
    public void testGetArmadaByNoPol() throws Exception {
        System.out.println("getArmadaByNoPol");
        String noPol = "";
        ArmadaDAO instance = new ArmadaDAO();
        Armada expResult = null;
        Armada result = instance.getArmadaByNoPol(noPol);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
