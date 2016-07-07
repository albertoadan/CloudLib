package com.bits.cloudlib.controlador;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Rafael on 16/04/2016.
 * @author : Rafael Bravo y Alberto Adán
 * @Modified : Alberto Adán
 */
public class Utils {

    /**
     * Metodo que obtiene un objeto Date de un String con forma de fecha. Primero se asigna el formato de la fecha
     * y después se hace un parse para convertir el texto en fecha.
     *
     * @param stringDate Texto con formato de fecha
     * @return Objeto Date
     */
    public static Date getDateFromString(String stringDate) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        format.setLenient(false);


        try {
            date = format.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * Metodo que transforma un objeto Date, fecha, en texto. Primero se asigna el formato y después obtiene el
     * texto formatado.
     *
     * @param date Objeto fecha a transformar
     * @return Texto con formato de fecha
     */
    public static String getStringFromDate(Date date) {
        String formatDate = null;
        String format = "dd-MM-yyyy HH:mm:ss";

        SimpleDateFormat formatData = new SimpleDateFormat(format, Locale.ROOT);
        formatDate = formatData.format(date);

        return formatDate;
    }
}