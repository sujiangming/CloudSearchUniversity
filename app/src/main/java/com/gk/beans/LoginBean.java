package com.gk.beans;

import com.alibaba.fastjson.JSON;
import com.gk.global.YXXApplication;
import com.gk.global.YXXConstants;
import com.gk.tools.JdryPersistence;

import java.io.Serializable;

/**
 * Created by JDRY-SJM on 2017/11/18.
 */

public class LoginBean implements Serializable {

    /**
     * "address": null, //地址
     * "administrator": false, //是否管理员
     * "banned": false,   //是否禁言
     * "birthday": null, //出生日期
     * "classGrade": null,   //班级
     * "cname": null,    //姓名
     * "companyId": null,  //公司id
     * "createDate": 1510240198000, //创建日期
     * "depts": null,   //部门
     * "email": null,   //邮件
     * "enabled": true, //是否可用
     * "ename": null,   //英文名称
     * "headImg": null,  //头像
     * "latestLoginIp": null, //最后登录ip
     * "latestLoginTime": null,  //最后登录时间
     * "male": true,    //性别
     * "mobile": "15285630464", //电话
     * "nation": null, //名族
     * "password": "ce0d06028b025af428d46d8ad7a908f4", 密码
     * "ranking": 0, //排名
     * "roles": null,  //角色
     * "salt": "191",  //盐值
     * "school": null, //学校
     * "score": 0,  //分数
     * "subjectType": null,  //文理科
     * "type": null,   //用户身份
     * "username": "15285630464",  //用户名
     * "vipLevel": null //vip等级
     * "weixin": null
     */

    private String address;
    private boolean administrator;
    private boolean banned;
    private long birthday;
    private String classGrade;
    private String cname;
    private String companyId;
    private long createDate;
    private String depts;
    private String email;
    private boolean enabled;
    private String ename;
    private String headImg;
    private String latestLoginIp;
    private long latestLoginTime;
    private boolean male;
    private String mobile;
    private String nation;
    private String nickName;
    private String password;
    private String ranking;
    private String roles;
    private String salt;
    private String school;
    private String score;
    private int subjectType;
    private String type;
    private String username;
    private int vipLevel;
    private String weixin;
    private String wlDesc;

    private static final long serialVersionUID = 1L;
    private volatile static LoginBean instance = null;//volatile关键字来保证其线程间的可见性

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

    public void load() {  // 加载本地数据
        try {
            String objectStr = JdryPersistence.getObject(YXXApplication.getInstance().getApplicationContext(), YXXConstants.LOGIN_INFO_SERIALIZE_KEY);
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
        this.setAddress(loginBean.getAddress());
        this.setBirthday(loginBean.getBirthday());
        this.setClassGrade(loginBean.getClassGrade());
        this.setNation(loginBean.getNation());
        this.setCompanyId(loginBean.getCompanyId());
        this.setEmail(loginBean.getEmail());
        this.setEname(loginBean.getEname());
        this.setRoles(loginBean.getRoles());
        this.setType(loginBean.getType());
        this.setSchool(loginBean.getSchool());
        this.setDepts(loginBean.getDepts());
        this.setWeixin(loginBean.getWeixin());
    }

    public void saveLoginBean(LoginBean loginBean) {
        //setInstance(loginBean);
        instance = loginBean;
        save();
    }

    public void save() {
        try {
            JdryPersistence.saveObject(YXXApplication.getInstance().getApplicationContext(), JSON.toJSONString(instance), YXXConstants.LOGIN_INFO_SERIALIZE_KEY);
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

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public String getClassGrade() {
        return classGrade;
    }

    public void setClassGrade(String classGrade) {
        this.classGrade = classGrade;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getDepts() {
        return depts;
    }

    public void setDepts(String depts) {
        this.depts = depts;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public void setWlDesc(String wlDesc) {
        this.wlDesc = wlDesc;
    }
}
