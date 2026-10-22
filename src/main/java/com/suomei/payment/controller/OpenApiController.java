package com.suomei.payment.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.suomei.payment.api.BaseReqDto;
import com.suomei.payment.api.req.*;
import com.suomei.payment.common.CheckReqParam;
import com.suomei.payment.common.Result;
import com.suomei.payment.common.config.PartnerAlipayConfigs;
import com.suomei.payment.common.exception.ServiceErrorCode;
import com.suomei.payment.common.exception.ServiceException;
import com.suomei.payment.common.utils.ResultUtils;
import com.suomei.payment.common.utils.TraceLogUtils;
import com.suomei.payment.service.TransManage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Slf4j
@RequestMapping("openApi")
public class OpenApiController {

    private final Gson gson = new Gson();

    @Resource
    private TransManage transManage;

    @Resource
    private CheckReqParam checkReqParam;


    /**
     * 支付宝商家账户余额查询
     * @param reqDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryAccountInfo",method = RequestMethod.POST)
    public Result queryAccountInfo(@Validated @RequestBody BaseReqDto reqDto){
        TraceLogUtils.beginTrace();
        log.info("queryAccountInfo 支付宝商家账户余额查询 reqDto = {}",gson.toJson(reqDto));
        try {
            PartnerAlipayConfigs partnerAlipayConfigs = checkReqParam.checkPartnerReqParam(reqDto);
            JSONObject respData = transManage.queryAccountInfo(partnerAlipayConfigs);
            return ResultUtils.toSuccess(respData, checkReqParam.sysSign(respData));
        } catch (ServiceException e) {
            log.error("支付宝商家账户余额查询 ServiceException ：{}"+e.getDetailMessage(),e);
            TraceLogUtils.endTrace();
            return ResultUtils.toFault(e.getCode(),e.getMessage());
        } catch (Exception e) {
            log.error("支付宝商家账户余额查询 Exception：", e);
            TraceLogUtils.endTrace();
            return ResultUtils.toFault(ServiceErrorCode.FAIL);
        }
    }

    /**
     * 商家单笔转账
     * @param reqDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/uniTransfer",method = RequestMethod.POST)
    public Result uniTransfer(@Validated @RequestBody AlipayUniTransferReqDto reqDto){
        TraceLogUtils.beginTrace();
        log.info("uniTransfer 商家单笔转账 reqDto = {}",gson.toJson(reqDto));
        try {
            PartnerAlipayConfigs partnerAlipayConfigs = checkReqParam.checkPartnerReqParam(reqDto);
            JSONObject respData = transManage.uniTransfer(reqDto,partnerAlipayConfigs);
            return ResultUtils.toSuccess(respData, checkReqParam.sysSign(respData));
        } catch (ServiceException e) {
            log.error("商家单笔转账 ServiceException ：{}"+e.getDetailMessage(),e);
            TraceLogUtils.endTrace();
            return ResultUtils.toFault(e.getCode(),e.getMessage());
        } catch (Exception e) {
            log.error("商家单笔转账 Exception：", e);
            TraceLogUtils.endTrace();
            return ResultUtils.toFault(ServiceErrorCode.FAIL);
        }
    }

    /**
     * 转账订单查询
     * @param reqDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryTransferInfo",method = RequestMethod.POST)
    public Result queryTransferInfo(@Validated @RequestBody AlipayQueryTransferInfoReqDto reqDto){
        TraceLogUtils.beginTrace();
        log.info("queryTransferInfo 转账订单查询 reqDto = {}",gson.toJson(reqDto));
        try {
            PartnerAlipayConfigs partnerAlipayConfigs = checkReqParam.checkPartnerReqParam(reqDto);
            JSONObject respData = transManage.queryTransferInfo(reqDto,partnerAlipayConfigs);
            return ResultUtils.toSuccess(respData, checkReqParam.sysSign(respData));
        } catch (ServiceException e) {
            log.error("转账订单查询 ServiceException ：{}"+e.getDetailMessage(),e);
            TraceLogUtils.endTrace();
            return ResultUtils.toFault(e.getCode(),e.getMessage());
        } catch (Exception e) {
            log.error("转账订单查询 Exception：", e);
            TraceLogUtils.endTrace();
            return ResultUtils.toFault(ServiceErrorCode.FAIL);
        }
    }

    /**
     * 申请电子回单
     * @param reqDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/applyEreceipt",method = RequestMethod.POST)
    public Result applyEreceipt(@Validated @RequestBody AlipayApplyEreceiptReqDto reqDto){
        TraceLogUtils.beginTrace();
        log.info("applyEreceipt 申请电子回单 reqDto = {}",gson.toJson(reqDto));
        try {
            PartnerAlipayConfigs partnerAlipayConfigs = checkReqParam.checkPartnerReqParam(reqDto);
            JSONObject respData = transManage.applyEreceipt(reqDto,partnerAlipayConfigs);
            return ResultUtils.toSuccess(respData, checkReqParam.sysSign(respData));
        } catch (ServiceException e) {
            log.error("申请电子回单 ServiceException ：{}"+e.getDetailMessage(),e);
            TraceLogUtils.endTrace();
            return ResultUtils.toFault(e.getCode(),e.getMessage());
        } catch (Exception e) {
            log.error("申请电子回单 Exception：", e);
            TraceLogUtils.endTrace();
            return ResultUtils.toFault(ServiceErrorCode.FAIL);
        }
    }

    /**
     * 电子回单状态查询
     * @param reqDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryEreceipt",method = RequestMethod.POST)
    public Result queryEreceipt(@Validated @RequestBody AlipayQueryEreceiptReqDto reqDto){
        TraceLogUtils.beginTrace();
        log.info("queryEreceipt 电子回单状态查询 reqDto = {}",gson.toJson(reqDto));
        try {
            PartnerAlipayConfigs partnerAlipayConfigs = checkReqParam.checkPartnerReqParam(reqDto);
            JSONObject respData = transManage.queryEreceipt(reqDto,partnerAlipayConfigs);
            return ResultUtils.toSuccess(respData, checkReqParam.sysSign(respData));
        } catch (ServiceException e) {
            log.error("电子回单状态查询 ServiceException ：{}"+e.getDetailMessage(),e);
            TraceLogUtils.endTrace();
            return ResultUtils.toFault(e.getCode(),e.getMessage());
        } catch (Exception e) {
            log.error("电子回单状态查询 Exception：", e);
            TraceLogUtils.endTrace();
            return ResultUtils.toFault(ServiceErrorCode.FAIL);
        }
    }


    /**
     * 查询对账单下载地址
      * @param reqDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryBillDownloadUrl",method = RequestMethod.POST)
    public Result queryBillDownloadUrl(@Validated @RequestBody AlipayQueryBillDownloadUrlReqDto reqDto){
        TraceLogUtils.beginTrace();
        log.info("queryBillDownloadUrl 查询对账单下载地址 reqDto = {}",gson.toJson(reqDto));
        try {
            PartnerAlipayConfigs partnerAlipayConfigs = checkReqParam.checkPartnerReqParam(reqDto);
            JSONObject respData = transManage.queryBillDownloadUrl(reqDto,partnerAlipayConfigs);
            return ResultUtils.toSuccess(respData, checkReqParam.sysSign(respData));
        } catch (ServiceException e) {
            log.error("查询对账单下载地址 ServiceException ：{}"+e.getDetailMessage(),e);
            TraceLogUtils.endTrace();
            return ResultUtils.toFault(e.getCode(),e.getMessage());
        } catch (Exception e) {
            log.error("查询对账单下载地址 Exception：", e);
            TraceLogUtils.endTrace();
            return ResultUtils.toFault(ServiceErrorCode.FAIL);
        }
    }

}
