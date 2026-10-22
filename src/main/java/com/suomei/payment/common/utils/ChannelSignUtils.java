package com.suomei.payment.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.suomei.payment.common.Result;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChannelSignUtils {


    /**
     * 合作方请求参数验签
     * @param reqJsonObj
     * @param signPubKey
     * @return
     * @throws Exception
     */
    public static boolean partnerReqDataVerify(JSONObject reqJsonObj, String signPubKey) throws Exception {
        String sign = reqJsonObj.getString("sign");
        reqJsonObj.remove("sign");
        JSONObject data = JsonUtil.removeNulls(reqJsonObj);
        log.info("partnerReqDataVerify 验签数据明文:data={}",data.toJSONString());
        boolean result = SignUtils.validateRsaSign(data.toJSONString(),sign,signPubKey);
        log.info("partnerReqDataVerify 验签结果:result={}",result);
        reqJsonObj.put("sign",sign);
        return result;
    }


    public static String reqDataSign(JSONObject reqJsonObj, String signPrivateKey) throws Exception {
        JSONObject data = JsonUtil.removeNulls(reqJsonObj);
        log.info("签名数据明文:data={}",data.toJSONString());
        String sign = SignUtils.sortAndRsaSign(data.toJSONString(), signPrivateKey);
        log.info("签名结果:sign={}",sign);
        reqJsonObj.put("sign",sign);
        return reqJsonObj.toJSONString();
    }

    public static boolean respDataVerify(JSONObject reqJsonObj, String signPubKey) throws Exception {
        String sign = reqJsonObj.getString("sign");
        reqJsonObj.remove("sign");
        JSONObject data = JsonUtil.removeNulls(reqJsonObj);
        log.info("验签数据明文:data={}",data.toJSONString());
        boolean result = SignUtils.validateRsaSign(data.toJSONString(),sign,signPubKey);
        log.info("验签结果:result={}",result);
        reqJsonObj.put("sign",sign);
        return result;
    }

    public static String sysSign(JSONObject reqJsonObj, String signPrivateKey) throws Exception {
        JSONObject data = JsonUtil.removeNulls(reqJsonObj);
        log.info("响应/通知商户数据明文:data={}",data.toJSONString());
        String sign = SignUtils.sortAndRsaSign(data.toJSONString(), signPrivateKey);
        log.info("响应/通知商户数据签名:sign={}",sign);
        return sign;
    }

    public static Result sysResultSign(JSONObject reqJsonObj, String signPrivateKey) throws Exception {
        Result result = ResultUtils.toSuccess(reqJsonObj);
        JSONObject resultJson = (JSONObject) JSONObject.toJSON(result);
        JSONObject data = JsonUtil.removeNulls(resultJson);
        log.info("响应/通知商户数据明文:data={}",data.toJSONString());
        String sign = SignUtils.sortAndRsaSign(data.toJSONString(), signPrivateKey);
        log.info("响应/通知商户数据签名:sign={}",sign);
        result.setSign(sign);
        return result;
    }

}
