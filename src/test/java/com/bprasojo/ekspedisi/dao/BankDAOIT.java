/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.bprasojo.ekspedisi.dao;

import com.bprasojo.ekspedisi.model.Bank;
import com.bprasojo.ekspedisi.model.Perkiraan;
import java.sql.SQLException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author USER
 */
@TestMethodOrder(OrderAnnotation.class)
public class BankDAOIT {
    
    static BankDAO instance = null;
    static PerkiraanDAO perkiraanDAO;
    static long noRekInt;
    public BankDAOIT() {
    }
    
    @BeforeAll
    public static void setUpClass() {
        instance = new BankDAO();
        perkiraanDAO = new PerkiraanDAO();
        noRekInt = System.currentTimeMillis()/1000;
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

    private Bank save() throws SQLException{
        noRekInt = noRekInt + 1;
        Bank bank = new Bank();
        
        Perkiraan akun = perkiraanDAO.getById(perkiraanDAO.getRandomID());
        bank.setAkun(akun);
        bank.setAtasNama("test atasNama");
        bank.setKeterangan("keterangan");
        bank.setNamaBank("Bank Test");
        
        String noRekening = noRekInt + "";
        System.out.println("No Rek : " + noRekening);
        bank.setNoRekening(noRekening);
        
        instance.addBank(bank);
        return bank;
        
    }
    @Test
    @Order(1)
    public void testAddBank() throws Exception {
        System.out.println("addBank");
        Bank bank = save();
        System.out.println("Bank.Id : " + bank.getId());
        
        assertTrue(bank.getId() > 0);
    }
    
    @Test
    @Order(2)
    public void testUpdateBank() throws Exception {
        System.out.println("Update Bank");
        Bank bank = save();
        System.out.println("Bank.Rekening : " + bank.getNoRekening());
        
        int id = bank.getId();
        
        noRekInt = noRekInt + 1;
        String noRek = noRekInt + "";
        bank.setNoRekening(noRek);
        instance.updateBank(bank);
        System.out.println("Update Bank.Rekening : " + bank.getNoRekening());
        
        Bank bankUpdate = instance.getById(id);
        System.out.println("Update Bank.Rekening : " + bankUpdate.getNoRekening());
        
        
        
        assertTrue(bankUpdate.getNoRekening().equals(noRek));
    }

    @Test
    @Order(3)
    public void testGetBankByNoRekening() throws Exception {
        System.out.println("getBankByNoRekening");
        String noRekening = noRekInt + "";
        
        Bank expResult = instance.getBankByNoRekening(noRekening);
        assertEquals(expResult.getNoRekening(), noRekening);
        
    }

    @Test
    @Order(4)
    public void testGetBankById() throws Exception {
        System.out.println("getBankById");
        int idBank = instance.getRandomID();
        
        Bank bank = instance.getById(idBank);
        assertEquals(idBank, bank.getId());
        
    }
    @Test
    @Order(5)
    public void testZDeleteBank() throws Exception {
        System.out.println("deleteBank");
        
        Bank bank = instance.getBankByNoRekening(noRekInt + "");
        assertEquals(bank.getNoRekening(), noRekInt+"");
        instance.deleteBank(noRekInt+"");
        
        Bank bank2 = instance.getBankByNoRekening(noRekInt + "");
        assertEquals(bank2, null);
    }


    
}
