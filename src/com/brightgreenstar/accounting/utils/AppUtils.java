package com.brightgreenstar.accounting.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by peter on 28/01/2016.
 */
public class AppUtils {

    public static String generateMD5(String to_be_md5) {
        String md5_sum = "";
        // If the provided String is null, then throw an Exception.
        if (to_be_md5 == null) {
            throw new RuntimeException(
                    "There is no string to calculate a MD5 hash from.");
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(to_be_md5.getBytes("UTF-8"));
            StringBuffer collector = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                collector.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
                        .substring(1, 3));
            }
            md5_sum = collector.toString();
        }// end try
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Could not find a MD5 instance: "
                    + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Could not translate UTF-8: "
                    + e.getMessage());
        }
        return md5_sum;
    }// end generateMD5

    public static long longAmount(String string) {
        try{
            return  Long.parseLong(string);
        }catch(NumberFormatException e){}
        return 0;
    }
}
