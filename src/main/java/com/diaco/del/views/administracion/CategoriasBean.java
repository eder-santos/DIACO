package com.diaco.del.views.administracion;

import com.diaco.del.bo.Categoria;
import com.diaco.del.util.DiacoUtil;
import com.diaco.del.util.ServiceUtil;
import com.diaco.del.views.beans.CategoriaBean;
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
 * Time: 6:03 PM
 */

@ManagedBean(name = "categoria")
@SessionScoped
public class CategoriasBean {

    private static final Logger logger = Logger.getLogger(CategoriasBean.class);
    private boolean preRendered;
    private boolean exito;
    private String titulo;
    private int opcion;
    private String categoriaCampo;
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

    public String getCategoriaCampo() {
        return categoriaCampo;
    }

    public void setCategoriaCampo(String categoriaCampo) {
        this.categoriaCampo = categoriaCampo;
    }

    public boolean isExito() {
        return exito;
    }

    public void setExito(boolean exito) {
        this.exito = exito;
    }

    public ArrayList<CategoriaBean> getCategorias() {
        return categorias;
    }

    public void setCategorias(ArrayList<CategoriaBean> categorias) {
        this.categorias = categorias;
    }

    public RowStateMap getStateMap() {
        return stateMap;
    }

    public void setStateMap(RowStateMap stateMap) {
        this.stateMap = stateMap;
    }

    public CategoriaBean getSelectedRow() {
        if (stateMap.getSelected() != null && stateMap.getSelected().size() > 0)
            return (CategoriaBean) stateMap.getSelected().get(0);
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
            if (categorias == null) {
                cargarDatos();
            }
        }
    }

    private void cargarDatos() {
        categorias = new ArrayList<CategoriaBean>();
        List<Categoria> regs = ServiceUtil.getDaoService().findByFields(Categoria.class, null, null);
        for (Categoria reg: regs) {
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
        titulo = "Crear Categoria";
        JavaScriptRunner.runScript(FacesContext.getCurrentInstance(), "dialogo.show();");
    }

    public void edita() {
        clear("form");
        if (getSelectedRow() != null) {
            categoriaCampo = getSelectedRow().getNombre();
            opcion = 2;
            titulo = "Modificar Categoria";
            JavaScriptRunner.runScript(FacesContext.getCurrentInstance(), "dialogo.show();");
        }
    }

    public void borra() {
        clear("form");
        if (getSelectedRow() != null) {
            categoriaCampo = getSelectedRow().getNombre();
            opcion = 3;
            titulo = "Eliminar Categoria";
            JavaScriptRunner.runScript(FacesContext.getCurrentInstance(), "dialogo.show();");
        }
    }

    public void agregar() {
        String sql = "{call DIACO.ingresar_categoria(?)}";
        logger.info("SQL[" + sql + "]");
        if (!ServiceUtil.getDaoService().callProcedure(sql, new Object[]{categoriaCampo})) {
            DiacoUtil.crearMensajeError("Error de base de datos", "form:regionNombre", FacesMessage.SEVERITY_ERROR);
        } else {
            categoriaCampo = null;
            JavaScriptRunner.runScript(FacesContext.getCurrentInstance(), "dialogo.hide();");
            cargarDatos();
            exito = true;
        }
    }

    public void modificar() {
        String sql = "{call DIACO.actualizar_categoria(?,?)}";
        logger.info("SQL["+sql+"]");
        if (!ServiceUtil.getDaoService().callProcedure(sql, new Object[]{getSelectedRow().getId(), categoriaCampo})) {
            DiacoUtil.crearMensajeError("Error de base de datos", "form:regionNombre", FacesMessage.SEVERITY_ERROR);
        } else {
            categoriaCampo = null;
            JavaScriptRunner.runScript(FacesContext.getCurrentInstance(), "dialogo.hide();");
            cargarDatos();
            exito = true;
        }
        stateMap.setAllSelected(false);
    }

    public void eliminar() {
        String sql = "{call DIACO.eliminar_categoria(?)}";
        logger.info("SQL["+sql+"]");
        if (!ServiceUtil.getDaoService().callProcedure(sql, new Object[]{getSelectedRow().getId()})) {
            DiacoUtil.crearMensajeError("Error de base de datos", "form:regionNombre", FacesMessage.SEVERITY_ERROR);
        } else {
            categoriaCampo = null;
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
