package com.diaco.del.views.administracion;

import com.diaco.del.bo.Departamento;
import com.diaco.del.bo.Municipio;
import com.diaco.del.util.DiacoUtil;
import com.diaco.del.util.ServiceUtil;
import com.diaco.del.views.beans.DepartamentoBean;
import com.diaco.del.views.beans.MunicipioBean;
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

@ManagedBean(name = "municipio")
@SessionScoped
public class MunicipiosBean {

    private static final Logger logger = Logger.getLogger(MunicipiosBean.class);
    private boolean preRendered;
    private boolean exito;
    private String titulo;
    private int opcion;
    private String campoNombre;
    private String comboId;
    private ArrayList<MunicipioBean> municipios;
    private ArrayList<DepartamentoBean> departamentos;
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

    public ArrayList<DepartamentoBean> getDepartamentos() {
        return departamentos;
    }

    public void setDepartamentos(ArrayList<DepartamentoBean> departamentos) {
        this.departamentos = departamentos;
    }

    public String getComboId() {
        return comboId;
    }

    public void setComboId(String comboId) {
        this.comboId = comboId;
    }

    public ArrayList<MunicipioBean> getMunicipios() {
        return municipios;
    }

    public void setMunicipios(ArrayList<MunicipioBean> municipios) {
        this.municipios = municipios;
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

    public MunicipioBean getSelectedRow() {
        if (stateMap.getSelected() != null && stateMap.getSelected().size() > 0)
            return (MunicipioBean) stateMap.getSelected().get(0);
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
            if (municipios == null) {
                cargarDatos();
            }
        }
    }

    private void cargarDatos() {
        municipios = new ArrayList<MunicipioBean>();
        List<Municipio> regs = ServiceUtil.getDaoService().findByFields(Municipio.class, null, null);
        for (Municipio reg: regs) {
            MunicipioBean b = new MunicipioBean();
            BeanUtils.copyProperties(reg, b, new String[]{"departamento"});
            DepartamentoBean b2 = new DepartamentoBean();
            BeanUtils.copyProperties(reg.getDepartamento(), b2, new String[]{"region"});
            b.setDepartamento(b2);
            municipios.add(b);
        }
        departamentos = new ArrayList<DepartamentoBean>();
        List<Departamento> regs2 = ServiceUtil.getDaoService().findByFields(Departamento.class, null, null);
        for (Departamento reg: regs2) {
            DepartamentoBean b = new DepartamentoBean();
            BeanUtils.copyProperties(reg, b,new String[]{"region"});
            departamentos.add(b);
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
        titulo = "Crear Municipios";
        JavaScriptRunner.runScript(FacesContext.getCurrentInstance(), "dialogo.show();");
    }

    public void edita() {
        clear("form");
        if (getSelectedRow() != null) {
            campoNombre = getSelectedRow().getNombre();
            comboId = getSelectedRow().getDepartamento().getIdStr();
            opcion = 2;
            titulo = "Modificar Municipios";
            JavaScriptRunner.runScript(FacesContext.getCurrentInstance(), "dialogo.show();");
        }
    }

    public void borra() {
        clear("form");
        if (getSelectedRow() != null) {
            campoNombre = getSelectedRow().getNombre();
            comboId = getSelectedRow().getDepartamento().getIdStr();
            opcion = 3;
            titulo = "Eliminar Municipios";
            JavaScriptRunner.runScript(FacesContext.getCurrentInstance(), "dialogo.show();");
        }
    }

    public void agregar() {
        String sql = "{call DIACO.ingresar_municipio(?,?)}";
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
        String sql = "{call DIACO.actualizar_municipio(?,?,?)}";
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
        String sql = "{call DIACO.eliminar_municipio(?)}";
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
