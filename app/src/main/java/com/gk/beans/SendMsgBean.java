package com.gk.beans;

/**
 * Created by JDRY-SJM on 2017/12/2.
 */

public class SendMsgBean {

    /**
     * createTime : 1512185669007
     * fromUser : 15285630464
     * id : 402881fa601548b50160154a25da0000
     * isRead : 0
     * message : 测试发送消息
     * messageType : 1
     * toUser : buxc
     */

    private long createTime;
    private String fromUser;
    private String id;
    private int isRead;
    private String message;
    private int messageType;
    private String toUser;

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }
}
