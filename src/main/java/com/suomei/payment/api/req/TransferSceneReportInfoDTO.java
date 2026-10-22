package com.suomei.payment.api.req;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 支付宝商家单笔转账接口，设置转账场景上报信息
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TransferSceneReportInfoDTO implements Serializable {

    private static final long serialVersionUID = -8423310621226603633L;
    /**
     * 【描述】转账场景信息类型，即商户所属转账场景下的信息类型。此字段内容为固定值，需严格按照转账场景信息字段说明传参。
     * 例如：transfer_scene_name为“佣金报酬”时，该值必须填写取值：佣金报酬说明
     * 注：信息类型不可重复填写，请严格按照示例传入
     * 【示例值】佣金报酬说明
     */
    @NotBlank(message = "转账场景信息类型不能为空")
    @Length(max = 64,message = "转账场景信息类型长度不能大于64位")
    private String infoType;

    
    /**
     * 【描述】转账场景信息描述，即商户所属转账场景下的信息内容。商户可按实际业务场景自定义传参，需严格按照转账场景信息字段说明传参。
     * 例如：transfer_scene_name为“佣金报酬”且info_type为“佣金报酬说明”时，该值可传入：8月家政服务报酬
     * 【示例值】8月家政服务报酬
     */
    @NotBlank(message = "转账场景信息描述不能为空")
    @Length(max = 256,message = "转账场景信息描述长度不能大于256位")
    private String infoContent;

}
