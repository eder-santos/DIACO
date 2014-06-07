package com.diaco.del.views.beans;

import javax.faces.model.SelectItem;

/**
 * User: esantos
 * Date: 5/24/14
 * Time: 4:53 PM
 */

public class MunicipioBean extends SelectItem {

    private int id;
    private int departamentoId;
    private String nombre;
    private String idStr;
    private DepartamentoBean departamento;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        setIdStr(String.valueOf(id));
    }

    public int getDepartamentoId() {
        return departamentoId;
    }

    public void setDepartamentoId(int departamentoId) {
        this.departamentoId = departamentoId;
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

    public DepartamentoBean getDepartamento() {
        return departamento;
    }

    public void setDepartamento(DepartamentoBean departamento) {
        this.departamento = departamento;
    }
}
