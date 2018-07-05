package com.ttt.qx.qxcall.function.login.model.entity;

/**
 * 获取用户支付订单信息
 * Created by wyd on 2017/10/16.
 */

public class GetPayInfoResponse {

    /**
     * message : 请求成功！
     * status_code : 200
     * data : {"paystr":"app_id=2017092808976485&amp;biz_content=%7B%22body%22%3A%22%E7%A1%AE%E5%B9%B8%E5%85%85%E5%80%BC%22%2C%22subject%22%3A%22%E7%A1%AE%E5%B9%B8%E5%85%85%E5%80%BC%22%2C%22out_trade_no%22%3A%22201710161018097870%22%2C%22total_amount%22%3A%222000%22%2C%22seller_id%22%3A%222088822316679363%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22goods_type%22%3A%221%22%2C%22passback_params%22%3A%22201710161018097870%22%2C%22timeout_express%22%3A%221439m%22%7D&amp;charset=UTF-8&amp;format=JSON&amp;method=alipay.trade.app.pay&amp;notify_url=http%3A%2F%2F116.62.217.183%2Fapi%2Fpay%2Fnotify_ali&amp;return_url=http%3A%2F%2F116.62.217.183%2Fapi%2Fpay%2Fresult&amp;sign_type=RSA2&amp;timestamp=2017-10-16+10%3A18%3A09&amp;version=1.0&amp;sign=g88JREj9yXpANuSxmH9npxQEdJ9qD4dzfwJOv0YEnRmZpgsMF3OcJZ3wLICdQXgZlg9xManY5o4xXjW06WwPZDWb9Bw0JYBIec99QkcKgZiThP9PtLCtxS4hz4BBDZDr8Svhsltxby6mdcpraPUZ0j80Jyte9BsoIxcyyFof%2Fz%2F2jA0poG%2B7zXJfdip7ZiZsA7iRiKlkBmpn3jL40uR%2B8UyVUeup332muW4pjir4oWrVljpjsp6qCgLT3XARl%2BWQI9wWvdm3XcWK4PDDOQCwyXhvKdIEweTbGAliFsuEySTfIIkvlEQKySbnfRyz9mHC1EsR9qtRCDVdrChPwWqKiA%3D%3D"}
     */

    private String message;
    private int status_code;
    private DataBean data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * paystr : app_id=2017092808976485&amp;biz_content=%7B%22body%22%3A%22%E7%A1%AE%E5%B9%B8%E5%85%85%E5%80%BC%22%2C%22subject%22%3A%22%E7%A1%AE%E5%B9%B8%E5%85%85%E5%80%BC%22%2C%22out_trade_no%22%3A%22201710161018097870%22%2C%22total_amount%22%3A%222000%22%2C%22seller_id%22%3A%222088822316679363%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22goods_type%22%3A%221%22%2C%22passback_params%22%3A%22201710161018097870%22%2C%22timeout_express%22%3A%221439m%22%7D&amp;charset=UTF-8&amp;format=JSON&amp;method=alipay.trade.app.pay&amp;notify_url=http%3A%2F%2F116.62.217.183%2Fapi%2Fpay%2Fnotify_ali&amp;return_url=http%3A%2F%2F116.62.217.183%2Fapi%2Fpay%2Fresult&amp;sign_type=RSA2&amp;timestamp=2017-10-16+10%3A18%3A09&amp;version=1.0&amp;sign=g88JREj9yXpANuSxmH9npxQEdJ9qD4dzfwJOv0YEnRmZpgsMF3OcJZ3wLICdQXgZlg9xManY5o4xXjW06WwPZDWb9Bw0JYBIec99QkcKgZiThP9PtLCtxS4hz4BBDZDr8Svhsltxby6mdcpraPUZ0j80Jyte9BsoIxcyyFof%2Fz%2F2jA0poG%2B7zXJfdip7ZiZsA7iRiKlkBmpn3jL40uR%2B8UyVUeup332muW4pjir4oWrVljpjsp6qCgLT3XARl%2BWQI9wWvdm3XcWK4PDDOQCwyXhvKdIEweTbGAliFsuEySTfIIkvlEQKySbnfRyz9mHC1EsR9qtRCDVdrChPwWqKiA%3D%3D
         */

        private String paystr;

        public String getPaystr() {
            return paystr;
        }

        public void setPaystr(String paystr) {
            this.paystr = paystr;
        }
    }
}
