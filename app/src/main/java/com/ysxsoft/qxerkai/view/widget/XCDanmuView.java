package com.ysxsoft.qxerkai.view.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.common.util.log.LogUtil;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.utils.ToastUtil;
import com.ysxsoft.qxerkai.net.ResponseSubscriber;
import com.ysxsoft.qxerkai.net.RetrofitTools;
import com.ysxsoft.qxerkai.net.response.BaseResponse;
import com.ysxsoft.qxerkai.net.response.GetHuaTiListResponse;
import com.ysxsoft.qxerkai.utils.DBUtils;
import com.ysxsoft.qxerkai.utils.DimenUtils;
import com.ysxsoft.qxerkai.utils.LogUtils;
import com.ysxsoft.qxerkai.utils.SystemUtils;
import com.ysxsoft.qxerkai.utils.ToastUtils;
import com.ysxsoft.qxerkai.utils.ZPUtils;
import com.ysxsoft.qxerkai.view.activity.NPersonCenterActivity;
import com.ysxsoft.qxerkai.view.activity.NZhiLiaoActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by caizhiming on 2015/10/11.
 */
public class XCDanmuView extends RelativeLayout {
    private int mScreenWidth;
    private List<View> mChildList;
    private boolean mIsWorking = false;
    private Context mContext;
    private int mDelayDuration = 1000;
    private int animationDuration = 10000;
    private int animationHalfDuration = 10000;
    private Random mRandom;

    private boolean isFirst = true;

    public XCDanmuView(Context context) {
        this(context, null, 0);
    }

    public XCDanmuView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XCDanmuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    public boolean isWorking() {
        return mIsWorking;
    }

    private void init() {
        mScreenWidth = getScreenWidth();
        mChildList = new ArrayList<>();
        mRandom = new Random();
    }

    public void initDanmuItemViews(String[] strContents) {
        for (int i = 0; i < strContents.length; i++) {
            createDanmuView(i, strContents[i]);
        }
    }

    public void initDanmuItemViews(List<GetHuaTiListResponse.DataBeanX.DataBean> list) {
        for (int i = 0; i < list.size(); i++) {
            createDanmuView(i, list.get(i));
        }
    }

    private ArrayList<Integer> containRow = new ArrayList<>();

    public void createDanmuView(int index, GetHuaTiListResponse.DataBeanX.DataBean data) {
        View genView = View.inflate(mContext, R.layout.activity_paohuati_item, null);
        TextView title = (TextView) genView.findViewById(R.id.tv_title);
        TextView price = (TextView) genView.findViewById(R.id.price);
        title.setText(ZPUtils.subString(data.getTitle(), 8));
        price.setText(data.getNum() + "砰砰豆/分钟");
        de.hdodenhof.circleimageview.CircleImageView logo = (CircleImageView) genView.findViewById(R.id.logo);
        Glide.with(mContext).load(data.getIcon()).into(logo);//发表人头像

        int mRowNum = (getScreenHight() - DimenUtils.dp2px(mContext, 50)) / DimenUtils.dp2px(mContext, 110);
        ImageView ivVIP = (ImageView) genView.findViewById(R.id.iv_vip);
        int row = mRandom.nextInt(mRowNum);
        if (containRow.size() >= mRowNum) {
            row = containRow.get(index % mRowNum);
        } else {
            while (containRow.contains(row)) {
                row = mRandom.nextInt(mRowNum);
            }
        }
        if (data.getIs_vip() == 0) {//不是vip
            ivVIP.setVisibility(View.INVISIBLE);
        } else {
            ivVIP.setVisibility(View.VISIBLE);
        }
        containRow.add(row);
        RelativeLayout.LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.topMargin = row * DimenUtils.dp2px(mContext, 110);
        genView.setLayoutParams(lp);

        genView.findViewById(R.id.fl_bg).setTag(data.getUser_id());//传输用户id
        genView.findViewById(R.id.fl_bg).setOnClickListener(new ViewClick(data));
        this.addView(genView);
        mChildList.add(index, genView);
    }

    class ViewClick implements View.OnClickListener {
        private GetHuaTiListResponse.DataBeanX.DataBean data;

        public ViewClick(GetHuaTiListResponse.DataBeanX.DataBean data) {
            this.data = data;
        }

        @Override
        public void onClick(View v) {
            String userId = "" + (int) v.getTag();
            String dbUserId = DBUtils.getUserId();
            if (userId != null) {
                if (userId.equals(dbUserId)) {
                    ToastUtil.showToast(mContext, "不能抢聊自己的话题");
                } else {
                    startHuaTi(data, (int) v.getTag());
//                    NimUIKit.startP2PSessionWithTitle(mContext, "" + tag, data.getIcon(), data.getNick_name(), data.getTitle(), data.getIs_vip(), data.getNum());
                }
            }

        }
    }

