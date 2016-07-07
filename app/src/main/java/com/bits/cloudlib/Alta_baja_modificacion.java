package com.bits.cloudlib;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bits.cloudlib.controlador.ConexionBDRemotaCloudLibException;
import com.bits.cloudlib.controlador.ConexionBDRemotaMovilCloudLib;
import com.bits.cloudlib.controlador.ConexionBDRemotaMovilCloudLibExceptionHandler;
import com.bits.cloudlib.controlador.DBInterface;
import com.bits.cloudlib.controlador.Utilidades;
import com.bits.cloudlib.objetos.Usuario;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase en la cual se implementa la activity que nos servirá para gestionar las altas, bajas y modificación de
 * usuarios
 *
 * @author Alberto Adán
 */
public class Alta_baja_modificacion extends AppCompatActivity implements View.OnClickListener {


    private EditText ed3, ed4, ed5, ed6, ed7, ed8, ed9, ed10, ed22, ed23;
    private Button b2, b3;
    private Date fechaAlta, fechaBaja;
    private Usuario user;
    private DBInterface db = new DBInterface(this);
    protected static ConnectivityManager connMgr;
    protected Usuario remota_user;
    protected Usuario interna = null;
    boolean cond;
    //private String edt3;

    /**
     * Metodo que se ejecuta al crear la activity.
     * Se asocian los widgets de la interfaz de usuario a los elementos de codigo y se le añaden los respectivos listeners.
     * A traves del intent se recibe el objeto usuario si la activity ha sido llamada por un usuario registrado, sino sera
     * null.
     * Se instancia la base de datos y dependiendo de si el usuario está registrado o no se rellenan los widgets con los datos
     * del objeto usuario
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ConexionBDRemotaMovilCloudLibExceptionHandler());
        setContentView(R.layout.activity_alta_baja_modificacion);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.toolbar_registro);

        ed3 = (EditText) findViewById(R.id.editText3);
        ed4 = (EditText) findViewById(R.id.editText4);
        ed5 = (EditText) findViewById(R.id.editText5);
        ed6 = (EditText) findViewById(R.id.editText6);
        ed7 = (EditText) findViewById(R.id.editText7);
        ed8 = (EditText) findViewById(R.id.editText8);
        ed9 = (EditText) findViewById(R.id.editText9);
        ed10 = (EditText) findViewById(R.id.editText10);
        ed22 = (EditText) findViewById(R.id.editText22);
        ed23 = (EditText) findViewById(R.id.editText23);


        b2 = (Button) findViewById(R.id.button2);
        b3 = (Button) findViewById(R.id.button3);

        b2.setOnClickListener(this);
        b3.setOnClickListener(this);

        connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        try {
            user = (Usuario) getIntent().getSerializableExtra("usuario");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //  db = new DBInterface(this);

        Intent i = getIntent();
        if (i.getStringExtra("usuario") != null) {
            ed10.setText(i.getStringExtra("usuario").toString());
        }
        if (user != null) {
            b2.setText(getResources().getText(R.string.boton_modificar));
            ed3.setText(user.getDni());
            ed4.setText(user.getNombre());
            ed5.setText(user.getApellidos());
            ed6.setText(user.getDireccion());
            ed7.setText(user.getCodigoPostal());
            ed8.setText(user.getPoblacion());
            ed9.setText(user.getTelefono());
            ed10.setText(user.getEmail());
            ed22.setText("");
            ed23.setText("");
        }
    }

    /**
     * Metodo que se ejecuta al clickar un widget de la interfaz de usuario
     * Si se pulsa el boton b2 hay la opción de que sea para registrar un nuevo usuario y se ejecuta la clase Connect
     * para  modificar un usuario  o el metodo registrar para registrar un nuevo usuario. Si se realiza con exito el registro
     * o modificación se finaliza la activity con resultado Ok.
     * Si se pulsa el boton b3 se ejecuta el metodo baja() que nos gestiona la baja del usuario
     *
     * @param v Widget clickado de la interfaz
     */
    @Override
    public void onClick(View v) {
        boolean fin = false;
        if (v == b2) {
            if (b2.getText().toString().equals(getString(R.string.boton_modificar))) {
                if (checkConnection()) {
                    if (!ed22.getText().toString().equals("") & !ed23.getText().toString().equals("")) {
                        if (ed22.getText().toString().equals(ed23.getText().toString())) {
                            user.setPassword(ed22.getText().toString());
                            Connect c = new Connect();
                            c.execute(user);
                        } else {
                            toast(getString(R.string.toast_contrasena));
                        }
                    } else {
                        toast(getString(R.string.toast_faltan_Datos));
                    }
                } else {
                    toast(getString(R.string.toast_conexion));
                }
            } else {
                try {
                    fin = registrar();
                } catch (ConexionBDRemotaCloudLibException e) {
                    Logger.getLogger(Alta_baja_modificacion.class.getName()).log(Level.SEVERE, null, e);
                }
            }
            if (fin == true) {
                Intent i = new Intent();
                i.putExtra("usuario", ed10.getText().toString());
                i.putExtra("contrasena", ed22.getText().toString());
                setResult(Activity.RESULT_OK, i);
                finish();
            }
        } else if (v == b3) {
            fin = baja();
            if (fin) {
                Toast.makeText(getApplicationContext(), getString(R.string.toast_baja), Toast.LENGTH_LONG).show();
            }
        }
    }


