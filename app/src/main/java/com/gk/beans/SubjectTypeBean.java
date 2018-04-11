package com.gk.beans;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class SubjectTypeBean {
    @Id(autoincrement = true)
    private Long id;
    @Unique
    private String index;
    private String name;

    public SubjectTypeBean(String index, String name) {
        this.index = index;
        this.name = name;
    }

    @Generated(hash = 128664568)
    public SubjectTypeBean(Long id, String index, String name) {
        this.id = id;
        this.index = index;
        this.name = name;
    }

    @Generated(hash = 2016731104)
    public SubjectTypeBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIndex() {
        return this.index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
