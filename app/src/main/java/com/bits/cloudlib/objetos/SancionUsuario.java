package com.bits.cloudlib.objetos;

import java.util.Date;

/**
 * Clase para la creación / modificación de sanciones para los usuarios
 *
 * Created by BiTs on 04/04/2016
 */
public class SancionUsuario {

    
    private final int id;
    private Date fechaAplicacion, fechaVencimiento;
    private int tipoSancion, idUsuario;


    /**
     * Constructor para la creación de un nuevo objeto SancionesUsuarios
     *
     * @param id Identificador de la sanción de usuario en la base de datos. Sólo es posible introducir este campo con éste constructor
     * @param fechaAplicacion  Fecha en la que se aplica la sanción
     * @param fechaVencimiento  Fecha en la que termina la sanción
     * @param sancion Sanción que se aplica
     * @param usuario Usuario al que se le aplica la sanción
     */
    public SancionUsuario (int id, Date fechaAplicacion, Date fechaVencimiento, int sancion, int usuario) {
        this.id = id;
        this.fechaAplicacion = fechaAplicacion;
	this.fechaVencimiento = fechaVencimiento;
        this.tipoSancion = sancion;
	this.idUsuario = usuario;
    }
    
    /**
     * Constructor por defecto
     */
    public SancionUsuario() {
        id = -1;
        fechaAplicacion = null;
        fechaVencimiento = null;
        tipoSancion = -1;
        idUsuario = -1;
    }
    
    /**
     * Obtiene el identificador de la sanción de usuario en la base de datos
     * 
     * @return identificador de la sanción de usuario
     */
    public int getId() {
        return id;
    }
    
    /**
     * Obtiene la fecha de aplicación de la sanción
     *
     * @return la fecha de aplicación
     */
    public Date getFechaAplicacion() {
        return fechaAplicacion;
    }

    /**
     * Introduce la fecha de aplicación de la sanción
     *
     * @param aplicacion fecha de aplicación
     */
    public void setFechaAplicacion(Date aplicacion) {
        this.fechaAplicacion = aplicacion;
    }

    /**
     * Obtiene la fecha del vencimiento de la sanción
     *
     * @return la fecha del vencimiento
     */
    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    /**
     * Introduce la fecha del vencimiento de la sanción
     *
     * @param vencimiento Fecha de vencimiento
     */
    public void setFechaVencimiento(Date vencimiento) {
        this.fechaVencimiento = vencimiento;
    }

    /**
     * Obtiene la sanción del usuario
     *
     * @return la sanción del usuario
     */
    public int getTipoSancion() {
        return tipoSancion;
    }

    /**
     * Introduce la sanción del usuario
     *
     * @param tipoSancion identificador de la sanción del usuario
     */
    public void setTipoSancion(int tipoSancion) {
        this.tipoSancion = tipoSancion;
    }

    /**
     * Obtiene el identificador del usuario
     *
     * @return el identificador del usuario
     */
    public int getIdUsuario() {
        return idUsuario;
    }

    /**
     * Introduce el identificador del usuario
     *
     * @param idUsuario identificador del usuario
     */
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}
