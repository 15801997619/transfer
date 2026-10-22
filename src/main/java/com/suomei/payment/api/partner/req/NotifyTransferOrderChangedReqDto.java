package com.suomei.payment.api.partner.req;


import com.suomei.payment.api.BaseReqDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * 转账单据状态变更通知(消息属性)请参实体类
 * ● 设置方式：需根据 订阅消息 指引，在应用开发设置的from平台订阅alipay.fund.trans.order.changed（资金单据状态变更通知接口） ，HTTP接入模式需额外设置应用网关作为通知接收地址。
 * ● 验签：商户系统接收到异步通知以后，必须通过验签（验证通知中的 sign 参数）来确保支付通知是由支付宝发送的。详细验签规则可查看 异步通知验签。
 * ● 支付宝开放平台消息服务接入，可查看 开放平台消息服务 文档。本消息接口同时支持 http(s)方式和支持 WebSocket 长连接，推荐使用 WebSocket 的长连接通道，性能更优。
 */
@Data
@ToString
@EqualsAndHashCode
public class NotifyTransferOrderChangedReqDto extends BaseReqDto implements Serializable {

    private static final long serialVersionUID = -7292361911054183397L;

    /**
     * 【描述】请求来源的接口
     * 【示例值】alipay.fund.trans.app.pay
     * 必选  64
     */
//    private String originInterface;

    /**
     * 【描述】商户端的唯一订单号
     * 【示例值】201806300001
     * 必选  64
     */
    private String outBizNo;

    /**
     * 【描述】销售产品码，商家和支付宝签约的产品码。 STD_RED_PACKET：现金红包； TRANS_ACCOUNT_NO_PWD：单笔无密转账 FUND_ACCOUNT_BOOK：记账本
     * 【枚举值】
     * 单笔无密转账: TRANS_ACCOUNT_NO_PWD
     * 现金红包: STD_RED_PACKET
     * 记账本: FUND_ACCOUNT_BOOK
     * 【示例值】STD_RED_PACKET
     * 必选  32
     */
    private String productCode;

    /**
     * 【描述】描述特定的业务场景，如果传递了out_biz_no则该字段为必传。可取的业务场景如下： PERSONAL_PAY：C2C现金红包-发红包； PERSONAL_COLLECTION：C2C现金红包-领红包； REFUND：C2C现金红包-红包退回； DIRECT_TRANSFER：B2C现金红包、单笔无密转账 SATF_DEPOSIT：记账本充值
     * 【枚举值】
     * C2C现金红包-发红包: PERSONAL_PAY
     * C2C现金红包-领红包: PERSONAL_COLLECTION
     * C2C现金红包-红包退回: REFUND
     * B2C现金红包、单笔无密转账: DIRECT_TRANSFER
     * 记账本充值: SATF_DEPOSIT
     * 【示例值】PERSONAL_PAY
     * 必选  32
     */
    private String bizScene;

    /**
     * 【描述】支付宝转账单据号
     * 【示例值】20190624110075000006530000014566
     * 必选  64
     */
    private String orderId;

    /**
     * 【描述】转账单据状态。可能出现的状态如下： SUCCESS：转账成功； WAIT_PAY：等待支付； CLOSED：订单超时关闭； REFUND：退票； DEALING：转账到银行卡处理中； FAIL：转账失败； alipay.fund.trans.app.pay涉及的状态： WAIT_PAY SUCCESS CLOSED alipay.fund.trans.uni.transfer、alipay.fund.trans.refund涉及的状态：SUCCESS REFUND DEALING FAIL 不同的转账接口涉及不同的单据状态，以实际场景为准
     * 【枚举值】
     * 转账成功: SUCCESS
     * 等待支付: WAIT_PAY
     * 订单超时关闭: CLOSED
     * 退票: REFUND
     * 处理中: DEALING
     * 转账失败: FAIL
     * 【示例值】SUCCESS
     * 必选  16
     */
    private String status;

    /**
     * 【描述】资金订单的操作类型， CREATE-创建； FINISH- 订单处理已完结 CLOSE-超时关闭 ；
     * 【枚举值】
     * 创建: CREATE
     * 订单处理已完结: FINISH
     * 超时关闭: CLOSE
     * 【示例值】FINISH
     * 必选  32
     */
    private String actionType;

    /**
     * 【描述】转账金额,单位元
     * 【示例值】32.00
     * 必选  20
     */
    private String transAmount;

//    /**
//     * 清算机构流水号
//     * 【描述】金融机构发起签约类、支付类、差错类业务时，应为每笔业务分配唯一的交易流水号。31位交易流水号组成规则为：“8位日期”+“16位序列号”+“1位预留位”+“6位控制位”，其中： a）“8位日期”为系统当前日期，ISODate格式：“YYYYMMDD” b）“16位序列号”由金融机构生成，金融机构应确保该值在网联当日唯一 c）“1位预留位”由平台分配 d）“6位控制位”由金融机构通过平台获取 例如：2023052993044491260542090100400
//     * 【示例值】2023052993044491260542090100400
//     */
//    private String settleSerialNo;

    /**
     * 收款方支付宝账号uid
     * 【描述】收款方支付宝账号uid新商户建议使用receiver_open_id替代该字段。对于新商户，receiver_user_id字段未来计划逐步回收，存量商户可继续使用。如使用receiver_open_id，请确认 应用-开发配置-openid配置管理 已启用。无该配置项，可查看openid配置申请。
     * 【示例值】2088872048673300
     */
    private String receiverUserId;

    /**
     * 收款方支付宝账号openid
     * 【描述】收款方支付宝账号openid  详情可查看 openid简介
     * 【示例值】074a1CcTG1LelxKe4xQC0zgNdId0nxi95b5lsNpazWYoCo5
     */
    private String receiverOpenId;

    /**
     * 【描述】支付宝支付资金流水号，仅当转账成功才会返回该参数
     * 【示例值】20190801110070001506380000251556
     * 可选  64
     */
    private String payFundOrderId;

    /**
     * 【描述】支付完成时间
     * 【示例值】2013-01-01 08:08:08
     * 可选  20
     */
    private String payDate;

    /**
     * 【描述】自动退款时间
     * 【示例值】2013-01-02 08:08:08
     * 可选  20
     */
    private String refundDate;

}
