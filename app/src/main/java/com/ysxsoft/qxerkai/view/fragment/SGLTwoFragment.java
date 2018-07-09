package com.ysxsoft.qxerkai.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.netease.nim.uikit.common.util.string.StringUtil;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.utils.IntentUtil;
import com.ttt.qx.qxcall.utils.ToastUtil;
import com.ysxsoft.qxerkai.net.ResponseSubscriber;
import com.ysxsoft.qxerkai.net.RetrofitTools;
import com.ysxsoft.qxerkai.net.response.SaGouLiangLikeResponse;
import com.ysxsoft.qxerkai.net.response.SaGouLiangListResponse;
import com.ysxsoft.qxerkai.utils.DBUtils;
import com.ysxsoft.qxerkai.utils.DimenUtils;
import com.ysxsoft.qxerkai.utils.StringUtils;
import com.ysxsoft.qxerkai.utils.SystemUtils;
import com.ysxsoft.qxerkai.utils.ToastUtils;
import com.ysxsoft.qxerkai.view.activity.NPersonCenterActivity;
import com.ysxsoft.qxerkai.view.activity.NZhiLiaoActivity;
import com.ysxsoft.qxerkai.view.activity.PublishSGLActivity;
import com.ysxsoft.qxerkai.view.adapter.SGLTwoAdapter;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;
import com.ysxsoft.qxerkai.view.widget.ResizableImageView;
import com.ysxsoft.qxerkai.view.widget.RoundAngleImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhaozhipeng on 18/5/23.
 */

