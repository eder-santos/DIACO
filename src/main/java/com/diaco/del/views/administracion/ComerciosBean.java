package com.diaco.del.views.administracion;

import com.diaco.del.bo.Categoria;
import com.diaco.del.bo.Comercio;
import com.diaco.del.util.DiacoUtil;
import com.diaco.del.util.ServiceUtil;
import com.diaco.del.views.beans.CategoriaBean;
import com.diaco.del.views.beans.ComercioBean;
import org.apache.log4j.Logger;
import org.icefaces.ace.model.table.RowStateMap;
import org.icefaces.util.JavaScriptRunner;
import org.springframework.beans.BeanUtils;

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
 * Time: 6:55 PM
 */

@ManagedBean(name = "comercio")
@SessionScoped
public class ComerciosBean {

    private static final Logger logger = Logger.getLogger(ComerciosBean.class);
    private boolean preRendered;
    private boolean exito;
    private String titulo;
    private int opcion;
    private String campoNombre;
    private String comboId;
    private ArrayList<ComercioBean> comercios;
    private ArrayList<CategoriaBean> categorias;
    private RowStateMap stateMap = new RowStateMap();

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

    public String getCampoNombre() {
        return campoNombre;
    }

    public void setCampoNombre(String campoNombre) {
        this.campoNombre = campoNombre;
    }

    public ArrayList<ComercioBean> getComercios() {
        return comercios;
    }

    public void setComercios(ArrayList<ComercioBean> comercios) {
        this.comercios = comercios;
    }

    public ArrayList<CategoriaBean> getCategorias() {
        return categorias;
    }

    public void setCategorias(ArrayList<CategoriaBean> categorias) {
        this.categorias = categorias;
    }

    public String getComboId() {
        return comboId;
    }

    public void setComboId(String comboId) {
        this.comboId = comboId;
    }

    public boolean isExito() {
        return exito;
    }

    public void setExito(boolean exito) {
        this.exito = exito;
    }

    public RowStateMap getStateMap() {
        return stateMap;
    }

    public void setStateMap(RowStateMap stateMap) {
        this.stateMap = stateMap;
    }

    public ComercioBean getSelectedRow() {
        if (stateMap.getSelected() != null && stateMap.getSelected().size() > 0)
            return (ComercioBean) stateMap.getSelected().get(0);
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
            if (comercios == null) {
                cargarDatos();
            }
        }
    }

    private void cargarDatos() {
        comercios = new ArrayList<ComercioBean>();
        List<Comercio> regs = ServiceUtil.getDaoService().findByFields(Comercio.class, null, null);
        for (Comercio reg: regs) {
            ComercioBean b = new ComercioBean();
            BeanUtils.copyProperties(reg, b, new String[]{"categoria"});
            CategoriaBean b2 = new CategoriaBean();
            BeanUtils.copyProperties(reg.getCategoria(), b2);
            b.setCategoria(b2);
            comercios.add(b);
        }
        categorias = new ArrayList<CategoriaBean>();
        List<Categoria> regs2 = ServiceUtil.getDaoService().findByFields(Categoria.class, null, null);
        for (Categoria reg: regs2) {
            CategoriaBean b = new CategoriaBean();
            BeanUtils.copyProperties(reg, b);
            categorias.add(b);
        }
    }

    public boolean isSelected() {
        return getSelectedRow() != null;
    }

    public void crea() {
        clear("form");
        opcion = 1;
        campoNombre = null;
        comboId = null;
        titulo = "Crear Comercio";
        JavaScriptRunner.runScript(FacesContext.getCurrentInstance(), "dialogo.show();");
    }

    public void edita() {
        clear("form");
        if (getSelectedRow() != null) {
            campoNombre = getSelectedRow().getNombre();
            comboId = getSelectedRow().getCategoria().getIdStr();
            opcion = 2;
            titulo = "Modificar Comercio";
            JavaScriptRunner.runScript(FacesContext.getCurrentInstance(), "dialogo.show();");
        }
    }

    public void borra() {
        clear("form");
        if (getSelectedRow() != null) {
            campoNombre = getSelectedRow().getNombre();
            comboId = getSelectedRow().getCategoria().getIdStr();
            opcion = 3;
            titulo = "Eliminar Comercio";
            JavaScriptRunner.runScript(FacesContext.getCurrentInstance(), "dialogo.show();");
        }
    }

    public void agregar() {
        String sql = "{call DIACO.ingresar_comercio(?,?)}";
        logger.info("SQL["+sql+"]");

        if (!ServiceUtil.getDaoService().callProcedure(sql, new Object[]{Integer.parseInt(comboId), campoNombre})) {
            DiacoUtil.crearMensajeError("Error de base de datos", "form:regionNombre", FacesMessage.SEVERITY_ERROR);
        } else {
            campoNombre = null;
            comboId = null;
            JavaScriptRunner.runScript(FacesContext.getCurrentInstance(), "dialogo.hide();");
            cargarDatos();
            exito = true;
        }
    }

    public void modificar() {
        String sql = "{call DIACO.actualizar_comercio(?,?,?)}";
        logger.info("SQL["+sql+"]");
        if (!ServiceUtil.getDaoService().callProcedure(sql, new Object[]{getSelectedRow().getId(), Integer.parseInt(comboId), campoNombre})) {
            DiacoUtil.crearMensajeError("Error de base de datos", "form:regionNombre", FacesMessage.SEVERITY_ERROR);
        } else {
            campoNombre = null;
            comboId = null;
            JavaScriptRunner.runScript(FacesContext.getCurrentInstance(), "dialogo.hide();");
            cargarDatos();
            exito = true;
        }
        stateMap.setAllSelected(false);
    }

    public void eliminar() {
        String sql = "{call DIACO.eliminar_comercio(?)}";
        logger.info("SQL["+sql+"]");
        if (!ServiceUtil.getDaoService().callProcedure(sql, new Object[]{getSelectedRow().getId()})) {
            DiacoUtil.crearMensajeError("Error de base de datos", "form:regionNombre", FacesMessage.SEVERITY_ERROR);
        } else {
            campoNombre = null;
            comboId = null;
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
