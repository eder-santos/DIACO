package com.diaco.del.bo;

import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: esantos
 * Date: 5/24/14
 * Time: 7:43 PM
 */

@NamedNativeQueries({
        @NamedNativeQuery(
                name = "ingresar_queja",
                query = "CALL ingresar_queja(:comercio_id, :sucursal_id, :queja_descripcion, :queja_incidente)",
                resultClass = Queja.class
        )
})
@javax.persistence.Table(name = "queja", schema = "", catalog = "DIACO")
@Entity
public class Queja {

    private int id;
    private Comercio comercio;
    private Sucursal sucursal;
/*    private int comercioId;
    private int sucursalId;*/
    private String descripcion;
    private String fecha;
    private Date fechaIncidente;

    @javax.persistence.Column(name = "queja_id")
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

/*    @javax.persistence.Column(name = "comercio_id")
    @Basic
    public int getComercioId() {
        return comercioId;
    }

    public void setComercioId(int comercioId) {
        this.comercioId = comercioId;
    }

    @javax.persistence.Column(name = "sucursal_id")
    @Basic
    public int getSucursalId() {
        return sucursalId;
    }

    public void setSucursalId(int sucursalId) {
        this.sucursalId = sucursalId;
    }*/

    @javax.persistence.Column(name = "queja_descripcion")
    @Basic
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @javax.persistence.Column(name = "queja_fecha")
    @Basic
    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @javax.persistence.Column(name = "queja_fecha_incidente")
    @Basic
    public Date getFechaIncidente() {
        return fechaIncidente;
    }

    public void setFechaIncidente(Date fechaIncidente) {
        this.fechaIncidente = fechaIncidente;
    }
/*    public String getFechaIncidente() {
*//*        Date date = null;
        if (fechaIncidente != null) {
            try {
                DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                date = formatter.parse(fechaIncidente);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (date != null)
            return date.toString();*//*
        return fechaIncidente;
    }

    public void setFechaIncidente(String fechaIncidente) {
        this.fechaIncidente = fechaIncidente;
    }*/

    @OneToOne(cascade={CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH})
    @JoinColumn(name="comercio_id", insertable=false, updatable=false)
    public Comercio getComercio() {
        return comercio;
    }

    public void setComercio(Comercio comercio) {
        this.comercio = comercio;
    }

    @OneToOne(cascade={CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH})
    @JoinColumn(name="sucursal_id", insertable=false, updatable=false)
    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }
}