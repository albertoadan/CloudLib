package com.bits.cloudlib;

import com.bits.cloudlib.objetos.Usuario;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Test unitario de objeto Usuario
 * @author Rafael Bravo Contreras
 */
public class UsuarioTest {
    
    public UsuarioTest() {
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
     * Test of getId method, of class Usuario.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        Usuario instance = new Usuario();
        int expResult = -1;
        int result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getNombre method, of class Usuario.
     */
    @Test
    public void testGetNombre() {
        System.out.println("getNombre");
        Usuario instance = new Usuario();
        String expResult = null;
        String result = instance.getNombre();
        assertEquals(expResult, result);
    }

    /**
     * Test of setNombre method, of class Usuario.
     */
    @Test
    public void testSetNombre() {
        System.out.println("setNombre");
        String nombre = "";
        Usuario instance = new Usuario();
        instance.setNombre(nombre);
        assertEquals(nombre, instance.getNombre());
    }

    /**
     * Test of getDni method, of class Usuario.
     */
    @Test
    public void testGetDni() {
        System.out.println("getDni");
        Usuario instance = new Usuario();
        String expResult = null;
        String result = instance.getDni();
        assertEquals(expResult, result);
    }

    /**
     * Test of setDni method, of class Usuario.
     */
    @Test
    public void testSetDni() {
        System.out.println("setDni");
        String dni = "";
        Usuario instance = new Usuario();
        instance.setDni(dni);
        assertEquals(dni, instance.getDni());
    }

    /**
     * Test of getRol method, of class Usuario.
     */
    @Test
    public void testGetRol() {
        System.out.println("getRol");
        Usuario instance = new Usuario();
        int expResult = -1;
        int result = instance.getRol();
        assertEquals(expResult, result);
    }

    /**
     * Test of setRol method, of class Usuario.
     */
    @Test
    public void testSetRol() {
        System.out.println("setRol");
        int rol = 0;
        Usuario instance = new Usuario();
        instance.setRol(rol);
        assertEquals(rol, instance.getRol());
    }

    /**
     * Test of getApellidos method, of class Usuario.
     */
    @Test
    public void testGetApellidos() {
        System.out.println("getApellidos");
        Usuario instance = new Usuario();
        String expResult = null;
        String result = instance.getApellidos();
        assertEquals(expResult, result);
    }

    /**
     * Test of setApellidos method, of class Usuario.
     */
    @Test
    public void testSetApellidos() {
        System.out.println("setApellidos");
        String apellidos = "";
        Usuario instance = new Usuario();
        instance.setApellidos(apellidos);
        assertEquals(apellidos, instance.getApellidos());
    }

    /**
     * Test of getEmail method, of class Usuario.
     */
    @Test
    public void testGetEmail() {
        System.out.println("getEmail");
        Usuario instance = new Usuario();
        String expResult = null;
        String result = instance.getEmail();
        assertEquals(expResult, result);
    }

    /**
     * Test of setEmail method, of class Usuario.
     */
    @Test
    public void testSetEmail() {
        System.out.println("setEmail");
        String email = "";
        Usuario instance = new Usuario();
        instance.setEmail(email);
        assertEquals(email, instance.getEmail());
    }

    /**
     * Test of getDireccion method, of class Usuario.
     */
    @Test
    public void testGetDireccion() {
        System.out.println("getDireccion");
        Usuario instance = new Usuario();
        String expResult = null;
        String result = instance.getDireccion();
        assertEquals(expResult, result);
    }

    /**
     * Test of setDireccion method, of class Usuario.
     */
    @Test
    public void testSetDireccion() {
        System.out.println("setDireccion");
        String direccion = "";
        Usuario instance = new Usuario();
        instance.setDireccion(direccion);
        assertEquals(direccion, instance.getDireccion());
    }

    /**
     * Test of getPoblacion method, of class Usuario.
     */
    @Test
    public void testGetPoblacion() {
        System.out.println("getPoblacion");
        Usuario instance = new Usuario();
        String expResult = null;
        String result = instance.getPoblacion();
        assertEquals(expResult, result);
    }

    /**
     * Test of setPoblacion method, of class Usuario.
     */
    @Test
    public void testSetPoblacion() {
        System.out.println("setPoblacion");
        String poblacion = "";
        Usuario instance = new Usuario();
        instance.setPoblacion(poblacion);
        assertEquals(poblacion, instance.getPoblacion());
    }

    /**
     * Test of getCodigoPostal method, of class Usuario.
     */
    @Test
    public void testGetCodigoPostal() {
        System.out.println("getCodigoPostal");
        Usuario instance = new Usuario();
        String expResult = null;
        String result = instance.getCodigoPostal();
        assertEquals(expResult, result);
    }

    /**
     * Test of setCodigoPostal method, of class Usuario.
     */
    @Test
    public void testSetCodigoPostal() {
        System.out.println("setCodigoPostal");
        String codigoPostal = "";
        Usuario instance = new Usuario();
        instance.setCodigoPostal(codigoPostal);
        assertEquals(codigoPostal, instance.getCodigoPostal());
    }

    /**
     * Test of getTelefono method, of class Usuario.
     */
    @Test
    public void testGetTelefono() {
        System.out.println("getTelefono");
        Usuario instance = new Usuario();
        String expResult = null;
        String result = instance.getTelefono();
        assertEquals(expResult, result);
    }

    /**
     * Test of setTelefono method, of class Usuario.
     */
    @Test
    public void testSetTelefono() {
        System.out.println("setTelefono");
        String telefono = "";
        Usuario instance = new Usuario();
        instance.setTelefono(telefono);
        assertEquals(telefono, instance.getTelefono());
    }

    /**
     * Test of getObservaciones method, of class Usuario.
     */
    @Test
    public void testGetObservaciones() {
        System.out.println("getObservaciones");
        Usuario instance = new Usuario();
        String expResult = null;
        String result = instance.getObservaciones();
        assertEquals(expResult, result);
    }

    /**
     * Test of setObservaciones method, of class Usuario.
     */
    @Test
    public void testSetObservaciones() {
        System.out.println("setObservaciones");
        String observaciones = "";
        Usuario instance = new Usuario();
        instance.setObservaciones(observaciones);
        assertEquals(observaciones, instance.getObservaciones());
    }

    /**
     * Test of getPassword method, of class Usuario.
     */
    @Test
    public void testGetPassword() {
        System.out.println("getPassword");
        Usuario instance = new Usuario();
        String expResult = null;
        String result = instance.getPassword();
        assertEquals(expResult, result);
    }

    /**
     * Test of setPassword method, of class Usuario.
     */
    @Test
    public void testSetPassword() {
        System.out.println("setPassword");
        String password = "";
        Usuario instance = new Usuario();
        instance.setPassword(password);
        assertEquals(password, instance.getPassword());
    }

    /**
     * Test of getDiasRetrasoDevoluciones method, of class Usuario.
     */
    @Test
    public void testGetDiasRetrasoDevoluciones() {
        System.out.println("getDiasRetrasoDevoluciones");
        Usuario instance = new Usuario();
        int expResult = 0;
        int result = instance.getDiasRetrasoDevoluciones();
        assertEquals(expResult, result);
    }

    /**
     * Test of setDiasRetrasoDevoluciones method, of class Usuario.
     */
    @Test
    public void testSetDiasRetrasoDevoluciones() {
        System.out.println("setDiasRetrasoDevoluciones");
        int diasRetraso = 1;
        Usuario instance = new Usuario();
        instance.setDiasRetrasoDevoluciones(diasRetraso);
        assertEquals(diasRetraso, instance.getDiasRetrasoDevoluciones());
    }

    /**
     * Test of getIdBiblioteca method, of class Usuario.
     */
    @Test
    public void testGetIdBiblioteca() {
        System.out.println("getIdBiblioteca");
        Usuario instance = new Usuario();
        int expResult = -1;
        int result = instance.getIdBiblioteca();
        assertEquals(expResult, result);
    }

    /**
     * Test of setIdBiblioteca method, of class Usuario.
     */
    @Test
    public void testSetIdBiblioteca() {
        System.out.println("setIdBiblioteca");
        int idBiblioteca = 1;
        Usuario instance = new Usuario();
        instance.setIdBiblioteca(idBiblioteca);
    }

    /**
     * Test of getContadorBloqueo method, of class Usuario.
     */
    @Test
    public void testGetContadorBloqueo() {
        System.out.println("getContadorBloqueo");
        Usuario instance = new Usuario();
        int expResult = 0;
        int result = instance.getContadorBloqueo();
        assertEquals(expResult, result);
    }

    /**
     * Test of setContadorBloqueo method, of class Usuario.
     */
    @Test
    public void testSetContadorBloqueo() {
        System.out.println("setContadorBloqueo");
        int contadorBloqueo = 1;
        Usuario instance = new Usuario();
        instance.setContadorBloqueo(contadorBloqueo);
        assertEquals(contadorBloqueo, instance.getContadorBloqueo());
    }

    /**
     * Test of getFechaAlta method, of class Usuario.
     */
    @Test
    public void testGetFechaAlta() {
        System.out.println("getFechaAlta");
        Usuario instance = new Usuario();
        Date expResult = null;
        Date result = instance.getFechaAlta();
        assertEquals(expResult, result);
    }

    /**
     * Test of setFechaAlta method, of class Usuario.
     */
    @Test
    public void testSetFechaAlta() {
        System.out.println("setFechaAlta");
        Date fechaAlta = new Date();
        Usuario instance = new Usuario();
        instance.setFechaAlta(fechaAlta);
        assertEquals(fechaAlta, instance.getFechaAlta());
    }

    /**
     * Test of getFechaBaja method, of class Usuario.
     */
    @Test
    public void testGetFechaBaja() {
        System.out.println("getFechaBaja");
        Usuario instance = new Usuario();
        Date expResult = null;
        Date result = instance.getFechaBaja();
        assertEquals(expResult, result);
    }

    /**
     * Test of setFechaBaja method, of class Usuario.
     */
    @Test
    public void testSetFechaBaja() {
        System.out.println("setFechaBaja");
        Date fechaBaja = new Date();
        Usuario instance = new Usuario();
        instance.setFechaBaja(fechaBaja);
        assertEquals(fechaBaja, instance.getFechaBaja());
    }
    
}
