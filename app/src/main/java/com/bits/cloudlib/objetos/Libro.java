package com.bits.cloudlib.objetos;

/**
 * Clase utilizada como objeto Libro para administrar todos los datos relativos a un libro.
 *
 * Created by BiTs on 07/03/2016.
 */
public class Libro {
    
    private final int id;
    private String isbn;
    private String titulo;
    private String autor;
    private String genero;
    private String editorial;
    private int edicion;
    private String observaciones;
    

    /**
     * Constructor de la clase Libro para crear un objeto nuevo
     *
     * @param id Identificador del libro en la base de datos. Sólo es posible introducir este campo con éste constructor
     * @param isbn identificador internacional de libros
     * @param titulo Título del libro
     * @param autor Persona que ha escrito el libro
     * @param genero Tipo de literatura (drama, novela, etc)
     * @param editorial Empresa encargada de la publicación del libro
     * @param edicion Versión del libro o cantidad de veces publicado
     * @param observaciones Información extra sobre el libro
     */
    public Libro (int id, String isbn, String titulo, String autor, String genero, String editorial, int edicion,
                String observaciones) {
        
        this.id = id;
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.editorial = editorial;
        this.edicion = edicion;
        this.observaciones = observaciones;
    }
    
    /**
     * Constructor por defecto
     */
    public Libro() {
        id = -1;
        isbn = null;
        titulo = null;
        autor = null;
        genero = null;
        editorial = null;
        edicion = 1;
        observaciones = null;
    }
    
    /**
     * Obtiene el identificador del libro en la base de datos
     * 
     * @return identificador del libro
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene el ocdigo internacional isbn del libro
     *
     * @return el codigo isbn
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Introduce el codigo isbn del libro
     *
     * @param isbn codigo internacional del libro
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Obtiene el titulo de libro
     *
     * @return Titulo del libro
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Introduce el titulo del libro
     *
     * @param titulo Nombre del libro
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Obtiene el autor de un libro
     *
     * @return el nombre del autor
     */
    public String getAutor() {
        return autor;
    }

    /**
     * Introduce el nombre del autor del libro
     *
     * @param autor Nombre del autor
     */
    public void setAutor(String autor) {
        this.autor = autor;
    }

    /**
     * Obtiene el género del libro
     *
     * @return Genero correspondiente al libro
     */
    public String getGenero() {
        return genero;
    }

    /**
     * Introduce el género del libro
     *
     * @param genero el género del libro
     */
    public void setGenero(String genero) {
        this.genero = genero;
    }

    /**
     * Obtiene la editorial del libro
     *
     * @return el nombre de la empresa que ha publicado el libro
     */
    public String getEditorial() {
        return editorial;
    }

    /**
     * Introduce la emprea que ha publicado el libro
     *
     * @param editorial Nombre de la emprea que ha publicado el libro
     */
    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    /**
     * Obtiene la versión o veces que se ha publicado el libro
     *
     * @return la versión o veces publicado el libro
     */
    public int getEdicion() {
        return edicion;
    }

    /**
     * Introduce la version o veces que se ha publicado el libro
     *
     * @param edicion Versión o veces publicado el libro
     */
    public void setEdicion(int edicion) {
        this.edicion = edicion;
    }

    /**
     * Obtiene las observaciones sobre el libro
     *
     * @return las obsersaciones sobre el libro
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * Introduce las observaciones del libro
     *
     * @param observaciones Datos extras a introducir sobre el libro
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
