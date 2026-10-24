package com.suomei.payment;

import com.alibaba.fastjson.JSONObject;
import com.suomei.payment.common.utils.RSAHelper;
import com.suomei.payment.common.utils.SignUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AlipayTransferPreApplication.class)
@Slf4j
class AlipayTransferTests {


    @Test
    void contextLoads() {
    }
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    /** 测试秘钥>>签名私钥 */
    private String priKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCV/G2XFFv2WhPEGdAqY8y/nhArxTIjmE3RBGj5stDSGMKr5jrWmH8qSpfpkc8sDhHujY9+KGlMXQi383FCpsDqn8EyozUkI2rl9voTdMFSYy9vcyHILbbYgKK21pf9S7RlL0qKHKqn2h9ia3StgaWLmd5lFTO1heIf/OfEaZ1ko2Xji/oLl4zmncQHIjGPvMAJoWZRhp9dqJUpdXA3/WXzl0IzMmNN2XIRm2s7FhkULZmshSLae+ZLeAzeXKEjRTnn8hWL6QRkn5cv/gA3StAZOrE2CraN5qjzphbugOWqIizFhT3Ru3MMrhi1bGSnXZw6fRrRzfs4iqP0QK18ZAuhAgMBAAECggEAPhUBa7KyqhL48YfZwWIT4xDTErsKGO+27hiMNycMBprXIX4nxqHSqTe9D6IaBdtBEuQnjAnAJCqyLCopLHSHj9ZWitZBbdb3LX8AXkNKrgZ+IPyf9BLwB0efklzbBq5qWiOsD+Tzwoynxv5BinwKgPFMu7x97dogEiuF56frzTau0uGrg4nk9+OOQU6+m09QcV1prjsBYmmwrYxGz2MzJ/tbII2rQNsG4gsSaQ9IoyAqB0hs33Vbg85ftL17sPQr01zya4ylxS+3M+zVTvHc/kMd11pA05UJ9c59BXDiDWGv9POHyI/UsQfGnBDP7XrS+3EPE60qlefEWkCQQWBPQQKBgQDNpTkF4Jpw7BDbME2c62z5zlgw6pIyO1+4S3u2K0eWRUmZms2htamLNaMI2FYAYO6UC+ZMsmWZ7hwDIVxfQ3d4cSEkgya8iD19wGT0yei/okRRgkCkcTjmWppk2SxZMtx28h/rylc+sRXtE/pv0k1hAsTk/1kGnNaA3Q+S2mABmQKBgQC6tjohaEnH59wrmTTIfRxVyD/KOn18Hbdjnfk7jttnS6SmDryUYser7Ihj20/m2X8gbxmJQTwagcmp9c0TbiA1rUef3HESTpOtnHFLHGIEiPejLrm27rFtEig3VfJqyrewMfqqN7aFml/+rIBd68V5yuGWRYyhdRMGTD940MyvSQKBgFErghJ70Pn5c73QlxH1KxJ/C/hi3knNfb60rKP++UarGp3yqG/H0KAOD8gR/AAyQnlVKRXk5I2ZmpzxshA4BRQZM0fCLKwF6p+ULUUh3Sim1+iETRebSSZDZOWhgwvCM5sPfuIG/+DS7SCi3hr9G2XLPgRVdcJSbah3mQoSAAshAoGACc5cSzjg9yDUTW3kecsoW9ECmKptwMEGyrKXTmt4VnqAkUAg42I0vnDT0PrJvYQayydKngzRDUtXvhdsx+J2yprpMhovzhhWZT3cLWe2NirDZsyjcFok82eKuS1IaBdxgk19rsbyTR4IgKWqsEQbwQPIyU2pzykI8MG8a6n7thECgYEAyHlszuqYBbGmCRkGkeXlnCkq4ZB7ZHBkIzovrCc+9ZyV1AMqLwRfFk4NMazhwkrz3omw6B+g+RHoFEzua+A/U7fsdIRC443/0wxmf76DAwDkMA1iLD79E7GJSuKOAjwtGV6r2IxwyrDbylEQ7P1RUgZNO3LzYiobS6yefmrRBJE=";

    /** 测试秘钥>>验签公钥 */
    private String pubKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlfxtlxRb9loTxBnQKmPMv54QK8UyI5hN0QRo+bLQ0hjCq+Y61ph/KkqX6ZHPLA4R7o2PfihpTF0It/NxQqbA6p/BMqM1JCNq5fb6E3TBUmMvb3MhyC222ICittaX/Uu0ZS9Kihyqp9ofYmt0rYGli5neZRUztYXiH/znxGmdZKNl44v6C5eM5p3EByIxj7zACaFmUYafXaiVKXVwN/1l85dCMzJjTdlyEZtrOxYZFC2ZrIUi2nvmS3gM3lyhI0U55/IVi+kEZJ+XL/4AN0rQGTqxNgq2jeao86YW7oDlqiIsxYU90btzDK4YtWxkp12cOn0a0c37OIqj9ECtfGQLoQIDAQAB";


