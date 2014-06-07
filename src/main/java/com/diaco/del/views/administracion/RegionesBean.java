package com.diaco.del.views.administracion;

import com.diaco.del.bo.Region;
import com.diaco.del.util.DiacoUtil;
import com.diaco.del.util.ServiceUtil;
import com.diaco.del.views.beans.RegionBean;
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
import javax.faces.event.ActionEvent;
import javax.faces.event.ComponentSystemEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * User: esantos
 * Date: 5/25/14
 * Time: 9:50 PM
 */

@ManagedBean(name = "region")
@SessionScoped
public class RegionesBean {

    private static final Logger logger = Logger.getLogger(RegionesBean.class);
    private boolean preRendered;
    private boolean exito;
    private String titulo;
    private int opcion;
    private String regionCampo;
    private ArrayList<RegionBean> regiones;
    private RowStateMap stateMap = new RowStateMap();

    public int getOpcion() {
        return opcion;
    }

    public void setOpcion(int opcion) {
        this.opcion = opcion;
    }

    public ArrayList<RegionBean> getRegiones() {
        return regiones;
    }

    public void setRegiones(ArrayList<RegionBean> regiones) {
        this.regiones = regiones;
    }

    public RowStateMap getStateMap() {
        return stateMap;
    }

    public void setStateMap(RowStateMap stateMap) {
        this.stateMap = stateMap;
    }

    public boolean isSelected() {
        return getSelectedRegion() != null;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public RegionBean getSelectedRegion() {
        if (stateMap.getSelected() != null && stateMap.getSelected().size() > 0)
            return (RegionBean) stateMap.getSelected().get(0);
        else
            return null;
    }

    public String getRegionCampo() {
        return regionCampo;
    }

    public void setRegionCampo(String regionCampo) {
        this.regionCampo = regionCampo;
    }

    public boolean isExito() {
        return exito;
    }

    public void setExito(boolean exito) {
        this.exito = exito;
    }

    @PreDestroy
    public void destroy() {
        preRendered = false;
    }

    public void preRender(ComponentSystemEvent event) {
        if (!preRendered) {
            preRendered = true;
            if (regiones == null) {
                cargarDatos();
            }
        }
    }

    private void cargarDatos() {
        regiones = new ArrayList<RegionBean>();
        List<Region> regs = ServiceUtil.getDaoService().findByFields(Region.class, null, null);
        for (Region reg: regs) {
            RegionBean b = new RegionBean();
            BeanUtils.copyProperties(reg, b);
            regiones.add(b);
        }
    }

    public void crea() {
        clear("form");
        opcion = 1;
        titulo = "Crear Region";
        JavaScriptRunner.runScript(FacesContext.getCurrentInstance(), "dialogo.show();");
    }

    public void edita() {
        clear("form");
        if (getSelectedRegion() != null) {
            regionCampo = getSelectedRegion().getNombre();
            opcion = 2;
            titulo = "Modificar Region";
            JavaScriptRunner.runScript(FacesContext.getCurrentInstance(), "dialogo.show();");
        }
    }

    public void borra() {
        clear("form");
        if (getSelectedRegion() != null) {
            regionCampo = getSelectedRegion().getNombre();
            opcion = 3;
            titulo = "Eliminar Region";
            JavaScriptRunner.runScript(FacesContext.getCurrentInstance(), "dialogo.show();");
        }
    }

    public void agregar() {
        String sql = "{call DIACO.ingresar_region(?)}";
        logger.info("SQL[" + sql + "]");
        if (!ServiceUtil.getDaoService().callProcedure(sql, new Object[]{regionCampo})) {
            DiacoUtil.crearMensajeError("Error de base de datos", "form:regionNombre", FacesMessage.SEVERITY_ERROR);
        } else {
            regionCampo = null;
            JavaScriptRunner.runScript(FacesContext.getCurrentInstance(), "dialogo.hide();");
            cargarDatos();
            exito = true;
        }
    }

    public void modificar() {
        String sql = "{call DIACO.actualizar_region(?,?)}";
        logger.info("SQL["+sql+"]");
        if (!ServiceUtil.getDaoService().callProcedure(sql, new Object[]{getSelectedRegion().getId(), regionCampo})) {
            DiacoUtil.crearMensajeError("Error de base de datos", "form:regionNombre", FacesMessage.SEVERITY_ERROR);
        } else {
            regionCampo = null;
            JavaScriptRunner.runScript(FacesContext.getCurrentInstance(), "dialogo.hide();");
            cargarDatos();
            exito = true;
        }
        stateMap.setAllSelected(false);
    }

    public void eliminar() {
        String sql = "{call DIACO.eliminar_region(?)}";
        logger.info("SQL["+sql+"]");
        if (!ServiceUtil.getDaoService().callProcedure(sql, new Object[]{getSelectedRegion().getId()})) {
            DiacoUtil.crearMensajeError("Error de base de datos", "form:regionNombre", FacesMessage.SEVERITY_ERROR);
        } else {
            regionCampo = null;
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

        List<FacesMessage> globals = (List<FacesMessage>) facesContext.getViewRoot().getAttributes().get("org.icefaces.event.saved_global_faces_messages");
        if (globals != null) {
            globals.clear();
        }

        Iterator iterator = FacesContext.getCurrentInstance().getMessages();
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
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
