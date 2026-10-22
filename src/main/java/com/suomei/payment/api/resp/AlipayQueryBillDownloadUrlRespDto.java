package com.suomei.payment.api.resp;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;


/**
 * 查询对账单下载地址返参实体类
 */
@Data
@ToString
@EqualsAndHashCode
public class AlipayQueryBillDownloadUrlRespDto implements Serializable {

    private static final long serialVersionUID = -8554285541925945430L;


    /**
     *  账单下载地址
     * 【描述】当账单可获取时，返回账单下载地址链接，获取链接后30秒后未下载，链接地址失效。
     * 【示例值】http://dwbillcenter.alipay.com/downloadBillFile.resource?bizType=X&pid=X&fileType=X&bizDates=X&downloadFileName=X&fileId=X
     * 可选
     */
    private String billDownloadUrl;

    /**
     * 【描述】描述本次申请的账单文件状态。 EMPTY_DATA_WITH_BILL_FILE：当天无账单业务数据&&可以获取到空数据账单文件。
     * 【枚举值】
     * 空账单数据文件：当前周期无数据时候产生的账单文件: EMPTY_DATA_WITH_BILL_FILE
     * 【注意事项】目前仅对默认配置用户生效，主动配置的不会返回当前字段。
     * 【示例值】EMPTY_DATA_WITH_BILL_FILE
     * 可选
     */
    private String billFileCode;

}
