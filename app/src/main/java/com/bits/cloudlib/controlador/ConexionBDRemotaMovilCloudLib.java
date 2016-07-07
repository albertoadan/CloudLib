package com.bits.cloudlib.controlador;

/**
 * Created by Alberto Adán & Rafael Bravo & Víctor Castellanos on 02/04/2016.
 */
import com.bits.cloudlib.objetos.Biblioteca;
import com.bits.cloudlib.objetos.Disponible;
import com.bits.cloudlib.objetos.Ejemplar;
import com.bits.cloudlib.objetos.FechaHoraConexionUsuario;
import com.bits.cloudlib.objetos.Libro;
import com.bits.cloudlib.objetos.Prestamo;
import com.bits.cloudlib.objetos.Reserva;
import com.bits.cloudlib.objetos.Rol;
import com.bits.cloudlib.objetos.Sancion;
import com.bits.cloudlib.objetos.SancionUsuario;
import com.bits.cloudlib.objetos.Seccion;
import com.bits.cloudlib.objetos.Usuario;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Clase que proporciona una conexión con la base de datos y métodos para
 * introducir, consultar y actualizar los datos en la base de datos
 *
 * @author Rafael Bravo Contreras
 */
public class ConexionBDRemotaMovilCloudLib {

    // Constantes: valores por defecto
    private static final String DEFAULT_USUARIO = "cloudlib";
    private static final String DEFAULT_PASSWORD = "admin";
    private static final String DEFAULT_NOMBRE_BD = "cloudlib";
    private static final String DEFAULT_IP = "localhost";
    private static final String DEFAULT_PORT = "3306";

    // Variable: Conexión con la BD
    private Connection conexion;

    // Variables: Datos de conexión
    private final String usuario, password, nombreBD, ip, port, urlConexion;

    /**
     * Constructor sin parámetros
     *
     * @throws ConexionBDRemotaCloudLibException
     */
    public ConexionBDRemotaMovilCloudLib() throws ConexionBDRemotaCloudLibException {
        usuario = DEFAULT_USUARIO;
        password = DEFAULT_PASSWORD;
        nombreBD = DEFAULT_NOMBRE_BD;
        ip = DEFAULT_IP;
        port = DEFAULT_PORT;
        urlConexion = getUrlConexion();
        conexion = getConexionBDRemotaCloudLib();
    }

    /**
     * Constructor con parámetros
     *
     * @param usuario Usuario de la base de datos del sistema
     * @param password Password del usuario de la base de datos del sistema
     * @param nombreBD Nombre de la base de datos del sistema
     * @param ip IP de conexión con la base de datos del sistema
     * @param port Puerto de conexión con la base de datos del sistema
     *
     * @throws ConexionBDRemotaCloudLibException
     */
    public ConexionBDRemotaMovilCloudLib(String usuario, String password, String nombreBD, String ip, String port)
            throws ConexionBDRemotaCloudLibException {
        this.usuario = usuario;
        this.password = password;
        this.nombreBD = nombreBD;
        this.ip = ip;
        this.port = port;
        urlConexion = getUrlConexion();
        conexion = getConexionBDRemotaCloudLib();
    }

