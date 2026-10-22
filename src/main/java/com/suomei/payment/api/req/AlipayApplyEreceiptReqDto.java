package com.suomei.payment.api.req;

import com.suomei.payment.api.BaseReqDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;


/**
 * 电子回单申请请参实体类
 */
@Data
@ToString
@EqualsAndHashCode
public class AlipayApplyEreceiptReqDto extends BaseReqDto implements Serializable {

    private static final long serialVersionUID = -8029028524150648071L;


    /**
     *  申请类型
     * 【描述】申请的类型。可传入：
     * * ACCOUNT_LOG_DETAIL - 余额收支证明
     * * ACCOUNT_FLOW_DETAIL - 余额收支流水证明
     * * FUND_DETAIL - 转入转出收支证明
     * * ACCOUNT_LOG_SUM_DAILY - 收支汇总证明（日汇总）
     * * ACCOUNT_LOG_SUM_MONTHLY - 收支汇总证明（月汇总）
     * 【示例值】FUND_DETAIL
     */
    @NotBlank(message = "申请类型不能为空")
    @Length(max = 50,message = "申请类型长度不能大于50位")
    private String type;

    /**
     * 【描述】根据不同业务类型，传入不同参数。
     * 格式如下：
     * * ACCOUNT_LOG_DETAIL - 传入账务流水号，示例：117007*********9151
     * * ACCOUNT_FLOW_DETAIL - 传入流水日期区间示例：20220301-20220331
     * * FUND_DETAIL - 传入资金单据号，示例：2022051511007***************0731
     * * ACCOUNT_LOG_SUM_DAILY - 传入日期，示例：2022-01-01
     * * ACCOUNT_LOG_SUM_MONTHLY - 传入月份，示例：2022-01
     * 【示例值】2020xxx
     */
    @NotBlank(message = "申请KEY不能为空")
    @Length(max = 30000,message = "申请KEY长度不能大于30000位")
    private String key;

//    /**
//     * 【描述】授权商户pid。需调用方获取商户授权，才可使用。
//     * 【示例值】2088********9999
//     * 可选
//     */
//    @Length(max = 64,message = "授权商户pid长度不能大于64位")
//    private String billUserId;

}
