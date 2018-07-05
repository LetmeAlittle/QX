package com.ttt.qx.qxcall.function.wxpay;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 王亚东 on 2017/12/10.
 */

public class WXPayData {

    /**
     * message : 请求成功！
     * status_code : 200
     * data : {"paystr":{"appid":"wx1675874735a7dea6","partnerid":"1491601622","prepayid":"wx201712102151412c7a9541b80931950073","package":"Sign=WXPay","noncestr":"ccbn4sl5c6hp056gzvc1co88d1w4imwi","timestamp":1512913901,"sign":"A8E62F166DA4B85C73CF383054411298"}}
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
         * paystr : {"appid":"wx1675874735a7dea6","partnerid":"1491601622","prepayid":"wx201712102151412c7a9541b80931950073","package":"Sign=WXPay","noncestr":"ccbn4sl5c6hp056gzvc1co88d1w4imwi","timestamp":1512913901,"sign":"A8E62F166DA4B85C73CF383054411298"}
         */

        private PaystrBean paystr;

        public PaystrBean getPaystr() {
            return paystr;
        }

        public void setPaystr(PaystrBean paystr) {
            this.paystr = paystr;
        }

        public static class PaystrBean {
            /**
             * appid : wx1675874735a7dea6
             * partnerid : 1491601622
             * prepayid : wx201712102151412c7a9541b80931950073
             * package : Sign=WXPay
             * noncestr : ccbn4sl5c6hp056gzvc1co88d1w4imwi
             * timestamp : 1512913901
             * sign : A8E62F166DA4B85C73CF383054411298
             */

            private String appid;
            private String partnerid;
            private String prepayid;
            @SerializedName("package")
            private String packageX;
            private String noncestr;
            private int timestamp;
            private String sign;

            public String getAppid() {
                return appid;
            }

            public void setAppid(String appid) {
                this.appid = appid;
            }

            public String getPartnerid() {
                return partnerid;
            }

            public void setPartnerid(String partnerid) {
                this.partnerid = partnerid;
            }

            public String getPrepayid() {
                return prepayid;
            }

            public void setPrepayid(String prepayid) {
                this.prepayid = prepayid;
            }

            public String getPackageX() {
                return packageX;
            }

            public void setPackageX(String packageX) {
                this.packageX = packageX;
            }

            public String getNoncestr() {
                return noncestr;
            }

            public void setNoncestr(String noncestr) {
                this.noncestr = noncestr;
            }

            public int getTimestamp() {
                return timestamp;
            }

            public void setTimestamp(int timestamp) {
                this.timestamp = timestamp;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }
        }
    }
}
