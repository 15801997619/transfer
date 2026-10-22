package com.suomei.payment.common;


import com.suomei.payment.common.exception.ServiceErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 前置统一返回对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Result implements Serializable {

    private static final long serialVersionUID = 2871763874701896262L;

    private String code;

    private String message;

    private Object respObj;

    private String sign;

    public Result(String code, String message, Object respObj) {
        super();
        this.code = code;
        this.message = message;
        this.respObj = respObj;
    }

    public Result(String code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public Result(ServiceErrorCode code) {
        this.code = code.getCode();
        this.message=code.getMessage();
    }

}
