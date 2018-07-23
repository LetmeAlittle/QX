package com.ysxsoft.qxerkai.net.response;

public class UpdatePwdResponse extends BaseResponse {


    private String data;

    public String getData() {
        return data == null ? "" : data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
