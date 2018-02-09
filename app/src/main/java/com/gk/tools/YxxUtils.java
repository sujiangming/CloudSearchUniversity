package com.gk.tools;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

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

    public static void setViewData(TextView tv, String value) {
        if (value != null && !"".equals(value)) {
            tv.setText(value);
        }
    }

    public static void dialPhoneNumber(Context context, String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
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

    /**
     * 验证手机格式
     */
    public static boolean isMobile(String number) {
    /*
    移动：134、135、136、137、138、139、150、151、152、157(TD)、158、159、178(新)、182、184、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、170、173、177、180、181、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String num = "[1][34578]\\d{9}";//"[1]"代表第1位为数字1，"[34578]"代表第二位可以为3、4、5、7、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(number)) {
            return false;
        } else {
            //matches():字符串是否在给定的正则表达式匹配
            return number.matches(num);
        }
    }


}
