package com.suomei.payment.api.req;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 支付宝商家单笔转账接口，收款方信息
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PayeeInfoDTO implements Serializable {

    /**
     * 【描述】参与方的标识类型，目前支持如下枚举：
     * ALIPAY_USER_ID：支付宝会员的用户 ID，可通过 获取会员信息 能力获取。
     * ALIPAY_LOGON_ID：支付宝登录号，支持邮箱和手机号格式。
     * 【枚举值】
     * 支付宝的会员ID: ALIPAY_USER_ID
     * 支付宝登录号: ALIPAY_LOGON_ID
     * 支付宝openid: ALIPAY_OPEN_ID
     * 【示例值】ALIPAY_USER_ID
     */
    @NotBlank(message = "参与方的标识类型型不能为空")
    @Length(max = 64,message = "参与方的标识类型长度不能大于64位")
    private String identityType;

    /**
     * 【描述】参与方的标识 ID。
     * 当 identity_type=ALIPAY_USER_ID 时，填写支付宝用户 UID。示例值：2088123412341234。
     * 当 identity_type=ALIPAY_LOGON_ID 时，填写支付宝登录号。示例值：186xxxxxxxx。
     * 【示例值】2088123412341234
     */
    @NotBlank(message = "参与方的标识ID不能为空")
    @Length(max = 64,message = "参与方的标识ID长度不能大于64位")
    private String identity;

    /**
     * 【描述】参与方真实姓名。如果非空，将校验收款支付宝账号姓名一致性。
     * 当 identity_type=ALIPAY_LOGON_ID 时，本字段必填。若传入该属性，则在支付宝回单中将会显示这个属性。
     * 【示例值】黄龙国际有限公司
     */
    @Length(max = 256,message = "参与方真实姓名长度不能大于256位")
    private String name;

}
