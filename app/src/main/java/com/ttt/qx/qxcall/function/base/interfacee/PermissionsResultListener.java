package com.ttt.qx.qxcall.function.base.interfacee;

/**
 * 应用权限处理
 * Created by wyd on 2017/7/31.
 */

public interface PermissionsResultListener {
    void onPermissionGranted();

    void onPermissionDenied();
}
