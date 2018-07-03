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
        //根据清单文件自动获取敏感权限一次性申请，无需过多管理
        EasyPermission.with(this).code(REQUEST_CODE).request();
//        EasyPermission.with(this).code(REQUEST_CODE).permissions(
//                Manifest.permission.READ_EXTERNAL_STORAGE
//        ).request();
//        //根据列出的权限分次获取敏感权限
//        EasyPermission.with(this).code(REQUEST_CODE).permissions(
//                Manifest.permission.READ_EXTERNAL_STORAGE,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                Manifest.permission.CAMERA,
//                Manifest.permission.READ_PHONE_STATE,
//                Manifest.permission.RECORD_AUDIO,
//                Manifest.permission.ACCESS_COARSE_LOCATION,
//                Manifest.permission.ACCESS_FINE_LOCATION).request();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        EasyPermission.handleResult(this, requestCode, permissions, grantResults);//处理权限申请回调结果
    }

    /**
     * @author davidinchina
     * cerate at 2017/8/13 下午2:39
     * @description 运行时注解，权限申请成功回调
     */
    @OnEasyPermissionSuccess(REQUEST_CODE)
    public void onBasicPermissionSuccess() {
        Toast.makeText(this, "权限申请成功", Toast.LENGTH_SHORT).show();
    }

    /**
     * @author davidinchina
     * cerate at 2017/8/13 下午2:39
     * @description 运行时注解，权限申请失败回调
     */
    @OnEasyPermissionFailed(REQUEST_CODE)
    public void onBasicPermissionFailed() {
        Toast.makeText(this, "权限申请失败，请移步系统设置修改权限", Toast.LENGTH_SHORT).show();
    }

}
