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
public class AlipayQueryTransferInfoRespDto implements Serializable {

    private static final long serialVersionUID = 3483323426580984563L;

//    /**
//     *  资金流入网联交易流水号
//     * 【描述】金融机构发起签约类、支付类、差错类业务时，应为每笔业务分配唯一的交易流水号。31位交易流水号组成规则为：“8位日期”+“16位序列号”+“1位预留位”+“6位控制位”，其中： a）“8位日期”为系统当前日期，ISODate格式：“YYYYMMDD” b）“16位序列号”由金融机构生成，金融机构应确保该值在网联当日唯一 c）“1位预留位”由平台分配 d）“6位控制位”由金融机构通过平台获取 例如：2023052993044491260542090100400
//     * 【示例值】2023052993044491260542090100400
//     * 必选
//     */
//    private String inflowSettleSerialNo;

    /**
     * 【描述】支付宝转账单据号，查询失败不返回。
     * 【示例值】20190801110070000006380000250621
     * 必选
     */
    private String orderId;

    /**
     *  【描述】商户订单号
     * 【示例值】201808080001
     * 必选
     */
    private String outBizNo;

    /**
     * 【描述】转账单据状态。可能出现的状态如下： SUCCESS：转账成功； WAIT_PAY：等待支付； CLOSED：订单超时关闭； FAIL：失败（适用于"单笔转账到银行卡"）； DEALING：处理中（适用于"单笔转账到银行卡"）； REFUND：退票（适用于"单笔转账到银行卡"）； alipay.fund.trans.app.pay涉及的状态： WAIT_PAY、SUCCESS、CLOSED alipay.fund.trans.refund涉及的状态：SUCCESS alipay.fund.trans.uni.transfer涉及的状态：SUCCESS、FAIL、DEALING、REFUND
     * 【示例值】SUCCESS
     * 必选
     */
    private String status;

//    /**
//     *  清算机构流水号
//     * 【描述】金融机构发起签约类、支付类、差错类业务时，应为每笔业务分配唯一的交易流水号。31位交易流水号组成规则为：“8位日期”+“16位序列号”+“1位预留位”+“6位控制位”，其中： a）“8位日期”为系统当前日期，ISODate格式：“YYYYMMDD” b）“16位序列号”由金融机构生成，金融机构应确保该值在网联当日唯一 c）“1位预留位”由平台分配 d）“6位控制位”由金融机构通过平台获取 例如：2023052993044491260542090100400
//     * 【示例值】2023052993044491260542090100400
//     * 必选
//     */
//    private String settleSerialNo;

    /**
     *  收款方支付宝账号uid
     * 【描述】收款方支付宝账号uid新商户建议使用receiver_open_id替代该字段。对于新商户，receiver_user_id字段未来计划逐步回收，存量商户可继续使用。如使用receiver_open_id，请确认 应用-开发配置-openid配置管理 已启用。无该配置项，可查看openid配置申请。
     * 【示例值】2088828045603300
     * 可选
     */
    private String receiverUserId;

    /**
     *  收款方支付宝账号openid
     * 【描述】收款方支付宝账号openid  详情可查看 openid简介
     * 【示例值】074a1CcTG1LelxKe4xQC0zgNdId0nxi95b5lsNpazWYoCo5
     * 可选
     */
    private String receiverOpenId;

    /**
     *  支付宝支付资金流水号
     * 【描述】支付宝支付资金流水号，转账失败不返回。
     * 【示例值】20190801110070001506380000251556
     * 特殊可选
     */
    private String payFundOrderId;

    /**
     *  转账金额，单位：元，保留小数点后两位
     * 【描述】付款金额，收银台场景下付款成功后的支付金额，订单状态为SUCCESS才返回，其他状态不返回。付款金额，单位为元，精确到小数点后两位。
     * 【示例值】32.00
     * 特殊可选
     */
    private String transAmount;

    /**
     *  支付时间
     * 【描述】支付时间，格式为yyyy-MM-dd HH:mm:ss，转账失败不返回。
     * 【示例值】2013-01-01 08:08:08
     * 特殊可选
     */
    private String payDate;

//    /**
//     *  预计到账时间
//     * 【描述】预计到账时间，转账到银行卡专用，格式为yyyy-MM-dd HH:mm:ss，转账受理失败不返回。 注意： 此参数为预计时间，可能与实际到账时间有较大误差，不能作为实际到账时间使用，仅供参考用途。
//     * 【示例值】2013-01-01 08:08:08
//     * 特殊可选
//     */
//    private String arrivalTimeEnd;
//
//    /**
//     *  预计收费金额
//     * 【描述】预计收费金额（元），转账到银行卡专用，数字格式，精确到小数点后2位，转账失败或转账受理失败不返回。
//     * 【示例值】0.02
//     * 特殊可选
//     */
//    private String orderFee;

    /**
     *  错误代码
     * 【描述】查询到的订单状态为FAIL失败或REFUND退票时，返回错误代码
     * 【示例值】PAYEE_CARD_INFO_ERROR
     * 特殊可选
     */
    private String errorCode;

    /**
     *  失败原因
     * 【描述】查询到的订单状态为FAIL失败或REFUND退票时，返回具体的原因。
     * 【示例值】收款方银行卡信息有误
     * 特殊可选
     */
    private String failReason;

}
