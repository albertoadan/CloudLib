package com.bits.cloudlib.objetos;

import java.util.Date;

/**
 * Clase para la creación / modificación de un objeto Reserva que servirá para tener un libro apartado por un usuario hasta que
 * se haga efectivo el prestamo
 * <p/>
 * Created by BiTs on 04/04/2016
 */
public class Reserva {


    // Constantes públicas: Estado de una reserva
    public static final int ESTADO_ACTIVA = 0;
    public static final int ESTADO_CADUCADA = 1;
    public static final int ESTADO_CANCELADA = 2;
    public static final int ESTADO_COMPLETADA = 3;

    private final int id;
    private Date fechaReserva, fechaCaducidad;
    private String observaciones;
    private int estado, idEjemplar, idUsuario;

    /**
     * Constructor para la creación de un nuevo objeto Reserva
     *
     * @param id             Identificador de la reserva en la base de datos. Sólo es posible introducir este campo con éste constructor
     * @param fechaReserva   Fecha en la que se hace la reserva
     * @param fechaCaducidad Fecha en la que caduca la reserva
     * @param idEjemplar     Ejemplar que se reserva
     * @param idUsuario      Usuario que reserva el libro
     * @param estado         Indica si está estado
     * @param observaciones  Cualquier información relevante sobre la reserva.
     */
    public Reserva(int id, Date fechaReserva, Date fechaCaducidad, String observaciones, int estado,
                   int idEjemplar, int idUsuario) {
        this.id = id;
        this.fechaReserva = fechaReserva;
        this.fechaCaducidad = fechaCaducidad;
        this.observaciones = observaciones;
        this.estado = estado;
        this.idEjemplar = idEjemplar;
        this.idUsuario = idUsuario;
    }

    /**
     * Constructor por defecto
     */
    public Reserva() {
        id = -1;
        fechaReserva = null;
        fechaCaducidad = null;
        observaciones = null;
        estado = 0;
        idEjemplar = -1;
        idUsuario = -1;
    }

    /**
     * Obtiene el identificador de la reserva en la base de datos
     *
     * @return identificador de la reserva
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene la fecha de la reserva
     *
     * @return la fecha de la reserva
     */
    public Date getFechaReserva() {
        return fechaReserva;
    }

    /**
     * Introduce la fecha de la reserva
     *
     * @param reserva Fecha en la que se reserva el libro
     */
    public void setFechaReserva(Date reserva) {
        this.fechaReserva = reserva;
    }

    /**
     * Obtiene la fecha de caducidad de la reserva
     *
     * @return la fecha de caducidad
     */
    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    /**
     * Introduce la fecha de caducidad de la reserva
     *
     * @param caducidad Fecha en la que caduca la reserva
     */
    public void setFechaCaducidad(Date caducidad) {
        this.fechaCaducidad = caducidad;
    }

    /**
     * Obtiene las observaciones de la reserva
     *
     * @return observaciones de la reserva
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * Introduce las observaciones de la reserva
     *
     * @param observaciones observaciones de la reserva
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    /**
     * Obtiene el valor que indica si la reserva se ha cancelado
     *
     * @return valor estado
     */
    public int getEstado() {
        return estado;
    }

    /**
     * Introduce el valor que indica si la reserva se ha cancelado
     *
     * @param estado valor estado
     */
    public void setEstado(int estado) {
        this.estado = estado;
    }

    /**
     * Obtiene el identificador del ejemplar correspondiente a la reserva
     *
     * @return el identificador del ejemplar
     */
    public int getIdEjemplar() {
        return idEjemplar;
    }

    /**
     * Introduce el identificador del ejemplar correspondiente a la reserva
     *
     * @param idEjemplar identificador del ejemplar
     */
    public void setIdEjemplar(int idEjemplar) {
        this.idEjemplar = idEjemplar;
    }

    /**
     * Obtiene el identificador del usuario que hace la reserva
     *
     * @return el identificador del usuario
     */
    public int getIdUsuario() {
        return idUsuario;
    }

    /**
     * Introduce el identificador del usuario que hace la reserva
     *
     * @param idUsuario el identificador del usuario
     */
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}
