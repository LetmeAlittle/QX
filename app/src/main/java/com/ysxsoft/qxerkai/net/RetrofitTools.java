package com.ysxsoft.qxerkai.net;

import android.support.annotation.RestrictTo;

import com.umeng.socialize.media.Base;
import com.ysxsoft.qxerkai.net.response.BaseResponse;
import com.ysxsoft.qxerkai.net.response.GetCardDetailResponse;
import com.ysxsoft.qxerkai.net.response.GetCardListResponse;
import com.ysxsoft.qxerkai.net.response.GetHuaTiListResponse;
import com.ysxsoft.qxerkai.net.response.GetLiaoRenListResponse;
import com.ysxsoft.qxerkai.net.response.GetLuYinListResponse;
import com.ysxsoft.qxerkai.net.response.GetNoticeListResponse;
import com.ysxsoft.qxerkai.net.response.GetPiPeiListResponse;
import com.ysxsoft.qxerkai.net.response.GetTouTingDetailResponse;
import com.ysxsoft.qxerkai.net.response.GetTouTingListResponse;
import com.ysxsoft.qxerkai.net.response.GetQuestionRespose;
import com.ysxsoft.qxerkai.net.response.GuardsListResponse;
import com.ysxsoft.qxerkai.net.response.HaoYouListResponse;
import com.ysxsoft.qxerkai.net.response.HomeItemList;
import com.ysxsoft.qxerkai.net.response.MemberListResponse;
import com.ysxsoft.qxerkai.net.response.MyLiWuResponse;
import com.ysxsoft.qxerkai.net.response.RuleResponse;
import com.ysxsoft.qxerkai.net.response.SaGouLiangLikeResponse;
import com.ysxsoft.qxerkai.net.response.SaGouLiangListResponse;
import com.ysxsoft.qxerkai.net.response.SaGouLiangPublishResponse;
import com.ysxsoft.qxerkai.net.response.SearchListResponse;
import com.ysxsoft.qxerkai.net.response.TwoPageTuiJianResponse;
import com.ysxsoft.qxerkai.net.response.UserXiaoFeiNum;
import com.ysxsoft.qxerkai.utils.LogUtils;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.ttt.qx.qxcall.constant.CommonConstant.CARD_ADD;
import static com.ttt.qx.qxcall.constant.CommonConstant.COMMON_BASE_URL;
import static com.ttt.qx.qxcall.constant.CommonConstant.FRIEND_Q_backgroundqh;
import static com.ttt.qx.qxcall.constant.CommonConstant.SA_GOU_LIANG_COMMIT;

public class RetrofitTools {
	public static RetrofitTools instance;

	public static RetrofitApi getManager(String url) {
		return getInstance().getBuilder().baseUrl(url).build().create(RetrofitApi.class);
	}

