/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bprasojo.ekspedisi.utils;

import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;

/**
 *
 * @author USER
 */
public class CustomFocusTraversalPolicy extends FocusTraversalPolicy {

    private final Component[] order;

    public CustomFocusTraversalPolicy(Component... order) {
        this.order = order;
    }

    @Override
    public Component getComponentAfter(Container focusCycleRoot, Component aComponent) {
        for (int i = 0; i < order.length; i++) {
            if (aComponent == order[i]) {
                return order[(i + 1) % order.length]; // Menangani perulangan
            }
        }
        return null;
    }

    @Override
    public Component getComponentBefore(Container focusCycleRoot, Component aComponent) {
        for (int i = 0; i < order.length; i++) {
            if (aComponent == order[i]) {
                return order[(i - 1 + order.length) % order.length]; // Menangani perulangan
            }
        }
        return null;
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
