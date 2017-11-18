package com.gk.beans;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.gk.global.YXXConstants;
import com.gk.tools.JdryPersistence;

import java.io.Serializable;

/**
 * Created by JDRY-SJM on 2017/11/18.
 */

public class LoginBean implements Serializable {

    /**
     * administrator : false
     * banned : false
     * createDate : 1510974192000
     * enabled : true
     * latestLoginIp : 192.168.1.121
     * latestLoginTime : 1510974418562
     * male : true
     * mobile : 18275128970
     * password : 84b913d2aae84cec4293816c87ef7149
     * ranking : 0
     * salt : 70
     * score : 0
     * username : 18275128970
     * vipLevel : 1
     */

    private boolean administrator;
    private boolean banned;
    private long createDate;
    private boolean enabled;
    private String latestLoginIp;
    private long latestLoginTime;
    private boolean male;
    private String mobile;
    private String password;
    private int ranking;
    private String salt;
    private int score;
    private String username;
    private String vipLevel;
    private String cname;
    private String headImg;
    private String nickName;

    private static final long serialVersionUID = 1L;
    private volatile static LoginBean instance = null;//volatile关键字来保证其线程间的可见性
    private Context mContext;

    private LoginBean() {
    }

    public static LoginBean getInstance() {
        if (instance == null) {
            synchronized (LoginBean.class) {
                if (instance == null) {
                    instance = new LoginBean();
                }
            }
        }
        return instance;
    }

    public LoginBean setmContext(Context context) {
        this.mContext = context;
        return instance;
    }

    public void load() {  // 加载本地数据
        try {
            String objectStr = JdryPersistence.getObject(mContext, YXXConstants.LOGIN_INFO_SERIALIZE_KEY);
            if (null == objectStr || "".equals(objectStr)) {
                return;
            }
            LoginBean loginBean = JSON.parseObject(objectStr, LoginBean.class);
            setInstance(loginBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setInstance(LoginBean loginBean) {
        this.setAdministrator(loginBean.isAdministrator());
        this.setCreateDate(loginBean.getCreateDate());
        this.setEnabled(loginBean.isEnabled());
        this.setMale(loginBean.isMale());
        this.setMobile(loginBean.getMobile());
        this.setPassword(loginBean.getPassword());
        this.setSalt(loginBean.getSalt());
        this.setUsername(loginBean.getUsername());
        this.setBanned(loginBean.isBanned());
        this.setLatestLoginIp(loginBean.getLatestLoginIp());
        this.setLatestLoginTime(loginBean.getLatestLoginTime());
        this.setRanking(loginBean.getRanking());
        this.setScore(loginBean.getScore());
        this.setVipLevel(loginBean.getVipLevel());
        this.setCname(loginBean.getCname());
        this.setHeadImg(loginBean.getHeadImg());
        this.setNickName(loginBean.getNickName());
    }

    public void saveLoginBean(LoginBean loginBean) {
        setInstance(loginBean);
        save();
    }

    public void save() {
        try {
            JdryPersistence.saveObject(mContext, JSON.toJSONString(this), YXXConstants.LOGIN_INFO_SERIALIZE_KEY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isAdministrator() {
        return administrator;
    }

    public void setAdministrator(boolean administrator) {
        this.administrator = administrator;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getLatestLoginIp() {
        return latestLoginIp;
    }

    public void setLatestLoginIp(String latestLoginIp) {
        this.latestLoginIp = latestLoginIp;
    }

    public long getLatestLoginTime() {
        return latestLoginTime;
    }

    public void setLatestLoginTime(long latestLoginTime) {
        this.latestLoginTime = latestLoginTime;
    }

    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(String vipLevel) {
        this.vipLevel = vipLevel;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
