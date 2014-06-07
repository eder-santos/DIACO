package com.diaco.del.bo;

import javax.persistence.*;

/**
 * User: esantos
 * Date: 5/25/14
 * Time: 9:53 PM
 */

@javax.persistence.Table(name = "region", schema = "", catalog = "DIACO")
@Entity
public class Region {

    private int id;
    private String nombre;

    @javax.persistence.Column(name = "region_id")
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @javax.persistence.Column(name = "region_nombre")
    @Basic
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
