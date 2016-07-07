package com.bits.cloudlib;

import com.bits.cloudlib.objetos.Libro;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test unitario de objeto Libro
 * @author Rafael Bravo Contreras
 */
public class LibroTest {
    
    public LibroTest() {
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
     * Test of getId method, of class Libro.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        Libro instance = new Libro();
        int expResult = -1;
        int result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getIsbn method, of class Libro.
     */
    @Test
    public void testGetIsbn() {
        System.out.println("getIsbn");
        Libro instance = new Libro();
        String expResult = null;
        String result = instance.getIsbn();
        assertEquals(expResult, result);
    }

    /**
     * Test of setIsbn method, of class Libro.
     */
    @Test
    public void testSetIsbn() {
        System.out.println("setIsbn");
        String isbn = "";
        Libro instance = new Libro();
        instance.setIsbn(isbn);
        assertEquals(isbn, instance.getIsbn());
    }

    /**
     * Test of getTitulo method, of class Libro.
     */
    @Test
    public void testGetTitulo() {
        System.out.println("getTitulo");
        Libro instance = new Libro();
        String expResult = null;
        String result = instance.getTitulo();
        assertEquals(expResult, result);
    }

    /**
     * Test of setTitulo method, of class Libro.
     */
    @Test
    public void testSetTitulo() {
        System.out.println("setTitulo");
        String titulo = "";
        Libro instance = new Libro();
        instance.setTitulo(titulo);
        assertEquals(titulo, instance.getTitulo());
    }

    /**
     * Test of getAutor method, of class Libro.
     */
    @Test
    public void testGetAutor() {
        System.out.println("getAutor");
        Libro instance = new Libro();
        String expResult = null;
        String result = instance.getAutor();
        assertEquals(expResult, result);
    }

    /**
     * Test of setAutor method, of class Libro.
     */
    @Test
    public void testSetAutor() {
        System.out.println("setAutor");
        String autor = "";
        Libro instance = new Libro();
        instance.setAutor(autor);
        assertEquals(autor, instance.getAutor());
    }

    /**
     * Test of getGenero method, of class Libro.
     */
    @Test
    public void testGetGenero() {
        System.out.println("getGenero");
        Libro instance = new Libro();
        String expResult = null;
        String result = instance.getGenero();
        assertEquals(expResult, result);
    }

    /**
     * Test of setGenero method, of class Libro.
     */
    @Test
    public void testSetGenero() {
        System.out.println("setGenero");
        String genero = "";
        Libro instance = new Libro();
        instance.setGenero(genero);
        assertEquals(genero, instance.getGenero());
    }

    /**
     * Test of getEditorial method, of class Libro.
     */
    @Test
    public void testGetEditorial() {
        System.out.println("getEditorial");
        Libro instance = new Libro();
        String expResult = null;
        String result = instance.getEditorial();
        assertEquals(expResult, result);
    }

    /**
     * Test of setEditorial method, of class Libro.
     */
    @Test
    public void testSetEditorial() {
        System.out.println("setEditorial");
        String editorial = "";
        Libro instance = new Libro();
        instance.setEditorial(editorial);
        assertEquals(editorial, instance.getEditorial());
    }

    /**
     * Test of getEdicion method, of class Libro.
     */
    @Test
    public void testGetEdicion() {
        System.out.println("getEdicion");
        Libro instance = new Libro();
        int expResult = 1;
        int result = instance.getEdicion();
        assertEquals(expResult, result);
    }

    /**
     * Test of setEdicion method, of class Libro.
     */
    @Test
    public void testSetEdicion() {
        System.out.println("setEdicion");
        int edicion = 0;
        Libro instance = new Libro();
        instance.setEdicion(edicion);
        assertEquals(edicion, instance.getEdicion());
    }

    /**
     * Test of getObservaciones method, of class Libro.
     */
    @Test
    public void testGetObservaciones() {
        System.out.println("getObservaciones");
        Libro instance = new Libro();
        String expResult = null;
        String result = instance.getObservaciones();
        assertEquals(expResult, result);
    }

    /**
     * Test of setObservaciones method, of class Libro.
     */
    @Test
    public void testSetObservaciones() {
        System.out.println("setObservaciones");
        String observaciones = "";
        Libro instance = new Libro();
        instance.setObservaciones(observaciones);
        assertEquals(observaciones, instance.getObservaciones());
    }
    
}
