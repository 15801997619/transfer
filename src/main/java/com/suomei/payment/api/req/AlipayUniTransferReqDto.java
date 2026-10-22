package com.suomei.payment.api.req;

import com.suomei.payment.api.BaseReqDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 支付宝商家单笔转账请参
 */
@Data
@ToString
@EqualsAndHashCode
public class AlipayUniTransferReqDto extends BaseReqDto implements Serializable {

    private static final long serialVersionUID = -7801441480462419738L;
    /**
     * 【描述】商家侧唯一订单号，由商家自定义。对于不同转账请求，商家需保证该订单号在自身系统唯一。
     * 【示例值】201806300001
     */
    @NotBlank(message = "商家侧唯一订单号不能为空")
    @Length(max = 64,message = "商家侧唯一订单号长度不能大于64位")
    private String outBizNo;

    /**
     * 【描述】订单总金额，单位为元，不支持千位分隔符，精确到小数点后两位，取值范围[0.1,100000000]。
     * 【示例值】23.00
     */
    @NotBlank(message = "订单总金额不能为空")
    @Length(max = 16,message = "订单总金额长度不能大于16位")
    private String transAmount;


    /**
     * 【描述】销售产品码。单笔无密转账固定为 TRANS_ACCOUNT_NO_PWD。
     * 【示例值】TRANS_ACCOUNT_NO_PWD
     */
    @NotBlank(message = "销售产品码不能为空")
    @Length(max = 64,message = "销售产品码长度不能大于64位")
    private String productCode;

    /**
     * 【描述】业务场景。单笔无密转账固定为 DIRECT_TRANSFER。
     * 【枚举值】
     * 单笔无密转账到支付宝，B2C现金红包: DIRECT_TRANSFER
     * C2C现金红包-领红包: PERSONAL_COLLECTION
     * CAE代扣: CAE_TRANSFER
     * 余额充值到记账本: DIRECT_ALLOCATION
     * 资金调拨转账: DIRECT_ALLOCATION_TRANSFER
     * 记账本间资金调拨: ENTRUST_ALLOCATION
     * 调拨并转账: ENTRUST_ALLOCATION_TRANSFER
     * 记账本代发: ENTRUST_TRANSFER
     * 境外结汇入金: OVERSEA_FCY_TRANSFER
     * 红包资金发放: THIRDPARTY_PERSONAL_COLLECTION
     * 红包资金领取: THIRDPARTY_PERSONAL_COLLECTION_CONFIRM
     * 大额无限付: UNLIMITED_PAY
     * 收起
     * 【示例值】DIRECT_TRANSFER
     */
    @NotBlank(message = "业务场景不能为空")
    @Length(max = 64,message = "业务场景长度不能大于64位")
    private String bizScene;

    /**
     * 收款方信息
     */
    @NotNull(message = "收款方信息不能为空")
    @Valid
    private PayeeInfoDTO payeeInfo;

    /**
     * 【描述】转账业务的标题，用于在支付宝用户的账单里显示。
     * 【示例值】201905代发
     */
    @NotBlank(message = "转账业务的标题不能为空")
    @Length(max = 64,message = "转账业务的标题长度不能大于64位")
    private String orderTitle;

    /**
     *  转账场景名称
     * 【描述】该笔转账使用的转账场景，商户可前往【支付宝商家平台-资金管理-转账场景】中查看/申明转账场景。
     * 目前支持以下枚举值：现金营销、企业退款、佣金报酬、业务结算、二手回收、公益补助、行政补贴和退款、保险理赔
     * 注意：26年及以后新接入商户必须传入该字段。
     * 【示例值】佣金报酬
     */
    @NotBlank(message = "转账场景名称不能为空")
    @Length(max = 64,message = "转账场景名称长度不能大于64位")
    private String transferSceneName;

    /**
     *  转账场景上报信息
     * 【描述】转账场景信息，包含转账场景信息类型和转账场景信息描述。需按该笔转账使用的转账场景准确填写
     * 注意：26年及以后新接入商户必须传入该字段。
     */
    @NotNull(message = "设置转账场景上报信息不能为空")
    @Valid
    private List<TransferSceneReportInfoDTO> transferSceneReportInfos;

    /**
     * 【描述】业务备注。
     * 【示例值】201905代发
     * 可选
     */
    @Length(max = 200,message = "业务备注长度不能大于200位")
    private String remark;

    /**
     * 【描述】转账业务请求的扩展参数，支持传入的扩展参数如下：
     * payer_show_name_use_alias：是否展示付款方别名，可选，收款方在支付宝账单中可见。枚举支持：
     * * true：展示别名，将展示商家支付宝在商家中心 商户信息 > 商户基本信息 页面配置的 商户别名。
     * * false：不展示别名。默认为 false。
     * 示例值：{\"payer_show_name_use_alias\":\"true\"}
     * 可选
     */
    @Length(max = 2048,message = "长度不能大于2048位")
    private String businessParams;

}
