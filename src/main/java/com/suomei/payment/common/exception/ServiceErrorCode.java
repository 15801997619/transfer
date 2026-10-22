package com.suomei.payment.common.exception;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * 前置错误码枚举
 *
 */
public enum ServiceErrorCode implements Serializable {

    SUCCESS("000000", "成功", "成功"),
    CHANNEL_NOTIFY_SUCCESS("0000", "成功", "成功"),
    FAIL("999999", "失败", "失败"),
    SYSTEM_ERROR("551001", "系统异常，请稍后重试", "系统异常"),
    PARAMETER_IS_NULL("551002", "参数不能为空", "参数不能为空"),
    PARAMETER_CHECK_FAIL("551002", "请求参数校验失败", "请求参数校验失败"),

    QUERY_RESULT_EMPTY("551003", "无数据", "无数据"),
    MER_CONFIG_IS_NULL("551003", "未查询到商户配置", "未查询到商户配置merConfig"),
    PARTNER_VERIFY_SIGN_FAIL("661002", "验签失败", "合作方请参验签失败"),

    EXT_API_REQUEST_FAIL("661001", "系统业务异常，请稍后重试", "调用外部系统接口异常"),
    THIRD_VERIFY_SIGN_FAIL("661002", "验签失败", "通道接口响应结果验签失败"),
    THIRD_RETURN_DATA_IS_NULL("661003", "请求失败", "通道返回数据为空"),
    CHANNEL_TYPE_UNKNOWN("661004", "通道类型错误", "未知的通道类型"),
    ORDER_CHECK_ERROR("661010", "商户订单和通道订单不能同时为空", "商户订单和通道订单不能同时为空"),

    CALL_BACK_SIGN_ERROR("661011", "支付宝交易回调验签失败", "支付宝交易回调验签失败"),

    CHANNEL_REQ_PARAM_ERROR("661012", "参数有误", "请求通道参数异常"),
    MER_REQ_ORDER_ID_REPEAT("661013", "商户订单号不能重复", "商户订单号不能重复"),





    ;


    /** 错误码 */
    private String code;
    /** 错误描述(对外统一提示) */
    private String message;
    /** 错误描述（内部详细说明）*/
    private String detailMessage;

    /**
     * @param code          错误码
     * @param message       错误信息
     * @param detailMessage 详细信息
     */
    ServiceErrorCode(String code, String message, String detailMessage) {
        this.code = code;
        this.message = message;
        this.detailMessage = detailMessage;
    }

    /**
     * @param code
     * @return
     */
    public static ServiceErrorCode getEnums(String code) {
        for (ServiceErrorCode enums : values()) {
            if (StringUtils.equalsIgnoreCase(code, enums.getCode())) {
                return enums;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetailMessage() {
        return detailMessage;
    }

    public void setDetailMessage(String detailMessage) {
        this.detailMessage = detailMessage;
    }
}
