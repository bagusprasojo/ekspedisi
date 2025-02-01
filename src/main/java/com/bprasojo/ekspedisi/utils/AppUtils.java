/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bprasojo.ekspedisi.utils;

import com.bprasojo.ekspedisi.model.BaseClass;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author USER
 */
public class AppUtils {
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
}
