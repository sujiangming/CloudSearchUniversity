package com.gk.beans;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by JDRY-SJM on 2018/4/10.
 */
@Entity
public class UniversityTypeBean {
    @Id(autoincrement = true)
    private Long id;
    @Unique
    private int index;
    private String name;

    @Generated(hash = 1349259846)
    public UniversityTypeBean(Long id, int index, String name) {
        this.id = id;
        this.index = index;
        this.name = name;
    }

    public UniversityTypeBean(String name, int index) {
        this.index = index;
        this.name = name;
    }

    @Generated(hash = 635591058)
    public UniversityTypeBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
