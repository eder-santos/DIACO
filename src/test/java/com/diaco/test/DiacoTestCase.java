package com.diaco.test;

import com.diaco.del.bo.*;
import com.diaco.del.util.ServiceUtil;
import junit.framework.TestCase;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * User: esantos
 * Date: 5/24/14
 * Time: 6:25 PM
 */

public class DiacoTestCase extends TestCase {

    private static final Logger logger = Logger.getLogger(DiacoTestCase.class);
    protected static ApplicationContext appContext;

    public void setUp(){
        if (appContext == null)
            /*appContext = new ClassPathXmlApplicationContext(new String[] {"/WEB-INF/applicationContext.xml"});*/
            appContext = new FileSystemXmlApplicationContext("file:src/main/webapp/WEB-INF/applicationContext.xml");
    }

    public void testMunicipios() {
        List<Municipio> lista = ServiceUtil.getDaoService().findByField(Municipio.class, "nombre", "Cobán");
/*        List<Municipio> lista = ServiceUtil.getDaoService().findByFields(Municipio.class, new String[] {"departamento.id"},
                new Integer[] {1});*/
        logger.info("Size[" + lista.size() + "]");
    }

    public void testRegiones() {
        List<Region> lista = ServiceUtil.getDaoService().findByFields(Region.class, null, null);
        logger.info("Size[" + lista.size() + "]");
    }

    public void testDepartamentos() {
        List<Departamento> lista = ServiceUtil.getDaoService().findByFields(Departamento.class, null, null);

        logger.info("Size[" + lista.size() + "]");
        logger.info("RegionNombre[" + lista.get(0).getRegion().getNombre() + "]");
    }

    public void testIngresarQueja() {
        String sql = "{call DIACO.ingresar_queja(?,?,?,?)}";
        ServiceUtil.getDaoService().callProcedure(sql, new Object[]{5, 3, "Probando la aplicacion", "25/05/2014"});
    }

    public void testUsuarios() {
        String usuario = "esantos";
        String password = "70f62e7cd925e9002ffb2f9016a67675";
        List<Usuario> lista = ServiceUtil.getDaoService().findByFields(Usuario.class, new String[]{"usuario", "password"}, new Object[] {usuario, password});
        logger.info("Size[" + lista.size() + "]");
    }


    public void testGroup() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MILLISECOND, 0);
        Date fechaInicial = c.getTime();
        Date fechaFinal = new Date(System.currentTimeMillis());
        List<Object[]> lista = ServiceUtil.getDaoService().findByFieldsSuma(Queja.class,
                new String[]{"fechaIncidente", "fechaIncidente"}, new Object[]{fechaInicial, fechaFinal}, "comercio.id", "id");
        logger.info("Size[" + lista.size() + "]");
        for (Object[] r : lista) {
            logger.info("Int["+r[0]+"] R["+r[1]+"]");
        }
    }

    public void testGroup2() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MILLISECOND, 0);
        Date fechaInicial = c.getTime();
        Date fechaFinal = new Date(System.currentTimeMillis());
        List<Object[]> lista = ServiceUtil.getDaoService().findStatsByMunicipios(Queja.class,
                new String[]{"fechaIncidente", "fechaIncidente"}, new Object[]{fechaInicial, fechaFinal}, "municipio.id", "id");
        for (Object[] r : lista) {
            logger.info("Int["+r[0]+"] R["+r[1]+"]");
        }
    }

    public void testGroup3() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MILLISECOND, 0);
        Date fechaInicial = c.getTime();
        Date fechaFinal = new Date(System.currentTimeMillis());
        List<Object[]> lista = ServiceUtil.getDaoService().findStatsByDepartamentos(Queja.class,
                new String[]{"fechaIncidente", "fechaIncidente"}, new Object[]{fechaInicial, fechaFinal}, "departamento.id", "id");
        for (Object[] r : lista) {
            logger.info("Int["+r[0]+"] R["+r[1]+"]");
        }
    }

    public void testGroup4() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MILLISECOND, 0);
        Date fechaInicial = c.getTime();
        Date fechaFinal = new Date(System.currentTimeMillis());
        List<Object[]> lista = ServiceUtil.getDaoService().findStatsByRegion(Queja.class,
                new String[]{"fechaIncidente", "fechaIncidente"}, new Object[]{fechaInicial, fechaFinal}, "region.id", "id");
        for (Object[] r : lista) {
            logger.info("Int["+r[0]+"] R["+r[1]+"]");
        }
    }
}
