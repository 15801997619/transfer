package com.suomei.payment.controller;

import com.suomei.payment.common.constant.ConstantParams;
import com.suomei.payment.common.exception.ServiceException;
import com.suomei.payment.common.utils.TraceLogUtils;
import com.suomei.payment.service.CommonManage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("callback")
@Slf4j
public class CallbackController {

    @Resource
    private CommonManage commonManage;


    /**
     * 转账单据状态变更通知
     * @param request
     * @return
     */
    @RequestMapping(value = "/alipayTransfer",method = RequestMethod.POST)
    public String alipayTransfer(HttpServletRequest request){
        TraceLogUtils.beginTrace();
        Map<String, String> params = getParameterMap(request);
        log.info("########支付宝转账单据状态变更通知请求参数 {}",params);
        try {
            String respResult = commonManage.notifyPartner(params);
            log.info("########支付宝转账单据状态变更通知业务处理完成{}", respResult);
            TraceLogUtils.endTrace();
            return respResult;
        } catch (ServiceException e) {
            log.error("########支付宝转账单据状态变更通知业务处理ServiceException ：{}"+e.getDetailMessage(),e);
            TraceLogUtils.endTrace();
            return ConstantParams.channel_fail_str;
        } catch (Exception e) {
            log.error("########支付宝转账单据状态变更通知业务处理Exception：", e);
            TraceLogUtils.endTrace();
            return ConstantParams.channel_fail_str;
        }
    }

    public Map<String, String> getParameterMap(HttpServletRequest request) {
        Map<String, String[]> parameters = request.getParameterMap();
        return parameters.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> joinArrayElements(entry.getValue())));
    }
    private String joinArrayElements(String[] values) {
        return values != null ? String.join(", ", values) : null;
    }

}
