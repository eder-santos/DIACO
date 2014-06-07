package com.diaco.del.views.estadisticas;

import com.diaco.del.bo.Queja;
import com.diaco.del.util.ServiceUtil;
import org.apache.log4j.Logger;
import org.icefaces.ace.model.table.RowStateMap;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ComponentSystemEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * User: esantos
 * Date: 5/24/14
 * Time: 7:57 PM
 */

@ManagedBean(name = "consulta")
@SessionScoped
public class ConsultaQuejasBean {

    private static final Logger logger = Logger.getLogger(ConsultaQuejasBean.class);
    private boolean preRendered;
    private Date fechaInicial;
    private Date fechaFinal = new Date(System.currentTimeMillis());
    private Date fechaMaxima = new Date(System.currentTimeMillis());
    private List<Queja> quejas;
    private RowStateMap stateMap = new RowStateMap();

    public Date getFechaInicial() {
        if (fechaInicial == null) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.DAY_OF_MONTH, 1);
            c.set(Calendar.HOUR, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MILLISECOND, 0);
            fechaInicial = c.getTime();
        }
        return fechaInicial;
    }

    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public Date getFechaMaxima() {
        return fechaMaxima;
    }

    public void setFechaMaxima(Date fechaMaxima) {
        this.fechaMaxima = fechaMaxima;
    }

    public List<Queja> getQuejas() {
        return quejas;
    }

    public void setQuejas(List<Queja> quejas) {
        this.quejas = quejas;
    }

    public RowStateMap getStateMap() {
        return stateMap;
    }

    public void setStateMap(RowStateMap stateMap) {
        this.stateMap = stateMap;
    }

    @PreDestroy
    public void destroy() {
        preRendered = false;
    }

    public void preRender(ComponentSystemEvent event) {
        if (!preRendered) {
            preRendered = true;
            if (quejas == null) {
                cargarDatos();
            }
        }
    }

    private void cargarDatos() {
        logger.info("Fecha Inicial["+getFechaInicial()+"]");
        logger.info("Fecha Final["+getFechaFinal()+"]");
        quejas = ServiceUtil.getDaoService().findByFieldsBetween(Queja.class, new String[]{"fechaIncidente", "fechaIncidente"}, new Object[]{getFechaInicial(), getFechaFinal()});
    }

    public void consultar() {
        cargarDatos();
    }
}