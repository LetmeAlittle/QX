package com.ysxsoft.qxerkai.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.netease.nim.uikit.NimUIKit;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.utils.ToastUtil;
import com.ysxsoft.qxerkai.net.ResponseSubscriber;
import com.ysxsoft.qxerkai.net.RetrofitTools;
import com.ysxsoft.qxerkai.net.response.HaoYouListResponse;
import com.ysxsoft.qxerkai.net.response.SearchListResponse;
import com.ysxsoft.qxerkai.utils.DBUtils;
import com.ysxsoft.qxerkai.utils.LogUtils;
import com.ysxsoft.qxerkai.utils.WYUtils;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BanYanActivity extends NBaseActivity implements BaseQuickAdapter.RequestLoadMoreListener {

	public static final String TYPE_BY = "TYPE_BY";
	//声明AMapLocationClient类对象
	public AMapLocationClient mLocationClient = null;
	//声明AMapLocationClientOption对象
	public AMapLocationClientOption mLocationOption = null;
	//1111
	@BindView(R.id.status_bar)
	View statusBar;
	@BindView(R.id.swipe_target)
	RecyclerView swipeTarget;
	@BindView(R.id.multipleStatusView)
	MultipleStatusView multipleStatusView;
	@BindView(R.id.iv_left)
	ImageView ivLeft;
	@BindView(R.id.iv_right)
	ImageView ivRight;
	@BindView(R.id.mengban)
	ImageView mengban;
	@BindView(R.id.mengban2)
	ImageView mengban2;
	@BindView(R.id.ll_hao_you)
	LinearLayout llHaoYou;
	@BindView(R.id.ll_fu_jin)
	LinearLayout llFuJin;
	@BindView(R.id.et_public_titlebar)
	EditText etPublicTitlebar;
	private int pageType = 0, pageIndex = 1, pageTotal = 1, urlType = 0;
	private int picLeft = R.mipmap.touxiang_dashu, picRight = R.mipmap.touxiang_luoli;
	private String user_id = "";
	private OnChooseClick onChooseClick;
	private BanYanAdapter adapter;
	private HashMap<String, String> map = new HashMap<>();
	private double latitude = 34.801765;
	private double longitude = 113.611325;
	//声明定位回调监听器
	public AMapLocationListener mLocationListener = new AMapLocationListener() {
		@Override
		public void onLocationChanged(AMapLocation amapLocation) {
			if (amapLocation != null) {
				if (amapLocation.getErrorCode() == 0) {
					LogUtils.e("实现定位===" + amapLocation.getAddress());
					//可在其中解析amapLocation获取相应内容。
					//获取纬度
					latitude = amapLocation.getLatitude();
					//获取经度
					longitude = amapLocation.getLongitude();

				} else {
					//定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
					Log.e("AmapError", "location Error, ErrCode:"
							+ amapLocation.getErrorCode() + ", errInfo:"
							+ amapLocation.getErrorInfo());
				}
			}
		}
	};
	private boolean isLeft = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ban_yan);
		ButterKnife.bind(this);
		initStatusBar();
		initStatusBar(statusBar);
