package com.suomei.payment.api.resp;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * 支付宝商家单笔转账返回参数
 */
@Data
@ToString
@EqualsAndHashCode
public class AlipayUniTransferRespDto implements Serializable {

    /**
     * 【描述】商户订单号
     * 【示例值】201808080001
     */
    private String outBizNo;

    /**
     * 【描述】支付宝转账订单号
     * 【示例值】20190801110070000006380000250621
     */
    private String orderId;

    /**
     * 【描述】支付宝支付资金流水号
     * 【示例值】20190801110070001506380000251556
     */
    private String payFundOrderId;

    /**
     * 转账单据状态。
     * 【描述】转账单据状态。 SUCCESS（该笔转账交易成功）：成功； FAIL：失败（具体失败原因请参见error_code以及fail_reason返回值）；
     * 【示例值】SUCCESS
     * 可选
     */
    private String status;

    /**
     * 【描述】订单支付时间，格式为yyyy-MM-dd HH:mm:ss
     * 【示例值】2019-08-21 00:00:00
     * 可选
     */
    private String transDate;

}
