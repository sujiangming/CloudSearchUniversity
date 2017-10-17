package com.gk.tools;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by jdry on 2016/11/28.
 */

public class ToastUtils {

    private static Toast toast;

    public static void toast(Context context, String content) {
        if (toast == null) {
            toast = Toast.makeText(context.getApplicationContext(),
                    content,
                    Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }

    public static void hideToast(){
        if(toast != null){
            toast.cancel();
        }
    }
}
