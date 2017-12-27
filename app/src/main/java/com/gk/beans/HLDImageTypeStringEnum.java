package com.gk.beans;

import com.gk.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JDRY-SJM on 2017/11/29.
 */

public enum HLDImageTypeStringEnum {
    A(R.string.a_type, "A"),
    S(R.string.s_type, "S"),
    E(R.string.e_type, "E"),
    C(R.string.c_type, "C"),
    REAL(R.string.r_type, "R"),
    I(R.string.i_type, "I");

    private int imageRes;
    private String index;

    private HLDImageTypeStringEnum(int name, String index) {
        this.imageRes = name;
        this.index = index;
    }

    public static List<HLDImageTypeStringEnum> getHLDTypeList() {
        List<HLDImageTypeStringEnum> areas = new ArrayList<>();
        for (HLDImageTypeStringEnum a : HLDImageTypeStringEnum.values()) {
            areas.add(a);
        }
        return areas;
    }

    public static int getImageRes(String index) {
        for (HLDImageTypeStringEnum a : HLDImageTypeStringEnum.values()) {
            if (a.index.equals(index)) {
                return a.imageRes;
            }
        }
        return 0;
    }

}
