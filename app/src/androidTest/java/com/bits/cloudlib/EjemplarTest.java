package com.bits.cloudlib;

import com.bits.cloudlib.objetos.Ejemplar;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test unitario de objeto Ejemplar
 * @author Rafael Bravo Contreras
 */
public class EjemplarTest {
    
    public EjemplarTest() {
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
     * Test of getId method, of class Ejemplar.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        Ejemplar instance = new Ejemplar();
        int expResult = -1;
        int result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getEstado method, of class Ejemplar.
     */
    @Test
    public void testGetEstado() {
        System.out.println("getEstado");
        Ejemplar instance = new Ejemplar();
        int expResult = -1;
        int result = instance.getEstado();
        assertEquals(expResult, result);
    }

    /**
     * Test of setEstado method, of class Ejemplar.
     */
    @Test
    public void testSetEstado() {
        System.out.println("setEstado");
        int estado = 0;
        Ejemplar instance = new Ejemplar();
        instance.setEstado(estado);
        assertEquals(estado, instance.getEstado());
    }

    /**
     * Test of getIdLibro method, of class Ejemplar.
     */
    @Test
    public void testGetIdLibro() {
        System.out.println("getIdLibro");
        Ejemplar instance = new Ejemplar();
        int expResult = -1;
        int result = instance.getIdLibro();
        assertEquals(expResult, result);
    }

    /**
     * Test of setIdLibro method, of class Ejemplar.
     */
    @Test
    public void testSetIdLibro() {
        System.out.println("setIdLibro");
        int idLibro = 0;
        Ejemplar instance = new Ejemplar();
        instance.setIdLibro(idLibro);
        assertEquals(idLibro, instance.getIdLibro());
    }
    
    /**
     * Test of getIdSeccion method, of class Ejemplar.
     */
    @Test
    public void testGetIdSeccion() {
        System.out.println("getIdSeccion");
        Ejemplar instance = new Ejemplar();
        int expResult = -1;
        int result = instance.getIdSeccion();
        assertEquals(expResult, result);
    }

    /**
     * Test of setIdSeccion method, of class Ejemplar.
     */
    @Test
    public void testSetIdSeccion() {
        System.out.println("setIdSeccion");
        int idSeccion = 0;
        Ejemplar instance = new Ejemplar();
        instance.setIdSeccion(idSeccion);
        assertEquals(idSeccion, instance.getIdSeccion());
    }
    
    /**
     * Test of getIdBiblioteca method, of class Ejemplar.
     */
    @Test
    public void testGetIdBiblioteca() {
        System.out.println("getIdBiblioteca");
        Ejemplar instance = new Ejemplar();
        int expResult = -1;
        int result = instance.getIdBiblioteca();
        assertEquals(expResult, result);
    }

    /**
     * Test of setIdBiblioteca method, of class Ejemplar.
     */
    @Test
    public void testSetIdBiblioteca() {
        System.out.println("setIdBiblioteca");
        int idBiblioteca = 0;
        Ejemplar instance = new Ejemplar();
        instance.setIdBiblioteca(idBiblioteca);
        assertEquals(idBiblioteca, instance.getIdBiblioteca());
    }
    
}
