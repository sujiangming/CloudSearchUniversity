package com.gk.beans;

import java.io.Serializable;

/**
 * Created by jdry on 2017/2/21.
 */

public class SpinnerBean implements Serializable{
    public int index;
    public String deptId;
    public String deptName;
    public String deptType;

    @Override
    public String toString() {
        // 为什么要重写toString()呢？因为适配器在显示数据的时候，
        // 如果传入适配器的对象不是字符串的情况下，直接就使用对象.toString()
        return deptName;
    }
}
