package com.bits.cloudlib.controlador;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * Created by Rafael on 28/04/2016.
 */
public class ConexionBDRemotaMovilCloudLibExceptionHandler extends Throwable implements UncaughtExceptionHandler {

    private static UncaughtExceptionHandler mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        mDefaultHandler.uncaughtException(thread, ex);
    }
}
