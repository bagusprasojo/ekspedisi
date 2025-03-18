/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bprasojo.ekspedisi.utils;

/**
 *
 * @author USER
 */
import java.io.IOException;
import java.util.Properties;

public class VersionUtils {
    public static String getAppVersion() {
        Properties properties = new Properties();
        try {
            properties.load(VersionUtils.class.getClassLoader().getResourceAsStream("META-INF/maven/com.bprasojo/ekspedisi/pom.properties"));
            return properties.getProperty("version");
        } catch (IOException e) {
            e.printStackTrace();
            return "Unknown Version";
        }
    }
}

