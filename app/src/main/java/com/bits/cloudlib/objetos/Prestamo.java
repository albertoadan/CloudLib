package com.bits.cloudlib.objetos;

import java.util.Date;

/**
 * Clase para la creación/modificación de un objeto Prestamo
 *
 * Created by BiTs on 04/04/2016
 */
public class Prestamo {
    
    // Constantes públicas: Estados de un préstamo
    public static final int ESTADO_ACTIVO = 0;
    public static final int ESTADO_COMPLETADO = 1;
    public static final int ESTADO_CANCELADO = 2;
    
    private final int id;
    private Date fechaPrestamo, fechaPrevistaDevolucion, fechaDevolucion;
    private String observaciones;
    private int estado, idEjemplar, idUsuario;


    /**
     * Constructor para la creación de un nuevo objeto Prestamo
     *
     * @param id Identificador del préstamo en la base de datos. Sólo es posible introducir este campo con éste constructor
     * @param fechaPrestamo Fecha en la que se hace efectivo el prestamo
     * @param ejemplar Identificador del ejemplar que se presta o devuelve
     * @param usuario Identificador del usuario que toma prestado o devuelve el ejemplar
     * @param fechaPrevistaDevolucion Fecha en la que debería devolverse el ejemplar
     * @param fechaDevolucion Fecha en la que se ha devuelto el ejemplar
     * @param observaciones Cualquier información que haya que tener en cuenta sobre el préstamo
     * @param estado Indica si está estado
     */
    public Prestamo(int id, Date fechaPrestamo, Date fechaPrevistaDevolucion, Date fechaDevolucion, String observaciones,
            int estado, int ejemplar, int usuario) {
        this.id = id;
        this.fechaPrestamo = fechaPrestamo;
	this.fechaPrevistaDevolucion = fechaPrevistaDevolucion;
        this.fechaDevolucion = fechaDevolucion;
	this.observaciones = observaciones;
        this.estado = estado;
        this.idEjemplar = ejemplar;
        this.idUsuario = usuario;
    }
    
    /**
     * Constructor por defecto
     */
    public Prestamo() {
        id = -1;
        fechaPrestamo = null;
        fechaPrevistaDevolucion = null;
        fechaDevolucion = null;
        observaciones = null;
        estado = 0;
        idEjemplar = -1;
        idUsuario = -1;
    }
    
    /**
     * Obtiene el identificador del préstamo en la base de datos
     * 
     * @return identificador del préstamo
     */
    public int getId() {
        return id;
    }
    
    /**
     * Obtiene la fecha del prestamo
     *
     * @return Fecha del prestamo
     */
    public Date getFechaPrestamo() {
        return fechaPrestamo;
    }

    /**
     * Introduce  la fecha del prestamo
     *
     * @param prestamo Fecha del prestamo
     */
    public void setFechaPrestamo(Date prestamo) {
        this.fechaPrestamo = prestamo;
    }
    
    /**
     * Obtiene la fecha en la que está prevista que se devuelva el libro
     *
     * @return Fecha de devolución del libro
     */
    public Date getFechaPrevistaDevolucion() {
        return fechaPrevistaDevolucion;
    }

    /**
     * Introduce la fecha en la que está prevista su devolución
     *
     * @param fechaPrevista Fecha prevista de devolución
     */
    public void setFechaPrevistaDevolucion(Date fechaPrevista) {
        this.fechaPrevistaDevolucion = fechaPrevista;
    }

    /**
     * Obtiene la fecha de devolucion del prestamo
     *
     * @return la fecha de devolucion del prestamo
     */
    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    /**
     * Introduce la fecha de devolucion del prestamo
     *
     * @param devolucion Fecha en la que se devuelve el libro
     */
    public void setFechaDevolucion(Date devolucion) {
        this.fechaDevolucion = devolucion;
    }

    /**
     * Obtiene las observaciones sobre el prestamo
     *
     * @return Datos extra sobre el prestamo
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * Introduce  las observaciones sobre el prestamo
     *
     * @param observaciones Datos extra sobre el prestamo
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    /**
     * Obtiene un valor que indica si el préstamo se ha estado
     * 
     * @return valor estado
     */
    public int getEstado() {
        return estado;
    }
    
    /**
     * Introduce un valor que indica si el préstamo se ha estado
     * 
     * @param estado valor estado
     */
    public void setEstado(int estado) {
        this.estado = estado;
    }

    /**
     * Obtiene el identificador del ejemplar correspondiente al prestamo
     *
     * @return el identificador del ejemplar
     */
    public int getIdEjemplar() {
        return idEjemplar;
    }

    /**
     * Introduce el identificador del ejemplar en el prestamo
     *
     * @param idEjemplar el identificador del ejemplar
     */
    public void setIdEjemplar(int idEjemplar) {
        this.idEjemplar = idEjemplar;
    }

    /**
     * Obtiene el identificador del usuario correspondiente al prestamo
     *
     * @return el identificador del usuario
     */
    public int getIdUsuario() {
        return idUsuario;
    }

    /**
     * Introduce el identificador del usuario correspondiente al prestamo
     *
     * @param idUsuario el identificador del usuario
     */
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}