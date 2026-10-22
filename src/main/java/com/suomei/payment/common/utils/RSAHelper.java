package com.suomei.payment.common.utils;

import javax.crypto.Cipher;
import java.io.*;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * RSAHelper -对RSA 签名&验签/分段加密&分段解密的包装签名算法: "SHA1withRSA", 私钥进行签名; 公钥进行验签.
 * 加密算法: "RSA/ECB/PKCS1Padding", 公钥进行加密; 私钥进行解密.
 * <p>
 * [localPrivKey]是自己的私钥, 自己的公钥给通信对方. [peerPubKey]是对方的公钥, 对方的私钥在对方那边. 为了方便,
 * 这里假定双方的密钥长度一致, 签名和加密的规则也一致.
 * <p>
 * 以`Base64Str`结尾的参数表示内容是Base64编码的字符串, 其他情况都是raw字符串.
 *
 */
public class RSAHelper {

    public static final String PUBLIC_KEY = "PUBLIC_KEY";
    public static final String PRIVATE_KEY = "PRIVATE_KEY";


    public static final String KEY_ALGORITHM = "RSA";
    public static final String SIGNATURE_ALGORITHM = "SHA1withRSA";
    public static final String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding"; // 加密block需要预留11字节
    public static int KEYBIT = 2048;
    public static final int RESERVEBYTES = 11;

    private PrivateKey localPrivKey;
    private PublicKey peerPubKey;


    public RSAHelper() {
    }

    /**
     * 初始化自己的私钥,对方的公钥以及密钥长度.
     *
     * @param localPrivKeyBase64Str Base64编码的私钥,PKCS#8编码. (去掉pem文件中的头尾标识)
     * @param peerPubKeyBase64Str   Base64编码的公钥. (去掉pem文件中的头尾标识)
     * @param keysize               密钥长度, 一般2048
     */
    public void initKey(String localPrivKeyBase64Str,
                        String peerPubKeyBase64Str, int keysize) throws Exception {
        try {
            if (localPrivKeyBase64Str != null && !localPrivKeyBase64Str.isEmpty()) {
                localPrivKey = getPrivateKey(localPrivKeyBase64Str);
            }
            if (peerPubKeyBase64Str != null && !peerPubKeyBase64Str.isEmpty()) {
                peerPubKey = getPublicKey(peerPubKeyBase64Str);
            }
            KEYBIT = keysize;
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从文件中输入流中加载公钥
     *
     * @param in 公钥输入流
     * @throws Exception 加载公钥时产生的异常
     */
    public RSAPublicKey getPublicKey(InputStream in) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        try {
            String readLine = null;
            StringBuilder sb = new StringBuilder();
            while ((readLine = br.readLine()) != null) {
                if (readLine.charAt(0) == '-') {
                    continue;
                } else {
                    sb.append(readLine);
                    sb.append('\r');
                }
            }
            return getPublicKey(sb.toString());
        } catch (IOException e) {
            throw new Exception("公钥数据流读取错误");
        } catch (NullPointerException e) {
            throw new Exception("公钥输入流为空");
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (Exception e) {
                throw new Exception("关闭输入缓存流出错");
            }

            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                throw new Exception("关闭输入流出错");
            }
        }
    }

    /**
     * 从字符串中加载公钥
     *
     * @param publicKeyStr 公钥数据字符串
     * @throws Exception 加载公钥时产生的异常
     */
    public RSAPublicKey getPublicKey(String publicKeyStr) throws Exception {
        try {
            //byte[] buffer = Base64.getDecoder().decode(publicKeyStr);
            byte[] buffer = Base64RSA.decode(publicKeyStr.toCharArray());
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            RSAPublicKey publicKey = (RSAPublicKey) keyFactory
                    .generatePublic(keySpec);
            return publicKey;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("公钥非法");
        } catch (NullPointerException e) {
            throw new Exception("公钥数据为空");
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    /**
     * 从文件中加载私钥
     *
     * @param in 私钥文件名
     * @throws Exception
     * @return是否成功
     */
    public RSAPrivateKey getPrivateKey(InputStream in) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        try {
            String readLine = null;
            StringBuilder sb = new StringBuilder();
            while ((readLine = br.readLine()) != null) {
                if (readLine.charAt(0) == '-') {
                    continue;
                } else {
                    sb.append(readLine);
                    sb.append('\r');
                }
            }
            return getPrivateKey(sb.toString());
        } catch (IOException e) {
            throw new Exception("私钥数据读取错误");
        } catch (NullPointerException e) {
            throw new Exception("私钥输入流为空");
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (Exception e) {
                throw new Exception("关闭输入缓存流出错");
            }

            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                throw new Exception("关闭输入流出错");
            }
        }
    }

