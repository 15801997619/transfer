package com.suomei.payment.common;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.suomei.payment.common.config.AccessPartnerProperties;
import com.suomei.payment.common.config.AlipayConfigProperties;
import com.suomei.payment.common.config.PartnerAlipayConfigs;
import com.suomei.payment.common.constant.ConstantParams;
import com.suomei.payment.common.exception.ServiceErrorCode;
import com.suomei.payment.common.exception.ServiceException;
import com.suomei.payment.common.utils.ChannelSignUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
@RefreshScope
public class CheckReqParam {

    /**
     * 商家支付宝用户ID
     */
    @Resource
    private AlipayConfigProperties alipayConfigs;

    /**
     * 合作伙伴配置项
     */
    @Resource
    private AccessPartnerProperties partnerProperties;

    /** 系统签名私钥 test */
    @Value("${resp.mer.rsa.sign.priKey}")
    private String sysMerSignPriKey;

    /** 系统签名私钥 prod */
    @Value("${resp.mer.pr.rsa.sign.priKey}")
    private String sysMerPrSignPriKey;

    public PartnerAlipayConfigs checkPartnerReqParam(Object req) throws Exception {
        JSONObject reqJson = (JSONObject) JSON.toJSON(req);
        /**
         * 1.商户配置校验
         * 2.支付宝商家参数校验
         * 3.签名校验
         */
        //平台唯一凭证
        String platformAppId = reqJson.getString("platformAppId");
        //支付宝商家AppID
        String alipayAppId = reqJson.getString("alipayAppId");
        AccessPartnerProperties.Partner partner = partnerProperties.getPartnerByAppId(platformAppId);
        if(null == partner){
            log.error("partner req params error 请参有误，platformAppId = {}",platformAppId);
            throw new ServiceException(ServiceErrorCode.PARAMETER_CHECK_FAIL);
        }
        AlipayConfigProperties.AlipayAccount alipayAccount = alipayConfigs.getConfigByAppId(alipayAppId);
        if(null == alipayAccount){
            log.error("partner req params error 请参有误，alipayAppId = {}",alipayAppId);
            throw new ServiceException(ServiceErrorCode.PARAMETER_CHECK_FAIL);
        }
        String partnerPubKey = partner.getMerPubKey();
        boolean verifyResult = ChannelSignUtils.partnerReqDataVerify(reqJson,partnerPubKey);
        if(!verifyResult){
            log.error("partnerReqDataVerify fail 验签失败，verifyResult = {}",verifyResult);
            throw new ServiceException(ServiceErrorCode.PARTNER_VERIFY_SIGN_FAIL);
        }
        return new PartnerAlipayConfigs(platformAppId,partner,alipayAppId,alipayAccount);
    }

    /**
     * 根据阿里AppID反查对应的合作方信息
     * @param aliAppId
     * @return
     * @throws Exception
     */
    public PartnerAlipayConfigs getConfigParamsByAliAppid(String aliAppId) {
        AlipayConfigProperties.AlipayAccount alipayAccount = alipayConfigs.getConfigByAppId(aliAppId);
        if(null == alipayAccount){
            log.error("aliCallBack req params error 请参有误，alipayAppId = {}",aliAppId);
            throw new ServiceException(ServiceErrorCode.PARAMETER_CHECK_FAIL);
        }
        //平台唯一凭证
        String platformAppId = alipayAccount.getPartnerAppId();
        AccessPartnerProperties.Partner partner = partnerProperties.getPartnerByAppId(platformAppId);
        if(null == partner){
            log.error("aliCallBack config error 参数配置有误，aliAppId={}，platformAppId = {}",aliAppId,platformAppId);
            throw new ServiceException(ServiceErrorCode.PARAMETER_CHECK_FAIL);
        }
        return new PartnerAlipayConfigs(platformAppId,partner,aliAppId,alipayAccount);
    }

    /**
     * 返参签名
     * @param respData
     * @return
     * @throws Exception
     */
    public String sysSign(JSONObject respData,String platformAppId) throws Exception {
        String sysPriKey = sysMerSignPriKey;
        if(StringUtils.isNotBlank(platformAppId) && platformAppId.startsWith(ConstantParams.PROD_PARTNER_APPID_PREFIX)){
            sysPriKey = sysMerPrSignPriKey;
        }
        return ChannelSignUtils.sysSign(respData,sysPriKey);
    }

}
