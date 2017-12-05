package com.gk.beans;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by JDRY-SJM on 2017/12/5.
 */
@Entity
public class HLDTable {
    @Id
    private Long id;
    @Unique
    private String hldId;
    private String interestType;
    private String title;
    private boolean isSelected;
    @Generated(hash = 1506093041)
    public HLDTable(Long id, String hldId, String interestType, String title,
            boolean isSelected) {
        this.id = id;
        this.hldId = hldId;
        this.interestType = interestType;
        this.title = title;
        this.isSelected = isSelected;
    }
    @Generated(hash = 364032478)
    public HLDTable() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getHldId() {
        return this.hldId;
    }
    public void setHldId(String hldId) {
        this.hldId = hldId;
    }
    public String getInterestType() {
        return this.interestType;
    }
    public void setInterestType(String interestType) {
        this.interestType = interestType;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public boolean getIsSelected() {
        return this.isSelected;
    }
    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

}
