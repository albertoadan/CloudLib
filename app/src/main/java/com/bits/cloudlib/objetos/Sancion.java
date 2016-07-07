package com.bits.cloudlib.objetos;

/**
 * Clase para la creación / modificación de un objeto Sanción
 *
 * Created by BiTs on 04/04/2016
 */
public class Sancion {
    
    
    private final int id;
    private String descripcion;
    private int tipo, diasReferencia, diasSancion;


    /**
     * Constructor para crear un nuevo objeto Sanción
     *
     * @param id Identificador de la sanción en la base de datos. Sólo es posible introducir este campo con éste constructor
     * @param tipo Tipo de sanción
     * @param desc Descripición/Explicación de la sanción
     * @param diasRef Dias de referencia de la sanción
     * @param diasSancion Dias que se deben cumplir de sanción
     */
    public Sancion (int id, int tipo, String desc, int diasRef, int diasSancion) {
        this.id = id;
        this.tipo = tipo;
        this.descripcion = desc;
        this.diasReferencia = diasRef;
        this.diasSancion = diasSancion;
    }
    
    /**
     * Constructor por defecto
     */
    public Sancion() {
        id = -1;
        tipo = -1;
        descripcion = null;
        diasReferencia = -1;
        diasSancion = -1;
    }
    
    /**
     * Obtiene el identificador de la sanción en la base de datos
     * 
     * @return identificador de la sanción
     */
    public int getId() {
        return id;
    }
    
    /**
     * Obtiene el tipo de sanción a aplicar
     *
     * @return Tipo de sanción
     */
    public int getTipo() {
        return tipo;
    }

    /**
     * Introduce el tipo de sanción
     *
     * @param tipo Tipo de sanción
     */
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene la descripción/explicación de la sanción
     *
     * @return Texto explicativo de la sanción
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Introduce la descripción de la sanción
     *
     * @param descripcion Texto explicativo de la sanción
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene los dias de referencia de la sanción
     *
     * @return los dias de referencia
     */
    public int getDiasReferencia() {
        return diasReferencia;
    }

    /**
     * Introduce los dias de referencia de la sanción
     *
     * @param diasReferencia dias de referencia de la sanción
     */
    public void setDiasReferencia(int diasReferencia) {
        this.diasReferencia = diasReferencia;
    }

    /**
     * Obtiene los dias d de la sanción
     *
     * @return los dias  de la sanción
     */
    public int getDiasSancion() {
        return diasSancion;
    }

    /**
     * Introduce los dias de la sanción
     *
     * @param diasSancion Dias de sanción
     */
    public void setDiasSancion(int diasSancion) {
        this.diasSancion = diasSancion;
    }
}
