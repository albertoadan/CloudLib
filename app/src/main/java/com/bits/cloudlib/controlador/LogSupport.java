package com.bits.cloudlib.controlador;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Graba las excepciones en un ficher de log en la carpeta /logs de la aplicacion.
 * El nombre del fichero sera: CloudLib_log_ddmmyyyyHHmmss.txt
 * (Basado en código fuente de un EAC del IOC) 
 * @author Víctor Castellanos Pérez (modificaciones del origianl del IOC)
 */
public class LogSupport {

    private static final Logger log = Logger.getLogger("CloudLib");
    private static FileHandler fileTxt = null;
    private static SimpleFormatter formatterTxt;

    /**
     * Graba una excepcion en el log.
     * @param e excepcion para grabar.
     */
    public static void grava(Exception e) {
        //Inicializamos el log en la primera llamada.
        if (fileTxt == null) {  

            try {

                fileTxt = new FileHandler("./logs/CloudLib_log_" + (new SimpleDateFormat("yyyymmddHHmmss")).format(new Date()) + ".txt", true);
                
            } catch (IOException | SecurityException ex) {
                throw new RuntimeException("Error al inicializar el logger.\n" + ex.getMessage());
            }


            // Crear txt Formatter  
            formatterTxt = new SimpleFormatter();
            fileTxt.setFormatter(formatterTxt);
            log.addHandler(fileTxt);

        }
        //Grabamos la informacion de la excepcion producida.
        log.log(Level.SEVERE, e.getMessage(), e);
    }
}
