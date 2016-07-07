package com.bits.cloudlib;

import com.bits.cloudlib.objetos.Seccion;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test unitario de objeto Seccion
 * @author Rafael Bravo Contreras
 */
public class SeccionTest {
    
    public SeccionTest() {
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
     * Test of getId method, of class Seccion.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        Seccion instance = new Seccion();
        int expResult = -1;
        int result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getNombre method, of class Seccion.
     */
    @Test
    public void testGetNombre() {
        System.out.println("getNombre");
        Seccion instance = new Seccion();
        String expResult = null;
        String result = instance.getNombre();
        assertEquals(expResult, result);
    }

    /**
     * Test of setNombre method, of class Seccion.
     */
    @Test
    public void testSetNombre() {
        System.out.println("setNombre");
        String nombre = "";
        Seccion instance = new Seccion();
        instance.setNombre(nombre);
        assertEquals(nombre, instance.getNombre());
    }
    
}
