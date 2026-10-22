package com.suomei.payment.api.req;

import com.suomei.payment.api.BaseReqDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 查询对账单下载地址请参实体类
 */
@Data
@ToString
@EqualsAndHashCode
public class AlipayQueryBillDownloadUrlReqDto extends BaseReqDto implements Serializable {

    private static final long serialVersionUID = 4099662322379025057L;

    /**
     * 【描述】账单类型，商户通过接口或商户经开放平台授权后其所属服务商通过接口可以获取以下账单类型。
     * 【枚举值】
     * 商户基于支付宝交易收单的业务账单: trade
     * 基于商户支付宝余额收入及支出等资金变动的账务账单: signcustomer
     * 营销活动账单，包含营销活动的发放，核销记录: merchant_act
     * 直付通二级商户查询交易的业务账单: trade_zft_merchant
     * 直付通平台商查询二级商户流水使用，返回所有二级商户流水。: zft_acc
     * 每日结算到卡的资金对应的明细，下载内容包含批次结算到卡明细文件（示例）和批次结算到卡汇总文件（示例）；若查询时间范围内有多个批次，会将多个批次的明细和汇总文件打包到一份压缩包中；: settlementMerge
     * 收起
     * 【示例值】trade
     */
    @NotBlank(message = "账单类型不能为空")
    @Length(max = 20,message = "账单类型长度不能大于20位")
    private String billType;
    /**
     * 【描述】账单时间：
     *  日账单格式为yyyy-MM-dd，最早可下载近6年的日账单。不支持下载当日账单，只能下载前一日24点前的账单数据（T+1），当日数据一般于次日 9 点前生成，特殊情况可能延迟。
     *  月账单格式为yyyy-MM，最早可下载近6年的月账单。不支持下载当月账单，只能下载上一月账单数据，当月账单一般在次月 3 日生成，特殊情况可能延迟。
     *  当biz_type为settlementMerge时候，时间为汇总批次结算资金到账的日期，日期格式为yyyy-MM-dd，最早可下载2023年4月17日及以后的账单。
     *  接口调用仅支持下载近6年的账单，更多账单请前往 b.alipay.com 对账中心-账单下载页下载。
     * 【示例值】2025-05-01
     */
    @NotBlank(message = "账单时间不能为空")
    @Length(max = 15,message = "账单时间长度不能大于15位")
    private String billDate;

//    /**
//     * 【描述】二级商户smid，这个参数只在bill_type是trade_zft_merchant时才能使用
//     * 【示例值】2088123412341234
//     * 可选
//     */
//    @Length(max = 20,message = "二级商户smid长度不能大于20位")
//    private String smid;

}