    /**
     * Método para obtener la conexión con la base de datos
     *
     * @author Rafael Bravo Contreras
     *
     * @return Objeto Connection
     * @throws ConexionBDRemotaCloudLibException
     */
    public final Connection getConexionBDRemotaCloudLib() throws ConexionBDRemotaCloudLibException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexion = (Connection) DriverManager.getConnection(urlConexion, usuario, password);

        } catch (SQLException | ClassNotFoundException ex) {
            throw new ConexionBDRemotaCloudLibException(ex.getMessage(), ex);
        }

        return conexion;
    }

    /**
     * Método que cierra la conexión con la base de datos
     *
     * @throws ConexionBDRemotaCloudLibException
     */
    public void closeConexionBDRemotaCloudLib() throws ConexionBDRemotaCloudLibException {
        try {
            conexion.close();
        } catch (SQLException sqle) {
            throw new ConexionBDRemotaCloudLibException(sqle.getMessage(), sqle);
        }
    }

    /**
     * Método que comprueba si la conexión con la base de datos está cerrada
     *
     * @author Rafael Bravo Contreras
     *
     * @return true si la conexión está activa o false si no lo está
     * @throws ConexionBDRemotaCloudLibException
     */
    public boolean isConnectionClosed() {
        return conexion.isClosed();
    }

    /**
     * Método que comprueba si el objeto Connection es null
     *
     * @author Rafael Bravo Contreras
     *
     * @return true si conexion es null, false en caso contrario
     */
    public boolean isConnectionNull() {
        return conexion == null;
    }

    /**
     * Método que obtiene un listado de objetos Biblioteca
     *
     * @author Rafael Bravo Contreras
     *
     * @return listado de objetos Biblioteca
     * @throws ConexionBDRemotaCloudLibException
     */
    public ArrayList<Biblioteca> getAllBiblioteca() throws ConexionBDRemotaCloudLibException {
        ResultSet rs;
        Statement st;
        ArrayList<Biblioteca> listadoBiblioteca;

        try {
            //Preparamos la consulta
            String consulta = "SELECT * FROM biblioteca ORDER BY nombre ASC";
            st = (Statement) conexion.createStatement();
            //Y ejecutamos
            rs = st.executeQuery(consulta);
            //Obtenemos el unico resultado.
            if (rs.next()) {
                listadoBiblioteca = new ArrayList<>();
                do {
                    Biblioteca biblioteca = new Biblioteca(rs.getInt(1), rs.getString(2),
                            rs.getString(3), rs.getString(4));
                    listadoBiblioteca.add(biblioteca);
                } while (rs.next());

                return listadoBiblioteca;

            } else {
                throw new ConexionBDRemotaCloudLibException("No se han obtenido resultados");
            }

        } catch (SQLException sqle) {
            throw new ConexionBDRemotaCloudLibException(sqle.getMessage(), sqle);
        }
    }
	
	/**
     * Método que obtiene un libro a partir de un identificador de ejemplar
     * @author Rafael Bravo Contreras
     *
     * @param idEjemplar Identificador de ejemplar
     * @return objeto Libro
     * @throws ConexionBDRemotaCloudLibException
     */
    public Libro getLibroPorIdEjemplar(int idEjemplar) throws ConexionBDRemotaCloudLibException {
        ResultSet rs;
        PreparedStatement ps;
        Libro libro;

        try {
            //Preparamos la consulta
            String consulta = "SELECT * FROM libro WHERE _id=(SELECT id_libro FROM ejemplar WHERE _id=?)";
            ps = (PreparedStatement) conexion.prepareStatement(consulta);
            //Añadimos los parametros y ejecutamos.
            ps.setInt(1, idEjemplar);
            rs = ps.executeQuery();
            //Obtenemos el unico resultado.
            if (rs.next()) {
                libro = new Libro(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getInt(7), rs.getString(8));

                return libro;

            } else {
                throw new ConexionBDRemotaCloudLibException("No se han obtenido resultados");
            }

        } catch (SQLException sqle) {
            throw new ConexionBDRemotaCloudLibException(sqle.getMessage(), sqle);
        }
    }

    /**
     * Método que obtiene un libro
     * @author Víctor Castellanos
     *
     * @param titulo Titulo del libro
     * @return objeto Libro
     * @throws ConexionBDRemotaCloudLibException
     */
    public Libro getLibroByTitulo(String titulo) throws ConexionBDRemotaCloudLibException {
        ResultSet rs;
        PreparedStatement ps;
        Libro libro;

        try {
            //Preparamos la consulta
            String consulta = "SELECT * FROM libro WHERE titulo=?";
            ps = (PreparedStatement) conexion.prepareStatement(consulta);
            //Añadimos los parametros y ejecutamos.
            ps.setString(1, titulo);
            rs = ps.executeQuery();
            //Obtenemos el unico resultado.
            if (rs.next()) {
                libro = new Libro(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getInt(7), rs.getString(8));

                return libro;

            } else {
                throw new ConexionBDRemotaCloudLibException("No se han obtenido resultados");
            }

        } catch (SQLException sqle) {
            throw new ConexionBDRemotaCloudLibException(sqle.getMessage(), sqle);
        }
    }

    /**
     * Método que obtiene una sección genérica
     * @author Rafael Bravo Contreras
     *
     * @param idLibro Identificador del ejemplar
     * @return objeto Seccion
     * @throws ConexionBDRemotaCloudLibException
     */
    public Seccion getSeccionPorIdLibro(int idLibro) throws ConexionBDRemotaCloudLibException {
        ResultSet rs;
        PreparedStatement ps;
        Seccion seccion;

        try {
            //Preparamos la consulta
            String consulta = "SELECT * FROM seccion WHERE _id=(SELECT id_seccion FROM ejemplar WHERE id_libro=? LIMIT 1) ";
            ps = (PreparedStatement) conexion.prepareStatement(consulta);
            //Añadimos los parametros y ejecutamos.
            ps.setInt(1, idLibro);
            rs = ps.executeQuery();
            //Obtenemos el unico resultado.
            if (rs.next()) {
                seccion = new Seccion(rs.getInt(1), rs.getString(2));
                return seccion;

            } else {
                throw new ConexionBDRemotaCloudLibException("No se han obtenido resultados");
            }

        } catch (SQLException sqle) {
            throw new ConexionBDRemotaCloudLibException(sqle.getMessage(), sqle);
        }
    }

    /**
     * Método que obtiene una sección genérica
     * @author Rafael Bravo Contreras
     *
     * @param idEjemplar Identificador del ejemplar
     * @return objeto Seccion
     * @throws ConexionBDRemotaCloudLibException
     */
    public Seccion getSeccionPorIdEjemplar(int idEjemplar) throws ConexionBDRemotaCloudLibException {
        ResultSet rs;
        PreparedStatement ps;
        Seccion seccion;

        try {
            //Preparamos la consulta
            String consulta = "SELECT * FROM seccion WHERE _id=(SELECT id_seccion FROM ejemplar WHERE _id=?)";
            ps = (PreparedStatement) conexion.prepareStatement(consulta);
            //Añadimos los parametros y ejecutamos.
            ps.setInt(1, idEjemplar);
            rs = ps.executeQuery();
            //Obtenemos el unico resultado.
            if (rs.next()) {
                seccion = new Seccion(rs.getInt(1), rs.getString(2));
                return seccion;

            } else {
                throw new ConexionBDRemotaCloudLibException("No se han obtenido resultados");
            }

        } catch (SQLException sqle) {
            throw new ConexionBDRemotaCloudLibException(sqle.getMessage(), sqle);
        }
    }

    /**
     * Metodo que obtiene un usuario
     *
     * @author Víctor Castellanos Pérez & Rafael Bravo Contreras
     *
     * @param email - Email del usuario
     * @return Objeto Usuario
     * @throws ConexionBDRemotaCloudLibException
     */
    public Usuario getUsuario(String email) throws ConexionBDRemotaCloudLibException {
        ResultSet rs;
        PreparedStatement ps;
        Usuario usr;

        try {
            //Preparamos la consulta
            String consulta = "SELECT * FROM usuario WHERE email=?";
            ps = (PreparedStatement) conexion.prepareStatement(consulta);
            //Añadimos los parametros y ejecutamos.
            ps.setString(1, email);
            rs = ps.executeQuery();
            //Obtenemos el unico resultado.
            if (rs.next()) {
                usr = new Usuario(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9),
                        rs.getString(10), rs.getString(11), rs.getInt(12), rs.getInt(13), rs.getDate(14),
                        rs.getDate(15), rs.getInt(16), rs.getInt(17));

                return usr;

            } else {
                throw new ConexionBDRemotaCloudLibException("No se han obtenido resultados");
            }

        } catch (SQLException sqle) {
            throw new ConexionBDRemotaCloudLibException(sqle.getMessage(), sqle);
        }
    }

    /**
     * Metodo que obtiene un usuario
     *
     * @author Víctor Castellanos Pérez & Rafael Bravo Contreras
     *
     * @param id - Identificador del usuario
     * @return Objeto Usuario
     * @throws ConexionBDRemotaCloudLibException
     */
    public Usuario getUsuario(int id) throws ConexionBDRemotaCloudLibException {
        ResultSet rs;
        PreparedStatement ps;
        Usuario usr;

        try {
            //Preparamos la consulta
            String consulta = "SELECT * FROM usuario WHERE _id=?";
            ps = (PreparedStatement) conexion.prepareStatement(consulta);
            //Añadimos los parametros y ejecutamos.
            ps.setInt(1, id);
            rs = ps.executeQuery();
            //Obtenemos el unico resultado.
            if (rs.next()) {
                usr = new Usuario(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9),
                        rs.getString(10), rs.getString(11), rs.getInt(12), rs.getInt(13), rs.getDate(14),
                        rs.getDate(15), rs.getInt(16), rs.getInt(17));

                return usr;

            } else {
                throw new ConexionBDRemotaCloudLibException("No se han obtenido resultados");
            }

        } catch (SQLException sqle) {
            throw new ConexionBDRemotaCloudLibException(sqle.getMessage(), sqle);
        }
    }

    /**
     * Método que introduce un usuario. Incluye la introducción de fecha y hora de
     * conexión.
     *
     * @author Rafael Bravo Contreras
     *
     * @param usuario objeto usr
     * @throws ConexionBDRemotaCloudLibException
     */
    public void putUsuario(Usuario usuario) throws ConexionBDRemotaCloudLibException {
        PreparedStatement ps;

        try {
            //Preparamos la consulta
            String consulta = "INSERT INTO usuario (nombre, apellidos, dni, direccion, poblacion, "
                    + "codigo_postal, telefono, email, observaciones, password,  fecha_alta, rol, id_biblioteca) VALUES "
                    + "(?, ?, ?, ?, ?, ?, ?, ?, ?, md5(?), now(), ?, ?)";
            ps = (PreparedStatement) conexion.prepareStatement(consulta);
            //Añadimos los parametros y ejecutamos.
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellidos());
            ps.setString(3, usuario.getDni());
            ps.setString(4, usuario.getDireccion());
            ps.setString(5, usuario.getPoblacion());
            ps.setString(6, usuario.getCodigoPostal());
            ps.setString(7, usuario.getTelefono());
            ps.setString(8, usuario.getEmail());
            ps.setString(9, usuario.getObservaciones());
            ps.setString(10, usuario.getPassword());
            ps.setInt(11, usuario.getRol());
            ps.setInt(12, usuario.getIdBiblioteca());

            ps.executeUpdate();

        } catch (SQLException sqle) {
            throw new ConexionBDRemotaCloudLibException(sqle.getMessage(), sqle);
        }
    }

    /**
     * Método que actualiza todos los datos de un usuario
     *
     * @author Rafael Bravo Contreras
     *
     * @param usuario objeto Usuario
     * @throws ConexionBDRemotaCloudLibException
     */
    public void updateUsuario(Usuario usuario) throws ConexionBDRemotaCloudLibException {

        switch (usuario.getId()) {
            case 1:
                throw new ConexionBDRemotaCloudLibException("Error: Éste usuario no es modificable");
        }

        PreparedStatement ps;

        try {
            //Preparamos la consulta
            String consulta = "UPDATE usuario SET nombre=?, apellidos=?, dni=?, direccion=?, poblacion=?, codigo_postal=?, telefono=?, "
                    + "email=?, observaciones=?, contador_bloqueo=?, " +
                    "rol=?, id_biblioteca=? WHERE _id=?";
            ps = (PreparedStatement) conexion.prepareStatement(consulta);
            //Añadimos los parametros y ejecutamos.
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellidos());
            ps.setString(3, usuario.getDni());
            ps.setString(4, usuario.getDireccion());
            ps.setString(5, usuario.getPoblacion());
            ps.setString(6, usuario.getCodigoPostal());
            ps.setString(7, usuario.getTelefono());
            ps.setString(8, usuario.getEmail());
            ps.setString(9, usuario.getObservaciones());
            ps.setInt(10, usuario.getContadorBloqueo());
            ps.setInt(11, usuario.getRol());
            ps.setInt(12, usuario.getIdBiblioteca());
            ps.setInt(13, usuario.getId());

            int executeUpdate = ps.executeUpdate();

            if (executeUpdate < 1) {
                throw new ConexionBDRemotaCloudLibException("Error: El objeto con id=" + usuario.getId()
                        + " no se ha podido actualizar");
            }

        } catch (SQLException sqle) {
            throw new ConexionBDRemotaCloudLibException(sqle.getMessage(), sqle);
        }
    }

    /**
     * Método que actualiza el password de un usuario
     *
     * @author Rafael Bravo Contreras
     *
     * @param idUsuario Identificador del usuario
     * @param password Password
     * @throws ConexionBDRemotaCloudLibException
     */
    public void updatePasswordUsuario(int idUsuario, String password) throws ConexionBDRemotaCloudLibException {
        PreparedStatement ps;

        try {
            //Preparamos la consulta
            String consulta = "UPDATE usuario SET password=md5(?) WHERE _id=?";
            ps = (PreparedStatement) conexion.prepareStatement(consulta);
            //Añadimos los parametros y ejecutamos.
            ps.setString(1, password);
            ps.setInt(2, idUsuario);

            int executeUpdate = ps.executeUpdate();

            if (executeUpdate < 1) {
                throw new ConexionBDRemotaCloudLibException("Error: El objeto con id=" + idUsuario
                        + " no se ha podido actualizar");
            }

        } catch (SQLException sqle) {
            throw new ConexionBDRemotaCloudLibException(sqle.getMessage(), sqle);
        }
    }

    /**
     * Método que introduce el número de días de retraso de devoluciones de un
     * usuario
     *
     * @author Rafael Bravo Contreras
     *
     * @param idUsuario Identificador del usuario
     * @param numDias Número de días
     * @throws ConexionBDRemotaCloudLibException
     */
    public void setDiasRetrasoDevolucionesUsuario(int idUsuario, int numDias) throws ConexionBDRemotaCloudLibException {
        PreparedStatement ps;

        try {
            //Preparamos la consulta
            String consulta = "UPDATE usuario SET dias_retraso_devoluciones=? WHERE _id=?";
            ps = (PreparedStatement) conexion.prepareStatement(consulta);
            //Añadimos los parametros y ejecutamos.
            ps.setInt(1, numDias);
            ps.setInt(2, idUsuario);

            int executeUpdate = ps.executeUpdate();

            if (executeUpdate < 1) {
                throw new ConexionBDRemotaCloudLibException("Error: El objeto con id= " + idUsuario
                        + " no se ha podido actualizar");
            }

        } catch (SQLException sqle) {
            throw new ConexionBDRemotaCloudLibException(sqle.getMessage(), sqle);
        }
    }

    /**
     * Método que incrementa el contador de bloqueo de un usuario
     *
     * @author Víctor Castellanos Pérez & Rafael Bravo Contreras
     *
     * @param idUsuario Identificador del usuario.
     * @throws ConexionBDRemotaCloudLibException
     */
    public void incrementContadorBloqueoUsuario(int idUsuario) throws ConexionBDRemotaCloudLibException {
        PreparedStatement ps;

        try {
            //Preparamos la consulta
            String consulta = "UPDATE usuario SET contador_bloqueo=contador_bloqueo+1 WHERE _id=?";
            ps = (PreparedStatement) conexion.prepareStatement(consulta);
            //Añadimos los parametros y ejecutamos.
            ps.setInt(1, idUsuario);

            int executeUpdate = ps.executeUpdate();

            if (executeUpdate < 1) {
                throw new ConexionBDRemotaCloudLibException("Error: El objeto con id=" + idUsuario
                        + " no se ha podido actualizar");
            }

        } catch (SQLException sqle) {
            throw new ConexionBDRemotaCloudLibException(sqle.getMessage(), sqle);
        }
    }

    /**
     * Método que resetea a 0 el contador de bloqueo de un usuario
     *
     * @author Víctor Castellanos Pérez & Rafael Bravo Contreras
     *
     * @param idUsuario Identificador del usuario.
     * @throws ConexionBDRemotaCloudLibException
     */
    public void resetContadorBloqueoUsuario(int idUsuario) throws ConexionBDRemotaCloudLibException {
        PreparedStatement ps;

        try {
            //Preparamos la consulta
            String consulta = "UPDATE usuario SET contador_bloqueo=0 WHERE _id=?";
            ps = (PreparedStatement) conexion.prepareStatement(consulta);
            //Añadimos los parametros y ejecutamos.
            ps.setInt(1, idUsuario);

            int executeUpdate = ps.executeUpdate();

            if (executeUpdate < 1) {
                throw new ConexionBDRemotaCloudLibException("Error: El objeto con id=" + idUsuario
                        + " no se ha podido actualizar");
            }

        } catch (SQLException sqle) {
            throw new ConexionBDRemotaCloudLibException(sqle.getMessage(), sqle);
        }
    }

    /**
     * Método que gestiona los cambios necesarios en la base de datos para dar
     * de baja a un usuario
     *
     * @author Rafael Bravo Contreras
     *
     * @param idUsuario Identificador del usuario
     * @throws ConexionBDRemotaCloudLibException
     */
    public void setBajaUsuario(int idUsuario) throws ConexionBDRemotaCloudLibException {
        PreparedStatement ps;

        try {
            //Preparamos la consulta
            String consulta = "UPDATE usuario SET fecha_baja=now(), rol=3 WHERE _id=?";
            ps = (PreparedStatement) conexion.prepareStatement(consulta);
            //Añadimos los parametros y ejecutamos.
            ps.setInt(1, idUsuario);

            int executeUpdate = ps.executeUpdate();

            if (executeUpdate < 1) {
                throw new ConexionBDRemotaCloudLibException("Error: El objeto con id=" + idUsuario
                        + " no se ha podido actualizar");
            }

        } catch (SQLException sqle) {
            throw new ConexionBDRemotaCloudLibException(sqle.getMessage(), sqle);
        }
    }

    /**
     * Método que obtiene la fecha y hora más reciente de conexión de un usuario
     *
     * @author Rafael Bravo Contreras
     *
     * @param idUsuario identificador de usuario
     * @return objeto FechaHoraConexionUsuario
     * @throws ConexionBDRemotaCloudLibException
     */
    public FechaHoraConexionUsuario getFechaHoraConexionUsuario(int idUsuario) throws ConexionBDRemotaCloudLibException {
        ResultSet rs;
        PreparedStatement ps;
        FechaHoraConexionUsuario fechaHoraConexionUsuario;

        try {
            //Preparamos la consulta
            String consulta = "SELECT _id, max(fecha_hora_conexion), id_usuario FROM fecha_hora_conexion_usuario "
                    + "WHERE id_usuario=?";
            ps = (PreparedStatement) conexion.prepareStatement(consulta);
            //Añadimos los parametros y ejecutamos.
            ps.setInt(1, idUsuario);
            rs = ps.executeQuery();
            //Obtenemos el unico resultado.
            if (rs.next()) {
                fechaHoraConexionUsuario = new FechaHoraConexionUsuario(rs.getInt(1), rs.getDate(2),
                        rs.getInt(3));

                return fechaHoraConexionUsuario;

            } else {
                throw new ConexionBDRemotaCloudLibException("No se han obtenido resultados");
            }

        } catch (SQLException sqle) {
            throw new ConexionBDRemotaCloudLibException(sqle.getMessage(), sqle);
        }
    }

    /**
     * Método que introduce la fecha y hora de conexión de un usuario
     *
     * @author Rafael Bravo Contreras
     *
     * @param usuario Objeto usuario
     * @throws ConexionBDRemotaCloudLibException
     */
    public void putFechaHoraConexionUsuario(Usuario usuario) throws ConexionBDRemotaCloudLibException {
        PreparedStatement ps;

        try {
            //Preparamos la consulta
            String consulta = "INSERT INTO fecha_hora_conexion_usuario (fecha_hora_conexion, id_usuario) "
                    + "VALUES (now(), (SELECT _id FROM usuario WHERE email=?))";
            ps = (PreparedStatement) conexion.prepareStatement(consulta);
            //Añadimos los parametros y ejecutamos.
            ps.setString(1, usuario.getEmail());

            ps.executeUpdate();

        } catch (SQLException sqle) {
            throw new ConexionBDRemotaCloudLibException(sqle.getMessage(), sqle);
        }
    }

    /**
     * Método que introduce la fecha y hora de conexión de un usuario.
     *
     * @author Rafael Bravo Contreras
     *
     * @param idUsuario Identificador del usuario
     * @throws ConexionBDRemotaCloudLibException
     */
    public void putFechaHoraConexionUsuario(int idUsuario) throws ConexionBDRemotaCloudLibException {
        PreparedStatement ps;

        try {
            //Preparamos la consulta
            String consulta = "INSERT INTO fecha_hora_conexion_usuario (fecha_hora_conexion, id_usuario) "
                    + "VALUES (now(), ?)";
            ps = (PreparedStatement) conexion.prepareStatement(consulta);
            //Añadimos los parametros y ejecutamos.
            ps.setInt(1, idUsuario);

            int executeUpdate = ps.executeUpdate();

        } catch (SQLException sqle) {
            throw new ConexionBDRemotaCloudLibException(sqle.getMessage(), sqle);
        }
    }

    /**
     * Método que obtiene un rol
     *
     * @author Rafael Bravo Contreras
     *
     * @param tipo el tipo de rol
     * @return objeto Rol
     * @throws ConexionBDRemotaCloudLibException
     */
    public Rol getRol(int tipo) throws ConexionBDRemotaCloudLibException {
        ResultSet rs;
        PreparedStatement ps;
        Rol rol;

        try {
            //Preparamos la consulta
            String consulta = "SELECT * FROM rol WHERE tipo=?";
            ps = (PreparedStatement) conexion.prepareStatement(consulta);
            //Añadimos los parametros y ejecutamos.
            ps.setInt(1, tipo);
            rs = ps.executeQuery();
            //Obtenemos el unico resultado.
            if (rs.next()) {
                rol = new Rol(rs.getInt(1), rs.getInt(2), rs.getString(3));

                return rol;

            } else {
                throw new ConexionBDRemotaCloudLibException("No se han obtenido resultados");
            }

        } catch (SQLException sqle) {
            throw new ConexionBDRemotaCloudLibException(sqle.getMessage(), sqle);
        }
    }

    /**
     * Método que obtiene un rol
     *
     * @author Rafael Bravo Contreras
     *
     * @param descripcion la descripcion o nombre del rol
     * @return objeto Rol
     * @throws ConexionBDRemotaCloudLibException
     */
    public Rol getRol(String descripcion) throws ConexionBDRemotaCloudLibException {
        ResultSet rs;
        PreparedStatement ps;
        Rol rol;

        try {
            //Preparamos la consulta
            String consulta = "SELECT * FROM rol WHERE descripcion=?";
            ps = (PreparedStatement) conexion.prepareStatement(consulta);
            //Añadimos los parametros y ejecutamos.
            ps.setString(1, descripcion);
            rs = ps.executeQuery();
            //Obtenemos el unico resultado.
            if (rs.next()) {
                rol = new Rol(rs.getInt(1), rs.getInt(2), rs.getString(3));

                return rol;

            } else {
                throw new ConexionBDRemotaCloudLibException("No se han obtenido resultados");
            }

        } catch (SQLException sqle) {
            throw new ConexionBDRemotaCloudLibException(sqle.getMessage(), sqle);
        }
    }

    /**
     * Método que obtiene un listado de roles
     *
     * @author Rafael Bravo Contreras
     *
     * @return listado de objetos Rol
     * @throws ConexionBDRemotaCloudLibException
     */
    public ArrayList<Rol> getAllRol() throws ConexionBDRemotaCloudLibException {
        ResultSet rs;
        Statement st;
        ArrayList<Rol> listadoRol;

        try {
            //Preparamos la consulta
            String consulta = "SELECT * FROM rol ORDER BY descripcion ASC";
            st = (Statement) conexion.createStatement();
            //Y ejecutamos
            rs = st.executeQuery(consulta);
            //Obtenemos el unico resultado.
            if (rs.next()) {
                listadoRol = new ArrayList<>();
                do {
                    Rol rol = new Rol(rs.getInt(1), rs.getInt(2), rs.getString(3));
                    listadoRol.add(rol);
                } while (rs.next());

                return listadoRol;

            } else {
                throw new ConexionBDRemotaCloudLibException("No se han obtenido resultados");
            }

        } catch (SQLException sqle) {
            throw new ConexionBDRemotaCloudLibException(sqle.getMessage(), sqle);
        }
    }

    /**
     * Método que obtiene un listado préstamos de un usuario
     *
     * @author Rafael Bravo Contreras
     *
     * @param idUsuario Identificador del usuario
     * @return objeto Prestamo
     * @throws ConexionBDRemotaCloudLibException
     */
    public ArrayList<Prestamo> getPrestamosUsuario(int idUsuario) throws ConexionBDRemotaCloudLibException {
        ResultSet rs;
        PreparedStatement ps;
        ArrayList<Prestamo> listadoPrestamos;

        try {
            //Preparamos la consulta
            String consulta = "SELECT * FROM prestamo WHERE id_usuario=?";
            ps = (PreparedStatement) conexion.prepareStatement(consulta);
            //Añadimos los parametros y ejecutamos.
            ps.setInt(1, idUsuario);
            rs = ps.executeQuery();
            //Obtenemos el unico resultado.
            if (rs.next()) {
                listadoPrestamos = new ArrayList<>();
                do {
                    Prestamo prestamo = new Prestamo(rs.getInt(1), rs.getDate(2), rs.getDate(3), rs.getDate(4),
                            rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8));
                    listadoPrestamos.add(prestamo);
                } while (rs.next());

                return listadoPrestamos;

            } else {
                throw new ConexionBDRemotaCloudLibException("No se han obtenido resultados");
            }

          //  return listadoPrestamos;

        } catch (SQLException sqle) {
            throw new ConexionBDRemotaCloudLibException(sqle.getMessage(), sqle);
        }
    }

    /**
     * Método que obtiene un listado de reservas de un usuario
     *
     * @author Rafael Bravo Contreras
     *
     * @param idUsuario Identificador del usuario al que pertenecen las reseras
     * @return listado de objetos Reserva
     * @throws ConexionBDRemotaCloudLibException
     */
    public ArrayList<Reserva> getReservasUsuario(int idUsuario) throws ConexionBDRemotaCloudLibException {
        ResultSet rs;
        PreparedStatement ps;
        ArrayList<Reserva> listadoReservas;

        try {
            //Preparamos la consulta
            String consulta = "SELECT * FROM reserva WHERE id_usuario=?";
            ps = (PreparedStatement) conexion.prepareStatement(consulta);
            //Añadimos los parametros y ejecutamos.
            ps.setInt(1, idUsuario);
            rs = ps.executeQuery();
            if (rs.next()) {
                listadoReservas = new ArrayList<>();
                do {
                    Reserva reserva = new Reserva(rs.getInt(1), rs.getDate(2), rs.getDate(3), rs.getString(4),
                            rs.getInt(5), rs.getInt(6), rs.getInt(7));
                    listadoReservas.add(reserva);
                } while (rs.next());

                return listadoReservas;

            } else {
                throw new ConexionBDRemotaCloudLibException("No se han obtenido resultados");
            }

        } catch (SQLException sqle) {
            throw new ConexionBDRemotaCloudLibException(sqle.getMessage(), sqle);
        }
    }

    /**
     * Método que introduce una reserva
     *
     * @author Rafael Bravo Contreras
     *
     * @param reserva objeto Reserva
     * @throws ConexionBDRemotaCloudLibException
     */
    public void putReserva(Reserva reserva) throws ConexionBDRemotaCloudLibException {
        PreparedStatement ps;

        try {
            //Preparamos la consulta
            String consulta = "INSERT INTO reserva (fecha_reserva, fecha_caducidad, observaciones, estado, "
                    + "id_ejemplar, id_usuario) VALUES (now(), ?, ?, ?, ?, ?)";
            ps = (PreparedStatement) conexion.prepareStatement(consulta);
            //Añadimos los parametros y ejecutamos.
            ps.setString(1, getFechaHoraFormatoBD(reserva.getFechaCaducidad()));
            ps.setString(2, reserva.getObservaciones());
            ps.setInt(3, reserva.getEstado());
            ps.setInt(4, reserva.getIdEjemplar());
            ps.setInt(5, reserva.getIdUsuario());

            ps.executeUpdate();

        } catch (SQLException sqle) {
            throw new ConexionBDRemotaCloudLibException(sqle.getMessage(), sqle);
        }
    }

    /**
     * Método que actualiza los datos de una reserva
     *
     * @author Rafael Bravo Contreras
     *
     * @param reserva objeto Reserva
     * @throws ConexionBDRemotaCloudLibException
     */
    public void updateReserva(Reserva reserva) throws ConexionBDRemotaCloudLibException {
        PreparedStatement ps;

        try {
            //Preparamos la consulta
            String consulta = "UPDATE reserva SET fecha_reserva=?, fecha_caducidad=?, observaciones=?, "
                    + "estado=?, id_ejemplar=?, id_usuario=? WHERE _id=?";
            ps = (PreparedStatement) conexion.prepareStatement(consulta);
            //Añadimos los parametros y ejecutamos.
            ps.setString(1, getFechaHoraFormatoBD(reserva.getFechaReserva()));
            ps.setString(2, getFechaHoraFormatoBD(reserva.getFechaCaducidad()));
            ps.setString(3, reserva.getObservaciones());
            ps.setInt(4, reserva.getEstado());
            ps.setInt(5, reserva.getIdEjemplar());
            ps.setInt(6, reserva.getIdUsuario());
            ps.setInt(7, reserva.getId());

            int executeUpdate = ps.executeUpdate();

            if (executeUpdate < 1) {
                throw new ConexionBDRemotaCloudLibException("Error: El objeto con id=" + reserva.getId()
                        + " no se ha podido actualizar");
            }

        } catch (SQLException sqle) {
            throw new ConexionBDRemotaCloudLibException(sqle.getMessage(), sqle);
        }
    }

    /**
     * Método que obtiene un listado de sanciones
     *
     * @author Rafael Bravo Contreras
     *
     * @return listado de objetos Sancion
     * @throws ConexionBDRemotaCloudLibException
     */
    public ArrayList<Sancion> getAllSancion() throws ConexionBDRemotaCloudLibException {
        ResultSet rs;
        Statement st;
        ArrayList<Sancion> listadoSancion;

        try {
            //Preparamos la consulta
            String consulta = "SELECT * FROM sancion ORDER BY descripcion ASC";
            st = (Statement) conexion.createStatement();
            //Y ejecutamos
            rs = st.executeQuery(consulta);
            //Obtenemos el unico resultado.
            if (rs.next()) {
                listadoSancion = new ArrayList<>();
                do {
                    Sancion sancion = new Sancion(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getInt(4),
                            rs.getInt(5));
                    listadoSancion.add(sancion);
                } while (rs.next());

                return listadoSancion;

            } else {
                throw new ConexionBDRemotaCloudLibException("No se han obtenido resultados");
            }

        } catch (SQLException sqle) {
            throw new ConexionBDRemotaCloudLibException(sqle.getMessage(), sqle);
        }
    }

    /**
     * Método que obtiene todas las sanciones de un usuario
     *
     * @author Rafael Bravo Contreras
     *
     * @param idUsuario identificador del usuario
     * @return listado de sanciones de un usuario
     * @throws ConexionBDRemotaCloudLibException
     */
    public ArrayList<SancionUsuario> getSancionesUsuario(int idUsuario) throws ConexionBDRemotaCloudLibException {
        ResultSet rs;
        PreparedStatement ps;
        ArrayList<SancionUsuario> listadoSancionesUsuario;

        try {
            //Preparamos la consulta
            String consulta = "SELECT * FROM sancion_usuario WHERE id_usuario=?";
            ps = (PreparedStatement) conexion.prepareStatement(consulta);
            //Añadimos los parametros y ejecutamos.
            ps.setInt(1, idUsuario);
            rs = ps.executeQuery();
            //Obtenemos el unico resultado.
            if (rs.next()) {
                listadoSancionesUsuario = new ArrayList<>();
                do {
                    SancionUsuario sancionUsuario = new SancionUsuario(rs.getInt(1), rs.getDate(2), rs.getDate(3),
                            rs.getInt(4), rs.getInt(5));
                    listadoSancionesUsuario.add(sancionUsuario);
                } while (rs.next());

                return listadoSancionesUsuario;

            } else {
                throw new ConexionBDRemotaCloudLibException("No se han obtenido resultados");
            }
        } catch (SQLException sqle) {
            throw new ConexionBDRemotaCloudLibException(sqle.getMessage(), sqle);
        }
    }

    /**
     * Método que construye la url de conexión con la base de datos a partir de
     * las variables de la clase
     *
     * @author Rafael Bravo Contreras
     *
     * @return URL de conexión con la base de datos
     */
    private String getUrlConexion() {
        StringBuilder builder = new StringBuilder();
        builder.append("jdbc:mysql://");
        builder.append(this.ip);
        builder.append(":");
        builder.append(this.port);
        builder.append("/");
        builder.append(this.nombreBD);

        return builder.toString();
    }

    /**
     * Método que obtiene una hora y fecha en formato 'agradable' para la base
     * de datos
     *
     * @author Rafael Bravo Contreras
     *
     * @param fecha fecha
     * @return texto con la fecha
     */
    private String getFechaHoraFormatoBD(Date fecha) {
        String formato = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat formatData = new SimpleDateFormat(formato, Locale.ROOT);
        String fechaConFormato = formatData.format(fecha);

        return fechaConFormato;
    }


    /**
     * Método que obtiene un listado de ejemplares disponibles del título solicitado
     * @author Rafael Bravo Contreras
     *
     * @param titulo Título solicitado
     * @return Listado de objetos Disponible
     * @throws ConexionBDRemotaCloudLibException
     */
    public ArrayList<Disponible> verDisponiblesPorTitulo(String titulo) throws ConexionBDRemotaCloudLibException {
        ResultSet rs;
        PreparedStatement ps;
        ArrayList<Disponible> listadoBibliotecasConEjemplaresDisponibles;

        try {
            //Preparamos la consulta
            String consulta = "select distinct b._id, b.nombre, b.direccion, " +
                    "b.descripcion, l.titulo, l.autor, l.editorial, l.edicion " +
                    "from biblioteca b join ejemplar e on b._id=e.id_biblioteca " +
                    "join libro l on e.id_libro=l._id " +
                    "where e.id_libro in (select _id from libro where titulo LIKE ?) " +
                    "and e.estado=0";
            ps = (PreparedStatement) conexion.prepareStatement(consulta);
            //Añadimos los parametros y ejecutamos.
            ps.setString(1, titulo);
            rs = ps.executeQuery();
            //Obtenemos el unico resultado.
            if (rs.next()) {
                listadoBibliotecasConEjemplaresDisponibles = new ArrayList<>();
                do {
                    Disponible disponible = new Disponible(rs.getInt(1), rs.getString(2), rs.getString(3),
                            rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),
                            rs.getInt(8));
                    listadoBibliotecasConEjemplaresDisponibles.add(disponible);
                } while (rs.next());

                return listadoBibliotecasConEjemplaresDisponibles;

            } else {
                throw new ConexionBDRemotaCloudLibException("No se han obtenido resultados");
            }
        } catch (SQLException sqle) {
            throw new ConexionBDRemotaCloudLibException(sqle.getMessage(), sqle);
        }
    }

    /**
     * Método que obtiene un listado de ejemplares disponibles relacionados con el título solicitado
     * @author Rafael Bravo Contreras
     *
     * @param titulo Título solicitado
     * @return Listado de objetos Disponible
     * @throws ConexionBDRemotaCloudLibException
     */
    public ArrayList<Disponible> verDisponiblesRelacionadosPorTitulo(String titulo) throws ConexionBDRemotaCloudLibException {
        ResultSet rs;
        PreparedStatement ps;
        ArrayList<Disponible> listadoBibliotecasConEjemplaresDisponibles;

        try {
            //Preparamos la consulta
            String consulta = "select distinct b._id, b.nombre, b.direccion, " +
                    "b.descripcion, l.titulo, l.autor, l.editorial, l.edicion " +
                    "from biblioteca b join ejemplar e on b._id=e.id_biblioteca " +
                    "join libro l on e.id_libro=l._id " +
                    "where e.id_libro in (select _id from libro where titulo LIKE ?) " +
                    "and e.estado=0";
            ps = (PreparedStatement) conexion.prepareStatement(consulta);
            //Añadimos los parametros y ejecutamos.
            String tituloParecido = "%" + titulo + "%";
            ps.setString(1, tituloParecido);
            rs = ps.executeQuery();
            //Obtenemos el unico resultado.
            if (rs.next()) {
                listadoBibliotecasConEjemplaresDisponibles = new ArrayList<>();
                do {
                    Disponible disponible = new Disponible(rs.getInt(1), rs.getString(2), rs.getString(3),
                            rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),
                            rs.getInt(8));
                    listadoBibliotecasConEjemplaresDisponibles.add(disponible);
                } while (rs.next());

                return listadoBibliotecasConEjemplaresDisponibles;

            } else {
                throw new ConexionBDRemotaCloudLibException("No se han obtenido resultados");
            }
        } catch (SQLException sqle) {
            throw new ConexionBDRemotaCloudLibException(sqle.getMessage(), sqle);
        }
    }

    /**
     * Método que obtiene un listado de ejemplares disponibles del autor solicitado
     * @author Rafael Bravo Contreras
     *
     * @param autor Autor solicitado
     * @return Listado de objetos Disponible
     * @throws ConexionBDRemotaCloudLibException
     */
    public ArrayList<Disponible> verDisponiblesPorAutor(String autor) throws ConexionBDRemotaCloudLibException {
        ResultSet rs;
        PreparedStatement ps;
        ArrayList<Disponible> listadoBibliotecasConEjemplaresDisponibles;

        try {
            //Preparamos la consulta
            String consulta = "select distinct b._id, b.nombre, b.direccion, " +
                    "b.descripcion, l.titulo, l.autor, l.editorial, l.edicion " +
                    "from biblioteca b join ejemplar e on b._id=e.id_biblioteca " +
                    "join libro l on e.id_libro=l._id " +
                    "where e.id_libro in (select _id from libro where autor LIKE ?) " +
                    "and e.estado=0";
            ps = (PreparedStatement) conexion.prepareStatement(consulta);
            //Añadimos los parametros y ejecutamos.
            ps.setString(1, autor);
            rs = ps.executeQuery();
            //Obtenemos el unico resultado.
            if (rs.next()) {
                listadoBibliotecasConEjemplaresDisponibles = new ArrayList<>();
                do {
                    Disponible disponible = new Disponible(rs.getInt(1), rs.getString(2), rs.getString(3),
                            rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),
                            rs.getInt(8));
                    listadoBibliotecasConEjemplaresDisponibles.add(disponible);
                } while (rs.next());

                return listadoBibliotecasConEjemplaresDisponibles;

            } else {
                throw new ConexionBDRemotaCloudLibException("No se han obtenido resultados");
            }
        } catch (SQLException sqle) {
            throw new ConexionBDRemotaCloudLibException(sqle.getMessage(), sqle);
        }
    }

    /**
     * Método que obtiene un listado de ejemplares disponibles relacionados con el autor solicitado
     * @author Rafael Bravo Contreras
     *
     * @param autor Autor solicitado
     * @return Listado de objetos Disponible
     * @throws ConexionBDRemotaCloudLibException
     */
    public ArrayList<Disponible> verDisponiblesRelacionadosPorAutor(String autor) throws ConexionBDRemotaCloudLibException {
        ResultSet rs;
        PreparedStatement ps;
        ArrayList<Disponible> listadoBibliotecasConEjemplaresDisponibles;

        try {
            //Preparamos la consulta
            String consulta = "select distinct b._id, b.nombre, b.direccion, " +
                    "b.descripcion, l.titulo, l.autor, l.editorial, l.edicion " +
                    "from biblioteca b join ejemplar e on b._id=e.id_biblioteca " +
                    "join libro l on e.id_libro=l._id " +
                    "where e.id_libro in (select _id from libro where autor LIKE ?) " +
                    "and e.estado=0";
            ps = (PreparedStatement) conexion.prepareStatement(consulta);
            //Añadimos los parametros y ejecutamos.
            String autorParecido = "%" + autor + "%";
            ps.setString(1, autorParecido);
            rs = ps.executeQuery();
            //Obtenemos el unico resultado.
            if (rs.next()) {
                listadoBibliotecasConEjemplaresDisponibles = new ArrayList<>();
                do {
                    Disponible disponible = new Disponible(rs.getInt(1), rs.getString(2), rs.getString(3),
                            rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),
                            rs.getInt(8));
                    listadoBibliotecasConEjemplaresDisponibles.add(disponible);
                } while (rs.next());

                return listadoBibliotecasConEjemplaresDisponibles;

            } else {
                throw new ConexionBDRemotaCloudLibException("No se han obtenido resultados");
            }
        } catch (SQLException sqle) {
            throw new ConexionBDRemotaCloudLibException(sqle.getMessage(), sqle);
        }
    }

    /**
     * Método que obtiene un listado de ejemplares disponibles de la editorial solicitada
     * @author Rafael Bravo Contreras
     *
     * @param editorial Editorial solicitada
     * @return Listado de objetos Disponible
     * @throws ConexionBDRemotaCloudLibException
     */
    public ArrayList<Disponible> verDisponiblesPorEditorial(String editorial) throws ConexionBDRemotaCloudLibException {
        ResultSet rs;
        PreparedStatement ps;
        ArrayList<Disponible> listadoBibliotecasConEjemplaresDisponibles;

        try {
            //Preparamos la consulta
            String consulta = "select distinct b._id, b.nombre, b.direccion, " +
                    "b.descripcion, l.titulo, l.autor, l.editorial, l.edicion " +
                    "from biblioteca b join ejemplar e on b._id=e.id_biblioteca " +
                    "join libro l on e.id_libro=l._id " +
                    "where e.id_libro in (select _id from libro where editorial LIKE ?) " +
                    "and e.estado=0";
            ps = (PreparedStatement) conexion.prepareStatement(consulta);
            //Añadimos los parametros y ejecutamos.
            ps.setString(1, editorial);
            rs = ps.executeQuery();
            //Obtenemos el unico resultado.
            if (rs.next()) {
                listadoBibliotecasConEjemplaresDisponibles = new ArrayList<>();
                do {
                    Disponible disponible = new Disponible(rs.getInt(1), rs.getString(2), rs.getString(3),
                            rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),
                            rs.getInt(8));
                    listadoBibliotecasConEjemplaresDisponibles.add(disponible);
                } while (rs.next());

                return listadoBibliotecasConEjemplaresDisponibles;

            } else {
                throw new ConexionBDRemotaCloudLibException("No se han obtenido resultados");
            }
        } catch (SQLException sqle) {
            throw new ConexionBDRemotaCloudLibException(sqle.getMessage(), sqle);
        }
    }

    /**
     * Método que obtiene un listado de ejemplares disponibles de la editorial solicitada
     * @author Rafael Bravo Contreras
     *
     * @param editorial Editorial solicitada
     * @return Listado de objetos Disponible
     * @throws ConexionBDRemotaCloudLibException
     */
    public ArrayList<Disponible> verDisponiblesRelacionadosPorEditorial(String editorial) throws ConexionBDRemotaCloudLibException {
        ResultSet rs;
        PreparedStatement ps;
        ArrayList<Disponible> listadoBibliotecasConEjemplaresDisponibles;

        try {
            //Preparamos la consulta
            String consulta = "select distinct b._id, b.nombre, b.direccion, " +
                    "b.descripcion, l.titulo, l.autor, l.editorial, l.edicion " +
                    "from biblioteca b join ejemplar e on b._id=e.id_biblioteca " +
                    "join libro l on e.id_libro=l._id " +
                    "where e.id_libro in (select _id from libro where editorial LIKE ?) " +
                    "and e.estado=0";
            ps = (PreparedStatement) conexion.prepareStatement(consulta);
            //Añadimos los parametros y ejecutamos.
            String editorialParecida = "%" + editorial + "%";
            ps.setString(1, editorialParecida);
            rs = ps.executeQuery();
            //Obtenemos el unico resultado.
            if (rs.next()) {
                listadoBibliotecasConEjemplaresDisponibles = new ArrayList<>();
                do {
                    Disponible disponible = new Disponible(rs.getInt(1), rs.getString(2), rs.getString(3),
                            rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),
                            rs.getInt(8));
                    listadoBibliotecasConEjemplaresDisponibles.add(disponible);
                } while (rs.next());

                return listadoBibliotecasConEjemplaresDisponibles;

            } else {
                throw new ConexionBDRemotaCloudLibException("No se han obtenido resultados");
            }
        } catch (SQLException sqle) {
            throw new ConexionBDRemotaCloudLibException(sqle.getMessage(), sqle);
        }
    }

    /**
     * Método que obtiene un listado de ejemplares disponibles del isbn solicitado
     * @author Rafael Bravo Contreras
     *
     * @param isbn Isbn solicitado
     * @return Listado de objetos Disponible
     * @throws ConexionBDRemotaCloudLibException
     */
    public ArrayList<Disponible> verDisponiblesPorIsbn(String isbn) throws ConexionBDRemotaCloudLibException {
        ResultSet rs;
        PreparedStatement ps;
        ArrayList<Disponible> listadoBibliotecasConEjemplaresDisponibles;

        try {
            //Preparamos la consulta
            String consulta = "select distinct b._id, b.nombre, b.direccion, " +
                    "b.descripcion, l.titulo, l.autor, l.editorial, l.edicion " +
                    "from biblioteca b join ejemplar e on b._id=e.id_biblioteca " +
                    "join libro l on e.id_libro=l._id " +
                    "where e.id_libro=(select _id from libro where isbn LIKE ?) " +
                    "and e.estado=0";
            ps = (PreparedStatement) conexion.prepareStatement(consulta);
            //Añadimos los parametros y ejecutamos.
            ps.setString(1, isbn);
            rs = ps.executeQuery();
            //Obtenemos el unico resultado.
            if (rs.next()) {
                listadoBibliotecasConEjemplaresDisponibles = new ArrayList<>();
                do {
                    Disponible disponible = new Disponible(rs.getInt(1), rs.getString(2), rs.getString(3),
                            rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),
                            rs.getInt(8));
                    listadoBibliotecasConEjemplaresDisponibles.add(disponible);
                } while (rs.next());

                return listadoBibliotecasConEjemplaresDisponibles;

            } else {
                throw new ConexionBDRemotaCloudLibException("No se han obtenido resultados");
            }
        } catch (SQLException sqle) {
            throw new ConexionBDRemotaCloudLibException(sqle.getMessage(), sqle);
        }
    }
	
	/**
     * Método que obtiene un listado de ejemplares disponibles
     * @author Rafael Bravo Contreras
     *
     * @param idBiblioteca Identificador de la biblioteca
	 * @param idLibro Identificador del libro
     * @return Listado de objetos Ejemplar
     * @throws ConexionBDRemotaCloudLibException
     */
    public ArrayList<Ejemplar> getEjemplaresDisponibles(int idBiblioteca, int idLibro) throws ConexionBDRemotaCloudLibException {
        ResultSet rs;
        PreparedStatement ps;
        ArrayList<Ejemplar> listadoEjemplaresDisponibles;

        try {
            //Preparamos la consulta
            String consulta = "select * from ejemplar where id_biblioteca=? and id_libro=? and estado=0";
            ps = (PreparedStatement) conexion.prepareStatement(consulta);
            //Añadimos los parametros y ejecutamos.
            ps.setInt(1, idBiblioteca);
            ps.setInt(2, idLibro);
            rs = ps.executeQuery();
            //Obtenemos el unico resultado.
            if (rs.next()) {
                listadoEjemplaresDisponibles = new ArrayList<>();
                do {
                    Ejemplar ejemplar = new Ejemplar(rs.getInt(1), rs.getInt(2), rs.getInt(3),
                            rs.getInt(4), rs.getInt(5));
                    listadoEjemplaresDisponibles.add(ejemplar);
                } while (rs.next());

                return listadoEjemplaresDisponibles;

            } else {
                throw new ConexionBDRemotaCloudLibException("No se han obtenido resultados");
            }
        } catch (SQLException sqle) {
            throw new ConexionBDRemotaCloudLibException(sqle.getMessage(), sqle);
        }
    }

    /**
     * Método que obtiene un objeto Ejemplar
     *
     * @author Rafael Bravo Contreras
     *
     * @param idEjemplar identificador de ejemplar
     * @return objeto Ejemplar
     * @throws ConexionBDRemotaCloudLibException
     */
    public Ejemplar getEjemplar(int idEjemplar) throws ConexionBDRemotaCloudLibException {
        ResultSet rs;
        PreparedStatement ps;
        Ejemplar ejemplar;

        try {
            //Preparamos la consulta
            String consulta = "SELECT * FROM ejemplar WHERE _id=?";
            ps = (PreparedStatement) conexion.prepareStatement(consulta);
            //Añadimos los parametros y ejecutamos.
            ps.setInt(1, idEjemplar);
            rs = ps.executeQuery();
            //Obtenemos el unico resultado.
            if (rs.next()) {
                ejemplar = new Ejemplar(rs.getInt(1), rs.getInt(2), rs.getInt(3),
                        rs.getInt(4), rs.getInt(5));

                return ejemplar;

            } else {
                throw new ConexionBDRemotaCloudLibException("No se han obtenido resultados");
            }

        } catch (SQLException sqle) {
            throw new ConexionBDRemotaCloudLibException(sqle.getMessage(), sqle);
        }
    }

    /**
     * Método que obtiene un listado de ejemplares
     * @author Rafael Bravo Contreras
     *
     * @param idLibro Identificador del libro al que pertenecen
     * @return listado de objetos Ejemplar
     * @throws ConexionBDRemotaCloudLibException
     */
    public ArrayList<Ejemplar> getEjemplares(int idLibro) throws ConexionBDRemotaCloudLibException {
        ResultSet rs;
        PreparedStatement ps;
        ArrayList<Ejemplar> listadoEjemplar;

        try {
            //Preparamos la consulta
            String consulta = "SELECT * FROM ejemplar WHERE id_libro=?";
            ps = (PreparedStatement) conexion.prepareStatement(consulta);
            //Añadimos los parametros y ejecutamos.
            ps.setInt(1, idLibro);

            rs = ps.executeQuery();
            //Obtenemos el unico resultado.
            if (rs.next()) {
                listadoEjemplar = new ArrayList<>();
                do {
                    Ejemplar ejemplar = new Ejemplar(rs.getInt(1), rs.getInt(2), rs.getInt(3),
                            rs.getInt(4), rs.getInt(5));
                    listadoEjemplar.add(ejemplar);
                } while (rs.next());

                return listadoEjemplar;

            } else {
                throw new ConexionBDRemotaCloudLibException("No se han obtenido resultados");
            }

        } catch (SQLException sqle) {
            throw new ConexionBDRemotaCloudLibException(sqle.getMessage(), sqle);
        }
    }

    /**
     * Método que obtiene un libro
     *
     * @author Rafael Bravo Contreras
     *
     * @param idLibro identificador del libro
     * @return objeto Libro
     * @throws ConexionBDRemotaCloudLibException
     */
    public Libro getLibro(int idLibro) throws ConexionBDRemotaCloudLibException {
        ResultSet rs;
        PreparedStatement ps;
        Libro libro;

        try {
            //Preparamos la consulta
            String consulta = "SELECT * FROM libro WHERE _id=?";
            ps = (PreparedStatement) conexion.prepareStatement(consulta);
            //Añadimos los parametros y ejecutamos.
            ps.setInt(1, idLibro);
            rs = ps.executeQuery();
            //Obtenemos el unico resultado.
            if (rs.next()) {
                libro = new Libro(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getInt(7), rs.getString(8));

                return libro;

            } else {
                throw new ConexionBDRemotaCloudLibException("No se han obtenido resultados");
            }

        } catch (SQLException sqle) {
            throw new ConexionBDRemotaCloudLibException(sqle.getMessage(), sqle);
        }
    }
}
