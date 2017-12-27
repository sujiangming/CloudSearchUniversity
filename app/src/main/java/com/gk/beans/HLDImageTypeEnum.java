package com.gk.beans;

import com.gk.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JDRY-SJM on 2017/11/29.
 */

public enum HLDImageTypeEnum {
    A(R.drawable.hld, "A"),
    S(R.drawable.hld_blue, "S"),
    E(R.drawable.hld, "E"),
    C(R.drawable.hld_blue, "C"),
    REAL(R.drawable.hld, "R"),
    I(R.drawable.hld_blue, "I");

    private int imageRes;
    private String index;

    private HLDImageTypeEnum(int name, String index) {
        this.imageRes = name;
        this.index = index;
    }

    public static List<HLDImageTypeEnum> getHLDTypeList() {
        List<HLDImageTypeEnum> areas = new ArrayList<>();
        for (HLDImageTypeEnum a : HLDImageTypeEnum.values()) {
            areas.add(a);
        }
        return areas;
    }

    public static int getImageRes(String index) {
        for (HLDImageTypeEnum a : HLDImageTypeEnum.values()) {
            if (a.index.equals(index)) {
                return a.imageRes;
            }
        }
        return 0;
    }

}
