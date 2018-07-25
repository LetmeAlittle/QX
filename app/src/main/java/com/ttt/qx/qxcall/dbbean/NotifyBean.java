package com.ttt.qx.qxcall.dbbean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 本地数据库操作实体类
 * Created by wyd on 2017/8/14.
 */

@Table(name = "NotifyBean")
public class NotifyBean {
    @Column(name = "ID", isId = true, autoGen = true)
    public Integer id;
    @Column(name = "creatTime")
    private String creatTime;
    @Column(name = "content")
    private String content;
    @Column(name = "msgType")
    private String msgType;
    @Column(name = "title")
    private String title="砰砰通知";

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }
}
