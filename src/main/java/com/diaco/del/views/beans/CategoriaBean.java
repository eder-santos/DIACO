package com.diaco.del.views.beans;

import javax.faces.model.SelectItem;

/**
 * User: esantos
 * Date: 5/24/14
 * Time: 7:57 PM
 */
public class CategoriaBean extends SelectItem {

    private int id;
    private String nombre;
    private String idStr;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        setIdStr(String.valueOf(id));
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
        setLabel(nombre);
    }

    public String getIdStr() {
        return idStr;
    }

    public void setIdStr(String idStr) {
        this.idStr = idStr;
        setValue(idStr);
    }
}
