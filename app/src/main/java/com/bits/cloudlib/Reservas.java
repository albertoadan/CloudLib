package com.bits.cloudlib;

import android.content.Context;
import android.database.SQLException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bits.cloudlib.controlador.ConexionBDRemotaCloudLibException;
import com.bits.cloudlib.controlador.ConexionBDRemotaMovilCloudLib;
import com.bits.cloudlib.controlador.DBInterface;
import com.bits.cloudlib.controlador.Utilidades;
import com.bits.cloudlib.objetos.Disponible;
import com.bits.cloudlib.objetos.Ejemplar;
import com.bits.cloudlib.objetos.Libro;
import com.bits.cloudlib.objetos.Reserva;
import com.bits.cloudlib.objetos.Seccion;
import com.bits.cloudlib.objetos.Usuario;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que nos sirve para ejecutar la activity Reservas
 *
 * @author Alberto Adán
 */
public class Reservas extends AppCompatActivity implements View.OnClickListener {

    private EditText ed14, ed15, ed16, ed17, ed18, ed19, ed20, ed21;
    private Button buton7, buton8;
    private Usuario user;
    private Disponible disponible;
    private Seccion s;
    private Ejemplar ej;
    private Libro libro;
    private ArrayList<Reserva> lista_reservas = new ArrayList();
    private ArrayList<Ejemplar> lista_ejemplares_disponibles = new ArrayList();
    private Seccion seccion;
    private Connect connect;
    private Reserva r;
    protected static ConnectivityManager connMgr;
    private ProgressBar pg;
    //   Utilidades ut = new Utilidades(false);
    private DBInterface db = new DBInterface(this);


