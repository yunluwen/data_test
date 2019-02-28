package com.zyh.house_scala.concurrent.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解
 * 用来标记【线程安全】的类
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)           //注解存在的范围
public @interface ThreadSafe {

    String value() default "";

}
