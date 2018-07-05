package com.ttt.qx.qxcall;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.common.util.log.LogUtil;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.SDKOptions;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.avchat.AVChatManager;
import com.netease.nimlib.sdk.avchat.constant.AVChatControlCommand;
import com.netease.nimlib.sdk.avchat.model.AVChatData;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.CustomNotification;
import com.netease.nimlib.sdk.uinfo.UserInfoProvider;
import com.netease.nimlib.sdk.uinfo.UserService;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;
import com.ttt.qx.qxcall.constant.CommonConstant;
import com.ttt.qx.qxcall.database.NotifyDao;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.NotifyBean;
import com.ttt.qx.qxcall.dbbean.UserBean;
import com.ttt.qx.qxcall.dialog.ReceiveGiftDialog;
import com.ttt.qx.qxcall.function.helper.SessionHelper;
import com.ttt.qx.qxcall.function.home.view.MainActivity;
import com.ttt.qx.qxcall.function.login.model.entity.AddressEntity;
import com.ttt.qx.qxcall.function.login.model.entity.JsonBean;
import com.ttt.qx.qxcall.function.message.entity.GiftSendNotify;
import com.ttt.qx.qxcall.function.voice.AVChatActivity;
import com.ttt.qx.qxcall.function.voice.AVChatProfile;
import com.ttt.qx.qxcall.function.voice.DemoCache;
import com.ttt.qx.qxcall.receiver.PhoneCallStateObserver;
import com.ttt.qx.qxcall.utils.CustomActivityManager;
import com.ttt.qx.qxcall.utils.DeviceUtil;
import com.ttt.qx.qxcall.utils.FileUtils;
import com.ttt.qx.qxcall.utils.MD5Util;
import com.ttt.qx.qxcall.utils.SystemUtil;
import com.ttt.qx.qxcall.utils.ToastUtil;
import com.ttt.qx.qxcall.utils.crash.CrashHandler;
import com.ttt.qx.qxcall.utils.crash.CrashListener;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.ttt.qx.qxcall.database.XUtil.BUFFER_SIZE;
import static com.ttt.qx.qxcall.database.XUtil.DB_NAME;
import static com.ttt.qx.qxcall.database.XUtil.DB_PATH;


/**
 * Created by wyd on 2017/9/25.
 */

