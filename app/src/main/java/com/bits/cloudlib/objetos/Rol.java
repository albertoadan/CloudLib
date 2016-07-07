package com.bits.cloudlib.objetos;

/**
 * Clase para la creación / modificación del objeto Rol
 *
 * Created by BiTs on 04/04/2016
 */
public class Rol {

    public static final int ADMIN_USER = 0;
    public static final int NORMAL_USER = 1;
    public static final int BLOCK_USER = 2;
    public static final int BAJA_USER = 3;

    private final int id;
    private int tipo;
    private String descripcion;

    /**
     * Constructor para la creación de un nuevo objeto Rol
     *
     * @param id Identificador del rol en la base de datos. Sólo es posible
     * introducir este campo con éste constructor
     * @param tipo Entero que identifica el tipo de rol.
     * @param descripcion Explicación de los permisos del rol
     */
    public Rol(int id, int tipo, String descripcion) {
        this.id = id;
        this.tipo = tipo;
        this.descripcion = descripcion;
    }

    /**
     * Constructor por defecto
     */
    public Rol() {
        id = -1;
        tipo = -1;
        descripcion = null;
    }

    /**
     * Obtiene el identificador del rol en la base de datos
     *
     * @return identificador del rol
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene el tipo de rol
     *
     * @return tipo de rol
     */
    public int getTipo() {
        return tipo;
    }

    /**
     * Introduce el tipo de rol
     *
     * @param tipo tipo de rol
     */
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene la explicación de los permisos que tiene el usuario según tipo de
     * rol
     *
     * @return la explicación de los permisos
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Introduce la explicación de los permisos según el tipo de rol
     *
     * @param descripcion la explicación de los permisos
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    /**
     * @author Víctor Castellanos Pérez
     */
    public String toString() {
        return descripcion;
    }
}