    /**
     * 对接秘钥>>商户签名私钥（商户生成，自行保存）
     * 注意：商户生成的公钥需要提供给平台进行验签使用
     * */
    private String merPriKey = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQC/ABaO+lBXsqpDjzyChV5Z3sXiNKZi5X2cONIpI9xP0duaFY/D2fbnPLeOUb/Nqpe4C7T3P2ju3ANZnaGS0HU7PDj48t8c/L/cwv0l+ZjxjGX5426Lzl8I78LOD4WJA8hXCr1FXNLHtrO6/ij6VIPgBWc1pvWYDdZANdkSHANN+5BT+7vnm95tDEZZejM4eohx2RDMO3EFthBaS8Lz62ioCbkQ1xzAsH0dUXryXeemNr9K8HCVFpqQ00V2IGsPNPubElruJ/f/PKtDBCIVO2RcZLM21MkF/LTUwpIVwQLbSzgsUweK9vxcGEGOsfepAutuyUqf99uQKrtl9Se+j8GDAgMBAAECggEBAKXOMccUzUIUO3JCmcjEdk+V8DqacgZQKma/fNFx7ALurrBoH1FpmcjLEJ1foh2sBeEjaIlqZnFUVSQqjuSgSeSONvcF/2FumC/QpWmd74Ng2fW/1WtQT3e2i2nOV6HFwtis3fmkxuXQWN3vXStSythiWGJe+bxx5pf/Jt1trAC47XiHijWmxa1SftuF3v+M+QFgmh6MfUiOelO944aoeTI3VdMsPxoQKBBZYzhBOjy1hi0hEi2U8PB8DokhE3dOsUup+TSdwXiAaHDCScDPyD5tYiQbWUWVtL77AptzAWQ/cbel8gQP+uLLIJ22HS3LV4hLe14aZRgzjClf4EUbO2ECgYEA3p4Z+bkAGOptzZKzpyA99WafaMc0LF8Ie32yh7PbnPURwxdcXqV92E5eRVkgHeBX3BoOsXzZbmjl3jPVqF5L/RrsB48JBkgg09/6AILJwCHIQZ6dcE5E2Ng8LnyemUsJvFu/tG+YsH7WUyD/82myWWXtddTg/UdysEsrNoe5mM8CgYEA26RBm+SOXYLxNmMTZEZCnptM09nffbikY5aiqo2+1jdnKJgven3V9RBQ4NDbNt/O7WcGuDUF+eVz16hP7G3WCdjslLbe6Y0y1wDuwua59PHQ1hgqHG9LdXFkxxqKQ00LMJU798nzJybdTTCFvncua5u8MMLbGOCORBp29Zn+0Q0CgYAdWvfN6ElM/8zO6ONrzGKfM5Mdu/bzSuzRa/fY0SzKe76F3VS+3k/D8EGaH3COwACV85GwcegyGP8G2Ar6ksSucZhiPshP3b8GcWiEHLsB7PJ9RK86GfgWKB13e9NmH3D/N5IF9xlJXNBiUd2ueWedRkWT3ih0/bDv8lmWbTtaHQKBgQCBv//OEu+TCDZsdYzQEL1sKrvw7mUbRMc/2Y3QiRmyPFphXb22c73AHlpY5KiLOx1nmxQaH6CfJlAPlutX9O6bNJ1VoM4AVBwzbOafFmPF8k/3GaFO2ORPxaPOBryk00+H7LDXSWKtv0S+Q6rOgGNYyFAc0g9SG92PF78siUNBxQKBgQCWMMk6OnNA2+lLGHnC8I5PQkdgjXjChEHV+agMPv6DMAIgyhN4LzbLUZVuYjqHqZWM+SHYzH2gAP8zKP63cy00yXPmm2Vd3JnBYtCykBi6rFzYkX1/hNlmUKyFHOb+4BJ/8siTl1vC1Gc7VivrhbSxvauq7xreH/5F9dHiUdi+xg==";
    /** 对接秘钥>>商户验签公钥（平台生成，提供给商户） */
    private String platformPubKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlmkDOMzThja0KkFVRTTdEqnB1g1/8YjF1PW6VWWMtwbZhAY5jorf4bFjrlaFfeyjadGAZ9EGecx5akja8Wv674pxNmPNAW0HxdKNJ7gwNbn/9HiNh2FOT24olTYtNMEKXLR220HqpTLT+lbOiOcMHvf33Y/LaqgXpy4WABWHWH618hcMBbgm8rOJLD3/HWmQ/hqGgyQcm52ZbH7gCvHS3rukgDTeSxBfDGUFQEhF7qdVZNfA5IoIkRv4ni1ZytGnARScZJrmvyd/GzgQOznChaz97tGprFQPty7UirlQQfbEEUucdeVmUqC+rEoXpAL4pld4OuyIHbllJFqSxdEeWwIDAQAB";
    private String platformPubKey1 ="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlJ4xBAg70CB7JdumePznRerzSif14bvoAVaSHuP49a1nrZ5f5h6RYwrcvWcIAywwcbOsn8hUlqayyRkjA+865uUGi/tCH+Tt99F7fGiXYXIh4SUNcPkTXqnepPS28A3UyNAH5zIcLI0Obg8h66Q/0zVW3ghtrIl/er3XORYqnuyE5lSxXb62EdfxZ9Ssq+GjnOKTg/Y1Nn9RJ252bKGBqQJ/zOLpHNVBZxNkZkwZGmZc3Uv3k/5fL+rb94gj/Pcgy18DRiB+9osnoIyAm7Gvhu+jLD0dOE0HUxcgWvrH9p1oMPuUal1K70Agg0Ky6IK8k8HarL6q44oqZBDcOkyZqwIDAQAB";