    /**
     * Metodo que se ejecuta al crear la activity. Se obtiene el soporte de la toolbar, se asocian los widgets a los elementos de la
     * interfaz de usuario, se anaden los listeners, se obtiene el objeto Disponible de la activity anterior.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.toolbar_reservas);

        ed14 = (EditText) findViewById(R.id.editText14);
        ed15 = (EditText) findViewById(R.id.editText15);
        ed16 = (EditText) findViewById(R.id.editText16);
        ed17 = (EditText) findViewById(R.id.editText17);
        ed18 = (EditText) findViewById(R.id.editText18);
        ed19 = (EditText) findViewById(R.id.editText19);
        ed20 = (EditText) findViewById(R.id.editText20);
        ed21 = (EditText) findViewById(R.id.editText21);

        buton7 = (Button) findViewById(R.id.button7);
        buton7.setOnClickListener(this);
        buton8 = (Button) findViewById(R.id.button8);
        buton8.setOnClickListener(this);

        pg = (ProgressBar) findViewById(R.id.progressBar4);

        connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        user = (Usuario) getIntent().getSerializableExtra("usuario");

        disponible = (Disponible) getIntent().getSerializableExtra("disponible");

        getSupportActionBar().setTitle(R.string.toolbar_reservas);

        Item it = null;

        obtieneDatos();

    }

    /**
     * Metodo para cargar los datos de ejemplares disponibles, del libro y de las secciones conectandose a la base de datos
     * remota.
     */
    public void obtieneDatos() {
        if (checkConnection()) {
            connect = new Connect();
            connect.execute(disponible);
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.toast_conexion), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Metodo que se ejecuta al clickar uno de los elementos de la interfaz que tengan asociado un listener.
     * Si se pulsa el boton de cancelar se vuelve hacia atras a la activity anterior, si se pulsa el boton de
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (v == buton8) {
            setResult(RESULT_CANCELED);
            finish();
        } else if (v == buton7) {
            boolean cond = false;
            ej = lista_ejemplares_disponibles.get(0);
            for (Reserva r : lista_reservas) {
                if (r.getEstado() == Reserva.ESTADO_ACTIVA) {
                    for (Ejemplar e : lista_ejemplares_disponibles) {
                        if (e.getId() == r.getIdEjemplar()) {
                            if (e.getIdLibro() == libro.getId()) {
                                cond = true;
                                Toast.makeText(getApplicationContext(), getString(R.string.toast_reservaActiva), Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }
            }

            if (cond == false) {
                if (lista_reservas.size() <= 4) {
                    Calendar c = Calendar.getInstance();
                    Date fecha = new Date(System.currentTimeMillis());
                    c.setTime(fecha);
                    c.add(Calendar.DATE, 1);
                    Reserva r = new Reserva();
                    r.setIdUsuario(user.getId());
                    r.setIdEjemplar(ej.getId());
                    r.setEstado(Reserva.ESTADO_ACTIVA);
                    c.add(Calendar.DATE, 7);
                    r.setFechaCaducidad(c.getTime());
                    if (checkConnection()) {
                        ConnectReservar reservar = new ConnectReservar();
                        reservar.execute(r);
                    }
                    connect.cancel(true);
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.toast_maximo_reservas), Toast.LENGTH_LONG).show();
                }
            }
        }
    }


    /**
     * Clase para conectarse a la base de datos remota y obtener a traves del objeto disponible que proviene de la activity
     * navegación, la lista de ejemplares disponibles, y de paso otros datos que usaremos como el libro, sección.
     * En el metodo onPostExecute cargamos todos los datos del ejemplar disponible en la interfaz
     */
    private class Connect extends AsyncTask<Disponible, Void, String> {
        @Override
        protected String doInBackground(Disponible... u) {
            Disponible d = (Disponible) u[0];
            String response = "";
            final ConexionBDRemotaMovilCloudLib cloudlibDB;
            try {
                //    Utilidades util = new Utilidades("root", "123456789", "cloudlib", "axelussnas.dlinkddns.com", "3306");
             //   cloudlibDB = new ConexionBDRemotaMovilCloudLib("cloudlib", "1234", "cloudlib", "10.0.3.2", "3306");
                cloudlibDB = new ConexionBDRemotaMovilCloudLib("root", "123456789", "cloudlib", "axelussnas.dlinkddns.com", "3306");

                libro = cloudlibDB.getLibroByTitulo(disponible.getTituloLibro());
                seccion = cloudlibDB.getSeccionPorIdLibro(libro.getId());

                try {
                    lista_reservas = cloudlibDB.getReservasUsuario(user.getId());
                }catch (ConexionBDRemotaCloudLibException e1) {
                    Logger.getLogger(Reservas.class.getName()).log(Level.SEVERE, null, e1);
                }
                try {
                    lista_ejemplares_disponibles = cloudlibDB.getEjemplares(libro.getId());
                }catch (ConexionBDRemotaCloudLibException e1) {
                    Logger.getLogger(Reservas.class.getName()).log(Level.SEVERE, null, e1);
                }

                cloudlibDB.closeConexionBDRemotaCloudLib();

                response = "OK";
            } catch (ConexionBDRemotaCloudLibException e1) {
                Logger.getLogger(Reservas.class.getName()).log(Level.SEVERE, null, e1);
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.equals("OK")) {
                ed21.setText(seccion.getNombre());
                ed14.setText(libro.getIsbn());
                ed15.setText(libro.getTitulo());
                ed16.setText(libro.getAutor());
                ed17.setText(libro.getGenero());
                ed18.setText(libro.getEditorial());
                ed19.setText(String.valueOf(libro.getEdicion()));
                ed20.setText(disponible.getNombreBiblioteca());
            }
        }
    }

    /**
     * Clase que usaremos para conectar a la base de datos remota y añadir la reserva al registro del usuario. Primero se comprueba
     * que el usuario no tenga ya una reserva activa de este libro.
     * En el metodo onPostExecute añadimos la reserva a la base de datos interna. Le añadimos la fecha y buscamos el id del usuario
     * en la interna ya que podria ser distinto que en la remota.
     */
    private class ConnectReservar extends AsyncTask<Reserva, Void, String> {
        @Override
        protected void onPreExecute() {
            pg.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Reserva... u) {
            r = (Reserva) u[0];
            String response = "";
            final ConexionBDRemotaMovilCloudLib cloudlibDB;
            try {
                Utilidades ut = new Utilidades("root", "123456789", "cloudlib", "axelussnas.dlinkddns.com", "3306");
              //  cloudlibDB = new ConexionBDRemotaMovilCloudLib("cloudlib", "1234", "cloudlib", "10.0.3.2", "3306");
                cloudlibDB = new ConexionBDRemotaMovilCloudLib("root", "123456789", "cloudlib", "axelussnas.dlinkddns.com", "3306");
                Usuario remota = cloudlibDB.getUsuario(user.getEmail());
                if (ut.checkReservasLibro(remota.getId(), libro.getId())) {
                    Toast.makeText(getApplicationContext(), getString(R.string.toast_reservaActiva), Toast.LENGTH_LONG).show();

                } else {
                    cloudlibDB.putReserva(r);
                    response = "OK";
                }
            } catch (ConexionBDRemotaCloudLibException e1) {
                Logger.getLogger(Navegacion.class.getName()).log(Level.SEVERE, null, e1);
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            pg.setVisibility(View.GONE);
            if (result.equals("OK")) {
                try {
                    db.open();
                    Date d = new Date(System.currentTimeMillis());
                    r.setFechaReserva(d);
                    Usuario interna = db.getUsuario(user.getEmail());
                    r.setIdUsuario(interna.getId());
                    db.putReserva(r);
                    db.close();
                    setResult(RESULT_OK);
                    finish();
                } catch (SQLException e) {
                    Logger.getLogger(Navegacion.class.getName()).log(Level.SEVERE, null, e);
                }

            }

        }
    }

    /**
     * Metodo para comprobar si hay conexión a internet
     *
     * @return devuelte true si hay conexión móvil o wifi
     */
    public Boolean checkConnection() {

        // Obtenim l'estat de la xarxa mòbil
        NetworkInfo networkInfo = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean connected3G = networkInfo.isConnected();
        // Obtenim l'estat de la xarxa Wi-Fi
        networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        boolean connectedWifi = networkInfo.isConnected();
        // O bé hem de tenir 3G o bé wifi
        return connected3G || connectedWifi;
    }


}
