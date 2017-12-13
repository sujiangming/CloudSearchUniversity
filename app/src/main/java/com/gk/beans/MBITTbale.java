package com.gk.beans;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by JDRY-SJM on 2017/12/13.
 */
@Entity
public class MBITTbale {
    @Id
    private Long id;
    @Unique
    private String mbitId;
    @NotNull
    private String selectItem;

    @Generated(hash = 249882388)
    public MBITTbale(Long id, String mbitId, @NotNull String selectItem) {
        this.id = id;
        this.mbitId = mbitId;
        this.selectItem = selectItem;
    }

    @Generated(hash = 1924436295)
    public MBITTbale() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMbitId() {
        return this.mbitId;
    }

    public void setMbitId(String mbitId) {
        this.mbitId = mbitId;
    }

    public String getSelectItem() {
        return this.selectItem;
    }

    public void setSelectItem(String selectItem) {
        this.selectItem = selectItem;
    }
}
