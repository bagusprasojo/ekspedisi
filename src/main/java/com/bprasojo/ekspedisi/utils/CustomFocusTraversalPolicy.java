/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bprasojo.ekspedisi.utils;

import com.toedter.calendar.IDateEditor;
import com.toedter.calendar.JDateChooser;
import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import javax.swing.JComponent;

/**
 *
 * @author USER
 */
public class CustomFocusTraversalPolicy extends FocusTraversalPolicy {

    private final Component[] order;

    public CustomFocusTraversalPolicy(Component... order) {
        this.order = order;
        for (int i = 0; i < order.length; i++) {
            if (order[i] instanceof JDateChooser jDateChooser){
                IDateEditor ed = jDateChooser.getDateEditor();
                order[i] = ed.getUiComponent();
            }
        }
    }

    @Override
    public Component getComponentAfter(Container focusCycleRoot, Component aComponent) {
        for (int i = 0; i < order.length; i++) {
            if (aComponent == order[i]) {
                // Cari komponen berikutnya yang enabled
                int nextIndex = (i + 1) % order.length;
                Component nextComponent = order[nextIndex];
                
                // Cek apakah komponen berikutnya disabled
                while (!nextComponent.isEnabled()) {
                    // Jika disabled, cari komponen berikutnya
                    nextIndex = (nextIndex + 1) % order.length;
                    nextComponent = order[nextIndex];

                    // Jika sudah kembali ke komponen pertama dan semuanya disable, hentikan
                    if (nextIndex == (i + 1) % order.length) {
                        return null; // Semua komponen berikutnya dinonaktifkan
                    }
                }
                return nextComponent;
            }
        }
        return null; // Jika aComponent tidak ditemukan dalam order
    }

    @Override
    public Component getComponentBefore(Container focusCycleRoot, Component aComponent) {
        for (int i = 0; i < order.length; i++) {
            if (aComponent == order[i]) {
                // Cari komponen sebelumnya yang enabled
                int prevIndex = (i - 1 + order.length) % order.length;
                Component prevComponent = order[prevIndex];

                // Cek apakah komponen sebelumnya disabled
                while (!prevComponent.isEnabled()) {
                    // Jika disabled, cari komponen sebelumnya lagi
                    prevIndex = (prevIndex - 1 + order.length) % order.length;
                    prevComponent = order[prevIndex];

                    // Jika sudah kembali ke komponen pertama dan semuanya disable, hentikan
                    if (prevIndex == (i - 1 + order.length) % order.length) {
                        return null; // Semua komponen sebelumnya dinonaktifkan
                    }
                }
                return prevComponent;
            }
        }
        return null; // Jika aComponent tidak ditemukan dalam order
    }


    @Override
    public Component getFirstComponent(Container focusCycleRoot) {
        return order[0];
    }

    @Override
    public Component getLastComponent(Container focusCycleRoot) {
        return order[order.length - 1];
    }

    @Override
    public Component getDefaultComponent(Container focusCycleRoot) {
        return order[0]; // Biasanya kita ingin komponen pertama sebagai default
    }
}
