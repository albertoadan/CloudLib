package com.bits.cloudlib.controlador;

import com.aeat.valida.Validador;
import com.bits.cloudlib.Sanciones_reservas;
import com.bits.cloudlib.objetos.Ejemplar;
import com.bits.cloudlib.objetos.Libro;
import com.bits.cloudlib.objetos.Prestamo;
import com.bits.cloudlib.objetos.Reserva;
import com.bits.cloudlib.objetos.Usuario;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase Utilidades.class que contiene varios metodos utiles
 *
 * @author Víctor Castellanos Pérez
 * @modified Alberto Adán
 */
public class Utilidades  {

    //Expresion regular que debe cumplir cualquier direccion de email.
    private static final String VALIDAR_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    //Expresion regular que debe cumplir un codigo postal español
    private static final String VALIDAR_POSTAL = "^([1-9]{2}|[0-9][1-9]|[1-9][0-9])[0-9]{3}$";
    //Expresion regular que debe cumplir un codigo ISBN
    private static final String VALIDAR_ISBN = "[0-9xX]{10,13}";
    //Expresion regular que debe cumplir el campo numerico
    private static final String VALIDAR_NUMERICO = "[0-9]{1,3}";

    public static final int NO_EXISTE = 0;
    public static final int PASS_ERROR = 4;
    public static final int OK = 1;
    
    private static final int MAX_RESERVAS_PER_USER=5;

    private String ip;
    private String pass;
    private String usuario;
    private String db;
    private String puerto;

    private String timer;

    public Utilidades(boolean cond) {

    }

    public Utilidades (String usuario, String pass, String db, String ip, String puerto) {
        this.usuario = usuario;
        this.pass = pass;
        this.db = db;
        this.ip = ip;
        this.puerto = puerto;
    }

    public Utilidades() {
        getProperties();
    }

    /**
     * Obtiene las propiedades del archivo de configuracion.
     */
    public final void getProperties() {
        try {
            Properties propiedades = new Properties();

            File f = new File("./config/config.properties");

            if (f.exists()) {
                propiedades.load(new FileInputStream(f));
                ip = propiedades.getProperty("ip");
                puerto = propiedades.getProperty("puerto");
                db = propiedades.getProperty("db");
                usuario = propiedades.getProperty("usuario");
                pass = propiedades.getProperty("pass");
                timer = propiedades.getProperty("timer");
            }else {
                f.createNewFile();
            }


        } catch (FileNotFoundException ex) {
            LogSupport.grava(ex);
        } catch (IOException ex) {
            LogSupport.grava(ex);
        }
    }

    /**
     * Valida un email pasado por parametro con la expresion regular.
     *
     * @param email Email para validar.
     * @return true si es valido, sino false
     */
    public boolean validateEmail(String email) {

        Pattern pattern = Pattern.compile(VALIDAR_EMAIL);
        //COmprobamos el email.
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();

    }

    /**
     * Valida un DNI/CIF/NIE pasado por parametro
     *
     * @param dni DNI/CIF/NIE para validar.
     * @return true si es correcto, sino false.
     */
    public boolean validateDNI(String dni) {
        Validador validador = new Validador();
        int resultado = validador.checkNif(dni);
        return resultado > 0;
    }

    /**
     * Valida un codigo postal pasado por parametro con la expresion regular.
     *
     * @param codigo Codigo postal para validar.
     * @return true si es valido, sino false
     */
    public boolean validatePostal(String codigo) {

        Pattern pattern = Pattern.compile(VALIDAR_POSTAL);
        //COmprobamos el email.
        Matcher matcher = pattern.matcher(codigo);
        return matcher.matches();
    }

    /**
     * Valida que un campo String contenga un valor numero de 3 cifras.
     *
     * @param edicion - String que contiene el valor numerico.
     * @return - True si es un numero de 3 digitos, sino false.
     */
    public Boolean validateNumericField(String edicion) {
        Pattern pattern = Pattern.compile(VALIDAR_NUMERICO);
        //COmprobamos el email.
        Matcher matcher = pattern.matcher(edicion);
        return matcher.matches();
    }

