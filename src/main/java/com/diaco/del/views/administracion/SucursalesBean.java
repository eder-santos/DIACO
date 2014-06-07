package com.diaco.del.views.administracion;

import com.diaco.del.bo.Comercio;
import com.diaco.del.bo.Departamento;
import com.diaco.del.bo.Municipio;
import com.diaco.del.bo.Sucursal;
import com.diaco.del.util.DiacoUtil;
import com.diaco.del.util.ServiceUtil;
import com.diaco.del.views.beans.*;
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
import javax.faces.event.ValueChangeEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * User: esantos
 * Date: 5/29/14
 * Time: 6:55 PM
 */

@ManagedBean(name = "sucursal")
@SessionScoped
public class SucursalesBean {

    private static final Logger logger = Logger.getLogger(SucursalesBean.class);
    private boolean preRendered;
    private boolean exito;
    private boolean showMuncipios;
    private String titulo;
    private int opcion;
    private String campoNombre;
    private String campoDire;
    private String comboId;
    private String comboId2;
    private String comboId3;
    private ArrayList<SucursalBean> sucursales;
    private ArrayList<ComercioBean> comercios;
    private ArrayList<MunicipioBean> municipios;
    private ArrayList<DepartamentoBean> departamentos;
    private List<Departamento> departamentosBo;
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

    public String getCampoDire() {
        return campoDire;
    }

    public void setCampoDire(String campoDire) {
        this.campoDire = campoDire;
    }

    public ArrayList<SucursalBean> getSucursales() {
        return sucursales;
    }

    public void setSucursales(ArrayList<SucursalBean> sucursales) {
        this.sucursales = sucursales;
    }

    public ArrayList<ComercioBean> getComercios() {
        return comercios;
    }

    public void setComercios(ArrayList<ComercioBean> comercios) {
        this.comercios = comercios;
    }

    public ArrayList<MunicipioBean> getMunicipios() {
        return municipios;
    }

    public void setMunicipios(ArrayList<MunicipioBean> municipios) {
        this.municipios = municipios;
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

    public String getComboId2() {
        return comboId2;
    }

    public void setComboId2(String comboId2) {
        this.comboId2 = comboId2;
    }

    public String getComboId3() {
        return comboId3;
    }

    public void setComboId3(String comboId3) {
        this.comboId3 = comboId3;
    }

    public boolean isExito() {
        return exito;
    }

    public void setExito(boolean exito) {
        this.exito = exito;
    }

    public boolean isShowMuncipios() {
        return showMuncipios;
    }

    public void setShowMuncipios(boolean showMuncipios) {
        this.showMuncipios = showMuncipios;
    }

    public RowStateMap getStateMap() {
        return stateMap;
    }

    public void setStateMap(RowStateMap stateMap) {
        this.stateMap = stateMap;
    }

    public SucursalBean getSelectedRow() {
        if (stateMap.getSelected() != null && stateMap.getSelected().size() > 0)
            return (SucursalBean) stateMap.getSelected().get(0);
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
            if (sucursales == null) {
                cargarDatos();
            }
        }
    }

    private void cargarDatos() {
        sucursales = new ArrayList<SucursalBean>();
        List<Sucursal> regs = ServiceUtil.getDaoService().findByFields(Sucursal.class, null, null);
        for (Sucursal reg: regs) {
            SucursalBean b = new SucursalBean();
            BeanUtils.copyProperties(reg, b, new String[]{"comercio", "municipio"});
            ComercioBean b2 = new ComercioBean();
            BeanUtils.copyProperties(reg.getComercio(), b2, new String[]{"categoria"});
            b.setComercio(b2);
            MunicipioBean b3 = new MunicipioBean();
            BeanUtils.copyProperties(reg.getMunicipio(), b3, new String[]{"departamento"});
            b.setMunicipio(b3);
            DepartamentoBean b4 = new DepartamentoBean();
            BeanUtils.copyProperties(reg.getMunicipio().getDepartamento(), b4, new String[]{"region"});
            b.getMunicipio().setDepartamento(b4);
            sucursales.add(b);
        }
        comercios = new ArrayList<ComercioBean>();
        List<Comercio> regs2 = ServiceUtil.getDaoService().findByFields(Comercio.class, null, null);
        for (Comercio reg: regs2) {
            ComercioBean b = new ComercioBean();
            BeanUtils.copyProperties(reg, b, new String[]{"categoria"});
            comercios.add(b);
        }

        departamentos = new ArrayList<DepartamentoBean>();
        departamentosBo = ServiceUtil.getDaoService().findByFields(Departamento.class, null, null);
        for (Departamento reg: departamentosBo) {
            DepartamentoBean b = new DepartamentoBean();
            BeanUtils.copyProperties(reg, b, new String[]{"region"});
            departamentos.add(b);
        }
    }

    public void departamentoListener(ValueChangeEvent event) {
        Object value = event.getNewValue();
        comboId3 = null;
        if (!isEmpty(value)) {
            Departamento found = null;
            for (Departamento dep: departamentosBo) {
                if (value.toString().equals(String.valueOf(dep.getId()))) {
                    found = dep;
                    break;
                }
            }

            if (found != null) {
                municipios = new ArrayList<MunicipioBean>();
                List<Municipio> muns = ServiceUtil.getDaoService().findByFields(Municipio.class,
                        new String[] {"departamento"}, new Object[] {found});
                for (Municipio mun: muns) {
                    MunicipioBean b = new MunicipioBean();
                    BeanUtils.copyProperties(mun, b, new String[]{"departamento"});
                    municipios.add(b);
                }
            }

            if (municipios != null && municipios.size() > 0)
                showMuncipios = true;
            else
                showMuncipios = false;
        }
    }

