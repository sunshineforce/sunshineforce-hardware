package com.sunshineforce.hardware.util;

public class ByteUtil {

    public static String ByteToHex(byte[] byteArr){
        StringBuilder buf = new StringBuilder(byteArr.length * 2);
        for(byte b : byteArr) { // 使用String的format方法进行转换
            buf.append(String.format("%02x", new Integer(b & 0xff)));
        }

        return buf.toString();
    }

    public static byte[] HexToByte(String hexString){
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static String hexToStr(String hexStr) {
        String str = "0123456789ABCDEF";
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int n;
        for (int i = 0; i < bytes.length; i++) {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        return new String(bytes);
    }

    public static byte[] strToByte(String str){
        return str.getBytes();
    }

    public static String strToHex(String str){
        String param = "";
        for (int i = 0; i < str.length(); i++) {
            int ch = (int) str.charAt(i);
            String hex = Integer.toHexString(ch);
            if(hex.length() < 2){
                hex = "0" + hex;
            }
            param = param + hex;
        }
        return param;
    }
    public static String intToHex(int num){
        return Integer.toHexString(num);
    }

    public static int hexToInt(String hex){
        int num = Integer.parseInt(hex, 16) - 256;
        System.out.println(num);
        num = (~(Math.abs(num))) + 1;
        return Math.abs(num);
    }

    public static String byteToBit(byte b){
        return ""
                + (byte) ((b >> 7) & 0x1) + (byte) ((b >> 6) & 0x1)
                + (byte) ((b >> 5) & 0x1) + (byte) ((b >> 4) & 0x1)
                + (byte) ((b >> 3) & 0x1) + (byte) ((b >> 2) & 0x1)
                + (byte) ((b >> 1) & 0x1) + (byte) ((b >> 0) & 0x1);
    }

    public static void main(String args[]){
        System.out.println(hexToStr("6c6a"));
    }
}
