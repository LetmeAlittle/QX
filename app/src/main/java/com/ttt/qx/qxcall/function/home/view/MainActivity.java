package com.ttt.qx.qxcall.function.home.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ttt.qx.qxcall.QXCallApplication;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.adapter.ViewPagerAdapter;
import com.ttt.qx.qxcall.constant.CommonConstant;
import com.ttt.qx.qxcall.dialog.ReceiveGiftDialog;
import com.ttt.qx.qxcall.eventbus.ReCeiveGift;
import com.ttt.qx.qxcall.eventbus.SetSelectItem;
import com.ttt.qx.qxcall.function.home.presenter.IMainPresenter;
import com.ttt.qx.qxcall.function.home.presenter.MainPresenterImpl;
import com.ttt.qx.qxcall.function.message.entity.GiftSendNotify;
import com.ttt.qx.qxcall.function.start.BaseActivity;
import com.ttt.qx.qxcall.pager.BasePager;
import com.ttt.qx.qxcall.pager.FragmentViewPager;
import com.ttt.qx.qxcall.pager.MessagePager;
import com.ttt.qx.qxcall.utils.DeviceUtil;
import com.ttt.qx.qxcall.utils.Exit;
import com.ttt.qx.qxcall.utils.IntentUtil;
import com.ttt.qx.qxcall.utils.PermissionUtil;
import com.ttt.qx.qxcall.utils.ToastUtil;
import com.ttt.qx.qxcall.utils.crash.CrashHandler;
import com.ttt.qx.qxcall.utils.crash.CrashListener;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMShareAPI;
import com.ysxsoft.qxerkai.view.activity.NLoginActivity;
import com.ysxsoft.qxerkai.view.fragment.FivePage;
import com.ysxsoft.qxerkai.view.fragment.OnePage;
import com.ysxsoft.qxerkai.view.fragment.ThreePage;
import com.ysxsoft.qxerkai.view.fragment.TwoPage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ttt.qx.qxcall.QXCallApplication.PERMISSIONS;
import static com.ttt.qx.qxcall.QXCallApplication.basePath;
import static com.ttt.qx.qxcall.QXCallApplication.deviceAndLogHandle;

/**
 * 应用首页主视图
 * Created by wyd on 2017/7/19.
 */