    private boolean isEmpty(Object value) {
        return (value == null && ("".equals(value)));
    }

    public boolean isSelected() {
        return getSelectedRow() != null;
    }

    public void crea() {
        clear("form");
        showMuncipios = false;
        opcion = 1;
        campoNombre = null;
        campoDire = null;
        comboId = null;
        comboId2 = null;
        titulo = "Crear Sucursal";
        JavaScriptRunner.runScript(FacesContext.getCurrentInstance(), "dialogo.show();");
    }

    public void edita() {
        clear("form");
        if (getSelectedRow() != null) {
            showMuncipios = true;
            campoNombre = getSelectedRow().getNombre();
            comboId = getSelectedRow().getComercio().getIdStr();
            comboId2 = getSelectedRow().getMunicipio().getDepartamento().getIdStr();
            comboId3 = getSelectedRow().getMunicipio().getIdStr();
            campoDire = getSelectedRow().getDireccion();

            Departamento found = null;
            for (Departamento dep: departamentosBo) {
                if (comboId2.equals(String.valueOf(dep.getId()))) {
                    found = dep;
                    break;
                }
            }

            if (found != null) {
                municipios = new ArrayList<MunicipioBean>();
                List<Municipio> muns = ServiceUtil.getDaoService().findByFields(Municipio.class,
                        new String[] {"departamento"}, new Object[] {found});
                for (Municipio mun: muns) {
                    MunicipioBean b = new MunicipioBean();
                    BeanUtils.copyProperties(mun, b, new String[]{"departamento"});
                    municipios.add(b);
                }
            }
            opcion = 2;
            titulo = "Modificar Sucursal";
            JavaScriptRunner.runScript(FacesContext.getCurrentInstance(), "dialogo.show();");
        }
    }

    public void borra() {
        clear("form");
        if (getSelectedRow() != null) {
            showMuncipios = true;
            campoNombre = getSelectedRow().getNombre();
            comboId = getSelectedRow().getComercio().getIdStr();
            comboId2 = getSelectedRow().getMunicipio().getDepartamento().getIdStr();
            comboId3 = getSelectedRow().getMunicipio().getIdStr();
            campoDire = getSelectedRow().getDireccion();

            Departamento found = null;
            for (Departamento dep: departamentosBo) {
                if (comboId2.equals(String.valueOf(dep.getId()))) {
                    found = dep;
                    break;
                }
            }

            if (found != null) {
                municipios = new ArrayList<MunicipioBean>();
                List<Municipio> muns = ServiceUtil.getDaoService().findByFields(Municipio.class,
                        new String[] {"departamento"}, new Object[] {found});
                for (Municipio mun: muns) {
                    MunicipioBean b = new MunicipioBean();
                    BeanUtils.copyProperties(mun, b, new String[]{"departamento"});
                    municipios.add(b);
                }
            }
            opcion = 3;
            titulo = "Eliminar Sucursal";
            JavaScriptRunner.runScript(FacesContext.getCurrentInstance(), "dialogo.show();");
        }
    }

    public void agregar() {
        if (comboId3 == null) {
            DiacoUtil.crearMensajeError("Debe seleccionar un municipio", "form:regionNombre", FacesMessage.SEVERITY_ERROR);
            return;
        }

        String sql = "{call DIACO.ingresar_sucursal(?,?,?,?)}";
        logger.info("SQL["+sql+"]");

        if (!ServiceUtil.getDaoService().callProcedure(sql, new Object[]{Integer.parseInt(comboId), Integer.parseInt(comboId3),
                campoNombre, campoDire})) {
            DiacoUtil.crearMensajeError("Error de base de datos", "form:regionNombre", FacesMessage.SEVERITY_ERROR);
        } else {
            campoNombre = null;
            campoDire = null;
            comboId = null;
            comboId2 = null;
            comboId3 = null;
            JavaScriptRunner.runScript(FacesContext.getCurrentInstance(), "dialogo.hide();");
            cargarDatos();
            exito = true;
        }
    }

    public void modificar() {
        if (comboId3 == null) {
            DiacoUtil.crearMensajeError("Debe seleccionar un municipio", "form:regionNombre", FacesMessage.SEVERITY_ERROR);
            return;
        }

        String sql = "{call DIACO.actualizar_sucursal(?,?,?,?,?)}";
        logger.info("SQL["+sql+"]");
        if (!ServiceUtil.getDaoService().callProcedure(sql, new Object[]{getSelectedRow().getId(), Integer.parseInt(comboId),
            Integer.parseInt(comboId3), campoNombre, campoDire})) {
            DiacoUtil.crearMensajeError("Error de base de datos", "form:regionNombre", FacesMessage.SEVERITY_ERROR);
        } else {
            campoNombre = null;
            campoDire = null;
            comboId = null;
            comboId2 = null;
            comboId3 = null;
            JavaScriptRunner.runScript(FacesContext.getCurrentInstance(), "dialogo.hide();");
            cargarDatos();
            exito = true;
        }
        stateMap.setAllSelected(false);
    }

    public void eliminar() {
        String sql = "{call DIACO.eliminar_sucursal(?)}";
        logger.info("SQL["+sql+"]");
        if (!ServiceUtil.getDaoService().callProcedure(sql, new Object[]{getSelectedRow().getId()})) {
            DiacoUtil.crearMensajeError("Error de base de datos", "form:regionNombre", FacesMessage.SEVERITY_ERROR);
        } else {
            campoNombre = null;
            campoDire = null;
            comboId = null;
            comboId2 = null;
            comboId3 = null;
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
