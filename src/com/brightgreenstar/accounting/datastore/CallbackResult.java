package com.brightgreenstar.accounting.datastore;

import com.google.gson.Gson;

/**
 * Created by peter on 28/01/2016.
 */
public class CallbackResult {
    private int retCode=0;
    private Object data;
    private Object tokenData;

    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }
    public int getRetCode() {
        return retCode;
    }
    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }

    public void setTokenData(Object tokenData) {
        this.tokenData = tokenData;
    }

    public Object getTokenData() {
        return tokenData;
    }


    public static String ToJson(CallbackResult result) {
        Gson gson = new Gson();
        return gson.toJson(result);
    }
}
