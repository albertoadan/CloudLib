package com.bits.cloudlib.objetos;

import java.io.Serializable;
import java.util.Date;

/**
 * Clase para la creación / modificación de un objeto Usuario
 *
 * Created by BiTs on 04/04/2016
 */
public class Usuario implements Serializable {
    
    
    private  int id;
    private String nombre;
    private String apellidos;
    private String dni;
    private String direccion;
    private String poblacion;
    private String codigoPostal;
    private String telefono;
    private String email;
    private String observaciones;
    private String password;
    private int diasRetrasoDevoluciones;
    private int contadorBloqueo;
    private Date fechaAlta, fechaBaja;
    private int rol;
    private int idBiblioteca;
    

    /**
     * Constructor para crear un nuevo objeto Usuario
     *
     * @param id Identificador del usuario en la base de datos. Sólo es posible introducir este campo con éste constructor
     * @param nombre Nombre del usuario
     * @param nif Documento identificativo del usuario
     * @param apellidos Apellidos del usuario
     * @param email Correo electronico del Usuario que se usará como login
     * @param direccion Dirección del domicilio del usuario
     * @param poblacion Población del usuario
     * @param codigo Codigo Postal del usuario
     * @param telefono Teléfono móvil / fijo del usuario
     * @param password Contraseña del usuario
     * @param dias Cantidad de dias que tiene el usuario como sanción
     * @param biblio Bilioteca correspondiente por defecto al usuario
     * @param contadorBloqueo
     * @param fechaAlta Fecha en la que se da de alta al usuario
     * @param fechaBaja Fecha en la que se da de baja al usuario
     * @param rol Rol que le corresponde al usuario
     * @param observaciones Cualquier dato extra que haya que tener en cuenta sobre el usuario
     */
    public Usuario (int id, String nombre, String apellidos, String nif, String direccion, String poblacion, 
		String codigo, String telefono, String email, String observaciones, String password, int dias,
		int contadorBloqueo, Date fechaAlta, Date fechaBaja, int rol, int biblio) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
	this.dni = nif;
        this.direccion = direccion;
        this.poblacion = poblacion;
        this.codigoPostal = codigo;
        this.telefono = telefono;
	this.email = email;
	this.observaciones = observaciones;
        this.password = password;
        this.diasRetrasoDevoluciones = dias;
	this.contadorBloqueo = contadorBloqueo;
	this.fechaAlta = fechaAlta;
	this.fechaBaja = fechaBaja;
	this.rol = rol;
        this.idBiblioteca = biblio;
    }
    
    /**
     * Constructor por defecto
     */
    public Usuario() {
        id = -1;
        nombre = null;
        apellidos = null;
        dni = null;
        direccion = null;
        poblacion = null;
        codigoPostal = null;
        telefono = null;
        email = null;
        observaciones = null;
        password = null;
        diasRetrasoDevoluciones = 0;
        contadorBloqueo = 0;
        fechaAlta = null;
        fechaBaja = null;
        rol = -1;
        idBiblioteca = -1;
    }
    
    /**
     * Obtiene el identificador del usuario en la base de datos
     * 
     * @return identificador del usuario
     */
    public int getId() {
        return id;
    }





    
    /**
     * Obtiene el nombre del usuario
     *
     * @return nombre del usuario
     *
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Introduce el nombre del usuario
     *
     * @param nombre nombre del usuario
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el documento identificativo del usuario
     *
     * @return documento del usuario
     */
    public String getDni() {
        return dni;
    }

    /**
     * Introduce el documento identificativo del usuario
     *
     * @param dni Documento identificativo
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * Obtiene el rol del usuario
     * @return devuelve el id de rol del usuario
     */
    public int getRol() {
        return rol;
    }

    /**
     * Introduce el rol del usuario
     * @param rol id de rol del usuario
     */
    public void setRol(int rol) {
        this.rol = rol;
    }

    /**
     * Obtiene los apellidos del usuario
     *
     * @return apellidos del usuario
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Introducee los apellidos del usuario
     *
     * @param apellidos Apellidos del usuario
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * Obtiene el correo electrónico del usuario
     *
     * @return correo electrónico
     */
    public String getEmail() {
        return email;
    }

    /**
     * Introduce el correo electrónico del usuario
     *
     * @param email Correo electrónico del usuario
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene la dirección del usuario
     *
     * @return devuelve la direción del usuario
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Introduce la dirección del usuario
     *
     * @param direccion Direción de la vivienda del usuario
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Obtiene la población del usuario
     *
     * @return la población del usuario
     */
    public String getPoblacion() {
        return poblacion;
    }

    /**
     * Introduce la población del usuario
     *
     * @param poblacion Población donde vive el usuario
     */
    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    /**
     * Obtiene el codigo postal de la dirección del usuario
     *
     * @return el codigo postal
     */
    public String getCodigoPostal() {
        return codigoPostal;
    }

    /**
     * Introduce el codigo postal del usuario
     *
     * @param codigoPostal codigo postal de la dirección del usuario
     */
    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    /**
     * Obtiene el teléfono del usuario
     *
     * @return el teléfono del usuario
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Introduce el teléfono del usuario
     *
     * @param telefono Teléfono del usuario
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Obtiene observaciones sobre el usuario
     *
     * @return devuelve texto con información extra sobre el usuario
     */
    public String getObservaciones() {
        return observaciones;
    }


    /**
     * Introduce las observaciones sobre el usuario
     *
     * @param observaciones Texto extra a añadir sobre el usuario
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    /**
     * Obtiene la contraseña del usuario
     *
     * @return la contraseña del usuario
     */
    public String getPassword() {
        return password;
    }

    /**
     * Introduce la contraseña del usuario
     *
     * @param password Contraseña del usuario
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Obtiene los dias acumulados de retraso en devoluciones del usuario
     *
     * @return la cantidad de dias acumulados
     */
    public int getDiasRetrasoDevoluciones() {
        return diasRetrasoDevoluciones;
    }

    /**
     * Introduce los dias de retraso acumulados en devoluciones del usuario
     *
     * @param diasRetraso Dias acumulados
     */
    public void setDiasRetrasoDevoluciones(int diasRetraso) {
        this.diasRetrasoDevoluciones = diasRetraso;
    }

    /**
     * Obtiene la biblioteca correspondiente por defecto al usuario
     *
     * @return el identificador de la biblioteca
     */
    public int getIdBiblioteca() {
        return idBiblioteca;
    }

    /**
     * Introduce la biblioteca correspondiente al usuario por defeecto
     *
     * @param idBiblioteca identificador de la biblioteca asignada a usuario
     */
    public void setIdBiblioteca(int idBiblioteca) {
        this.idBiblioteca = idBiblioteca;
    }

    /**
     * Obtiene el valor del contador de bloqueo
     *
     * @return Contador de bloqueo
     */
    public int getContadorBloqueo() {
        return contadorBloqueo;
    }

    /**
     * Introduce el valor del contador para el bloqueo del usuario
     *
     * @param contadorBloqueo contador de bloqueo
     */
    public void setContadorBloqueo(int contadorBloqueo) {
        this.contadorBloqueo = contadorBloqueo;
    }

    /**
     * Obtiene la fecha en la que se da de alta el usuario en la apliación tal como pide la LOPD
     *
     * @return la fecha del alta
     */
    public Date getFechaAlta() {
        return fechaAlta;
    }

    /**
     * Introduce la fecha del alta del usuario en la aplicación
     *
     * @param fechaAlta Fecha de alta del usuario
     */
    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    /**
     * Obtiene la fecha de baja del usuario
     *
     * @return la fecha de la baja del usuario
     */
    public Date getFechaBaja() {
        return fechaBaja;
    }

    /**
     * Introduce la fecha de baja del usuario
     *
     * @param fechaBaja Fecha en la que se da de baja el usuario
     */
    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }
}