    public void createDanmuView(int index, String content) {
        View genView = View.inflate(mContext, R.layout.activity_paohuati_item, null);
        TextView title = (TextView) genView.findViewById(R.id.tv_title);
        title.setText(ZPUtils.subString(content, 8));

        int mRowNum = (getScreenHight() - DimenUtils.dp2px(mContext, 50)) / DimenUtils.dp2px(mContext, 110);

        LogUtils.i("----------" + mRowNum);

        ImageView ivVIP = (ImageView) genView.findViewById(R.id.iv_vip);

        int row = mRandom.nextInt(mRowNum);
        if (containRow.size() >= mRowNum) {
            row = containRow.get(index % mRowNum);
        } else {
            while (containRow.contains(row)) {
                row = mRandom.nextInt(mRowNum);
            }
        }
        if (row % 2 == 0) {
            ivVIP.setVisibility(View.INVISIBLE);
        } else {
            ivVIP.setVisibility(View.VISIBLE);
        }

        LogUtils.i(containRow.toString());
        containRow.add(row);
        RelativeLayout.LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.topMargin = row * DimenUtils.dp2px(mContext, 110);
        genView.setLayoutParams(lp);
        genView.findViewById(R.id.fl_bg).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showToast(mContext, content, 0);
            }
        });
        this.addView(genView);
        mChildList.add(index, genView);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//            Animation animator = new TranslateAnimation((mScreenWidth + mChildList.get(msg.what).getWidth()), -(mScreenWidth + mChildList.get(msg.what).getWidth()), 0, 0);
//            animator.setDuration(animationDuration);
//            animator.setInterpolator(new LinearInterpolator());
//            mChildList.get(msg.what).startAnimation(animator);//开始动画
//            LogUtils.i("objectAnimator" + msg.what);
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mChildList.get(msg.what), "translationX", (mScreenWidth + mChildList.get(msg.what).getWidth()), -(mScreenWidth + mChildList.get(msg.what).getWidth()));
            objectAnimator.setDuration(animationDuration);
            objectAnimator.start();
        }
    };

    private Thread runThread;

    public void start() {
        mIsWorking = true;
        runThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (mIsWorking) {
                    for (int i = 0; i < mChildList.size(); i++) {
                        handler.sendEmptyMessageDelayed(i, i * mDelayDuration);
                    }
                    SystemClock.sleep((mChildList.size() + 1) * mDelayDuration);
                    if (isFirst) {
                        isFirst = false;
                    }
                }
            }
        });
        runThread.start();
    }


    public void stop() {
        mIsWorking = false;
        try {
            runThread.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        int childCount = this.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            LayoutParams lp = (LayoutParams) view.getLayoutParams();
            if (lp.leftMargin <= 0) {
                view.layout(mScreenWidth, lp.topMargin, mScreenWidth + view.getMeasuredWidth(),
                        lp.topMargin + view.getMeasuredHeight());
            } else {
                continue;
            }
        }
    }

    private int getScreenWidth() {
        WindowManager mWm = (WindowManager) this.getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        // 获取屏幕信息
        mWm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    private int getScreenHight() {
        WindowManager mWm = (WindowManager) this.getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        // 获取屏幕信息
        mWm.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    private int gid;
    /**
     * 抢话题/继续话题
     */
    private void startHuaTi(GetHuaTiListResponse.DataBeanX.DataBean data, int tag) {
        if (data == null) {
            return;
        }
         gid=data.getGid();
        Map<String, String> map = new HashMap<>();
        map.put("user_id", DBUtils.getUserId());
        map.put("gid", data.getGid() + "");//价格
        RetrofitTools.startHuaTi(map)
                .subscribe(new ResponseSubscriber<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse baseResponse, int code, String msg) {
                        if (code == 200) {
                            notifyUser(gid);//通知用户
                            NimUIKit.startP2PSessionWithTitle(mContext, "" + tag, data.getIcon(), data.getNick_name(), data.getTitle(), data.getIs_vip(),data.getNum(),gid,1);//callType传1
                        } else {
                            ToastUtil.showToast(mContext, msg);
                        }
                    }

                    @Override
                    public void onFailed(Throwable e) {
                    }
                });
    }

    /**
     * 通知用户
     * @param gid
     */
    private void notifyUser(int gid){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("gid",""+gid);
        RetrofitTools
                .notifyUser(hashMap)
                .subscribe(new ResponseSubscriber<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse baseResponse, int code, String msg) {
                        LogUtils.e("onSuccess");
                    }

                    @Override
                    public void onFailed(Throwable e) {
                        LogUtils.e("onFailed");
                    }
                });
    }
}
