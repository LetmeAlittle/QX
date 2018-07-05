package com.ysxsoft.qxerkai.view.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.adapter.InvitedRecordAdapter;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.UserBean;
import com.ttt.qx.qxcall.function.base.interfacee.SubScribeOnNextListener;
import com.ttt.qx.qxcall.function.base.subscribe.ProgressSubscribe;
import com.ttt.qx.qxcall.function.login.model.LoginModel;
import com.ttt.qx.qxcall.function.login.model.entity.InvitedRecord;
import com.ttt.qx.qxcall.function.login.model.entity.UserShareInfo;
import com.ttt.qx.qxcall.function.login.view.InvitedFriendsActivity;
import com.ttt.qx.qxcall.utils.ToastUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.ysxsoft.qxerkai.utils.ToastUtils;
import com.ysxsoft.qxerkai.view.adapter.YaoQingHaoYouAdapter;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NMyYaoQingActivity extends NBaseActivity implements BaseQuickAdapter.RequestLoadMoreListener{

    @BindView(R.id.status_bar)
    View statusBar;
    @BindView(R.id.iv_public_titlebar_left_1)
    ImageView ivPublicTitlebarLeft1;
    @BindView(R.id.ll_public_titlebar_left)
    LinearLayout llPublicTitlebarLeft;
    @BindView(R.id.tv_public_titlebar_right)
    TextView tvPublicTitlebarRight;
    @BindView(R.id.ll_public_titlebar_right)
    LinearLayout llPublicTitlebarRight;
    @BindView(R.id.tv_public_titlebar_center)
    TextView tvPublicTitlebarCenter;
    @BindView(R.id.swipe_target)
    RecyclerView swipeTarget;
    @BindView(R.id.multipleStatusView)
    MultipleStatusView multipleStatusView;

    private int pageIndex=1;
    private int pageTotal=1;
    private YaoQingHaoYouAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nmy_yao_qing);
        ButterKnife.bind(this);
        initStatusBar();
        initStatusBar(statusBar);
        initTitleBar();
        initView();
        initData();
    }

    private void initTitleBar() {
        ivPublicTitlebarLeft1.setVisibility(View.VISIBLE);
        ivPublicTitlebarLeft1.setImageResource(R.mipmap.back_left_white);
        llPublicTitlebarLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvPublicTitlebarCenter.setText("邀请好友");
        tvPublicTitlebarRight.setVisibility(View.VISIBLE);
        tvPublicTitlebarRight.setText("分享");
        llPublicTitlebarRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showShareBoard();
            }
        });
    }

    private void initView() {
        swipeTarget.setLayoutManager(new LinearLayoutManager(this));
        adapter = new YaoQingHaoYouAdapter(R.layout.activity_nmy_yao_qing_item);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        adapter.isFirstOnly(true);
        adapter.setOnLoadMoreListener(this, swipeTarget);
        swipeTarget.setAdapter(adapter);
    }

    private UserBean mUserBean;
    private UserShareInfo.DataBean shareInfoData = null;
    private void initData() {
        UserDao userDao = new UserDao();
        mUserBean = userDao.queryFirstData();
        LoginModel.getLoginModel().getInvitedRecord(new ProgressSubscribe<>(new SubScribeOnNextListener<InvitedRecord>() {
            @Override
            public void onNext(InvitedRecord invitedRecord) throws IOException {
                if (invitedRecord.getStatus_code() == 200) {
                    List<InvitedRecord.DataBeanX.DataBean> beanList = invitedRecord.getData().getData();
                    if (beanList != null && beanList.size() > 0) {
                        if(pageIndex==1){
                            adapter.setNewData(beanList);
                        }else {
                            adapter.addData(beanList);
                        }
                    } else {
                    }
                } else {
                    onToast(invitedRecord.getMessage());
                }
            }
        }, NMyYaoQingActivity.this), String.valueOf(mUserBean.getUserId()));
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
        ShareAction shareAction = new ShareAction(NMyYaoQingActivity.this);
        shareAction.setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE);
        shareAction.setShareboardclickCallback(shareBoardlistener);
        shareAction.open();
    }

    private ShareBoardlistener shareBoardlistener = new ShareBoardlistener() {

        @Override
        public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
            //邀请分享
            UMWeb web = new UMWeb(shareInfoData.getUrl());
//            Bitmap bitmap = BitmapFactory.decodeResource(InvitedFriendsActivity.this.getResources(), R.mipmap.app_right_icon);
            UMImage umImage = new UMImage(NMyYaoQingActivity.this, shareInfoData.getImg());
            web.setTitle(shareInfoData.getTitle());//标题
            web.setThumb(umImage);  //缩略图
            web.setDescription(shareInfoData.getDescription());//描述
            new ShareAction(NMyYaoQingActivity.this)
                    .withMedia(web)
                    .setPlatform(share_media)
                    .share();
        }
    };

    @Override
    public void onLoadMoreRequested() {
        if (pageIndex < pageTotal) {
            pageIndex++;
            initData();
        } else {
            adapter.loadMoreEnd();
        }
    }

    public void onToast(String message) {
        //消息弹出
        ToastUtils.showToast(this,message,Toast.LENGTH_SHORT);
    }

}