	public static Retrofit.Builder getBuilder() {
		return new Retrofit.Builder()
				.baseUrl(COMMON_BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.client(new OkHttpClient.Builder()
						.addInterceptor(new LogInterceptor())
						.build());
	}

	public static RetrofitTools getInstance() {
		if (instance == null) {
			synchronized (RetrofitTools.class) {
				instance = new RetrofitTools();
			}
		}
		return instance;
	}

	/**
	 * 撒狗粮列表
	 *
	 * @param map
	 * @return
	 */
	public static Observable<SaGouLiangListResponse> getSaGouLiangList(Map<String, String> map) {
		return subscribe(RetrofitTools.getManager().getSaGouLiangList(map));
	}

	///////////////////////////////////////////////////////////////////////////
	// 网络请求 基础Method
	///////////////////////////////////////////////////////////////////////////
	public static <T> Observable<T> subscribe(Observable<T> responseObservable) {
		return responseObservable
				.observeOn(AndroidSchedulers.mainThread())
				.subscribeOn(Schedulers.io())
				.unsubscribeOn(Schedulers.io());
	}

	public static RetrofitApi getManager() {
		return getBuilder().build().create(RetrofitApi.class);
	}

	/**
	 * 撒狗粮点赞
	 *
	 * @param map
	 * @return
	 */
	public static Observable<SaGouLiangLikeResponse> likeSaGouLiang(Map<String, String> map) {
		return subscribe(RetrofitTools.getManager().likeSaGouLiang(map));
	}

	/**
	 * 关于我们/我的收益规则/萨狗粮规则/萨狗粮奖品/使用说明/vip特权
	 *
	 * @param map
	 * @return
	 */
	public static Observable<RuleResponse> getRule(Map<String, String> map) {
		return subscribe(RetrofitTools.getManager().getRule(map));
	}

	///////////////////////////////////////////////////////////////////////////
	// 网络请求 Api
	///////////////////////////////////////////////////////////////////////////

	/**
	 * 发布撒狗粮    图片
	 *
	 * @param map
	 * @return
	 */
	public static Observable<SaGouLiangPublishResponse> publishSaGouLiang(Map<String, String> map, String[] imageNames, File[] imageFiles) {
		return subscribe(RetrofitTools.getManager().publishSaGouLiang(COMMON_BASE_URL + SA_GOU_LIANG_COMMIT, builder(map, imageNames, imageFiles).build()));
	}

	/**
	 * 请求体builder  上传文件
	 *
	 * @param map
	 * @param imageNames
	 * @param imageFile
	 * @return
	 */
	public static MultipartBody.Builder builder(Map<String, String> map, String[] imageNames, File[] imageFile) {
		MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
		if (map != null) {
			Set<String> keys = map.keySet();
			Iterator<String> iterator = keys.iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				builder.addFormDataPart(key, map.get(key));
			}
		}
		if (imageNames != null && imageFile != null && imageNames.length == imageFile.length) {
			int length = imageNames.length;
			for (int i = 0; i < length; i++) {
				builder.addFormDataPart(imageNames[i], imageFile[i].getName(), RequestBody.create(MediaType.parse("image/*"), imageFile[i]));
			}
		} else {
			throw new IllegalArgumentException("The param imageNames is null");
		}
		return builder;
	}

	/**
	 * 更换朋友圈背景    图片
	 *
	 * @param map
	 * @return
	 */
	public static Observable<SaGouLiangPublishResponse> commitFriendBg(Map<String, String> map, String[] imageNames, File[] imageFiles) {
		return subscribe(RetrofitTools.getManager().publishSaGouLiang(COMMON_BASE_URL + FRIEND_Q_backgroundqh, builder(map, imageNames, imageFiles).build()));
	}

	/**
	 * 发布撒狗粮    文字
	 *
	 * @param map
	 * @return
	 */
	public static Observable<SaGouLiangPublishResponse> publishSaGouLiang(Map<String, String> map) {
		return subscribe(RetrofitTools.getManager().publishSaGouLiang(map));
	}

	/**
	 * 对某人进行守护
	 *
	 * @param map
	 * @return
	 */
	public static Observable<BaseResponse> getGuardsing(Map<String, String> map) {
		return subscribe(RetrofitTools.getManager().getGuardsing(map));
	}

	/**
	 * 用户的守护列表/用户守护的列表
	 *
	 * @param map
	 * @return
	 */
	public static Observable<GuardsListResponse> getGuardsingList(Map<String, String> map) {
		return subscribe(RetrofitTools.getManager().getGuardsingList(map));
	}

	/**
	 * 小情趣列表
	 *
	 * @param map
	 * @return
	 */
	public static Observable<GetCardListResponse> getCardList(Map<String, String> map) {
		return subscribe(RetrofitTools.getManager().getCardList(map));
	}

	/**
	 * 小情趣帖子详情
	 *
	 * @param map
	 * @return
	 */
	public static Observable<GetCardDetailResponse> getCardDetail(Map<String, String> map) {
		return subscribe(RetrofitTools.getManager().getCardDetail(map));
	}

	/**
	 * 小情趣帖子评论
	 *
	 * @param map
	 * @return
	 */
	public static Observable<BaseResponse> submitCardComment(Map<String, String> map) {
		return subscribe(RetrofitTools.getManager().submitCardComment(map));
	}

	/**
	 * 小情趣 公告
	 *
	 * @param map
	 * @return
	 */
	public static Observable<GetNoticeListResponse> getNoticeList(Map<String, String> map) {
		return subscribe(RetrofitTools.getManager().getNoticeList(map));
	}

