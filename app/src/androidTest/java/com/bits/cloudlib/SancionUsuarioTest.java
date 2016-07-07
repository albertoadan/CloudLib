package com.bits.cloudlib;

import com.bits.cloudlib.objetos.SancionUsuario;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Test unitario de objeto SancionUsuario
 * @author Rafael Bravo Contreras
 */
public class SancionUsuarioTest {
    
    public SancionUsuarioTest() {
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
     * Test of getId method, of class SancionUsuario.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        SancionUsuario instance = new SancionUsuario();
        int expResult = -1;
        int result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getFechaAplicacion method, of class SancionUsuario.
     */
    @Test
    public void testGetFechaAplicacion() {
        System.out.println("getFechaAplicacion");
        SancionUsuario instance = new SancionUsuario();
        Date expResult = null;
        Date result = instance.getFechaAplicacion();
        assertEquals(expResult, result);
    }

    /**
     * Test of setFechaAplicacion method, of class SancionUsuario.
     */
    @Test
    public void testSetFechaAplicacion() {
        System.out.println("setFechaAplicacion");
        Date aplicacion = new Date();
        SancionUsuario instance = new SancionUsuario();
        instance.setFechaAplicacion(aplicacion);
        assertEquals(aplicacion, instance.getFechaAplicacion());
    }

    /**
     * Test of getFechaVencimiento method, of class SancionUsuario.
     */
    @Test
    public void testGetFechaVencimiento() {
        System.out.println("getFechaVencimiento");
        SancionUsuario instance = new SancionUsuario();
        Date expResult = null;
        Date result = instance.getFechaVencimiento();
        assertEquals(expResult, result);
    }

    /**
     * Test of setFechaVencimiento method, of class SancionUsuario.
     */
    @Test
    public void testSetFechaVencimiento() {
        System.out.println("setFechaVencimiento");
        Date vencimiento = new Date();
        SancionUsuario instance = new SancionUsuario();
        instance.setFechaVencimiento(vencimiento);
        assertEquals(vencimiento, instance.getFechaVencimiento());
    }

    /**
     * Test of getTipoSancion method, of class SancionUsuario.
     */
    @Test
    public void testGetTipoSancion() {
        System.out.println("getTipoSancion");
        SancionUsuario instance = new SancionUsuario();
        int expResult = -1;
        int result = instance.getTipoSancion();
        assertEquals(expResult, result);
    }

    /**
     * Test of setTipoSancion method, of class SancionUsuario.
     */
    @Test
    public void testSetTipoSancion() {
        System.out.println("setTipoSancion");
        int tipoSancion = 1;
        SancionUsuario instance = new SancionUsuario();
        instance.setTipoSancion(tipoSancion);
        assertEquals(tipoSancion, instance.getTipoSancion());
    }

    /**
     * Test of getIdUsuario method, of class SancionUsuario.
     */
    @Test
    public void testGetIdUsuario() {
        System.out.println("getIdUsuario");
        SancionUsuario instance = new SancionUsuario();
        int expResult = -1;
        int result = instance.getIdUsuario();
        assertEquals(expResult, result);
    }

    /**
     * Test of setIdUsuario method, of class SancionUsuario.
     */
    @Test
    public void testSetIdUsuario() {
        System.out.println("setIdUsuario");
        int idUsuario = 1;
        SancionUsuario instance = new SancionUsuario();
        instance.setIdUsuario(idUsuario);
        assertEquals(idUsuario, instance.getIdUsuario());
    }
    
}
