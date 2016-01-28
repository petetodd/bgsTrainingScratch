package com.brightgreenstar.accounting.datastore;

import java.util.logging.Logger;

/**
 * Created by peter on 28/01/2016.
 */
public class OfyImpl <T>{
    private static final Logger log = Logger.getLogger(OfyImpl.class.getName());
    protected Class<T> entityClass;

    public void insert(T entity) throws Exception {


        try {
            OfyService.ofy().save().entity(entity).now();  // synchronous
            //      mgr.persist(entity);
        } catch (Exception e)
        {
            throw e;
        }finally {
            //        mgr.close();
        }
    }



}

