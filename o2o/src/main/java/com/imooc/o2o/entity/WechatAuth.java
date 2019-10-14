package com.imooc.o2o.entity;

import java.io.Serializable;
import java.util.Date;
//微信账户
public class WechatAuth implements Serializable {
    private Long wechatAuthId;
//    微信号和公众号绑定的标识
    private String openId;
    private Date createTime;
//    用于获取用户id,id也是外键
    private PersonInfo personInfo;

    public Long getWechatAuthId() {
        return wechatAuthId;
    }

    public void setWechatAuthId(Long wechatAuthId) {
        this.wechatAuthId = wechatAuthId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public PersonInfo getPersonInfo() {
        return personInfo;
    }

    public void setPersonInfo(PersonInfo personInfo) {
        this.personInfo = personInfo;
    }

    @Override
    public String toString() {
        return "WechatAuth{" +
                "wechatAuthId=" + wechatAuthId +
                ", openId='" + openId + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
