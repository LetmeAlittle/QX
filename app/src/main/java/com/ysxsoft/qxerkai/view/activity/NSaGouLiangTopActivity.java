package com.ysxsoft.qxerkai.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.utils.ToastUtil;
import com.ysxsoft.qxerkai.net.ResponseSubscriber;
import com.ysxsoft.qxerkai.net.RetrofitTools;
import com.ysxsoft.qxerkai.net.response.GouLiangTop;
import com.ysxsoft.qxerkai.net.response.HaoYouListResponse;
import com.ysxsoft.qxerkai.view.adapter.TopBangOneAdapter;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NSaGouLiangTopActivity extends NBaseActivity  implements BaseQuickAdapter.RequestLoadMoreListener{

	@BindView(R.id.status_bar)
	View statusBar;
	@BindView(R.id.iv_public_titlebar_left_1)
	ImageView ivPublicTitlebarLeft1;
	@BindView(R.id.tv_public_titlebar_left)
	TextView tvPublicTitlebarLeft;
	@BindView(R.id.iv_public_titlebar_left_2)
	ImageView ivPublicTitlebarLeft2;
	@BindView(R.id.ll_public_titlebar_left)
	LinearLayout llPublicTitlebarLeft;
	@BindView(R.id.iv_public_titlebar_right_1)
	ImageView ivPublicTitlebarRight1;
	@BindView(R.id.tv_public_titlebar_right)
	TextView tvPublicTitlebarRight;
	@BindView(R.id.iv_public_titlebar_right_2)
	ImageView ivPublicTitlebarRight2;
	@BindView(R.id.ll_public_titlebar_right)
	LinearLayout llPublicTitlebarRight;
	@BindView(R.id.tv_public_titlebar_center)
	TextView tvPublicTitlebarCenter;
	@BindView(R.id.ll_public_titlebar)
	LinearLayout llPublicTitlebar;
	@BindView(R.id.swipe_target)
	RecyclerView swipeTarget;
	@BindView(R.id.multipleStatusView)
	MultipleStatusView multipleStatusView;
	private TopAdapter adapter;
	private int pageIndex = 1;
	private int pageTotal = 1;
	private HashMap<String, String> map = new HashMap<>();
	private ImageView ivTouXiang;
	private TextView tvNick, tvMoney;
	private String type = "1";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gou_liang_top);
		ButterKnife.bind(this);
		initStatusBar();
		initStatusBar(statusBar);
		initTitleBar();
		init();
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
		tvPublicTitlebarCenter.setText("狗粮排行榜");
	}

	private void init(){
		swipeTarget.setLayoutManager(new LinearLayoutManager(this));
		adapter = new TopAdapter(R.layout.activity_top_bang_item);
		adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
		adapter.isFirstOnly(true);
		adapter.setOnLoadMoreListener(this, swipeTarget);
		View headView = getLayoutInflater().inflate(R.layout.activity_top_bang_top, (ViewGroup) swipeTarget.getParent(), false);
		ivTouXiang = (ImageView) headView.findViewById(R.id.iv_touxiang);
		tvMoney = (TextView) headView.findViewById(R.id.tv_money);
		TextView description = (TextView) headView.findViewById(R.id.description);
		description.setText("获得狗粮赞");
		tvNick = (TextView) headView.findViewById(R.id.tv_nickname);
		adapter.addHeaderView(headView);
		swipeTarget.setAdapter(adapter);
	}

	private void initData() {
		pageIndex = 1;
		getList();
	}

	private void getList() {
		Map<String, String> map = new HashMap<>();
		RetrofitTools.gouLiangTopList(map).subscribe(new ResponseSubscriber<GouLiangTop>() {
			@Override
			public void onSuccess(GouLiangTop top, int code, String msg) {
				multipleStatusView.hideLoading();
				adapter.loadMoreComplete();
				if (code == 200) {
					List<GouLiangTop.DataBeanX.DataBean> data = top.getData().getData();
					if (pageIndex == 1) {
						pageTotal = top.getData().getLast_page();
						if (data != null) {
							if (data.size() > 1) {
								Message message = new Message();
								message.what = 0;
								message.obj = data.get(0);
								handler.sendMessage(message);

								data.remove(0);
								adapter.setNewData(data);
							}
						}

					} else {
						adapter.addData(data);
					}
				}
			}

			@Override
			public void onFailed(Throwable e) {
				multipleStatusView.hideLoading();
				adapter.loadMoreComplete();
			}

			@Override
			public void onStart() {
				super.onStart();
				multipleStatusView.showLoading();
			}
		});
	}

	@Override
	public void onLoadMoreRequested() {
		if (pageIndex < pageTotal) {
			pageIndex++;
			getList();
		} else {
			adapter.loadMoreEnd();
		}
	}

	public class TopAdapter extends BaseQuickAdapter<GouLiangTop.DataBeanX.DataBean, BaseViewHolder> {
		public TopAdapter(int layoutResId) {
			super(layoutResId);
		}

		@Override
		protected void convert(BaseViewHolder helper, GouLiangTop.DataBeanX.DataBean item) {
			int position = helper.getLayoutPosition();

			helper.setText(R.id.tv_nickname,item.getNick_name());
			helper.setText(R.id.tv_money,item.getCnt()+"");
			helper.setText(R.id.desc,"获得狗粮赞");
			Glide.with(mContext).load(item.getIcon()).into((ImageView) helper.getView(R.id.iv_touxiang));
			helper.setText(R.id.tv_no, position+1+"");
		}
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
				case 0:
					GouLiangTop.DataBeanX.DataBean dataBean = (GouLiangTop.DataBeanX.DataBean) msg.obj;
					Glide.with(NSaGouLiangTopActivity.this).load(dataBean.getIcon()).into(ivTouXiang);
					tvNick.setText(dataBean.getNick_name());
					tvMoney.setText(dataBean.getCnt()+"");
					break;
			}
		}
	};
}
