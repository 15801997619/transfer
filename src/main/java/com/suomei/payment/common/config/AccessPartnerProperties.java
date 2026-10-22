package com.suomei.payment.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.List;

@RefreshScope
@ConfigurationProperties(prefix = "myapp.access") // 前缀为根节点，字段名 partners 会自动拼成 myapp.access.partners[0].xxx
@Data
@Component
public class AccessPartnerProperties {

    // 对应 myapp.access.partners[0]...
    private List<Partner> partners;

    @Data
    public static class Partner {
        private String appId;
        private String merPubKey;
        private String notifyUrl;
    }

    public Partner getPartnerByAppId(String appId) {
        if (partners == null || partners.isEmpty()) {
            throw new IllegalStateException("myapp.access.partners 未配置");
        }
        for (Partner partner : partners) {
            if(partner.getAppId().equals(appId)) {
                return partner;
            }
        }
        return null;
    }
}
