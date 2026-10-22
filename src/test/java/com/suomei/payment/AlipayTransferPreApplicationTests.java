package com.suomei.payment;

import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.io.resource.Resource;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.diagnosis.DiagnosisUtils;
import com.alipay.api.domain.AlipayFundAccountQueryModel;
import com.alipay.api.request.AlipayFundAccountQueryRequest;
import com.alipay.api.response.AlipayFundAccountQueryResponse;
import com.suomei.payment.service.CommonManage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AlipayTransferPreApplication.class)
@Slf4j
class AlipayTransferPreApplicationTests {

    @Test
    void contextLoads() {
    }

    /**
     * 支付宝appID
     */
    private static String aliAppId = "2021006176658274";
    private static String aliAppId_sx = "9021000169679124";

    /**
     * 应用私钥
     */
    private static String appPriKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCGkQTl8cgAiONgRyTd1wzQUzm1LDA88VvoRXtBXJZpHmSEsZgGlCkxZV/8wVEfbfu6aQV17JUjKPSlgL7yPFj+CAtigNZwhASUy0h4FNE6vCzwjnbHcvO12gP2FVeK8388iCKoc53Ry+qCNySBbu5Bk0qihDj3gRZQIfW6TLRffuaBmh3v0oVvMK+kvRURFyhMEvsCc9/inyJ3kiI1Eufvhqjc87EiLT/qzxrnhnd6v0YF7VZpjSNLmShn0HL2X8zUfz8X1vMtX6sQ4yMn96iDWO7qYOQGN4NlhHzroZzQnbM/BYkMXJqHuUFOoGGUpMJopnolj6pU6eIj0BYvQK8jAgMBAAECggEAJyd5EmMYe7dY2YG542aYTuF4cBPMcV8VC+9znqAPD8UNIGMARiHEHqBNnqCtNwTsct/t+9L7zgAztxhP9vHCPyAeF+o++vwezSCdFHuSKZHpTZFWVFBJQ0eNUYQCNecFxZoi43rIl+vKb1sWHyck4iW2WLU6UiHYWTaYZj29We+GLgGztX75g8XG3E63Nfw7/mS8ltA0O2PTsf+hnKxOL/ftCz4wH/gFCLsBrGyv7huy/G5VplSFYFp+a5SZX2ujZRtRgt4Q1DSsWcWJmcpaAFTlMPypJbRjRaSc4gwDsNjcMiqk2cTXALDMcC7uSjgM/mO/gZrd/xh4PQPEzRsX8QKBgQDu+OlbnMn3RjSDA4UpfwJGDZp4ZMbX3HoI4FdFjNPoWOuTiI267mW/xEgqxGyruxEjXnf9LCMpknyoufoxJmQ2mh7hnFdpMI2GWfRoI0xXN2rLQRe/ChU4N3ytqnJWz0eCeeF11tSobY9pNfLt3YVnjsbqbbNqTiDNyMc8UKiPOQKBgQCQJ6SqwszawLI/h+sKxjDxMONpxb0ZsliAvKCdXjVtpGe0uLusyupMR6jG4GMvaw3vNUL6J9Z6zjQhMYImiF/AscuHMPEaxZV+22VqVHsm1noWUYdoUccgEmq+iB+VTuKWfYxEBECU8/ICE47FCqJn/PgykkCKKNMoOz6GwegVOwKBgQCH6FgPGiRi1TKYMDBTYUBRxvrK7cWIjoN4+N2pWGZMBgY0abzxSxPi9CZ27C9Bm6ori1aCTc/RnNX4v4xi14q24wAdH2U3Fb3nYtF1SF3qXEIIRoTSyVE4H5Zwzjco18m7ksV+1ycqguTE2UHHtexg8Pp/6NPY6YYOgtmMm/LBQQKBgHNOEmu4N22+dpVUnY/Xqawy46RmU3SrcBlBR+O4SFo0B/PJU+zJvhFXvEufjhaOte2hl7pKhUdjitUGKcmyiYyzak3vutskMgjvarZbbspJAgy6F+o8h19C+/j9Hu4p1Bp1v+1jzViXq/s3M5quf+tXsBubWl5oe0YR5Via342jAoGAb3E3MXpxqScew5Nc5vqUFqa0it/9bj/J9bnCaRZxg0vhnEbQXUu9RzaBHkdj7XLleRw94jEg6gfmx8a5bsZomiCPsUwBepSjr1O4syN25oKt+GU5sff0R5JK05TqiBY1Q2LV6tlCQjwg5kpu+8fzTXO+8JM6C8HpdLOdkZes2x4=";
    private static String appPriKey_sx = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCCndvnxwVAPPuOigQTne/hGe8kwvsBeO92Ozmk1RvvhGPxouRGg/IPRXufHlS220o9wwh8Lnywy6x8l5BCHQNgeJpz5hBHpaBk/O6J1HccQps+is7RUP+ACP7FNevsar3lrSs0jm7eG1axSRvbQteChVTF0KRQGMuT1USzLs6XiMmXKIzjn3h8SULZfPivWUd5pkJzr1C/A/YZX/YHVRUIhT/NCVljc8jmcHjtWXMzx1hrJEpObnIeFOAjDzVrmFnBozO2v5Q7Vj0Oh1Ib1NzKKohl2AUKYSL6w3KrVdAO6fnUvKFLtA3msfrlWa4CTC5UcfE/TMuWup5o9lyQ5TBNAgMBAAECggEAXQe+VukDrmtA6JcYrDgzYGquT8qoKLZQKC+qsOIwkXoEDVjtqePUhlgA4qYhxFOQH0lIQ9qAaRPH+L2iu9QaUZ3UtHPKaJ1pDkSwSW8Qnwo5tGP9RkygjjrZ6vCq5qpoMqwSN86y3HMwXfkqZBLJmGcKqBIg1gOzC8o8sjrGXlrD18TdeYC5QIMh5BvdwgxyB1IMvtwa9OaDch6y7L63xrU2QAEOB2K7zQpj6ObIqW1G/owjMVA64aLmMXgI9gnqKmfjRKkmqGNQ1YTKNL8aF4zhlFjw3SwOceGzP8eHyQciQ1dofBbnam/HoEm10D6ljs9mhYIm+DGDDXxshAt2aQKBgQDDkqkMq1+MSPl9cqplZ7cO1ZX06RwBzIKdnSejkuw5gwyy+/kMEQdiHZKX3og9iWhZ7F8sobpO16hLf3gwiNsePCL3ckB4x14N1fD9+xgpPWYnARHdoJXefohMxtsCkAXmz/4frj/mkWVAELEcJRpQB5khRLtxGPTIdu7L813rPwKBgQCq+VEmqGHd6Y42zQflLE8ywH6J4u9kZYxu5BUbS71H/qBfVV9raCjLZDHAh0W7s+Gea7YGOfZrxLVvo60ppEGdCDRVVtKxZgc/s9hlHvxkc1YPtqcr6Oq9L5dsCmT6LeoZm6/oZ/ZgpguTMz7rB4VPD+Nj1S67DvPlz89T+jm9cwKBgQCzfoZi12KBSXSZ+0SCf1cWaVcRCWWoDQAQEm2QBkeCXVGpb/NabMKe/U9OzXGzMMqyRWvwdZqdw/Z1HE++E80vibf57UVY1HnxLtrY77x0FSMNR0ETbNA//VexR404Y+DWvyyX/QAu6GDKFMfIchEdRGv5ibCTE3mOBwIR85HOVwKBgGfqEECGprTRdvYnt1y6iYg3JrF3lDhY3/ml9o4Ygnp3b9GBefU14VV9GCAEF4X11nMK1B8Eq5jkfcHUoTJl+tsOWYBkvLnFYTJ+hE8xfgUvdGcohIJTlhhWDJpF1wmXTmkyDsAaL8yeHvrgcqU2dPeQmIvczgIzHFnY7BIGS4GxAoGBALpdbDqj9QRgmr46r1WcXnqf/X/UYa/JitMrsRFQBOIlnWwzXozyAFCUA8EgSbKJkfW0H7fnxTnYL3wVTTKvf958Ib+awHSJ0CPY6mgmB+/OoHCky8sYTLwHOy9ZtXKu0tddn8cFvj1B3StLmzEEuM70A6kEGScY3y344VzSw2mo";

