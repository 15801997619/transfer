package com.suomei.payment.common.config;

import com.suomei.payment.common.Result;
import com.suomei.payment.common.constant.ConstantParams;
import com.suomei.payment.common.exception.ServiceErrorCode;
import com.suomei.payment.common.exception.ServiceException;
import com.suomei.payment.common.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ValidationException;

/**
 * 异常处理
 *
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(ServiceException.class)
    public Object baseExceptions(ServiceException e) {
        log.error("ServiceException 异常", e);
        return coverResult(e.getCode(), e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = {BindException.class, MethodArgumentNotValidException.class})
    public Object validationExceptions(Exception exception) {
        log.error("validation异常，{}", exception.getMessage());
        BindingResult bindResult = null;
        if (exception instanceof BindException) {
            bindResult = ((BindException) exception).getBindingResult();
        } else if (exception instanceof MethodArgumentNotValidException) {
            bindResult = ((MethodArgumentNotValidException) exception).getBindingResult();
        }
        String errCode = ServiceErrorCode.PARAMETER_CHECK_FAIL.getCode();
        String errMsg = ServiceErrorCode.PARAMETER_CHECK_FAIL.getMessage();

        if (bindResult != null && bindResult.hasErrors()) {
            errMsg = bindResult.getAllErrors().get(0).getDefaultMessage();
//            if (errMsg.contains("NumberFormatException")) {
//                log.error("validation异常，{}", exception.getMessage());
//            }
        }

        return coverResult(errCode, errMsg);
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Object handleExceptions(Exception e) {
        if (e instanceof ValidationException) {
            log.error("Exception系统异常{},{}", e.getMessage(),e.getStackTrace());
            return coverResult(ServiceErrorCode.SYSTEM_ERROR.getCode(), e.getMessage());
        }
        log.error("Exception系统异常", e);
        return coverResult(ConstantParams.DEF_BLANK_VAL, ConstantParams.DEF_BLANK_VAL);
    }

    private Result coverResult(String errCode, String errMsg) {
        if (StringUtils.isBlank(errCode)) {
            errCode = ServiceErrorCode.SYSTEM_ERROR.getCode();
        }
        if (StringUtils.isBlank(errMsg)) {
            errMsg = ServiceErrorCode.SYSTEM_ERROR.getMessage();
        }
        return ResultUtils.toFault(errCode, errMsg);
    }

}
