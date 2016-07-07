package com.bits.cloudlib;

import java.io.Serializable;

/**
 * Created by Albert on 27/04/2016.
 *
 * @author Alberto Adán
 *
 * Clase para la creación de los elementos que habrá dentro de los distintos lisview de la aplicación teniendo
 * dos partes una principal y otra secundaria para añadir dos textos a cada elemento del listview.
 */
public class Item implements Serializable {


    private String texto1;
    private String texto2;


    /**
     *Constructor de la clase item
     *
     * @param t1 Texto principal o título que tendrá el item en el listview
     * @param t2 Texto secundario en el listview
     */
    public Item (String t1, String t2)  {
        this.texto1 = t1;
        this.texto2 = t2;
    }

    /**
     * Metodo que devuelve el texto principal del Item
     * @return devuelve el texto principal
     */
    public String getTexto1 () {
        return texto1;
    }

    /**
     * Metodo que cambia el valor del texto principal del Item
     * @param obj Texto a cambiar
     */
    public void setTexto1 (String obj) {
        this.texto1=obj;
    }

    /**
     * Meotdo que devuelve el texto secundario del Item
     * @return devuelve el texto secundario
     */
    public String getTexto2()  {
        return texto2;
    }

    /**
     * Metodo para cambiar el valor del texto secundario
     * @param obj Texto a cambiar
     */
    public void setTexto2(String obj) {
        this.texto2 = obj;
    }


}
