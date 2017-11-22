package com.gk.tools;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by JDRY-SJM on 2017/11/22.
 */

public class YxxEncoderUtils {
    public static String URLEncoder(String value) {
        String ret = "";
        try {
            ret = URLEncoder.encode(value, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return ret;
    }
}
