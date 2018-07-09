package com.ttt.qx.qxcall.constant;

import android.os.Environment;

/**
 * todo 该Constant包是项目共享包
 * todo 各功能模块常量类在此包下分别创建
 * 项目共有常量类
 * Created by wyd on 2017/7/19.
 */

public class CommonConstant {
    //网络请求基础网址
    public static final String COMMON_BASE_URL = "http://116.62.217.183/";
    //上传图片基础路径
    public static final String COMMON_UPLOAD_IMG_BASE_URL = "http://116.62.217.183/";
    //网络请求超时时间
    public static final int TIME_OUT = 5;
    //当前项目工作根目录
    public static final String WORK_SPACE_PATH = "/QXCall/";
    //当前项目日志信息保存文件夹
    public static final String LOG_FOLDER = "Log/";
    //应用缓存文件
    public static final String APP_SP_CONFIG = "app_sp_config";
    //应用是否是首次进入缓存key
    public static final String FIRST_ENETR = "first_enetr";
    // 应用程序退出时延
    public static final int EXIT_DELAY = 3000;
    //标识选择首页常量
    public static final int SELECT_HOME = 0;
    //标识选择偷听常量
    public static final int SELECT_LISTEN = 1;
    //标识选择消息常量
    public static final int SELECT_MESSAGE = 3;
    //标识选择发现常量
    public static final int SELECT_FIND = 2;
    //标识选择我的常量
    public static final int SELECT_MINE = 4;
    //手机格式校验正则
    public static final String PHONE_FORMAT_REG = "^((\\+?\\d{2} )?[1-9]\\d{10}(-\\d{1,4})?)?$";
    //手机格式校验正则 中国移动
    public static final String PHONE_FORMAT_REG_YD = "^1(3[4-9]|4[7]|5[0-27-9]|7[0]|7[8]|8[2-478])\\d{8}$";
    //手机格式校验正则 中国联通
    public static final String PHONE_FORMAT_REG_LT = "^1(3[0-2]|4[5]|5[56]|709|7[1]|7[6]|8[56])\\d{8}$";
    //手机格式校验正则 中国电信
    public static final String PHONE_FORMAT_REG_DX = "^1(3[34]|53|77|700|8[019])\\d{8}$";
    //密码格式校验正则
    public static final String PWD_FORMAT_REG = "/^[^\\s]{6,16}$/";
    //登录session ID
    public static final String SESSION_ID = "session_id";
    //登录ACCOUT
    public static final String ACCOUT = "accout";
    //登录PWD
    public static final String PWD = "pwd";
    public static final String EMAIL = "email";
    public static final String QQ_APP_KEY = "3lIvAIzq00bf59r2";
    public static final String QQ_APP_ID = "1106479676";
    public static final String WX_APP_KEY = "198bf45708d2302f60868aba15980c8c";
    public static final String WX_APP_ID = "wx1675874735a7dea6";
    //交易账户默认密码
    public static final String TRADE_ACCOUNT_DEFAUL_PWD = "abc123";
    //FM模块 用户是否点赞标识
    public static final String FM_DIANZAN_FLAG = "fm_dianzan_flag";
    //应用Toast显示时间 ms
    public static final int TOAST_SHOW_TIME = 1000;
    //人民币符号
    public static final String REN_MIN_COIN_SING = "¥";
    //
    public static final String QQ = "客服QQ:2627765937";
    //
    public static final String PHONE = "客服电话:18837117311";
    //
    public static final String SHARE = "http://116.62.217.183/share/";

    public static final String BASE_PATH = Environment.getExternalStorageDirectory() + "/ttt/pp/";
    public static final String PHOTO_URL = BASE_PATH+"images";

    public static final String SA_GOU_LIANG_LIST="api/friend/dogList";                     //撒狗粮列表  type[1讲故事 2发图片]   page
    public static final String SA_GOU_LIANG_LIKE="api/friend/dogLike";                     //撒狗粮点赞  user_id  sid
    public static final String SA_GOU_LIANG_COMMIT="api/friend/publishDog";               //发布撒狗粮  user_id  type[1讲故事 2发图片]  content[type为1必填]   file[type为2必填]
    public static final String RULE="/api/lucres";                                            //网页规则    aid [1关于我们/2我的收益规则3/萨狗粮规则4/萨狗粮奖品5使用说明/6vip特权]
    public static final String CARD_LIST="api/friend/cardList";                             //帖子列表  user_id  page   type [1老司机开车 2闺蜜私房话 3两性研究所 4剧本专区 5撩妹区 6撩汉区]
    public static final String CARD_DETAIL="api/friend/carddetail";                         //帖子详情  page   tid
    public static final String CARD_LIKE="api/friend/likes";                                 //帖子/评论点赞  user_Id
    public static final String NOTICE_LIST="api/getNotice";                                  //公告  type [0公告 1随机话题 2老司机开车 3闺蜜私房语4两性研究社]
    public static final String PUSH_COMMENT="api/friend/pushComment";                      //帖子评论  user_id content tid



    //朋友圈
    public static final String FRIEND_Q_LIST="/api/friend/list";// 朋友圈
    public static final String FRIEND_Q_friend_list="/api/friend/friend_list";// 好友朋友圈
    public static final String FRIEND_Q_reply="/api/friend/reply";// 评论朋友动态
    public static final String FRIEND_Q_zan="/api/friend/zan";// 朋友圈点赞
    public static final String FRIEND_Q_send_gift="/api/friend/send_gift";// 给朋友圈送礼物
    public static final String FRIEND_Q_backgroundqh="/api/backgroundqh";// 更换朋友圈背景图


}
