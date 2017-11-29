package com.gk.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JDRY-SJM on 2017/11/29.
 */

public enum UniversityFeatureEnum {
    JBW("985", 1),
    ERYIYI("211", 2),
    SHUAANGYILIU("双一流", 3);

    private String name;
    private int index;

    private UniversityFeatureEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public static List<UniversityFeatureEnum> getUniversityFeatureList() {
        List<UniversityFeatureEnum> areas = new ArrayList<>();
        for (UniversityFeatureEnum a : UniversityFeatureEnum.values()) {
            areas.add(a);
        }
        return areas;
    }

    public static String getName(int index) {
        for (UniversityFeatureEnum a : UniversityFeatureEnum.values()) {
            if (a.index == index) {
                return a.name;
            }
        }
        return null;
    }

    public static int getIndex(String name) {
        for (UniversityFeatureEnum a : UniversityFeatureEnum.values()) {
            if (a.name.equals(name)) {
                return a.index;
            }
        }
        return 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
