package com.gk.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JDRY-SJM on 2017/11/29.
 */

public enum UniversityAreaEnum {
    LIMITLESS("不限", 0),
    BEIJING("北京", 1),
    TIANJIN("天津", 2),
    SHANGHAI("上海", 3),
    CHONGQING("重庆", 4),
    HEBEI("河北", 5),
    SHANXI("山西", 6),
    LIAONING("辽宁", 7),
    JILIN("吉林", 8),
    HEILONGJIANG("黑龙江", 9),
    JIANGSU("江苏", 10),
    ZHEJIANG("浙江", 11),
    ANHUI("安徽", 12),
    FUJIAN("福建", 13),
    JIANGXI("江西", 14),
    SHANDONG("山东", 15),
    HENAN("河南", 16),
    HUBEI("湖北", 17),
    HUNAN("湖南", 18),
    GUANGDONG("广东", 19),
    HAINAN("海南", 20),
    SICHUAN("四川", 21),
    GUIZHOU("贵州", 22),
    YUNNAN("云南", 23),
    SANXI("陕西", 24),
    GANSU("甘肃", 25),
    QINGHAI("青海", 26),
    TAIWAN("台湾", 27),
    NEIMENGGU("内蒙古", 28),
    GUANGXI("广西", 29),
    XIZANG("西藏", 30),
    NINGXIA("宁夏", 31),
    XINJIANG("新疆", 32),
    XIANGGANG("香港", 33),
    ANMEN("澳门", 34);
    // 成员变量
    private String name;
    private int index;

    private UniversityAreaEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public static List<UniversityAreaEnum> getAreaList() {
        List<UniversityAreaEnum> areas = new ArrayList<>();
        for (UniversityAreaEnum a : UniversityAreaEnum.values()) {
            areas.add(a);
        }
        return areas;
    }

    public static String getName(int index) {
        for (UniversityAreaEnum a : UniversityAreaEnum.values()) {
            if (a.index == index) {
                return a.name;
            }
        }
        return null;
    }

    public static int getIndex(String name) {
        for (UniversityAreaEnum a : UniversityAreaEnum.values()) {
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
