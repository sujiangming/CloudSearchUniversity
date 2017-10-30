package com.gk.beans;

import java.util.ArrayList;

/**第一级实体bean
 * Created by Administrator on 2016/7/15.
 */
public class FirstBean {
    private String title;
    private String score;
    ArrayList<SecondBean> firstData = new ArrayList<SecondBean>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public ArrayList<SecondBean> getFirstData() {
        return firstData;
    }

    public void setFirstData(ArrayList<SecondBean> firstData) {
        this.firstData = firstData;
    }
}
