package com.gk.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JDRY-SJM on 2017/11/29.
 */

public enum CourseTypeEnum {
    YUWEN("语文", "yuwen"),
    SHUXUE("数学", "shuxue"),
    YINGYU("英语", "yingyu"),
    LIZONG("理综", "lizong"),
    WENZONG("文综", "wenzong"),
    WULI("物理", "wuli"),
    HUAXUE("化学", "huaxue"),
    LISHI("历史", "lishi"),
    DILI("地理", "dili"),
    ZHZENGZHI("政治", "zhengzhi"),
    SHENGWU("生物", "shengwu");
    // 成员变量
    private String name;
    private String pinYin;

    private CourseTypeEnum(String name, String pinYin) {
        this.name = name;
        this.pinYin = pinYin;
    }

    public static List<CourseTypeEnum> getAreaList() {
        List<CourseTypeEnum> areas = new ArrayList<>();
        for (CourseTypeEnum a : CourseTypeEnum.values()) {
            areas.add(a);
        }
        return areas;
    }

    public static String getPinYin(String name) {
        for (CourseTypeEnum a : CourseTypeEnum.values()) {
            if (a.name.equals(name)) {
                return a.pinYin;
            }
        }
        return null;
    }

    public static String getSubjectTypeName(String pinyin) {
        for (CourseTypeEnum a : CourseTypeEnum.values()) {
            if (a.pinYin.equals(pinyin)) {
                return a.name;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinYin() {
        return pinYin;
    }

    public void setPinYin(String pinYin) {
        this.pinYin = pinYin;
    }
}
