package com.af.system.annotation;

import java.lang.annotation.*;

/**
 * 自定义注解，配合 AOP 实现权限数据范围过滤
 * @author AF
 * @date 2021/5/19 20:27
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DataScope {

    String deptAlias() default "";

    String userAlias() default "";
}