public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, IMainView {
    // 应用程序退出对象
    private Exit exit = null;
    //应用程序对象
    private QXCallApplication mQXCallApplication;
    //各功能模块容器类viewPager对象
    public FragmentViewPager fragmentViewPager;
    //底部菜单条目radiogroup对象
    public RadioGroup radio_group;
    //底部分割线
    public View view_line;
    //全局的公共的FM控制按钮
    public ImageView fm_iv;
    //radioGroup选中的条目,默认是电报
    public static int selectedPostion = CommonConstant.SELECT_HOME;
    //radioGroup上次选中的条目
//    public static int lastedPostion = CommonConstant.SELECT_HOME;
    //创建BasePager集合，每个菜单条目对应的basePager对象
    private List<BasePager> pagerList;
    //当前上下文
    private Context context;
    //菜单容器类viewPagerAdapter对象
    private ViewPagerAdapter viewPagerAdapter;
    //  DisplayMetrics displayMetrics
    private DisplayMetrics displayMetrics = null;
    private IMainPresenter mMainPresenter;
    private PermissionUtil mPermissionUtil;
    private Animation mCircle_anim;
    public static final int START_CALENDAR_LOGIN = 1;
    //标记是否是代码设置切换页签菜单
    private boolean codeSetting = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setBackEnable(false);
        initStatusBar();
        initView();
        initData();
    }

    /**
     * 初始化view
     */
    private void initView() {
        radio_group = (RadioGroup) findViewById(R.id.radio_group);
        view_line = findViewById(R.id.view_line);
        fragmentViewPager = (FragmentViewPager) findViewById(R.id.fragmentview_pager);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        QXCallApplication.mLastActivity = this;
        QXCallApplication.mActivity = this;
        //注册EventBus
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        //权限处理
        mPermissionUtil = new PermissionUtil(this, this);
        //任何一个权限缺少都得请求
        if (mPermissionUtil.lacksPermissions(PERMISSIONS)) {
            mPermissionUtil.requestPermissions(PERMISSIONS, 1111, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        exit = new Exit();
        context = this;
        //初始化mMainPresenter
        mMainPresenter = new MainPresenterImpl(this, this);
        mQXCallApplication = (QXCallApplication) getApplication();
        displayMetrics = new DisplayMetrics();
        // 获取屏幕的宽度和高度;
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        //设置保存用户手机屏幕宽高
        mQXCallApplication.screenWidth = displayMetrics.widthPixels;
        mQXCallApplication.screenHeight = displayMetrics.heightPixels;
        radio_group.check(R.id.rb_telegram);
        radio_group.setOnCheckedChangeListener(this);
        //初始化BasePager集合
        pagerList = new ArrayList<BasePager>();
        //在集合中添加相应的BasePager元素
        addCollectionElemTO(pagerList);
        //设置viewpager当前条目为第一条
        viewPagerAdapter = new ViewPagerAdapter(pagerList);
        fragmentViewPager.setAdapter(viewPagerAdapter);
        fragmentViewPager.setCurrentItem(CommonConstant.SELECT_HOME, false);
        pagerList.get(CommonConstant.SELECT_HOME).initData();
        //辅助打印设备像素密度以及分辨率
        Log.i("Density", DeviceUtil.getDensityDpi(this) + "===" + DeviceUtil.getScreenHeight(this) + "x" + DeviceUtil.getScreenWidth(this));
    }

    @Override
    public void onBackPressed() {
        if (exit.isExit()) {
            //退出之前保存U盟统计数据
            MobclickAgent.onKillProcess(this);
            //如果已经提示要退出一个用程序，则退出
            onFinish();
            //杀死该进程
            android.os.Process.killProcess(android.os.Process.myPid());
        } else {
            onToast(getResources().getString(R.string.exit_show_promt));
            exit.doExitInSeconds();
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        ScaleAnimation animation = new ScaleAnimation(
                0.8f, 1.1f, 0.8f, 1.1f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        );
        animation.setDuration(120);
        //默认选择项是第一项
        if (!codeSetting) {
//            //上次选中位置保存
//            if (selectedPostion != CommonConstant.SELECT_FIND) {
//                lastedPostion = selectedPostion;
//            }
            group.getChildAt(selectedPostion).clearAnimation();
            switch (checkedId) {//这里的checkedId是点击变化后的最终选择的radioButton
                case R.id.rb_telegram:
                    selectedPostion = CommonConstant.SELECT_HOME;
                    // 以view中心为缩放点，由初始状态放大两倍
                    group.getChildAt(selectedPostion).startAnimation(animation);
                    break;
                case R.id.rb_listen:
                    selectedPostion = CommonConstant.SELECT_LISTEN;
                    group.getChildAt(selectedPostion).startAnimation(animation);
                    break;
                case R.id.rb_message:
                    selectedPostion = CommonConstant.SELECT_MESSAGE;
                    group.getChildAt(selectedPostion).startAnimation(animation);
                    break;
                case R.id.rb_find:
                    selectedPostion = CommonConstant.SELECT_FIND;
                    group.getChildAt(selectedPostion).startAnimation(animation);
                    break;
                case R.id.rb_mine:
                    selectedPostion = CommonConstant.SELECT_MINE;
                    group.getChildAt(selectedPostion).startAnimation(animation);
                    break;
            }
            setSelection();
        }
        codeSetting = false;
    }

    /**
     * 设置选择条目
     */
    private void setSelection() {
        if (selectedPostion != CommonConstant.SELECT_HOME) {
            if (!QXCallApplication.login) {//如果是没有登录直接跳转至登陆页
                SetSelectItem setSelectItem = new SetSelectItem();
                setSelectItem.selectPosition = CommonConstant.SELECT_HOME;//上次选择就是首页
                codeSetSelect(setSelectItem);
                IntentUtil.jumpIntent(context, NLoginActivity.class);
                return;
            }
        }
        /**
         * 设置当前选择项为选中项
         */
        fragmentViewPager.setCurrentItem(selectedPostion, false);
        pagerList.get(selectedPostion).initData();
    }

    @Subscribe
    public void onEventSetSelectItem(SetSelectItem setSelectItem) {
        codeSetting = true;
        codeSetSelect(setSelectItem);
    }

    private void codeSetSelect(SetSelectItem setSelectItem) {
        switch (setSelectItem.selectPosition) {//这里的checkedId是点击变化后的最终选择的radioButton
            case CommonConstant.SELECT_HOME:
                radio_group.check(R.id.rb_telegram);
                break;
            case CommonConstant.SELECT_LISTEN:
                radio_group.check(R.id.rb_listen);
                break;
            case CommonConstant.SELECT_MESSAGE:
                radio_group.check(R.id.rb_message);
                break;
            case CommonConstant.SELECT_FIND:
                radio_group.check(R.id.rb_find);
                break;
            case CommonConstant.SELECT_MINE:
                radio_group.check(R.id.rb_mine);
                break;
        }
        selectedPostion = setSelectItem.selectPosition;
    }

    @Subscribe
    public void onEventReCeiveGift(ReCeiveGift reCeiveGift) {
        GiftSendNotify giftSendNotify = reCeiveGift.giftSendNotify;
        Map<String, String> receiveMap = new HashMap<String, String>();
        receiveMap.put("avator", giftSendNotify.getMember_info().getAvatar());
        receiveMap.put("gift_avator", giftSendNotify.getGift_info().getGift_img());
        receiveMap.put("nick_name", giftSendNotify.getMember_info().getNick_name());
        receiveMap.put("gift_num", giftSendNotify.getGift_info().getGit_num());
        ReceiveGiftDialog.showReceiveGiftDialog(this, receiveMap, new ReceiveGiftDialog.OnComponentClickListener() {
            @Override
            public void onCancle() {
            }

            @Override
            public void onSend(String gift_id) {
            }
        });
    }

    /**
     * 添加pager对象到集合中
     *
     * @param pagerList
     */
    private void addCollectionElemTO(List<BasePager> pagerList) {
        /**
         * 初始化pager对象，并添加至list集合中
         */
        //首页
        OnePage onePage = new OnePage(context);
        onePage.setMyapplication(mQXCallApplication);
        pagerList.add(onePage);

        //小情趣
        TwoPage twoPage = new TwoPage(context);
        twoPage.setMyapplication(mQXCallApplication);
        pagerList.add(twoPage);

        //私人订制
        ThreePage threePage = new ThreePage(context);
        threePage.setMyapplication(mQXCallApplication);
        pagerList.add(threePage);
//        HomePager homePager = new HomePager(context);
//        homePager.setMyapplication(mQXCallApplication);
//        pagerList.add(homePager);
        //偷听
//        ListenPager listenPager = new ListenPager(context);
//        listenPager.setMyapplication(mQXCallApplication);
//        pagerList.add(listenPager);
        //发现
//        FindPager findPager = new FindPager(context);
//        findPager.setMyapplication(mQXCallApplication);
//        pagerList.add(findPager);
        //消息
        MessagePager messagePager = new MessagePager(context);
        messagePager.setMyapplication(mQXCallApplication);
        pagerList.add(messagePager);
        //我的
//        MinePager minePager = new MinePager(context);
//        minePager.setMyapplication(mQXCallApplication);
//        pagerList.add(minePager);
        FivePage fivePage = new FivePage(context);
        fivePage.setMyapplication(mQXCallApplication);
        pagerList.add(fivePage);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case START_CALENDAR_LOGIN:
                /**
                 * 设置当前选择项为选中项
                 */
                fragmentViewPager.setCurrentItem(CommonConstant.SELECT_LISTEN, false);
                pagerList.get(CommonConstant.SELECT_LISTEN).initData();
                break;
            case 1111://所有需要的权限申请完毕
                if (mPermissionUtil.lacksPermissions(PERMISSIONS)) {
                    mPermissionUtil.requestPermissions(PERMISSIONS, 1111, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                } else {
                    deviceAndLogHandle();
                }
                break;
            default:
                UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onToast(String message) {
        ToastUtil.show(this, message, Toast.LENGTH_SHORT);
    }

    @Override
    public void onFinish() {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 应用系统横竖屏切换调用
     *
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    /**
     * 窗口有焦点即所有的布局绘制完毕
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * 未捕获异常处理初始化
     */
    private void crashHandlerInit() {
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

}
