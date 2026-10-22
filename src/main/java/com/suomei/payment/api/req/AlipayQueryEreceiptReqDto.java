package com.suomei.payment.api.req;

import com.suomei.payment.api.BaseReqDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 查询电子回单状态请参实体类
 */
@Data
@ToString
@EqualsAndHashCode
public class AlipayQueryEreceiptReqDto extends BaseReqDto implements Serializable {

    private static final long serialVersionUID = -4660732680087475237L;

    /**
     * 申请单id
     * 单个申请建议每5分钟查询一次状态。批量回单建议20分钟查询一次。汇总回单建议30分钟查询一次
     */
    @NotBlank(message = "申请单id不能为空")
    @Length(max = 50,message = "申请单id长度不能大于50位")
    private String fileId;
}
