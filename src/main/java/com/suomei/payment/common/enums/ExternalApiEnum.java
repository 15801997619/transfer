package com.suomei.payment.common.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 对接三方系统API
 */
@Getter
@AllArgsConstructor
public enum ExternalApiEnum {
    /**
     * 支付宝接口服务
     * （支付宝使用SDK调用，相关枚举仅做业务处理使用）
     * */
    ALIPAY_QUERY_ACCOUNT_INFO("ALIPAY","alipay.fund.account.query", "1.0","支付宝资金账户资产查询接口","支付宝商家账户余额查询"),
    ALIPAY_UNI_TRANSFER("ALIPAY","alipay.fund.trans.uni.transfer","1.0", "单笔转账接口","商家单笔转账"),
    ALIPAY_QUERY_TRANSFER_INFO("ALIPAY","alipay.fund.trans.common.query", "1.0","转账业务单据查询接口","转账订单查询"),
    ALIPAY_APPLY_ERECEIPT("ALIPAY","alipay.data.bill.ereceipt.apply", "1.0","申请电子回单(incubating)","电子回单申请"),
    ALIPAY_QUERY_ERECEIPT("ALIPAY","alipay.data.bill.ereceipt.query", "1.0","查询电子回单状态(incubating)","电子回单状态查询"),
    ALIPAY_QUERY_BILL_DOWNLOAD_URL("ALIPAY","alipay.data.dataservice.bill.downloadurl.query", "1.0","查询对账单下载地址","查询对账单下载地址"),

    ;

    /** 系统 */
    private String sys;
    /** 接口请求路径*/
    private String url;
    /** 版本号 */
    private String version;
    /** 接口描述 */
    private String desc;
    /** 合作方API接口描述 */
    private String partnerApiDesc;

}
