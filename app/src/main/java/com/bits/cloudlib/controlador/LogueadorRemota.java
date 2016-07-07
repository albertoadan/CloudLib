package com.bits.cloudlib.controlador;


import com.bits.cloudlib.objetos.Usuario;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase Logueador.class que gestiona el login del usuario en el sistema
 * CloudLib en la base de datos remota
 *
 * @author Víctor Castellanos Pérez
 *
 */
public class LogueadorRemota {

    //Cantidad de intentos de logueo maximo.
    private static final int INTENTOS = 3;

    //Estados de logueo.
    public static final int NO_EXISTE = 0;
    public static final int OK = 1;
    public static final int BLOQUEADO = 2;
    public static final int BAJA = 3;
    public static final int PASS_ERROR = 4;
    public static final int ERROR=5;

    private final ConexionBDRemotaMovilCloudLib db;
    private final String usuario;
    private final String pass;

    private Usuario user;



    //Constructor
    public LogueadorRemota (ConexionBDRemotaMovilCloudLib db, String usuario, String pass) {
        this.db = db;
        this.usuario = usuario;
        this.pass = pass;
    }

    /**
     * Metodo que gestiona/comprueba que los datos de login introducidos
     * correspondan a un usuario de la base de datos
     *
     * @author Víctor Castellanos Pérez
     * @return - 0 Si el usuario no existe. 1 Si el acceso es correcto. 2 Si la
     * contraseña es incorrecta. 3 Si el cliente esta bloqueado.
     */
    public int getLogin() {
        try {
            db.getConexionBDRemotaCloudLib();
        } catch (ConexionBDRemotaCloudLibException ex) {
            Logger.getLogger(LogueadorRemota.class.getName()).log(Level.SEVERE, null, ex);
            return ERROR;
        }

        //Comprobamos que el usuario exista.
        try {
            user = db.getUsuario(usuario);

        } catch (ConexionBDRemotaCloudLibException ex) {
            return NO_EXISTE;
        }

        //Si existe, comprobamos que no este bloqueado.
        if (user.getRol()== BLOQUEADO) {
            return BLOQUEADO;
        }

        //Si existe, comprobamos que no sea baja.
        if (user.getRol() == BAJA) {
            return BAJA;
        }
        try{


            //Si existe, no esta bloqueado y no es baja, comprobamos la combinacion usuario - pass
            if (user.getPassword().equals(getStringMessageDigest(pass))) {
                //Reseteamos contador de bloqueo y devolvemos estado.
                db.resetContadorBloqueoUsuario(user.getId());
                return OK;
            } else {
                //Mostramos un mensaje indicando que la autentificacion ha sido erronea.
                db.incrementContadorBloqueoUsuario(user.getId());
                if (user.getContadorBloqueo()+1 == INTENTOS) {
                    user.setRol(BLOQUEADO);
                    db.updateUsuario(user);
                    return BLOQUEADO;
                }
                return PASS_ERROR;

            }
        }catch(ConexionBDRemotaCloudLibException ex){
            return ERROR;
        }

    }

    //Getter
    public Usuario getUser() {
        return user;
    }

    /**
     * Metodo que convierte un array de bytes en String usando valores
     * hexadecimales
     *
     * @param digest array de bytes a convertir
     * @return String creado a partir del digest
     */
    private static String toHexadecimal(byte[] digest) {

        String hash = "";
        for (byte aux : digest) {
            int b = aux & 0xff;
            if (Integer.toHexString(b).length() == 1) {
                hash += "0";
            }
            hash += Integer.toHexString(b);
        }
        return hash;
    }

    /**
     * Metodo que encripta un texto mediante algoritmo MD5
     *
     * @param message texto a encriptar
     * @return mensaje encriptado.
     */
    private static String getStringMessageDigest(String message) {

        byte[] digest = null;
        byte[] buffer = message.getBytes();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(buffer);
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(LogueadorRemota.class.getName()).log(Level.SEVERE, null, ex);
        }
        return toHexadecimal(digest);
    }


}
