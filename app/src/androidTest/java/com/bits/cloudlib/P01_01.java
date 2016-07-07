package com.bits.cloudlib;

import com.bits.cloudlib.controlador.ConexionBDRemotaCloudLibException;
import com.bits.cloudlib.controlador.ConexionBDRemotaMovilCloudLib;
import com.bits.cloudlib.controlador.LogueadorRemota;
import com.bits.cloudlib.objetos.Usuario;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;

/**
 * Pruebas de la clase Logueador.java
 * @author Victor Castellanos Pérez
 */
public class P01_01 {

    private ConexionBDRemotaMovilCloudLib db;
    private Usuario normal;
    private Usuario baja;
    private Usuario bloqueado;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");

    public P01_01() {
        try {
            db = new ConexionBDRemotaMovilCloudLib("root", "123456789", "cloudlib", "192.168.1.18", "3306");
            normal = new Usuario(2, "Víctor", "Castellanos Pérez", "47756483L", "Cl. Ausias March, 14 Esc. A1 3º 1ª", "Vila-seca", "43480", "616265673", "vicaspe@gmail.com", "", "13840761e6614ffd1d597e6308af2173", 0, 0, sdf.parse("2016-04-26 02:59:18"), null, 1, 2);
            baja = new Usuario(3, "Carlos", "Castellanos Pérez", "47756484C", "Alli al lado.", "Salou", "43480", "", "victor.castellanos@outlook.com", "", "13840761e6614ffd1d597e6308af2173", 0, 0, sdf.parse("2016-04-26 03:02:37"), sdf.parse("2016-05-07 23:46:29"), 3, 2);
            bloqueado = new Usuario(6, "alberto", "adan", "53071349E", "joan ubach", "santa coloma", "08923", "santa coloma", "alberto@ioc.com", "", "81dc9bdb52d04dc20036dbd8313ed055", 0, 2, sdf.parse("2016-05-23 20:40:41"), null, 2, 1);
        } catch (ConexionBDRemotaCloudLibException | ParseException ex) {
            Logger.getLogger(P01_01.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    /**
     * Test of getLogin method, of class Logueador.
     */
    @Test
    public void testGetLogin() {
        System.out.println("getLogin - Usuario correcto");

        LogueadorRemota instance = new LogueadorRemota(db, "vicaspe@gmail.com", "26011979");
        int expResult = 1;
        int result = instance.getLogin();
        assertEquals(expResult, result);
    }

    /**
     * Test of getLogin method, of class Logueador.
     */
    @Test
    public void testGetLogin2() {
        System.out.println("getLogin - Usuario NO existe");

        LogueadorRemota instance = new LogueadorRemota (db, "vicaspe2@gmail.com", "26011979");
        int expResult = 0;
        int result = instance.getLogin();
        assertEquals(expResult, result);
    }

    /**
     * Test of getLogin method, of class Logueador.
     */
    @Test
    public void testGetLogin3() {
        System.out.println("getLogin - Contraseña incorrecta");

        LogueadorRemota instance = new LogueadorRemota(db, "vicaspe@gmail.com", "1234");
        int expResult = 4;
        int result = instance.getLogin();
        assertEquals(expResult, result);
    }

    /**
     * Test of getLogin method, of class Logueador.
     */
    @Test
    public void testGetLogin4() {
        System.out.println("getLogin - Usuario en BAJA");

        LogueadorRemota instance = new LogueadorRemota(db, "victor.castellanos@outlook.com", "1234");
        int expResult = 3;
        int result = instance.getLogin();
        assertEquals(expResult, result);
    }

    /**
     * Test of getLogin method, of class Logueador.
     */
    @Test
    public void testGetLogin5() {
        System.out.println("getLogin - Usuario BLOQUEADO");

        LogueadorRemota instance = new LogueadorRemota(db, "alberto@ioc.com", "1234");
        int expResult = 2;
        int result = instance.getLogin();
        assertEquals(expResult, result);
    }

    /**
     * Test of getLogin method, of class Logueador.
     */
    @Test
    public void testGetLogin6() {
        System.out.println("getLogin - Usuario null");

        LogueadorRemota instance = new LogueadorRemota(db, null, null);
        int expResult = 0;
        int result = instance.getLogin();
        assertEquals(expResult, result);
    }

    /**
     * Test of getUser method, of class Logueador.
     */
    @Test
    public void testGetUser() {
        System.out.println("getUser - Usuario existe.");
        LogueadorRemota instance = new LogueadorRemota(db, "vicaspe@gmail.com", "26011979");
        Usuario expResult = normal;
        instance.getLogin();
        Usuario result = instance.getUser();
        assertEquals(expResult.getId(), result.getId());
    }

    /**
     * Test of getUser method, of class Logueador.
     */
    @Test
    public void testGetUser2() {
        System.out.println("getUser - Usuario NO existe.");
        LogueadorRemota instance = new LogueadorRemota(db, "vicaspe2@gmail.com", "26011979");
        Usuario expResult = null;
        instance.getLogin();
        Usuario result = instance.getUser();
        assertEquals(expResult, result);
    }

    /**
     * Test of getUser method, of class Logueador.
     */
    @Test
    public void testGetUser3() {
        System.out.println("getUser - Usuario baja.");
        LogueadorRemota instance = new LogueadorRemota(db, "victor.castellanos@outlook.com", "26011979");
        Usuario expResult = baja;
        instance.getLogin();
        Usuario result = instance.getUser();
        assertEquals(expResult.getId(), result.getId());
    }

    /**
     * Test of getUser method, of class Logueador.
     */
    @Test
    public void testGetUser4() {
        System.out.println("getUser - Usuario bloqueado.");
        LogueadorRemota instance = new LogueadorRemota (db, "alberto@ioc.com", "1234");
        Usuario expResult = bloqueado;
        instance.getLogin();
        Usuario result = instance.getUser();
        assertEquals(expResult.getId(), result.getId());
    }

    /**
     * Test of getUser method, of class Logueador.
     */
    @Test
    public void testGetUser5() {
        System.out.println("getUser - Usuario campos null.");
        LogueadorRemota instance = new LogueadorRemota(db, null, null);
        Usuario expResult = null;
        instance.getLogin();
        Usuario result = instance.getUser();
        assertEquals(expResult, result);
    }

}
