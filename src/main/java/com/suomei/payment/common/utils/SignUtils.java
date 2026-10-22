package com.suomei.payment.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 签名工具类
 *
 * @author fengzhaowang
 */
@Component
public class SignUtils {

    private static final Logger log = (Logger) LoggerFactory.getLogger(SignUtils.class);


    /**
     * rsa签名
     */
    public static String rsa(String json, String keySecret) throws Exception {
        return rsaSign(json, keySecret);
    }


    /**
     * 验证签名
     */
//    public static boolean validateSign(String jsonStr, String reqSign, String signKey) {
//        SignUtils validateSign = new SignUtils();
//        return validateSign.checkSign(jsonStr, reqSign, signKey);
//    }


    /**
     * rsa 验证签名
     */
    public static boolean validateRsaSign(String jsonStr, String reqSign, String publikcKey) throws Exception {
        SignUtils validateSigns = new SignUtils();

        String signStr = sortMap(jsonStr);
        log.debug("signStr:{}", signStr);
        return validateSigns.checkRsaSign(signStr, reqSign, publikcKey);
    }

    /**
     * Rsa 验证签名
     */
    private boolean checkRsaSign(String jsonStr, String reqSign, String publikcKey) throws Exception {
        // 生成签名
        RSAHelper rsaHelper = new RSAHelper();
        int keyLength = 2048;
        rsaHelper.initKey("", publikcKey, keyLength);
        return rsaHelper.verifyRSA(jsonStr.getBytes(StandardCharsets.UTF_8), reqSign.getBytes(StandardCharsets.UTF_8), true, StandardCharsets.UTF_8.name());
    }

    /**
     * 排序并 Rsa 验证签名
     */
    public static String sortAndRsaSign(String jsonStr, String privateKey) throws Exception {
        String sortData = sortMap(jsonStr);
        return rsaSign(sortData, privateKey);
    }

    /**
     * Rsa 验证签名
     */
    public static String rsaSign(String jsonStr, String privateKey) throws Exception {
        // 生成签名
        RSAHelper rsaHelper = new RSAHelper();
        int keyLength = 2048;
        rsaHelper.initKey(privateKey, null, keyLength);
        String sign = new String(rsaHelper.signRSA(jsonStr.getBytes(StandardCharsets.UTF_8), true, StandardCharsets.UTF_8.name()), StandardCharsets.UTF_8);
        log.info("jsonStr:{},sign:{}",jsonStr, sign);
        return sign;
    }

    public static String sortMap(String str) {
        String result = "";

        List<Map.Entry<String, Object>> infoIds = new ArrayList<>();
        buildList(JSON.parseObject(str), infoIds);
        infoIds.sort(new Comparator<Map.Entry<String, Object>>() {
            public int compare(Map.Entry<String, Object> o1, Map.Entry<String, Object> o2) {
                if ((o1.getKey()).trim().compareTo((o2.getKey()).trim()) == 0) {
                    return (Objects.toString(o1.getValue())).trim().compareTo(((Objects.toString(o2.getValue())).trim()));
                }
                return (o1.getKey()).trim().compareTo((o2.getKey()).trim());
            }
        });

        try {
            StringBuilder sb = new StringBuilder();

            for (Map.Entry<String, Object> infoId : infoIds) {
                if (null != infoId) {
                    String key = (String) ((Map.Entry<?, ?>) infoId).getKey();
                    Object val = infoId.getValue();
                    if (null != val) {
                        sb.append(key).append("=").append(val).append("&");
                    }
                }
            }

            result = sb.substring(0, sb.length() - 1);
        } catch (Exception var8) {
            var8.printStackTrace();
        }
        log.debug("sortData:{}", result);
        return result;
    }

    private static void buildList(JSONObject jsonObject, List<Map.Entry<String, Object>> infoIds) {
        for (Map.Entry<String, Object> en : jsonObject.entrySet()) {
            if (en.getValue() instanceof JSONObject) {
                buildList(((JSONObject) en.getValue()), infoIds);
            } else if (en.getValue() instanceof JSONArray) {
                JSONArray array = ((JSONArray) en.getValue());
                for (int i = 0; i < array.size(); i++) {
                    buildList(array.getJSONObject(i), infoIds);
                }
            } else {
                infoIds.add(en);
            }
        }
    }


}
