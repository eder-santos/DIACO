package com.diaco.del.dao;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.List;

/**
 * User: esantos
 * Date: 5/24/14
 * Time: 11:16 AM
 */

@Service
public class DaoServiceImpl implements DaoService {
    private final static byte[] keylock = new byte[0];
    private static final Logger logger = Logger.getLogger(DaoServiceImpl.class);

    @Autowired(required=true)
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public <T> List<T> findByField(Class objClass, String field, Object value) {
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(objClass);
        crit.add(Restrictions.eq(field, value));
        //noinspection unchecked
        return crit.list();
    }

    @Transactional
    public <T> List<T> findByFields(Class objClass, String[] fields, Object[] values) {
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(objClass);
        if (fields != null)
            for (int i = 0; i < fields.length; i++)
                crit.add(Restrictions.eq(fields[i], values[i]));
        //noinspection unchecked
        return (List<T>)crit.list();
    }

    @Transactional
    public <T> List<T> findByFieldsBetween(Class objClass, String[] fields, Object[] values) {
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(objClass);
        crit.add(Restrictions.ge(fields[0], values[0]));
        crit.add(Restrictions.lt(fields[1], values[1]));
        //noinspection unchecked
        return (List<T>)crit.list();
    }

    @Transactional
    public <T> List<T> findByFieldsSuma(Class objClass, String[] fields, Object[] values, String group, String count) {
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(objClass);
        crit.add(Restrictions.ge(fields[0], values[0]));
        crit.add(Restrictions.lt(fields[1], values[1]));
        crit.setProjection(Projections.projectionList().add(Projections.groupProperty(group))
        .add(Projections.count(count)));
        return (List<T>)crit.list();
    }

/*
    @Transactional
    public <T> List<T> findByFieldsSuma2(Class objClass, String[] fields, Object[] values, String group, String count) {
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(objClass);
        if (fields != null)
            for (int i = 0; i < fields.length; i++)
                crit.add(Restrictions.eq(fields[i], values[i]));
        crit.setProjection(Projections.projectionList().add(Projections.groupProperty(group))
                .add(Projections.count(count)));
        return (List<T>)crit.list();
    }
*/

    @Transactional
    public <T> List<T> findStatsByMunicipios(Class objClass, String[] fields, Object[] values, String group, String count) {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(objClass, "q");
        c.add(Restrictions.ge(fields[0], values[0]));
        c.add(Restrictions.lt(fields[1], values[1]));
        c.createAlias("q.sucursal", "sucursal");
        c.createAlias("sucursal.municipio", "municipio");
        c.setProjection(Projections.projectionList().add(Projections.groupProperty(group))
                .add(Projections.count(count)));
        return c.list();
    }

    @Transactional
    public <T> List<T> findStatsByDepartamentos(Class objClass, String[] fields, Object[] values, String group, String count) {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(objClass, "q");
        c.add(Restrictions.ge(fields[0], values[0]));
        c.add(Restrictions.lt(fields[1], values[1]));
        c.createAlias("q.sucursal", "sucursal");
        c.createAlias("sucursal.municipio", "municipio");
        c.createAlias("municipio.departamento", "departamento");
        c.setProjection(Projections.projectionList().add(Projections.groupProperty(group))
                .add(Projections.count(count)));
        return c.list();
    }

    @Transactional
    public <T> List<T> findStatsByRegion(Class objClass, String[] fields, Object[] values, String group, String count) {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(objClass, "q");
        c.add(Restrictions.ge(fields[0], values[0]));
        c.add(Restrictions.lt(fields[1], values[1]));
        c.createAlias("q.sucursal", "sucursal");
        c.createAlias("sucursal.municipio", "municipio");
        c.createAlias("municipio.departamento", "departamento");
        c.createAlias("departamento.region", "region");
        c.setProjection(Projections.projectionList().add(Projections.groupProperty(group))
                .add(Projections.count(count)));
        return c.list();
    }

    @Transactional
    public boolean callProcedure(String statement, Object[] values) {
        try {
            Session s = sessionFactory.getCurrentSession();
            CallableStatement cs = s.connection().prepareCall(statement);
            int i = 1;
            for (Object obj: values) {
                if (obj instanceof Integer) {
                    cs.setInt(i, (Integer) obj);
                } else if (obj instanceof String) {
                    cs.setString(i, obj.toString());
                }
                i++;
            }
            cs.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
