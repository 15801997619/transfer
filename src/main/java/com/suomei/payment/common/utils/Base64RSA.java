package com.suomei.payment.common.utils;

/**
 * com.nfcgo.sdk.util -->
 *
 */

public class Base64RSA {

    private static char alphabet[] =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".toCharArray();
    private static byte codes[];

    public Base64RSA() {
    }

    public static byte[] decode(char ac[]) throws Exception {
        return decode(ac, true);
    }

    public static byte[] decode(char ac[], boolean flag) throws Exception {
        int i = ac.length;
        char ac1[] = ac;
        int k = ac1.length;
        for (int l = 0; l < k; l++) {
            char c = ac1[l];
            byte byte0 = codes[c & 0xff];
            if (byte0 < 0 && c != '=') {
                i--;
            }
        }

        int j = ((i + 3) / 4) * 3;
        if (i > 0 && ac[i - 1] == '=') {
            j--;
        }
        if (i > 1 && ac[i - 2] == '=') {
            j--;
        }
        byte abyte0[] = new byte[j];
        int i1 = 0;
        int j1 = 0;
        int k1 = 0;
        char ac2[] = ac;
        int l1 = ac2.length;
        for (int i2 = 0; i2 < l1; i2++) {
            char c1 = ac2[i2];
            byte byte1 = codes[c1 & 0xff];
            if (byte1 < 0) {
                continue;
            }
            j1 <<= 6;
            i1 += 6;
            j1 |= byte1;
            if (i1 >= 8) {
                i1 -= 8;
                abyte0[k1++] = (byte) (j1 >> i1 & 0xff);
            }
        }

        if (flag && k1 != abyte0.length) {
            throw new Exception((new StringBuilder()).append("Miscalculated data length (wrote ").append(k1).append(" instead of ").append(abyte0.length).append(")").toString());
        } else {
            return abyte0;
        }
    }

    public static char[] encode(byte abyte0[]) {
        char ac[] = new char[((abyte0.length + 2) / 3) * 4];
        int i = 0;
        for (int j = 0; i < abyte0.length; j += 4) {
            boolean flag = false;
            boolean flag1 = false;
            int k = 0xff & abyte0[i];
            k <<= 8;
            if (i + 1 < abyte0.length) {
                k |= 0xff & abyte0[i + 1];
                flag1 = true;
            }
            k <<= 8;
            if (i + 2 < abyte0.length) {
                k |= 0xff & abyte0[i + 2];
                flag = true;
            }
            ac[j + 3] = alphabet[flag ? k & 0x3f : 64];
            k >>= 6;
            ac[j + 2] = alphabet[flag1 ? k & 0x3f : 64];
            k >>= 6;
            ac[j + 1] = alphabet[k & 0x3f];
            k >>= 6;
            ac[j] = alphabet[k & 0x3f];
            i += 3;
        }

        return ac;
    }

    static {
        codes = new byte[256];
        for (int i = 0; i < 256; i++) {
            codes[i] = -1;
        }

        for (int j = 65; j <= 90; j++) {
            codes[j] = (byte) (j - 65);
        }

        for (int k = 97; k <= 122; k++) {
            codes[k] = (byte) ((26 + k) - 97);
        }

        for (int l = 48; l <= 57; l++) {
            codes[l] = (byte) ((52 + l) - 48);
        }

        codes[43] = 62;
        codes[47] = 63;
    }
}
