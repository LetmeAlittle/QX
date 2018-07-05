package com.ttt.qx.qxcall.function.login.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;
import android.widget.Toast;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.UserBean;
import com.ttt.qx.qxcall.function.start.BaseActivity;
import com.ttt.qx.qxcall.utils.ToastUtil;

import butterknife.ButterKnife;


/**
 * 关于微财
 * Created by wyd on 2017/8/12.
 */

public class ActionActivity extends BaseActivity implements ISettingView {

    private UserDao mUserDao;
    private UserBean mUserBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ButterKnife.bind(this);
        initData();
//        initView();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mUserDao = new UserDao();
        mUserBean = mUserDao.queryFirstData();
    }

//    /**
//     * 初始化view
//     */
//    private void initView() {
//        WebSettings settings = action_wv.getSettings();
//        //设置支持与JS交互
//        settings.setJavaScriptEnabled(true);
//        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
//        action_wv.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                // TODO Auto-generated method stub
//                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
//                view.loadUrl(url);
//                return true;
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                Message message = Message.obtain();
//                message.what = CLOSE_DIALOG;
//                mHandler.sendMessage(message);
//
//            }
//
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//                Message message = Message.obtain();
//                message.what = SHOW_DIALOG;
//                mHandler.sendMessage(message);
//            }
//        });
//        action_wv.loadUrl("http://m.fxbtg.com/Guessing/activity?signKey=" + mUserBean.getLoginSession());
////        action_wv.loadUrl("http://jtvasd.natappfree.cc/feinong2/activity_list.html?signKey=" + mUserBean.getLoginSession());
////        action_wv.loadUrl("http://j8hstu.natappfree.cc/Radio/fm");
//        //添加js接口
//        action_wv.addJavascriptInterface(new ActionJSInterface(), "action");
//    }
//
//    /**
//     * 活动页JS对象接口
//     */
//    class ActionJSInterface {
//        @JavascriptInterface
//        public void androidMethod() //提供给js调用的方法
//        {
//            //todo
//        }
//
//        @JavascriptInterface
//        public void androidMethod2(String flag, String flag2, String flag3) //提供给js调用的方法...
//        {
//            System.out.println(flag + "=" + flag2 + "=" + flag3);
//        }
//    }
//
//    private static final int SHOW_DIALOG = 2;
//    private static final int CLOSE_DIALOG = 3;
//    private Dialog mDialog;
//    Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case SHOW_DIALOG:
//                    mDialog = CustomAlertDialogUtil.createLoadingDialog(ActionActivity.this, "加载中...", true);
//                    mDialog.show();
//                    break;
//                case CLOSE_DIALOG:
//                    if (mDialog != null && mDialog.isShowing()) {
//                        mDialog.dismiss();
//                    }
//                    break;
//            }
//        }
//    };

    @Override
    public void onToast(String message) {
        //消息弹出
        ToastUtil.show(this, message, Toast.LENGTH_SHORT);
    }

    @Override
    public void onFinish() {
        //销毁当前
        finish();
    }
}