	/**
	 * 小情趣点赞
	 *
	 * @param map
	 * @return
	 */
	public static Observable<BaseResponse> cardLike(Map<String, String> map) {
		return subscribe(RetrofitTools.getManager().cardLike(map));
	}

	/**
	 * 发布帖子  标题 文本
	 *
	 * @param map
	 * @return
	 */
	public static Observable<BaseResponse> publishCard(Map<String, String> map) {
		return subscribe(RetrofitTools.getManager().publishCard(map));
	}

	/**
	 * 发布帖子  标题 文本 图片
	 *
	 * @param map
	 * @return
	 */
	public static Observable<BaseResponse> publishCard(Map<String, String> map, String[] imageNames, File[] imageFiles) {
		return subscribe(RetrofitTools.getManager().publishCard(COMMON_BASE_URL + CARD_ADD, builder(map, imageNames, imageFiles).build()));
	}

	/**
	 * 撩人区列表
	 *
	 * @param map
	 * @return
	 */
	public static Observable<GetLiaoRenListResponse> getLiaoRenList(Map<String, String> map) {
		return subscribe(RetrofitTools.getManager().getLiaoRenList(map));
	}

	/**
	 * 撩人区列表
	 *
	 * @param map
	 * @return
	 */
	public static Observable<TwoPageTuiJianResponse> getTuiJianList(Map<String, String> map) {
		return subscribe(RetrofitTools.getManager().getTuiJianList(map));
	}

	/**
	 * 获取附近的人列表  私人定制
	 *
	 * @param map
	 * @return
	 */
	public static Observable<HaoYouListResponse> getFjRenList(Map<String, String> map) {
		return subscribe(RetrofitTools.getManager().getFjRenList(map));
	}

	/**
	 * 获取好友列表  私人定制
	 *
	 * @param map
	 * @return
	 */
	public static Observable<HaoYouListResponse> getHaoYouList(Map<String, String> map) {
		return subscribe(RetrofitTools.getManager().getHaoYouList(map));
	}

	/**
	 * 搜索好友  私人定制
	 *
	 * @param map
	 * @return
	 */
	public static Observable<SearchListResponse> getSearchList(Map<String, String> map) {
		return subscribe(RetrofitTools.getManager().getSearchList(map));
	}

	/**
	 * 获取好友列表  私人定制
	 *
	 * @param map type 1土豪2一姐
	 * @return
	 */
	public static Observable<HaoYouListResponse> getTopList(Map<String, String> map) {
		return subscribe(RetrofitTools.getManager().getTopList(map));
	}

	/**
	 * 获取我的礼物列表
	 *
	 * @param map
	 * @return
	 */
	public static Observable<MyLiWuResponse> getMyLiWuList(Map<String, String> map) {
		return subscribe(RetrofitTools.getManager().getMyLiWuList(map));
	}

	/**
	 * 获取用户消费的砰砰豆
	 *
	 * @param map
	 * @return
	 */
	public static Observable<UserXiaoFeiNum> getUserXiaoFei(Map<String, String> map) {
		return subscribe(RetrofitTools.getManager().getUserXiaoFei(map));
	}

	/**
	 * 获取首页用户列表
	 *
	 * @param map
	 * @return
	 */
	public static Observable<HomeItemList> getHomeItemList(Map<String, String> map) {
		return subscribe(RetrofitTools.getManager().getHomeItemList(map));
	}

	/**
	 * 漂浮的话题列表
	 *
	 * @param map
	 * @return
	 */
	public static Observable<GetHuaTiListResponse> getHuaTiList(Map<String, String> map) {
		return subscribe(RetrofitTools.getManager().getHuaTiList(map));
	}

	/**
	 * 漂浮的话题列表
	 *
	 * @param map
	 * @return
	 */
	public static Observable<BaseResponse> addHuaTi(Map<String, String> map) {
		return subscribe(RetrofitTools.getManager().addHuaTi(map));
	}

	/**
	 * 抢话题/继续聊
	 *
	 * @param map
	 * @return
	 */
	public static Observable<BaseResponse> startHuaTi(Map<String, String> map) {
		return subscribe(RetrofitTools.getManager().huaTiStart(map));
	}

