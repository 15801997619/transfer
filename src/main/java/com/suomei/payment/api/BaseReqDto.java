package com.suomei.payment.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BaseReqDto implements Serializable {

    private static final long serialVersionUID = 8032825606638425235L;

    /** 接口版本 默认1.0*/
    @NotBlank(message = "接口版本不能为空")
    private String version;
    /** 平台唯一凭证 */
    @NotBlank(message = "platformAppId不能为空")
    @Length(max = 32,message = "platformAppId长度不能大于32位")
    private String platformAppId;
    /** 支付宝商家APPID */
    @NotBlank(message = "alipayAppId不能为空")
    @Length(max = 32,message = "alipayAppId长度不能大于32位")
    private String alipayAppId;
    /** 签名类型，默认RSA*/
    @NotBlank(message = "签名类型不能为空")
    private String signType;
    /** 签名值 */
    @NotBlank(message = "sign不能为空")
    private String sign;

}
