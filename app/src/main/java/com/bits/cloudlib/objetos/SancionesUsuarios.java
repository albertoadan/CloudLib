package com.bits.cloudlib.objetos;

import java.util.Date;

/**
 * Clase para la creación / modificación de sanciones para los usuarios
 *
 * Created by BiTs on 04/04/2016
 * @author Albert Adán & Rafael Bravo 04/04/2016
 */
public class SancionesUsuarios {
    private Date fechaAplicacion;
    private Date fechaVencimiento;
    private int idSancion, idUsuario;


    public SancionesUsuarios () {}

    /**
     * Constructor para la creación de un nuevo objeto SancionesUsuarios
     *
     * @param fechaAplicacion  Fecha en la que se aplica la sanción
     * @param fechaVencimiento  Fecha en la que termina la sanción
     * @param sancion Sanción que se aplica
     * @param usuario Usuario al que se le aplica la sanción
     */
    public SancionesUsuarios(Date fechaAplicacion, Date fechaVencimiento, int sancion, int usuario) {
        this.fechaAplicacion = fechaAplicacion;
        this.fechaVencimiento = fechaVencimiento;
        this.idSancion = sancion;
        this.idUsuario = usuario;
    }

        /**
     * obtiene la fecha de aplicación de la sanción
     *
     * @return devuelve la fecha de aplicación
     */
    public Date getAplicacion() {
        return fechaAplicacion;
    }

    /**
     * introduce la fecha de aplicación de la sanción
     *
     * @param aplicacion fecha de aplicación
     */
    public void setAplicacion(Date aplicacion) {
        this.fechaAplicacion = aplicacion;
    }

    /**
     * obtiene la fecha del vencimiento de la sanción
     *
     * @return devuelve la fecha del vencimiento
     */
    public Date getVencimiento() {
        return fechaVencimiento;
    }

    /**
     * introduce la fecha del vencimiento de la sanción
     *
     * @param vencimiento Fecha de vencimiento
     */
    public void setVencimiento(Date vencimiento) {
        this.fechaVencimiento = vencimiento;
    }

    /**
     * obtiene la sanción del usuario
     *
     * @return devuelve la sanción del usuario
     */
    public int getIdSancion() {
        return idSancion;
    }

    /**
     * introduce la sanción del usuario
     *
     * @param idSancion Sanción del usuario
     */
    public void setIdSancion(int idSancion) {
        this.idSancion = idSancion;
    }

    /**
     * obtiene el usuario
     *
     * @return devuelve el usuario
     */
    public int getIdUsuario() {
        return idUsuario;
    }

    /**
     * introduce el usuario
     *
     * @param idUsuario Usuario
     */
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}