    /**
     * Metodo con el cual se registra un usuario en la base de datos remota. Primero se comprueba que haya conexion a internet
     * con el metodo checkconnection, después se comprueba que todos los widgets tengan su valor correspondiente y finalmente
     * se instancia un nuevo usuario y se ejecuta la clase Connect para registrar el usuario con AsyncTask
     *
     * @return true si el usuario se ha registrado con exito.
     * @throws ConexionBDRemotaCloudLibException lanza una excepcion a la clase diseñada para las excepciones de la base de datos
     */
    public boolean registrar() throws ConexionBDRemotaCloudLibException {
        cond = false;
        Utilidades u = new Utilidades(false);
        if (checkConnection()) {
            if (!ed4.getText().toString().equals("") & !ed5.getText().toString().equals("") & !ed6.getText().toString().equals("") &
                    !ed8.getText().toString().equals("") & !ed9.getText().toString().equals("") & !ed22.getText().toString().equals("")) {
                if (u.validateDNI((ed3.getText().toString()))) {
                    if (u.validatePostal(ed7.getText().toString())) {
                        if (u.validateEmail(ed10.getText().toString())) {
                            if (ed22.getText().toString().equals(ed23.getText().toString())) {
                                try {
                                    Usuario user = new Usuario();
                                    user.setNombre(ed4.getText().toString());
                                    user.setDni(ed3.getText().toString());
                                    user.setApellidos(ed5.getText().toString());
                                    user.setDireccion(ed6.getText().toString());
                                    user.setCodigoPostal(ed7.getText().toString());
                                    user.setPoblacion(ed8.getText().toString());
                                    user.setPassword(ed22.getText().toString());
                                    user.setTelefono(ed8.getText().toString());
                                    user.setEmail(ed10.getText().toString());
                                    user.setRol(1);
                                    user.setIdBiblioteca(1);
                                    ConnectAlta con = new ConnectAlta();
                                    con.execute(user);
                                } catch (Exception e) {
                                    Logger.getLogger(Alta_baja_modificacion.class.getName()).log(Level.SEVERE, null, e);
                                }
                                return true;
                            } else {
                                toast(getString(R.string.toast_contrasena));
                            }
                        } else {
                            toast(getString(R.string.mail_incorrecto));
                        }
                    } else {
                        toast(getString(R.string.toast_codigoPostal));
                    }
                }
            } else {
                toast(getString(R.string.toast_faltan_Datos));
            }
        } else {
            toast(getString(R.string.toast_conexion));
        }

        return cond;
    }

