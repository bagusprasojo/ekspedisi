/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bprasojo.ekspedisi.utils;

import com.bprasojo.ekspedisi.database.DatabaseConnection;
import com.bprasojo.ekspedisi.model.BaseClass;
import com.toedter.calendar.JDateChooser;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author USER
 */
public class AppUtils {
    public static void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    public static void showWarningDialog(String message) {
        JOptionPane.showMessageDialog(null, message, "Warning", JOptionPane.WARNING_MESSAGE);
    }
    
    public static void showInfoDialog(String message) {
        JOptionPane.showMessageDialog(null, message, "Information", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static boolean showConfirmDialog(String message) {
        int option = JOptionPane.showConfirmDialog(
            null,               // parent component, null berarti di tengah layar
            message,            // pesan yang ditampilkan
            "Konfirmasi",       // judul dialog
            JOptionPane.YES_NO_OPTION,   // pilihan Ya dan Tidak
            JOptionPane.QUESTION_MESSAGE  // ikon pertanyaan
        );

        // Mengembalikan true jika user memilih Yes, false jika memilih No
        return option == JOptionPane.YES_OPTION;
    }
    
    public static <T extends BaseClass> void setSelectedIndexById(JComboBox<T> comboBox, int id) {
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            if (comboBox.getItemAt(i).getId() == id) {
                comboBox.setSelectedIndex(i);  // Set index jika id cocok
                return;
            }
        }
        
        comboBox.setSelectedIndex(-1);
    }
    
    public static void showReport(String sourceFileName, Map<String, Object> params) throws JRException{
        try {
            Connection conn  = DatabaseConnection.getConnection();
            
            JasperPrint print = JasperFillManager.fillReport(sourceFileName, params, conn);
            JasperViewer viewer = new JasperViewer(print, false);
            viewer.setVisible(true);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }       
        
    }
    
    public static void SetTanggalToday(JDateChooser edTanggal){
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime(); 
        edTanggal.setDate(today);    
    }
    
    public static void SetTanggalAwalBulan(JDateChooser edTanggal){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1); // Set tanggal ke 1
        Date firstOfMonth = calendar.getTime();
        edTanggal.setDate(firstOfMonth);   
    }
    
    public static void SetTanggalAkhirBulan(JDateChooser edTanggal){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH)); // Set ke hari terakhir bulan
        Date lastDayOfMonth = calendar.getTime();
        edTanggal.setDate(lastDayOfMonth);
    }
    
    public static Integer getIntValue(JFormattedTextField ed) {
        Integer nilaiInt = 0;
        if (ed.getValue() != null){
            nilaiInt = ((Number) ed.getValue()).intValue();
        }
        
        return nilaiInt;
        
    }
}
