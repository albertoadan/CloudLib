package com.bits.cloudlib;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.bits.cloudlib.controlador.ConexionBDRemotaCloudLibException;
import com.bits.cloudlib.controlador.ConexionBDRemotaMovilCloudLib;
import com.bits.cloudlib.controlador.DBInterface;
import com.bits.cloudlib.objetos.Libro;
import com.bits.cloudlib.objetos.Prestamo;
import com.bits.cloudlib.objetos.Reserva;
import com.bits.cloudlib.objetos.SancionUsuario;
import com.bits.cloudlib.objetos.Usuario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Clase que genera la activity Sanciones_Reservas donde se gestionarán, prestamos, reservas y sanciones
 * <p/>
 * @author Alberto Adán
 */
public class Sanciones_reservas extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView lv;
    private List<HashMap<String, String>> listItems;
    private SimpleAdapter adapter;
    private ArrayList<Item> lista;
    private String tb;
    private Usuario user;
    private ArrayList<Reserva> lista_reservas;
    private ArrayList<SancionUsuario> lista_sanciones;
    private ArrayList<Prestamo> lista_prestamo;
    private DBInterface db;
    protected Usuario remota_user;
    protected static ConnectivityManager connMgr;
    private ProgressBar pg;



    /**
     * Metodo que se llama al crear la activity. Se asocian los widgets a los elementos de la interfaz de usuario.
     * Se asignan los respectivos listeners y según con que dato se haya llamado a la activity sabremos si la activity
     * la usaremos para prestamos, reservas o sanciones y cambiamos el texto de la toolbar y cargamos unos datos u otros
     * en el listview de la interfaz.
     *
     * @param savedInstanceState Datos guardados con anterioridad
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sanciones_reservas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent i = getIntent();
        tb = i.getStringExtra("toolbar");
        getSupportActionBar().setTitle(tb);

        lv = (ListView) findViewById(R.id.listView2);
        lv.setOnItemClickListener(this);

        user = (Usuario) getIntent().getSerializableExtra("usuario");

        connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        listItems = new ArrayList<HashMap<String, String>>();
        lista = new ArrayList<Item>();

        pg = (ProgressBar) findViewById(R.id.progressBar3);

        db = new DBInterface(this);

        inicio();
    }

    /**
     * Metodo en el cual comprobamos si en la toolbar tenemos prestamo, reserva o sancion para saber si tenemos que ejecutar una
     * cosa u otra. Después comprobamos si tenemos conexión a internet. Si hay conexión llamará a su respectiva clase asynctask
     * para obtener todos los datos de reservas, sanciones o prestamos. Si no hay conexión los buscará en la base de datos interna.
     * Finalmente se llama al metodo cargarListView que mostrará todos los datos en la interfaz
     */
    public void inicio() {
        if (tb.equals(getString(R.string.toolbar_prestamo))) {
            if (checkConnection()) {
                ConnectPrestamos c = new ConnectPrestamos();
                c.execute(user);
            } else {
                db.open();
                Usuario interna = db.getUsuario(user.getEmail());
                lista_prestamo = db.getPrestamosUsuario(interna.getId());
                db.close();
                for (Prestamo p : lista_prestamo) {
                    Item it = new Item(getString(R.string.toast_offline) + ": ", getString(R.string.fechaPrestamo) + ": "
                            + (p.getFechaPrestamo().toString()) + " - "
                            + (p.getFechaPrevistaDevolucion().toString()));
                    lista.add(it);
                }
                cargarListView();
            }

        } else if (tb.equals(getString(R.string.toolbar_reservas))) {
            if (checkConnection()) {
                ConnectReservas c = new ConnectReservas();
                c.execute(user);
            } else {
                db.open();
                Usuario interna = db.getUsuario(user.getEmail());
                lista_reservas = db.getReservasUsuario(interna.getId());
                db.close();
                for (Reserva r : lista_reservas) {
                    Item it = new Item(getString(R.string.toast_offline), getString(R.string.fechaReserva) + ": "
                            + r.getFechaReserva().toString());
                    lista.add(it);
                }
                cargarListView();
            }

        } else if (tb.equals(getString(R.string.toolbar_sanciones))) {
            if (checkConnection()) {
                ConnectSanciones c = new ConnectSanciones();
                c.execute(user);
            } else {
                db.open();
                Usuario interna = db.getUsuario(user.getEmail());
                lista_sanciones = db.getSancionesUsuario(interna.getId());
                db.close();
                for (SancionUsuario s : lista_sanciones) {
                    Item it = new Item(getString(R.string.SancionUsuario) + ": " + s.getTipoSancion() + "\n",
                            getString(R.string.fechaSancion) + ": " + s.getFechaAplicacion().toString()
                                    + " - " + getString(R.string.fechaVencimiento) + ": " + s.getFechaVencimiento().toString());
                }
                cargarListView();
            }

        }
    }


    /**
     * Metodo usado para cargar los datos en el listview. En el listview tenemos dentro de cada Item del listview 2 partes:
     * Una primera que es el texto principal y una secundario que es otro texto.
     */
    public void cargarListView() {
            String[] hash = {"texto1", "texto2"};

            int[] items_lv = {R.id.listBiblio, R.id.listEstado};

            adapter = new SimpleAdapter(getBaseContext(), listItems,
                    R.layout.item_list, hash, items_lv);

            lv.setAdapter(adapter);
            for (Item item : lista) {
                HashMap<String, String> element;
                element = new HashMap<String, String>();
                element.put("texto1", item.getTexto1());
                element.put("texto2", item.getTexto2());
                listItems.add(element);
            }
            if (lista.size() == 0) {
                Toast.makeText(getApplicationContext(), R.string.toast_listView_vacio, Toast.LENGTH_LONG).show();
            }
            adapter.notifyDataSetChanged();
    }

    /**
     * Metodo que se ejecuta al clickar un elemento del listview. Los datos de ese elemento son enviado al
     * metodo muestra_dialogo que nos mostrará un alert-dialog que explicará con más detalle los datos.
     *
     * @param parent
     * @param view     Widget
     * @param position posicion del listview
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (lista.size() > 0) {
            Item it = lista.get(position);
            muestra_dialogo(lista.get(position).getTexto1(), lista.get(position).getTexto2());
        }
    }

    /**
     * Metodo para mostrar en la pantalla una alert-dialog que usaremos para poder mostrar más datos
     * del elemento seleccionado en el listview.
     *
     * @param titulo Texto principal del alert-dialog
     * @param texto  Texto secundario o mensaje del alert-dialog
     */
    public void muestra_dialogo(String titulo, String texto) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater l = (this).getLayoutInflater();
        builder.setTitle(titulo);
        builder.setMessage(texto);
        builder.setCancelable(false);
        builder.setView(l.inflate(R.layout.dialog, null))
                // Add action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        builder.create();
        builder.show();
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

    /**
     * Clase que usaremos para conectar a la base de datos remota y obtener las reservas el usuario que llega como parametro.
     * En el metodo onPostExecute buscamos esa reserva en la base de datos interna y si no está la guardamos, primero obteniendo
     * el id del usuario en la base de datos interna.
     */
    private class ConnectReservas extends AsyncTask<Usuario, Void, String> {
        @Override
        protected void onPreExecute() {
            pg.setVisibility(View.VISIBLE);
        }
        @Override
        protected String doInBackground(Usuario... u) {

            String response = "";
            Usuario usuario = u[0];
            ConexionBDRemotaMovilCloudLib cloudlibDB = null;
            try {
             //          cloudlibDB = new ConexionBDRemotaMovilCloudLib("cloudlib", "1234", "cloudlib", "10.0.3.2", "3306");
                cloudlibDB = new ConexionBDRemotaMovilCloudLib("root", "123456789", "cloudlib", "axelussnas.dlinkddns.com", "3306");
                remota_user = cloudlibDB.getUsuario(usuario.getEmail());
                lista_reservas = cloudlibDB.getReservasUsuario(remota_user.getId());

                for (Reserva r : lista_reservas) {
                    Libro l;
                    l = cloudlibDB.getLibroPorIdEjemplar(r.getIdEjemplar());
                    Item it = new Item(getString(R.string.textView7) + " : " + l.getTitulo(), getString(R.string.fechaReserva) + ": "
                            + r.getFechaReserva().toString());
                    lista.add(it);
                }
                cloudlibDB.closeConexionBDRemotaCloudLib();
                response = "OK";
            } catch (ConexionBDRemotaCloudLibException e) {
                response = "NOK";
                Logger.getLogger(Sanciones_reservas.class.getName()).log(Level.SEVERE, null, e);
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            pg.setVisibility(View.GONE);
            if (result.equals("OK")) {
                for (Reserva r : lista_reservas) {
                    try {
                        db.open();
                        if (db.getReserva(r.getId()) != null) {
                        } else {
                            Usuario interna = db.getUsuario(user.getEmail());
                            r.setIdUsuario(interna.getId());
                            db.putReserva(r);
                        }
                        db.close();
                    }catch (SQLException e) {
                        Logger.getLogger(Sanciones_reservas.class.getName()).log(Level.SEVERE, null, e);
                    }

                }
                cargarListView();
            }
        }
    }


    /**
     * Clase que usaremos para conectarnos a la base de datos remota y obtener los prestamos del usuario.
     * En el metodo onPostExecute buscamos si el prestamo está en  la base de datos interna y si no está,
     * obtenemos el id de usuario en la intera y guardamos el prestamo en la interna
     *
     */
    private class ConnectPrestamos extends AsyncTask<Usuario, Void, String> {
        @Override
        protected void onPreExecute() {
            pg.setVisibility(View.VISIBLE);
        }
        @Override
        protected String doInBackground(Usuario... u) {
            String response = "";
            Usuario usuario = u[0];
            ConexionBDRemotaMovilCloudLib cloudlibDB = null;
            try {
             //   cloudlibDB = new ConexionBDRemotaMovilCloudLib("cloudlib", "1234", "cloudlib", "10.0.3.2", "3306");
                cloudlibDB = new ConexionBDRemotaMovilCloudLib("root", "123456789", "cloudlib", "axelussnas.dlinkddns.com", "3306");
                remota_user = cloudlibDB.getUsuario(usuario.getEmail());
                lista_prestamo = cloudlibDB.getPrestamosUsuario(remota_user.getId());
                for (Prestamo p : lista_prestamo) {
                    Libro l = cloudlibDB.getLibroPorIdEjemplar(p.getIdEjemplar());
                    Item it = new Item(getString(R.string.textView7) + " : " + l.getTitulo() + " ", getString(R.string.fechaPrestamo) + ": "
                            + (p.getFechaPrestamo().toString()) + " - "
                            + (p.getFechaPrevistaDevolucion().toString()));
                    lista.add(it);
                }
                cloudlibDB.closeConexionBDRemotaCloudLib();
                response = "OK";
            } catch (ConexionBDRemotaCloudLibException e) {
                response = "NOK";
                Logger.getLogger(Sanciones_reservas.class.getName()).log(Level.SEVERE, null, e);
            }
            return response;
        }
        @Override
        protected void onPostExecute(String result) {
            pg.setVisibility(View.GONE);
            if (result.equals("OK")) {
                for (Prestamo p : lista_prestamo) {
                    try {
                        db.open();
                        if (db.getPrestamo(p.getId()) != null) {
                        } else {
                            Usuario interna = db.getUsuario(user.getEmail());
                            p.setIdUsuario(interna.getId());
                            db.putPrestamo(p);
                        }
                        db.close();
                    }catch (SQLException e) {
                        Logger.getLogger(Sanciones_reservas.class.getName()).log(Level.SEVERE, null, e);
                    }

                }
                cargarListView();
            }
        }
    }


    /**
     * Clase que usaremos para conectarnos a la base de datos remota y obtener las sanciones del usuario que llega como
     * parametro a la clase.
     * En el metodo onPostExecute buscamos las sanciones en la base de datos interna y si no están, buscamos el id del
     * usuario en la interna para guardar las sanciones en la interna también
     */
    private class ConnectSanciones extends AsyncTask<Usuario, Void, String> {
        @Override
        protected String doInBackground(Usuario... u) {
            String response = "";
            Usuario usuario = u[0];
            ConexionBDRemotaMovilCloudLib cloudlibDB = null;
            try {
              //         cloudlibDB = new ConexionBDRemotaMovilCloudLib("cloudlib", "1234", "cloudlib", "10.0.3.2", "3306");
                cloudlibDB = new ConexionBDRemotaMovilCloudLib("root", "123456789", "cloudlib", "axelussnas.dlinkddns.com", "3306");
                remota_user = cloudlibDB.getUsuario(usuario.getEmail());
                lista_sanciones = cloudlibDB.getSancionesUsuario(remota_user.getId());
                cloudlibDB.closeConexionBDRemotaCloudLib();
                response = "OK";
            } catch (ConexionBDRemotaCloudLibException e) {
                response = "NOK";
                Logger.getLogger(Sanciones_reservas.class.getName()).log(Level.SEVERE, null, e);
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.equals("OK")) {
                for (SancionUsuario s : lista_sanciones) {
                    Item it = new Item(getString(R.string.SancionUsuario) + ": " + s.getTipoSancion() + "\n",
                            getString(R.string.fechaSancion) + ": " + s.getFechaAplicacion().toString()
                                    + " - " + getString(R.string.fechaVencimiento) + ": " + s.getFechaVencimiento().toString());
                    lista.add(it);
                }
                for (SancionUsuario s : lista_sanciones) {
                    try {
                        db.open();
                        if (db.getSancionesUsuario(s.getId()) != null) {
                        } else {
                            Usuario interna = db.getUsuario(user.getEmail());
                            s.setIdUsuario(interna.getId());
                            db.putSancionUsuario(s);
                        }
                        db.close();
                    }catch (SQLException e) {
                        Logger.getLogger(Sanciones_reservas.class.getName()).log(Level.SEVERE, null, e);
                    }
                }
            }
            cargarListView();
        }

    }
}