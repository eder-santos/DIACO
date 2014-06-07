package com.diaco.del.views.beans;

import javax.faces.model.SelectItem;

/**
 * User: esantos
 * Date: 5/25/14
 * Time: 9:55 PM
 */

public class RegionBean extends SelectItem {

    private int id;
    private String idStr;
    private String nombre;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        setIdStr(String.valueOf(id));
    }

    public String getIdStr() {
        return idStr;
    }

    public void setIdStr(String idStr) {
        this.idStr = idStr;
        setValue(idStr);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
        setLabel(nombre);
    }
}