    /**
     * Valida el ISBN pasado por parametro.
     *
     * @param numeroISBN numero ISBN que hay que validar.
     * @return si es valido, sino false
     */
    public boolean validateISBN(String numeroISBN) {
        //Quitamos los guiones.
        String isbn = numeroISBN.replaceAll("-", "");
        //Comprobamos que el numero sin guiones cumpla las condiciones de un ISBN
        if (isbn.matches(VALIDAR_ISBN)) {
            //Realizamos la validacion del ISBN si es de tipo 10 digitos.
            if (isbn.length() == 10) {
                int tot = 0;
                for (int i = 0; i < 9; i++) {
                    int digit = Integer.parseInt(isbn.substring(i, i + 1));
                    tot += ((10 - i) * digit);
                }
                //Calculamos el checksum
                String checksum = Integer.toString((11 - (tot % 11)) % 11);

                if ("10".equals(checksum)) {
                    checksum = "X";
                }

                return checksum.equalsIgnoreCase(isbn.substring(9));
            }
            //Realizamos la validacion del ISBN si es de tipo 10 digitos.
            if (isbn.length() == 13) {
                int tot = 0;
                for (int i = 0; i < 12; i++) {
                    int digit = Integer.parseInt(isbn.substring(i, i + 1));
                    tot += (i % 2 == 0) ? digit * 1 : digit * 3;
                }
                //Calculamos el checksum
                int checksum = 10 - (tot % 10);
                if (checksum == 10) {
                    checksum = 0;
                }
                return checksum == Integer.parseInt(isbn.substring(12));
            }
        }
        return false;
    }





    /**
     * Valida que el usuario pasado por parametro no tenga ninguna reserva o
     * prestamo pendiente.
     *
     * @param user Usuario a validar.
     * @return True si no tiene nada pendiente, sino false
     */
    public Boolean validatePrestamosReservas(Usuario user) {
        ArrayList<Reserva> reservas = new ArrayList<>();
        ArrayList<Prestamo> prestamos = new ArrayList<>();
        Boolean r = true;
        Boolean p = true;

        try {
            ConexionBDRemotaMovilCloudLib bd = new ConexionBDRemotaMovilCloudLib(usuario, pass, db, ip, puerto);
            bd.getConexionBDRemotaCloudLib();
            reservas = bd.getReservasUsuario(user.getId());
            prestamos = bd.getPrestamosUsuario(user.getId());
            bd.closeConexionBDRemotaCloudLib();
        } catch (ConexionBDRemotaCloudLibException ex) {
            LogSupport.grava(ex);
        }
        for (Reserva res : reservas) {
            if (res.getEstado() == Reserva.ESTADO_ACTIVA) {
                r = false;
                break;
            }
        }
        for (Prestamo pres : prestamos) {
            if (pres.getEstado() == Prestamo.ESTADO_ACTIVO) {
                p = false;
            }
        }

        return r && p;
    }

    /**
     * Comprueba que el usuario no tenga reservas de ejemplares activas del
     * mismo libro.
     *
     * @param userID - ID del usuario
     * @param libroID - ID del libro
     * @return - True si las tiene, false si no.
     */
    public Boolean checkReservasLibro(int userID, int libroID) {
        try {
            ConexionBDRemotaMovilCloudLib bd = new ConexionBDRemotaMovilCloudLib(usuario, pass, db, ip, puerto);
            bd.getConexionBDRemotaCloudLib();
            ArrayList<Reserva> reservasUsuario = bd.getReservasUsuario(userID);
            bd.closeConexionBDRemotaCloudLib();
            for (Reserva r : reservasUsuario) {
                if (r.getEstado() == Reserva.ESTADO_ACTIVA) {
                    bd.getConexionBDRemotaCloudLib();
                    Ejemplar e = bd.getEjemplar(r.getIdEjemplar());
                    if (e.getIdLibro() == libroID) {
                        return true;
                    }
                }
            }
            return false;
        } catch (ConexionBDRemotaCloudLibException ex) {
            Logger.getLogger(Sanciones_reservas.class.getName()).log(Level.SEVERE, null, ex);
          //  LogSupport.grava(ex);
        }
        return false;
    }
    
