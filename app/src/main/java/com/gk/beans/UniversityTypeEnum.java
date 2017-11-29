package com.gk.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JDRY-SJM on 2017/11/29.
 */

public enum UniversityTypeEnum {
    ZONGHE("综合", 1),
    GONGKE("工科", 2),
    SHIFAN("师范", 3),
    CAIJING("财经", 4),
    ZHENGFA("政法", 5),
    YUYAN("语言", 6),
    YIYAO("医药", 7),
    NONGYE("农业", 8),
    LINYE("林业", 9),
    MINZU("民族", 10),
    YISHU("艺术", 11),
    TIYU("体育", 12),
    JUNSHI("军事", 13);

    private String name;
    private int index;

    private UniversityTypeEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public static List<UniversityTypeEnum> getUniversityList() {
        List<UniversityTypeEnum> areas = new ArrayList<>();
        for (UniversityTypeEnum a : UniversityTypeEnum.values()) {
            areas.add(a);
        }
        return areas;
    }

    public static String getName(int index) {
        for (UniversityTypeEnum a : UniversityTypeEnum.values()) {
            if (a.index == index) {
                return a.name;
            }
        }
        return null;
    }

    public static int getIndex(String name) {
        for (UniversityTypeEnum a : UniversityTypeEnum.values()) {
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
