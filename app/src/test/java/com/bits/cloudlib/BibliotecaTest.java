package com.bits.cloudlib;

import com.bits.cloudlib.objetos.Biblioteca;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test unitario de objeto Biblioteca
 * @author Rafael Bravo Contreras
 */
public class BibliotecaTest {
    
    public BibliotecaTest() {
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
     * Test of getId method, of class Biblioteca.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        Biblioteca instance = new Biblioteca();
        int expResult = -1;
        int result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getNombre method, of class Biblioteca.
     */
    @Test
    public void testGetNombre() {
        System.out.println("getNombre");
        Biblioteca instance = new Biblioteca();
        String expResult = null;
        String result = instance.getNombre();
        assertEquals(expResult, result);
    }

    /**
     * Test of setNombre method, of class Biblioteca.
     */
    @Test
    public void testSetNombre() {
        System.out.println("setNombre");
        String nombre = "";
        Biblioteca instance = new Biblioteca();
        instance.setNombre(nombre);
        assertEquals(nombre, instance.getNombre());
    }

    /**
     * Test of getDireccion method, of class Biblioteca.
     */
    @Test
    public void testGetDireccion() {
        System.out.println("getDireccion");
        Biblioteca instance = new Biblioteca();
        String expResult = null;
        String result = instance.getDireccion();
        assertEquals(expResult, result);
    }

    /**
     * Test of setDireccion method, of class Biblioteca.
     */
    @Test
    public void testSetDireccion() {
        System.out.println("setDireccion");
        String direccion = "";
        Biblioteca instance = new Biblioteca();
        instance.setDireccion(direccion);
        assertEquals(direccion, instance.getDireccion());
    }

    /**
     * Test of getDescripcion method, of class Biblioteca.
     */
    @Test
    public void testGetDescripcion() {
        System.out.println("getDescripcion");
        Biblioteca instance = new Biblioteca();
        String expResult = null;
        String result = instance.getDescripcion();
        assertEquals(expResult, result);
    }

    /**
     * Test of setDescripcion method, of class Biblioteca.
     */
    @Test
    public void testSetDescripcion() {
        System.out.println("setDescripcion");
        String descripcion = "";
        Biblioteca instance = new Biblioteca();
        instance.setDescripcion(descripcion);
        assertEquals(descripcion, instance.getDescripcion());
    }
    
}