    /**
     * Comprueba que el usuario no haya alcanzado el maximo de reservas autorizadas.
     * @param userID - ID del usuario a chequear.
     * @return - true si aun puede reservar, false si no.
     */
    public Boolean checkMaxReservas(int userID){
        int total=0;
        try {
            ConexionBDRemotaMovilCloudLib bd = new ConexionBDRemotaMovilCloudLib(usuario, pass, db, ip, puerto);
            bd.getConexionBDRemotaCloudLib();
            ArrayList<Reserva> reservasUsuario = bd.getReservasUsuario(userID);
            for (Reserva r: reservasUsuario){
                if(r.getEstado()==Reserva.ESTADO_ACTIVA){
                    total++;
                }
            }
        if(total>=MAX_RESERVAS_PER_USER){
            return false;
        }else{
            return true;
        }    
            
        } catch (ConexionBDRemotaCloudLibException ex) {
            //si no tiene reservas 
            return true;
        }
    }
    /**
     * Genera la cadena que mostrara el .toString() del objeto Reserva
     *
     * @param r Objeto reserva del que realizar la cadena
     * @return Cadena de texto para el .toString()
     */
    public String getToStringReserva(Reserva r) {
        Libro libro = new Libro();
        Ejemplar ejemplar;
        String stat = "";
        getProperties();
        try {
            ConexionBDRemotaMovilCloudLib bd = new ConexionBDRemotaMovilCloudLib(usuario, pass, db, ip, puerto);
            bd.getConexionBDRemotaCloudLib();
            ejemplar = bd.getEjemplar(r.getIdEjemplar());
            libro = bd.getLibro(ejemplar.getIdLibro());
            bd.closeConexionBDRemotaCloudLib();
        } catch (ConexionBDRemotaCloudLibException ex) {
            LogSupport.grava(ex);
        }
        switch (r.getEstado()) {
            case Reserva.ESTADO_ACTIVA:
                stat = "<font color=green>Activa.</font>";
                break;
            case Reserva.ESTADO_CADUCADA:
                stat = "<font color=orange>Caducada.</font>";
                break;
            case Reserva.ESTADO_CANCELADA:
                stat = "<font color=red>Cancelada.</font>";
                break;
            case Reserva.ESTADO_COMPLETADA:
                stat = "<font color=blue>Completada.</font>";
                break;

        }

        return "<html><strong>ID reserva: </strong>" + r.getId() + "<strong> ID ejemplar: </strong>"+r.getIdEjemplar()+"<br/>"
                + "<strong>Titulo: </strong>" + libro.getTitulo() + "<br/>"
                + "<strong>Desde: </strong>" + r.getFechaReserva() + "<br/>"
                + "<strong>Hasta: </strong>" + r.getFechaCaducidad() + "<br/>"
                + "<strong>Estado: " + stat + "</strong>";
    }

    /**
     * Genera la cadena que mostrara el .toString() del objeto Prestamo
     *
     * @param p Objeto prestamo del que realizar la cadena
     * @return Cadena de texto para el .toString()
     */
    public String getToStringPrestamo(Prestamo p) {
        Libro l = new Libro();
        Ejemplar e = new Ejemplar();
        String stat = "";

        try {
            ConexionBDRemotaMovilCloudLib bd = new ConexionBDRemotaMovilCloudLib(usuario, pass, db, ip, puerto);
            bd.getConexionBDRemotaCloudLib();
            e = bd.getEjemplar(p.getIdEjemplar());
            l = bd.getLibro(e.getIdLibro());
            bd.closeConexionBDRemotaCloudLib();

        } catch (ConexionBDRemotaCloudLibException ex) {
            LogSupport.grava(ex);
        }
        //formateamos el estado.
        switch (p.getEstado()) {
            case Prestamo.ESTADO_ACTIVO:
                stat = "<font color=green>Activa.</font>";
                break;
            case Prestamo.ESTADO_CANCELADO:
                stat = "<font color=orange>Cancelado.</font>";
                break;
            case Prestamo.ESTADO_COMPLETADO:
                stat = "<font color=blue>Compleado.</font>";
                break;
        }
        String devolucion="";
        try{
            devolucion=p.getFechaDevolucion().toString();
        } catch(NullPointerException ex){
            devolucion="<font color=orange>Pendiente.</font>";
        }
        return "<html><strong>ID prestamo: </strong>" + p.getId() + "<br/>"
                + "<strong>Desde: </strong>" + p.getFechaPrestamo() + "<br/>"
                + "<strong>Hasta: </strong>" + p.getFechaPrevistaDevolucion() + "<br/>"
                + "<strong>Devolucion real: </strong>" + devolucion + "<br/>"
                + "<strong>Titulo: </strong>" + l.getTitulo() + "<br/>"
                + "<strong>Estado: " + stat + "</strong>";
    }

    /**
     * Genera la cadena que mostrara el .toString() del objeto Ejemplar
     *
     * @param e Objeto reserva del que realizar la cadena
     * @return Cadena de texto para el .toString()
     */
    public String getToStringEjemplar(Ejemplar e) {

        String stat = "";

        switch (e.getEstado()) {
            case Ejemplar.ESTADO_DISPONIBLE:
                stat = "<font color=green>Disponible.</font>";
                break;
            case Ejemplar.ESTADO_RESERVADO:
                stat = "<font color=orange>Reservado.</font>";
                break;
            case Ejemplar.ESTADO_PRESTADO:
                stat = "<font color=red>Prestado.</font>";
                break;
            case Ejemplar.ESTADO_BAJA:
                stat = "<font color=red>Baja.</font>";
                break;

        }
        return "<html><strong>ID ejemplar: </strong>" + e.getId() + "<strong> Estado: " + stat + "</strong>";
    }

    /**
     * Formatea una fecha.
     *
     * @param date - Fecha a formatear.
     * @return - Fecha formateada dd/MM/yyyy HH:mm:ss
     */
    public String formatearFecha(Date date) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return df.format(date);
    }

    //Getters de la utilidad
    public String getIp() {
        return ip;
    }

    public String getPass() {
        return pass;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getDb() {
        return db;
    }

    public String getPuerto() {
        return puerto;
    }

    public String getTimer() {
        return timer;
    }






}