    /**
     * 从字符串中加载私钥
     *
     * @param privateKeyStr 公钥数据字符串
     * @throws Exception 加载私钥时产生的异常
     */
    public RSAPrivateKey getPrivateKey(String privateKeyStr) throws Exception {
        try {
            byte[] buffer = Base64RSA.decode(privateKeyStr.toCharArray());
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory
                    .generatePrivate(keySpec);
            return privateKey;

//			BASE64Decoder base64Decoder = new BASE64Decoder();
//			byte[] buffer = base64Decoder.decodeBuffer(publicKeyStr);
//			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
//			RSAPublicKey publicKey = (RSAPublicKey) keyFactory
//					.generatePublic(keySpec);

        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            throw new Exception("私钥非法");
        } catch (NullPointerException e) {
            throw new Exception("私钥数据为空");
        }
    }

    /**
     * RAS加密
     *
     * @param plainBytes 公钥
     * @param charset    待加密信息
     * @return byte[]
     * @throws Exception
     */
    public byte[] encryptRSA(byte[] plainBytes, boolean useBase64Code, String charset)
            throws Exception {
        String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding"; // 加密block需要预留11字节
        int RESERVEBYTES = 11;
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        int decryptBlock = KEYBIT / 8; // 256 bytes
        int encryptBlock = decryptBlock - RESERVEBYTES; // 245 bytes
        // 计算分段加密的block数 (向上取整)
        int nBlock = (plainBytes.length / encryptBlock);
        if ((plainBytes.length % encryptBlock) != 0) { // 余数非0，block数再加1
            nBlock += 1;
        }
        // 输出buffer, 大小为nBlock个decryptBlock
        ByteArrayOutputStream outbuf = new ByteArrayOutputStream(nBlock
                * decryptBlock);
        cipher.init(Cipher.ENCRYPT_MODE, peerPubKey);
        // cryptedBase64Str =
        // Base64.encodeBase64String(cipher.doFinal(plaintext.getBytes()));
        // 分段加密
        for (int offset = 0; offset < plainBytes.length; offset += encryptBlock) {
            // block大小: encryptBlock 或剩余字节数
            int inputLen = (plainBytes.length - offset);
            if (inputLen > encryptBlock) {
                inputLen = encryptBlock;
            }
            // 得到分段加密结果
            byte[] encryptedBlock = cipher.doFinal(plainBytes, offset, inputLen);
            // 追加结果到输出buffer中
            outbuf.write(encryptedBlock);
        }
        // 如果是Base64编码，则返回Base64编码后的数组
        if (useBase64Code) {
            return new String(Base64RSA.encode(outbuf.toByteArray())).getBytes();
			/*return Base64.encodeBase64String(outbuf.toByteArray()).getBytes(
					charset);*/
        } else {
            return outbuf.toByteArray(); // ciphertext
        }
    }

    /**
     * RSA解密
     *
     * @param charset      私钥
     * @param cryptedBytes 待解密信息
     * @return byte[]
     * @throws Exception
     */
    public byte[] decryptRSA(byte[] cryptedBytes, boolean useBase64Code,
                             String charset) throws Exception {
        String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding"; // 加密block需要预留11字节
        byte[] data = null;

        // 如果是Base64编码的话，则要Base64解码
        if (useBase64Code) {
            data = Base64RSA.decode(new String(cryptedBytes, charset).toCharArray());
        } else {
            data = cryptedBytes;
        }

        int RESERVEBYTES = 11;
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        int decryptBlock = KEYBIT / 8; // 256 bytes
        int encryptBlock = decryptBlock - RESERVEBYTES; // 245 bytes
        // 计算分段解密的block数 (理论上应该能整除)
        int nBlock = (data.length / decryptBlock);
        // 输出buffer, , 大小为nBlock个encryptBlock
        ByteArrayOutputStream outbuf = new ByteArrayOutputStream(nBlock
                * encryptBlock);
        cipher.init(Cipher.DECRYPT_MODE, localPrivKey);
        // plaintext = new
        // String(cipher.doFinal(Base64.decodeBase64(cryptedBase64Str)));
        // 分段解密
        for (int offset = 0; offset < data.length; offset += decryptBlock) {
            // block大小: decryptBlock 或剩余字节数
            int inputLen = (data.length - offset);
            if (inputLen > decryptBlock) {
                inputLen = decryptBlock;
            }

            // 得到分段解密结果
            byte[] decryptedBlock = cipher.doFinal(data, offset, inputLen);
            // 追加结果到输出buffer中
            outbuf.write(decryptedBlock);
        }
        outbuf.flush();
        outbuf.close();
        return outbuf.toByteArray();
    }

