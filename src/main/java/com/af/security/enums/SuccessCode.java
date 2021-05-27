package com.af.security.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author AF
 * @date 2021/5/14 11:49
 */
@Getter
@AllArgsConstructor
public enum SuccessCode implements StatusCode {

    SUCCESS_CODE(2000, "请求成功");

    private final Integer code;

    private final String msg;

}
