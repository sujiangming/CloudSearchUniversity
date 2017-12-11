package com.gk.beans;

import com.gk.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JDRY-SJM on 2017/11/29.
 */

public enum HLDImageTypeEnum {
    A(R.drawable.interest_ys, "A"),
    S(R.drawable.interest_sh, "S"),
    E(R.drawable.interest_yy, "E"),
    C(R.drawable.interest_ct, "C"),
    REAL(R.drawable.interest_xs, "R"),
    I(R.drawable.interest_yj, "I");

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
