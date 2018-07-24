package com.ysxsoft.qxerkai.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.constant.CommonConstant;
import com.ttt.qx.qxcall.utils.ToastUtil;
import com.ysxsoft.qxerkai.net.ResponseSubscriber;
import com.ysxsoft.qxerkai.net.RetrofitApi;
import com.ysxsoft.qxerkai.net.RetrofitTools;
import com.ysxsoft.qxerkai.net.response.CheckVersionResponse;
import com.ysxsoft.qxerkai.view.activity.NSettingActivity;
import com.ysxsoft.qxerkai.view.widget.UpdateDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * 升级更新
 */
public class DownloadUtils {
	private static final String TAG = "DownloadUtils";

	public static void download(Context context, String url, int code) {
		if (code > getVersion(context)) {//如果返回的code大于当前版本的 更新
			File file = new File(CommonConstant.APK_PATH);
			if (!file.exists()) {
				file.mkdirs();
			}
			download(context, url, file.getPath() + "/pengpeng.apk");//路径为   /apk/pengpeng.apk
		} else {
			ToastUtils.showToast(context, "当前已是最新版!", 1);
		}
	}

	/**
	 * 获取版本号
	 *
	 * @return 当前应用的版本号
	 */
	public static int getVersion(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
			int version = info.versionCode;
			return version;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * 下载文件
	 *
	 * @param url
	 */
	public static void download(Context context, String url, String filePath) {
		Retrofit retrofit = new Retrofit.Builder().baseUrl(CommonConstant.COMMON_BASE_URL).build();
		final RetrofitApi service = retrofit.create(RetrofitApi.class);
//		new AsyncTask<Void, Long, Void>() {
//			@Override
//			protected Void doInBackground(Void... voids) {
				Call<ResponseBody> call = service.downloadFile(url);
				call.enqueue(new Callback<ResponseBody>() {
					@Override
					public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
						if (response.isSuccessful()) {
							Log.e(TAG, "server contacted and has file");
							new Thread(new Runnable() {
								@Override
								public void run() {
									boolean writtenToDisk = writeResponseBodyToDisk(response.body(), filePath);
									if(writtenToDisk){
										installApk(context, filePath);
									}
								}
							}).start();
						} else {
							Log.e(TAG, "server contact failed");
						}
					}

					@Override
					public void onFailure(Call<ResponseBody> call, Throwable t) {
						Log.e(TAG, "server contact failed");
					}
				});
//				return null;
//			}
//		}.execute();
	}

	/**
	 * 保存到本地
	 *
	 * @param body
	 * @param filePath
	 * @return
	 */
	public static boolean writeResponseBodyToDisk(ResponseBody body, String filePath) {
		try {
			File file = new File(filePath);
			if (file.exists()) {
				file.delete();
				file.createNewFile();
			}
			InputStream inputStream = null;
			OutputStream outputStream = null;
			try {
				byte[] fileReader = new byte[4096];
				long fileSize = body.contentLength();
				long fileSizeDownloaded = 0;
				inputStream = body.byteStream();
				outputStream = new FileOutputStream(file);
				while (true) {
					int read = inputStream.read(fileReader);
					if (read == -1) {
						break;
					}
					outputStream.write(fileReader, 0, read);
					fileSizeDownloaded += read;
					Log.e(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
				}
				outputStream.flush();
				return true;
			} catch (IOException e) {
				return false;
			} finally {
				if (inputStream != null) {
					inputStream.close();
				}
				if (outputStream != null) {
					outputStream.close();
				}
			}
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * 安装apk
	 */
	public static void installApk(Context context, String path) {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { //判读版本是否在7.0以上
			//参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
			Uri apkUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", new File(path));
			//添加这一句表示对目标应用临时授权该Uri所代表的文件
			intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
		} else {
			intent.setDataAndType(Uri.fromFile(new File(path)), "application/vnd.android.package-archive");
		}
		context.startActivity(intent);
	}
	///////////////////////////////////////////////////////////////////////////
	// 弹窗
	///////////////////////////////////////////////////////////////////////////

	/**
	 * 检测版本
	 */
	public static void checkVersion(Context context) {
//		DownloadUtils.download(context,"http://fanxing.ysxapp.com/data/pos.apk",26);
		Map<String, String> map = new HashMap<>();
		map.put("type", "1");
		RetrofitTools.checkVersion(map)
				.subscribe(new ResponseSubscriber<CheckVersionResponse>() {
					@Override
					public void onSuccess(CheckVersionResponse checkVersionResponse, int code, String msg) {
						LogUtils.e("checkVersion onSuccess");
						if (code == 200) {
							UpdateDialog dialog = new UpdateDialog(context, R.style.dialogHuaTiStyle);
							dialog.setTitle("发现新版本");
							dialog.setContent(checkVersionResponse.getData().getContent());
							dialog.show(new UpdateDialog.OnUpdateListener() {
								@Override
								public void start() {
									ToastUtils.showToast(context,"下载中",1);
									//下载文件
//									DownloadUtils.download(context,checkVersionResponse.getData().getFileAbsolutePath(),checkVersionResponse.getData().getVerCode());
									DownloadUtils.download(context,checkVersionResponse.getData().getFileAbsolutePath(),27);
								}
							});
						} else {
							ToastUtil.showToast(context, msg);
						}
					}

					@Override
					public void onFailed(Throwable e) {
						LogUtils.e("checkVersion onFailed" + e.getMessage());
					}
				});
	}
}
