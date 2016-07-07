package com.bits.cloudlib.objetos;

import java.io.Serializable;

/**
 * Clase para la creación/modifiación del objeto Ejemplar
 *
 * Created by Created by BiTs on 04/04/2016.
 */
public class Ejemplar  implements Serializable{
    
    
    private final int id;
    private int estado, idLibro, idSeccion, idBiblioteca;
    
    // Constantes públicas: Estados de un ejemplar
    public static final int ESTADO_DISPONIBLE = 0;
    public static final int ESTADO_PRESTADO = 1;
    public static final int ESTADO_RESERVADO = 2;
    public static final int ESTADO_BAJA = 3;
    

    /**
     * Constructor del objeto Ejemplar
     *
     * @param id Identificador del ejemplar en la base de datos. Sólo es posible introducir este campo con éste constructor
     * @param estado Nos dice si el libro se encuentra en la biblioteca, reservado o prestado.
     * @param idLibro Identificador del libro
     * @param idSeccion Identificador de la sección
     * @param idBiblioteca Identificador de la biblioteca
     */
    public Ejemplar (int id, int estado, int idLibro, int idSeccion, int idBiblioteca) {
        this.id = id;
        this.estado = estado;
        this.idLibro = idLibro;
        this.idSeccion = idSeccion;
        this.idBiblioteca = idBiblioteca;
    }
    
    /**
     * Constructor por defecto
     */
    public Ejemplar () {
        id = -1;
        estado = -1;
        idLibro = -1;
        idSeccion = -1;
        idBiblioteca = -1;
    }
    
    /**
     * Obtiene el identificador del ejemplar en la base de datos
     * 
     * @return identificador del ejemplar
     */
    public int getId() {
        return id;
    }
    
    
    /**
     * Obtiene el estado del libro
     *
     * @return estado del libro
     */
    public int getEstado() {
        return estado;
    }

    /**
     * Introduce el estado del libro. Pueden usarse las constantes de clase, cuando se usa éste método.
     *
     * @param estado estado del libro
     */
    public void setEstado(int estado) {
        this.estado = estado;
    }

    /**
     * Obtiene el identificador del libro
     *
     * @return el identificador del libro
     */
    public int getIdLibro() {
        return idLibro;
    }

    /**
     * Introduce el identificador del libro
     *
     * @param idLibro el identificador del libro
     */
    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }
    
    /**
     * Obtiene la sección a la que pertenece el ejemplar
     *
     * @return el identificador de la sección
     */
    public int getIdSeccion() {
        return idSeccion;
    }

    /**
     * Introduce la sección a la que pertenece el ejemplar
     *
     * @param idSeccion el identificador de la sección
     */
    public void setIdSeccion(int idSeccion) {
        this.idSeccion = idSeccion;
    }
    
    /**
     * Obtiene el identificador de la biblioteca a la que corresponde la sección.
     *
     * @return identificador de la biblioteca
     */
    public int getIdBiblioteca() {
        return idBiblioteca;
    }

    /**
     * Introduce el identificador de la biblioteca a la que corresponde  la sección
     *
     * @param idBiblioteca Biblioteca correspondiente a la sección
     */
    public void setIdBiblioteca(int idBiblioteca) {
        this.idBiblioteca = idBiblioteca;
    }
}
