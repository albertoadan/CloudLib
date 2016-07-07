package com.bits.cloudlib.objetos;

import java.io.Serializable;

/**
 * Clase que optimiza la gestión de los resultados de búsqueda de ejemplares en la base de datos
 * 
 * Created by Alberto Adán & Rafael Bravo & Víctor Castellanos on 02/04/2016.
 */
public class Disponible implements Serializable{
    
    /* Variables finales de la clase. No son modificables, pues son simplemente el resultado
       de una búsqueda en la base de datos para mostrar resultados en la pantalla principal
    */
    private final int idBiblioteca, edicionLibro;
    private final String nombreBiblioteca, direccionBiblioteca, descripcionBiblioteca, tituloLibro,
            autorLibro, editorialLibro;
    
    /**
     * Constructor único, con todos los parámetros
     * 
     * @param idBiblioteca Identificador de la biblioteca
     * @param nombreBiblioteca Nombre de la biblioteca
     * @param direccionBiblioteca Dirección de la biblioteca
     * @param descripcionBiblioteca Descripción de la biblioteca
     * @param tituloLibro Título del libro
     * @param autorLibro Autor del libro
     * @param editorialLibro Editorial que publica el libro
     * @param edicionLibro Edición del libro
     */
    public Disponible(int idBiblioteca, String nombreBiblioteca, String direccionBiblioteca, 
            String descripcionBiblioteca, String tituloLibro, String autorLibro, String editorialLibro, 
            int edicionLibro) {
        
        this.idBiblioteca = idBiblioteca;
        this.nombreBiblioteca = nombreBiblioteca;
        this.direccionBiblioteca = direccionBiblioteca;
        this.descripcionBiblioteca = descripcionBiblioteca;
        this.tituloLibro = tituloLibro;
        this.autorLibro = autorLibro;
        this.editorialLibro = editorialLibro;
        this.edicionLibro = edicionLibro;
    }
    
    /**
     * Obtiene el identificador de la biblioteca
     * @return Identificador de biblioteca
     */
    public int getIdBiblioteca() {
        return idBiblioteca;
    }
    
    /**
     * Obtiene el nombre de la biblioteca
     * @return Nombre de la biblioteca
     */
    public String getNombreBiblioteca() {
        return nombreBiblioteca;
    }
    
    /**
     * Obtiene la dirección de la biblioteca
     * @return Dirección de la biblioteca
     */
    public String getDireccionBiblioteca() {
        return direccionBiblioteca;
    }
    
    /**
     * Obtiene la descripción de la biblioteca
     * @return Descripción de la biblioteca
     */
    public String getDescripcionBiblioteca() {
        return descripcionBiblioteca;
    }
    
    /**
     * Obtiene el título del libro
     * @return Título del libro
     */
    public String getTituloLibro() {
        return tituloLibro;
    }
    
    /**
     * Obtiene el autor del libro
     * @return Autor del libro
     */
    public String getAutorLibro() {
        return autorLibro;
    }
    
    /**
     * Obtiene la editorial que publica el libro
     * @return Editorial que publica el libro
     */
    public String getEditorialLibro() {
        return editorialLibro;
    }
    
    /**
     * Obtiene la edición del libro
     * @return Edición del libro
     */
    public int getEdicionLibro() {
        return edicionLibro;
    }
}
