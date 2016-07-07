package com.bits.cloudlib.controlador;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Rafael Bravo Contreras on 17/02/2016.
 */
public class SyncService extends Service {

    // Constantes para la configuración de la conexión
    private static final String NAMESPACE = "http://cloudlib.com";
    private static final String URL = "http://ip:port/cloudlib";

    // Intérvalo de ejecución programada
    private static final int INTERVALO_EJECUCION_SYNC = 28000000;

    // Base de datos de la aplicación
    private DBInterface internalDB;
    // Conexión con la base de datos del sistema (CloudLib)
    private ConexionBDRemotaMovilCloudLib cloudlibDB = null;

    // Temporizador de ejecución
    private Timer temporizador = new Timer();
    private final SyncHandler syncHandler = new SyncHandler(this);
    private final TimerTask syncTimerTask = new TimerTask() {
        @Override
        public void run() {
            syncHandler.sendEmptyMessage(0);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("SYNC", "Servicio creado");
        Thread.setDefaultUncaughtExceptionHandler(new ConexionBDRemotaMovilCloudLibExceptionHandler());

        // Obtención de la base de datos de la aplicación
        internalDB = new DBInterface(this);

        // Activación del temporizador de ejecución
        temporizador.scheduleAtFixedRate(syncTimerTask, 0, INTERVALO_EJECUCION_SYNC);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("SYNC", "Servicio destruido");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private static class SyncHandler extends Handler {

        private final WeakReference<SyncService> mActivity;

        public SyncHandler(SyncService activity) {
            mActivity = new WeakReference<SyncService>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final SyncService activity = mActivity.get();
            if (activity != null) {

                activity.internalDB.open();
                Thread fil1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // Obtención de la conexión con la base de datos del sistema (CloudLib)
                            activity.cloudlibDB =  new ConexionBDRemotaMovilCloudLib("root", "123456789", "cloudlib", "axelussnas.dlinkddns.com", "3306");
                            Log.d("SYNC:1OK", "ConexionBDRemotaMovilCloudLib");
                        } catch (ConexionBDRemotaCloudLibException e) {
                            Log.d("SYNC:open", e.getMessage());
                        }

                    }
                });

                fil1.start();
                try {
                    fil1.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                // Sincronización de datos entre la base de datos interna (SQLite) y la del sistema (CloudLib)
                // (Los datos de Usuarios y Reservas no necesitan ser sincronizados)

                // En primer lugar, se actualizan las tablas que NO tienen relación con los usuarios: Rol, Sancion
                Thread fil2 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            activity.internalDB.actualizarDatosTablaRol(activity.cloudlibDB.getAllRol());
                            Log.d("SYNC:2OK", "actualizarDatosTablaRol()");
                        } catch (ConexionBDRemotaCloudLibException e) {
                            Log.d("SYNC:Rol", e.getMessage());
                        }

                        try {
                            activity.internalDB.actualizarDatosTablaSancion(activity.cloudlibDB.getAllSancion());
                            Log.d("SYNC:2OK", "actualizarDatosTablaSancion()");
                        } catch (ConexionBDRemotaCloudLibException e) {
                            Log.d("SYNC:Sancion", e.getMessage());
                        }
                    }
                });

                fil2.start();
                try {
                    fil2.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // En segundo lugar, se actualizan los datos de los usuarios y las tablas que si tienen relación con los usuarios: Prestamo, SancionUsuario
                // Para ello, se obtienen los identificadores de usuario en la base de datos del sistema
                Thread fil3 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final ArrayList<int[]> relacionUsuarios = activity.internalDB.getAllRelacionUsuarios();
                        // Se comprueba que hay relaciones de usuarios en la base de datos interna (SQLite)
                        if ((relacionUsuarios != null) && (relacionUsuarios.size() > 0)) {
                            for (int[] relacion : relacionUsuarios) {
                                // Actualización de datos de usuarios
                                try {
                                    activity.internalDB.updateUsuario(relacion[1], activity.cloudlibDB.getUsuario(relacion[2]));
                                    Log.d("SYNC:3OK", "updateUsuario()");
                                } catch (ConexionBDRemotaCloudLibException e) {
                                    Log.d("SYNC:Usuario", e.getMessage());
                                }
                                // Actualización de objetos Prestamo de cada usuario
                                try {
                                    activity.internalDB.actualizarDatosTablaPrestamo(activity.cloudlibDB.getPrestamosUsuario(relacion[2]));
                                    Log.d("SYNC:3OK", "actualizarDatosTablaPrestamo()");
                                } catch (ConexionBDRemotaCloudLibException e) {
                                    Log.d("SYNC:Prestamo", e.getMessage());
                                }
                                // Actualización de objetos SancionUsuario
                                try {
                                    activity.internalDB.actualizarDatosTablaSancionUsuario(activity.cloudlibDB.getSancionesUsuario(relacion[2]));
                                    Log.d("SYNC:3OK", "actualizarDatosTablaSancionUsuario()");
                                } catch (ConexionBDRemotaCloudLibException e) {
                                    Log.d("SYNC:SancionUsuario", e.getMessage());
                                }
                                // Actualización de objetos Reserva
                                try {
                                    activity.internalDB.actualizarDatosReservasUsuario(activity.cloudlibDB.getReservasUsuario(relacion[2]));
                                    Log.d("SYNC:3OK", "actualizarDatosReservasUsuario()");
                                } catch (ConexionBDRemotaCloudLibException e) {
                                    Log.d("SYNC:ReservasUsuario", e.getMessage());
                                }
                            }
                        } else {
                            Log.d("SYNC:3OK", "No se han encontrado relaciones de usuarios");
                        }

                    }
                });

                fil3.start();
                try {
                    fil3.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                activity.internalDB.close();
                try {
                    activity.cloudlibDB.closeConexionBDRemotaCloudLib();
                } catch (ConexionBDRemotaCloudLibException e) {
                    Log.d("SYNC:CloseConnection", e.getMessage());
                }
            }
        }
    }
}
