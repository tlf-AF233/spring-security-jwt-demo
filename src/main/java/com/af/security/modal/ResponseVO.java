package com.af.security.modal;


import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 自定义返回类
 * @author AF
 * @date 2021/5/14 11:41
 */
@Data
@AllArgsConstructor
public class ResponseVO<T> {
    private Integer code;
    private String msg;
    private T data;
}
