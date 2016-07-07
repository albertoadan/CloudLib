package com.bits.cloudlib;

import com.bits.cloudlib.objetos.Disponible;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test unitario de objeto Disponible
 * @author Rafael Bravo Contreras
 */
public class DisponibleTest {
    
    public DisponibleTest() {
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
     * Test of getIdBiblioteca method, of class Disponible.
     */
    @Test
    public void testGetIdBiblioteca() {
        System.out.println("getIdBiblioteca");
        Disponible instance = new Disponible(1, "nombre", "direccion", "descripcion", "titulo", "autor", "editorial", 1);
        int expResult = 1;
        int result = instance.getIdBiblioteca();
        assertEquals(expResult, result);
    }

    /**
     * Test of getNombreBiblioteca method, of class Disponible.
     */
    @Test
    public void testGetNombreBiblioteca() {
        System.out.println("getNombreBiblioteca");
        Disponible instance = new Disponible(1, "nombre", "direccion", "descripcion", "titulo", "autor", "editorial", 1);
        String expResult = "nombre";
        String result = instance.getNombreBiblioteca();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDireccionBiblioteca method, of class Disponible.
     */
    @Test
    public void testGetDireccionBiblioteca() {
        System.out.println("getDireccionBiblioteca");
        Disponible instance = new Disponible(1, "nombre", "direccion", "descripcion", "titulo", "autor", "editorial", 1);
        String expResult = "direccion";
        String result = instance.getDireccionBiblioteca();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDescripcionBiblioteca method, of class Disponible.
     */
    @Test
    public void testGetDescripcionBiblioteca() {
        System.out.println("getDescripcionBiblioteca");
        Disponible instance = new Disponible(1, "nombre", "direccion", "descripcion", "titulo", "autor", "editorial", 1);
        String expResult = "descripcion";
        String result = instance.getDescripcionBiblioteca();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTituloLibro method, of class Disponible.
     */
    @Test
    public void testGetTituloLibro() {
        System.out.println("getTituloLibro");
        Disponible instance = new Disponible(1, "nombre", "direccion", "descripcion", "titulo", "autor", "editorial", 1);
        String expResult = "titulo";
        String result = instance.getTituloLibro();
        assertEquals(expResult, result);
    }

    /**
     * Test of getAutorLibro method, of class Disponible.
     */
    @Test
    public void testGetAutorLibro() {
        System.out.println("getAutorLibro");
        Disponible instance = new Disponible(1, "nombre", "direccion", "descripcion", "titulo", "autor", "editorial", 1);
        String expResult = "autor";
        String result = instance.getAutorLibro();
        assertEquals(expResult, result);
    }

    /**
     * Test of getEditorialLibro method, of class Disponible.
     */
    @Test
    public void testGetEditorialLibro() {
        System.out.println("getEditorialLibro");
        Disponible instance = new Disponible(1, "nombre", "direccion", "descripcion", "titulo", "autor", "editorial", 1);
        String expResult = "editorial";
        String result = instance.getEditorialLibro();
        assertEquals(expResult, result);
    }

    /**
     * Test of getEdicionLibro method, of class Disponible.
     */
    @Test
    public void testGetEdicionLibro() {
        System.out.println("getEdicionLibro");
        Disponible instance = new Disponible(1, "nombre", "direccion", "descripcion", "titulo", "autor", "editorial", 1);
        int expResult = 1;
        int result = instance.getEdicionLibro();
        assertEquals(expResult, result);
    }
    
}
