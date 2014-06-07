package com.diaco.del.views.estadisticas;


import com.diaco.del.bo.Comercio;
import com.diaco.del.bo.Queja;
import com.diaco.del.util.ServiceUtil;
import org.apache.log4j.Logger;
import org.icefaces.ace.component.chart.Axis;
import org.icefaces.ace.component.chart.AxisType;
import org.icefaces.ace.model.chart.CartesianSeries;
import org.icefaces.ace.model.table.RowStateMap;

import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ComponentSystemEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * User: esantos
 * Date: 5/24/14
 * Time: 7:57 PM
 */

@ManagedBean(name = "estadistica")
@SessionScoped
public class EstadisticaQuejasBean {

    private final int POR_COMERCIO = 1;
    private final int POR_MUNICIPIO = 2;
    private final int POR_DEPARTAMENTO = 3;
    private final int POR_REGION = 4;

    private String opcion;

    public String getOpcion() {
        return opcion;
    }

    public void setOpcion(String opcion) {
        this.opcion = opcion;
    }

    private CartesianSeries barData;

    private Axis barDemoDefaultAxis = new Axis() {{
        setTickAngle(-30);
    }};


    private Axis barDemoXAxis = new Axis() {{
        setType(AxisType.CATEGORY);
    }};

    private Axis barDemoYAxis = new Axis() {{
        setAutoscale(true);
        setTickInterval("5");
        setLabel("Quejas");
    }};

    public CartesianSeries getBarData() {
        return barData;
    }

    public void setBarData(CartesianSeries barData) {
        this.barData = barData;
    }

    public Axis getBarDemoXAxis() {
        return barDemoXAxis;
    }

    public void setBarDemoXAxis(Axis barDemoXAxis) {
        this.barDemoXAxis = barDemoXAxis;
    }

    public Axis getBarDemoDefaultAxis() {
        return barDemoDefaultAxis;
    }

    public void setBarDemoDefaultAxis(Axis barDemoDefaultAxis) {
        this.barDemoDefaultAxis = barDemoDefaultAxis;
    }

    private Axis barDemoXTwoAxis = new Axis() {{
        setTicks(new String[] {"Nickle", "Aluminum", "Xenon", "Silver", "Sulfur", "Silicon", "Vanadium"});
        setType(AxisType.CATEGORY);
    }};

    public Axis getBarDemoXTwoAxis() {
        return barDemoXTwoAxis;
    }

    public void setBarDemoXTwoAxis(Axis barDemoXTwoAxis) {
        this.barDemoXTwoAxis = barDemoXTwoAxis;
    }

    public Axis getBarDemoYAxis() {
        return barDemoYAxis;
    }

    public void setBarDemoYAxis(Axis barDemoYAxis) {
        this.barDemoYAxis = barDemoYAxis;
    }

    private static final Logger logger = Logger.getLogger(EstadisticaQuejasBean.class);
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
                cargarDatos(POR_COMERCIO);
            }
        }
    }

    private void cargarDatos(int type) {
        logger.info("Fecha Inicial["+getFechaInicial()+"]");
        logger.info("Fecha Final["+getFechaFinal()+"]");
        quejas = ServiceUtil.getDaoService().findByFieldsBetween(Queja.class, new String[]{"fechaIncidente", "fechaIncidente"}, new Object[]{getFechaInicial(), getFechaFinal()});
        barData = new CartesianSeries();
        barData.setType(CartesianSeries.CartesianType.BAR);
        switch (type) {
            case POR_COMERCIO: {
                    List<Object[]> lista = ServiceUtil.getDaoService().findByFieldsSuma(Queja.class,
                            new String[]{"fechaIncidente", "fechaIncidente"}, new Object[]{getFechaInicial(), getFechaFinal()}, "comercio.id", "id");
                    for (Queja q: quejas) {
                        for (Object[] r : lista) {
                            if (q.getComercio().getId() == ((Integer) r[0])) {
                                barData.add(q.getComercio().getNombre(), r[1]);
                                break;
                            }
                        }
                    }
                    barData.setLabel("Comercios / Quejas");
                }
                break;
            case POR_MUNICIPIO: {
                    List<Object[]> lista = ServiceUtil.getDaoService().findStatsByMunicipios(Queja.class,
                        new String[]{"fechaIncidente", "fechaIncidente"}, new Object[]{getFechaInicial(), getFechaFinal()}, "municipio.id", "id");

                    for (Queja q: quejas) {
                        for (Object[] r : lista) {
                            if (q.getSucursal().getMunicipio().getId() == ((Integer) r[0])) {
                                barData.add(q.getSucursal().getMunicipio().getNombre(), r[1]);
                                break;
                            }
                        }
                    }
                    barData.setLabel("Municipios / Quejas");
                }
                break;
            case POR_DEPARTAMENTO: {
                    List<Object[]> lista = ServiceUtil.getDaoService().findStatsByDepartamentos(Queja.class,
                        new String[]{"fechaIncidente", "fechaIncidente"}, new Object[]{getFechaInicial(), getFechaFinal()}, "departamento.id", "id");
                    for (Queja q: quejas) {
                        for (Object[] r : lista) {
                            if (q.getSucursal().getMunicipio().getDepartamento().getId() == ((Integer) r[0])) {
                                barData.add(q.getSucursal().getMunicipio().getDepartamento().getNombre(), r[1]);
                                break;
                            }
                        }
                    }
                    barData.setLabel("Departamentos / Quejas");
                }
                break;
            case POR_REGION: {
                List<Object[]> lista = ServiceUtil.getDaoService().findStatsByRegion(Queja.class,
                        new String[]{"fechaIncidente", "fechaIncidente"}, new Object[]{getFechaInicial(), getFechaFinal()}, "region.id", "id");
                for (Queja q: quejas) {
                    for (Object[] r : lista) {
                        if (q.getSucursal().getMunicipio().getDepartamento().getRegion().getId() == ((Integer) r[0])) {
                            barData.add(q.getSucursal().getMunicipio().getDepartamento().getRegion().getNombre(), r[1]);
                            break;
                        }
                    }
                }
                barData.setLabel("Region / Quejas");
                }
        }

    }

    public void consultar() {
        int opc = POR_COMERCIO;
        if (opcion != null) {
            try {
                opc = Integer.parseInt(opcion);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        cargarDatos(opc);
    }
}