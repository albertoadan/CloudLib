package com.bits.cloudlib.controlador;

import java.sql.SQLException;

/**
 * Clase para la gestión de excepciones de la conexión remota
 * 
 * @author Rafael Bravo Contreras
 */
public class ConexionBDRemotaCloudLibException extends Exception {
    
    /**
     * Constructor sin parámetros
     */
    public ConexionBDRemotaCloudLibException() {
        super();
    }
    
    public ConexionBDRemotaCloudLibException(String msg) {
        super(msg);
    }
    
    public ConexionBDRemotaCloudLibException(String msg, SQLException sqlException) {
        super(msg, sqlException);
    }
    
    public ConexionBDRemotaCloudLibException(String msg, Exception exception) {
        super(msg, exception);
    }
}
