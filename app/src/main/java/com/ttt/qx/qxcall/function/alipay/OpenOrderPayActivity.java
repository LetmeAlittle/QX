package com.ttt.qx.qxcall.function.alipay;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.H5AuthActivity;
import com.alipay.sdk.app.PayTask;
import com.ttt.qx.qxcall.R;

import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 重要说明:
 * <p>
 * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
 * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
 * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
 */
public class OpenOrderPayActivity extends FragmentActivity {

    /**
     * 支付宝支付业务：入参app_id
     */
    public static final String APPID = "2016081500253507";

    /**
     * 支付宝账户登录授权业务：入参pid值
     */
    public static final String PID = "";
    /**
     * 支付宝账户登录授权业务：入参target_id值
     */
    public static final String TARGET_ID = "";

    /** 商户私钥，pkcs8格式 */
    /** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
    /** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
    /** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
    /** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
    /**
     * 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1
     */
    public static final String RSA2_PRIVATE = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCBCuk1HpnurU/kNIX6HcZjTFZq6AOqzOdHS9gFq7kCY2FQY2mTpiSrmn0vMUEGV9HFsUhppsQnK9VeVrb+EvFbRjZ4bkCX3/9OXNQ1eqpf6aI4t+K9Qi5emjPmN1xykm+rbGkDsG7e3DmLF637aurTnc05qvXDb0ZZSYQDDHAqRmdvrbtd0HTUyLoU29KP7G/OecdVbQNilMs+MVekYbmhysq4e2jZ+NxqTZcQ6DyNzqM/Yg7vCBBoxGlzcQjjXcCNv0uWA5oqI+qVJTeUWDsPRwaF+CaCssFqRoKwIc/EmthAMZdo2tpvh0w/b4OX5iiTqYZf7VcpacGEz+eZrzlZAgMBAAECggEAd4I6Xua7Ww+JeOin1+8xZ48wTYwYsVtZKm90XjQegLa3TnLzOTnapk7KdyVfeldkwHkifi5jZX+z+M3IZd/3RTfuhHRfQAjEcreIFQtItNE0WuD6jZq4RQb8384PwVAbOaCKVU9WLvG4HThN+hWX62ouwaSyPVBVz6VFLGmdi5gacMNChB+CWpFvJwHp/JtMgvCg//C7BTfFOuODblSZcEQ1QpOUfwdigU/VIoANIE+dZLpA+HKwuEeOw2nEBFok/aLPUGHhHnOwJe3Fxb8s4x5svv6mW1qy25VK+g0nKVB/W7DtQIAsFtZCEKlrmEgzeVOsTO5hQb5rmEC8eGC0rQKBgQDRc0uu3DPSTN/hqTGLtXwdvfy7O4M2J43aie8m82Sa7Tee6IM4weC1MoUMr8O1KJZ1RKFeBvmpPkVAFSkfxg6ubCxMjChlXywySvBJvMZF6/C3iupbSetkrm5edPPeFA0qLR0a4CP28HFRwkKz+mWRm5wr5xgwH37LLkiWSQrO9wKBgQCduM53bAVRpUdwfNXn5CwY0+USRhEDy+5NWHaVHjrbZNazRcG5FPGzjd4Q/U5r+Dt5lzsRX46DmTARrDlLSJU6espIlFWo6GNAG/RfXdCZCnjKKPnJiwew/vluKv2v7plPUyj20+DS6GHMWcXdpi70fP0NmqTZbjXUyFC19+YWLwKBgAs6K+9mnFyEhGe7Qw2jH9EUUwzSRtlLS35QcIJc/6KSXq3k9su/EMWFvL4OWeZHE7GbiS7aHt3kmZ+ywq6SVk0hPUqpJK0yadMUU0cQmGw9Fn0wNBnKn5SZCQ+FI2FfH8mH+4xqQWNGRjzEtgChxnoEpl1wGMrTlV9czsVHiw3HAoGAZqMU+PH6pB8CVllmefkqGR4LO9EM0YOsF/kxceiyNINylEcYwfC3OTlhAX1Vwz1OttmLPssIWusdNOL2hd89/UhaytB8zeYscFPAuy65yF3gLO5tXpJy9uN2PhML86k5az2assU+0QEYY3soIA09ES0lH+06yaPGtCmHHGOt/1ECgYA6QAKke5N5E9kD5V5ffLp3+OuaT4K9cb8Xp30Mh8dCIhxJMtd4SVfVmCMNnHNlbcJd+mLixsxlC9lAhqFbS3EcborLScWVS1jxiLwvLCkYVjjKsjp2afKMjvGoI2fmvcYebHFEnVjekd270oiLh9t5s0juueiw6H0Vjq/U99QNBA==";
    public static final String RSA_PRIVATE = "";

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(OpenOrderPayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(OpenOrderPayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(OpenOrderPayActivity.this,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(OpenOrderPayActivity.this,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_order_pay);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.pay_tv})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.pay_tv:

                Runnable payRunnable = new Runnable() {

                    @Override
                    public void run() {
                        PayTask alipay = new PayTask(OpenOrderPayActivity.this);
                        Map<String, String> result = alipay.payV2("app_id=2017092808976485&biz_content=%7B%22body%22%3A%22%E7%A1%AE%E5%B9%B8%E5%85%85%E5%80%BC%22%2C%22subject%22%3A%22%E7%A1%AE%E5%B9%B8%E5%85%85%E5%80%BC%22%2C%22out_trade_no%22%3A%22201710160006456533%22%2C%22total_amount%22%3A%220.01%22%2C%22seller_id%22%3A%222088822316679363%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22goods_type%22%3A%221%22%2C%22passback_params%22%3A%22201710160006456533%22%2C%22timeout_express%22%3A%221439m%22%7D&charset=UTF-8&format=JSON&method=alipay.trade.app.pay&notify_url=http%3A%2F%2F116.62.217.183%2Fapi%2Fpay%2Fnotify_ali&return_url=http%3A%2F%2F116.62.217.183%2Fapi%2Fpay%2Fresult&sign_type=RSA2&timestamp=2017-10-16+00%3A06%3A45&version=1.0&sign=dtbVUbrR7szsCGti3VZsNbiNrpkKtIR34U2gPE9fE5rRdxFycy12w7JZAjKnosIXSscMNyBo%2Fb4neEyZyksOF2HATSqREPJjoySKlxjglHip5F%2FC4CmqVhaz13HP8VqT0BwNc8BbWXvpmoeJEJJSXvFfNPLl4v2h5KIjFLWiIk0HrZKKVUehuM8WjbuYRr9RGwXqwAAMjSj66zyJeA%2FPerCN2eamapvo3%2FbpqFlmbb5FXPapi6CF41eKWVSdng5Ik23V4O0MrH8A%2FEQC21B1BEd8OCHbRAQPl0iDIMGJNHpBzK4DP3snzNEWD23KehwQDW1OGY3toZKbLIMqnseLuw%3D%3D", true);
                        Log.i("msp", result.toString());

                        Message msg = new Message();
                        msg.what = SDK_PAY_FLAG;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    }
                };

                Thread payThread = new Thread(payRunnable);
                payThread.start();
                break;
        }

    }





    /**
     * 支付宝支付业务
     *
     * @param v
     */
    public void payV2(View v) {
        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
            new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            //
                            finish();
                        }
                    }).show();
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(OpenOrderPayActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * 支付宝账户授权业务
     *
     * @param v
     */
    public void authV2(View v) {
        if (TextUtils.isEmpty(PID) || TextUtils.isEmpty(APPID)
                || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))
                || TextUtils.isEmpty(TARGET_ID)) {
            new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置PARTNER |APP_ID| RSA_PRIVATE| TARGET_ID")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                        }
                    }).show();
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * authInfo的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> authInfoMap = OrderInfoUtil2_0.buildAuthInfoMap(PID, APPID, TARGET_ID, rsa2);
        String info = OrderInfoUtil2_0.buildOrderParam(authInfoMap);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(authInfoMap, privateKey, rsa2);
        final String authInfo = info + "&" + sign;
        Runnable authRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造AuthTask 对象
                AuthTask authTask = new AuthTask(OpenOrderPayActivity.this);
                // 调用授权接口，获取授权结果
                Map<String, String> result = authTask.authV2(authInfo, true);

                Message msg = new Message();
                msg.what = SDK_AUTH_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread authThread = new Thread(authRunnable);
        authThread.start();
    }

    /**
     * get the sdk version. 获取SDK版本号
     */
    public void getSDKVersion() {
        PayTask payTask = new PayTask(this);
        String version = payTask.getVersion();
        Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
    }

    /**
     * 原生的H5（手机网页版支付切natvie支付） 【对应页面网页支付按钮】
     *
     * @param v
     */
    public void h5Pay(View v) {
        Intent intent = new Intent(this, H5AuthActivity.class);
        Bundle extras = new Bundle();
        /**
         * url 是要测试的网站，在 Demo App 中会使用 H5PayDemoActivity 内的 WebView 打开。
         *
         * 可以填写任一支持支付宝支付的网站（如淘宝或一号店），在网站中下订单并唤起支付宝；
         * 或者直接填写由支付宝文档提供的“网站 Demo”生成的订单地址
         * （如 https://mclient.alipay.com/h5Continue.htm?h5_route_token=303ff0894cd4dccf591b089761dexxxx）
         * 进行测试。
         *
         * H5PayDemoActivity 中的 MyWebViewClient.shouldOverrideUrlLoading() 实现了拦截 URL 唤起支付宝，
         * 可以参考它实现自定义的 URL 拦截逻辑。
         */
        String url = "http://m.taobao.com";
        extras.putString("url", url);
        intent.putExtras(extras);
        startActivity(intent);
    }

}
