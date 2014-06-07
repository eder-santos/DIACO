package com.diaco.del.bo;

import javax.persistence.*;

/**
 * User: esantos
 * Date: 5/24/14
 * Time: 7:37 PM
 */

@javax.persistence.Table(name = "comercio", schema = "", catalog = "DIACO")
@Entity
public class Comercio {

    private int id;
    /*private int categoriaId;*/
    private String nombre;
    private Categoria categoria;

    @javax.persistence.Column(name = "comercio_id")
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

/*    @javax.persistence.Column(name = "categoria_id")
    @Basic
    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }*/

    @javax.persistence.Column(name = "comercio_nombre")
    @Basic
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @OneToOne(cascade={CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH})
    @JoinColumn(name="categoria_id", insertable=false, updatable=false)
    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
