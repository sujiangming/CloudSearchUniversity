package com.gk.beans;

import java.util.ArrayList;

/**
 * 第二级实体bean
 * Created by Administrator on 2016/7/15.
 */
public class SecondBean {
    private String title;
    ArrayList<ThirdBean> secondBean = new ArrayList<ThirdBean>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<ThirdBean> getSecondBean() {
        return secondBean;
    }

    public void setSecondBean(ArrayList<ThirdBean> secondBean) {
        this.secondBean = secondBean;
    }
}
