package com.bits.cloudlib;


import com.bits.cloudlib.controlador.ConexionBDRemotaCloudLibException;
import com.bits.cloudlib.controlador.ConexionBDRemotaMovilCloudLib;
import com.bits.cloudlib.controlador.LogueadorRemota;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Albert on 06/06/2016.
 */
public class LogueadorInternaTest {


    private ConexionBDRemotaMovilCloudLib cloudlib;

    public LogueadorInternaTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testComprobarUsuario () {
        try {
            cloudlib =  new ConexionBDRemotaMovilCloudLib("root", "123456789", "cloudlib", "axelussnas.dlinkddns.com", "3306");
        } catch (ConexionBDRemotaCloudLibException e) {
            e.printStackTrace();
        }
        LogueadorRemota l = new LogueadorRemota(cloudlib,"vicaspe@gmail.com", "26011979" );
        int x =  l.getLogin();
        assertEquals(1, x);
    }

}



