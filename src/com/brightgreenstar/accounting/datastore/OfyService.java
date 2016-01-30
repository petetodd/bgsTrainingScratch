package com.brightgreenstar.accounting.datastore;

import com.brightgreenstar.accounting.entities.Invoice;
import com.brightgreenstar.accounting.entities.InvoiceLine;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

/**
 * Created by peter on 28/01/2016.
 */
public class OfyService {
    static {

        factory().register(Invoice.class);
        factory().register(InvoiceLine.class);


    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();

        //   return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {

        return ObjectifyService.factory();



    }
}