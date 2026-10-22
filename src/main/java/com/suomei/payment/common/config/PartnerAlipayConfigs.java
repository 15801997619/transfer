package com.suomei.payment.common.config;

import lombok.*;

import java.io.Serializable;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PartnerAlipayConfigs implements Serializable {


    private static final long serialVersionUID = 5172230043764483268L;

    /** 平台唯一凭证 */
    private String platformAppId;

    private AccessPartnerProperties.Partner partner;

    /** 支付宝商家APPID */
    private String alipayAppId;

    private AlipayConfigProperties.AlipayAccount alipayAccount;


}
