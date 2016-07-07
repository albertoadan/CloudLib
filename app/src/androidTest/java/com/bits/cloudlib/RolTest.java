package com.bits.cloudlib;

import com.bits.cloudlib.objetos.Rol;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test unitario de objeto Rol
 * @author Rafael Bravo Contreras
 */
public class RolTest {
    
    public RolTest() {
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
     * Test of getId method, of class Rol.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        Rol instance = new Rol();
        int expResult = -1;
        int result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTipo method, of class Rol.
     */
    @Test
    public void testGetTipo() {
        System.out.println("getTipo");
        Rol instance = new Rol();
        int expResult = -1;
        int result = instance.getTipo();
        assertEquals(expResult, result);
    }

    /**
     * Test of setTipo method, of class Rol.
     */
    @Test
    public void testSetTipo() {
        System.out.println("setTipo");
        int tipo = 0;
        Rol instance = new Rol();
        instance.setTipo(tipo);
        assertEquals(tipo, instance.getTipo());
    }

    /**
     * Test of getDescripcion method, of class Rol.
     */
    @Test
    public void testGetDescripcion() {
        System.out.println("getDescripcion");
        Rol instance = new Rol();
        String expResult = null;
        String result = instance.getDescripcion();
        assertEquals(expResult, result);
    }

    /**
     * Test of setDescripcion method, of class Rol.
     */
    @Test
    public void testSetDescripcion() {
        System.out.println("setDescripcion");
        String descripcion = "";
        Rol instance = new Rol();
        instance.setDescripcion(descripcion);
        assertEquals(descripcion, instance.getDescripcion());
    }
    
}
