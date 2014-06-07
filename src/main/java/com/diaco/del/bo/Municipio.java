package com.diaco.del.bo;

import javax.persistence.*;

/**
 * User: esantos
 * Date: 5/24/14
 * Time: 4:45 PM
 */

@javax.persistence.Table(name = "municipio", schema = "", catalog = "DIACO")
@Entity
public class Municipio {

    private int id;
    private String nombre;
    private Departamento departamento;

    @javax.persistence.Column(name = "municipio_id")
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @javax.persistence.Column(name = "municipio_nombre")
    @Basic
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @OneToOne(cascade={CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH})
    @JoinColumn(name="departamento_id", insertable=false, updatable=false)
    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }
}