	/**
	 * 一键匹配
	 *
	 * @param map
	 * @return
	 */
	public static Observable<GetPiPeiListResponse> getPiPeiList(Map<String, String> map) {
		return subscribe(RetrofitTools.getManager().getPiPeiList(map));
	}

	/**
	 * 开始匹配
	 *
	 * @param map
	 * @return
	 */
	public static Observable<BaseResponse> startPiPei(Map<String, String> map) {
		return subscribe(RetrofitTools.getManager().startPiPei(map));
	}

	/**
	 * 一键匹配 每分钟扣钱
	 *
	 * @param map
	 * @return
	 */
	public static Observable<BaseResponse> piPeiBuy(Map<String, String> map) {
		return subscribe(RetrofitTools.getManager().piPeiBuy(map));
	}

	/**
	 * 往相册上传图片
	 *
	 * @return
	 */
	public static Observable<BaseResponse> uploadIcon(Map<String, String> map, String[] imageNames, File[] imageFiles) {
		return subscribe(RetrofitTools.getManager().uploadIcon(builder(map, imageNames, imageFiles).build()));
	}

	/**
	 * 删除相册中的图片
	 *
	 * @return
	 */
	public static Observable<BaseResponse> delIcon(Map<String, String> map) {
		return subscribe(RetrofitTools.getManager().delIcon(map));
	}

	/**
	 * 获取偷听列表
	 *
	 * @return
	 */
	public static Observable<GetTouTingListResponse> getTouTingList(Map<String, String> map) {
		return subscribe(RetrofitTools.getManager().getTouTingList(map));
	}

	/**
	 * 获取录音列表
	 *
	 * @return
	 */
	public static Observable<GetLuYinListResponse> getLuYinList(Map<String, String> map) {
		return subscribe(RetrofitTools.getManager().getLuYinList(map));
	}

	/**
	 * 获取偷听详情
	 *
	 * @return
	 */
	public static Observable<GetTouTingDetailResponse> getTouTingDetail(Map<String, String> map) {
		return subscribe(RetrofitTools.getManager().getTouTingDetail(map));
	}

	/**
	 * 获取偷听详情
	 *
	 * @return
	 */
	public static Observable<BaseResponse> suo(Map<String, String> map) {
		return subscribe(RetrofitTools.getManager().suo(map));
	}

	/**
	 * 获取用户身份列表
	 *
	 * @return
	 */
	public static Observable<MemberListResponse> getMemberList() {
		return subscribe(RetrofitTools.getManager().getMemberList());
	}

	/**
	 * 获取用户身份列表
	 *
	 * @return
	 */
	public static Observable<GetQuestionRespose> getQuestionList() {
		return subscribe(RetrofitTools.getManager().getQuestionList());
	}

	/**
	 *发弹幕
	 *
	 * @return
	 */
	public static Observable<BaseResponse> fadanmu(Map<String,String> map) {
		return subscribe(RetrofitTools.getManager().fadanmu(map));
	}

	/**
	 * 偷听/录音解锁
	 *
	 * @return
	 */
	public static Observable<BaseResponse> jiesuo(Map<String,String> map) {
		return subscribe(RetrofitTools.getManager().jiesuo(map));
	}

	/**
	 *检测房间是否能被偷听
	 *
	 * @return
	 */
	public static Observable<BaseResponse> checkTouTing(Map<String,String> map) {
		return subscribe(RetrofitTools.getManager().checkTouTing(map));
	}


	/**
	 * OkHttp过滤器
	 */
	private static class LogInterceptor implements Interceptor {
		@Override
		public okhttp3.Response intercept(Chain chain) throws IOException {
			Request request = chain.request();
			LogUtils.e("RequestTool-->  request:" + request.toString());
			okhttp3.Response response = chain.proceed(request);
			okhttp3.MediaType mediaType = response.body().contentType();
			String content = response.body().string();
			LogUtils.e("RequestTool-->  response body:" + content);
			if (response.body() != null) {
				ResponseBody body = ResponseBody.create(mediaType, content);
				return response.newBuilder().body(body).build();
			} else {
				return response;
			}
		}
	}
}
