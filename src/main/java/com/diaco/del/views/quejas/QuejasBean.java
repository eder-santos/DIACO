package com.diaco.del.views.quejas;

import com.diaco.del.bo.*;
import com.diaco.del.util.CaptchaDiaco;
import com.diaco.del.util.DiacoUtil;
import com.diaco.del.util.ServiceUtil;
import com.diaco.del.views.beans.*;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: esantos
 * Date: 5/23/14
 * Time: 10:48 PM
 */

@ManagedBean(name = "quejas")
@SessionScoped
public class QuejasBean {

    private static final Logger logger = Logger.getLogger(QuejasBean.class);
    private boolean preRendered;
    private boolean showMunicipios;
    private boolean showCategorias;
    private boolean showComercios;
    private boolean showSucursal;
    private boolean showDetalles;
    private boolean exito;
    private String departamento = "";
    private String municipio = "";
    private String categoria = "";
    private String comercio = "";
    private String sucursal = "";
    private String quejaDetalle = "";
    private String captchaTexto = "";
    private Date fechaIncidente = new Date(System.currentTimeMillis());
    private Date fechaMaxima = new Date(System.currentTimeMillis());
    private ArrayList<DepartamentoBean> departamentos;
    private List<Departamento> departamentosBo;
    private ArrayList<MunicipioBean> municipios;
    private List<Municipio> municipiosBo;
    private ArrayList<CategoriaBean> categorias;
    private List<Categoria> categoriasBo;
    private ArrayList<ComercioBean> comercios;
    private List<Comercio> comerciosBo;
    private ArrayList<SucursalBean> sucursales;

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public ArrayList<DepartamentoBean> getDepartamentos() {
        return departamentos;
    }

    public void setDepartamentos(ArrayList<DepartamentoBean> departamentos) {
        this.departamentos = departamentos;
    }

    public ArrayList<MunicipioBean> getMunicipios() {
        return municipios;
    }

