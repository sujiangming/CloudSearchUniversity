package com.gk.beans;

/**
 * Created by JDRY-SJM on 2017/11/2.
 */

public class CommonBean<T> {
    private int flag;
    private int status;
    private String message;
    private T mData;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return mData;
    }

    public void setData(T mData) {
        this.mData = mData;
    }

    @Override
    public String toString() {
        return "[message=" + this.getMessage() + ",status=" + this.getStatus() + ",mData=" + this.mData + "]";
    }
}
