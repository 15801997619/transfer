package com.suomei.payment.api.resp;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@EqualsAndHashCode
public class AlipayFundAccountQueryRespDto implements Serializable {

    /**
     * 账户可用余额，单位元，精确到小数点后两位
     */
    private String availableAmount;

}
