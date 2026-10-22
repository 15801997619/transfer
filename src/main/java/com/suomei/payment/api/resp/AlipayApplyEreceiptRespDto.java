package com.suomei.payment.api.resp;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@EqualsAndHashCode
public class AlipayApplyEreceiptRespDto implements Serializable {

    private static final long serialVersionUID = 5153198933037127231L;

    /**
     * 【描述】文件申请号file_id信息。
     * 使用file_id可以查询处理状态，有效期：2天
     * 【示例值】12345
     */
    private String fileId;
}
