/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bprasojo.ekspedisi;

import com.toedter.calendar.JDateChooser;
import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 *
 * @author USER
 */
public class FrmDefault extends javax.swing.JInternalFrame{
    public FrmDefault() {
        // Fokus pindah pakai Enter
        KeyboardFocusManager.getCurrentKeyboardFocusManager()
        .addKeyEventDispatcher(e -> {
            if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_ENTER) {
                Component comp = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
                if ((comp instanceof JTextField) 
                        || (comp instanceof JButton) 
                        || (comp instanceof JDateChooser)
                        || (comp instanceof JComboBox)) 
                {
                    e.consume(); // Jangan lanjut proses Enter
                    KeyboardFocusManager.getCurrentKeyboardFocusManager().focusNextComponent();
                }
            }
            return false;
        });
    }
}
