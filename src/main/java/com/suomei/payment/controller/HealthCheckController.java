package com.suomei.payment.controller;

import com.alibaba.fastjson.JSONObject;
import com.suomei.payment.common.exception.ServiceErrorCode;
import com.suomei.payment.common.utils.TraceLogUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RefreshScope
public class HealthCheckController {

    /** 系统签名私钥 */
    @Value("${resp.mer.rsa.sign.priKey}")
    private String sysMerSignPriKey;

    /** 系统签名验签公钥 */
    @Value("${resp.mer.rsa.sign.pubKey}")
    private String sysMerSignPubKey;


    @RequestMapping(value = "/healthCheck", method = RequestMethod.GET)
    public String healthCheck(){
        return "OK";
    }


//    @PostMapping("rsaSign")
//    @ResponseBody
//    public JSONObject rsaSign(@RequestBody JSONObject reqData) throws Exception {
//        TraceLogUtils.beginTrace();
//        log.info("rsaSign reqData={}",reqData.toJSONString());
//        String priKey = reqData.getString("priKey");
//        if(StringUtils.isBlank(priKey)){
//            priKey = sysMerSignPriKey;
//        }
//        reqData.remove("priKey");
//        String jsonDataStr = reqData.toJSONString();
//        log.info("加签明文:\n{}", jsonDataStr);
//        String sign = SignUtils.sortAndRsaSign(jsonDataStr, priKey);
//        reqData.put("sign",sign);
//        JSONObject jsonObject = JSONObject.parseObject(jsonDataStr);
//        jsonObject.put("sign", sign);
//        log.info("签名值:\n{}", sign);
//        log.info("接口请参:\n{}", jsonObject.toJSONString());
//        TraceLogUtils.endTrace();
//        return jsonObject;
//    }
//
//
//    @PostMapping("validateRsaSign")
//    @ResponseBody
//    public String validateRsaSign(@RequestBody JSONObject reqData) throws Exception {
//        TraceLogUtils.beginTrace();
//        log.info("validateRsaSign reqData={}",reqData.toJSONString());
//        String pubKey = reqData.getString("pubKey");
//        if(StringUtils.isBlank(pubKey)){
//            pubKey = sysMerSignPubKey;
//        }
//        String sign = reqData.getString("sign");
//        reqData.remove("pubKey");
//        reqData.remove("sign");
//        String jsonDataStr = reqData.toJSONString();
//        log.info("验签明文:\n{}", jsonDataStr);
//        log.info("签名值:\n{}", sign);
//        boolean result = SignUtils.validateRsaSign(jsonDataStr,sign,pubKey);
//        log.info("验签结果:\n{}", result);
//        TraceLogUtils.endTrace();
//        return result?"TRUE":"FALSE";
//    }

    @PostMapping("notifyMerTest")
    @ResponseBody
    public JSONObject notifyMerTest(@RequestBody JSONObject reqData){
        TraceLogUtils.beginTrace();
        log.info("notifyMerTest reqData={}",reqData.toJSONString());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", ServiceErrorCode.CHANNEL_NOTIFY_SUCCESS.getCode());
        jsonObject.put("msg",ServiceErrorCode.CHANNEL_NOTIFY_SUCCESS.getMessage());
        TraceLogUtils.endTrace();
        return jsonObject;
    }

}
