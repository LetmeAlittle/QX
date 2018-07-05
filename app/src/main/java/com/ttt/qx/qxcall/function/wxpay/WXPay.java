package com.ttt.qx.qxcall.function.wxpay;

import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.ttt.qx.qxcall.QXCallApplication;
import com.ttt.qx.qxcall.constant.CommonConstant;

/**
 * Created by 王亚东 on 2017/12/10.
 */

public class WXPay {
    public static void wxpay(WXPayData.DataBean.PaystrBean paystrBean) {
        IWXAPI api = WXAPIFactory.createWXAPI(QXCallApplication.getApplication()
                , CommonConstant.WX_APP_ID, false);
        api.registerApp(CommonConstant.WX_APP_ID);
        //支付请求
        PayReq payReq=new PayReq();
        payReq.appId = paystrBean.getAppid();
        payReq.partnerId = paystrBean.getPartnerid();
        payReq.prepayId = paystrBean.getPrepayid();
        payReq.packageValue = paystrBean.getPackageX();
        payReq.nonceStr = paystrBean.getNoncestr();
        payReq.timeStamp = String.valueOf(paystrBean.getTimestamp());
        payReq.sign = paystrBean.getSign();
        api.sendReq(payReq);
    }
}
