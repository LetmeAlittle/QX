package com.ysxsoft.qxerkai.view.fragment;

import android.content.Intent;
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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.netease.nim.uikit.common.util.string.StringUtil;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.constant.CommonConstant;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.UserBean;
import com.ttt.qx.qxcall.utils.ToastUtil;
import com.ysxsoft.qxerkai.net.ResponseSubscriber;
import com.ysxsoft.qxerkai.net.RetrofitTools;
import com.ysxsoft.qxerkai.net.response.SaGouLiangLikeResponse;
import com.ysxsoft.qxerkai.net.response.SaGouLiangListResponse;
import com.ysxsoft.qxerkai.net.response.SaGouLiangPublishResponse;
import com.ysxsoft.qxerkai.utils.DBUtils;
import com.ysxsoft.qxerkai.utils.DimenUtils;
import com.ysxsoft.qxerkai.utils.StringUtils;
import com.ysxsoft.qxerkai.utils.SystemUtils;
import com.ysxsoft.qxerkai.utils.ToastUtils;
import com.ysxsoft.qxerkai.view.adapter.SGLOneAdapter;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;
import com.ysxsoft.qxerkai.view.widget.RoundAngleImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.photopicker.util.BGAPhotoHelper;
import top.zibin.luban.OnCompressListener;

/**
 * Created by zhaozhipeng on 18/5/23.
 */