    public void setMunicipios(ArrayList<MunicipioBean> municipios) {
        this.municipios = municipios;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public ArrayList<CategoriaBean> getCategorias() {
        return categorias;
    }

    public void setCategorias(ArrayList<CategoriaBean> categorias) {
        this.categorias = categorias;
    }

    public String getComercio() {
        return comercio;
    }

    public void setComercio(String comercio) {
        this.comercio = comercio;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public ArrayList<ComercioBean> getComercios() {
        return comercios;
    }

    public void setComercios(ArrayList<ComercioBean> comercios) {
        this.comercios = comercios;
    }

    public ArrayList<SucursalBean> getSucursales() {
        return sucursales;
    }

    public void setSucursales(ArrayList<SucursalBean> sucursales) {
        this.sucursales = sucursales;
    }

    public Date getFechaIncidente() {
        return fechaIncidente;
    }

    public void setFechaIncidente(Date fechaIncidente) {
        this.fechaIncidente = fechaIncidente;
    }

    public Date getFechaMaxima() {
        return fechaMaxima;
    }

    public void setFechaMaxima(Date fechaMaxima) {
        this.fechaMaxima = fechaMaxima;
    }

    public String getCaptchaTexto() {
        return captchaTexto;
    }

    public void setCaptchaTexto(String captchaTexto) {
        this.captchaTexto = captchaTexto;
    }

    public String getQuejaDetalle() {
        return quejaDetalle;
    }

    public void setQuejaDetalle(String quejaDetalle) {
        this.quejaDetalle = quejaDetalle;
    }

    public boolean isShowMunicipios() {
        return showMunicipios;
    }

    public void setShowMunicipios(boolean showMunicipios) {
        this.showMunicipios = showMunicipios;
    }

    public boolean isShowCategorias() {
        return showCategorias;
    }

    public void setShowCategorias(boolean showCategorias) {
        this.showCategorias = showCategorias;
    }

    public boolean isShowComercios() {
        return showComercios;
    }

    public void setShowComercios(boolean showComercios) {
        this.showComercios = showComercios;
    }

    public boolean isShowSucursal() {
        return showSucursal;
    }

    public void setShowSucursal(boolean showSucursal) {
        this.showSucursal = showSucursal;
    }

    public boolean isShowDetalles() {
        return showDetalles;
    }

    public void setShowDetalles(boolean showDetalles) {
        this.showDetalles = showDetalles;
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
            if (departamentos == null) {
                departamentos = new ArrayList<DepartamentoBean>();
                departamentosBo = ServiceUtil.getDaoService().findByFields(Departamento.class, null, null);
                for (Departamento dep: departamentosBo) {
                    DepartamentoBean b = new DepartamentoBean();
                    BeanUtils.copyProperties(dep, b, new String[]{"region"});
                    departamentos.add(b);
                }
            }
        }
    }

    public void departamentoListener(ValueChangeEvent event) {
        Object value = event.getNewValue();
        if (!isEmpty(value)) {
            Departamento found = null;
            for (Departamento obj: departamentosBo) {
                if (value.toString().equals(String.valueOf(obj.getId()))) {
                    found = obj;
                    break;
                }
            }

            if (found != null) {
                municipios = new ArrayList<MunicipioBean>();
                municipiosBo = ServiceUtil.getDaoService().findByFields(Municipio.class,
                        new String[] {"departamento"}, new Object[] {found});
                for (Municipio mun: municipiosBo) {
                    MunicipioBean b = new MunicipioBean();
                    BeanUtils.copyProperties(mun, b, new String[]{"departamento"});
                    municipios.add(b);
                }
                showMunicipios = true;
                municipio = "";
                categoria = "";
                comercio = "";
                sucursal = "";
            }
        }
    }

    public void municipioListener(ValueChangeEvent event) {
        Object value = event.getNewValue();
        if (!isEmpty(value)) {
            categorias = new ArrayList<CategoriaBean>();
            categoriasBo = ServiceUtil.getDaoService().findByFields(Categoria.class, null, null);
            for (Categoria cat: categoriasBo) {
                CategoriaBean b = new CategoriaBean();
                BeanUtils.copyProperties(cat, b);
                categorias.add(b);
            }
            showCategorias = true;
            categoria = "";
            comercio = "";
            sucursal = "";
        }
    }

    public void categoriasListener(ValueChangeEvent event) {
        Object value = event.getNewValue();
        if (!isEmpty(value)) {
            Categoria found = null;
            for (Categoria obj: categoriasBo) {
                if (value.toString().equals(String.valueOf(obj.getId()))) {
                    found = obj;
                    break;
                }
            }

            if (found != null) {
                comercios = new ArrayList<ComercioBean>();
                comerciosBo = ServiceUtil.getDaoService().findByFields(Comercio.class,
                        new String[] {"categoria"}, new Object[] {found});
                for (Comercio com: comerciosBo) {
                    ComercioBean b = new ComercioBean();
                    BeanUtils.copyProperties(com, b, new String[]{"categoria"});
                    CategoriaBean b2 = new CategoriaBean();
                    BeanUtils.copyProperties(com.getCategoria(), b2);
                    b.setCategoria(b2);
                    comercios.add(b);
                }
                showComercios = true;
                comercio = "";
                sucursal = "";
            }
        }
    }

    public void comercioListener(ValueChangeEvent event) {
        Object value = event.getNewValue();
        if (!isEmpty(value)) {
            Comercio found1 = null;
            for (Comercio obj: comerciosBo) {
                if (value.toString().equals(String.valueOf(obj.getId()))) {
                    found1 = obj;
                    break;
                }
            }

            Municipio found2 = null;
            for (Municipio obj: municipiosBo) {
                if (municipio.equals(String.valueOf(obj.getId()))) {
                    found2 = obj;
                    break;
                }
            }

            if (found1 != null && found2 != null) {
                sucursales = new ArrayList<SucursalBean>();
                List<Sucursal> sucs = ServiceUtil.getDaoService().findByFields(Sucursal.class,
                        new String[] {"comercio", "municipio"},
                        new Object[] {found1, found2});
                for (Sucursal suc: sucs) {
                    SucursalBean b = new SucursalBean();
                    BeanUtils.copyProperties(suc, b, new String[]{"comercio", "municipio"});
                    sucursales.add(b);
                }
                showSucursal = true;
                sucursal = "";
            }
        }
    }

    public void sucursalListener(ValueChangeEvent event) {
        Object value = event.getNewValue();
        if (!isEmpty(value)) {
            showDetalles = true;
        }
    }

    private boolean isEmpty(Object value) {
        return (value == null || ("".equals(value.toString().trim())));
    }

    public void ingresarQueja() {
        if (isEmpty(departamento) || isEmpty(categoria)) {
            return;
        }

        if (isEmpty(comercio)) {
            DiacoUtil.crearMensajeError("Debe seleccionar un comercio", "form:quejaDet", FacesMessage.SEVERITY_ERROR);
            return;
        }

        if (isEmpty(sucursal)) {
            DiacoUtil.crearMensajeError("Debe seleccionar una sucursal", "form:quejaDet", FacesMessage.SEVERITY_ERROR);
            return;
        }

        if (isEmpty(quejaDetalle)) {
            DiacoUtil.crearMensajeError("Debe ingresar el detalle de la queja", "form:quejaDet", FacesMessage.SEVERITY_ERROR);
            return;
        }

        if (fechaIncidente == null) {
            DiacoUtil.crearMensajeError("Debe ingresar la fecha del incidente", "form:quejaDet", FacesMessage.SEVERITY_ERROR);
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String fecha = sdf.format(fechaIncidente);
        logger.info("Ingresando Queja");
        logger.info("Comercio["+comercio+"]");
        logger.info("Sucursal["+sucursal+"]");
        logger.info("Detalle["+quejaDetalle+"]");
        logger.info("FechaIncidente["+fecha+"]");

        String sql = "{call DIACO.ingresar_queja(?,?,?,?)}";
        logger.info("SQL["+sql+"]");

        if (!ServiceUtil.getDaoService().callProcedure(sql, new Object[]{Integer.parseInt(comercio), Integer.parseInt(sucursal), quejaDetalle, fecha})) {
            FacesMessage msg = new FacesMessage();
            msg.setDetail("Error de base de datos");
            msg.setSummary("Error de base de datos");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("form:quejaDet", msg);
        } else {
            exito = true;
        }
    }

    public void ingresarNueva() {
        limpiar();
    }

    private void limpiar() {
        showMunicipios = false;
        showCategorias = false;
        showComercios = false;
        showSucursal = false;
        showDetalles = false;
        exito = false;
        departamento = "";
        municipio = "";
        categoria = "";
        comercio = "";
        sucursal = "";
        quejaDetalle = "";
        captchaTexto = "";
    }
    //TODO: Generar nuevo captcha
}