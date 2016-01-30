package com.brightgreenstar.accounting.api;

import com.brightgreenstar.accounting.datastore.CallbackResult;
import com.brightgreenstar.accounting.entities.Invoice;
import com.brightgreenstar.accounting.entities.InvoiceLine;
import com.brightgreenstar.accounting.ofyImplementations.InvoiceOfyImpl;
import com.brightgreenstar.accounting.utils.AppUtils;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.gson.Gson;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;

import javax.inject.Named;

import java.util.List;
import java.util.logging.Logger;



import static com.brightgreenstar.accounting.datastore.OfyService.ofy;
import static com.brightgreenstar.accounting.datastore.OfyService.factory;


/**
 * Created by peter on 28/01/2016.
 */

@Api(name = "invoiceEndpoint",
        version = "v1",
        namespace = @ApiNamespace(ownerDomain = "brightgreenstar.com",
                ownerName = "BrightGreenStar",
                packagePath="accounting"))

public class InvoiceEndpoint {
    // All instances of this class will use the same logger
    private static final Logger logger =
            Logger.getLogger(InvoiceEndpoint.class.getName());



    public  InvoiceEndpoint(){

       logger.info(InvoiceEndpoint.class.getSimpleName()+ " endpoint initialised.");

    }

    @ApiMethod(name = "insertInvoice")
    public CallbackResult insertInvoice(@Named("jsonObject") String _jsonObject)  {
        CallbackResult result = new CallbackResult();
        result.setRetCode(1);
        // Retcode has been set to 1 = success.  But we'll update it if fail
        try {
            JSONObject jObject = new JSONObject(_jsonObject);
            InvoiceOfyImpl invoiceImpl = new InvoiceOfyImpl();
            result = invoiceImpl.insertInvoice(jObject);
        } catch (Exception e) {
            logger.warning(e.getMessage());
            result.setRetCode(-1);
            result.setData(e.getMessage());
        }
        logger.warning(new Gson().toJson(result));
        return result;
    }

    @ApiMethod(name = "insertInvoiceLine", path="insert_Invoice_Line", httpMethod= ApiMethod.HttpMethod.POST)
    public CallbackResult insertInvoiceLine(@Named("jsonObject") String _jsonObject)  {
        CallbackResult result = new CallbackResult();
        result.setRetCode(1);
        try {

            // The Json sent by user holds the description and line amount, but may be expanded to holds other details
            JSONObject jObject = new JSONObject(_jsonObject);

            // Get the key for the Invoice
            Key<Invoice> invoiceKey = Key.create(Invoice.class, (jObject.getString("invoiceId")));

            // Fetch the Invoice from the datastore.
            Invoice invoice = ofy().load().key(invoiceKey).now();
            System.out.println("Invoice amount : " + invoice.getAmountCents());

            // Allocate a key for the user -- let App Engine allocate the ID
            // Don't forget to include the invoice in the allocated ID
            final Key<InvoiceLine> invLineKey = factory().allocateId(invoiceKey, InvoiceLine.class);

            // Get the invoice line Id from the Key
            final long invLineId = invLineKey.getId();

            InvoiceLine invLine = new InvoiceLine(invLineId,(jObject.getString("invoiceId")),jObject);

            ofy().save().entities(invLine, invoice).now();

        } catch (Exception e) {
            logger.warning(e.getMessage());
            result.setRetCode(-1);
            result.setData(e.getMessage());
        }
        logger.warning(new Gson().toJson(result));
        return result;
    }


    @ApiMethod(
            name = "queryInvoice_nofilters",
            path = "queryInvoice_nofilters",
            httpMethod = ApiMethod.HttpMethod.POST
    )
    public List<Invoice> queryInvoice_nofilters() {
        // Find all entities of type Conference
        Query<Invoice> query = ofy().load().type(Invoice.class);

        return query.list();
    }

    @ApiMethod(
            name = "queryInvoiceLines_invoice",
            path = "queryInvoiceLines_invoice",
            httpMethod = ApiMethod.HttpMethod.POST
    )
    public List<InvoiceLine> queryInvoiceLines_invoice(@Named("jsonObject") String _jsonObject) {
        // Find all invoice line entities for a given invoice

        try {
            JSONObject jObject = new JSONObject(_jsonObject);
            String invoiceId = (jObject.getString("invoiceId"));
            Key<Invoice> invoiceKey = Key.create(Invoice.class, invoiceId);
            return ofy().load().type(InvoiceLine.class)
                    .ancestor(invoiceKey)
                    .order("amount").list();
        } catch (Exception e) {
            // logger.warning(e.getMessage());
        }
        return null;

    }


    @ApiMethod(
            name = "getInvoiceWithId",
            path = "getInvoiceWithId"
    )
    public List<InvoiceLine> getInvoiceWithId(@Named("jsonObject") String _jsonObject)  {

        try {
            JSONObject jObject = new JSONObject(_jsonObject);

            String invoiceId = "";

            try{
                invoiceId = jObject.getString("invoiceId");
            }catch (Exception e) {
            }

            Key<Invoice> invoiceKey = Key.create(Invoice.class, invoiceId);
            // Fetch the Invoice from the datastore.
            Invoice invoice = ofy().load().key(invoiceKey).now();

            logger.info("Invoice amount : " + invoice.getAmountCents());

            try {
                // We can pass either Invoice or just the Invoice key in the Ancestor parameter, both work
                logger.info("Returning Invoice Lines");
                /*
                List list = ofy().load().type(InvoiceLine.class)
                        .ancestor(invoice)
                        .order("amountCents").list();
                System.out.println("Invoice lines A : " + list.toString());
                */

                List list2 = ofy().load().type(InvoiceLine.class)
                        .ancestor(invoiceKey)
                        .order("amountCents").list();

                logger.info("Invoice lines B : " + list2.toString());

                return list2;

            } catch (Exception e) {
                logger.warning(e.getMessage());
            }
        }
        catch(Exception e){
            logger.warning(e.getMessage());
        }
        return null;
    }

    @ApiMethod(name = "createUploadUrl", path = "createUploadUrl")
    public CallbackResult createUploadUrl(){
        CallbackResult result = new CallbackResult();
        result.setRetCode(1);
        try{
            result.setData(AppUtils.createUploadUrl());
        }catch (Exception e){
            result.setRetCode(-1);
            result.setData(e.getMessage());
        }
        return result;
    }

}
