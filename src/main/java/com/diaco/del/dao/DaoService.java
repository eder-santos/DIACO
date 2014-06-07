package com.diaco.del.dao;

import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.List;

/**
 * User: esantos
 * Date: 5/24/14
 * Time: 11:16 AM
 */
public interface DaoService {

    public <T> List<T> findByField(Class clazz, String field, Object value);
    public <T> List<T> findByFields(Class objClass, String[] fields, Object[] values);
    public <T> List<T> findByFieldsBetween(Class objClass, String[] fields, Object[] values);
    public <T> List<T> findByFieldsSuma(Class objClass, String[] fields, Object[] values, String group, String count);
    public <T> List<T> findStatsByMunicipios(Class objClass, String[] fields, Object[] values, String group, String count);
    public <T> List<T> findStatsByDepartamentos(Class objClass, String[] fields, Object[] values, String group, String count);
    public <T> List<T> findStatsByRegion(Class objClass, String[] fields, Object[] values, String group, String count);
    public boolean callProcedure(String statement, Object[] values);
}
