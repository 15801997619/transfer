package com.suomei.payment.api.resp;


import lombok.Data;
import lombok.ToString;

import java.io.Serializable;


/**
 * 查询电子回单状态返参实体类
 */
@Data
@ToString
public class AlipayQueryEreceiptRespDto implements Serializable {

    private static final long serialVersionUID = 8183301344026205440L;

    /**
     *  处理状态
     * 【描述】处理状态。枚举值如下： INIT：初始化。 PROCESS：处理中。 SUCCESS：成功。 FAIL：失败。
     * 【示例值】SUCCESS
     */
    private String status;

    /**
     * 下载链接
     * 【描述】下载链接，status 为 SUCCESS 时返回。用户可以使用此http链接下载文件内容。有效时间为 30s。 生成文件为PDF格式，下载即可获取电子回单 PDF 内容。
     * 【示例值】https://dwbillcenter.alipay.com/xxx
     */
    private String downloadUrl;

    /**
     * 【描述】如果生成失败，则会返回失败原因
     * 【示例值】key不存在
     */
    private String errorMessage;

}
