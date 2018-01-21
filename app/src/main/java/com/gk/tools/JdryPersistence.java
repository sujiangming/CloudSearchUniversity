package com.gk.tools;

import android.content.Context;
import android.content.SharedPreferences;

import com.gk.global.YXXApplication;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Administrator on 2016/12/13.
 */

public class JdryPersistence {
    /**
     * 序列化对象
     *
     * @param object
     * @return
     * @throws IOException
     */
    public static String serialize(Object object) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                byteArrayOutputStream);
        objectOutputStream.writeObject(object);
        String serStr = byteArrayOutputStream.toString("ISO-8859-1");
        serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
        objectOutputStream.close();
        byteArrayOutputStream.close();
        return serStr;
    }

    /**
     * 反序列化对象
     *
     * @param str
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object deSerialization(String str) throws IOException,
            ClassNotFoundException {
        String redStr = java.net.URLDecoder.decode(str, "UTF-8");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                redStr.getBytes("ISO-8859-1"));
        ObjectInputStream objectInputStream = new ObjectInputStream(
                byteArrayInputStream);
        Object object = objectInputStream.readObject();
        objectInputStream.close();
        byteArrayInputStream.close();
        return object;
    }

    public static void saveObject(Context context, String strObject, String objName) {
        SharedPreferences sp = context.getSharedPreferences(objName, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(objName, strObject);
        edit.commit();
    }

    public static String getObject(Context context, String objName) {
        SharedPreferences sp = context.getSharedPreferences(objName, Context.MODE_PRIVATE);
        return sp.getString(objName, null);
    }

    public static void saveByAppContext(String strObject, String objName) {
        SharedPreferences sp = YXXApplication.getInstance().getApplicationContext().getSharedPreferences(objName, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(objName, strObject);
        edit.commit();
    }

    public static String getObjectByAppContext(String objName) {
        SharedPreferences sp = YXXApplication.getInstance().getApplicationContext().getSharedPreferences(objName, Context.MODE_PRIVATE);
        return sp.getString(objName, null);
    }

    public static void deleteLoginBean(Context context, String keyName) {
        SharedPreferences sp = context.getSharedPreferences(keyName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(keyName);
        editor.commit();
    }

    public static void clearLoginBean(Context context, String keyName) {
        SharedPreferences sp = context.getSharedPreferences(keyName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }
}
