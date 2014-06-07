package com.diaco.del.views.administracion;

import com.diaco.del.bo.Departamento;
import com.diaco.del.bo.Region;
import com.diaco.del.util.DiacoUtil;
import com.diaco.del.util.ServiceUtil;
import com.diaco.del.views.beans.DepartamentoBean;
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
import javax.faces.event.ComponentSystemEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * User: esantos
 * Date: 5/29/14
 * Time: 6:55 PM
 */

@ManagedBean(name = "departamento")
@SessionScoped
public class DepartamentosBean {

    private static final Logger logger = Logger.getLogger(DepartamentosBean.class);
    private boolean preRendered;
    private boolean exito;
    private String titulo;
    private int opcion;
    private String departamentoCampo;
    private String comboId;
    private ArrayList<DepartamentoBean> departamentos;
    private ArrayList<RegionBean> regiones;
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

    public String getDepartamentoCampo() {
        return departamentoCampo;
    }

    public void setDepartamentoCampo(String departamentoCampo) {
        this.departamentoCampo = departamentoCampo;
    }

    public ArrayList<RegionBean> getRegiones() {
        return regiones;
    }

    public void setRegiones(ArrayList<RegionBean> regiones) {
        this.regiones = regiones;
    }

    public String getComboId() {
        return comboId;
    }

    public void setComboId(String comboId) {
        this.comboId = comboId;
    }

    public ArrayList<DepartamentoBean> getDepartamentos() {
        return departamentos;
    }

    public void setDepartamentos(ArrayList<DepartamentoBean> departamentos) {
        this.departamentos = departamentos;
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

    public DepartamentoBean getSelectedRow() {
        if (stateMap.getSelected() != null && stateMap.getSelected().size() > 0)
            return (DepartamentoBean) stateMap.getSelected().get(0);
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
            if (departamentos == null) {
                cargarDatos();
            }
        }
    }

    private void cargarDatos() {
        departamentos = new ArrayList<DepartamentoBean>();
        List<Departamento> regs = ServiceUtil.getDaoService().findByFields(Departamento.class, null, null);
        for (Departamento reg: regs) {
            DepartamentoBean b = new DepartamentoBean();
            BeanUtils.copyProperties(reg, b, new String[]{"region"});
            RegionBean b2 = new RegionBean();
            BeanUtils.copyProperties(reg.getRegion(), b2);
            b.setRegion(b2);
            departamentos.add(b);
        }
        regiones = new ArrayList<RegionBean>();
        List<Region> regs2 = ServiceUtil.getDaoService().findByFields(Region.class, null, null);
        for (Region reg: regs2) {
            RegionBean b = new RegionBean();
            BeanUtils.copyProperties(reg, b);
            regiones.add(b);
        }
    }

    public boolean isSelected() {
        return getSelectedRow() != null;
    }

    public void crea() {
        clear("form");
        opcion = 1;
        departamentoCampo = null;
        comboId = null;
        titulo = "Crear Departamento";
        JavaScriptRunner.runScript(FacesContext.getCurrentInstance(), "dialogo.show();");
    }

    public void edita() {
        clear("form");
        if (getSelectedRow() != null) {
            departamentoCampo = getSelectedRow().getNombre();
            comboId = getSelectedRow().getRegion().getIdStr();
            opcion = 2;
            titulo = "Modificar Departamento";
            JavaScriptRunner.runScript(FacesContext.getCurrentInstance(), "dialogo.show();");
        }
    }

    public void borra() {
        clear("form");
        if (getSelectedRow() != null) {
            departamentoCampo = getSelectedRow().getNombre();
            comboId = getSelectedRow().getRegion().getIdStr();
            opcion = 3;
            titulo = "Eliminar Departamento";
            JavaScriptRunner.runScript(FacesContext.getCurrentInstance(), "dialogo.show();");
        }
    }

    public void agregar() {
        String sql = "{call DIACO.ingresar_departamento(?,?)}";
        logger.info("SQL["+sql+"]");

        if (!ServiceUtil.getDaoService().callProcedure(sql, new Object[]{Integer.parseInt(comboId), departamentoCampo})) {
            DiacoUtil.crearMensajeError("Error de base de datos", "form:regionNombre", FacesMessage.SEVERITY_ERROR);
        } else {
            departamentoCampo = null;
            comboId = null;
            JavaScriptRunner.runScript(FacesContext.getCurrentInstance(), "dialogo.hide();");
            cargarDatos();
            exito = true;
        }
    }

    public void modificar() {
        String sql = "{call DIACO.actualizar_departamento(?,?,?)}";
        logger.info("SQL["+sql+"]");
        if (!ServiceUtil.getDaoService().callProcedure(sql, new Object[]{getSelectedRow().getId(), Integer.parseInt(comboId), departamentoCampo})) {
            DiacoUtil.crearMensajeError("Error de base de datos", "form:regionNombre", FacesMessage.SEVERITY_ERROR);
        } else {
            departamentoCampo = null;
            comboId = null;
            JavaScriptRunner.runScript(FacesContext.getCurrentInstance(), "dialogo.hide();");
            cargarDatos();
            exito = true;
        }
        stateMap.setAllSelected(false);
    }

    public void eliminar() {
        String sql = "{call DIACO.eliminar_departamento(?)}";
        logger.info("SQL["+sql+"]");
        if (!ServiceUtil.getDaoService().callProcedure(sql, new Object[]{getSelectedRow().getId()})) {
            DiacoUtil.crearMensajeError("Error de base de datos", "form:regionNombre", FacesMessage.SEVERITY_ERROR);
        } else {
            departamentoCampo = null;
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