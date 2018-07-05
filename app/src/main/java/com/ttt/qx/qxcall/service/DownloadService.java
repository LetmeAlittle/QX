package com.ttt.qx.qxcall.service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.function.download.Download;
import com.ttt.qx.qxcall.function.download.DownloadProgressListener;
import com.ttt.qx.qxcall.function.download.StringUtils;
import com.ttt.qx.qxcall.function.download.api.DownloadAPI;
import com.ttt.qx.qxcall.function.login.view.AboutWeiCaiActivity;

import java.io.File;

import rx.Subscriber;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static com.ttt.qx.qxcall.QXCallApplication.basePath;

/**
 * Created by JokAr on 16/7/5.
 */
public class DownloadService extends IntentService {
    private static final String TAG = "DownloadService";

    private NotificationCompat.Builder notificationBuilder;
    private NotificationManager notificationManager;

    int downloadCount = 0;

    public static String apkUrl = "http://www.fxbtg.com/packages/2.0.apk";

    public DownloadService() {
        super("DownloadService");
    }

    private File outputFile;

    @Override
    protected void onHandleIntent(Intent intent) {
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.app_icon_iv)
                .setContentTitle("确幸")
                .setContentText("Downloading File")
                .setAutoCancel(true);

        notificationManager.notify(0, notificationBuilder.build());

        download();
    }

    private void download() {
        DownloadProgressListener listener = new DownloadProgressListener() {
            @Override
            public void update(long bytesRead, long contentLength, boolean done) {
                //不频繁发送通知，防止通知栏下拉卡顿
                int progress = (int) ((bytesRead * 100) / contentLength);
                if ((downloadCount == 0) || progress > downloadCount) {
                    Download download = new Download();
                    download.setTotalFileSize(contentLength);
                    download.setCurrentFileSize(bytesRead);
                    download.setProgress(progress);

                    sendNotification(download);
                }
            }
        };
        outputFile = new File(basePath, "meilin.apk");
        if (outputFile.exists()) {
            outputFile.delete();
        }
        String baseUrl = StringUtils.getHostName(apkUrl);
        new DownloadAPI(baseUrl, listener).downloadAPK(apkUrl, outputFile, new Subscriber() {
            @Override
            public void onCompleted() {
                downloadCompleted();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                downloadCompleted();
                Log.e(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onNext(Object o) {

            }
        });
    }

    private void downloadCompleted() {
        Download download = new Download();
        download.setProgress(100);
        sendIntent(download);
        notificationManager.cancel(0);
        notificationBuilder.setProgress(0, 0, false);
        notificationBuilder.setContentText("下载完成");
        notificationManager.notify(0, notificationBuilder.build());

        //安装apk
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(outputFile), "application/vnd.android.package-archive");
        startActivity(intent);
    }

    private void sendNotification(Download download) {

        sendIntent(download);
        notificationBuilder.setProgress(100, download.getProgress(), false);
        notificationBuilder.setContentText(
                StringUtils.getDataSize(download.getCurrentFileSize()) + "/" +
                        StringUtils.getDataSize(download.getTotalFileSize()));
        notificationManager.notify(0, notificationBuilder.build());
    }

    private void sendIntent(Download download) {
        Intent intent = new Intent(AboutWeiCaiActivity.MESSAGE_PROGRESS);
        intent.putExtra("download", download);
        LocalBroadcastManager.getInstance(DownloadService.this).sendBroadcast(intent);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        notificationManager.cancel(0);
    }
}
