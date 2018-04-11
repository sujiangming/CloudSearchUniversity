package com.gk.global;

import android.os.Environment;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static CrashHandler crashHandler;

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        // TODO Auto-generated method stub
        if (crashHandler != null) {
            try {
                //将crash log写入文件
                FileOutputStream fileOutputStream = new FileOutputStream(Environment.getExternalStorageDirectory() + "/Download/" + "yxx_crash_log.txt", true);
                PrintStream printStream = new PrintStream(fileOutputStream);
                ex.printStackTrace(printStream);
                printStream.flush();
                printStream.close();
                fileOutputStream.close();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    //设置默认处理器
    public void init() {
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    private CrashHandler() {}

    //单例
    public static CrashHandler instance() {
        if (crashHandler == null) {
            synchronized (CrashHandler.class) {
                crashHandler = new CrashHandler();
            }
        }
        return crashHandler;
    }
}
