package com.diaco.del.views.beans;

import javax.faces.model.SelectItem;

/**
 * User: esantos
 * Date: 5/24/14
 * Time: 9:42 PM
 */

public class SucursalBean extends SelectItem {

    private int id;
    private int departamentoId;
    private int municipioId;
    private int comercioId;
    private String nombre;
    private String direccion;
    private String idStr;
    private MunicipioBean municipio;
    private ComercioBean comercio;

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

    public int getDepartamentoId() {
        return departamentoId;
    }

    public void setDepartamentoId(int departamentoId) {
        this.departamentoId = departamentoId;
    }

    public int getMunicipioId() {
        return municipioId;
    }

    public void setMunicipioId(int municipioId) {
        this.municipioId = municipioId;
    }

    public int getComercioId() {
        return comercioId;
    }

    public void setComercioId(int comercioId) {
        this.comercioId = comercioId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
        setLabel(nombre);
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public MunicipioBean getMunicipio() {
        return municipio;
    }

    public void setMunicipio(MunicipioBean municipio) {
        this.municipio = municipio;
    }

    public ComercioBean getComercio() {
        return comercio;
    }

    public void setComercio(ComercioBean comercio) {
        this.comercio = comercio;
    }
}
