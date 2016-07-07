package com.bits.cloudlib.objetos;

/**
 * Clase para crear/modificar el objeto Biblioteca
 *
 * Created by BiTs on 04/04/2016
 * @author Albert Adán & Rafael Bravo 04/04/2016
 */
public class Biblioteca {

    private String nombre;
    private String direccion;
    private String descripcion;
    private int id;



    public  Biblioteca() {}
    /**
     * Constructor de la clase Biblioteca
     *
     * @param nombre Nombre de la biblioteca
     * @param direccion Dirección de la biblioteca
     * @param descripcion Datos extra sobre la biblioteca que pueden ayudar por ejemplo a su localizacion
     */
    public  Biblioteca (int id, String nombre, String direccion, String descripcion) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.descripcion = descripcion;
        this.id = id;
    }

    /**
     * Devuelve el nombre de la biblioteca
     *
     * @return nombre de la biblioteca
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * introduce el nombre de la biblioteca
     *
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la direccion de la biblioteca
     *
     * @return direccion de la biblioteca
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * introduce la dirección de la biblioteca
     *
     * @param direccion Ubicación de la biblioteca en texto.
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Obtiene una descripcion de la biblioteca que puede ayudar a su localizacion
     *
     * @return Texto explicativo de la descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * introduce una descripcion de la biblioteca
     *
     * @param descripcion Texto describiendo detalles o información extra de la biblioteca
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * obtiene el identificador de la biblioteca
     *
     * @return devuelve el identificador
     */
    public int getId() {
        return id;
    }

    /**
     * introduce el identificador de la biblioteca
     *
     * @param id Identificador de la biblioteca
     */
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return nombre;
    }

}

