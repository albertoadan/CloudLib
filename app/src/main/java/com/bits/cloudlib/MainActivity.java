package com.bits.cloudlib;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bits.cloudlib.controlador.ConexionBDRemotaCloudLibException;
import com.bits.cloudlib.controlador.ConexionBDRemotaMovilCloudLib;
import com.bits.cloudlib.controlador.DBInterface;
import com.bits.cloudlib.controlador.LogueadorInterna;
import com.bits.cloudlib.controlador.LogueadorRemota;
import com.bits.cloudlib.controlador.SyncService;
import com.bits.cloudlib.objetos.Usuario;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Clase para generar la activity principal de la aplicación móvil. En esta activity se gestiona el login de la
 * aplicación
 *
 * @author  Alberto Adan
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button boton;
    private EditText ed1, ed2;
    private TextView t;
    private int NAV_ACTIVITY = 0;
    private int ALTA_ACTIVITY = 2;
    protected static ConnectivityManager connMgr;
    private LogueadorRemota logRem;
    private LogueadorInterna logInt;
    private int logResult = -1;
    private Usuario user = new Usuario();
    private Intent inav, ialta;
    private ProgressBar pb;
    private DBInterface db = new DBInterface(this);



    /**
     * Metodo que se ejecuta al crear la activity. Dentro del metodo se asocian los elementos de la interfaz de usuario con
     * los elementos del codigo, se añaden los listener correspondientes.
     *
     * @param savedInstanceState parametro que tiene los datos guardados que se recojen sobretodo para cuando se gira la pantalla
     *                           del móvil
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);

        t = (TextView) findViewById(R.id.textView14);
        boton = (Button) findViewById(R.id.button);
        boton.setOnClickListener(this);
        ed1 = (EditText) findViewById(R.id.editText);
        ed2 = (EditText) findViewById(R.id.editText2);
        ed1.setOnClickListener(this);
        ed2.setOnClickListener(this);
        t.setVisibility(View.INVISIBLE);

        pb = (ProgressBar) findViewById(R.id.progressBar);

        connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        //Se inicia el servicio que sincroniza los datos entre la base de datos remota y sqlite
        if (checkConnection()) {
            Intent serviceIntent = new Intent(this, SyncService.class);
            this.startService(serviceIntent);
        }


    }

    /**
     * Metodo que nos crea la opción de menú en la toolbar de la activity. Se añade el menú diseñado en el xml
     * R.menu.menu_main
     *
     * @param menu Recibe como parametro un objeto menu
     * @return devulve si ha sido posible crearlo
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Metodo que se ejecuta al elegir una opción del menú de la toolbar.
     * Obtenemos la id del item elegido para saber que opción del menú ha sido elegida
     *
     * @param item parametro que nos da que item del menú ha sido pulsado
     * @return devuelve si ha sido posible elegir alguna opción
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_visualizar) {
            Intent i = new Intent(MainActivity.this, Navegacion.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Metodo que se ejecuta cuando se clicka en los elementos de la interfaz a los que se le ha
     * añadido un listener. Se comprueba si el widget clickado es el boton que hemos seleccionado
     * y se llama al metodo comprobar usuario enviando los datos de email y password que tenemos en
     * la interfaz de usuario
     *
     * @param v parametro que nos dice que Widget es el que ha sido clickado
     */
    @Override
    public void onClick(View v) {
        // c.cancel(true);
        if (v == boton) {
            comprobarUsuario(ed1.getText().toString(), ed2.getText().toString());
        } else {
            visibilidad(0);
            logResult = logRem.ERROR;
        }
    }

    /**
     * Metodo que se ejecuta cuando termina una activity a la cual se ha llamado desde esta activity
     * Lo primero que hacemos es comprobar si el codigo de la activity se corresponde con el que nos interesa
     * después comprobamos que la activity ha terminado correctamente y  después se añade a dos widget de la
     * interfaz los datos que llegan a traves del intent de la activity a la que se ha llamado.
     *
     * @param requestCode Codigo que identifica que activity ha sido llamada
     * @param resultCode  Codigo que nos dice si el resultado ha sido Ok o NOK
     * @param data        Intent que llega de la activity a la que se ha llamado con los datos que le hayamos añadido.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        visibilidad(0);
        if (requestCode == NAV_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                ed1.setText(data.getStringExtra("usuario"));
                ed2.setText(data.getStringExtra("contrasena"));
                logResult = -1;
            } else {
                logResult = logRem.ERROR;
            }
        } else if (requestCode == ALTA_ACTIVITY)
            if (resultCode == Activity.RESULT_OK) {
                if (data.getStringExtra("usuario") != null) {
                    ed1.setText(data.getStringExtra("usuario"));
                    ed2.setText(data.getStringExtra("contrasena"));
                    Toast.makeText(getApplicationContext(), getString(R.string.toast_registrado), Toast.LENGTH_LONG).show();
                    logResult = -1;
                }
            } else {
                logResult = logRem.ERROR;
            }
    }


    /**
     * Metodo para comprobar los datos del usuario. Primero comprueba con una expresión regular que el email o identificacion
     * de usuario se correponda con numero y letras seguidos de @ y luego el . para indicar el dominio
     * Si hay conexión a internet se llama a la clase ConnectRemota para conectarse directamente a la base de datos remota
     * y sino lo hará en la interna
     *
     * @param usuario    Parametro que nos dice el email introducido como identificación en la interfaz de usuario
     * @param contrasena Parametro que nos dice la contrasena de usuario de la interfaz de usuario
     */
    public void comprobarUsuario(String usuario, String contrasena) {
        inav = new Intent(MainActivity.this, Navegacion.class);
        ialta = new Intent(MainActivity.this, Alta_baja_modificacion.class);

        if (usuario.matches("^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,4}$")) {
            user.setEmail(usuario);
            user.setPassword(contrasena);
            if (checkConnection()) {
                ConnectRemota con = new ConnectRemota();
                con.execute(user);
            } else {
                logInt = new LogueadorInterna(db, user.getEmail(), user.getPassword());
                //    log = new Logueador(db, user.getEmail(), user.getPassword());
                logResult = logInt.getLogin();
                resultado();
            }
        } else {
            Toast.makeText(getApplicationContext(), R.string.mail_incorrecto, Toast.LENGTH_LONG).show();
        }
    }


    /**
     * Metodo que modifica la visibilidad del textview según el valor que le llega como parametro
     *
     * @param x valor para indicar si hay que hacer que el textview sea visible o no
     */
    public void visibilidad(int x) {
        if (x == 0) {
            t.setVisibility(View.INVISIBLE);
            boton.setText(R.string.ingresar);
        } else if (x == 1) {
            t.setVisibility(View.VISIBLE);
            boton.setText(R.string.registrar);
        }
    }

    /**
     * Clase para conectarse a la base de datos remota. Si hay internet y nos podemos conectar a la base de datos remota se hara
     * el logueo con el resultado esperado. Si el usuario está registrado guardaremos el registro de fechaHora de conexión.
     * En el metodo onPostExecute buscamos el usuario en la interna, si ya está registrado en la interna lo actualizaremos y si no
     * añadiremos el usuario a los registros de la base de datos interna. Después llamamos al metodo resultado que según el resultado
     * del logueo nos llevará a una activity u otra.
     */
    private class ConnectRemota extends AsyncTask<Usuario, Void, String> {
        @Override
        protected void onPreExecute() {
            pb.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Usuario... u) {
            Usuario usuario = (Usuario) u[0];
            String response="";
            final ConexionBDRemotaMovilCloudLib cloudlibDB;
            try {
                try {
                 //     cloudlibDB = new ConexionBDRemotaMovilCloudLib("cloudlib", "1234", "cloudlib", "10.0.3.2", "3306");
                    cloudlibDB = new ConexionBDRemotaMovilCloudLib("root", "123456789", "cloudlib", "axelussnas.dlinkddns.com", "3306");
                    logRem = new LogueadorRemota(cloudlibDB, usuario.getEmail(), usuario.getPassword());
                    logResult = logRem.getLogin();
                    if (logResult != 0 && logResult != 5) {
                        user = logRem.getUser();
                        cloudlibDB.putFechaHoraConexionUsuario(usuario);
                        cloudlibDB.closeConexionBDRemotaCloudLib();
                    }
                    response="OK";
                } catch (ConexionBDRemotaCloudLibException ex) {
                    Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (Exception e) {
                logResult = logRem.ERROR;
                Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, null, e);
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            pb.setVisibility(View.GONE);
            if (result.equals("OK")) {
                try {
                    if (logResult != 0 && logResult != 5) {
                        db.open();
                        Usuario interna = db.getUsuario(user.getEmail());
                        if (interna != null) {
                            user.setPassword(interna.getPassword());
                            db.updateUsuario(interna.getId(), user);
                        } else {
                            user.setPassword(ed2.getText().toString());
                            db.putUsuario(user);
                        }
                        db.close();
                    }
                    resultado();
                } catch (SQLiteException ex) {
                    Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

    }

    /**
     * Metodo que según el resultado del logueo nos hará una cosa u otra. Si el logueo ha sido 1 , el usuario esta registrado
     * y llama a la activity navegación, si no está registrado llamará a la activity Alta_baja_modificación para registrarlo
     */
    public void resultado() {
        pb.setVisibility(View.GONE);
        Bundle b = new Bundle();
        switch (logResult) {
            case 1:
                b.putSerializable("usuario", user);
                inav.putExtras(b);
                startActivityForResult(inav, NAV_ACTIVITY);
                break;
            case 0:
                if (t.getVisibility() == View.VISIBLE) {
                    ialta.putExtra("usuario", ed1.getText().toString());
                    ialta.putExtra("contrasena", ed2.getText().toString());
                    startActivityForResult(ialta, ALTA_ACTIVITY);
                } else {
                    visibilidad(1);
                }
                break;
            case 4:
                Toast.makeText(getApplicationContext(), getString(R.string.toast_contrasena), Toast.LENGTH_LONG).show();
                break;
            case 2:
                Toast.makeText(getApplicationContext(), getString(R.string.toast_bloqueado), Toast.LENGTH_LONG).show();
                break;
            case 3:
                Toast.makeText(getApplicationContext(), getString(R.string.toast_usuarioBaja), Toast.LENGTH_LONG).show();
                break;
            case 5:
                Toast.makeText(getApplicationContext(), getString(R.string.toast_error), Toast.LENGTH_LONG).show();
                break;
            case 6:
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


    public int getLogResult () {
        return this.logResult;
    }


}
