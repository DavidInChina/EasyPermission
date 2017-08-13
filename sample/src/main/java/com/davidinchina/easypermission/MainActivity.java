package com.davidinchina.easypermission;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import easypermission.davidinchina.com.easylibrary.EasyPermission;
import easypermission.davidinchina.com.easylibrary.annotation.OnEasyPermissionFailed;
import easypermission.davidinchina.com.easylibrary.annotation.OnEasyPermissionSuccess;

public class MainActivity extends Activity {
    private static final int REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EasyPermission.with(this).code(REQUEST_CODE).request();
//        .permissions(
//                Manifest.permission.READ_EXTERNAL_STORAGE,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                Manifest.permission.CAMERA,
//                Manifest.permission.READ_PHONE_STATE,
//                Manifest.permission.RECORD_AUDIO,
//                Manifest.permission.ACCESS_COARSE_LOCATION,
//                Manifest.permission.ACCESS_FINE_LOCATION)
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        EasyPermission.handleResult(this, requestCode, permissions, grantResults);
    }

    @OnEasyPermissionSuccess(REQUEST_CODE)
    public void onBasicPermissionSuccess() {
        Toast.makeText(this, "权限申请成功", Toast.LENGTH_SHORT).show();
    }

    @OnEasyPermissionFailed(REQUEST_CODE)
    public void onBasicPermissionFailed() {
        Toast.makeText(this, "权限申请失败，请移步系统设置修改权限", Toast.LENGTH_SHORT).show();
    }

}
