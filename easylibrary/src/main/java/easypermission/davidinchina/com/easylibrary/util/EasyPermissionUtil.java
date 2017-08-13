package easypermission.davidinchina.com.easylibrary.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import easypermission.davidinchina.com.easylibrary.annotation.OnEasyPermissionFailed;
import easypermission.davidinchina.com.easylibrary.annotation.OnEasyPermissionSuccess;

/**
 * author:davidinchina on 2017/8/13 12:23
 * email:davicdinchina@gmail.com
 * version:1.0.0
 * des:
 */
public class EasyPermissionUtil {
    public static boolean isOverMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * 根据请求code在类中查找注解方法
     *
     * @param clazz
     * @param annotation
     * @param requestCode
     * @param <A>
     * @return
     */
    public static <A extends Annotation> Method findMethodWithRequestCode(Class clazz, Class<A> annotation, int requestCode) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getAnnotation(annotation) != null &&//有注解方法且注解名称和请求code相同
                    isEqualRequestCodeFromAnnotation(method, annotation, requestCode)) {
                return method;
            }
        }
        return null;
    }

    public static boolean isEqualRequestCodeFromAnnotation(Method m, Class clazz, int requestCode) {
        if (clazz.equals(OnEasyPermissionFailed.class)) {
            return requestCode == m.getAnnotation(OnEasyPermissionFailed.class).value();
        } else if (clazz.equals(OnEasyPermissionSuccess.class)) {
            return requestCode == m.getAnnotation(OnEasyPermissionSuccess.class).value();
        } else {
            return false;
        }
    }

    @TargetApi(value = Build.VERSION_CODES.M)
    public static List<String> findDeniedPermissions(Activity activity, String... permission) {
        List<String> denyPermissions = new ArrayList<>();
        for (String value : permission) {
            if (activity.checkSelfPermission(value) != PackageManager.PERMISSION_GRANTED) {
                denyPermissions.add(value);
            }
        }
        return denyPermissions;
    }

    public static String[] getPermissions(Context mContext) {
        PackageManager pm = mContext.getPackageManager();
        PackageInfo pi;
        try {
            pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_PERMISSIONS);
            String[] permissions = pi.requestedPermissions;
            List<String> result = new ArrayList<>();
            for (String permission : permissions
                    ) {

            }
            return (String[]) result.toArray();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return new String[]{};
    }
}