    /**
     * 应用公钥证书路径
     */
    private static String appCertPath = "security/test/appCertPublicKey_2021006176658274.crt";
    private static String appCertPath_sx = "security/9021000169679124/appCertPublicKey_9021000169679124.crt";

    /**
     * 支付宝公钥证书路径
     */
    private static String alipayPublicCertPath = "security/test/alipayCertPublicKey_RSA2.crt";
    private static String alipayPublicCertPath_sx = "security/9021000169679124/alipayCertPublicKey_RSA2.crt";

    /**
     * 支付宝根证书路径
     */
    private static String rootCertPath = "security/test/alipayRootCert.crt";
    private static String rootCertPath_sx = "security/9021000169679124/alipayRootCert.crt";

    @javax.annotation.Resource
    private CommonManage commonManage;


    @Test
    public void testAlipayCallBack() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("charset", "UTF-8");
        params.put("utc_timestamp", "1792566673396");
        params.put("biz_content", "{\"pay_date\":\"2026-10-21 15:11:12\",\"biz_scene\":\"DIRECT_TRANSFER\",\"action_type\":\"FINISH\",\"origin_interface\":\"alipay.fund.trans.uni.transfer\",\"out_biz_no\":\"2026102115010001\",\"trans_amount\":\"1.50\",\"product_code\":\"TRANS_ACCOUNT_NO_PWD\",\"order_id\":\"20261021110070000002450004582247\",\"status\":\"SUCCESS\"}");
        params.put("sign", "lBtRrV3vj7uS8AbwU8Gt0Lrt0tGmncHp8TZTfbSQoFovmr7EIARLay9P6DAp5sBNG5cTH2NrN2TLGyfV4lX1N50NRDOCWg8R93ktSajgDRBAO24VRIzfhjpBlBZ7jH9tlQl5iKrG1DcKvnT/ETEcMNvBCxV9uKEKe73b2z5eWstSp0XnDO9kBGUtquOxOZoQfxgHFgqyrOouIG8XyVP8/QURX/RyDkCaAky0E/G3jDy6Iug7hmi1lDNGjfU/cHOSeWBtyLXtP6OTrzl3c0rpeH5LiECgLia6UbG6NlaAHmKKyrrhHnUyJFVv5LppPoyNIRRVERXI4g6YLMxuGSRoaA==");
        params.put("sign_type","RSA2");
        params.put("version", "1.1");
        params.put("app_id", "9021000169679124");
        params.put("msg_method", "alipay.fund.trans.order.changed");
        params.put("notify_id", "2026102100222151112106520501493674");
        log.info("notifyPartner req={}",params);
        String result = commonManage.notifyPartner(params);
        log.info("notifyPartner result={}",result);
    }

    @Test
    public void testQueryAccountInfo() throws AlipayApiException {
        // 初始化SDK
        AlipayClient alipayClient = new DefaultAlipayClient(getAlipayConfig());

        // 构造请求参数以调用接口
        AlipayFundAccountQueryRequest request = new AlipayFundAccountQueryRequest();
        AlipayFundAccountQueryModel model = new AlipayFundAccountQueryModel();
        // 设置查询的账号类型
        model.setAccountType("ACCTRANS_ACCOUNT");
        model.setAlipayUserId("2088780680922880");
        request.setBizModel(model);
        AlipayFundAccountQueryResponse response = alipayClient.certificateExecute(request);
        System.out.println(response.getBody());

        if (response.isSuccess()) {
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
            // sdk版本是"4.38.0.ALL"及以上,可以参考下面的示例获取诊断链接
             String diagnosisUrl = DiagnosisUtils.getDiagnosisUrl(response);
             System.out.println(diagnosisUrl);
        }
    }

    private static AlipayConfig getAlipayConfig() {
        Resource appCertPathResource = new ClassPathResource(appCertPath);
        Resource alipayPublicCertPathResource = new ClassPathResource(alipayPublicCertPath);
        Resource rootCertPathResource = new ClassPathResource(rootCertPath);

        AlipayConfig alipayConfig = new AlipayConfig();
        //设置应用APPID
        alipayConfig.setAppId(aliAppId);
        //设置应用私钥
        alipayConfig.setPrivateKey(appPriKey);
        //设置应用公钥证书路径
        alipayConfig.setAppCertPath(appCertPathResource.getUrl().getPath());
        //设置支付宝公钥证书路径
        alipayConfig.setAlipayPublicCertPath(alipayPublicCertPathResource.getUrl().getPath());
        //设置支付宝根证书路径
        alipayConfig.setRootCertPath(rootCertPathResource.getUrl().getPath());
        return alipayConfig;
    }

    /**
     * 沙箱测试
     */
    @Test
    public void testQueryAccountInfo4Sx() throws AlipayApiException {
        // 初始化SDK
        AlipayClient alipayClient = new DefaultAlipayClient(getAlipayConfigSx());

        // 构造请求参数以调用接口
        AlipayFundAccountQueryRequest request = new AlipayFundAccountQueryRequest();
        AlipayFundAccountQueryModel model = new AlipayFundAccountQueryModel();
        // 设置查询的账号类型
        model.setAccountType("ACCTRANS_ACCOUNT");
        model.setAlipayUserId("2088721116604453");
        request.setBizModel(model);
        AlipayFundAccountQueryResponse response = alipayClient.certificateExecute(request);
        System.out.println(response.getBody());

        if (response.isSuccess()) {
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
            // sdk版本是"4.38.0.ALL"及以上,可以参考下面的示例获取诊断链接
            String diagnosisUrl = DiagnosisUtils.getDiagnosisUrl(response);
            System.out.println(diagnosisUrl);
        }
    }

    /**
     * 支付宝商家转账接口
     * 沙箱环境测试参数
     * @return
     */
    private static AlipayConfig getAlipayConfigSx() {
        Resource appCertPathResource = new ClassPathResource(appCertPath_sx);
        Resource alipayPublicCertPathResource = new ClassPathResource(alipayPublicCertPath_sx);
        Resource rootCertPathResource = new ClassPathResource(rootCertPath_sx);

        AlipayConfig alipayConfig = new AlipayConfig();
        alipayConfig.setServerUrl("https://openapi-sandbox.dl.alipaydev.com/gateway.do");//沙箱环境
        //设置应用APPID
        alipayConfig.setAppId(aliAppId_sx);
        //设置应用私钥
        alipayConfig.setPrivateKey(appPriKey_sx);
        //设置应用公钥证书路径
        alipayConfig.setAppCertPath(appCertPathResource.getUrl().getPath());
        //设置支付宝公钥证书路径
        alipayConfig.setAlipayPublicCertPath(alipayPublicCertPathResource.getUrl().getPath());
        //设置支付宝根证书路径
        alipayConfig.setRootCertPath(rootCertPathResource.getUrl().getPath());
        return alipayConfig;
    }
}
