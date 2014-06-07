package com.diaco.del.bo;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * User: esantos
 * Date: 5/24/14
 * Time: 7:31 PM
 */

@javax.persistence.Table(name = "categoria", schema = "", catalog = "DIACO")
@Entity
public class Categoria {

    private int id;
    private String nombre;

    @javax.persistence.Column(name = "categoria_id")
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @javax.persistence.Column(name = "categoria_nombre")
    @Basic
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