    /**
     * RSA签名
     *
     * @param plainBytes 私钥
     * @param charset    需要签名的信息
     * @return byte[]
     * @throws Exception
     */
    public byte[] signRSA(byte[] plainBytes, boolean useBase64Code,
                          String charset) throws Exception {
        String SIGNATURE_ALGORITHM = "SHA1withRSA";
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(localPrivKey);
        signature.update(plainBytes);

        // 如果是Base64编码的话，需要对签名后的数组以Base64编码
        if (useBase64Code) {
            return new String(Base64RSA.encode(signature.sign()))
                    .getBytes(charset);
        } else {
            return signature.sign();
        }
    }

    /**
     * 验签操作
     *
     * @param charset    公钥
     * @param plainBytes 需要验签的信息
     * @param signBytes  签名信息
     * @return boolean
     */
    public boolean verifyRSA(byte[] plainBytes, byte[] signBytes,
                             boolean useBase64Code, String charset) throws Exception {
        boolean isValid = false;
        try {
            String SIGNATURE_ALGORITHM = "SHA1withRSA";
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initVerify(peerPubKey);
            signature.update(plainBytes);

            // 如果是Base64编码的话，需要对验签的数组以Base64解码
            if (useBase64Code) {
                isValid = signature.verify(Base64RSA.decode(new String(
                        signBytes, charset).toCharArray()));
            } else {
                isValid = signature.verify(signBytes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isValid;
    }


    public static Map<String, String> createKey() {
        Map<String, String> keyMap = new HashMap<String, String>();
        KeyPairGenerator kpg;
        try {
            kpg = KeyPairGenerator.getInstance(KEY_ALGORITHM);
            kpg.initialize(KEYBIT);
            KeyPair kp = kpg.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey) kp.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) kp.getPrivate();

            String pubKey = new String(Base64RSA.encode(publicKey.getEncoded()));
            String priKey = new String(Base64RSA.encode(privateKey.getEncoded()));
            keyMap.put(PUBLIC_KEY, pubKey);
            keyMap.put(PRIVATE_KEY, priKey);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return keyMap;
    }

    public static void main(String[] args) {
        String sysPubKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtOKfzDEODmBIf8obugJP6L8p8s8VBNbTJM8rxUKmhYH1qq5eHGDoMerjz6dCOEkaO0T6zULVpBmpBvPT3VU22Ii66ijru1OaUAzNFx1N5hBJQsLiYL5FeikFKFFYnloBwNp8HUiNos3aLDqKFX4Gk00v5GK+pb5mLLqMZ6GBmsHpEYmplnqsl2G9lgEjdVqeCoFlrN0dYJdcCRGpc+2tAbMlTcvRVprvTu7wfQbfdRyOhYWvvbP8m43BfwiNTkLb7oArCPv7Iqd1KLixmYcrpzR9MNaublGkn5w4HwjyCTdGhzSl9OgV+AJyXsisAno58r/Fk3qfTSXfzRhsKHUcqwIDAQAB";
        System.out.println("平台提供商户的验签公钥:\n"+sysPubKey);
        Map<String, String> keyMap = createKey();
        System.out.println("商户提供的公钥对");
        System.out.println("签名私钥（商户请求平台时数据签名使用，商户自行管理）:\n"+keyMap.get(PRIVATE_KEY));
        System.out.println("验签公钥（商户请求平台数据，平台验签使用，商户提供给平台）:\n"+keyMap.get(PUBLIC_KEY));
    }
}