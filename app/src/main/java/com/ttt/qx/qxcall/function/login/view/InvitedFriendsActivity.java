package com.ttt.qx.qxcall.function.login.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.adapter.InvitedRecordAdapter;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.UserBean;
import com.ttt.qx.qxcall.function.base.interfacee.SubScribeOnNextListener;
import com.ttt.qx.qxcall.function.base.subscribe.ProgressSubscribe;
import com.ttt.qx.qxcall.function.login.model.LoginModel;
import com.ttt.qx.qxcall.function.login.model.entity.InvitedRecord;
import com.ttt.qx.qxcall.function.login.model.entity.User;
import com.ttt.qx.qxcall.function.login.model.entity.UserShareInfo;
import com.ttt.qx.qxcall.function.start.BaseActivity;
import com.ttt.qx.qxcall.utils.ImageUtil;
import com.ttt.qx.qxcall.utils.ToastUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 邀请好友得现金
 * Created by wyd on 2017/7/19.
 */
public class InvitedFriendsActivity extends BaseActivity {
    @BindView(R.id.yao_qing_banner_iv)
    ImageView yao_qing_banner_iv;
    @BindView(R.id.no_invited_record_tv)
    TextView no_invited_record_tv;
    @BindView(R.id.invited_record_recycler_view)
    RecyclerView invited_record_recycler_view;
    private Context mContext;
    private InvitedRecordAdapter invitedRecordAdapter;
    private UserBean mUserBean;
    private UserShareInfo.DataBean shareInfoData = null;
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invited_friends);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    @OnClick({R.id.top_back_rl, R.id.right_rl, R.id.now_invited_tv})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.top_back_rl:
                finish();
                break;
            case R.id.right_rl://弹出分享面饭
                showShareBoard();
                break;
            case R.id.now_invited_tv://弹出分享面饭
                showShareBoard();
                break;
        }
    }

    /**
     * 弹出分享面板
     */
    private void showShareBoard() {
        if (shareInfoData == null) {
            LoginModel.getLoginModel().getShareInfo(new ProgressSubscribe<>(new SubScribeOnNextListener<UserShareInfo>() {
                @Override
                public void onNext(UserShareInfo userShareInfo) throws IOException {
                    if (userShareInfo.getStatus_code() == 200) {
                        shareInfoData = userShareInfo.getData();
                        showBorad();
                    } else {
                        onToast(userShareInfo.getMessage());
                    }
                }
            }, this), String.valueOf(mUserBean.getUserId()));
        } else {
            showBorad();
        }
    }

    private void showBorad() {
        ShareAction shareAction = new ShareAction(InvitedFriendsActivity.this);
        shareAction.setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE);
        shareAction.setShareboardclickCallback(shareBoardlistener);
        shareAction.open();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mContext = this;
        UserDao userDao = new UserDao();
        mUserBean = userDao.queryFirstData();
        LoginModel.getLoginModel().getInvitedRecord(new ProgressSubscribe<>(new SubScribeOnNextListener<InvitedRecord>() {
            @Override
            public void onNext(InvitedRecord invitedRecord) throws IOException {
                if (invitedRecord.getStatus_code() == 200) {
                    List<InvitedRecord.DataBeanX.DataBean> beanList = invitedRecord.getData().getData();
                    if (beanList != null && beanList.size() > 0) {
                        invited_record_recycler_view.setVisibility(View.VISIBLE);
                        no_invited_record_tv.setVisibility(View.GONE);
                        invitedRecordAdapter = new InvitedRecordAdapter(mContext, beanList);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                        invited_record_recycler_view.setLayoutManager(linearLayoutManager);
                        invited_record_recycler_view.setAdapter(invitedRecordAdapter);
                    } else {
                        invited_record_recycler_view.setVisibility(View.GONE);
                        no_invited_record_tv.setVisibility(View.VISIBLE);
                    }
                } else {
                    onToast(invitedRecord.getMessage());
                }
            }
        }, mContext), String.valueOf(mUserBean.getUserId()));
    }

    /**
     * 初始化view
     */
    private void initView() {
//        ImageUtil.scaleImage(this, yao_qing_banner_iv, R.mipmap.yao_qing_banner_iv);
    }


    private void errorMessageShow(User user) {
        Object message = user.getErrorMessage();
        if (message != null) {
            onToast(message.toString());
        }
    }

    private ShareBoardlistener shareBoardlistener = new ShareBoardlistener() {

        @Override
        public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
            //邀请分享
            UMWeb web = new UMWeb(shareInfoData.getUrl());
//            Bitmap bitmap = BitmapFactory.decodeResource(InvitedFriendsActivity.this.getResources(), R.mipmap.app_right_icon);
            UMImage umImage = new UMImage(InvitedFriendsActivity.this, shareInfoData.getImg());
            web.setTitle(shareInfoData.getTitle());//标题
            web.setThumb(umImage);  //缩略图
            web.setDescription(shareInfoData.getDescription());//描述
            new ShareAction(InvitedFriendsActivity.this)
                    .withMedia(web)
                    .setPlatform(share_media)
                    .share();
        }
    };

    public void onToast(String message) {
        //消息弹出
        ToastUtil.show(this, message, Toast.LENGTH_SHORT);
    }

    public void onFinish() {
        //销毁当前
        finish();
    }
}