public class SGLOneFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.swipe_target)
    RecyclerView swipeTarget;
    @BindView(R.id.multipleStatusView)
    MultipleStatusView multipleStatusView;
    private View rootView;
    private TextView topLikeNum;
    private ImageView topImageView;

    private SGLOneAdapter adapter;
    private int pageIndex = 1;
    private int pageTotal = 1;
    private List<SaGouLiangListResponse.DataBean.ListBean> data = new ArrayList<>();
    private SaGouLiangListResponse.DataBean.TopBean topBean;
    public static final int RC_CHOOSE_PHOTO = 0x01;
    public static final int REQUEST_CODE_CROP = 0x71;
    private String selectPhoto;
    private BGAPhotoHelper mPhotoHelper;
    private View headView;
    private int size = 0;
    private List<String> lubanPath = new ArrayList<>();
    private TextView likeNumTextView;

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
        adapter = new SGLOneAdapter(R.layout.activity_sgl_item, data);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        adapter.isFirstOnly(true);
        adapter.setOnLoadMoreListener(this, swipeTarget);
        headView = getActivity().getLayoutInflater().inflate(R.layout.fragment_sgl_one_top, (ViewGroup) swipeTarget.getParent(), false);
        initHeadView(headView);
        adapter.addHeaderView(headView);
        swipeTarget.setAdapter(adapter);
    }

    private void initHeadView(View headView) {
        RoundAngleImageView raImageView = (RoundAngleImageView) headView.findViewById(R.id.ra_imageView);
        ImageView ivMengban = (ImageView) headView.findViewById(R.id.iv_mengban);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(((int) SystemUtils.getScreenWidth(getActivity()) - DimenUtils.dp2px(getActivity(), 40)), ((int) SystemUtils.getScreenWidth(getActivity()) - DimenUtils.dp2px(getActivity(), 40)) / 69 * 50);
        raImageView.setLayoutParams(lp);
        ivMengban.setLayoutParams(lp);
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
        map.put("type", "2");//1.故事  2.图片
        map.put("page", pageIndex + "");
        RetrofitTools.getSaGouLiangList(map)
                .subscribe(new ResponseSubscriber<SaGouLiangListResponse>() {
                    @Override
                    public void onSuccess(SaGouLiangListResponse saGouLiangListResponse, int code, String msg) {
                        multipleStatusView.hideLoading();
                        adapter.loadMoreComplete();
                        SaGouLiangListResponse.DataBean dataBean = saGouLiangListResponse.getData();
                        if (dataBean == null) {
                            return;
                        }
                        if (code == 200) {
                            //填充顶部
                            topBean = saGouLiangListResponse.getData().getTop();
                            fillHeader();
                            //填充列表
                            List<SaGouLiangListResponse.DataBean.ListBean> list = dataBean.getList();
                            if (pageIndex == 1) {
                                adapter.setNewData(list);
                                pageTotal=dataBean.getLast_page();
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
                        adapter.loadMoreComplete();
                        multipleStatusView.hideLoading();
                        if (pageIndex > 1) {
                            pageIndex--;
                        }
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        multipleStatusView.showLoading();
                    }
                });
    }

    private void fillHeader() {
        if (topBean != null) {
            com.ysxsoft.qxerkai.view.widget.RoundAngleImageView image = (RoundAngleImageView) headView.findViewById(R.id.ra_imageView);
            likeNumTextView = (TextView) headView.findViewById(R.id.tv_likeNum);

            if (topBean.getImgs() != null && topBean.getImgs().size() > 0) {
                Glide.with(getActivity()).load(topBean.getImgs().get(0).getImg()).into(image);
                likeNumTextView.setTag("" + topBean.getSid());
            }
            likeNumTextView.setText(StringUtils.convert(topBean.getLikes()));
            likeNumTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    like((String) v.getTag());
                }
            });
//            likeNum.setTextColor(getResources().getColor(R.color.colorAccent));
            ImageView fabu = (ImageView) headView.findViewById(R.id.iv_fabu);
            fabu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    choicePhotoWrapper();
                }
            });
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // 选择图片
    ///////////////////////////////////////////////////////////////////////////
    private void choicePhotoWrapper() {
        // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
        File takePhotoDir = new File(CommonConstant.PHOTO_URL, "quexin");
        Intent photoPickerIntent = new BGAPhotoPickerActivity.IntentBuilder(getActivity())
                .cameraFileDir(takePhotoDir) // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话则不开启图库里的拍照功能
                .maxChooseCount(6) // 图片选择张数的最大值
                .selectedPhotos(null) // 当前已选中的图片路径集合
                .pauseOnScroll(false) // 滚动列表时是否暂停加载图片
                .build();
        startActivityForResult(photoPickerIntent, RC_CHOOSE_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RC_CHOOSE_PHOTO:
                if (data != null) {
                    List<String> selectedPhotos = BGAPhotoPickerActivity.getSelectedPhotos(data);
                    if (selectedPhotos != null) {
                        size = selectedPhotos.size();
                        lubanPath.clear();
                        for (int i = 0; i < selectedPhotos.size(); i++) {
                            luban(selectedPhotos.get(i));
                        }
                    }
                }
                break;
        }
    }

    /**
     * 鲁班压缩
     *
     * @param imagePath
     */
    private void luban(String imagePath) {
        File file = new File(imagePath);
        top.zibin.luban.Luban.with(getActivity())
                .load(file)                     //传人要压缩的图片
                .setCompressListener(new OnCompressListener() { //设置回调

                    @Override
                    public void onStart() {
                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
                    }

                    @Override
                    public void onSuccess(File file) {
                        // TODO 压缩成功后调用，返回压缩后的图片文件
                        lubanPath.add(file.getPath());
                        if (size == 1) {
                            submit();
                            return;
                        }
                        size--;
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO 当压缩过去出现问题时调用
                        size--;
                    }
                }).launch();//启动压缩
    }

    /**
     * 撒狗粮 发照片
     */
    private void submit() {
        Map<String, String> params = new HashMap<>();
        params.put("user_id", DBUtils.getUserId());
        params.put("type", "2");

        String[] names = new String[lubanPath.size()];
        File[] files = new File[lubanPath.size()];
        for (int i = 0; i < lubanPath.size(); i++) {
            File file = new File(lubanPath.get(i));
            names[i] = "file" + i;
            files[i] = file;
        }
        RetrofitTools
                .publishSaGouLiang(params, names, files)
                .subscribe(new ResponseSubscriber<SaGouLiangPublishResponse>() {
                    @Override
                    public void onSuccess(SaGouLiangPublishResponse saGouLiangPublishResponse, int code, String msg) {
                        multipleStatusView.hideLoading();
                        if (code == 200) {
                            showToast(msg);
                            pageIndex = 1;
                            getList();
                        } else {
                            showToast(msg);
                        }
                    }

                    @Override
                    public void onFailed(Throwable e) {
                        multipleStatusView.hideLoading();
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        multipleStatusView.showLoading();
                    }
                });
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
                            ToastUtils.showToast(getActivity(),"赠送成功！",1);
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
        if (likeNumTextView == null) {
            return;
        }
        String topLike = likeNumTextView.getText().toString();
        if (!"".equals(topLike)) {
            int like = Integer.parseInt(topLike);
            likeNumTextView.setText(StringUtils.convert("" + (like + 1)));
        }
    }
}
