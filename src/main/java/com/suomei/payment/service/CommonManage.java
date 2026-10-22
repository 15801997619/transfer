package com.suomei.payment.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.response.*;
import com.suomei.payment.api.partner.req.NotifyTransferOrderChangedReqDto;
import com.suomei.payment.api.resp.*;
import com.suomei.payment.common.CheckReqParam;
import com.suomei.payment.common.Result;
import com.suomei.payment.common.config.PartnerAlipayConfigs;
import com.suomei.payment.common.constant.ConstantParams;
import com.suomei.payment.common.enums.ExternalApiEnum;
import com.suomei.payment.common.exception.ServiceErrorCode;
import com.suomei.payment.common.exception.ServiceException;
import com.suomei.payment.common.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
@Slf4j
public class CommonManage {

    @Resource
    private CheckReqParam checkReqParam;


    public JSONObject convert2PartnerResp(ExternalApiEnum apiEnum, Result result){
        String returnCode = result.getCode();
        String returnMsg = result.getMessage();
        if(returnCode.equals(ConstantParams.SUCCESS)){
            if(apiEnum.equals(ExternalApiEnum.ALIPAY_QUERY_ACCOUNT_INFO)){
                AlipayFundAccountQueryResponse channelResp = JSONObject.toJavaObject((JSON) JSON.toJSON(result.getRespObj()), AlipayFundAccountQueryResponse.class);
                AlipayFundAccountQueryRespDto respDto = new AlipayFundAccountQueryRespDto();
                respDto.setAvailableAmount(channelResp.getAvailableAmount());
                return (JSONObject) JSON.toJSON(respDto);
            }else if(apiEnum.equals(ExternalApiEnum.ALIPAY_UNI_TRANSFER)){
                AlipayFundTransUniTransferResponse channelResp = JSONObject.toJavaObject((JSON) JSON.toJSON(result.getRespObj()), AlipayFundTransUniTransferResponse.class);
                AlipayUniTransferRespDto respDto = new AlipayUniTransferRespDto();
                BeanUtils.copyProperties(channelResp, respDto);
                return (JSONObject) JSON.toJSON(respDto);
            }else if(apiEnum.equals(ExternalApiEnum.ALIPAY_QUERY_TRANSFER_INFO)){
                AlipayFundTransCommonQueryResponse channelResp = JSONObject.toJavaObject((JSON) JSON.toJSON(result.getRespObj()), AlipayFundTransCommonQueryResponse.class);
                AlipayQueryTransferInfoRespDto respDto = new AlipayQueryTransferInfoRespDto();
                BeanUtils.copyProperties(channelResp, respDto);
                return (JSONObject) JSON.toJSON(respDto);
            }else if(apiEnum.equals(ExternalApiEnum.ALIPAY_APPLY_ERECEIPT)){
                AlipayDataBillEreceiptApplyResponse channelResp = JSONObject.toJavaObject((JSON) JSON.toJSON(result.getRespObj()), AlipayDataBillEreceiptApplyResponse.class);
                AlipayApplyEreceiptRespDto respDto = new AlipayApplyEreceiptRespDto();
                BeanUtils.copyProperties(channelResp, respDto);
                return (JSONObject) JSON.toJSON(respDto);
            }else if(apiEnum.equals(ExternalApiEnum.ALIPAY_QUERY_ERECEIPT)){
                AlipayDataBillEreceiptQueryResponse channelResp = JSONObject.toJavaObject((JSON) JSON.toJSON(result.getRespObj()), AlipayDataBillEreceiptQueryResponse.class);
                AlipayQueryEreceiptRespDto respDto = new AlipayQueryEreceiptRespDto();
                BeanUtils.copyProperties(channelResp, respDto);
                return (JSONObject) JSON.toJSON(respDto);
            }else if(apiEnum.equals(ExternalApiEnum.ALIPAY_QUERY_BILL_DOWNLOAD_URL)){
                AlipayDataDataserviceBillDownloadurlQueryResponse channelResp = JSONObject.toJavaObject((JSON) JSON.toJSON(result.getRespObj()), AlipayDataDataserviceBillDownloadurlQueryResponse.class);
                AlipayQueryBillDownloadUrlRespDto respDto = new AlipayQueryBillDownloadUrlRespDto();
                BeanUtils.copyProperties(channelResp, respDto);
                return (JSONObject) JSON.toJSON(respDto);
            }else{
                log.error("未匹配到API类型，apiDesc={}",apiEnum.getDesc());
                throw new ServiceException(ServiceErrorCode.SYSTEM_ERROR.getCode(),returnCode+returnMsg);
            }
        }else{
            throw new ServiceException(ServiceErrorCode.EXT_API_REQUEST_FAIL.getCode(),returnCode+returnMsg);
        }
    }