public class SGLTwoFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.swipe_target)
    RecyclerView swipeTarget;
    @BindView(R.id.multipleStatusView)
    MultipleStatusView multipleStatusView;
    private View rootView;

    private SGLTwoAdapter adapter;
    private int pageIndex = 1;
    private int pageTotal = 1;
    private List<SaGouLiangListResponse.DataBean.ListBean> data = new ArrayList<>();
    private SaGouLiangListResponse.DataBean.TopBean topBean;
    private TextView tvContent;
    private TextView likeNum;
    private View headView;
    private static final int ADD_SGL_CODE = 0x01;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_sgl_one, container);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        swipeTarget.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new SGLTwoAdapter(R.layout.activity_sgl_item, data);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        adapter.isFirstOnly(true);
        adapter.setOnLoadMoreListener(this, swipeTarget);
        headView = getActivity().getLayoutInflater().inflate(R.layout.fragment_sgl_top, (ViewGroup) swipeTarget.getParent(), false);
        initHeadView(headView);
        adapter.addHeaderView(headView);
        swipeTarget.setAdapter(adapter);
    }

    private void initHeadView(View headView) {
        tvContent = (TextView) headView.findViewById(R.id.tv_content);
        tvContent.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/jybks.TTF"));
        LinearLayout llContent = (LinearLayout) headView.findViewById(R.id.ll_content);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(((int) SystemUtils.getScreenWidth(getActivity()) - DimenUtils.dp2px(getActivity(), 40)) / 69 * 33, ((int) SystemUtils.getScreenWidth(getActivity()) - DimenUtils.dp2px(getActivity(), 40)) / 69 * 41);
        lp.leftMargin = ((int) SystemUtils.getScreenWidth(getActivity()) - DimenUtils.dp2px(getActivity(), 40)) / 69 * 8;
        lp.topMargin = ((int) SystemUtils.getScreenWidth(getActivity()) - DimenUtils.dp2px(getActivity(), 40)) / 69 * 5;
        lp.bottomMargin = ((int) SystemUtils.getScreenWidth(getActivity()) - DimenUtils.dp2px(getActivity(), 40)) / 69 * 4;
        llContent.setLayoutParams(lp);

        com.ysxsoft.qxerkai.view.widget.ResizableImageView topImage = (ResizableImageView) headView.findViewById(R.id.riv_top);//发布撒狗粮
        topImage.setOnClickListener(new View.OnClickListener() {//顶部大图点击事件
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PublishSGLActivity.class);
                startActivityForResult(intent, ADD_SGL_CODE);
            }
        });
    }

    private void initData() {
        getList();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onLoadMoreRequested() {
        if (pageIndex < pageTotal) {
            pageIndex++;
            initData();
        } else {
            adapter.loadMoreEnd();
        }
    }

    private void getList() {
        Map<String, String> map = new HashMap<>();
        map.put("type", "1");//1.故事  2.图片
        map.put("page", pageIndex + "");
        RetrofitTools.getSaGouLiangList(map)
                .subscribe(new ResponseSubscriber<SaGouLiangListResponse>() {
                    @Override
                    public void onSuccess(SaGouLiangListResponse saGouLiangListResponse, int code, String msg) {
                        SaGouLiangListResponse.DataBean dataBean = saGouLiangListResponse.getData();
                        if (dataBean == null) {
                            return;
                        }
                        if (code == 200) {
                            //填充顶部
                            topBean = saGouLiangListResponse.getData().getTop();
                            fillHeader();

                            List<SaGouLiangListResponse.DataBean.ListBean> list = dataBean.getList();
                            if (pageIndex == 1) {
                                adapter.setNewData(list);
                                pageTotal = dataBean.getLast_page();
                            } else {
                                adapter.addData(list);
                            }
                        } else {
                            if (pageIndex > 1) {
                                pageIndex--;
                            }
                            ToastUtils.showToast(getActivity(), msg, 0);
                        }
                    }

                    @Override
                    public void onFailed(Throwable e) {
                        if (pageIndex > 1) {
                            pageIndex--;
                        }
                    }
                });
    }

    private void fillHeader() {
        if (topBean != null) {
            de.hdodenhof.circleimageview.CircleImageView image = (de.hdodenhof.circleimageview.CircleImageView) headView.findViewById(R.id.logo);
            if (topBean.getImgs() != null && topBean.getImgs().size() > 0) {
                Glide.with(getActivity()).load(topBean.getImgs().get(0).getImg()).into(image);
            }
            likeNum = (TextView) headView.findViewById(R.id.likeNum);
            likeNum.setText(StringUtils.convert(topBean.getLikes()));
            TextView name = (TextView) headView.findViewById(R.id.name);
            name.setText(StringUtils.convert(topBean.getUsername()));
            TextView content = (TextView) headView.findViewById(R.id.tv_content);
            content.setText(StringUtils.convert(topBean.getContent()));
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String suid = "" + topBean.getUid();//发表人id
                    if (suid.equals(DBUtils.getUserId())) {//本人
                        IntentUtil.jumpIntent(getActivity(), NPersonCenterActivity.class);
                    } else {
                        if ("".equals(suid)) {
                            return;
                        }
                        int id = Integer.parseInt(suid);
                        startActivity(new Intent(getActivity(), NZhiLiaoActivity.class).putExtra("id", id).putExtra("accid", suid));//查看好友资料
                    }
                }
            });
            //顶部点赞
            likeNum.setTag("" + topBean.getSid());
            likeNum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    like((String) v.getTag());
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case ADD_SGL_CODE:
                    pageIndex = 1;
                    getList();
                    break;
            }
        }
    }

    /**
     * 点赞
     *
     * @param sid
     */
    private void like(String sid) {
        Map<String, String> map = new HashMap<>();
        map.put("user_id", DBUtils.getUserId());
        map.put("sid", sid);

        RetrofitTools.likeSaGouLiang(map)
                .subscribe(new ResponseSubscriber<SaGouLiangLikeResponse>() {
                    @Override
                    public void onSuccess(SaGouLiangLikeResponse saGouLiangLikeResponse, int code, String msg) {
                        if (code == 200) {
                            refresh();
                        } else {
                            ToastUtil.showToast(getActivity(), msg);
                        }
                    }

                    @Override
                    public void onFailed(Throwable e) {
                        e.printStackTrace();
                    }
                });
    }

    private void refresh() {
        String topLike = likeNum.getText().toString();
        if (!"".equals(topLike)) {
            int like = Integer.parseInt(topLike);
            likeNum.setText(StringUtils.convert("" + (like + 1)));
        }
    }
}
