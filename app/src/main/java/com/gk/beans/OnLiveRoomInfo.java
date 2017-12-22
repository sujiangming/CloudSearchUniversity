package com.gk.beans;

import java.util.List;

/**
 * Created by JDRY-SJM on 2017/12/22.
 */

public class OnLiveRoomInfo {

    /**
     * fans : []
     * fansSpeak : [{"fansSpeak":"æµ\u008bè¯\u0095","id":"2c948a83607967190160796973ce0000","liveRoomId":"2c93654460769cc2016076effe1d0000","nickName":"tttttt","speakTime":1513865442000,"username":"15285630464"}]
     * headImg : http://192.168.1.29:8080/file/13648514015/B6FA7A6AD1674D459877ECD72350D795.jpg
     * id : 2c93654460769cc2016076effe1d0000
     * liveCrossLogo : 13648514015/139F485F8E744E7C91A3396A2C751C3D.jpg
     * liveName : 直播
     * livePullUrl : http://zhibo.yunxunxiao.com/yunxunxiao/13648514015--LR658KT0960K7696WYR8_lsd.flv
     * livePushUrl : rtmp://video-center.alivecdn.com/yunxunxiao/13648514015--LR658KT0960K7696WYR8?vhost=zhibo.yunxunxiao.com
     * liveStartTime : 1513823928000
     * liveStatus : 1
     * liveUser : 13648514015
     * liveVerticalLogo : 13648514015/9C41C559E57148A6A6D8195EFC6808F2.jpg
     * nickName : 未填写
     * onlineNum : 0
     */

    private String headImg;
    private String id;
    private String liveCrossLogo;
    private String liveName;
    private String livePullUrl;
    private String livePushUrl;
    private long liveStartTime;
    private int liveStatus;
    private String liveUser;
    private String liveVerticalLogo;
    private String nickName;
    private String onlineNum;
    private List<Fans> fans;
    private List<FansSpeakBean> fansSpeak;

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLiveCrossLogo() {
        return liveCrossLogo;
    }

    public void setLiveCrossLogo(String liveCrossLogo) {
        this.liveCrossLogo = liveCrossLogo;
    }

    public String getLiveName() {
        return liveName;
    }

    public void setLiveName(String liveName) {
        this.liveName = liveName;
    }

    public String getLivePullUrl() {
        return livePullUrl;
    }

    public void setLivePullUrl(String livePullUrl) {
        this.livePullUrl = livePullUrl;
    }

    public String getLivePushUrl() {
        return livePushUrl;
    }

    public void setLivePushUrl(String livePushUrl) {
        this.livePushUrl = livePushUrl;
    }

    public long getLiveStartTime() {
        return liveStartTime;
    }

    public void setLiveStartTime(long liveStartTime) {
        this.liveStartTime = liveStartTime;
    }

    public int getLiveStatus() {
        return liveStatus;
    }

    public void setLiveStatus(int liveStatus) {
        this.liveStatus = liveStatus;
    }

    public String getLiveUser() {
        return liveUser;
    }

    public void setLiveUser(String liveUser) {
        this.liveUser = liveUser;
    }

    public String getLiveVerticalLogo() {
        return liveVerticalLogo;
    }

    public void setLiveVerticalLogo(String liveVerticalLogo) {
        this.liveVerticalLogo = liveVerticalLogo;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getOnlineNum() {
        return onlineNum;
    }

    public void setOnlineNum(String onlineNum) {
        this.onlineNum = onlineNum;
    }

    public List<Fans> getFans() {
        return fans;
    }

    public void setFans(List<Fans> fans) {
        this.fans = fans;
    }

    public List<FansSpeakBean> getFansSpeak() {
        return fansSpeak;
    }

    public void setFansSpeak(List<FansSpeakBean> fansSpeak) {
        this.fansSpeak = fansSpeak;
    }

    public static class FansSpeakBean {
        /**
         * fansSpeak : æµè¯
         * id : 2c948a83607967190160796973ce0000
         * liveRoomId : 2c93654460769cc2016076effe1d0000
         * nickName : tttttt
         * speakTime : 1513865442000
         * username : 15285630464
         */

        private String id;
        private String fansSpeak;
        private String liveRoomId;
        private String nickName;
        private long speakTime;
        private String username;

        public String getFansSpeak() {
            return fansSpeak;
        }

        public void setFansSpeak(String fansSpeak) {
            this.fansSpeak = fansSpeak;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLiveRoomId() {
            return liveRoomId;
        }

        public void setLiveRoomId(String liveRoomId) {
            this.liveRoomId = liveRoomId;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public long getSpeakTime() {
            return speakTime;
        }

        public void setSpeakTime(long speakTime) {
            this.speakTime = speakTime;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }

    public static class Fans {
        /**
         * fansSpeak : æµè¯
         * id : 2c948a83607967190160796973ce0000
         * liveRoomId : 2c93654460769cc2016076effe1d0000
         * nickName : tttttt
         * speakTime : 1513865442000
         * username : 15285630464
         */
        private String id;
        private String headImg;
        private String liveRoomId;
        private String username;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public String getLiveRoomId() {
            return liveRoomId;
        }

        public void setLiveRoomId(String liveRoomId) {
            this.liveRoomId = liveRoomId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
