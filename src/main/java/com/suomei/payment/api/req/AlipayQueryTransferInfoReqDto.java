package com.suomei.payment.api.req;

import com.suomei.payment.api.BaseReqDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Data
@ToString
@EqualsAndHashCode
public class AlipayQueryTransferInfoReqDto extends BaseReqDto implements Serializable {

    private static final long serialVersionUID = 901452012382522589L;

    /**
     *  销售产品码
     * 【描述】销售产品码，商家和支付宝签约的产品码，如果传递了outBizNo则该字段为必传。可传值如下： STD_RED_PACKET：现金红包 TRANS_ACCOUNT_NO_PWD：单笔无密转账到支付宝账户 TRANS_BANKCARD_NO_PWD：单笔无密转账到银行卡
     * 【示例值】STD_RED_PACKET
     * 可选
     */
    @Length(max = 64,message = "销售产品码长度不能大于64位")
    private String productCode;

    /**
     *  业务场景
     * 【描述】描述特定的业务场景，如果传递了outBizNo则该字段为必传。可取的业务场景如下： PERSONAL_PAY：C2C现金红包-发红包； PERSONAL_COLLECTION：C2C现金红包-领红包； REFUND：C2C现金红包-红包退回； DIRECT_TRANSFER：B2C现金红包、单笔无密转账
     * 【示例值】PERSONAL_PAY
     * 可选
     */
    @Length(max = 64,message = "业务场景长度不能大于64位")
    private String bizScene;

    /**
     *  商户转账唯一订单号
     * 【描述】商户转账唯一订单号，发起转账来源方定义的转账单据ID。 本参数和orderId（支付宝转账单据号）、payFundOrderId（支付宝支付资金流水号）三者不能同时为空。 当三者同时传入时，将用payFundOrderId（支付宝支付资金流水号）进行查询，忽略其余两者； 当本参数和支付宝转账单据号同时提供时，将用支付宝转账单据号进行查询，忽略本参数。
     * 【示例值】201808080001
     * 可选
     */
    @Length(max = 64,message = "商户转账唯一订单号长度不能大于64位")
    private String outBizNo;

    /**
     *  支付宝转账单据号
     * 【描述】支付宝转账单据号。 本参数和outBizNo（商户转账唯一订单号）、payFundOrderId（支付宝支付资金流水号）三者不能同时为空。 当三者同时传入时，将用payFundOrderId（支付宝支付资金流水号）进行查询，忽略其余两者； 当本参数和payFundOrderId（支付宝支付资金流水号）同时提供时，将用支付宝支付资金流水号进行查询，忽略本参数； 当本参数和outBizNo（商户转账唯一订单号）同时提供时，将用本参数进行查询，忽略商户转账唯一订单号。
     * 【示例值】20190801110070000006380000250621
     * 可选
     */
    @Length(max = 32,message = "支付宝转账单据号长度不能大于32位")
    private String orderId;

     /**
     *  支付宝支付资金流水号
     * 【描述】支付宝支付资金流水号。本参数和支付宝转账单据号、商户转账唯一订单号三者不能同时为空。 当本参数和outBizNo（商户转账唯一订单号）、orderId（支付宝转账单据号）同时提供时，将用本参数进行查询，忽略其余两者； 当本参数和orderId（支付宝转账单据号）同时提供时，将用本参数进行查询，忽略支付宝转账单据号； 当本参数和outBizNo（商户转账唯一订单号）同时提供时，将用本参数进行查询，忽略商户转账唯一订单号。
     * 【示例值】20190801110070001506380000251556
      * 可选
     */
    @Length(max = 32,message = "支付宝支付资金流水号长度不能大于32位")
    private String payFundOrderId;

}
