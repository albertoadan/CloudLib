package com.bits.cloudlib;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.bits.cloudlib.controlador.ConexionBDRemotaCloudLibException;
import com.bits.cloudlib.controlador.ConexionBDRemotaMovilCloudLib;
import com.bits.cloudlib.controlador.Utilidades;
import com.bits.cloudlib.objetos.Disponible;
import com.bits.cloudlib.objetos.Usuario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que se usa como activity para la busqueda de un libro en las bibliotecas o para la gestión de datos del usuario
 * como los de su registro, reservas, prestamos o sanciones desde el menu de la toolbar.
 *
 * @author Alberto Adán
 */
public class Navegacion extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private Button b5;
    private EditText ed13;
    private int ALTA_BAJA_ACTIVITY = 0;
    private int RESERVAS_ACTIVITY = 1;
    private ListView lv;
    private SimpleAdapter adapter;
    private ArrayList<Item> lista = new ArrayList<Item>();
    private List<HashMap<String, String>> listItems;
    ;
    private Usuario user;
    private ProgressBar pg;
    private ArrayList<Disponible> lista_disponible = new ArrayList<Disponible>();
    protected static ConnectivityManager connMgr;
    private Spinner sp;
    public int opcion = -1;
    public static final int TITULO = 0;
    public static final int ISBN = 1;
    public static final int AUTOR = 2;
    public static final int EDITORIAL = 3;


    /**
     * Metodo que se llama al crear la activity. Se asocian los widgets a los elementos de la interfaz de usuario
     * y se aignan los respectivos listeners
     *
     * @param savedInstanceState Datos guardados con anterioridad
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //   Thread.setDefaultUncaughtExceptionHandler(new ConexionBDRemotaMovilCloudLibExceptionHandler());
        setContentView(R.layout.activity_navegacion);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.toolbar_nav);

        b5 = (Button) findViewById(R.id.button5);
        b5.setOnClickListener(this);
        b5.setText(R.string.buton5);

        ed13 = (EditText) findViewById(R.id.editText13);

        lv = (ListView) findViewById(R.id.listView);
        lv.setOnItemClickListener(this);

        // Barra de progreso para que se vea que la apliación se está conectando a la base de datos remota
        pg = (ProgressBar) findViewById(R.id.progressBar2);

        user = (Usuario) getIntent().getSerializableExtra("usuario");

        //Spinner para seleccionar entre las distintas opciones para buscar un libro
        sp = (Spinner) findViewById(R.id.spinner);

        sp.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        sp.setSelection(0);

        connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

    }

    /**
     * Metodo que se ejecuta al hacer click sobre los widget que tengan asociado un listener
     * Primero comprobamos que hay texto en el textview y después que hay conexión a internet, después se instancia
     * la clase ThreadedAsyncTask para conectarse a la base de datos remota y obtener la lista de elementos
     * disponibles. Con cada elemento de la lista se forma un Item que será parte del listview que se mostrará
     * por pantalla
     *
     * @param v Parametro que indica que widget es el que se ha clickado
     */
    @Override
    public void onClick(View v) {
        if (v == b5) {
            lista_disponible.clear();
            cargarListView();
            if (!ed13.getText().toString().equals("")) {
                Utilidades u = new Utilidades(false);
                if (checkConnection()) {
                    switch (opcion) {
                        case ISBN:
                            if (u.validateISBN(ed13.getText().toString())) {
                                Connect c = new Connect();
                                c.execute(ed13.getText().toString());
                                break;
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        getString(R.string.toast_isbn_incorrecto), Toast.LENGTH_LONG).show();
                            }
                            ;
                            break;
                        default:
                            Connect c = new Connect();
                            c.execute(ed13.getText().toString());
                            break;
                    }
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.toast_conexion), Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(Navegacion.this, getString(R.string.toast_navegacion), Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Metodo que se ejecuta al crearse un menu en la toolbar. Dependiendo de si hemos accedido a la activity en modo
     * registrado o en modo visualizar se nos mostrará o no el menu de la toolbar.
     *
     * @param menu Objeto menu
     * @return devuelve true si se ha accedido a la activity con un objeto usuario enviado por el intent
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Intent i = getIntent();
        user = (Usuario) getIntent().getSerializableExtra("usuario");
        if (user != null) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_nav, menu);
            return true;
        } else {
            return false;
        }

    }

    /**
     * Metodo que se ejecuta al seleccionar un elemento del menu de la toolbar. Según el elemento elegido se acabará
     * ejecutando la activity que Sanciones_Reservas que usamos para reservas, prestamos o sanciones o la activity
     * alta_baja_modificación mostrando todos los datos del usuario para poder modificarlos.
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_reservas:
                starSancionesReservas(R.string.toolbar_reservas);
                return true;
            case R.id.action_sanciones:
                starSancionesReservas(R.string.toolbar_sanciones);
                return true;
            case R.id.action_usuario:
                Intent g = new Intent(Navegacion.this, Alta_baja_modificacion.class);
                Intent h = getIntent();

                if (h.getSerializableExtra("usuario") != null) {
                    user = (Usuario) h.getSerializableExtra("usuario");
                    Bundle b = new Bundle();
                    b.putSerializable("usuario", user);
                    g.putExtras(b);
                    startActivityForResult(g, ALTA_BAJA_ACTIVITY);
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.toast_no_registrado), Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.action_prestamo:
                starSancionesReservas(R.string.toolbar_prestamo);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Metodo para ejecutar el intent de Sanciones_Reservas ya que tanto prestamo, como reserva como sanciones se
     * mostrarán en la misma interfaz de esta activity pero modificando datos.
     *
     * @param recurso Parametro que servirá para indicar que recurso es el que se ha usado y así saber si se ejecuta
     *                la activity Sanciones_Reservas para prestamo, reserva o sanción.
     */
    public void starSancionesReservas(int recurso) {
        Intent i = new Intent(Navegacion.this, Sanciones_reservas.class);
        Bundle b = new Bundle();
        b.putSerializable("usuario", user);
        b.putString("toolbar", getResources().getString(recurso));
        i.putExtras(b);
        startActivity(i);
    }

    /**
     * Metodo que se ejecuta al finalizar la activity a la que se llama obteniendo un resultado de ello.
     *
     * @param requestCode Codigo de la activity que se ha ejecutado
     * @param resultCode  Codigo con el resultado de haber ejecutado la activity
     * @param data        Intent con todos los datos provenientes de la activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ALTA_BAJA_ACTIVITY) {
            if (resultCode == RESULT_OK) {
                setResult(RESULT_OK, data);
                finish();
            }
        }
        else if (requestCode == RESERVAS_ACTIVITY) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(),getString(R.string.toast_reserva), Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Metodo que se ejecuta cuando se pulsa prolongadamente un item del listview mostrando una ventana de alert-dialog para
     * ampliar la información que hay en la lista_disponible sobre el item seleccionado.
     *
     * @param parent   AdapterView del listview
     * @param view     Widget
     * @param position posición del listview seleccionada
     * @param id
     */
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater l = (this).getLayoutInflater();
        builder.setTitle(getString(R.string.titulo) + ": " + lista_disponible.get(position).getTituloLibro());
        String msg = getString(R.string.autor) + ": " + lista_disponible.get(position).getAutorLibro() + "\n" +
                getString(R.string.edicion) + ": " + lista_disponible.get(position).getEdicionLibro() + "\n" +
                getString(R.string.editorial) + ": " + lista_disponible.get(position).getEditorialLibro() + "\n" +
                getString(R.string.biblioteca) + ": " + lista_disponible.get(position).getNombreBiblioteca() + "\n" +
                getString(R.string.direccion) + ": " + lista_disponible.get(position).getDireccionBiblioteca() + "\n" +
                getString(R.string.descripcion) + ": " + lista_disponible.get(position).getDescripcionBiblioteca();
        builder.setMessage(msg);
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

        return false;

    }

    /**
     * Metodo que se ejecuta cuando se pulsa  un elemento del listview ejecutando la activity Navegación
     * y enviando los datos del elemento disponible
     *
     * @param parent   AdapterView del listview
     * @param view     Widget
     * @param position posición seleccionada del listview
     * @param id
     * @return
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (user != null) {
            Bundle b = new Bundle();
            b.putSerializable("item", lista.get(position));
            b.putSerializable("disponible", lista_disponible.get(position));
            b.putSerializable("usuario", user);
            Intent i = new Intent(Navegacion.this, Reservas.class);
            i.putExtras(b);
            startActivityForResult(i, RESERVAS_ACTIVITY);
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.toast_modo_visualizar), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Metodo que nos sirve para saber que elemento del spinner ha sido seleccionado. Cada vez que se selecciona
     * un elemento se mira si se ha hecho una busqueda con anterioridad para limpiar la pantalla borrando la lista
     * de disponibles y el cuadro de texto y actualizando el listview con lo cual la pantalla se quedará limpia
     *
     * @param parent
     * @param view
     * @param position Posicion que está seleccionada del spinner
     * @param id       identificador de la posicion
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (lista_disponible != null) {
            lista_disponible.clear();
            ed13.setText("");
        }
        cargarListView();
        switch (position) {
            case TITULO:
                opcion = TITULO;
                break;
            case AUTOR:
                opcion = AUTOR;
                break;
            case EDITORIAL:
                opcion = EDITORIAL;
                break;
            case ISBN:
                opcion = ISBN;
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * Clase con la cual nos conectamos a la base de datos remota y según la opción elegida del spinner se busca en la base de datos
     * los disponibles por un metodo u otro. Después se llama al metodo cargarListView para actualizar los datos del listview. La clase
     * usa la barra de progreso para ir indicando que está conectandose a la base de datos. Una vez acabado esconde la barra de progreso.
     */
    private class Connect extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            pg.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... u) {
            String o = (String) u[0];
            final ConexionBDRemotaMovilCloudLib cloudlibDB;
            try {
             //   cloudlibDB = new ConexionBDRemotaMovilCloudLib("cloudlib", "1234", "cloudlib", "10.0.3.2", "3306");
               cloudlibDB = new ConexionBDRemotaMovilCloudLib("root", "123456789", "cloudlib", "axelussnas.dlinkddns.com", "3306");

                switch (opcion) {
                    case TITULO:
                        lista_disponible = cloudlibDB.verDisponiblesRelacionadosPorTitulo(o);
                        break;
                    case AUTOR:
                        lista_disponible = cloudlibDB.verDisponiblesRelacionadosPorAutor(o);
                        break;
                    case EDITORIAL:
                        lista_disponible = cloudlibDB.verDisponiblesRelacionadosPorEditorial(o);
                        break;
                    case ISBN:
                        lista_disponible = cloudlibDB.verDisponiblesPorIsbn(o);
                        break;
                }
            } catch (ConexionBDRemotaCloudLibException e) {
                Logger.getLogger(Navegacion.class.getName()).log(Level.SEVERE, null, e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            pg.setVisibility(View.GONE);
            cargarListView();
            if (lista.size() < 1) {
                Toast.makeText(getApplicationContext(), getString(R.string.toast_noLibro), Toast.LENGTH_LONG).show();
            }
        }
    }


    /**
     * Metodo utilizado para actualizar los datos del listView. Primero repasamos si tenemos datos en la lista de disponible obtenida
     * en la base de datos. En caso afirmativo vamos añadiendo un nuevo item a la lista que usaremos en el listview
     * Finalmente actualizamos el listView con un notifyDataSetChanged
     */
    public void cargarListView() {
        listItems = new ArrayList<HashMap<String, String>>();
        lista = new ArrayList<Item>();

        if (lista_disponible != null) {
            for (Disponible d : lista_disponible) {

                Item it = new Item(d.getTituloLibro(), getString(R.string.biblioteca) + ": " +
                        d.getNombreBiblioteca() + "\n" + getString(R.string.direccion) + ": " + d.getDireccionBiblioteca());
                lista.add(it);
            }

            String[] hash = {"titulo", "biblioteca"};

            int[] items_lv = {R.id.listBiblio, R.id.listEstado};

            adapter = new SimpleAdapter(getBaseContext(), listItems,
                    R.layout.item_list, hash, items_lv);

            lv.setAdapter(adapter);

            for (Item item : lista) {
                HashMap<String, String> element;
                element = new HashMap<String, String>();
                element.put("titulo", item.getTexto1());
                element.put("biblioteca", item.getTexto2());
                listItems.add(element);
            }

            adapter.notifyDataSetChanged();
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


