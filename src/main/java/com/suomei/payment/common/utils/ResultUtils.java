package com.suomei.payment.common.utils;


import com.suomei.payment.common.Result;
import com.suomei.payment.common.constant.ConstantParams;
import com.suomei.payment.common.exception.ServiceErrorCode;

/**
 * 前置反馈前端消息工具类
 *
 */
public class ResultUtils {

    public static Result toSuccess() {
        return new Result(ConstantParams.SUCCESS, "成功", null);
    }

    public static Result toSuccess(String message) {
        return new Result(ConstantParams.SUCCESS, message, null);
    }
    public static Result toChannelSuccess() {
        return new Result(ServiceErrorCode.CHANNEL_NOTIFY_SUCCESS.getCode(), ServiceErrorCode.CHANNEL_NOTIFY_SUCCESS.getMessage(), null);
    }

//    public static Result toSuccess(String message, Object data) {
//        return new Result(ConstantParams.SUCCESS, message, data);
//    }

    public static Result toSuccess(Object data) {
        return new Result(ConstantParams.SUCCESS, "成功", data);
    }
    public static Result toSuccess(Object data,String sign) {
        return new Result(ConstantParams.SUCCESS, "成功", data,sign);
    }
    public static Result toFault(String code, String errInfo) {
        return new Result(code, errInfo, null);
    }

    public static Result toFault(String code, String errInfo, Object data) {
        return new Result(code, errInfo, data);
    }

    public static Result toFault(ServiceErrorCode err) {
        return new Result(err.getCode(), err.getMessage(), null);
    }

}
