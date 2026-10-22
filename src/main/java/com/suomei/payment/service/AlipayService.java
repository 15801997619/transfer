package com.suomei.payment.service;

import com.suomei.payment.api.req.*;
import com.suomei.payment.common.Result;
import com.suomei.payment.common.config.PartnerAlipayConfigs;

public interface AlipayService {


    /**
     * 商户账户余额查询
     * @param partnerAlipayConfigs
     * @return
     * @throws Exception
     */
    Result queryAccountInfo(PartnerAlipayConfigs partnerAlipayConfigs) throws Exception;

    /**
     * 商家单笔转账
     * @param reqDto
     * @param partnerAlipayConfigs
     * @return
     * @throws Exception
     */
    Result uniTransfer(AlipayUniTransferReqDto reqDto, PartnerAlipayConfigs partnerAlipayConfigs) throws Exception;

    /**
     * 转账订单查询
     * @param reqDto
     * @param partnerAlipayConfigs
     * @return
     * @throws Exception
     */
    Result queryTransferInfo(AlipayQueryTransferInfoReqDto reqDto, PartnerAlipayConfigs partnerAlipayConfigs) throws Exception;

    /**
     * 申请电子回单
     * @param reqDto
     * @param partnerAlipayConfigs
     * @return
     * @throws Exception
     */
    Result applyEreceipt(AlipayApplyEreceiptReqDto reqDto, PartnerAlipayConfigs partnerAlipayConfigs) throws Exception;

    /**
     * 电子回单状态查询
     * @param reqDto
     * @param partnerAlipayConfigs
     * @return
     * @throws Exception
     */
    Result queryEreceipt(AlipayQueryEreceiptReqDto reqDto, PartnerAlipayConfigs partnerAlipayConfigs) throws Exception;

    /**
     * 查询对账单下载地址
     * @param reqDto
     * @param partnerAlipayConfigs
     * @return
     * @throws Exception
     */
    Result queryBillDownloadUrl(AlipayQueryBillDownloadUrlReqDto reqDto, PartnerAlipayConfigs partnerAlipayConfigs) throws Exception;

}
