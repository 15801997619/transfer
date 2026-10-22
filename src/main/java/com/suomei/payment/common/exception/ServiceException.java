package com.suomei.payment.common.exception;

import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;

public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 4956842514617972704L;

    private ServiceErrorCode errorCode;

    /** 错误码 */
    private String code;
    /** 错误描述(对外统一提示) */
    private String message;

    /** 错误描述（内部详细说明） */
    private String detailMessage;

    /**
     * @param message
     * @param cause
     */
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    /**
     * @param message
     */
    public ServiceException(String message) {
        super(message);
        this.message = message;
    }

    /**
     * @param errorCode
     */
    public ServiceException(ServiceErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    /**
     * @param errorCode
     * @param t
     */
    public ServiceException(ServiceErrorCode errorCode, Throwable t) {
        super(errorCode.getMessage(), t);
        this.errorCode = errorCode;
    }

    /**
     * @param errorCode
     * @param args
     */
    public ServiceException(ServiceErrorCode errorCode, Object... args) {
        super((new MessageFormat(errorCode.getMessage())).format(args));
        this.errorCode = errorCode;
    }

    /**
     * @param errorCode
     * @param args
     */
    public ServiceException(ServiceErrorCode errorCode, Throwable t, Object... args) {
        super((new MessageFormat(errorCode.getMessage())).format(args), t);
        this.errorCode = errorCode;
    }

    /**
     * @param code
     * @param message
     */
    public ServiceException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    /**
     * @param code
     * @param message
     */
    public ServiceException(String code, String message,String detailMessage) {
        super(message);
        this.code = code;
        this.message = message;
        this.detailMessage = detailMessage;
    }

    public String getCode() {
        if (errorCode != null) {
            return errorCode.getCode();
        } else if (StringUtils.isNotBlank(this.code)) {
            return this.code;
        }
        return ServiceErrorCode.SYSTEM_ERROR.getCode();
    }

    @Override
    public String getMessage() {
        if (errorCode != null) {
            return errorCode.getMessage();
        } else if (StringUtils.isNotBlank(this.message)) {
            return this.message;
        }
        return ServiceErrorCode.SYSTEM_ERROR.getMessage();
    }

    public String getDetailMessage() {
        if (errorCode != null) {
            return errorCode.getDetailMessage();
        } else if (StringUtils.isNotBlank(this.detailMessage)) {
            return this.detailMessage;
        }
        return ServiceErrorCode.SYSTEM_ERROR.getDetailMessage();
    }

}