    /**
     * 生成RSA密钥对
     */
    @Test
    public void testGenerateKey() {
        Map<String, String> keyMap = RSAHelper.createKey();
        log.info("签名私钥（商户请求平台时数据签名使用，商户自行管理）:\n"+keyMap.get("PRIVATE_KEY"));
        log.info("验签公钥（商户请求平台数据，平台验签使用，商户提供给平台）:\n"+keyMap.get("PUBLIC_KEY"));
    }

    /**
     * Rsa 签名
     * @throws Exception
     */
    @Test
    public void testRsaSign() throws Exception {
        JSONObject reqData = new JSONObject();
        reqData.put("platformAppId", "SM20261018160002");
        reqData.put("signType", "RSA");
        reqData.put("alipayAppId", "2021006176658274");
        reqData.put("version", "1.0");
        String jsonDataStr = reqData.toJSONString();
        log.info("加签明文:\n{}", jsonDataStr);
        String sign = SignUtils.sortAndRsaSign(jsonDataStr, priKey);
        reqData.put("sign", sign);
        log.info("签名值:\n{}", sign);
        log.info("签名后接口请参:\n{}", reqData.toJSONString());

    }

    /**
     * RSA 验签
     */
    @Test
    public void testValidateRsaSign() throws Exception {
        String reqDataStr = "{\"platformAppId\":\"SM20261018160002\",\"sign\":\"MVhxkNgCnW7adR9u9oivUf4Q9saLlOlgYxTyD9z5o7k+AWWTjK9f/1gyj5eAtJolrlTyRJOsgtv52xSggAuR1dsHtp+OjgMCHOMAW2dQ5UENO3dHc3aDHMJGQOuCY99jYgkCeDDt/9p90M3eSERFWwHg8LKSTXY8bJQP3oSzir4yaay8QePpXNNp3xBnouf79m0uftlM6DwhAcb0SIFYKHbdJj9qomEwIPz+t2XeC8976D22WIbhe458p6lcpZAUdsJx6sWM5O2LQLFcFLdlaJlxfeFeA3mbPvOLkLVLHvHffnA6cOX75L9Ylz+Wa8MkN9Uo7zlnBDTFU1ryoyU4cQ==\",\"signType\":\"RSA\",\"alipayAppId\":\"2021006176658274\",\"version\":\"1.0\"}";
        validateRsaSign(reqDataStr,null,pubKey);
    }

    /**
     * 验证签名
     * @param reqDataStr
     * @param reqSign
     * @param pubKey
     * @return
     * @throws Exception
     */
    public boolean validateRsaSign(String reqDataStr,String reqSign,String pubKey) throws Exception {
        JSONObject reqData = JSONObject.parseObject(reqDataStr);
        String sign = StringUtils.defaultIfBlank(reqSign,reqData.getString("sign"));
        reqData.remove("sign");
        String jsonDataStr = reqData.toJSONString();
        log.info("验签明文:\n{}", jsonDataStr);
        log.info("签名值:\n{}", sign);
        boolean result = SignUtils.validateRsaSign(jsonDataStr,sign,pubKey);
        log.info("验签结果:\n{}", result);
        return result;
    }

