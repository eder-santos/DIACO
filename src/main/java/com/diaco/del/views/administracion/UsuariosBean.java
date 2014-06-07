package com.diaco.del.views.administracion;

import com.diaco.del.bo.Usuario;
import com.diaco.del.util.DiacoUtil;
import com.diaco.del.util.ServiceUtil;
import com.diaco.del.views.beans.UsuarioBean;
import org.apache.log4j.Logger;
import org.icefaces.ace.model.table.RowStateMap;
import org.icefaces.util.JavaScriptRunner;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;

import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * User: esantos
 * Date: 5/29/14
 * Time: 6:03 PM
 */

@ManagedBean(name = "usuario")
@SessionScoped
public class UsuariosBean {

    private static final Logger logger = Logger.getLogger(UsuariosBean.class);
    private boolean preRendered;
    private boolean exito;
    private String titulo;
    private int opcion;
    private ArrayList<UsuarioBean> usuarios;
    private List<Usuario> usuariosBo;
    private RowStateMap stateMap = new RowStateMap();
    private String nombre;
    private String apellido;
    private String usuario;
    private String estado;
    private String rol;
    private String password;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getOpcion() {
        return opcion;
    }

    public void setOpcion(int opcion) {
        this.opcion = opcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isExito() {
        return exito;
    }

    public void setExito(boolean exito) {
        this.exito = exito;
    }

    public ArrayList<UsuarioBean> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(ArrayList<UsuarioBean> usuarios) {
        this.usuarios = usuarios;
    }

    public RowStateMap getStateMap() {
        return stateMap;
    }

    public void setStateMap(RowStateMap stateMap) {
        this.stateMap = stateMap;
    }

    public UsuarioBean getSelectedRow() {
        if (stateMap.getSelected() != null && stateMap.getSelected().size() > 0)
            return (UsuarioBean) stateMap.getSelected().get(0);
        else
            return null;
    }

    @PreDestroy
    public void destroy() {
        preRendered = false;
    }

    public void preRender(ComponentSystemEvent event) {
        if (!preRendered) {
            preRendered = true;
            if (usuarios == null) {
                cargarDatos();
            }
        }
    }

    private void cargarDatos() {
        usuarios = new ArrayList<UsuarioBean>();
        usuariosBo = ServiceUtil.getDaoService().findByFields(Usuario.class, null, null);
        for (Usuario reg: usuariosBo) {
            UsuarioBean b = new UsuarioBean();
            BeanUtils.copyProperties(reg, b);
            usuarios.add(b);
        }
    }

    public boolean isSelected() {
        return getSelectedRow() != null;
    }

    public void crea() {
        clear("form");
        opcion = 1;
        titulo = "Crear Usuario";
        JavaScriptRunner.runScript(FacesContext.getCurrentInstance(), "dialogo.show();");
    }

    public void edita() {
        clear("form");
        if (getSelectedRow() != null) {
            nombre = getSelectedRow().getNombre();
            password = getSelectedRow().getPassword();
            apellido = getSelectedRow().getApellido();
            usuario = getSelectedRow().getUsuario();
            estado = getSelectedRow().getEstado();
            rol = getSelectedRow().getRol();
            opcion = 2;
            titulo = "Modificar Usuario";
            JavaScriptRunner.runScript(FacesContext.getCurrentInstance(), "dialogo.show();");
        }
    }

    public void borra() {
        clear("form");
        if (getSelectedRow() != null) {
            nombre = getSelectedRow().getNombre();
            apellido = getSelectedRow().getApellido();
            usuario = getSelectedRow().getUsuario();
            estado = getSelectedRow().getEstado();
            rol = getSelectedRow().getRol();
            opcion = 3;
            titulo = "Eliminar Usuario";
            JavaScriptRunner.runScript(FacesContext.getCurrentInstance(), "dialogo.show();");
        }
    }

    public void agregar() {
        for (Usuario user: usuariosBo) {
            if (user.getUsuario().equals(usuario)) {
                DiacoUtil.crearMensajeError("Error usuario duplicado", "form:regionNombre", FacesMessage.SEVERITY_ERROR);
                return;
            }
        }

        String sql = "{call DIACO.ingresar_usuario(?,?,?,?,?,?)}";
        logger.info("SQL[" + sql + "]");
        estado = "A";
        password = ServiceUtil.getPasswordEncoder().encodePassword(password, "UMG");
        if (!ServiceUtil.getDaoService().callProcedure(sql, new Object[]{usuario.toUpperCase(), nombre, apellido, estado, rol, password})) {
            DiacoUtil.crearMensajeError("Error de base de datos", "form:regionNombre", FacesMessage.SEVERITY_ERROR);
        } else {
            nombre = null;
            apellido = null;
            usuario = null;
            estado = null;
            rol = null;
            JavaScriptRunner.runScript(FacesContext.getCurrentInstance(), "dialogo.hide();");
            cargarDatos();
            exito = true;
        }
    }

    public void modificar() {
        String sql = "{call DIACO.actualizar_usuario(?,?,?,?,?)}";
        logger.info("SQL["+sql+"]");
        if (!ServiceUtil.getDaoService().callProcedure(sql, new Object[]{getSelectedRow().getId(), nombre, apellido, estado, rol})) {
            DiacoUtil.crearMensajeError("Error de base de datos", "form:regionNombre", FacesMessage.SEVERITY_ERROR);
        } else {
            nombre = null;
            apellido = null;
            usuario = null;
            estado = null;
            rol = null;
            JavaScriptRunner.runScript(FacesContext.getCurrentInstance(), "dialogo.hide();");
            cargarDatos();
            exito = true;
        }
        stateMap.setAllSelected(false);
    }

    public void eliminar() {
        String sql = "{call DIACO.eliminar_usuario(?)}";
        logger.info("SQL["+sql+"]");
        if (!ServiceUtil.getDaoService().callProcedure(sql, new Object[]{getSelectedRow().getId()})) {
            DiacoUtil.crearMensajeError("Error de base de datos", "form:regionNombre", FacesMessage.SEVERITY_ERROR);
        } else {
            nombre = null;
            apellido = null;
            usuario = null;
            estado = null;
            rol = null;
            JavaScriptRunner.runScript(FacesContext.getCurrentInstance(), "dialogo.hide();");
            cargarDatos();
            exito = true;
        }
        stateMap.setAllSelected(false);
    }

    private void clear(String form) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot uiViewRoot = facesContext.getViewRoot();
        UIComponent inputContainer = (UIComponent) uiViewRoot.findComponent(form);
        clearSubmittedValues(inputContainer);
    }

    private void clearSubmittedValues(Object obj) {
        if (obj == null || obj instanceof UIComponent == false) {
            return;
        }

        Iterator<UIComponent> chld = ((UIComponent) obj).getFacetsAndChildren();
        while (chld.hasNext()) {
            clearSubmittedValues(chld.next());
        }
        if (obj instanceof UIInput) {
            ((UIInput) obj).setSubmittedValue(null);
            ((UIInput) obj).setValue(null);
            ((UIInput) obj).setLocalValueSet(false);
            ((UIInput) obj).resetValue();
        }
    }
}
