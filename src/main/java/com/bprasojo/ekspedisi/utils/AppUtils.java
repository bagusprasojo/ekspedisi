/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bprasojo.ekspedisi.utils;

import com.bprasojo.ekspedisi.database.DatabaseConnection;
import com.bprasojo.ekspedisi.model.BaseClass;
import com.toedter.calendar.JDateChooser;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 */
public class AppUtils {
    public static int dateToInt(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String formattedDate = sdf.format(date);
        
        return Integer.parseInt(formattedDate);
    }
    public static void setDefaultValues(JComponent... components) {
        for (JComponent component : components) {
            if (component instanceof JFormattedTextField) {
                ((JFormattedTextField) component).setValue(0);
            } else if (component instanceof JTextField) {
                ((JTextField) component).setText("");
            } else if (component instanceof JTextArea) {
                ((JTextArea) component).setText("");
            } else if (component instanceof JDateChooser) {
                ((JDateChooser) component).setDate(new Date());
            } else if (component instanceof JComboBox) {
                ((JComboBox<?>) component).setSelectedIndex(-1);
            } else if (component instanceof JLabel) {
                ((JLabel) component).setText("");
            }
        }
    }
    
    public static void showErrorDialogSimpan(Exception ex) {
        String message = "Transaksi tidak bisa disimpan karena : \n " +
                         ex.getMessage();
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    public static void showErrorDialogHapus(Exception ex) {
        String message = "Transaksi tidak bisa dihapus karena : \n " +
                         ex.getMessage();
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
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
    
    public static void showReport(InputStream sourceStream, Map<String, Object> params) throws JRException{
        try {
            Connection conn  = DatabaseConnection.getConnection();
            
            JasperPrint print = JasperFillManager.fillReport(sourceStream, params, conn);
            JasperViewer viewer = new JasperViewer(print, false);
            viewer.setVisible(true);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }       
        
    }
    
    public static Date now(){
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime(); 
    }
    public static void SetTanggalToday(JDateChooser edTanggal){
        edTanggal.setDate(AppUtils.now());    
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
    
    public static String DateFormatShort(java.util.Date date){
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        return df.format(date);
    }
    
    public static String NumericFormat(Object angka){
        DecimalFormat df = new DecimalFormat("###,###,###");
        return df.format(angka);
    }
    
    public static void SetTableAligmentRight(JTable table, int column) {
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        table.getColumnModel().getColumn(column).setCellRenderer(rightRenderer);
    }
    
    public static void SetTableAligmentCenter(JTable table, int column) {
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(column).setCellRenderer(rightRenderer);
    }
    
    private static final String[] SATUAN = {
        "", "Satu", "Dua", "Tiga", "Empat", "Lima", "Enam", "Tujuh", "Delapan", "Sembilan", "Sepuluh", "Sebelas"
    };

    public static String terbilang(int angka) {
        if (angka < 12) {
            return SATUAN[angka];
        } else if (angka < 20) {
            return SATUAN[angka - 10] + " Belas";
        } else if (angka < 100) {
            return SATUAN[angka / 10] + " Puluh " + terbilang(angka % 10);
        } else if (angka < 200) {
            return "Seratus " + terbilang(angka - 100);
        } else if (angka < 1000) {
            return SATUAN[angka / 100] + " Ratus " + terbilang(angka % 100);
        } else if (angka < 2000) {
            return "Seribu " + terbilang(angka - 1000);
        } else if (angka < 1000000) {
            return terbilang(angka / 1000) + " Ribu " + terbilang(angka % 1000);
        } else if (angka < 1000000000) {
            return terbilang(angka / 1000000) + " Juta " + terbilang(angka % 1000000);
        } else if (angka < 1000000000000L) {
            return terbilang((int) (angka / 1000000000)) + " Miliar " + terbilang((int) (angka % 1000000000));
        } else {
            return "Angka terlalu besar";
        }
    }
}
