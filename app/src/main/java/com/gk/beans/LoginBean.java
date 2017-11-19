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
    private String ranking;
    private String salt;
    private String score;
    private String username;
    private int vipLevel;
    private String cname;
    private String headImg;
    private String nickName;
    private int subjectType;
    private String address;

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

    public String getVipLevelDesc() {
        String desc = "";
        switch (this.getVipLevel()) {
            case 1:
                desc = "普通会员";
                break;
            case 2:
                desc = "银卡会员";
                break;
            case 3:
                desc = "金卡会员";
                break;
            default:
                desc = "游客";
                break;
        }
        return desc;
    }

    public String getWlDesc() {
        String desc = "";
        switch (this.getSubjectType()) {
            case 1:
                desc = "文科";
                break;
            case 2:
                desc = "理科";
                break;
            default:
                desc = "未知";
                break;
        }
        return desc;
    }

    public boolean isAdministrator() {
        return administrator;
    }

    public LoginBean setAdministrator(boolean administrator) {
        this.administrator = administrator;
        return instance;
    }

    public boolean isBanned() {
        return banned;
    }

    public LoginBean setBanned(boolean banned) {
        this.banned = banned;
        return instance;
    }

    public long getCreateDate() {
        return createDate;
    }

    public LoginBean setCreateDate(long createDate) {
        this.createDate = createDate;
        return instance;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public LoginBean setEnabled(boolean enabled) {
        this.enabled = enabled;
        return instance;
    }

    public String getLatestLoginIp() {
        return latestLoginIp;
    }

    public LoginBean setLatestLoginIp(String latestLoginIp) {
        this.latestLoginIp = latestLoginIp;
        return instance;
    }

    public long getLatestLoginTime() {
        return latestLoginTime;
    }

    public LoginBean setLatestLoginTime(long latestLoginTime) {
        this.latestLoginTime = latestLoginTime;
        return instance;
    }

    public boolean isMale() {
        return male;
    }

    public LoginBean setMale(boolean male) {
        this.male = male;
        return instance;
    }

    public String getMobile() {
        return mobile;
    }

    public LoginBean setMobile(String mobile) {
        this.mobile = mobile;
        return instance;
    }

    public String getPassword() {
        return password;
    }

    public LoginBean setPassword(String password) {
        this.password = password;
        return instance;
    }

    public String getRanking() {
        return ranking;
    }

    public LoginBean setRanking(String ranking) {
        this.ranking = ranking;
        return instance;
    }

    public String getSalt() {
        return salt;
    }

    public LoginBean setSalt(String salt) {
        this.salt = salt;
        return instance;
    }

    public String getScore() {
        return score;
    }

    public LoginBean setScore(String score) {
        this.score = score;
        return instance;
    }

    public String getUsername() {
        return username;
    }

    public LoginBean setUsername(String username) {
        this.username = username;
        return instance;
    }

    public int getVipLevel() {
        return vipLevel;
    }

    public LoginBean setVipLevel(int vipLevel) {
        this.vipLevel = vipLevel;
        return instance;
    }

    public String getCname() {
        return cname;
    }

    public LoginBean setCname(String cname) {
        this.cname = cname;
        return instance;
    }

    public String getHeadImg() {
        return headImg;
    }

    public LoginBean setHeadImg(String headImg) {
        this.headImg = headImg;
        return instance;
    }

    public String getNickName() {
        return nickName;
    }

    public LoginBean setNickName(String nickName) {
        this.nickName = nickName;
        return instance;
    }

    public int getSubjectType() {
        return subjectType;
    }

    public LoginBean setSubjectType(int subjectType) {
        this.subjectType = subjectType;
        return instance;
    }

    public String getAddress() {
        return address;
    }

    public LoginBean setAddress(String address) {
        this.address = address;
        return instance;
    }
}
