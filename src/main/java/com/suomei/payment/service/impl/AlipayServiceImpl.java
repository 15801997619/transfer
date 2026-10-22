package com.suomei.payment.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.*;
import com.alipay.api.domain.*;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import com.suomei.payment.api.req.*;
import com.suomei.payment.common.Result;
import com.suomei.payment.common.config.AlipayConfigProperties;
import com.suomei.payment.common.config.PartnerAlipayConfigs;
import com.suomei.payment.common.constant.ConstantParams;
import com.suomei.payment.common.exception.ServiceErrorCode;
import com.suomei.payment.common.exception.ServiceException;
import com.suomei.payment.common.utils.ResultUtils;
import com.suomei.payment.service.AlipayService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AlipayServiceImpl implements AlipayService {


    public AlipayClient initAlipayClient(AlipayConfigProperties.AlipayAccount alipayAccount) throws AlipayApiException {
        AlipayConfig alipayConfig = new AlipayConfig();
        // TODO 沙箱环境
        if(alipayAccount.getAppId().equals("9021000169679124")){
            alipayConfig.setServerUrl("https://openapi-sandbox.dl.alipaydev.com/gateway.do");
        }
        //设置应用APPID
        alipayConfig.setAppId(alipayAccount.getAppId());
        //设置应用私钥
        alipayConfig.setPrivateKey(alipayAccount.getAppPriKey());
        //设置应用公钥证书路径
        alipayConfig.setAppCertPath(alipayAccount.getAppCertPath());
        //设置支付宝公钥证书路径
        alipayConfig.setAlipayPublicCertPath(alipayAccount.getAlipayPublicCertPath());
        //设置支付宝根证书路径
        alipayConfig.setRootCertPath(alipayAccount.getRootCertPath());
        return new DefaultAlipayClient(alipayConfig);
    }


    @Override
    public Result queryAccountInfo(PartnerAlipayConfigs partnerAlipayConfigs) throws Exception {
        AlipayConfigProperties.AlipayAccount alipayAccount = partnerAlipayConfigs.getAlipayAccount();
        // 构造请求参数以调用接口
        AlipayFundAccountQueryRequest aliReq = new AlipayFundAccountQueryRequest();
        AlipayFundAccountQueryModel model = new AlipayFundAccountQueryModel();
        model.setAlipayUserId(alipayAccount.getUserId());
        // 设置查询的账号类型
        model.setAccountType("ACCTRANS_ACCOUNT");
        aliReq.setBizModel(model);
        AlipayClient alipayClient = initAlipayClient(alipayAccount);
        log.info("alipayClient请求通道商户账户余额查询请参:{}",JSONObject.toJSONString(aliReq));
        AlipayFundAccountQueryResponse response = alipayClient.certificateExecute(aliReq);
        log.info("alipayClient请求通道商户账户余额查询返参:{}",JSONObject.toJSONString(response));
        checkResp(response);
        return ResultUtils.toSuccess(response);
    }

    @Override
    public Result uniTransfer(AlipayUniTransferReqDto reqDto, PartnerAlipayConfigs partnerAlipayConfigs) throws Exception {
        AlipayConfigProperties.AlipayAccount alipayAccount = partnerAlipayConfigs.getAlipayAccount();
        AlipayFundTransUniTransferRequest aliReq = new AlipayFundTransUniTransferRequest();
        AlipayFundTransUniTransferModel model = new AlipayFundTransUniTransferModel();
        // 设置商家侧唯一订单号
        model.setOutBizNo(reqDto.getOutBizNo());
        // 设置订单总金额
        model.setTransAmount(reqDto.getTransAmount());
        // 设置描述特定的业务场景
        model.setBizScene(reqDto.getBizScene());
        // 设置业务产品码
        model.setProductCode(reqDto.getProductCode());
        // 设置转账业务的标题
        model.setOrderTitle(reqDto.getOrderTitle());
        // 设置收款方信息
        Participant payeeInfo = new Participant();
        payeeInfo.setIdentity(reqDto.getPayeeInfo().getIdentity());
        payeeInfo.setName(reqDto.getPayeeInfo().getName());
        payeeInfo.setIdentityType(reqDto.getPayeeInfo().getIdentityType());
        model.setPayeeInfo(payeeInfo);
        // 设置业务备注
        model.setRemark(reqDto.getRemark());
        // 设置转账场景名称
        model.setTransferSceneName(reqDto.getTransferSceneName());
        // 设置转账场景上报信息
        List<TransferSceneReportInfo> transferSceneReportInfos = new ArrayList<TransferSceneReportInfo>();
        TransferSceneReportInfo transferSceneReportInfos0 = new TransferSceneReportInfo();
        transferSceneReportInfos0.setInfoType(reqDto.getTransferSceneReportInfos().get(0).getInfoType());
        transferSceneReportInfos0.setInfoContent(reqDto.getTransferSceneReportInfos().get(0).getInfoContent());
        transferSceneReportInfos.add(transferSceneReportInfos0);
        model.setTransferSceneReportInfos(transferSceneReportInfos);
        // 设置转账业务请求的扩展参数
        model.setBusinessParams(reqDto.getBusinessParams());
        aliReq.setBizModel(model);
        AlipayClient alipayClient = initAlipayClient(alipayAccount);
        log.info("alipayClient请求通道商家单笔转账请参:{}",JSONObject.toJSONString(aliReq));
        AlipayFundTransUniTransferResponse response = alipayClient.certificateExecute(aliReq);
        log.info("alipayClient请求通道商家单笔转账返参:{}",JSONObject.toJSONString(response));
        checkResp(response);
        return ResultUtils.toSuccess(response);
    }

    @Override
    public Result queryTransferInfo(AlipayQueryTransferInfoReqDto reqDto, PartnerAlipayConfigs partnerAlipayConfigs) throws Exception {
        AlipayConfigProperties.AlipayAccount alipayAccount = partnerAlipayConfigs.getAlipayAccount();
        // 构造请求参数以调用接口
        AlipayFundTransCommonQueryRequest aliReq = new AlipayFundTransCommonQueryRequest();
        AlipayFundTransCommonQueryModel model = new AlipayFundTransCommonQueryModel();
        // 设置销售产品码
        model.setProductCode(reqDto.getProductCode());
        // 设置描述特定的业务场景
        model.setBizScene(reqDto.getBizScene());
        // 设置商户转账唯一订单号
        model.setOutBizNo(reqDto.getOutBizNo());
        // 设置支付宝转账单据号
        model.setOrderId(reqDto.getOrderId());
        // 设置支付宝支付资金流水号
        model.setPayFundOrderId(reqDto.getPayFundOrderId());
        aliReq.setBizModel(model);
        AlipayClient alipayClient = initAlipayClient(alipayAccount);
        log.info("alipayClient请求通道转账订单查询请参:{}",JSONObject.toJSONString(aliReq));
        AlipayFundTransCommonQueryResponse response = alipayClient.certificateExecute(aliReq);
        log.info("alipayClient请求通道转账订单查询返参:{}",JSONObject.toJSONString(response));
        checkResp(response);
        return ResultUtils.toSuccess(response);
    }

    @Override
    public Result applyEreceipt(AlipayApplyEreceiptReqDto reqDto, PartnerAlipayConfigs partnerAlipayConfigs) throws Exception {
        AlipayConfigProperties.AlipayAccount alipayAccount = partnerAlipayConfigs.getAlipayAccount();
        // 构造请求参数以调用接口
        AlipayDataBillEreceiptApplyRequest aliReq = new AlipayDataBillEreceiptApplyRequest();
        AlipayDataBillEreceiptApplyModel model = new AlipayDataBillEreceiptApplyModel();
        model.setKey(reqDto.getKey());
        model.setType(reqDto.getType());
        aliReq.setBizModel(model);
        AlipayClient alipayClient = initAlipayClient(alipayAccount);
        log.info("alipayClient请求通道申请电子回单请参:{}",JSONObject.toJSONString(aliReq));
        AlipayDataBillEreceiptApplyResponse response = alipayClient.certificateExecute(aliReq);
        log.info("alipayClient请求通道申请电子回单返参:{}",JSONObject.toJSONString(response));
        checkResp(response);
        return ResultUtils.toSuccess(response);
    }

    @Override
    public Result queryEreceipt(AlipayQueryEreceiptReqDto reqDto, PartnerAlipayConfigs partnerAlipayConfigs) throws Exception {
        AlipayConfigProperties.AlipayAccount alipayAccount = partnerAlipayConfigs.getAlipayAccount();
        AlipayClient alipayClient = initAlipayClient(alipayAccount);
        AlipayDataBillEreceiptQueryRequest aliReq = new AlipayDataBillEreceiptQueryRequest();
        AlipayDataBillEreceiptQueryModel model = new AlipayDataBillEreceiptQueryModel();
        model.setFileId(reqDto.getFileId());
        aliReq.setBizModel(model);
        log.info("alipayClient请求通道查询电子回单状态请参:{}",JSONObject.toJSONString(aliReq));
        AlipayDataBillEreceiptQueryResponse response = alipayClient.certificateExecute(aliReq);
        log.info("alipayClient请求通道查询电子回单状态返参:{}",JSONObject.toJSONString(response));
        checkResp(response);
        return ResultUtils.toSuccess(response);
    }

    @Override
    public Result queryBillDownloadUrl(AlipayQueryBillDownloadUrlReqDto reqDto, PartnerAlipayConfigs partnerAlipayConfigs) throws Exception {
        AlipayConfigProperties.AlipayAccount alipayAccount = partnerAlipayConfigs.getAlipayAccount();
        AlipayClient alipayClient = initAlipayClient(alipayAccount);
        // 构建请求对象
        AlipayDataDataserviceBillDownloadurlQueryRequest aliReq = new AlipayDataDataserviceBillDownloadurlQueryRequest();
        // 设置业务参数
        AlipayDataDataserviceBillDownloadurlQueryModel model = new AlipayDataDataserviceBillDownloadurlQueryModel();
        model.setBillType(reqDto.getBillType());
        model.setBillDate(reqDto.getBillDate());
        model.setSecure(ConstantParams.SECURE);
//        model.setSmid(reqDto.getSmid());
        aliReq.setBizModel(model);
        // 执行请求
        log.info("alipayClient请求通道查询对账单下载地址请参:{}",JSONObject.toJSONString(aliReq));
        AlipayDataDataserviceBillDownloadurlQueryResponse response = alipayClient.certificateExecute(aliReq);
        log.info("alipayClient请求通道查询对账单下载地址返参:{}",JSONObject.toJSONString(response));
        checkResp(response);
        return ResultUtils.toSuccess(response);
    }


    public void checkResp(AlipayResponse response){
        if(ObjectUtils.isEmpty(response)){
            log.error("Ali通道侧返回数据为空");
            throw new ServiceException(ServiceErrorCode.THIRD_RETURN_DATA_IS_NULL);
        }
        String msg = StringUtils.isNotBlank(response.getMsg())?response.getMsg(): response.getSubMsg();
        String code = StringUtils.isNotBlank(response.getCode())?response.getCode(): response.getSubCode();
        if(!response.isSuccess()){
            msg = msg.concat(response.getSubMsg());
            log.error("Ali通道侧返回非成功,code={},msg={}",code,msg);
            throw new ServiceException(ServiceErrorCode.EXT_API_REQUEST_FAIL.getCode(),code.concat("#").concat(msg));
        }
        log.info("obj = {} ",JSON.toJSON(response).toString());
    }

}
