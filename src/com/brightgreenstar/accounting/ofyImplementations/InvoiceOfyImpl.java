package com.brightgreenstar.accounting.ofyImplementations;

import com.brightgreenstar.accounting.datastore.CallbackResult;
import com.brightgreenstar.accounting.entities.Invoice;
import com.brightgreenstar.accounting.utils.AppUtils;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.brightgreenstar.accounting.datastore.OfyImpl;



/**
 * Created by peter on 28/01/2016.
 */
public class InvoiceOfyImpl extends OfyImpl<Invoice> {

    public CallbackResult insertInvoice(JSONObject _jObject) throws Exception{
        CallbackResult result = new CallbackResult();

        //
        String nanoTime = String.valueOf(System.nanoTime());
        String md5Hash = AppUtils.generateMD5(nanoTime);
        String entityKey = "IncomeRec_" + md5Hash;
        //
        Invoice invoice = new Invoice();
        invoice.setId(entityKey);

        //
        invoice.setAmountCents(AppUtils.longAmount(_jObject.getString("amount")));

        //
        insert(invoice);

        String invoiceDetails = toJSON(invoice).toString();
        result.setRetCode(1);
        result.setData(invoiceDetails);
        //
        return result;
    }

    public JSONObject toJSON(Invoice objHash) {
        JSONObject jObject = new JSONObject();
        try {
            jObject.put("key", objHash.getId());
            jObject.put("amount", objHash.getAmountCents());

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jObject;
    }
}
