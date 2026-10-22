package com.suomei.payment.service;

import com.alibaba.fastjson.JSONObject;
import com.suomei.payment.api.req.*;
import com.suomei.payment.common.Result;
import com.suomei.payment.common.config.PartnerAlipayConfigs;
import com.suomei.payment.common.enums.ExternalApiEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class TransManage {

    @Resource
    private AlipayService alipayService;

    @Autowired
    private CommonManage commonManage;

    /**
     * 商户账户余额查询
     * @param partnerAlipayConfigs
     * @return
     * @throws Exception
     */
    public JSONObject queryAccountInfo(PartnerAlipayConfigs partnerAlipayConfigs)throws Exception{
        Result result = alipayService.queryAccountInfo(partnerAlipayConfigs);
        return commonManage.convert2PartnerResp(ExternalApiEnum.ALIPAY_QUERY_ACCOUNT_INFO,result);
    }

    /**
     * 商家单笔转账
     * @param reqDto
     * @param partnerAlipayConfigs
     * @return
     * @throws Exception
     */
    public JSONObject uniTransfer(AlipayUniTransferReqDto reqDto, PartnerAlipayConfigs partnerAlipayConfigs)throws Exception{
        Result result = alipayService.uniTransfer(reqDto,partnerAlipayConfigs);
        return commonManage.convert2PartnerResp(ExternalApiEnum.ALIPAY_UNI_TRANSFER,result);
    }

    /**
     * 转账订单查询
     * @param reqDto
     * @param partnerAlipayConfigs
     * @return
     * @throws Exception
     */
    public JSONObject queryTransferInfo(AlipayQueryTransferInfoReqDto reqDto, PartnerAlipayConfigs partnerAlipayConfigs)throws Exception{
        Result result = alipayService.queryTransferInfo(reqDto,partnerAlipayConfigs);
        return commonManage.convert2PartnerResp(ExternalApiEnum.ALIPAY_QUERY_TRANSFER_INFO,result);
    }

    /**
     * 申请电子回单
     * @param reqDto
     * @param partnerAlipayConfigs
     * @return
     * @throws Exception
     */
    public JSONObject applyEreceipt(AlipayApplyEreceiptReqDto reqDto, PartnerAlipayConfigs partnerAlipayConfigs)throws Exception{
        Result result = alipayService.applyEreceipt(reqDto,partnerAlipayConfigs);
        return commonManage.convert2PartnerResp(ExternalApiEnum.ALIPAY_APPLY_ERECEIPT,result);
    }

    /**
     * 电子回单状态查询
     * @param reqDto
     * @param partnerAlipayConfigs
     * @return
     * @throws Exception
     */
    public JSONObject queryEreceipt(AlipayQueryEreceiptReqDto reqDto, PartnerAlipayConfigs partnerAlipayConfigs)throws Exception{
        Result result = alipayService.queryEreceipt(reqDto,partnerAlipayConfigs);
        return commonManage.convert2PartnerResp(ExternalApiEnum.ALIPAY_QUERY_ERECEIPT,result);
    }

    /**
     * 查询对账单下载地址
     * @param reqDto
     * @param partnerAlipayConfigs
     * @return
     * @throws Exception
     */
    public JSONObject queryBillDownloadUrl(AlipayQueryBillDownloadUrlReqDto reqDto, PartnerAlipayConfigs partnerAlipayConfigs)throws Exception{
        Result result = alipayService.queryBillDownloadUrl(reqDto,partnerAlipayConfigs);
        return commonManage.convert2PartnerResp(ExternalApiEnum.ALIPAY_QUERY_BILL_DOWNLOAD_URL,result);
    }

}
