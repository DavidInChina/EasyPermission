package easypermission.davidinchina.com.easylibrary.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author:davidinchina on 2017/8/13 11:58
 * email:davicdinchina@gmail.com
 * version:1.0.0
 * des: 权限请求成功回调
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnEasyPermissionSuccess {
    int value() default 101;
}
