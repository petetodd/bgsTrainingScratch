package com.brightgreenstar.accounting.api;

import com.brightgreenstar.accounting.datastore.CallbackResult;
import com.brightgreenstar.accounting.ofyImplementations.InvoiceOfyImpl;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

import javax.inject.Named;

/**
 * Created by peter on 28/01/2016.
 */

@Api(name = "invoiceEndpoint",
        version = "v1",
        namespace = @ApiNamespace(ownerDomain = "brightgreenstar.com",
                ownerName = "BrightGreenStar",
                packagePath="accounting"))

public class InvoiceEndpoint {



    public  InvoiceEndpoint(){

        System.out.println(InvoiceEndpoint.class.getSimpleName()+ " endpoint initialised.");

    }

    @ApiMethod(name = "insertInvoice")
    public CallbackResult insertInvoice(@Named("jsonObject") String _jsonObject)  {
        CallbackResult result = new CallbackResult();
        result.setRetCode(1);
        // Redcode has been set to 1 = success.  But we'll update it if fail
        try {
            JSONObject jObject = new JSONObject(_jsonObject);
            InvoiceOfyImpl invoiceImpl = new InvoiceOfyImpl();
            result = invoiceImpl.insertInvoice(jObject);
        } catch (Exception e) {
           // logger.warning(e.getMessage());
            result.setRetCode(-1);
            result.setData(e.getMessage());
        }
      //  logger.warning(new Gson().toJson(result));
        return result;
    }



}
