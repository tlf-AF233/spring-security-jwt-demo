package com.af.security.util;

import com.af.security.enums.ErrorCode;
import com.af.security.enums.StatusCode;
import com.af.security.enums.SuccessCode;
import com.af.security.modal.ResponseVO;

/**
 * 构造返回信息
 * @author AF
 * @date 2021/5/14 11:45
 */
public class ResponseUtils {

    /**
     * 返回一个默认成功响应
     * @return
     */
    public static ResponseVO<Object> success() {
        return success(null);
    }

    /**
     * 带有信息的成功响应
     * @param data 数据信息
     * @return
     */
    public static ResponseVO<Object> success(Object data) {
        return new ResponseVO<>(SuccessCode.SUCCESS_CODE.getCode(), SuccessCode.SUCCESS_CODE.getMsg(), data);
    }

    /**
     * 返回没有数据的错误响应
     * @param errorCode
     * @return
     */
    public static ResponseVO<Object> error(ErrorCode errorCode) {
        return error(errorCode, null);
    }

    /**
     * 返回错误响应
     * @param errorCode 错误码
     * @param data 数据信息
     * @return
     */
    public static ResponseVO<Object> error(ErrorCode errorCode, Object data) {
        return new ResponseVO<>(errorCode.getCode(), errorCode.getMsg(), data);
    }
}
