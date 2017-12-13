package com.gk.beans;

/**
 * Created by JDRY-SJM on 2017/11/29.
 */

public enum MBTITypeEnum {

    E("外向", "E"),
    I("内向", "I"),
    S("实感", "S"),
    N("直觉", "N"),
    T("思考", "T"),
    F("情感", "F"),
    J("判断", "J"),
    P("知觉", "P");

    private String name;
    private String index;

    private MBTITypeEnum(String name, String index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(String index) {
        for (MBTITypeEnum a : MBTITypeEnum.values()) {
            if (a.index.equals(index)) {
                return a.name;
            }
        }
        return null;
    }

    public static String getIndex(String name) {
        for (MBTITypeEnum a : MBTITypeEnum.values()) {
            if (a.name.equals(name)) {
                return a.index;
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

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
}
