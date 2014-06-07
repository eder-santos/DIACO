package com.diaco.del.bo;

import javax.persistence.*;

/**
 * User: esantos
 * Date: 5/24/14
 * Time: 11:54 AM
 */

@javax.persistence.Table(name = "departamento", schema = "", catalog = "DIACO")
@Entity
public class Departamento {

    private int id;
    /*private int regionId;*/
    private String nombre;
    private Region region;

    @javax.persistence.Column(name = "departamento_id")
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

/*    @javax.persistence.Column(name = "region_id")
    @Basic
    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }*/

    @javax.persistence.Column(name = "departamento_nombre")
    @Basic
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @OneToOne(cascade={CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH})
    @JoinColumn(name="region_id", insertable=false, updatable=false)
    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }
}
