package com.gk.beans;

import com.alibaba.fastjson.JSON;
import com.gk.R;
import com.gk.global.YXXApplication;
import com.gk.global.YXXConstants;
import com.gk.tools.JdryPersistence;

import java.io.Serializable;

/**
 * Created by JDRY-SJM on 2017/11/18.
 */

public class LoginBean implements Serializable {
    /**
     * address :
     * administrator : false
     * admissionRiskNum :
     * banned : false
     * birthday :
     * classGrade : null
     * cname : 未填写
     * companyId :
     * createDate : 1518011645740
     * depts :
     * email :
     * enabled : true
     * ename :
     * headImg :
     * heartTestNum :
     * isHeartTest :
     * isLive :
     * latestLoginIp : 223.104.24.111
     * latestLoginTime : 1518011646037
     * male : true
     * mobile : 18275128970
     * nation :
     * nickName : 未填写
     * password : 808248770360cebc92dcb0d5d41441b5
     * ranking :
     * roles :
     * salt : 182
     * sameScoreToNum :
     * school :
     * score :
     * subjectType :
     * type :
     * username : 18275128970
     * vipLevel : 1
     * weixin :
     */
    private String address;
    private boolean administrator;
    private String admissionRiskNum;
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
    private String heartTestNum;
    private String isHeartTest;
    private String isLive;
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
    private String sameScoreToNum;
    private String school;
    private String score;
    private int subjectType;
    private String type;
    private String username;
    private int vipLevel;
    private String weixin;
    private String wishUniversity;
    private String wishProvince;
    private boolean hasReport;
    private String weiXinHeadImg;
    private String weiXinUnionid;
    private String verifyCode;
    private String empty = null;
    private int vipLevelTmp;//临时会员等级状态
    private String orderNo;//临时保存订单等级；

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
        //Log.e(LoginBean.class.getName(), JSON.toJSONString(instance));
        return instance;
    }

    public void load() {  // 加载本地数据
        try {
            String objectStr = JdryPersistence.getObject(YXXApplication.getInstance().getApplicationContext(), YXXConstants.LOGIN_INFO_SERIALIZE_KEY);
            if (null == objectStr || "".equals(objectStr)) {
                return;
            }
            LoginBean loginBean = JSON.parseObject(objectStr, LoginBean.class);
            instance = loginBean;
            save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setInstance(LoginBean loginBean) {
        this.setAddress(loginBean.getAddress());
        this.setAdministrator(loginBean.isAdministrator());
        this.setBanned(loginBean.isBanned());
        this.setBirthday(loginBean.getBirthday());
        this.setClassGrade(loginBean.getClassGrade());
        this.setCname(loginBean.getCname());
        this.setCompanyId(loginBean.getCompanyId());
        this.setCreateDate(loginBean.getCreateDate());
        this.setDepts(loginBean.getDepts());
        this.setEmail(loginBean.getEmail());
        this.setEnabled(loginBean.isEnabled());
        this.setEname(loginBean.getEname());
        this.setHeadImg(loginBean.getHeadImg());
        this.setIsHeartTest(loginBean.getIsHeartTest());
        this.setLatestLoginIp(loginBean.getLatestLoginIp());
        this.setLatestLoginTime(loginBean.getLatestLoginTime());
        this.setMale(loginBean.isMale());
        this.setMobile(loginBean.getMobile());
        this.setNation(loginBean.getNation());
        this.setNickName(loginBean.getNickName());
        this.setRanking(loginBean.getRanking());
        this.setRoles(loginBean.getRoles());
        this.setSalt(loginBean.getSalt());
        this.setSchool(loginBean.getSchool());
        this.setScore(loginBean.getScore());
        this.setSubjectType(loginBean.getSubjectType());
        this.setType(loginBean.getType());
        this.setUsername(loginBean.getUsername());
        this.setPassword(loginBean.getPassword());
        this.setVipLevel(loginBean.getVipLevel());
        this.setWeixin(loginBean.getWeixin());
    }

    public void saveLoginBean(LoginBean loginBean) {
        setInstance(loginBean);
        save();
    }

    public void save() {
        try {
            JdryPersistence.saveObject(YXXApplication.getInstance().getApplicationContext(), JSON.toJSONString(instance), YXXConstants.LOGIN_INFO_SERIALIZE_KEY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearInstance() {
        this.setAddress(empty);
        this.setAdministrator(false);
        this.setBanned(false);
        this.setBirthday(0);
        this.setClassGrade(empty);
        this.setCompanyId(empty);
        this.setCreateDate(0);
        this.setDepts(empty);
        this.setEmail(empty);
        this.setEnabled(false);
        this.setEname(empty);
        this.setHeadImg(empty);
        this.setLatestLoginIp(empty);
        this.setLatestLoginTime(0);
        this.setMale(false);
        this.setMobile(empty);
        this.setNation(empty);
        this.setNickName(empty);
        this.setRanking(empty);
        this.setRoles(empty);
        this.setSalt(empty);
        this.setSchool(empty);
        this.setScore(empty);
        this.setSubjectType(0);
        this.setType(empty);
        this.setUsername(empty);
        this.setPassword(empty);
        this.setVipLevel(0);
        this.setWeixin(empty);
        this.setIsHeartTest(empty);
        this.setWishProvince(empty);
        this.setHasReport(false);
        this.setWeiXinUnionid(empty);
        this.setWeiXinHeadImg(empty);
    }

    public String getVipLevelDesc() {
        String desc;
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
                break;
        }
        return desc;
    }

    public int getLevelImage() {
        int imageIndex;
        switch (this.getVipLevel()) {
            case 1:
                imageIndex = R.drawable.pu_user; //"普通会员";
                break;
            case 2:
                imageIndex = R.drawable.vip_silver3x; //"银卡会员";
                break;
            case 3:
                imageIndex = R.drawable.vip3x; //"金卡会员";
                break;
            default:
                imageIndex = R.drawable.pu_user; //"游客";
                break;
        }
        return imageIndex;
    }

    public String getAdmissionRiskNum() {
        return admissionRiskNum;
    }

    public void setAdmissionRiskNum(String admissionRiskNum) {
        this.admissionRiskNum = admissionRiskNum;
    }

    public String getHeartTestNum() {
        return heartTestNum;
    }

    public void setHeartTestNum(String heartTestNum) {
        this.heartTestNum = heartTestNum;
    }

    public String getIsHeartTest() {
        return isHeartTest;
    }

    public void setIsHeartTest(String isHeartTest) {
        this.isHeartTest = isHeartTest;
    }

    public String getIsLive() {
        return isLive;
    }

    public void setIsLive(String isLive) {
        this.isLive = isLive;
    }

    public String getSameScoreToNum() {
        return sameScoreToNum;
    }

    public void setSameScoreToNum(String sameScoreToNum) {
        this.sameScoreToNum = sameScoreToNum;
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
        String wlDesc1 = wlDesc;
    }

    public String getWishUniversity() {
        return wishUniversity;
    }

    public LoginBean setWishUniversity(String wishUniversity) {
        this.wishUniversity = wishUniversity;
        return instance;
    }

    public String getWishProvince() {
        return wishProvince;
    }

    public LoginBean setWishProvince(String wishProvince) {
        this.wishProvince = wishProvince;
        return instance;
    }

    public String getHeartTest() {
        return isHeartTest;
    }

    public LoginBean setHeartTest(String heartTest) {
        this.isHeartTest = heartTest;
        return instance;
    }

    public boolean isHasReport() {
        return hasReport;
    }

    public LoginBean setHasReport(boolean hasReport) {
        this.hasReport = hasReport;
        return instance;
    }

    public String getWeiXinHeadImg() {
        return weiXinHeadImg;
    }

    public void setWeiXinHeadImg(String weiXinHeadImg) {
        this.weiXinHeadImg = weiXinHeadImg;
    }

    public String getWeiXinUnionid() {
        return weiXinUnionid;
    }

    public void setWeiXinUnionid(String weiXinUnionid) {
        this.weiXinUnionid = weiXinUnionid;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public int getVipLevelTmp() {
        return vipLevelTmp;
    }

    public void setVipLevelTmp(int vipLevelTmp) {
        this.vipLevelTmp = vipLevelTmp;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }


}