    /**
     * 支付宝商家账户余额查询
     */
    @Test
    void testQueryAccountInfo() throws Exception {
        //1.组装请参
        String jsonBody = "{\n" +
                "  \"platformAppId\": \"SM20261018160002\",\n" +
                "  \"signType\": \"RSA\",\n" +
                "  \"alipayAppId\": \"9021000169679124\",\n" +
                "  \"version\": \"1.0\"\n" +
                "}";
        //2.接口请参签名
        log.info("加签明文:\n{}", jsonBody);
        String sign = SignUtils.sortAndRsaSign(jsonBody, merPriKey);
        JSONObject reqData = JSONObject.parseObject(jsonBody);
        reqData.put("sign", sign);
        //3.模拟post请求
        MvcResult result = mockMvc.perform(post("/openApi/queryAccountInfo")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .content(reqData.toJSONString()))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        //4.响应参数惨胡
        String responseBody = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        log.info("响应状态码:{}，响应体 JSON: {}" ,status,responseBody);
        JSONObject respBodyData = JSONObject.parseObject(responseBody);
        String respDataStr = respBodyData.getString("respObj");
        String reqSign = respBodyData.getString("sign");
        //5.接口返参验签
        if(StringUtils.isNotBlank(respDataStr)){
            validateRsaSign(respDataStr,reqSign,platformPubKey);
        }
    }

    /**
     * 支付宝商家账户余额查询
     */
    @Test
    void testQueryAccountInfo1() throws Exception {
        //1.组装请参
        String jsonBody = "{\n" +
                "  \"platformAppId\": \"PR20261018160002\",\n" +
                "  \"signType\": \"RSA\",\n" +
                "  \"alipayAppId\": \"9021000169679124\",\n" +
                "  \"version\": \"1.0\"\n" +
                "}";
        //2.接口请参签名
        log.info("加签明文:\n{}", jsonBody);
        String sign = SignUtils.sortAndRsaSign(jsonBody, merPriKey);
        JSONObject reqData = JSONObject.parseObject(jsonBody);
        reqData.put("sign", sign);
        //3.模拟post请求
        MvcResult result = mockMvc.perform(post("/openApi/queryAccountInfo")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .content(reqData.toJSONString()))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        //4.响应参数惨胡
        String responseBody = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        log.info("响应状态码:{}，响应体 JSON: {}" ,status,responseBody);
        JSONObject respBodyData = JSONObject.parseObject(responseBody);
        String respDataStr = respBodyData.getString("respObj");
        String reqSign = respBodyData.getString("sign");
        //5.接口返参验签
        if(StringUtils.isNotBlank(respDataStr)){
            validateRsaSign(respDataStr,reqSign,platformPubKey1);
        }
    }

    /**
     * 商家单笔转账
     */
    @Test
    void testUniTransfer() throws Exception {
        String jsonBody = "{\n" +
                "  \"platformAppId\": \"SM20261018160002\",\n" +
                "  \"outBizNo\": \"2026102114020002\",\n" +
                "  \"bizScene\": \"DIRECT_TRANSFER\",\n" +
                "  \"remark\": \"转账备注\",\n" +
                "  \"alipayAppId\": \"9021000169679124\",\n" +
                "  \"version\": \"1.0\",\n" +
                "  \"transAmount\": \"5.5\",\n" +
                "  \"transferSceneReportInfos\": [\n" +
                "    {\n" +
                "      \"infoType\": \"佣金报酬说明\",\n" +
                "      \"infoContent\": \"9月家政服务报酬\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"productCode\": \"TRANS_ACCOUNT_NO_PWD\",\n" +
                "  \"businessParams\": \"{\\\"payer_show_name_use_alias\\\":\\\"true\\\"}\",\n" +
                "  \"payeeInfo\": {\n" +
                "    \"identityType\": \"ALIPAY_USER_ID\",\n" +
                "    \"identity\": \"2088722116565757\",\n" +
                "    \"name\": \"nonooo3625\"\n" +
                "  },\n" +
                "  \"signType\": \"RSA\",\n" +
                "  \"orderTitle\": \"商家转账20261021\",\n" +
                "  \"transferSceneName\": \"佣金报酬\"\n" +
                "}";
        //2.接口请参签名
        log.info("加签明文:\n{}", jsonBody);
        String sign = SignUtils.sortAndRsaSign(jsonBody, merPriKey);
        JSONObject reqData = JSONObject.parseObject(jsonBody);
        reqData.put("sign", sign);
        //3.模拟post请求
        MvcResult result = mockMvc.perform(post("/openApi/uniTransfer")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .content(reqData.toJSONString()))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        //4.响应参数惨胡
        String responseBody = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        log.info("响应状态码:{}，响应体 JSON: {}" ,status,responseBody);
        JSONObject respBodyData = JSONObject.parseObject(responseBody);
        String respDataStr = respBodyData.getString("respObj");
        String reqSign = respBodyData.getString("sign");
        //5.接口返参验签
        if(StringUtils.isNotBlank(respDataStr)){
            validateRsaSign(respDataStr,reqSign,platformPubKey);
        }
    }

}
