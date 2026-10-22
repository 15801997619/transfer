package com.suomei.payment.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BaseRespDto implements Serializable {

    private static final long serialVersionUID = 3031427159602833363L;

    private String code;

    private String message;

    /** 接口版本 默认1.0*/
    private String version;
    /** 商户唯一凭证 */
    private String appId;
    /** 签名类型，默认RSA*/
    private String signType;
    /** 签名值 */
    private String sign;

}
