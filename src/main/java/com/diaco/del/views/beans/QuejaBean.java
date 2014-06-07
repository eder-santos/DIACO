package com.diaco.del.views.beans;

/**
 * User: esantos
 * Date: 5/24/14
 * Time: 9:41 PM
 */

public class QuejaBean {

    private int id;
    private int comercioId;
    private int sucursalId;
    private String descripcion;
    private String fecha;
    private String fechaIncidente;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getComercioId() {
        return comercioId;
    }

    public void setComercioId(int comercioId) {
        this.comercioId = comercioId;
    }

    public int getSucursalId() {
        return sucursalId;
    }

    public void setSucursalId(int sucursalId) {
        this.sucursalId = sucursalId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFechaIncidente() {
        return fechaIncidente;
    }

    public void setFechaIncidente(String fechaIncidente) {
        this.fechaIncidente = fechaIncidente;
    }
}
