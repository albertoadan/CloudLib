package com.bits.cloudlib;

import com.bits.cloudlib.objetos.Prestamo;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Test unitario de objeto Prestamo
 * @author Rafael Bravo Contreras
 */
public class PrestamoTest {
    
    public PrestamoTest() {
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
     * Test of getId method, of class Prestamo.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        Prestamo instance = new Prestamo();
        int expResult = -1;
        int result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getFechaPrestamo method, of class Prestamo.
     */
    @Test
    public void testGetFechaPrestamo() {
        System.out.println("getFechaPrestamo");
        Prestamo instance = new Prestamo();
        Date expResult = null;
        Date result = instance.getFechaPrestamo();
        assertEquals(expResult, result);
    }

    /**
     * Test of setFechaPrestamo method, of class Prestamo.
     */
    @Test
    public void testSetFechaPrestamo() {
        System.out.println("setFechaPrestamo");
        Date prestamo = new Date();
        Prestamo instance = new Prestamo();
        instance.setFechaPrestamo(prestamo);
        assertEquals(prestamo, instance.getFechaPrestamo());
    }

    /**
     * Test of getFechaPrevistaDevolucion method, of class Prestamo.
     */
    @Test
    public void testGetFechaPrevistaDevolucion() {
        System.out.println("getFechaPrevistaDevolucion");
        Prestamo instance = new Prestamo();
        Date expResult = null;
        Date result = instance.getFechaPrevistaDevolucion();
        assertEquals(expResult, result);
    }

    /**
     * Test of setFechaPrevistaDevolucion method, of class Prestamo.
     */
    @Test
    public void testSetFechaPrevistaDevolucion() {
        System.out.println("setFechaPrevistaDevolucion");
        Date fechaPrevista = new Date();
        Prestamo instance = new Prestamo();
        instance.setFechaPrevistaDevolucion(fechaPrevista);
        assertEquals(fechaPrevista, instance.getFechaPrevistaDevolucion());
    }

    /**
     * Test of getFechaDevolucion method, of class Prestamo.
     */
    @Test
    public void testGetFechaDevolucion() {
        System.out.println("getFechaDevolucion");
        Prestamo instance = new Prestamo();
        Date expResult = null;
        Date result = instance.getFechaDevolucion();
        assertEquals(expResult, result);
    }

    /**
     * Test of setFechaDevolucion method, of class Prestamo.
     */
    @Test
    public void testSetFechaDevolucion() {
        System.out.println("setFechaDevolucion");
        Date devolucion = new Date();
        Prestamo instance = new Prestamo();
        instance.setFechaDevolucion(devolucion);
        assertEquals(devolucion, instance.getFechaDevolucion());
    }

    /**
     * Test of getObservaciones method, of class Prestamo.
     */
    @Test
    public void testGetObservaciones() {
        System.out.println("getObservaciones");
        Prestamo instance = new Prestamo();
        String expResult = null;
        String result = instance.getObservaciones();
        assertEquals(expResult, result);
    }

    /**
     * Test of setObservaciones method, of class Prestamo.
     */
    @Test
    public void testSetObservaciones() {
        System.out.println("setObservaciones");
        String observaciones = "";
        Prestamo instance = new Prestamo();
        instance.setObservaciones(observaciones);
        assertEquals(observaciones, instance.getObservaciones());
    }

    /**
     * Test of getEstado method, of class Prestamo.
     */
    @Test
    public void testGetEstado() {
        System.out.println("getEstado");
        Prestamo instance = new Prestamo();
        int expResult = 0;
        int result = instance.getEstado();
        assertEquals(expResult, result);
    }

    /**
     * Test of setEstado method, of class Prestamo.
     */
    @Test
    public void testSetEstado() {
        System.out.println("setEstado");
        int estado = 1;
        Prestamo instance = new Prestamo();
        instance.setEstado(estado);
        assertEquals(estado, instance.getEstado());
    }

    /**
     * Test of getIdEjemplar method, of class Prestamo.
     */
    @Test
    public void testGetIdEjemplar() {
        System.out.println("getIdEjemplar");
        Prestamo instance = new Prestamo();
        int expResult = -1;
        int result = instance.getIdEjemplar();
        assertEquals(expResult, result);
    }

    /**
     * Test of setIdEjemplar method, of class Prestamo.
     */
    @Test
    public void testSetIdEjemplar() {
        System.out.println("setIdEjemplar");
        int idEjemplar = 1;
        Prestamo instance = new Prestamo();
        instance.setIdEjemplar(idEjemplar);
        assertEquals(idEjemplar, instance.getIdEjemplar());
    }

    /**
     * Test of getIdUsuario method, of class Prestamo.
     */
    @Test
    public void testGetIdUsuario() {
        System.out.println("getIdUsuario");
        Prestamo instance = new Prestamo();
        int expResult = -1;
        int result = instance.getIdUsuario();
        assertEquals(expResult, result);
    }

    /**
     * Test of setIdUsuario method, of class Prestamo.
     */
    @Test
    public void testSetIdUsuario() {
        System.out.println("setIdUsuario");
        int idUsuario = 1;
        Prestamo instance = new Prestamo();
        instance.setIdUsuario(idUsuario);
        assertEquals(idUsuario, instance.getIdUsuario());
    }
    
}
