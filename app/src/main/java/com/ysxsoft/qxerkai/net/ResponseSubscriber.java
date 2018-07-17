package com.ysxsoft.qxerkai.net;

import android.databinding.Observable;

import com.ysxsoft.qxerkai.net.response.BaseResponse;

import rx.Observer;
import rx.Subscriber;

public abstract class ResponseSubscriber<T extends BaseResponse> extends Subscriber<T> implements Observer<T> {
    public ResponseSubscriber() {
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        onFailed(e);
    }

    @Override
    public void onNext(T t) {
        if (t == null) {
            onError(new IllegalArgumentException("the request not response"));
        } else {
            onSuccess(t, t.status_code, t.getMessage());
        }
    }

    public abstract void onSuccess(T t, int code, String msg);

    public abstract void onFailed(Throwable e);

    @Override
    public void onStart() {
        super.onStart();
    }
}
