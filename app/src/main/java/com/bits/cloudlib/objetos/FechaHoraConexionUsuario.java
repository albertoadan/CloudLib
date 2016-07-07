package com.bits.cloudlib.objetos;

import java.util.Date;

/**
 * Clase para el control de las conexiones de usuarios tal como especifica la LOPD
 *
 * Created by Created by BiTs on 04/04/2016.
 */
public class FechaHoraConexionUsuario {
    
    
    private final int id;
    private Date fechaHoraConexion;
    private int idUsuario;

    /**
     * Constructor para creación - modificacion de una fecha-hora de conexión
     *
     * @param id Identificador de la fecha y hora de conexión de usuario en la base de datos. 
     * Sólo es posible introducir este campo con éste constructor
     * @param fechaHoraConexion Fecha y Hora a la que se conecta el usuario
     * @param usuario Usuario que se conecta
     */
    public FechaHoraConexionUsuario (int id, Date fechaHoraConexion, int usuario) {
        this.id = id;
        this.fechaHoraConexion = fechaHoraConexion;
        this.idUsuario = usuario;
    }
    
    /**
     * Constructor por defecto
     */
    public FechaHoraConexionUsuario() {
        id = -1;
        fechaHoraConexion = null;
        idUsuario = -1;
    }
    
    /**
     * Obtiene el identificador de la fecha y hora de conexión de usuario en la base de datos
     * 
     * @return identificador de la fecha y hora de conexión
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene la fecha y hora de conexión del usuario
     *
     * @return fecha y hora.
     */
    public Date getFechaHoraConexion() {
        return fechaHoraConexion;
    }

    /**
     * Introduce la fecha y hora a la que se conecta el usuario
     *
     * @param fechaHoraConexion Fecha y Hora a la que se conecta el usuario
     */
    public void setFechaHoraConexion(Date fechaHoraConexion) {
        this.fechaHoraConexion = fechaHoraConexion;
    }

    /**
     * Obtiene el identificador del usuario que se conecta
     *
     * @return el identificador del usuario
     */
    public int getIdUsuario() {
        return idUsuario;
    }

    /**
     * Introduce el identificador del usuario que se conecta
     *
     * @param idUsuario el identificador del usuario
     */
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}
