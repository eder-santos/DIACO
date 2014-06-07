package com.diaco.del.bo;

import javax.persistence.*;

/**
 * User: esantos
 * Date: 5/24/14
 * Time: 7:39 PM
 */

@javax.persistence.Table(name = "sucursal", schema = "", catalog = "DIACO")
@Entity
public class Sucursal {

    private int id;
    /*private int municipioId;*/
    /*private int comercioId;*/
    private String nombre;
    private String direccion;
    private Municipio municipio;
    private Comercio comercio;

    @javax.persistence.Column(name = "sucursal_id")
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

/*    @javax.persistence.Column(name = "municipio_id")
    @Basic
    public int getMunicipioId() {
        return municipioId;
    }

    public void setMunicipioId(int municipioId) {
        this.municipioId = municipioId;
    }*/

/*    @javax.persistence.Column(name = "comercio_id")
    @Basic
    public int getComercioId() {
        return comercioId;
    }

    public void setComercioId(int comercioId) {
        this.comercioId = comercioId;
    }*/

    @javax.persistence.Column(name = "sucursal_nombre")
    @Basic
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @javax.persistence.Column(name = "sucursal_direccion")
    @Basic
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @OneToOne(cascade={CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH})
    @JoinColumn(name="municipio_id", insertable=false, updatable=false)
    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    @OneToOne(cascade={CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH})
    @JoinColumn(name="comercio_id", insertable=false, updatable=false)
    public Comercio getComercio() {
        return comercio;
    }

    public void setComercio(Comercio comercio) {
        this.comercio = comercio;
    }
}