//        initTitleBar();
		initView();
		initData();
		initMap();
	}

   /* private void initTitleBar() {
        ivPublicTitlebarLeft1.setVisibility(View.VISIBLE);
        ivPublicTitlebarLeft1.setImageResource(R.mipmap.back_left_white);
        llPublicTitlebarLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvPublicTitlebarCenter.setText("");
    }*/

	private void initView() {
		etPublicTitlebar.setClickable(true);
		etPublicTitlebar.setFocusable(false);

		user_id = DBUtils.getUserId();

		swipeTarget.setLayoutManager(new LinearLayoutManager(this));
		adapter = new BanYanAdapter(R.layout.activity_xuan_zhe_shou_hu_item);
		adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
		adapter.isFirstOnly(true);
		swipeTarget.setAdapter(adapter);
		adapter.setOnLoadMoreListener(this, swipeTarget);

		Intent intent = getIntent();
		pageType = intent.getIntExtra(TYPE_BY, 0);
		switch (pageType) {
			case 0://大叔---萝莉
				picLeft = R.mipmap.touxiang_dashu;
				picRight = R.mipmap.touxiang_luoli;
				break;
			case 1://教师---学生
				picLeft = R.mipmap.touxiang_jiaoshi;
				picRight = R.mipmap.touxiang_xuesheng;
				break;
			case 2://空姐---乘客
				picLeft = R.mipmap.touxing_kongjie;
				picRight = R.mipmap.touxing_chengke;
				break;
			case 3://老板---秘书
				picLeft = R.mipmap.touxiang_laoban;
				picRight = R.mipmap.touxing_mishu;
				break;
			case 4://护士---病人
				picLeft = R.mipmap.touxing_hushi;
				picRight = R.mipmap.touxing_bingren;
				break;
			case 5://亲王---宠妃
				picLeft = R.mipmap.touxing_qinwang;
				picRight = R.mipmap.touxing_chongfei;
				break;
			default:
				break;
		}

		ivLeft.setImageResource(picLeft);
		ivRight.setImageResource(picRight);

		urlType = 0;
	}

	//角色扮演的  通知  id = 5 ;  members = 匹配的成员数租 ;  teamId = 发起者的id ;  role = (0或者1 — 0是代表扮演左边  1是代表扮演右边) ;  story = 故事类型(0:教师VS学生 1:亲王VS宠妃 2:护士VS病人 3:大叔VS萝莉 4:空姐VS乘客 5:老板VS秘书)  ;  teamName = 发起者的昵称

	private String convert(int type) {
		String result="";
		switch (type) {
			case 0:
				result="3";
				break;
			case 1:
				result="0";
				break;
			case 2:
				result="4";
				break;
			case 3:
				result="5";
				break;
			case 4:
				result="2";
				break;
			case 5:
				result="1";
				break;
		}
		return result;
	}


	private void initData() {
		pageIndex = 1;
		if (urlType == 0) {
			getHyData();
		} else {
			getFjData();
		}

		onChooseClick = new OnChooseClick() {
			@Override
			public void onClick(HaoYouListResponse.DataBeanX.DataBean item) {
				//TODO  角色扮演  选择
//				showToast(item.getNick_name());
				Intent intent = new Intent(BanYanActivity.this, NYiJianPiPeiActivity.class);
				intent.putExtra("isSystem", true);
				intent.putExtra("role", isLeft ? "0" : "1");
				intent.putExtra("avatar",item.getMember_avatar());
				intent.putExtra("story", convert(pageType));//android转换ios 自定义通知
				startActivity(intent);
				NimUIKit.startP2PSessionWithJiaoSe(BanYanActivity.this, new ArrayList<String>(), isLeft?"1":"0",DBUtils.getUserId(), convert(pageType), DBUtils.getUserNickName(),item.getMember_avatar());//跳转至角色扮演 携带对方id 对方名字
				WYUtils.notifyToUserBanYan( BanYanActivity.this,item.getId()+"",isLeft?"0":"1",convert(pageType));//通知用户
			}
		};
	}

	private void initMap() {
		//初始化定位
		mLocationClient = new AMapLocationClient(getApplicationContext());
		//设置定位回调监听
		mLocationClient.setLocationListener(mLocationListener);

		//初始化AMapLocationClientOption对象
		mLocationOption = new AMapLocationClientOption();
		//设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
		mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);


		//获取一次定位结果：
		//该方法默认为false。
		mLocationOption.setOnceLocation(true);

		//获取最近3s内精度最高的一次定位结果：
		//设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
		mLocationOption.setOnceLocationLatest(true);


		//给定位客户端对象设置定位参数
		mLocationClient.setLocationOption(mLocationOption);
		//启动定位
		mLocationClient.startLocation();
	}

	//获取好友列表数据
	private void getHyData() {
		map.clear();
		map.put("user_id", user_id);
		map.put("page", pageIndex + "");

		RetrofitTools.getHaoYouList(map).subscribe(new ResponseSubscriber<HaoYouListResponse>() {
			@Override
			public void onSuccess(HaoYouListResponse response, int code, String msg) {
				multipleStatusView.hideLoading();
				if (code == 200) {
					List<HaoYouListResponse.DataBeanX.DataBean> data = response.getData().getData();
					if (pageIndex == 1) {
						pageTotal = response.getData().getLast_page();
						if (data != null) {
							adapter.setNewData(data);
						}
					} else {
						adapter.addData(data);
					}
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

	//获取附近的人列表数据
	private void getFjData() {
		map.clear();
		map.put("user_id", user_id);
		map.put("page", pageIndex + "");
		map.put("lat", latitude + "");//纬度
		map.put("lng", longitude + "");//精度

		RetrofitTools.getFjRenList(map).subscribe(new ResponseSubscriber<HaoYouListResponse>() {
			@Override
			public void onSuccess(HaoYouListResponse response, int code, String msg) {
				multipleStatusView.hideLoading();
				if (code == 200) {
					List<HaoYouListResponse.DataBeanX.DataBean> data = response.getData().getData();
					if (pageIndex == 1) {
						pageTotal = response.getData().getLast_page();
						if (data != null) {
							adapter.setNewData(data);
						}
					} else {
						adapter.addData(data);
					}
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

	@OnClick({R.id.iv_public_titlebar_left_1, R.id.et_public_titlebar,
			R.id.tv_hy, R.id.tv_fj, R.id.tv_sysPipei, R.id.mengban, R.id.mengban2})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.iv_public_titlebar_left_1:
				finish();
				break;
			case R.id.mengban:
				isLeft = true;
				mengban2.setVisibility(View.VISIBLE);
				mengban.setVisibility(View.GONE);
				break;
			case R.id.mengban2:
				isLeft = false;
				mengban2.setVisibility(View.GONE);
				mengban.setVisibility(View.VISIBLE);
				break;
			case R.id.et_public_titlebar:
				startActivity(new Intent(this, NBanYanSearchActivity.class));
				break;
			case R.id.tv_hy://我的好友
				if (llHaoYou.getVisibility() != View.VISIBLE) {
					urlType = 0;
					llHaoYou.setVisibility(View.VISIBLE);
					llFuJin.setVisibility(View.INVISIBLE);
				}
				initData();
				break;
			case R.id.tv_fj://附近的人
				if (llFuJin.getVisibility() != View.VISIBLE) {
					urlType = 1;
					llFuJin.setVisibility(View.VISIBLE);
					llHaoYou.setVisibility(View.INVISIBLE);

				}
				initData();
				break;
			case R.id.tv_sysPipei://系统匹配
				//TODO   系统匹配
				Intent intent = new Intent(BanYanActivity.this, NYiJianPiPeiActivity.class);
				intent.putExtra("isSystem", true);
				intent.putExtra("role", isLeft ? "0" : "1");
				intent.putExtra("story", convert(pageType));//android转换ios 自定义通知
				startActivity(intent);
				break;
		}

	}

	@Override
	public void onLoadMoreRequested() {
		if (pageIndex < pageTotal) {
			pageIndex++;
			if (urlType == 0) {
				getHyData();
			} else {
				getFjData();
			}
		} else {
			adapter.loadMoreEnd();
		}
	}


	public interface OnChooseClick {
		void onClick(HaoYouListResponse.DataBeanX.DataBean item);
	}

	private class BanYanAdapter extends BaseQuickAdapter<HaoYouListResponse.DataBeanX.DataBean, BaseViewHolder> {
		public BanYanAdapter(int layoutResId) {
			super(layoutResId);
		}

		@Override
		protected void convert(BaseViewHolder helper, HaoYouListResponse.DataBeanX.DataBean item) {
			TextView tvStatus = helper.getView(R.id.tv_status);
			TextView tvChoose = helper.getView(R.id.tv_choose);
			int isOnLine = item.getIs_online();//0:离线 1：在线
			if (isOnLine == 0) {
				tvStatus.setTextColor(Color.parseColor("#30FFFFFF"));
				tvStatus.setText("离线");
				tvChoose.setVisibility(View.INVISIBLE);
			} else {
				tvStatus.setTextColor(Color.parseColor("#fd3d5c"));
				tvStatus.setText("在线");
				tvChoose.setVisibility(View.VISIBLE);
				tvChoose.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						onChooseClick.onClick(item);
					}
				});
			}
			Glide.with(mContext).load(item.getIcon())
					.into((ImageView) helper.getView(R.id.iv_touxiang));
			helper.setText(R.id.tv_nickname, item.getNick_name());
		}
	}
}
