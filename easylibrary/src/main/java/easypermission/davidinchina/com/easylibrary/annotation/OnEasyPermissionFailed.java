package easypermission.davidinchina.com.easylibrary.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author:davidinchina on 2017/8/13 12:00
 * email:davicdinchina@gmail.com
 * version:1.0.0
 * des: 权限请求失败回调
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OnEasyPermissionFailed {
    int value() default 101;
}
