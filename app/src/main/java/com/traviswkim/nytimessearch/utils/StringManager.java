package com.traviswkim.nytimessearch.utils;

import android.text.TextUtils;

/**
 * Created by traviswkim on 7/26/16.
 */
public class StringManager {
    public static String convertQuotes(String s){
        if(!TextUtils.isEmpty(s)) {
            s.replaceAll("\\\"","\"").replaceAll("\"\"", "\"");
        }
        return s;
    }
}