public class QXCallApplication extends MultiDexApplication {
    //应用工作根目录常量字符串
    public static final String basePath = Environment.getExternalStorageDirectory().getPath() + CommonConstant.WORK_SPACE_PATH;
    public boolean isDown = false;
    public boolean isRun = true;
    /**
     * 屏幕宽度
     */
    public static int screenWidth = 0;
    /**
     * 屏幕高度
     */
    public static int screenHeight = 0;
    public static UMShareAPI mUmShareAPI;
    //应用是否处于登录状态标记
    public static boolean login = false;
    private static Application application;
    public static final String[] PERMISSIONS = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO, Manifest.permission.GET_ACCOUNTS,
            Manifest.permission.READ_PHONE_STATE, Manifest.permission.CALL_PHONE, Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.BODY_SENSORS, Manifest.permission.SEND_SMS, Manifest.permission.CHANGE_WIFI_STATE
            , Manifest.permission.SYSTEM_ALERT_WINDOW};//PERMISSIONS
    private NotifyDao notifyDao;
    private Gson gson;
    //应用当前 正在显示的视图对象
    public static Activity mLastActivity;//上次实例
    public static Activity mActivity;//上次实例
    //应用当前语音通话网易云id
    public static String accid;
    //某次通话时间
    public static long baseTime = 0;
    public static List<AddressEntity> mAddressEntity = new ArrayList<>();
    public static boolean listenToLogin = false;
    public static ArrayList<JsonBean> options1Items = new ArrayList<>();
    public static ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    public static ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        //Umeng第三方登录相关初始化。
        PlatformConfig.setWeixin("wx1675874735a7dea6", "198bf45708d2302f60868aba15980c8c");
        PlatformConfig.setQQZone("1106479676", CommonConstant.QQ_APP_KEY);
        mUmShareAPI = UMShareAPI.get(this);
        MultiDex.install(this);
        //初始化数据库
        initDataBaseFile();
        x.Ext.init(this);//Xutils初始化
        deviceAndLogHandle();
        checkUserLoginStatus();
        //解决7.0拍照问题 闪退问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        //友盟统计初始化配置
        MobclickAgent.setScenarioType(getApplicationContext(), MobclickAgent.EScenarioType.E_UM_NORMAL);
        //网易云IM 相关配置
        DemoCache.setContext(getApplication());
        logigIM(this, loginInfo());
        //注册来点监听
        enableAVChat();
        notifyDao = new NotifyDao();
        gson = new Gson();
        //注册activity生命周期状态回调
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                CustomActivityManager.getInstance().setTopActivity(activity);
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {
//                CustomActivityManager.getInstance().setTopActivity(null);
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
        new Thread() {
            @Override
            public void run() {
                initJsonData();
            }
        }.start();
    }

    public void logigIM(Context context, LoginInfo loginInfo) {
        // NIMClient SDK初始化（启动后台服务，若已经存在用户登录信息， SDK 将完成自动登录）
        NIMClient.init(context, loginInfo, options());
        if (inMainProcess()) {
            // 如果有自定义通知是作用于全局的，不依赖某个特定的 Activity，那么这段代码应该在 Application 的 onCreate 中就调用
            NIMClient.getService(MsgServiceObserve.class).observeCustomNotification(new Observer<CustomNotification>() {
                @Override
                public void onEvent(CustomNotification message) {
                    // 在这里处理自定义通知。
                    String content = message.getContent();
                    System.out.println("content = " + content);
                    //通知包含 礼物赠送、用户充值 礼物赠送既要保存到本地数据库 又要给出系统提示和动画
                    //保存本地数据操作
                    try {
                        JSONObject jsonObject = new JSONObject(content);
                        if (jsonObject.has("msg_type")) {
                            NotifyBean notifyBean = new NotifyBean();
                            if (jsonObject.getInt("msg_type") == 2) {//礼物通知
                                GiftSendNotify giftSendNotify = gson.fromJson(content, GiftSendNotify.class);
                                notifyBean.setContent(giftSendNotify.getMsg());
                                notifyBean.setMsgType(String.valueOf(giftSendNotify.getMsg_type()));
                                notifyBean.setCreatTime(giftSendNotify.getTime());
                                notifyBean.setTitle("确幸礼物通知");
                                //toast提示
                                ToastUtil.showToast(getApplicationContext(), giftSendNotify.getMsg());
                                //系统级 礼物动画展示 对话框
                                Activity topActivity = CustomActivityManager.getInstance().getTopActivity();
                                Map<String, String> receiveMap = new HashMap<String, String>();
                                receiveMap.put("avator", giftSendNotify.getMember_info().getAvatar());
                                receiveMap.put("gift_avator", giftSendNotify.getGift_info().getGift_img());
                                receiveMap.put("nick_name", giftSendNotify.getMember_info().getNick_name());
                                receiveMap.put("gift_num", giftSendNotify.getGift_info().getGit_num());
                                ReceiveGiftDialog.showReceiveGiftDialog(topActivity, receiveMap, new ReceiveGiftDialog.OnComponentClickListener() {
                                    @Override
                                    public void onCancle() {
                                    }

                                    @Override
                                    public void onSend(String gift_id) {
                                    }
                                });
                            } else {//其他系统通知（充值成功、系统消息推送等）1
                                notifyBean.setContent(jsonObject.getString("msg"));
                                notifyBean.setMsgType(String.valueOf(jsonObject.getInt("msg_type")));
                                notifyBean.setCreatTime(jsonObject.getString("time"));
                                notifyBean.setTitle(jsonObject.getString("type"));
                            }
                            notifyDao.add(notifyBean);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                    TipDialog.showCenterTipDialog(getApplicationContext(), content, new TipDialog.OnComponentClickListener() {
//                        @Override
//                        public void onCancle() {
//                        }
//
//                        @Override
//                        public void onConfirm() {
//
//                        }
//                    }, false);
                }
            }, true);
            //UIKit初始化
            // 初始化 NimUIKit sdk
            NimUIKit.init(context);
            //首先获取所有用户账号
//            List<NimUserInfo> users = NIMClient.getService(UserService.class).getAllUserInfo();
//            if (users != null) {
//                ArrayList<String> accounts = new ArrayList<>();
//                for (NimUserInfo user : users) {
//                    accounts.add(user.getAccount());
//                }
//                //从服务获取 再缓存用户数据
//                NIMClient.getService(UserService.class).fetchUserInfo(accounts).setCallback(new RequestCallbackWrapper<List<NimUserInfo>>() {
//                    @Override
//                    public void onResult(int i, List<NimUserInfo> nimUserInfos, Throwable throwable) {
//                        NimUIKit.init(application, new UserInfoProvider() {
//                            @Override
//                            public UserInfo getUserInfo(String account) {
//                                //从本地数据中 查询用户对象
//                                NimUserInfo userInfo = NIMClient.getService(UserService.class).getUserInfo(account);
//                                return userInfo;
//                            }
//
//                            @Override
//                            public int getDefaultIconResId() {
//                                return 0;
//                            }
//
//                            @Override
//                            public Bitmap getAvatarForMessageNotifier(String s) {
//                                return null;
//                            }
//
//                            @Override
//                            public String getDisplayNameForMessageNotifier(String s, String s1, SessionTypeEnum sessionTypeEnum) {
//                                return null;
//                            }
//
//                            @Override
//                            public Bitmap getTeamIcon(String s) {
//                                return null;
//                            }
//                        }, new DefaultContactProvider());
//                    }
//                });
//            }
            // 可选定制项
            // 注册定位信息提供者类（可选）,如果需要发送地理位置消息，必须提供。
            // demo中使用高德地图实现了该提供者，开发者可以根据自身需求，选用高德，百度，google等任意第三方地图和定位SDK。
//        NimUIKit.setLocationProvider(new LocationProvider() {
//            @Override
//            public void requestLocation(Context context, Callback callback) {
//            }
//
//            @Override
//            public void openMap(Context context, double longitude, double latitude, String address) {
//            }
//        });

            // 会话窗口的定制: 示例代码可详见demo源码中的SessionHelper类。
            // 1.注册自定义消息附件解析器（可选）
            // 2.注册各种扩展消息类型的显示ViewHolder（可选）
            // 3.设置会话中点击事件响应处理（一般需要）
            SessionHelper.init();
//
//        // 通讯录列表定制：示例代码可详见demo源码中的ContactHelper类。
//        // 1.定制通讯录列表中点击事响应处理（一般需要，UIKit 提供默认实现为点击进入聊天界面)
//        ContactHelper.init();
            NimUIKit.doLogin(loginInfo, new RequestCallback<LoginInfo>() {
                @Override
                public void onSuccess(LoginInfo param) {
                    //登录成功
                    String account = param.getAccount();
                }

                @Override
                public void onFailed(int code) {
                    //登录失败
                }

                @Override
                public void onException(Throwable exception) {
                    //登录异常
                }

            });
            if (loginInfo != null) {
                DemoCache.setAccount(loginInfo.getAccount());
            }
        }

    }

    private void initJsonData() {   //解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        //  获取json数据
        String JsonData = MD5Util.JsonFileReader.getJson(this, "province_data.json");
        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市

                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {

                    for (int d = 0; d < jsonBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getCityList().get(c).getArea().get(d);

                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }
    }

    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }

    /**
     * 获取省份城市数据
     *
     * @param context
     * @return
     */
    public static List<AddressEntity> getAddress(Context context) {
        List<AddressEntity> addressEntitys = new ArrayList<>();
        Gson gson = new Gson();
        try {
            InputStream inputStream = context.getResources().getAssets().open("address.txt");
            String addressContent = FileUtils.getFileContent(inputStream);
            try {
                JSONArray jsonArray = new JSONArray(addressContent);
                for (int i = 0; i < jsonArray.length(); i++) {
                    String s = jsonArray.get(i).toString();
                    AddressEntity addressEntity = gson.fromJson(s, AddressEntity.class);
                    addressEntitys.add(addressEntity);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return addressEntitys;
    }

    /**
     * 显示接收到礼物动画
     *
     * @param giftSendNotify
     */
    private void showReceiveGiftAnimation(GiftSendNotify giftSendNotify) {
        Context context = getApplicationContext();
        Map<String, String> receiveMap = new HashMap<String, String>();
        receiveMap.put("avator", giftSendNotify.getMember_info().getAvatar());
        receiveMap.put("gift_avator", giftSendNotify.getGift_info().getGift_img());
        receiveMap.put("nick_name", giftSendNotify.getMember_info().getNick_name());
        receiveMap.put("gift_num", giftSendNotify.getGift_info().getGit_num());
        View view = View.inflate(getApplicationContext(), R.layout.receive_gift_dialog, null);
        CircleImageView user_head_icon_iv = (CircleImageView) view.findViewById(R.id.user_head_icon_iv);
        TextView user_nick_name_tv = (TextView) view.findViewById(R.id.user_nick_name_tv);
        ImageView gift_iv = (ImageView) view.findViewById(R.id.gift_iv);
        TextView gift_num = (TextView) view.findViewById(R.id.gift_num);
        //设置控件
        Glide.with(context).load(receiveMap.get("avator"))
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(user_head_icon_iv);
        Glide.with(context).load(receiveMap.get("gift_avator"))
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(gift_iv);
        user_nick_name_tv.setText(receiveMap.get("nick_name"));
        gift_num.setText(receiveMap.get("gift_num"));
        WindowManager mWindowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams mLayoutParams = new WindowManager.LayoutParams();
        mLayoutParams.x = 0;
        mLayoutParams.y = 0;
        mLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mLayoutParams.gravity = Gravity.CENTER;
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        mWindowManager.addView(view, mLayoutParams);

        TranslateAnimation animation = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, -100, Animation.RELATIVE_TO_PARENT, 800,
                Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0);
        animation.setDuration(4500);
        animation.setFillAfter(false);
        animation.setRepeatCount(0);
        view.setAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mWindowManager.removeViewImmediate(view);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animation.start();
    }

    /**
     * 用户进入应用程序检测是否处于登录状态给出全局标记
     */
    public static void checkUserLoginStatus() {
        //登录状态检验
        UserDao userDao = new UserDao();
        UserBean userBean = userDao.queryFirstData();
        if (userBean != null) {
            String token = userBean.getToken();
            if (token != null && !"".equals(token)) {
                login = true;
            } else {
                login = false;
            }
        } else {
            login = false;
        }
    }

    /**
     * 判断当前是否处于主进程
     *
     * @return
     */
    public boolean inMainProcess() {
        String packageName = getPackageName();
        String processName = SystemUtil.getProcessName(this);
        return packageName.equals(processName);
    }

    // 如果返回值为 null，则全部使用默认参数。
    private SDKOptions options() {
        SDKOptions options = new SDKOptions();

        // 如果将新消息通知提醒托管给 SDK 完成，需要添加以下配置。否则无需设置。
        StatusBarNotificationConfig config = new StatusBarNotificationConfig();
        config.notificationEntrance = MainActivity.class; // 点击通知栏跳转到该Activity
        config.notificationSmallIconId = R.mipmap.ic_stat_notify_msg;
        // 呼吸灯配置
        config.ledARGB = Color.GREEN;
        config.ledOnMs = 1000;
        config.ledOffMs = 1500;
        // 通知铃声的uri字符串
        config.notificationSound = "android.resource://com.netease.nim.demo/raw/msg";
        options.statusBarNotificationConfig = config;

        // 配置保存图片，文件，log 等数据的目录
        // 如果 options 中没有设置这个值，SDK 会使用下面代码示例中的位置作为 SDK 的数据目录。
        // 该目录目前包含 log, file, image, audio, video, thumb 这6个目录。
        // 如果第三方 APP 需要缓存清理功能， 清理这个目录下面个子目录的内容即可。
        String sdkPath = Environment.getExternalStorageDirectory() + "/" + getPackageName() + "/nim";
        options.sdkStorageRootPath = sdkPath;

        // 配置是否需要预下载附件缩略图，默认为 true
        options.preloadAttach = true;
        // 配置附件缩略图的尺寸大小。表示向服务器请求缩略图文件的大小
        // 该值一般应根据屏幕尺寸来确定， 默认值为 Screen.width / 2
        options.thumbnailSize = (int) (DeviceUtil.getScreenWidth(this) / 2);
        // 用户资料提供者, 目前主要用于提供用户资料，用于新消息通知栏中显示消息来源的头像和昵称
        options.userInfoProvider = new UserInfoProvider() {
            @Override
            public UserInfo getUserInfo(String account) {
                NimUserInfo userInfo = NIMClient.getService(UserService.class).getUserInfo(account);
                return userInfo;
            }

            @Override
            public int getDefaultIconResId() {
                return R.mipmap.nim_avatar_default;
            }

            @Override
            public Bitmap getTeamIcon(String tid) {
                return null;
            }

            @Override
            public Bitmap getAvatarForMessageNotifier(String account) {
                return null;
            }

            @Override
            public String getDisplayNameForMessageNotifier(String account, String sessionId,
                                                           SessionTypeEnum sessionType) {
                return null;
            }
        };
        return options;
    }

    /**
     * 设备分辨率以及Log日志处理
     */
    public static void deviceAndLogHandle() {
        // 未捕获异常处理初始化
        crashHandlerInit();
        //将设备尺寸信息保存
        File deviceFile = new File(basePath + "device.txt");
        try {
            OutputStream outputStream = new FileOutputStream(deviceFile);
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("主屏分辨率:" + DeviceUtil.getScreenHeight(getApplication()) + "x" + DeviceUtil.getScreenWidth(getApplication()));
            stringBuffer.append("\n");
            stringBuffer.append("像素密度Dpi:" + DeviceUtil.getDensityDpi(getApplication()));
//            stringBuffer.append("\n");
//            stringBuffer.append("像素密度:" + getResources().getDisplayMetrics().densityDpi);
            try {
                outputStream.write(stringBuffer.toString().getBytes());
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i("Density", stringBuffer.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // 如果已经存在用户登录信息，返回LoginInfo，否则返回null即可
    private LoginInfo loginInfo() {
        UserDao userDao = new UserDao();
        UserBean userBean = userDao.queryFirstData();
        if (userBean == null) {
            return null;
        } else {
            return new LoginInfo(userBean.getWy_acid(), userBean.getWy_token());
        }
    }

    private void enableAVChat() {
        registerAVChatIncomingCallObserver(true);
    }

    private void registerAVChatIncomingCallObserver(boolean register) {
        AVChatManager.getInstance().observeIncomingCall(new Observer<AVChatData>() {
            @Override
            public void onEvent(AVChatData data) {
                String extra = data.getExtra();
                Log.e("Extra", "Extra Message->" + extra);
                if (PhoneCallStateObserver.getInstance().getPhoneCallState() != PhoneCallStateObserver.PhoneCallStateEnum.IDLE
                        || AVChatProfile.getInstance().isAVChatting()
                        || AVChatManager.getInstance().getCurrentChatId() != 0) {
                    LogUtil.i("msg", "reject incoming call data =" + data.toString() + " as local phone is not idle");
                    AVChatManager.getInstance().sendControlCommand(data.getChatId(), AVChatControlCommand.BUSY, null);
                    return;
                }
                // 有网络来电打开AVChatActivity
                AVChatProfile.getInstance().setAVChatting(true);
                AVChatActivity.launch(getApplication(), data, AVChatActivity.FROM_BROADCASTRECEIVER);
            }
        }, register);
    }

    /**
     * 初始化数据库文件
     */
    private void initDataBaseFile() {
        String dbName = DB_PATH + "/" + DB_NAME;
        try {
            if (!(new File(dbName).exists())) {//判断数据库文件是否存在，若不存在则执行导入，否则直接打开数据库
                InputStream is = getResources().openRawResource(
                        R.raw.qxcall); //欲导入的数据库
                FileOutputStream fos = new FileOutputStream(dbName);
                byte[] buffer = new byte[BUFFER_SIZE];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("Database", "File not found");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("Database", "IO exception");
            e.printStackTrace();
        }
    }

    //获取当前进程名字
    public static String getProcessName(Context cxt, int pid) {
        ActivityManager am = (ActivityManager)
                cxt.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps != null) {
            for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
                if (procInfo.pid == pid) {
                    return procInfo.processName;
                }
            }
        }
        return null;
    }

    /**
     * 未捕获异常处理初始化
     */
    public static void crashHandlerInit() {
        CrashHandler crashHandler = CrashHandler.getInstance();
        String logFolderPath = basePath + CommonConstant.LOG_FOLDER;
        File fileFolder = new File(logFolderPath);
        //日志信息所在文件夹路径
        if (!fileFolder.exists()) {
            fileFolder.mkdirs();
        }
        //日志保存到的文件路径
        String logFilePath = logFolderPath + "log.txt";
        File file = new File(logFilePath);
        try {
            file.createNewFile();
            crashHandler.init(file, new CrashListener() {
                @Override
                public void afterSaveCrash(File file) {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //应用全局Toast
    public static void onToast(String message) {
        ToastUtil.show(application, message, Toast.LENGTH_SHORT);
    }

    public static Application getApplication() {
        return application;
    }

}
