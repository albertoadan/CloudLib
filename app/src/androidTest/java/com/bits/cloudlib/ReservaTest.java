package com.bits.cloudlib;

import com.bits.cloudlib.objetos.Reserva;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Test unitario de objeto Reserva
 * @author Rafael Bravo Contreras
 */
public class ReservaTest {
    
    public ReservaTest() {
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
     * Test of getId method, of class Reserva.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        Reserva instance = new Reserva();
        int expResult = -1;
        int result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getFechaReserva method, of class Reserva.
     */
    @Test
    public void testGetFechaReserva() {
        System.out.println("getFechaReserva");
        Reserva instance = new Reserva();
        Date expResult = null;
        Date result = instance.getFechaReserva();
        assertEquals(expResult, result);
    }

    /**
     * Test of setFechaReserva method, of class Reserva.
     */
    @Test
    public void testSetFechaReserva() {
        System.out.println("setFechaReserva");
        Date reserva = new Date();
        Reserva instance = new Reserva();
        instance.setFechaReserva(reserva);
        assertEquals(reserva, instance.getFechaReserva());
    }

    /**
     * Test of getFechaCaducidad method, of class Reserva.
     */
    @Test
    public void testGetFechaCaducidad() {
        System.out.println("getFechaCaducidad");
        Reserva instance = new Reserva();
        Date expResult = null;
        Date result = instance.getFechaCaducidad();
        assertEquals(expResult, result);
    }

    /**
     * Test of setFechaCaducidad method, of class Reserva.
     */
    @Test
    public void testSetFechaCaducidad() {
        System.out.println("setFechaCaducidad");
        Date caducidad = new Date();
        Reserva instance = new Reserva();
        instance.setFechaCaducidad(caducidad);
        assertEquals(caducidad, instance.getFechaCaducidad());
    }

    /**
     * Test of getObservaciones method, of class Reserva.
     */
    @Test
    public void testGetObservaciones() {
        System.out.println("getObservaciones");
        Reserva instance = new Reserva();
        String expResult = null;
        String result = instance.getObservaciones();
        assertEquals(expResult, result);
    }

    /**
     * Test of setObservaciones method, of class Reserva.
     */
    @Test
    public void testSetObservaciones() {
        System.out.println("setObservaciones");
        String observaciones = "";
        Reserva instance = new Reserva();
        instance.setObservaciones(observaciones);
        assertEquals(observaciones, instance.getObservaciones());
    }

    /**
     * Test of getEstado method, of class Reserva.
     */
    @Test
    public void testGetEstado() {
        System.out.println("getEstado");
        Reserva instance = new Reserva();
        int expResult = 0;
        int result = instance.getEstado();
        assertEquals(expResult, result);
    }

    /**
     * Test of setEstado method, of class Reserva.
     */
    @Test
    public void testSetEstado() {
        System.out.println("setEstado");
        int estado = 1;
        Reserva instance = new Reserva();
        instance.setEstado(estado);
        assertEquals(estado, instance.getEstado());
    }

    /**
     * Test of getIdEjemplar method, of class Reserva.
     */
    @Test
    public void testGetIdEjemplar() {
        System.out.println("getIdEjemplar");
        Reserva instance = new Reserva();
        int expResult = -1;
        int result = instance.getIdEjemplar();
        assertEquals(expResult, result);
    }

    /**
     * Test of setIdEjemplar method, of class Reserva.
     */
    @Test
    public void testSetIdEjemplar() {
        System.out.println("setIdEjemplar");
        int idEjemplar = 1;
        Reserva instance = new Reserva();
        instance.setIdEjemplar(idEjemplar);
        assertEquals(idEjemplar, instance.getIdEjemplar());
    }

    /**
     * Test of getIdUsuario method, of class Reserva.
     */
    @Test
    public void testGetIdUsuario() {
        System.out.println("getIdUsuario");
        Reserva instance = new Reserva();
        int expResult = -1;
        int result = instance.getIdUsuario();
        assertEquals(expResult, result);
    }

    /**
     * Test of setIdUsuario method, of class Reserva.
     */
    @Test
    public void testSetIdUsuario() {
        System.out.println("setIdUsuario");
        int idUsuario = 1;
        Reserva instance = new Reserva();
        instance.setIdUsuario(idUsuario);
        assertEquals(idUsuario, instance.getIdUsuario());
    }
    
}
