package com.ttt.qx.qxcall.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.ttt.qx.qxcall.constant.CommonConstant;
import com.ttt.qx.qxcall.eventbus.PaySuccess;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by 王亚东 on 2017/12/10.
 */

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final int TIMELINE_SUPPORTED_VERSION = 0x21020001;

    private Button gotoBtn, regBtn, launchBtn, checkBtn, scanBtn;

    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, CommonConstant.WX_APP_ID, false);
        //注意：
        //第三方开发者如果使用透明界面来实现WXEntryActivity，需要判断handleIntent的返回值，如果返回值为false，则说明入参不合法未被SDK处理，应finish当前透明界面，避免外部通过传递非法参数的Intent导致停留在透明界面，引起用户的疑惑
        try {
            api.handleIntent(getIntent(), this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {
        switch (req.getType()) {
//            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
//                goToGetMsg();
//                break;
//            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
//                goToShowMsg((ShowMessageFromWX.Req) req);
//                break;
            default:
                break;
        }
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            String result = "支付失败";
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    EventBus.getDefault().post(new PaySuccess());
                    result = "支付成功";
                    break;
//                case BaseResp.ErrCode.ERR_USER_CANCEL:
//                    result = result + BaseResp.ErrCode.ERR_USER_CANCEL;
//                    break;
//                case BaseResp.ErrCode.ERR_AUTH_DENIED:
//                    result = result + BaseResp.ErrCode.ERR_AUTH_DENIED;
//                    break;
//                case BaseResp.ErrCode.ERR_UNSUPPORT:
//                    result = result + BaseResp.ErrCode.ERR_UNSUPPORT;
//                    break;
//                default:
//                    result = result + BaseResp.ErrCode.ERR_USER_CANCEL;
//                    break;
            }
            Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        }
        finish();
    }
}
