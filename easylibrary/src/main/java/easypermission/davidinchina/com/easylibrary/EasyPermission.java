package easypermission.davidinchina.com.easylibrary;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import easypermission.davidinchina.com.easylibrary.annotation.OnEasyPermissionFailed;
import easypermission.davidinchina.com.easylibrary.annotation.OnEasyPermissionSuccess;
import easypermission.davidinchina.com.easylibrary.util.EasyPermissionUtil;

/**
 * author:davidinchina on 2017/8/13 11:47
 * email:davicdinchina@gmail.com
 * version:1.0.0
 * des:动态权限申请工具
 */
public class EasyPermission {
    private Activity activity;
    private String[] permissions;
    private int requestCode = 101;//默认值


    private EasyPermission(Activity object) {
        this.activity = object;
    }

    public static EasyPermission with(Activity activity) {
        return new EasyPermission(activity);
    }

    public EasyPermission permissions(String... permissions) {
        this.permissions = permissions;
        return this;
    }

    public EasyPermission code(int requestCode) {
        this.requestCode = requestCode;
        return this;
    }

    @TargetApi(value = Build.VERSION_CODES.M)
    public void request() {
        if (null == this.activity) {
            throw new IllegalArgumentException("null activity is not supported");
        }
        if (null == this.permissions) {
            //自动获取敏感权限列表
            this.permissions = EasyPermissionUtil.getPermissions(activity);
        }
        requestPermissions(activity, requestCode, permissions);
    }

    @TargetApi(value = Build.VERSION_CODES.M)
    private static void requestPermissions(Activity activity, int requestCode, String[] permissions) {
        if (!EasyPermissionUtil.isOverMarshmallow()) {
            doExecuteSuccess(activity, requestCode);
            return;
        }
        List<String> deniedPermissions = EasyPermissionUtil.findDeniedPermissions(activity, permissions);
        if (deniedPermissions.size() > 0) {
            activity.requestPermissions(deniedPermissions.toArray(new String[deniedPermissions.size()]), requestCode);
        } else {
            doExecuteSuccess(activity, requestCode);
        }
    }

    /**
     * 权限请求结果处理
     *
     * @param obj
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public static void handleResult(Activity obj, int requestCode, String[] permissions, int[] grantResults) {
        List<String> deniedPermissions = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {//权限未授予
                deniedPermissions.add(permissions[i]);
            }
        }
        if (deniedPermissions.size() > 0) {
            doExecuteFail(obj, requestCode);
            //直接反馈申请权限失败，不再次申请权限
        } else {
//            LogUtil.error("申请成功");
            doExecuteSuccess(obj, requestCode);
        }
    }

    /**
     * 回调成功方法
     *
     * @param activity
     * @param requestCode
     */
    private static void doExecuteSuccess(Activity activity, int requestCode) {
        executeMethod(activity, EasyPermissionUtil.findMethodWithRequestCode(activity.getClass(),
                OnEasyPermissionSuccess.class, requestCode));
    }

    /**
     * 回调失败方法
     *
     * @param activity
     * @param requestCode
     */
    private static void doExecuteFail(Activity activity, int requestCode) {
        executeMethod(activity, EasyPermissionUtil.findMethodWithRequestCode(activity.getClass(),
                OnEasyPermissionFailed.class, requestCode));
    }

    private static void executeMethod(Object activity, Method executeMethod) {
        executeMethodWithParam(activity, executeMethod, new Object[]{});
    }

    /**
     * 根据注解回调相应的方法
     *
     * @param activity
     * @param executeMethod
     * @param args
     */
    private static void executeMethodWithParam(Object activity, Method executeMethod, Object... args) {
        if (executeMethod != null) {
            try {
                if (!executeMethod.isAccessible()) {
                    executeMethod.setAccessible(true);
                }
                executeMethod.invoke(activity, args);//回调注解方法
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
