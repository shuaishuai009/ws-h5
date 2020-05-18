package com.wmx.thymeleafapp.utils;

import java.util.Arrays;

/**
 * @author wangmaoxiong
 * @version 1.0
 * @date 2020/5/17 15:41
 */
public class DataTypeConvertUtils {

    /**
     * 普通字符串转16进制字符串
     *
     * @param strSource 待转换字符串，如 "45 5A 43 2F 56 00"
     *                  每一个字符转换后都是2个16进制字符，如 4对34,5对35，空格对20...
     * @return 转换好的16进制字符串 ，如 "3435203541203433203246203536203030",每两位对应一个原始字符
     */
    public static String string2HexString(String strSource) {
        if (strSource == null || "".equals(strSource.trim())) {
            System.out.println("string2HexString 参数为空，放弃转换.");
            return null;
        }
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < strSource.length(); i++) {
            int ch = (int) strSource.charAt(i);
            String strHex = Integer.toHexString(ch);
            hexString.append(strHex);
        }
        return hexString.toString();
    }

    /**
     * 16进制字符串转普通字符串
     * 与上面的 string2HexString 方法对应
     *
     * @param strSource 16进制字符串，如 "3435203541203433203246203536203030"
     * @return 转换好的普通字符串 ，如 "45 5A 43 2F 56 00"
     */
    public static String hexString2String(String strSource) {
        if (strSource == null || "".equals(strSource.trim())) {
            System.out.println("hexString2String 参数为空，放弃转换.");
            return null;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < strSource.length() / 2; i++) {
            sb.append((char) Integer.valueOf(strSource.substring(i * 2, i * 2 + 2), 16).byteValue());
        }
        return sb.toString();
    }

    /**
     * 16进制字符串转十进制字节数组
     * 这是常用的方法，如某些硬件的通信指令就是提供的16进制字符串，发送时需要转为字节数组再进行发送
     *
     * @param strSource 16进制字符串，如 "455A432F5600"，每两位对应字节数组中的一个10进制元素
     *                  默认会去除参数字符串中的空格，所以参数 "45 5A 43 2F 56 00" 也是可以的
     * @return 十进制字节数组, 如 [69, 90, 67, 47, 86, 0]
     */
    public static byte[] hexString2Bytes(String strSource) {
        if (strSource == null || "".equals(strSource.trim())) {
            System.out.println("hexString2Bytes 参数为空，放弃转换.");
            return null;
        }
        strSource = strSource.replace(" ", "");
        int l = strSource.length() / 2;
        byte[] ret = new byte[l];
        for (int i = 0; i < l; i++) {
            ret[i] = Integer.valueOf(strSource.substring(i * 2, i * 2 + 2), 16).byteValue();
        }
        return ret;
    }

    /**
     * 10 进制字节数组转 16 进制字符串（因为16进制中含有A-F，所以只能用字符串表示）
     * 对应上面的 hexString2Bytes 方法
     *
     * @param b  :待转换的10进制字节数组，如 new byte[]{69, 83, 67, 47, 86, 80, 46, 110, 101, 116, 16, 3, 0, 0, 0, 0};
     * @param isHaveBlank 转换的结果是否用空格隔开，true 时如 "45 5A 43 2F 56 00"，false 时如 "455A432F5600"
     * @return 转换好的 16进制字符串，如 "45 53 43 2F 56 50 2E 6E 65 74 10 03 00 00 00 00 "
     */
    public static String bytes2HexString(byte[] b, boolean isHaveBlank) {
        if (b == null || b.length <= 0) {
            System.out.println("bytes2HexString 参数错误，放弃转换.");
            return null;
        }
        StringBuffer result = new StringBuffer();
        String hex;
        for (int i = 0; i < b.length; i++) {
            hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            if (isHaveBlank) {
                result.append(hex.toUpperCase() + " ");
            } else {
                result.append(hex.toUpperCase());
            }
        }
        return result.toString();
    }

    public static void main(String[] args) {
        String ordinaryString = "45 5A 43 2F 56 00";
        String hexString_result = string2HexString(ordinaryString);
        System.out.println("原字符串：" + ordinaryString);
        System.out.println("转换后16进制字符串：" + hexString_result);
        System.out.println("16进制字符串反转普通字符串：" + hexString2String(hexString_result));

        String hexString = "45 53 43 2F 56 50 2E 6E 65 74 10 03 00 00 00 00 ";
        byte[] bytes = hexString2Bytes(hexString);
        System.out.println("\n原16进制字符串：" + hexString);
        System.out.println("转换好的10进制字节数组：" + Arrays.toString(bytes));
        System.out.println("10进制数组反转16进制字符串：" + bytes2HexString(bytes, true));
    }
}