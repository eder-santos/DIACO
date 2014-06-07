package com.diaco.del.bo;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * User: esantos
 * Date: 5/24/14
 * Time: 7:31 PM
 */

@javax.persistence.Table(name = "usuario", schema = "", catalog = "DIACO")
@Entity
public class Usuario {

    private int id;
    private String nombre;
    private String apellido;
    private String usuario;
    private String estado;
    private String rol;
    private String fechaCreacion;
    private String password;

    @javax.persistence.Column(name = "usuario_id")
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @javax.persistence.Column(name = "usuario_nombre")
    @Basic
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @javax.persistence.Column(name = "usuario_apellido")
    @Basic
    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    @javax.persistence.Column(name = "usuario")
    @Basic
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    @javax.persistence.Column(name = "usuario_estado")
    @Basic
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @javax.persistence.Column(name = "usuario_rol")
    @Basic
    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @javax.persistence.Column(name = "usuario_fecha")
    @Basic
    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @javax.persistence.Column(name = "usuario_password")
    @Basic
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