    /**
     * 通知合作方
     * @param params
     * @return
     */
    public String notifyPartner(Map<String, String> params) throws Exception {
        String aliAppId = params.get("app_id");
        String signType = StringUtils.defaultIfBlank(params.get("sign_type"), ConstantParams.SIGN_TYPE2);
        PartnerAlipayConfigs partnerAlipayConfigs = checkReqParam.getConfigParamsByAliAppid(aliAppId);
        //支付宝公钥证书本地路径
        String alipayPublicCertPath = partnerAlipayConfigs.getAlipayAccount().getAlipayPublicCertPath();
        //验签 params: 支付宝回调的所有参数（包含 sign，SDK 内部会自动处理）
        if(!AlipaySignature.certVerifyV1(params, alipayPublicCertPath, ConstantParams.CHARSET,signType)){
            log.error("支付宝回调签名失败，map = {}",params);
            throw new ServiceException(ServiceErrorCode.CALL_BACK_SIGN_ERROR);
        }
        String bizContentJsonStr = params.get("biz_content");
        if(StringUtils.isNotBlank(bizContentJsonStr)){
            JSONObject bizContentJson = JSONObject.parseObject(bizContentJsonStr);
            //组装通知内容
            NotifyTransferOrderChangedReqDto notifyDto = new NotifyTransferOrderChangedReqDto();
//        notifyDto.setOriginInterface(StringUtils.defaultIfBlank(bizContentJson.getString("origin_interface"), null));
            notifyDto.setOutBizNo(StringUtils.defaultIfBlank(bizContentJson.getString("out_biz_no"), null));
            notifyDto.setProductCode(StringUtils.defaultIfBlank(bizContentJson.getString("product_code"), null));
            notifyDto.setBizScene(StringUtils.defaultIfBlank(bizContentJson.getString("biz_scene"), null));
            notifyDto.setOrderId(StringUtils.defaultIfBlank(bizContentJson.getString("order_id"), null));
            notifyDto.setStatus(StringUtils.defaultIfBlank(bizContentJson.getString("status"), null));
            notifyDto.setActionType(StringUtils.defaultIfBlank(bizContentJson.getString("action_type"), null));
            notifyDto.setTransAmount(StringUtils.defaultIfBlank(bizContentJson.getString("trans_amount"), null));
//        notifyDto.setSettleSerialNo(StringUtils.defaultIfBlank(bizContentJson.getString("settle_serial_no"), null));
            notifyDto.setReceiverUserId(StringUtils.defaultIfBlank(bizContentJson.getString("receiver_user_id"), null));
            notifyDto.setReceiverOpenId(StringUtils.defaultIfBlank(bizContentJson.getString("receiver_open_id"), null));
            notifyDto.setPayFundOrderId(StringUtils.defaultIfBlank(bizContentJson.getString("pay_fund_order_id"), null));
            notifyDto.setPayDate(StringUtils.defaultIfBlank(bizContentJson.getString("pay_date"), null));
            notifyDto.setRefundDate(StringUtils.defaultIfBlank(bizContentJson.getString("refund_date"), null));
            notifyDto.setVersion(ConstantParams.VERSION_1);
            notifyDto.setPlatformAppId(partnerAlipayConfigs.getAlipayAccount().getPartnerAppId());
            notifyDto.setAlipayAppId(partnerAlipayConfigs.getAlipayAccount().getAppId());
            notifyDto.setSignType(ConstantParams.SIGN_TYPE);
            //通知合作方
            return notifyMer((JSONObject) JSON.toJSON(notifyDto),partnerAlipayConfigs.getPartner().getNotifyUrl());
        }
        return ConstantParams.channel_fail_str;
    }

    private String notifyMer(JSONObject notifyJson,String notifyUrl) throws Exception {
        //对后台通知交互时，如果平台收到的应答不是JSON字符串{"code":"0000","msg":"成功"}，平台认为通知失败
        String partnerSign = checkReqParam.sysSign((JSONObject) JSON.toJSON(notifyJson));
        notifyJson.put("sign",partnerSign);
        log.info("notifyPartner notifyUrl = {},reqData = {}",notifyUrl,notifyJson.toJSONString());
        String respDataStr = HttpUtils.send(notifyUrl,notifyJson.toJSONString());
        log.info("notifyPartner resp, respData = {}",respDataStr);
        if(StringUtils.isBlank(respDataStr)){
            log.error("notifyPartner resp null");
            throw new RuntimeException("notifyPartner resp null");
        }
        JSONObject resultObj = JSONObject.parseObject(respDataStr);
        String code = resultObj.getString("code");
        String msg = resultObj.getString("msg");
        if(StringUtils.isNotBlank(code) && StringUtils.isNotBlank(msg)
                && code.equals(ServiceErrorCode.CHANNEL_NOTIFY_SUCCESS.getCode())
                && msg.equals(ServiceErrorCode.CHANNEL_NOTIFY_SUCCESS.getMessage())){
            return ConstantParams.channel_success_str;
        }
        return ConstantParams.channel_fail_str;
    }
}
