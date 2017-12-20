package com.gk.tools;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by JDRY-SJM on 2017/11/19.
 */

public class YxxUtils {
    /**
     * EditText获取焦点并显示软键盘
     */
    public static void showSoftInputFromWindow(EditText editText) {
        editText.requestFocus();
        InputMethodManager inputManager = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(editText, 0);
    }

    /**
     * 隐藏掉软盘
     */
    public static void hideSoftInputKeyboard(EditText editText) {
        //隐藏软盘
        InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        editText.clearFocus();
        editText.setText("");
    }

    public static String URLEncode(String value) {
        try {
            return URLEncoder.encode(value, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void LogToFile(String fileName, String fileContent) {
        File file = new File(Environment.getExternalStorageDirectory() + "/Download/", fileName + ".txt");
        Log.e("LogToFile:", file.getAbsolutePath());
        String path = file.getAbsolutePath();
        try {
            writeByOutputStreamWrite(path, fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 用OutputStreamWrite向文件写入内容
     *
     * @throws IOException
     */
    public static void writeByOutputStreamWrite(String _sDestFile, String _sContent) throws IOException {
        OutputStreamWriter os = null;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(_sDestFile);
            os = new OutputStreamWriter(fos, "UTF-8");
            os.write(_sContent);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {

            if (os != null) {

                os.close();

                os = null;
            }
            if (fos != null) {

                fos.close();

                fos = null;
            }

        }
    }

}
