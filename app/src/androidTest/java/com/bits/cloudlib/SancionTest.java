package com.bits.cloudlib;

import com.bits.cloudlib.objetos.Sancion;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test unitario de objeto Sancion
 * @author Rafael Bravo Contreras
 */
public class SancionTest {
    
    public SancionTest() {
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
     * Test of getId method, of class Sancion.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        Sancion instance = new Sancion();
        int expResult = -1;
        int result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTipo method, of class Sancion.
     */
    @Test
    public void testGetTipo() {
        System.out.println("getTipo");
        Sancion instance = new Sancion();
        int expResult = -1;
        int result = instance.getTipo();
        assertEquals(expResult, result);
    }

    /**
     * Test of setTipo method, of class Sancion.
     */
    @Test
    public void testSetTipo() {
        System.out.println("setTipo");
        int tipo = 1;
        Sancion instance = new Sancion();
        instance.setTipo(tipo);
        assertEquals(tipo, instance.getTipo());
    }

    /**
     * Test of getDescripcion method, of class Sancion.
     */
    @Test
    public void testGetDescripcion() {
        System.out.println("getDescripcion");
        Sancion instance = new Sancion();
        String expResult = null;
        String result = instance.getDescripcion();
        assertEquals(expResult, result);
    }

    /**
     * Test of setDescripcion method, of class Sancion.
     */
    @Test
    public void testSetDescripcion() {
        System.out.println("setDescripcion");
        String descripcion = "";
        Sancion instance = new Sancion();
        instance.setDescripcion(descripcion);
        assertEquals(descripcion, instance.getDescripcion());
    }

    /**
     * Test of getDiasReferencia method, of class Sancion.
     */
    @Test
    public void testGetDiasReferencia() {
        System.out.println("getDiasReferencia");
        Sancion instance = new Sancion();
        int expResult = -1;
        int result = instance.getDiasReferencia();
        assertEquals(expResult, result);
    }

    /**
     * Test of setDiasReferencia method, of class Sancion.
     */
    @Test
    public void testSetDiasReferencia() {
        System.out.println("setDiasReferencia");
        int diasReferencia = 1;
        Sancion instance = new Sancion();
        instance.setDiasReferencia(diasReferencia);
        assertEquals(diasReferencia, instance.getDiasReferencia());
    }

    /**
     * Test of getDiasSancion method, of class Sancion.
     */
    @Test
    public void testGetDiasSancion() {
        System.out.println("getDiasSancion");
        Sancion instance = new Sancion();
        int expResult = -1;
        int result = instance.getDiasSancion();
        assertEquals(expResult, result);
    }

    /**
     * Test of setDiasSancion method, of class Sancion.
     */
    @Test
    public void testSetDiasSancion() {
        System.out.println("setDiasSancion");
        int diasSancion = 1;
        Sancion instance = new Sancion();
        instance.setDiasSancion(diasSancion);
        assertEquals(diasSancion, instance.getDiasSancion());
    }
}
