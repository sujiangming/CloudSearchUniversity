package com.gk.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JDRY-SJM on 2017/11/29.
 */

public enum HLDTypeEnum {
    A("艺术", "A"),
    S("社会", "S"),
    E("企业", "E"),
    C("传统", "C"),
    R("实际", "R"),
    I("研究", "I");

    private String name;
    private String index;

    private HLDTypeEnum(String name, String index) {
        this.name = name;
        this.index = index;
    }

    public static List<HLDTypeEnum> getHLDTypeList() {
        List<HLDTypeEnum> areas = new ArrayList<>();
        for (HLDTypeEnum a : HLDTypeEnum.values()) {
            areas.add(a);
        }
        return areas;
    }

    public static String getName(String index) {
        for (HLDTypeEnum a : HLDTypeEnum.values()) {
            if (a.index.equals(index)) {
                return a.name;
            }
        }
        return null;
    }

    public static String getIndex(String name) {
        for (HLDTypeEnum a : HLDTypeEnum.values()) {
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
