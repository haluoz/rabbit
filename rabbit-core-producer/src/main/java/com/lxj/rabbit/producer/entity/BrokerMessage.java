package com.lxj.rabbit.producer.entity;

import com.lxj.rabbit.api.Message;

import java.io.Serializable;
import java.util.Date;

/**
 * 可靠性消息尸体列
 * @author Xingjing.Li
 * @since 2021/8/16
 */
public class BrokerMessage implements Serializable {
    private static final long serialVersionUID = 8292534046297121387L;
    private String messageId;
    private Message message;
    private Integer tryCount = 0;
    private String status;
    private Date nextRetry;
    private Date createTime;
    private Date updateTime;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Integer getTryCount() {
        return tryCount;
    }

    public void setTryCount(Integer tryCount) {
        this.tryCount = tryCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getNextRetry() {
        return nextRetry;
    }

    public void setNextRetry(Date nextRetry) {
        this.nextRetry = nextRetry;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
