package com.gk.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JDRY-SJM on 2017/11/29.
 */

public enum UniversityLevelEnum {
    TQP("提前批", 1), YIPI("一批", 2), ERPI("二批", 3);

    private String name;
    private int index;

    UniversityLevelEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public static List<UniversityLevelEnum> getUniversityLevelList() {
        List<UniversityLevelEnum> areas = new ArrayList<>();
        for (UniversityLevelEnum a : UniversityLevelEnum.values()) {
            areas.add(a);
        }
        return areas;
    }

    public static String getName(int index) {
        for (UniversityLevelEnum a : UniversityLevelEnum.values()) {
            if (a.index == index) {
                return a.name;
            }
        }
        return null;
    }

    public static int getIndex(String name) {
        for (UniversityLevelEnum a : UniversityLevelEnum.values()) {
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
