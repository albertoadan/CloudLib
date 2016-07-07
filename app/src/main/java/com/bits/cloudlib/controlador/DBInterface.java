package com.bits.cloudlib.controlador;

/**
 * Created by Alberto Adán & Rafael Bravo on 02/04/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.bits.cloudlib.objetos.Reserva;
import com.bits.cloudlib.objetos.Rol;
import com.bits.cloudlib.objetos.Sancion;
import com.bits.cloudlib.objetos.SancionUsuario;
import com.bits.cloudlib.objetos.Usuario;
import com.bits.cloudlib.objetos.Prestamo;

import java.util.ArrayList;
import java.util.Date;

public class DBInterface {

    // Variables
    private final Context context;
    private DBOpenHelper dbOpenHelper;
    private SQLiteDatabase db;

    // Constantes
    private static final String TAG = "DBInterface";
    private static final int VERSION_BD = 1;

    // Nombres de las tablas
    private static final String NOMBRE_BD = "cloudlib";
    private static final String NOMBRE_TABLA_USUARIO = "usuario";
    private static final String NOMBRE_TABLA_RELACION_USUARIOS_DB_CLOUDLIB = "relacion_usuarios_db_cloudlib";
    private static final String NOMBRE_TABLA_ROL = "rol";
	private static final String NOMBRE_TABLA_PRESTAMO = "prestamo";
	private static final String NOMBRE_TABLA_RESERVA = "reserva";
	private static final String NOMBRE_TABLA_SANCION = "sancion";
	private static final String NOMBRE_TABLA_SANCION_USUARIO = "sancion_usuario";
	
	// Campos de la tabla 'usuario'
	private static final String USUARIO_ID = "_id";
	private static final String USUARIO_NOMBRE = "nombre";
	private static final String USUARIO_APELLIDOS = "apellidos";
	private static final String USUARIO_DNI = "dni";
	private static final String USUARIO_DIRECCION = "direccion";
	private static final String USUARIO_POBLACION = "poblacion";
	private static final String USUARIO_CODIGO_POSTAL = "codigo_postal";
	private static final String USUARIO_TELEFONO = "telefono";
	private static final String USUARIO_EMAIL = "email";
	private static final String USUARIO_OBSERVACIONES = "observaciones";
	private static final String USUARIO_PASSWORD = "password";
	private static final String USUARIO_DIAS_RETRASO_DEVOLUCIONES = "dias_retraso_devoluciones";
	private static final String USUARIO_CONTADOR_BLOQUEO = "contador_bloqueo";
	private static final String USUARIO_FECHA_ALTA = "fecha_alta";
	private static final String USUARIO_FECHA_BAJA = "fecha_baja";
	private static final String USUARIO_ROL = "id_rol";
	private static final String USUARIO_ID_BIBLIOTECA = "id_biblioteca";

    // Campos de la tabla 'relacion_usuarios_db_cloudlib'
    private static final String RELACION_USUARIOS_ID = "_id";
    private static final String RELACION_USUARIOS_ID_USUARIO_INTERNAL = "id_usuario_internal";
    private static final String RELACION_USUARIOS_ID_USUARIO_DB_CLOUDLIB = "id_usuario_db_cloudlib";
	
	// Campos de la tabla 'rol'
	private static final String ROL_ID = "_id";
    private static final String ROL_TIPO = "tipo";
	private static final String ROL_DESCRIPCION = "descripcion";
	
	// Campos de la tabla 'prestamo'
	private static final String PRESTAMO_ID = "_id";
	private static final String PRESTAMO_FECHA_PRESTAMO = "fecha_prestamo";
	private static final String PRESTAMO_FECHA_PREVISTA_DEVOLUCION = "fecha_prevista_devolucion";
	private static final String PRESTAMO_FECHA_DEVOLUCION = "fecha_devolucion";
	private static final String PRESTAMO_OBSERVACIONES = "observaciones";
    private static final String PRESTAMO_ESTADO = "estado";
	private static final String PRESTAMO_ID_EJEMPLAR = "id_ejemplar";
	private static final String PRESTAMO_ID_USUARIO = "id_usuario";
	
	// Campos de la tabla 'reserva'
	private static final String RESERVA_ID = "_id";
	private static final String RESERVA_FECHA_RESERVA = "fecha_reserva";
	private static final String RESERVA_FECHA_CADUCIDAD = "fecha_caducidad";
    private static final String RESERVA_OBSERVACIONES = "observaciones";
    private static final String RESERVA_ESTADO = "estado";
	private static final String RESERVA_ID_EJEMPLAR = "id_ejemplar";
	private static final String RESERVA_ID_USUARIO = "id_usuario";
	
	// Campos de la tabla 'sancion'
	private static final String SANCION_ID = "_id";
	private static final String SANCION_TIPO = "tipo";
	private static final String SANCION_DESCRIPCION = "descripcion";
	private static final String SANCION_DIAS_REFERENCIA = "dias_referencia";
	private static final String SANCION_DIAS_SANCION = "dias_sancion";
	
	// Campos de la tabla 'sancion_usuario'
	private static final String SANCION_USUARIO_ID = "_id";
	private static final String SANCION_USUARIO_FECHA_APLICACION = "fecha_aplicacion";
	private static final String SANCION_USUARIO_FECHA_VENCIMIENTO = "fecha_vencimiento";
	private static final String SANCION_USUARIO_TIPO_SANCION = "tipo_sancion";
	private static final String SANCION_USUARIO_ID_USUARIO = "id_usuario";
	
	// Sentencia para crear la tabla 'usuario'
    private static final String BD_CREATE_USUARIO = "create table " + NOMBRE_TABLA_USUARIO + " (" +
            USUARIO_ID + " integer primary key autoincrement, " +
            USUARIO_NOMBRE + " text not null, " +
            USUARIO_APELLIDOS + " text not null, " +
			USUARIO_DNI + " text not null, " +
			USUARIO_DIRECCION + " text not null, " +
			USUARIO_POBLACION + " text not null, " +
			USUARIO_CODIGO_POSTAL + " text not null, " +
			USUARIO_TELEFONO + " text, " +
			USUARIO_EMAIL + " text not null, " +
			USUARIO_OBSERVACIONES + " text, " +
			USUARIO_PASSWORD + " text not null, " +
			USUARIO_DIAS_RETRASO_DEVOLUCIONES + " int not null, " +
			USUARIO_CONTADOR_BLOQUEO + " int not null, " +
			USUARIO_FECHA_ALTA + " text not null, " +
			USUARIO_FECHA_BAJA + " text, " +
            USUARIO_ROL + " int not null, " +
            USUARIO_ID_BIBLIOTECA + " int not null);";

    // Sentencia para crear la tabla 'relacion_usuarios_db_cloudlib'
    private static final String BD_CREATE_RELACION_USUARIOS_DB_CLOUDLIB = "create table " +
            NOMBRE_TABLA_RELACION_USUARIOS_DB_CLOUDLIB + " (" +
            RELACION_USUARIOS_ID + " integer primary key autoincrement, " +
            RELACION_USUARIOS_ID_USUARIO_INTERNAL + " int not null, " +
            RELACION_USUARIOS_ID_USUARIO_DB_CLOUDLIB + " int not null);";

	// Sentencia para crear la tabla 'rol'
    private static final String BD_CREATE_ROL = "create table " + NOMBRE_TABLA_ROL + " (" +
            ROL_ID + " int primary key, " +
            ROL_TIPO + " int not null, " +
            ROL_DESCRIPCION + " text not null);";
	
	// Sentencia para crear la tabla 'prestamo'
    private static final String BD_CREATE_PRESTAMO = "create table " + NOMBRE_TABLA_PRESTAMO + " (" +
            PRESTAMO_ID + " int primary key, " +
            PRESTAMO_FECHA_PRESTAMO + " text not null, " +
			PRESTAMO_FECHA_PREVISTA_DEVOLUCION + " text not null, " +
			PRESTAMO_FECHA_DEVOLUCION + " text not null, " +
			PRESTAMO_OBSERVACIONES + " text, " +
            PRESTAMO_ESTADO + " int not null, " +
            PRESTAMO_ID_EJEMPLAR + " int not null, " +
			PRESTAMO_ID_USUARIO + " int not null);";
	
	// Sentencia para crear la tabla 'reserva'
    private static final String BD_CREATE_RESERVA = "create table " + NOMBRE_TABLA_RESERVA + " (" +
            RESERVA_ID + " int primary key, " +
            RESERVA_FECHA_RESERVA + " text not null, " +
			RESERVA_FECHA_CADUCIDAD + " text not null, " +
            RESERVA_OBSERVACIONES + " text," +
            RESERVA_ESTADO + " int not null, " +
            RESERVA_ID_EJEMPLAR + " int not null, " +
			RESERVA_ID_USUARIO + " int not null);";
	
	// Sentencia para crear la tabla 'sancion'
    private static final String BD_CREATE_SANCION = "create table " + NOMBRE_TABLA_SANCION + " (" +
            SANCION_ID + " int primary key, " +
            SANCION_TIPO + " int not null, " +
            SANCION_DESCRIPCION + " text not null, " +
            SANCION_DIAS_REFERENCIA + " int not null, " +
            SANCION_DIAS_SANCION + " int not null);";
	
	// Sentencia para crear la tabla 'sancion_usuario'
    private static final String BD_CREATE_SANCION_USUARIO = "create table " + NOMBRE_TABLA_SANCION_USUARIO + " (" +
            SANCION_USUARIO_ID + " int primary key, " +
            SANCION_USUARIO_FECHA_APLICACION + " text not null, " +
            SANCION_USUARIO_FECHA_VENCIMIENTO + " text not null, " +
            SANCION_USUARIO_TIPO_SANCION + " int not null, " +
            SANCION_USUARIO_ID_USUARIO + " int not null);";
	


    /**
     * Constructor de la clase
     * @author Rafael Bravo Contreras
     *
     * @param con Objeto Context
     */
    public DBInterface(Context con) {
        this.context = con;
        dbOpenHelper = new DBOpenHelper(context);
    }

    /**
     * Método que abre la base de datos para escribir en ella
     * @author Rafael Bravo Contreras
     *
     * @return Objeto DBInterface
     */
    public DBInterface open() throws SQLException {
        db = dbOpenHelper.getWritableDatabase();
      //  db.delete("usuario", null, null);
        return this;
    }

    /**
     * Método que cierra la escritura en la base de datos
     * @author Rafael Bravo Contreras
     *
     */
    public void close() {
        dbOpenHelper.close();
    }


    /**
     * Metodo que obtiene los datos de un usuario
     * @author Rafael Bravo Contreras
     *
     * @param idUsuario Identificador del usuario
     * @return Objeto Usuario
     */
    public Usuario getUsuario(int idUsuario) {
        Usuario usuario = null;
        Date fechaBaja = null;
        try {
            Cursor cursor = db.query(NOMBRE_TABLA_USUARIO, null, USUARIO_ID + "=" + idUsuario, null, null, null, null);

            if (cursor != null) {
                cursor.moveToFirst();
                if (!cursor.isNull(cursor.getColumnIndex(USUARIO_FECHA_BAJA))) {
                    fechaBaja = Utils.getDateFromString(cursor.getString(cursor.getColumnIndex(USUARIO_FECHA_BAJA)));
                }

                usuario = new Usuario(cursor.getInt(cursor.getColumnIndex(USUARIO_ID)),
                        cursor.getString(cursor.getColumnIndex(USUARIO_NOMBRE)),
                        cursor.getString(cursor.getColumnIndex(USUARIO_APELLIDOS)),
                        cursor.getString(cursor.getColumnIndex(USUARIO_DNI)),
                        cursor.getString(cursor.getColumnIndex(USUARIO_DIRECCION)),
                        cursor.getString(cursor.getColumnIndex(USUARIO_POBLACION)),
                        cursor.getString(cursor.getColumnIndex(USUARIO_CODIGO_POSTAL)),
                        cursor.getString(cursor.getColumnIndex(USUARIO_TELEFONO)),
                        cursor.getString(cursor.getColumnIndex(USUARIO_EMAIL)),
                        cursor.getString(cursor.getColumnIndex(USUARIO_OBSERVACIONES)),
                        cursor.getString(cursor.getColumnIndex(USUARIO_PASSWORD)),
                        cursor.getInt(cursor.getColumnIndex(USUARIO_DIAS_RETRASO_DEVOLUCIONES)),
                        cursor.getInt(cursor.getColumnIndex(USUARIO_CONTADOR_BLOQUEO)),
                        Utils.getDateFromString(cursor.getString(cursor.getColumnIndex(USUARIO_FECHA_ALTA))),
                        fechaBaja ,
                        cursor.getInt(cursor.getColumnIndex(USUARIO_ROL)),
                        cursor.getInt(cursor.getColumnIndex(USUARIO_ID_BIBLIOTECA)));
            }
        } catch (CursorIndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }


        return usuario;
    }


    /**
     * Metodo que obtiene los datos de un usuario
     * @author Rafael Bravo Contreras
     *
     * @param emailUsuario Email del usuario
     * @return Objeto Usuario
     */
    public Usuario getUsuario(String emailUsuario) {
        Usuario usuario = null;
        Date fechaBaja = null;
        try {
            Cursor cursor = db.query(NOMBRE_TABLA_USUARIO, null, USUARIO_EMAIL + "=" + "'"+ emailUsuario + "'", null, null, null, null);

            if (cursor != null) {
                cursor.moveToFirst();
                if (!cursor.isNull(cursor.getColumnIndex(USUARIO_FECHA_BAJA))) {
                    fechaBaja = Utils.getDateFromString(cursor.getString(cursor.getColumnIndex(USUARIO_FECHA_BAJA)));
                }

                usuario = new Usuario(cursor.getInt(cursor.getColumnIndex(USUARIO_ID)),
                        cursor.getString(cursor.getColumnIndex(USUARIO_NOMBRE)),
                        cursor.getString(cursor.getColumnIndex(USUARIO_APELLIDOS)),
                        cursor.getString(cursor.getColumnIndex(USUARIO_DNI)),
                        cursor.getString(cursor.getColumnIndex(USUARIO_DIRECCION)),
                        cursor.getString(cursor.getColumnIndex(USUARIO_POBLACION)),
                        cursor.getString(cursor.getColumnIndex(USUARIO_CODIGO_POSTAL)),
                        cursor.getString(cursor.getColumnIndex(USUARIO_TELEFONO)),
                        cursor.getString(cursor.getColumnIndex(USUARIO_EMAIL)),
                        cursor.getString(cursor.getColumnIndex(USUARIO_OBSERVACIONES)),
                        cursor.getString(cursor.getColumnIndex(USUARIO_PASSWORD)),
                        cursor.getInt(cursor.getColumnIndex(USUARIO_DIAS_RETRASO_DEVOLUCIONES)),
                        cursor.getInt(cursor.getColumnIndex(USUARIO_CONTADOR_BLOQUEO)),
                        Utils.getDateFromString(cursor.getString(cursor.getColumnIndex(USUARIO_FECHA_ALTA))),
                        fechaBaja ,
                        cursor.getInt(cursor.getColumnIndex(USUARIO_ROL)),
                        cursor.getInt(cursor.getColumnIndex(USUARIO_ID_BIBLIOTECA)));
            }
        } catch (CursorIndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }


        return usuario;
    }

    /**
     * Método que obtiene todos los usuarios
     * @author Rafael Bravo Contreras
     *
     * @return Listado de objetos Usuario
     */
    public ArrayList<Usuario> getAllUsuario() {
        ArrayList<Usuario> listadoUsuarios = null;
        Date fechaBaja = null;
        try {
            Cursor cursor = db.query(NOMBRE_TABLA_USUARIO, null, null, null, null, null, null);

            if (cursor != null) {
                listadoUsuarios = new ArrayList<>();
                cursor.moveToFirst();

                do {
                    if (!cursor.isNull(cursor.getColumnIndex(USUARIO_FECHA_BAJA))) {
                        fechaBaja = Utils.getDateFromString(cursor.getString(cursor.getColumnIndex(USUARIO_FECHA_BAJA)).toString());
                    }

                    Usuario usuario = new Usuario(cursor.getInt(cursor.getColumnIndex(USUARIO_ID)),
                            cursor.getString(cursor.getColumnIndex(USUARIO_NOMBRE)),
                            cursor.getString(cursor.getColumnIndex(USUARIO_APELLIDOS)),
                            cursor.getString(cursor.getColumnIndex(USUARIO_DNI)),
                            cursor.getString(cursor.getColumnIndex(USUARIO_DIRECCION)),
                            cursor.getString(cursor.getColumnIndex(USUARIO_POBLACION)),
                            cursor.getString(cursor.getColumnIndex(USUARIO_CODIGO_POSTAL)),
                            cursor.getString(cursor.getColumnIndex(USUARIO_TELEFONO)),
                            cursor.getString(cursor.getColumnIndex(USUARIO_EMAIL)),
                            cursor.getString(cursor.getColumnIndex(USUARIO_OBSERVACIONES)),
                            cursor.getString(cursor.getColumnIndex(USUARIO_PASSWORD)),
                            cursor.getInt(cursor.getColumnIndex(USUARIO_DIAS_RETRASO_DEVOLUCIONES)),
                            cursor.getInt(cursor.getColumnIndex(USUARIO_CONTADOR_BLOQUEO)),
                            Utils.getDateFromString(cursor.getString(cursor.getColumnIndex(USUARIO_FECHA_ALTA)).toString()),
                            fechaBaja ,
                            cursor.getInt(cursor.getColumnIndex(USUARIO_ROL)),
                            cursor.getInt(cursor.getColumnIndex(USUARIO_ID_BIBLIOTECA)));

                    listadoUsuarios.add(usuario);
                } while (cursor.moveToNext());

            }
        } catch (CursorIndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }


        return listadoUsuarios;
    }

    /**
     * Metodo que introduce los datos del usuario
     * @author Rafael Bravo Contreras
     *
     * @param usuario Objeto Usuario
     */
    public void putUsuario(Usuario usuario) {
        ContentValues values = new ContentValues();

        values.put(USUARIO_NOMBRE, usuario.getNombre());
        values.put(USUARIO_APELLIDOS, usuario.getApellidos());
        values.put(USUARIO_DNI, usuario.getDni());
        values.put(USUARIO_DIRECCION, usuario.getDireccion());
        values.put(USUARIO_POBLACION, usuario.getPoblacion());
        values.put(USUARIO_CODIGO_POSTAL, usuario.getCodigoPostal());
        values.put(USUARIO_TELEFONO, usuario.getTelefono());
        values.put(USUARIO_EMAIL, usuario.getEmail());
        values.put(USUARIO_OBSERVACIONES, usuario.getObservaciones());
        values.put(USUARIO_PASSWORD, usuario.getPassword());
        values.put(USUARIO_DIAS_RETRASO_DEVOLUCIONES, usuario.getDiasRetrasoDevoluciones());
        values.put(USUARIO_CONTADOR_BLOQUEO, usuario.getContadorBloqueo());
        values.put(USUARIO_FECHA_ALTA, Utils.getStringFromDate(usuario.getFechaAlta()));
        values.put(USUARIO_ROL, usuario.getRol());
        values.put(USUARIO_ID_BIBLIOTECA, usuario.getIdBiblioteca());

        db.insert(NOMBRE_TABLA_USUARIO, null, values);
    }

    /**
     * Método que actualiza los datos de un usuario por los del usuario pasado por parámetro
     * @author Rafael Bravo Contreras
     *
     * @param idUsuario Identificador del usuario
     * @param usuario Objeto usuario con los datos nuevos
     */
    public void updateUsuario(int idUsuario, Usuario usuario) {
        ContentValues values = new ContentValues();

        if (usuario.getFechaBaja()!=null) {
            values.put(USUARIO_FECHA_BAJA, Utils.getStringFromDate(usuario.getFechaBaja()));
        }

        values.put(USUARIO_NOMBRE, usuario.getNombre());
        values.put(USUARIO_APELLIDOS, usuario.getApellidos());
        values.put(USUARIO_DNI, usuario.getDni());
        values.put(USUARIO_DIRECCION, usuario.getDireccion());
        values.put(USUARIO_POBLACION, usuario.getPoblacion());
        values.put(USUARIO_CODIGO_POSTAL, usuario.getCodigoPostal());
        values.put(USUARIO_TELEFONO, usuario.getTelefono());
        values.put(USUARIO_EMAIL, usuario.getEmail());
        values.put(USUARIO_OBSERVACIONES, usuario.getObservaciones());
        values.put(USUARIO_PASSWORD, usuario.getPassword());
        values.put(USUARIO_DIAS_RETRASO_DEVOLUCIONES, usuario.getDiasRetrasoDevoluciones());
        values.put(USUARIO_CONTADOR_BLOQUEO, usuario.getContadorBloqueo());
        values.put(USUARIO_FECHA_ALTA, Utils.getStringFromDate(usuario.getFechaAlta()));
        values.put(USUARIO_ROL, usuario.getRol());
        values.put(USUARIO_ID_BIBLIOTECA, usuario.getIdBiblioteca());

        db.update(NOMBRE_TABLA_USUARIO, values, USUARIO_ID + "=" + idUsuario, null);
    }

    /**
     * Método que introduce un registro de relación de usuarios
     * @author Rafael Bravo Contreras
     *
     * @param internalUserId Identificador del usuario en la base de datos interna de la aplicación
     * @param cloudlibUserId Identificador del usuario en la base de datos del sistema (CloudLib)
     */
    public void putRelacionUsuarios(int internalUserId, int cloudlibUserId) {
        ContentValues values = new ContentValues();

        values.put(RELACION_USUARIOS_ID_USUARIO_INTERNAL, internalUserId);
        // Se inicializa en negativo para saber que todavía no se ha sincronizado
        values.put(RELACION_USUARIOS_ID_USUARIO_DB_CLOUDLIB, cloudlibUserId);

        db.insert(NOMBRE_TABLA_RELACION_USUARIOS_DB_CLOUDLIB, null, values);
    }

    /**
     * Método que obtiene todos los registros de la tabla 'relacion_usuarios_db_cloudlib'
     * El valor de la primera posición es el identificador de la relación en la tabla de relaciones.
     * El valor de la segunda posición es el identificador del usuario en la base de datos interna (SQLite).
     * El valor de la tercera posición es el identificador del usuario en la base de datos del sistema (CloudLib) o -1 si
     * todavía no se ha introducido.
     * @author Rafael Bravo Contreras
     *
     * @return Listado de arrays de valores enteros.
     */
    public ArrayList<int[]> getAllRelacionUsuarios() {
        ArrayList<int[]> listadoRelacionUsuarios = null;
        try {
            Cursor cursor = db.query(NOMBRE_TABLA_RELACION_USUARIOS_DB_CLOUDLIB, null, null, null, null, null, null);

            if ((cursor != null) && (cursor.getCount() > 0)) {
                cursor.moveToFirst();
                listadoRelacionUsuarios = new ArrayList<>();
                do {
                    int[] relacion = new int[3];
                    relacion[0] = cursor.getInt(cursor.getColumnIndex(RELACION_USUARIOS_ID));
                    relacion[1] = cursor.getInt(cursor.getColumnIndex(RELACION_USUARIOS_ID_USUARIO_INTERNAL));
                    relacion[2] = cursor.getInt(cursor.getColumnIndex(RELACION_USUARIOS_ID_USUARIO_DB_CLOUDLIB));

                    listadoRelacionUsuarios.add(relacion);
                } while (cursor.moveToNext());
            }

        } catch (CursorIndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }

        return listadoRelacionUsuarios;
    }

    /**
     * Metodo que obtiene los datos de un préstamo
     * @author Rafael Bravo Contreras
     *
     * @param id Identificador del préstamo
     * @return Objeto Prestamo
     */
    public Prestamo getPrestamo(int id) {
        Prestamo prestamo = null;
        Date fechaDev = null;
        try {
            Cursor cursor = db.query(NOMBRE_TABLA_PRESTAMO, null, PRESTAMO_ID + "=" + id, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();

                if (!cursor.isNull(cursor.getColumnIndex(PRESTAMO_FECHA_DEVOLUCION))) {
                   fechaDev= Utils.getDateFromString(cursor.getString(cursor.getColumnIndex(PRESTAMO_FECHA_DEVOLUCION)).toString());
                }
                prestamo = new Prestamo(cursor.getInt(cursor.getColumnIndex(PRESTAMO_ID)),
                        Utils.getDateFromString(cursor.getString(cursor.getColumnIndex(PRESTAMO_FECHA_PRESTAMO)).toString()),
                        Utils.getDateFromString(cursor.getString(cursor.getColumnIndex(PRESTAMO_FECHA_PREVISTA_DEVOLUCION)).toString()),
                        fechaDev,
                        cursor.getString(cursor.getColumnIndex(PRESTAMO_OBSERVACIONES)),
                        cursor.getInt(cursor.getColumnIndex(PRESTAMO_ESTADO)),
                        cursor.getInt(cursor.getColumnIndex(PRESTAMO_ID_EJEMPLAR)),
                        cursor.getInt(cursor.getColumnIndex(PRESTAMO_ID_USUARIO)));
            }
        } catch (CursorIndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }


        return prestamo;
    }

    /**
     * Método que obtiene todos los prestamos del usuario
     * @author Rafael Bravo Contreras
     *
     * @param idUsuario Identificador del usuario
     * @return Listado de objetos Prestamo
     */
    public ArrayList<Prestamo> getPrestamosUsuario(int idUsuario) {
        ArrayList<Prestamo> listadoPrestamos = null;
        Date fechaDev = null;
        try {
            Cursor cursor = db.query(NOMBRE_TABLA_PRESTAMO, null, PRESTAMO_ID_USUARIO + "=" + idUsuario, null, null, null, null);
            if (cursor != null) {
                listadoPrestamos = new ArrayList<>();
                cursor.moveToFirst();
                do {
                    String x= cursor.getString(cursor.getColumnIndex(PRESTAMO_FECHA_PRESTAMO)).toString();
                    Date d = Utils.getDateFromString(cursor.getString(cursor.getColumnIndex(PRESTAMO_FECHA_PRESTAMO)).toString());
                    if (!cursor.isNull(cursor.getColumnIndex(PRESTAMO_FECHA_DEVOLUCION))) {
                        fechaDev = Utils.getDateFromString(cursor.getString(cursor.getColumnIndex(PRESTAMO_FECHA_DEVOLUCION)).toString());
                    }
                    Prestamo prestamo = new Prestamo(cursor.getInt(cursor.getColumnIndex(PRESTAMO_ID)),
                            Utils.getDateFromString(cursor.getString(cursor.getColumnIndex(PRESTAMO_FECHA_PRESTAMO)).toString()),
                            Utils.getDateFromString(cursor.getString(cursor.getColumnIndex(PRESTAMO_FECHA_PREVISTA_DEVOLUCION)).toString()),
                            fechaDev,
                            cursor.getString(cursor.getColumnIndex(PRESTAMO_OBSERVACIONES)),
                            cursor.getInt(cursor.getColumnIndex(PRESTAMO_ESTADO)),
                            cursor.getInt(cursor.getColumnIndex(PRESTAMO_ID_EJEMPLAR)),
                            cursor.getInt(cursor.getColumnIndex(PRESTAMO_ID_USUARIO)));
                    listadoPrestamos.add(prestamo);
                } while (cursor.moveToNext());
            }
        } catch (CursorIndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }

        return listadoPrestamos;
    }

    /**
     * Método que introduce un préstamo
     * @author Rafael Bravo Contreras
     *
     * @param prestamo Objeto Prestamo
     */
    public void putPrestamo(Prestamo prestamo) {
        ContentValues values = new ContentValues();


        values.put(PRESTAMO_ID, prestamo.getId());
        values.put(PRESTAMO_FECHA_PRESTAMO, Utils.getStringFromDate(prestamo.getFechaPrestamo()));
        values.put(PRESTAMO_FECHA_PREVISTA_DEVOLUCION, Utils.getStringFromDate(prestamo.getFechaPrevistaDevolucion()));
        values.put(PRESTAMO_FECHA_DEVOLUCION, Utils.getStringFromDate(prestamo.getFechaDevolucion()));
        values.put(PRESTAMO_OBSERVACIONES, prestamo.getObservaciones());
        values.put(PRESTAMO_ESTADO, prestamo.getEstado());
        values.put(PRESTAMO_ID_EJEMPLAR, prestamo.getIdEjemplar());
        values.put(PRESTAMO_ID_USUARIO, prestamo.getIdUsuario());

        db.insert(NOMBRE_TABLA_PRESTAMO, null, values);
    }

    /**
     * Metodo que obtiene los datos de una reserva
     * @author Rafael Bravo Contreras
     *
     * @param id Identificador de la reserva
     * @return Objeto Reserva
     */
    public Reserva getReserva(int id) {
        Reserva reserva = null;
        try {
            Cursor cursor = db.query(NOMBRE_TABLA_RESERVA, null, RESERVA_ID + "=" + id, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
               // cursor.moveToFirst();
                reserva = new Reserva(cursor.getInt(cursor.getColumnIndex(RESERVA_ID)),
                        Utils.getDateFromString(cursor.getString(cursor.getColumnIndex(RESERVA_FECHA_RESERVA)).toString()),
                        Utils.getDateFromString(cursor.getString(cursor.getColumnIndex(RESERVA_FECHA_CADUCIDAD)).toString()),
                        cursor.getString(cursor.getColumnIndex(RESERVA_OBSERVACIONES)),
                        cursor.getInt(cursor.getColumnIndex(RESERVA_ESTADO)),
                        cursor.getInt(cursor.getColumnIndex(RESERVA_ID_EJEMPLAR)),
                        cursor.getInt(cursor.getColumnIndex(RESERVA_ID_USUARIO)));
            }
        } catch (CursorIndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }

        return  reserva;
    }

    /**
     * Método que obtiene todas las reservas del usuario
     * @author Rafael Bravo Contreras
     *
     * @param idUsuario Identificador del usuario
     * @return Listado de objetos Reserva
     */
    public ArrayList<Reserva> getReservasUsuario(int idUsuario) {
        ArrayList<Reserva> listadoReservas = null;
        try {
            Cursor cursor = db.query(NOMBRE_TABLA_RESERVA, null, RESERVA_ID_USUARIO + "=" + idUsuario, null, null, null, null);
            if (cursor != null && cursor.moveToFirst() ) {
                listadoReservas = new ArrayList<>();
              //  cursor.moveToFirst();
                do {
                    Reserva reserva = new Reserva(cursor.getInt(cursor.getColumnIndex(RESERVA_ID)),
                            Utils.getDateFromString(cursor.getString(cursor.getColumnIndex(RESERVA_FECHA_RESERVA)).toString()),
                            Utils.getDateFromString(cursor.getString(cursor.getColumnIndex(RESERVA_FECHA_CADUCIDAD)).toString()),
                            cursor.getString(cursor.getColumnIndex(RESERVA_OBSERVACIONES)),
                            cursor.getInt(cursor.getColumnIndex(RESERVA_ESTADO)),
                            cursor.getInt(cursor.getColumnIndex(RESERVA_ID_EJEMPLAR)),
                            cursor.getInt(cursor.getColumnIndex(RESERVA_ID_USUARIO)));
                    listadoReservas.add(reserva);
                } while (cursor.moveToNext());
            }
        } catch (CursorIndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }

        return listadoReservas;
    }

    /**
     * Metodo que introduce una reserva
     * @author Rafael Bravo Contreras
     *
     * @param reserva Objeto Reserva
     */
    public void putReserva(Reserva reserva) {
        ContentValues values = new ContentValues();

        values.put(RESERVA_ID, reserva.getId());
        values.put(RESERVA_FECHA_RESERVA, Utils.getStringFromDate(reserva.getFechaReserva()));
        values.put(RESERVA_FECHA_CADUCIDAD, Utils.getStringFromDate(reserva.getFechaCaducidad()));
        values.put(RESERVA_OBSERVACIONES, reserva.getObservaciones());
        values.put(RESERVA_ESTADO, reserva.getEstado());
        values.put(RESERVA_ID_EJEMPLAR, reserva.getIdEjemplar());
        values.put(RESERVA_ID_USUARIO, reserva.getIdUsuario());

        db.insert(NOMBRE_TABLA_RESERVA, null, values);
    }

    /**
     * Metodo que obtiene los datos de un rol
     * @author Rafael Bravo Contreras
     *
     * @param tipo Tipo del rol
     * @return Objeto Rol
     */
    public Rol getRol(int tipo) {
        Rol rol = null;
        try {
            Cursor cursor = db.query(NOMBRE_TABLA_ROL, null, ROL_TIPO + "=" + tipo, null, null, null, null);
            if (cursor != null) {
                rol = new Rol(cursor.getInt(cursor.getColumnIndex(ROL_ID)),
                        cursor.getInt(cursor.getColumnIndex(ROL_TIPO)),
                        cursor.getString(cursor.getColumnIndex(ROL_DESCRIPCION)));
            }
        } catch (CursorIndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }

        return rol;
    }

    /**
     * Método que obtiene todos los roles disponibles
     * @author Rafael Bravo Contreras
     *
     * @return Listado de objetos Rol
     */
    public ArrayList<Rol> getAllRol() {
        ArrayList<Rol> listadoRoles = null;
        try {
            Cursor cursor = db.query(NOMBRE_TABLA_ROL, null, null, null, null, null, null);
            if (cursor != null) {
                listadoRoles = new ArrayList<>();
                cursor.moveToFirst();
                do {
                    Rol rol = new Rol(cursor.getInt(cursor.getColumnIndex(ROL_ID)),
                            cursor.getInt(cursor.getColumnIndex(ROL_TIPO)),
                            cursor.getString(cursor.getColumnIndex(ROL_DESCRIPCION)));
                    listadoRoles.add(rol);
                } while (cursor.moveToNext());
            }
        } catch (CursorIndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }

        return listadoRoles;
    }

    /**
     * Método que introduce un rol de usuario
     * @author Rafael Bravo Contreras
     *
     * @param rol Objeto Rol
     */
    public void putRol(Rol rol) {
        ContentValues values = new ContentValues();

        values.put(ROL_ID, rol.getId());
        values.put(ROL_TIPO, rol.getTipo());
        values.put(ROL_DESCRIPCION, rol.getDescripcion());

        db.insert(NOMBRE_TABLA_ROL, null, values);
    }

    /**
     * Metodo que obtiene los datos de una sanción
     * @author Rafael Bravo Contreras
     *
     * @param tipo Tipo de la sanción
     * @return Objeto Sancion
     */
    public Sancion getSancion(int tipo) {
        Sancion sancion = null;
        try {
            Cursor cursor = db.query(NOMBRE_TABLA_SANCION, null, SANCION_TIPO + "=" + tipo, null, null, null, null);
            if (cursor != null) {
                sancion = new Sancion(cursor.getInt(cursor.getColumnIndex(SANCION_ID)),
                        cursor.getInt(cursor.getColumnIndex(SANCION_TIPO)),
                        cursor.getString(cursor.getColumnIndex(SANCION_DESCRIPCION)),
                        cursor.getInt(cursor.getColumnIndex(SANCION_DIAS_REFERENCIA)),
                        cursor.getInt(cursor.getColumnIndex(SANCION_DIAS_SANCION)));
            }
        } catch (CursorIndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }

        return sancion;
    }

    /**
     * Método que obtiene todas las sanciones disponibles
     * @author Rafael Bravo Contreras
     *
     * @return Listado de objetos Sancion
     */
    public ArrayList<Sancion> getAllSancion() {
        ArrayList<Sancion> listadoSanciones = null;
        try {
            Cursor cursor = db.query(NOMBRE_TABLA_SANCION, null, null, null, null, null, null);
            if (cursor != null) {
                listadoSanciones = new ArrayList<>();
                cursor.moveToFirst();
                do {
                    Sancion sancion = new Sancion(cursor.getInt(cursor.getColumnIndex(SANCION_ID)),
                            cursor.getInt(cursor.getColumnIndex(SANCION_TIPO)),
                            cursor.getString(cursor.getColumnIndex(SANCION_DESCRIPCION)),
                            cursor.getInt(cursor.getColumnIndex(SANCION_DIAS_REFERENCIA)),
                            cursor.getInt(cursor.getColumnIndex(SANCION_DIAS_SANCION)));
                    listadoSanciones.add(sancion);
                } while (cursor.moveToNext());
            }
        } catch (CursorIndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }

        return listadoSanciones;
    }

    /**
     * Metodo que introduce una sancion
     * @author Rafael Bravo Contreras
     *
     * @param sancion Objeto Sancion
     */
    public void putSancion(Sancion sancion) {
        ContentValues values = new ContentValues();

        values.put(SANCION_ID, sancion.getId());
        values.put(SANCION_TIPO, sancion.getTipo());
        values.put(SANCION_DESCRIPCION, sancion.getDescripcion());
        values.put(SANCION_DIAS_REFERENCIA, sancion.getDiasReferencia());
        values.put(SANCION_DIAS_SANCION, sancion.getDiasSancion());

        db.insert(NOMBRE_TABLA_SANCION, null, values);
    }

    /**
     * Metodo que obtiene los datos de una sanción de usuario
     * @author Rafael Bravo Contreras
     *
     * @param id Identificador de la sanción de usuario
     * @return Objeto SancionUsuario
     */
    public SancionUsuario getSancionUsuario(int id) {
        SancionUsuario sancionUsuario = null;
        try {
            Cursor cursor = db.query(NOMBRE_TABLA_SANCION_USUARIO, null, SANCION_USUARIO_ID + "=" + id, null, null, null, null);
            if (cursor != null) {
                sancionUsuario = new SancionUsuario(cursor.getInt(cursor.getColumnIndex(SANCION_USUARIO_ID)),
                        Utils.getDateFromString(cursor.getString(cursor.getColumnIndex(SANCION_USUARIO_FECHA_APLICACION)).toString()),
                        Utils.getDateFromString(cursor.getString(cursor.getColumnIndex(SANCION_USUARIO_FECHA_VENCIMIENTO)).toString()),
                        cursor.getInt(cursor.getColumnIndex(SANCION_USUARIO_TIPO_SANCION)),
                        cursor.getInt(cursor.getColumnIndex(SANCION_USUARIO_ID_USUARIO)));
            }
        } catch (CursorIndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }

        return sancionUsuario;
    }

    /**
     * Método que obtiene todas las sanciones del usuario
     * @author Rafael Bravo Contreras
     *
     * @param idUsuario Identificador del usuario
     * @return Listado de objetos SancionUsuario
     */
    public ArrayList<SancionUsuario> getSancionesUsuario(int idUsuario) {
        ArrayList<SancionUsuario> listadoSancionesUsuario = null;
        try {
            Cursor cursor = db.query(NOMBRE_TABLA_SANCION_USUARIO, null, SANCION_USUARIO_ID_USUARIO + "=" + idUsuario,
                    null, null, null, null);
            if (cursor != null) {
                listadoSancionesUsuario = new ArrayList<>();
                cursor.moveToFirst();
                do {
                    SancionUsuario sancionUsuario = new SancionUsuario(cursor.getInt(cursor.getColumnIndex(SANCION_USUARIO_ID)),
                            Utils.getDateFromString(cursor.getString(cursor.getColumnIndex(SANCION_USUARIO_FECHA_APLICACION)).toString()),
                            Utils.getDateFromString(cursor.getString(cursor.getColumnIndex(SANCION_USUARIO_FECHA_VENCIMIENTO)).toString()),
                            cursor.getInt(cursor.getColumnIndex(SANCION_USUARIO_TIPO_SANCION)),
                            cursor.getInt(cursor.getColumnIndex(SANCION_USUARIO_ID_USUARIO)));
                    listadoSancionesUsuario.add(sancionUsuario);
                } while (cursor.moveToNext());
            }
        } catch (CursorIndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }

        return listadoSancionesUsuario;
    }

    /**
     * Metodo que introduce una sanción de usuario
     * @author Rafael Bravo Contreras
     *
     * @param sancionUsuario Objeto SancionUsuario
     */
    public void putSancionUsuario(SancionUsuario sancionUsuario) {
        ContentValues values = new ContentValues();

        values.put(SANCION_USUARIO_ID, sancionUsuario.getId());
        values.put(SANCION_USUARIO_FECHA_APLICACION, Utils.getStringFromDate(sancionUsuario.getFechaAplicacion()));
        values.put(SANCION_USUARIO_FECHA_VENCIMIENTO, Utils.getStringFromDate(sancionUsuario.getFechaVencimiento()));
        values.put(SANCION_USUARIO_TIPO_SANCION, sancionUsuario.getTipoSancion());
        values.put(SANCION_USUARIO_ID_USUARIO, sancionUsuario.getIdUsuario());

        db.insert(NOMBRE_TABLA_SANCION_USUARIO, null, values);
    }

    /**
     * Método que actualiza los datos de la tabla Rol
     * @author Rafael Bravo Contreras
     *
     * @param roles Listado de objetos Rol
     */
    public void actualizarDatosTablaRol(ArrayList<Rol> roles) {
        db.beginTransaction();
        try {
            db.delete(NOMBRE_TABLA_ROL, null, null);
            for (Rol rol : roles) {
                putRol(rol);
            }
            db.setTransactionSuccessful();

        } catch (SQLException e) {
            Log.d("actualizarTablaRol", e.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    /**
     * Método que actualiza los datos de la tabla Sancion
     * @author Rafael Bravo Contreras
     *
     * @param sanciones Listado de objetos Sancion
     */
    public void actualizarDatosTablaSancion(ArrayList<Sancion> sanciones) {
        db.beginTransaction();
        try {
            db.delete(NOMBRE_TABLA_SANCION, null, null);
            for (Sancion sancion : sanciones) {
                putSancion(sancion);
            }
            db.setTransactionSuccessful();

        } catch (SQLException e) {
            Log.d("actualizarTablaSancion", e.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    /**
     * Método que actualiza los datos de la tabla Prestamo
     * @author Rafael Bravo Contreras
     *
     * @param prestamosUsuario Listado de objetos Prestamo
     */
    public void actualizarDatosTablaPrestamo(ArrayList<Prestamo> prestamosUsuario) {
        db.beginTransaction();
        try {
            db.delete(NOMBRE_TABLA_PRESTAMO, PRESTAMO_ID_USUARIO + "=" + prestamosUsuario.get(0).getIdUsuario(), null);
            for (Prestamo prestamo : prestamosUsuario) {
                putPrestamo(prestamo);
            }
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            Log.d("actualizarPrestamo", e.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    /**
     * Método que actualiza los datos de la tabla SancionUsuario
     * @author Rafael Bravo Contreras
     *
     * @param sancionesUsuario Listado de objetos SancionUsuario
     */
    public void actualizarDatosTablaSancionUsuario(ArrayList<SancionUsuario> sancionesUsuario) {
        db.beginTransaction();
        try {
            db.delete(NOMBRE_TABLA_SANCION_USUARIO, SANCION_USUARIO_ID_USUARIO + "=" + sancionesUsuario.get(0).getIdUsuario(), null);
            for (SancionUsuario sancionUsuario : sancionesUsuario) {
                putSancionUsuario(sancionUsuario);
            }
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            Log.d("actualizarSancionUsr", e.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    /**
     * Método que actualiza los datos de la tabla Reserva de un usuario
     * @author Rafael Bravo Contreras
     *
     * @param reservasUsuario Listado de objetos Reserva de un usuario
     */
    public void actualizarDatosReservasUsuario(ArrayList<Reserva> reservasUsuario) {
        db.beginTransaction();
        try {
            db.delete(NOMBRE_TABLA_RESERVA, RESERVA_ID_USUARIO + "=" + reservasUsuario.get(0).getIdUsuario(), null);
            for (Reserva reserva : reservasUsuario) {
                putReserva(reserva);
            }
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            Log.d("actualizarReservaUsr", e.getMessage());
        } finally {
            db.endTransaction();
        }
    }


    /**
     * Clase que proporciona la conexión a la base de datos
     */
    private static class DBOpenHelper extends SQLiteOpenHelper {

        @Override
        public void onOpen(SQLiteDatabase db) {
            super.onOpen(db);
        }

        DBOpenHelper(Context con) {
            super(con, NOMBRE_BD, null, VERSION_BD);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {

                db.execSQL(BD_CREATE_USUARIO);
                db.execSQL(BD_CREATE_RELACION_USUARIOS_DB_CLOUDLIB);
				db.execSQL(BD_CREATE_ROL);
				db.execSQL(BD_CREATE_PRESTAMO);
				db.execSQL(BD_CREATE_RESERVA);
				db.execSQL(BD_CREATE_SANCION);
				db.execSQL(BD_CREATE_SANCION_USUARIO);
				
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			
			if (newVersion > oldVersion) {
				
				try {

					db.execSQL("DROP TABLE IF EXISTS " + NOMBRE_TABLA_USUARIO);
                    db.execSQL("DROP TABLE IF EXISTS " + NOMBRE_TABLA_RELACION_USUARIOS_DB_CLOUDLIB);
                    db.execSQL("DROP TABLE IF EXISTS " + NOMBRE_TABLA_ROL);
                    db.execSQL("DROP TABLE IF EXISTS " + NOMBRE_TABLA_PRESTAMO);
					db.execSQL("DROP TABLE IF EXISTS " + NOMBRE_TABLA_RESERVA);
					db.execSQL("DROP TABLE IF EXISTS " + NOMBRE_TABLA_SANCION);
					db.execSQL("DROP TABLE IF EXISTS " + NOMBRE_TABLA_SANCION_USUARIO);
					
					onCreate(db);
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
        }
    }

    public void borraTabla (String tabla) {
        db.delete(tabla, null,null);
    }
}