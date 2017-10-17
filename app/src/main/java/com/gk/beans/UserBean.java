package com.gk.beans;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by JDRY-SJM on 2017/10/10.
 */
@Entity
public class UserBean {
    @Id(autoincrement = false)
    private Long id;
    @Unique
    private String userId;
    @NotNull
    private String userPwd;
    private String userCName;
    private String userSex;
    private String userAddress;
    private String userScore;
    private boolean isVip;

    @Generated(hash = 2083635313)
    public UserBean(Long id, String userId, @NotNull String userPwd,
                    String userCName, String userSex, String userAddress, String userScore,
                    boolean isVip) {
        this.id = id;
        this.userId = userId;
        this.userPwd = userPwd;
        this.userCName = userCName;
        this.userSex = userSex;
        this.userAddress = userAddress;
        this.userScore = userScore;
        this.isVip = isVip;
    }

    @Generated(hash = 1203313951)
    public UserBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPwd() {
        return this.userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getUserCName() {
        return this.userCName;
    }

    public void setUserCName(String userCName) {
        this.userCName = userCName;
    }

    public String getUserSex() {
        return this.userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getUserAddress() {
        return this.userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserScore() {
        return this.userScore;
    }

    public void setUserScore(String userScore) {
        this.userScore = userScore;
    }

    public boolean getIsVip() {
        return this.isVip;
    }

    public void setIsVip(boolean isVip) {
        this.isVip = isVip;
    }
}
