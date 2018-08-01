package com.ysxsoft.qxerkai.view.fragment;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.netease.nimlib.sdk.avchat.constant.AVChatType;
import com.ttt.qx.qxcall.QXCallApplication;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.UserBean;
import com.ttt.qx.qxcall.function.home.model.HomeModel;
import com.ttt.qx.qxcall.function.home.model.entity.UserDetailInfo;
import com.ttt.qx.qxcall.function.listen.model.StealListenModel;
import com.ttt.qx.qxcall.function.register.model.entity.StandardResponse;
import com.ttt.qx.qxcall.function.voice.AVChatActivity;
import com.ttt.qx.qxcall.function.voice.AVChatProfile;
import com.ttt.qx.qxcall.pager.BasePager;
import com.ttt.qx.qxcall.utils.IntentUtil;
import com.ttt.qx.qxcall.utils.ToastUtil;
import com.ysxsoft.qxerkai.net.ResponseSubscriber;
import com.ysxsoft.qxerkai.net.RetrofitTools;
import com.ysxsoft.qxerkai.net.response.BaseResponse;
import com.ysxsoft.qxerkai.net.response.GetCardListResponse;
import com.ysxsoft.qxerkai.net.response.GetNoticeListResponse;
import com.ysxsoft.qxerkai.net.response.RuleResponse;
import com.ysxsoft.qxerkai.net.response.TwoPageTuiJianResponse;
import com.ysxsoft.qxerkai.utils.DBUtils;
import com.ysxsoft.qxerkai.utils.DimenUtils;
import com.ysxsoft.qxerkai.utils.ObserverMap;
import com.ysxsoft.qxerkai.utils.StringUtils;
import com.ysxsoft.qxerkai.utils.SystemUtils;
import com.ysxsoft.qxerkai.utils.ToastUtils;
import com.ysxsoft.qxerkai.view.activity.LiaoHanQuActivity;
import com.ysxsoft.qxerkai.view.activity.LiaoMeiQuActivity;
import com.ysxsoft.qxerkai.view.activity.NFaTieActivity;
import com.ysxsoft.qxerkai.view.activity.NHuaLiaoActivity;
import com.ysxsoft.qxerkai.view.activity.NLoginActivity;
import com.ysxsoft.qxerkai.view.activity.NQingQuDetailActivity;
import com.ysxsoft.qxerkai.view.activity.NQingQuListActivity;
import com.ysxsoft.qxerkai.view.activity.NZhiLiaoActivity;
import com.ysxsoft.qxerkai.view.adapter.PengYouQuanAdapter;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;
import com.ysxsoft.qxerkai.view.widget.ResizableImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

import static com.ttt.qx.qxcall.QXCallApplication.getApplication;

/**
 * Created by zhaozhipeng on 18/5/3.
 */

public class TwoPage extends BasePager implements View.OnClickListener, ObserverMap.IPageDataChangeObserver {

	public static final int GO_DETAIL = 0x02;
	@BindView(R.id.status_bar)
	View statusBar;
	@BindView(R.id.tv_public_titlebar_center)
	TextView tvPublicTitlebarCenter;
	@BindView(R.id.multipleStatusView)
	MultipleStatusView multipleStatusView;
	@BindView(R.id.top_bar_bg_view)
	LinearLayout topBarBgView;
	@BindView(R.id.tv_top_bar_item1)
	TextView tvTopBarItem1;
	@BindView(R.id.tv_top_bar_item2)
	TextView tvTopBarItem2;
	@BindView(R.id.tv_top_bar_item3)
	TextView tvTopBarItem3;
	@BindView(R.id.tv_top_bar_item4)
	TextView tvTopBarItem4;
	@BindView(R.id.tv_gonggao)
	TextView tvGonggao;
	@BindView(R.id.rv_quanzi)
	RecyclerView rvQuanzi;
	@BindView(R.id.rv_tuijianyonghu)
	RecyclerView rvTuijianyonghu;
	@BindView(R.id.riv_loop)
	ResizableImageView rivLoop;
	@BindView(R.id.iv_liaomei)
	ImageView ivLiaomei;
	@BindView(R.id.iv_liaohan)
	ImageView ivLiaohan;
	List<GetCardListResponse.DataBeanX.ListBean.DataBean> cardList = new ArrayList<>();
	private View rootView;
	private int offset = 0;// 动画图片偏移量
	private int currentTabIndex = 0;
	private QuanZiAdapter quanZiAdapter;
	private YongHuAdapter yongHuAdapter;
	private ArrayList<Integer> loops = new ArrayList<>();
	private ArrayList<Integer> temp = new ArrayList<>();
	private String type = "1";//1老司机开车 2闺蜜私房话 3两性研究所 4剧本专区 5撩妹区 6撩汉区

