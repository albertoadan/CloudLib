package com.bits.cloudlib.controlador;

import android.database.SQLException;

import com.bits.cloudlib.objetos.Usuario;

/**
 *
 * @author Alberto Adán
 * Created by Albert on 02/06/2016.
 *
 * Clase para loguearse en la base de datos interna cuando no hay conexión a internet en la aplicación móvil.
 * Para evitar incongruencias con la base de datos remota, el usuario de la interna cuando está sin cobertura no
 * cambia el rol , ni modificamos intentos por que cuando se tenga internet y se loguee el usuario lo hará en la remota y
 * se actualizará el usuario de la interna.
 */
public class LogueadorInterna {

    //Cantidad de intentos de logueo maximo.
    private static final int INTENTOS = 3;

    //Estados de logueo.
    public static final int NO_EXISTE = 0;
    public static final int OK = 1;
    public static final int BLOQUEADO = 2;
    public static final int BAJA = 3;
    public static final int PASS_ERROR = 4;
    public static final int ERROR=5;

    private final DBInterface db;
    private final String usuario;
    private final String pass;

    private Usuario user;

    public LogueadorInterna (DBInterface db, String usuario, String pass) {
        this.db = db;
        this.usuario = usuario;
        this.pass = pass;
    }

    /**
     * Metodo que gestiona/comprueba que los datos de login introducidos
     * correspondan a un usuario de la base de datos
     *
     * @return - 0 Si el usuario no existe. 1 Si el acceso es correcto. 2 Si la
     * contraseña es incorrecta. 3 Si el cliente esta bloqueado.
     * @author Víctor Castellanos Pérez
     */
    public int getLogin() {

        //Comprobamos que el usuario exista.
        try {
            db.open();
            user = db.getUsuario(usuario);

            if (user != null) {
                //Si existe, comprobamos que no este bloqueado.
                if (user.getRol() == BLOQUEADO) {
                    return BLOQUEADO;
                }
                //Si existe, comprobamos que no sea baja.
                if (user.getRol() == BAJA) {
                    return BAJA;
                }
            } else {
                return NO_EXISTE;
            }
        } catch (SQLException ex) {
            return NO_EXISTE;
        }


        //Si existe, no esta bloqueado y no es baja, comprobamos la combinacion usuario - pass
        if (user.getPassword().equals(pass)) {
            //Reseteamos contador de bloqueo y devolvemos estado.
            try {
                return OK;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ERROR;
        } else {
            //Mostramos un mensaje indicando que la autentificacion ha sido erronea.
            if (user.getContadorBloqueo() + 1 >= INTENTOS) {
                user.setRol(BLOQUEADO);

                return BLOQUEADO;

            }
            return PASS_ERROR;
        }
    }
}
