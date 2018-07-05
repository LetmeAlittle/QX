package com.ttt.qx.qxcall.dbbean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 本地数据库操作实体类
 * Created by wyd on 2017/8/14.
 */

@Table(name = "UserBean")
public class UserBean {
    @Column(name = "ID", isId = true, autoGen = true)
    public Integer id;
    @Column(name = "userId")
    private Integer userId;
    @Column(name = "nick_name")
    private String nick_name;
    @Column(name = "member_avatar")
    private String member_avatar;
    @Column(name = "level")
    private String level;
    @Column(name = "wy_acid")
    private String wy_acid;
    @Column(name = "wy_token")
    private String wy_token;
    @Column(name = "token")
    private String token;
    @Column(name = "member_sex")
    private String member_sex;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getMember_avatar() {
        return member_avatar;
    }

    public void setMember_avatar(String member_avatar) {
        this.member_avatar = member_avatar;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getWy_acid() {
        return wy_acid;
    }

    public void setWy_acid(String wy_acid) {
        this.wy_acid = wy_acid;
    }

    public String getWy_token() {
        return wy_token;
    }

    public void setWy_token(String wy_token) {
        this.wy_token = wy_token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMember_sex() {
        return member_sex;
    }

    public void setMember_sex(String member_sex) {
        this.member_sex = member_sex;
    }
}