	public TwoPage(Context ctx) {
		super(ctx);
	}

	@Override
	public View initView() {
		rootView = View.inflate(ctx, R.layout.fragment_two, null);
		ButterKnife.bind(this, rootView);
		initStatusBar(statusBar);
		initTitleBar();
		initRv();
		ObserverMap.reg(activity.getClass().getSimpleName(), this);
		return rootView;
	}

	private void initTitleBar() {
		tvPublicTitlebarCenter.setText("小情趣");
	}

	private void initRv() {
		rvQuanzi.setLayoutManager(new LinearLayoutManager(ctx));
		quanZiAdapter = new QuanZiAdapter(R.layout.fragment_two_content_item);
		rvQuanzi.setAdapter(quanZiAdapter);
		rvTuijianyonghu.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
		yongHuAdapter = new YongHuAdapter(R.layout.fragment_one_item);
		rvTuijianyonghu.setAdapter(yongHuAdapter);

		quanZiAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
				GetCardListResponse.DataBeanX.ListBean.DataBean bean = (GetCardListResponse.DataBeanX.ListBean.DataBean) adapter.getItem(position);
				if (bean != null) {
					NQingQuDetailActivity.start(activity, activity.getClass().getSimpleName(), bean.getTid(), GO_DETAIL);//跳转小情趣详情页  回调通知
				}
			}
		});
		yongHuAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
				TwoPageTuiJianResponse.DataBeanX.DataBean item = (TwoPageTuiJianResponse.DataBeanX.DataBean) adapter.getItem(position);
				int id = item.getId();//发表人id
				activity.startActivity(new Intent(activity, NZhiLiaoActivity.class).putExtra("id", id).putExtra("accid", id + ""));//查看好友资料
			}
		});
	}

	@Override
	public void initData() {
		initCurrView();
		initHttpData();
		loops.clear();
		loops.add(R.mipmap.fragment_two_laoshiji);
		loops.add(R.mipmap.fragment_two_guimi);
		loops.add(R.mipmap.fragment_two_liangxing);
		loops.add(R.mipmap.fragment_two_juben);
		temp.clear();
		temp.add(R.mipmap.image1);
		temp.add(R.mipmap.image2);
		temp.add(R.mipmap.image3);
		temp.add(R.mipmap.image4);
		temp.add(R.mipmap.image5);
	}

	private void initCurrView() {
		// 动画
		offset = (int) ((SystemUtils.getScreenWidth(activity) - DimenUtils.dp2px(ctx, 30)) / 4);
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(offset, ViewGroup.LayoutParams.MATCH_PARENT);
		topBarBgView.setLayoutParams(params);
		tvTopBarItem1.setOnClickListener(this);
		tvTopBarItem2.setOnClickListener(this);
		tvTopBarItem3.setOnClickListener(this);
		tvTopBarItem4.setOnClickListener(this);
		tvGonggao.setSelected(true);
		ivLiaomei.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ctx.startActivity(new Intent(ctx, LiaoMeiQuActivity.class));
			}
		});
		ivLiaohan.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ctx.startActivity(new Intent(ctx, LiaoHanQuActivity.class));
			}
		});
	}

	private void initHttpData() {
		switch (currentTabIndex) {
			case 0://老司机开车
				type = "1";//1老司机开车 2闺蜜私房话 3两性研究所 4剧本专区 5撩妹区 6撩汉区
				break;
			case 1://闺蜜私房话
				type = "2";
				break;
			case 2://两性研究所
				type = "3";
				break;
			case 3://剧本专区
				type = "4";
				break;
		}
		getList();
		getNotice();
		getTuiJianList();
	}

	/**
	 * 获取小情趣列表
	 */
	private void getList() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("user_id", DBUtils.getUserId());
		map.put("type", type);//1老司机开车 2闺蜜私房话 3两性研究所 4剧本专区 5撩妹区 6撩汉区
		map.put("page", "1");

		RetrofitTools.getCardList(map)
				.subscribe(new ResponseSubscriber<GetCardListResponse>() {
					@Override
					public void onStart() {
						super.onStart();
						multipleStatusView.showLoading();
					}					@Override
					public void onSuccess(GetCardListResponse getCardListResponse, int code, String msg) {
						multipleStatusView.hideLoading();
						if (code == 200) {
							if (getCardListResponse != null && getCardListResponse.getData() != null && getCardListResponse.getData().getList() != null) {
								fillData(getCardListResponse.getData().getList());
							}
						} else {
							ToastUtil.showToast(activity, msg);
						}
					}

					@Override
					public void onFailed(Throwable e) {
						multipleStatusView.hideLoading();
					}


				});
	}

	/**
	 * 根据类型获取通知
	 */
	private void getNotice() {
		Map<String, String> map = new HashMap<String, String>();

		//1老司机开车 2闺蜜私房话 3两性研究所 4剧本专区 5撩妹区 6撩汉区

		boolean needRequest = true;
		switch (type) {
			case "1"://老司机开车
				map.put("type", "2");//2老司机开车 3闺蜜私房话 4两性研究所
				break;
			case "2"://闺蜜私房话
				map.put("type", "3");//2老司机开车 3闺蜜私房话 4两性研究所
				break;
			case "3"://两性研究所
				map.put("type", "4");//2老司机开车 3闺蜜私房话 4两性研究所
				break;
			case "4"://4剧本专区
				tvGonggao.setText(StringUtils.convert("暂无公告"));
				needRequest = false;
				break;
		}
		if (!needRequest) {//剧本专区没有公告 暂时不用
			return;
		}
		RetrofitTools.getNoticeList(map)
				.subscribe(new ResponseSubscriber<GetNoticeListResponse>() {
					@Override
					public void onStart() {
						super.onStart();
						multipleStatusView.showLoading();
					}

					@Override
					public void onSuccess(GetNoticeListResponse getNoticeListResponse, int code, String msg) {
						multipleStatusView.hideLoading();
						if (code == 200) {
							if (getNoticeListResponse != null && getNoticeListResponse.getData() != null) {
								fillData(getNoticeListResponse.getData().getData());
							}
						} else {
							ToastUtil.showToast(activity, msg);
						}
					}

					@Override
					public void onFailed(Throwable e) {
						multipleStatusView.hideLoading();
					}


				});
	}

	/**
	 * 获取推荐的用户
	 */
	private void getTuiJianList() {
		Map<String, String> map = new HashMap<>();
		map.put("user_id", DBUtils.getUserId());

		RetrofitTools.getTuiJianList(map)
				.subscribe(new ResponseSubscriber<TwoPageTuiJianResponse>() {
					@Override
					public void onSuccess(TwoPageTuiJianResponse twoPageTuiJianResponse, int code, String msg) {
						if (code == 200) {
							if (twoPageTuiJianResponse.getData() != null && twoPageTuiJianResponse.getData().getData() != null) {
								yongHuAdapter.setNewData(twoPageTuiJianResponse.getData().getData());
							}
						} else {
							ToastUtil.showToast(activity, msg);
						}
					}

					@Override
					public void onFailed(Throwable e) {
					}
				});
	}

	private void fillData(GetCardListResponse.DataBeanX.ListBean list) {
		List<GetCardListResponse.DataBeanX.ListBean.DataBean> data = list.getData();
		if (data == null) {
			return;
		}
		int size = data.size();
		List<GetCardListResponse.DataBeanX.ListBean.DataBean> temp1 = new ArrayList<>();
		if (size > 3) {//超过三条取3条
			for (int i = 0; i < size; i++) {
				temp1.add(data.get(i));
				if (temp1.size() > 2) {
					break;
				}
			}
		} else {//未超过三条全取
			temp1.addAll(data);
		}
		quanZiAdapter.setNewData(temp1);
	}

	@Override
	public void onClick(View view) {
		int tag = Integer.valueOf(view.getTag().toString());
		if (tag == currentTabIndex) {
			return;
		}
		//必须用属性动画，否则回到这个页面会初始化位置
		ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(topBarBgView, "translationX", offset * currentTabIndex, offset * tag);
		objectAnimator.setDuration(400);
		objectAnimator.setInterpolator(new AnticipateOvershootInterpolator());
		objectAnimator.start();
		rivLoop.setImageResource(loops.get(tag));
		currentTabIndex = tag;
		initHttpData();
	}

	/**
	 * 填充通知
	 *
	 * @param data
	 */
	private void fillData(List<GetNoticeListResponse.DataBeanX.DataBean> data) {
		if (data == null) {
			return;
		}
		if (data != null && data.size() > 0) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < data.size(); i++) {
				sb.append(data.get(i).getContent() + "\t\t");
			}
			tvGonggao.setText(StringUtils.convert(sb.toString()));
		}
	}

	@OnClick({R.id.tv_mores})
	public void onMores(View view) {
		ctx.startActivity(new Intent(ctx, NQingQuListActivity.class).putExtra("type", type));
	}

	/**
	 * 发布
	 *
	 * @param view
	 */
	@OnClick({R.id.iv_fabu})
	public void onFabu(View view) {
		NFaTieActivity.start(ctx, ctx.getClass().getSimpleName());
	}

	@Override
	public void change() {
		getList();
	}

	@Override
	public void change(int likeNum, int commonNum, boolean isChanged, int position, int readNum) {
		//返回重新刷新数据
	}

	/**
	 * 点赞
	 *
	 * @param tid
	 */
	private void like(int tid) {
		Map<String, String> map = new HashMap<>();
		map.put("user_id", DBUtils.getUserId());
		map.put("cid", "" + tid);//列表的id
		map.put("type", "1");//	1帖子 2评论

		RetrofitTools.cardLike(map)
				.subscribe(new ResponseSubscriber<BaseResponse>() {
					@Override
					public void onSuccess(BaseResponse baseResponse, int code, String msg) {
						if (code == 200) {
							getList();
						} else {
							ToastUtil.showToast(activity, msg);
						}
					}

					@Override
					public void onFailed(Throwable e) {
						e.printStackTrace();
					}
				});
	}

	/**
	 * 小情趣适配器
	 */
	private class QuanZiAdapter extends BaseQuickAdapter<GetCardListResponse.DataBeanX.ListBean.DataBean, BaseViewHolder> {
		private int selectedItem=-1;
		public QuanZiAdapter(int layoutResId) {
			super(layoutResId);
		}

		@Override
		protected void convert(BaseViewHolder helper, GetCardListResponse.DataBeanX.ListBean.DataBean item) {
			helper.setText(R.id.cardTitle, StringUtils.convert(item.getTitle()));
			com.ysxsoft.qxerkai.view.widget.RoundAngleImageView image = helper.getView(R.id.cardImage);
			Glide.with(mContext).load(item.getImgss()).diskCacheStrategy(DiskCacheStrategy.ALL).into(image);
			helper.setText(R.id.cardContent, StringUtils.convert(item.getContent()));
			helper.setText(R.id.lookNum, StringUtils.convert(item.getLooks() + ""));
			helper.setText(R.id.goodNum, StringUtils.convert(item.getLikes()));
			helper.setText(R.id.sayNum, StringUtils.convert(item.getCom_num() + ""));
			ImageView likeImage = helper.getView(R.id.likeImage);
			if ("1".equals(item.getIs_like())) {//1 已点赞  0未点赞
				likeImage.setImageResource(R.mipmap.activity_pengyouquan_detail_zan_r);
			} else {
				likeImage.setImageResource(R.mipmap.fragment_two_dianzan);
			}
			likeImage.setOnClickListener(new OnLikeClickListener(item.getTid(), helper.getAdapterPosition()));

			//删除
			TextView delete = helper.getView(R.id.delete);
			if(item.getUser_id()== DBUtils.getIntUserId()){
				delete.setVisibility(View.VISIBLE);
			}else{
				delete.setVisibility(View.GONE);
			}
			delete.setOnClickListener(new OnDeleteClick(item.getTid(),helper.getAdapterPosition()));
		}

		private class OnDeleteClick implements View.OnClickListener{
			private int cid;
			private int p;
			public OnDeleteClick(int cid,int position) {
				this.cid = cid;
				this.p=position;
			}

			@Override
			public void onClick(View v) {
				selectedItem=p;
				new MaterialDialog.Builder(mContext)
						.title("温馨提示")
						.content("是否删除该动态")
						.positiveText("确定")
						.negativeText("取消")
						.onPositive(new MaterialDialog.SingleButtonCallback() {
							@Override
							public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
								delete(cid);
							}
						})
						.onNegative(new MaterialDialog.SingleButtonCallback() {
							@Override
							public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
								dialog.dismiss();
							}
						})
						.show();
			}
		}

		private void delete(int cid){
			Map<String, String> map2 = new HashMap<>();
			map2.put("cid", ""+cid);
			RetrofitTools.deleteQingQu(map2)
					.subscribe(new ResponseSubscriber<BaseResponse>() {
						@Override
						public void onSuccess(BaseResponse baseResponse, int code, String msg) {
							if (code == 200) {
								refreshAdapter();
							}
						}

						@Override
						public void onFailed(Throwable e) {
							Log.e("tag", "删除朋友圈失败");
						}
					});
		}

		private void refreshAdapter(){
			if(mData.size()>selectedItem&&selectedItem!=-1){
				mData.remove((selectedItem));
				notifyDataSetChanged();
			}
		}
		/**
		 * 点赞
		 */
		private class OnLikeClickListener implements View.OnClickListener {
			private int tid;
			private int position;

			public OnLikeClickListener(int tid, int position) {
				this.tid = tid;
				this.position = position;
			}

			@Override
			public void onClick(View v) {
				like(tid);
			}
		}
	}

	private class YongHuAdapter extends BaseQuickAdapter<TwoPageTuiJianResponse.DataBeanX.DataBean, BaseViewHolder> {

		public YongHuAdapter(int layoutResId) {
			super(layoutResId);
		}

		@Override
		protected void convert(BaseViewHolder helper, TwoPageTuiJianResponse.DataBeanX.DataBean item) {
			FrameLayout flBg = helper.getView(R.id.fl_bg);
			int bgWidth = (int) SystemUtils.getScreenWidth(activity) / 2 - DimenUtils.dp2px(ctx, 17.5f);
			flBg.setLayoutParams(new LinearLayout.LayoutParams(bgWidth, bgWidth / 34 * 45));
			ImageView ivView = helper.getView(R.id.iv_image);
			Glide.with(mContext).load(item.getMember_avatar()).into(ivView);
			ImageView ivCall = helper.getView(R.id.iv_call);
            ivCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!QXCallApplication.login) {//如果是没有登录直接跳转至登陆页
                        IntentUtil.jumpIntent(ctx, NLoginActivity.class);
                        return;
                    }
                    UserDao userDao = new UserDao();
                    UserBean userBean = userDao.queryFirstData();
                    StealListenModel.getStealListenModel().isAllowTalk(new Subscriber<StandardResponse>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                        }

                        @Override
                        public void onNext(StandardResponse standardResponse) {
                            if (standardResponse.getStatus_code() == 200) {
                                //调起拨打界面。
                                AVChatProfile.getInstance().setAVChatting(true);
                                AVChatActivity.launch(ctx, item.getWy_acid(), AVChatType.AUDIO.getValue(), AVChatActivity.FROM_INTERNAL);
                            } else {
                                ToastUtils.showToast(ctx, standardResponse.getMessage(), 0);
                            }
                        }
                    }, String.valueOf(item.getId()), "Bearer " + userBean.getToken());
                }
            });
			TextView name = helper.getView(R.id.tv_name);
			name.setText(item.getNick_name());
			TextView age = helper.getView(R.id.tv_age);
			if (item.getMember_age() == null || "".equals(item.getMember_age())) {
				age.setText("未知");
			} else {
				age.setText(item.getMember_age() + "岁");
			}
			if (item.getMember_sex() == 1) {
				age.setTextColor(mContext.getResources().getColor(R.color.sex_nan));
			} else if (item.getMember_sex() == 2) {
				age.setTextColor(mContext.getResources().getColor(R.color.sex_nv));
			} else {
				age.setTextColor(mContext.getResources().getColor(R.color.black));
			}
			TextView price = helper.getView(R.id.tv_price);
			price.setText(item.getMember_price() + "砰砰豆/分钟");
            ImageView ivJiBie = helper.getView(R.id.iv_jibie);
            switch (item.getJb()) {
                case 0:
                    ivJiBie.setVisibility(View.GONE);
                    break;
                case 1:
                    ivJiBie.setVisibility(View.VISIBLE);
                    ivJiBie.setImageResource(R.mipmap.lev_qingtong);
                    break;
                case 2:
                    ivJiBie.setVisibility(View.VISIBLE);
                    ivJiBie.setImageResource(R.mipmap.lev_baiyin);
                    break;
                case 3:
                    ivJiBie.setVisibility(View.VISIBLE);
                    ivJiBie.setImageResource(R.mipmap.lev_huangjin);
                    break;
                case 4:
                    ivJiBie.setVisibility(View.VISIBLE);
                    ivJiBie.setImageResource(R.mipmap.lev_bojin);
                    break;
                case 5:
                    ivJiBie.setVisibility(View.VISIBLE);
                    ivJiBie.setImageResource(R.mipmap.lev_zhuansi);
                    break;
            }
		}
	}




}
