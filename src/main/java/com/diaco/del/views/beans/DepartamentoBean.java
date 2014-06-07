package com.diaco.del.views.beans;

import javax.faces.model.SelectItem;

/**
 * User: esantos
 * Date: 5/24/14
 * Time: 1:18 PM
 */

public class DepartamentoBean extends SelectItem {

    private int id;
    private int regionId;
    private String nombre;
    private String idStr;
    private String nombreRegion;
    private RegionBean region;

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

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
        setLabel(nombre);
    }

    public String getNombreRegion() {
        return nombreRegion;
    }

    public void setNombreRegion(String nombreRegion) {
        this.nombreRegion = nombreRegion;
    }

    public RegionBean getRegion() {
        return region;
    }

    public void setRegion(RegionBean region) {
        this.region = region;
    }
}
