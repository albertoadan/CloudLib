package com.bits.cloudlib.objetos;

/**
 * Clase para la creación / modificación de un objeto Seccion que sirve para saber donde está situado un libro en una biblioteca
 *
 * Created by BiTs on 04/04/2016
 */
public class Seccion {
    
    
    private final int id;
    private String nombre ;

    /**
     * Constructor para crear un nuevo objeto sección
     *
     * @param id Identificador de la sección en la base de datos. Sólo es posible introducir este campo con éste constructor
     * @param seccion Sitio donde se encuentra el libro en la biblioteca
     */
    public Seccion (int id, String seccion) {
        this.id = id;
        this.nombre = seccion;
    }
    
    /**
     * Constructor por defecto
     */
    public Seccion() {
        id = -1;
        nombre = null;
    }
    
    /**
     * Obtiene el identificador de la sección en la base de datos
     * 
     * @return identificador de la sanción
     */
    public int getId() {
        return id;
    }
    /**
     * Obtiene el nombre de la sección
     *
     * @return nombre de la sección
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Introduce el nombre de la sección
     *
     * @param nombre Nombre a añadir de la sección de la biblioteca
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    /**
     * Método que devuelve el nombre de la sección como representación de ésta en texto
     * 
     * @return Nombre de la sección
     */
    @Override
    public String toString() {
        return nombre;
    }
}