    /**
     * Metodo para gestionar la baja de un usuario. Se comprueba la conexión a internet, y después se ejecuta
     * un alertdialog para confirmar que se quiere solicitar la baja y seguidamente se ejecuta
     * la clase Connect para conectarse a la base de datos remota y dar de baja al usuario
     *
     * @return
     */
    public boolean baja() {
        cond = false;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.dialog_title));
        builder.setMessage(getString(R.string.dialog_message));
        builder.setPositiveButton(getString(R.string.dialog_yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                            // Do nothing but close the dialog
                if (checkConnection()) {
                    ConnectBaja c = new ConnectBaja();
                    c.execute(user);
                    cond= true;
                } else {
                    toast(getString(R.string.toast_conexion));
                }
            }
        });
        builder.setNegativeButton(getString(R.string.dialog_no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();


        return cond;
    }

    /**
     * Metodo para publicar un Toast en pantalla según el mensaje recibido como parametro
     *
     * @param msj Mensaje a mostrar en el Toast
     */
    public void toast(String msj) {
        Toast.makeText(getApplicationContext(), msj, Toast.LENGTH_LONG).show();
    }

    /**
     * Clase que hereda de AsyncTask para poder conectarse a una base de datos remota, ya que android obliga a hacerlo en un
     * Thread distinto al principal
     * En esta clase se busca el usuario en la clase remota y si está se actualiza con los datos del usuario que llega como
     * parametro a la clase.
     * En el metodo onPostExecute, buscamos el usuario en la base de datos interna, si existe en la interna le asignamos los
     * datos obtenidos desde la remota con el usuario remota_user y le cambiamos el password por que sino el usuario de la interna
     * nos guardaria el password en md5.
     */
    private class Connect extends AsyncTask<Usuario, Void, String> {
        @Override
        protected String doInBackground(Usuario... u) {
            String response = "";
            Usuario usuario = u[0];
            ConexionBDRemotaMovilCloudLib cloudlibDB = null;
            try {
             //   cloudlibDB = new ConexionBDRemotaMovilCloudLib("cloudlib", "1234", "cloudlib", "10.0.3.2", "3306");
               cloudlibDB = new ConexionBDRemotaMovilCloudLib("root", "123456789", "cloudlib", "axelussnas.dlinkddns.com", "3306");

                if (cloudlibDB.getUsuario(usuario.getEmail()) != null) {
                    remota_user = cloudlibDB.getUsuario(usuario.getEmail());
                    cloudlibDB.updateUsuario(usuario);
                    cloudlibDB.updatePasswordUsuario(remota_user.getId(), usuario.getPassword());
                }
                response = "OK";
            } catch (ConexionBDRemotaCloudLibException e) {
                response = "NOK";
                Logger.getLogger(Alta_baja_modificacion.class.getName()).log(Level.SEVERE, null, e);
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.equals("OK")) {
                try {
                    db.open();
                    interna = db.getUsuario(remota_user.getEmail());
                    if (interna !=null) {
                        remota_user.setPassword(interna.getPassword());
                        db.updateUsuario(interna.getId(), remota_user);
                        user = interna;
                    }
                    db.close();
                } catch (SQLException e) {
                    Logger.getLogger(Alta_baja_modificacion.class.getName()).log(Level.SEVERE, null, e);
                }
                Intent i = getIntent();
                i.putExtra("usuario", remota_user.getEmail());
                i.putExtra("contrasena", remota_user.getPassword());
                setResult(RESULT_OK, i);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), R.string.toast_error, Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Clase que usaremos para dar de baja un usuario. En la base remota obtenemos el usuario remota_user para saber el id del usuario
     * a dar de baja. Después en el metodo onPostExecute buscamos el usuario en la base de datos interna y actualizamos el usuario con
     * lo cual ahora tendrá rol 3 y fecha de baja que nos servirán para saber que el usuario está de baja. Después se añaden los datos
     * al intent y se finaliza la activity con éxito.
     */
    private class ConnectBaja extends AsyncTask<Usuario, Void, String> {
        @Override
        protected String doInBackground(Usuario... u) {
            String response = "";
            Usuario usuario = u[0];
            ConexionBDRemotaMovilCloudLib cloudlibDB = null;
            try {
              //  cloudlibDB = new ConexionBDRemotaMovilCloudLib("cloudlib", "1234", "cloudlib", "10.0.3.2", "3306");
                cloudlibDB = new ConexionBDRemotaMovilCloudLib("root", "123456789", "cloudlib", "axelussnas.dlinkddns.com", "3306");
                remota_user = cloudlibDB.getUsuario(usuario.getEmail());
                cloudlibDB.setBajaUsuario(remota_user.getId());
                response = "OK";
            } catch (ConexionBDRemotaCloudLibException e) {
                response = "NOK";
                Logger.getLogger(Alta_baja_modificacion.class.getName()).log(Level.SEVERE, null, e);
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.equals("OK")) {
                try {
                    db.open();
                    Usuario interna = db.getUsuario(remota_user.getEmail());
                    remota_user.setPassword(interna.getPassword());
                    db.updateUsuario(interna.getId(), remota_user);
                    db.close();
                    cond = true;
                    Intent i = getIntent();
                    i.putExtra("usuario", remota_user.getEmail());
                    i.putExtra("contrasena", remota_user.getPassword());
                    setResult(RESULT_OK, i);
                    finish();
                }catch (SQLException ex ){
                    Logger.getLogger(Alta_baja_modificacion.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                Toast.makeText(getApplicationContext(), R.string.toast_error, Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Clase que usaremos para dar de alta un usuario. Conectamos con la base de datos remota, después añadimos el usuario. Después
     * obtenemos el usuario de la remota y le ponemos el password del objeto usuario ya que sino el objeto tendría el password en MD5.
     * En el metodo onPostExecute, añadimos el usuario a la base de datos interna y luego obtenemos los id del objeto de la interna y la remota
     * para llamar al metodo putRelacionUsuarios que actualizará la información del usuario con el syncservice. Se añaden los
     * datos email y contraseña al intent y se finaliza la activity con éxito.
     */
    private class ConnectAlta extends AsyncTask<Usuario, Void, String> {
        @Override
        protected String doInBackground(Usuario... u) {

            String response = "";
            Usuario usuario = u[0];
            ConexionBDRemotaMovilCloudLib cloudlibDB = null;
            try {
            //    cloudlibDB = new ConexionBDRemotaMovilCloudLib("cloudlib", "1234", "cloudlib", "10.0.3.2", "3306");
                cloudlibDB = new ConexionBDRemotaMovilCloudLib("root", "123456789", "cloudlib", "axelussnas.dlinkddns.com", "3306");
                cloudlibDB.putUsuario(usuario);
                remota_user = cloudlibDB.getUsuario(usuario.getEmail());
                remota_user.setPassword(usuario.getPassword());
                cloudlibDB.closeConexionBDRemotaCloudLib();
                response = "OK";
            } catch (ConexionBDRemotaCloudLibException e) {
                response = "NOK";
                Logger.getLogger(Alta_baja_modificacion.class.getName()).log(Level.SEVERE, null, e);
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.equals("OK")) {
                try {
                    db.open();
                    remota_user.setPassword(ed22.getText().toString());
                    db.putUsuario(remota_user);
                    Usuario interna_user = db.getUsuario(remota_user.getEmail());
                    db.putRelacionUsuarios(remota_user.getId(), interna_user.getId());
                    db.close();
                    cond = true;
                } catch (SQLException ex) {
                    Logger.getLogger(Alta_baja_modificacion.class.getName()).log(Level.SEVERE, null, ex);
                }
                Intent i = getIntent();
                i.putExtra("usuario", remota_user.getEmail());
                i.putExtra("contrasena", remota_user.getPassword());
                setResult(RESULT_OK, i);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), R.string.toast_error, Toast.LENGTH_LONG).show();
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
