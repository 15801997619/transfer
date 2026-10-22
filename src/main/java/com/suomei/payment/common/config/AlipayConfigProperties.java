package com.suomei.payment.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.List;

@RefreshScope
@ConfigurationProperties(prefix = "alipay") // 前缀为根节点，字段名 configs 会自动拼成 alipay.configs[0].xxx
@Data
@Component
public class AlipayConfigProperties {
    
    // 对应 alipay.configs[0]...
    private List<AlipayAccount> configs;

    @Data
    public static class AlipayAccount {
        private String userId;
        private String appId;
        private String partnerAppId;
        private String appPriKey;
        private String appCertPath;
        private String alipayPublicCertPath;
        private String rootCertPath;
    }

    public AlipayAccount getConfigByAppId(String appId) {
        if (configs == null || configs.isEmpty()) {
            throw new IllegalStateException("alipay.configs 未配置");
        }
        for (AlipayAccount alipayAccount : configs) {
            if(alipayAccount.getAppId().equals(appId)) {
                return alipayAccount;
            }
        }
        return null;
    }
}

