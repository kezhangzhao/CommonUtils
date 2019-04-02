package com.kzz.utilslibrary;

import android.content.Context;
import android.net.Uri;

import java.io.File;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文本工具类
 */
public class TextUtils {

    public static final Pattern WEB_PATTERN =
            Pattern.compile("(http://|ftp://|https://|www)?[^\u4e00-\u9fa5\\s\uFE30-\uFFA0]*?\\.([a-zA-Z0-9]|[1-255])[^\u4e00-\u9fa5\\s\uFE30-\uFFA0]*");

    /**
     * 验证文本是否为空
     *
     * @param text 待验证的文本
     * @return 空 true 否则 false
     */
    public static boolean empty(CharSequence text) {
        if (android.text.TextUtils.isEmpty(text)) {
            return true;
        } else if (text.length() == 4
                && text.toString().toUpperCase().equals("NULL")) {
            return true;
        }
        return false;
    }

    public static boolean empty(long text) {
        return false;
    }

    public static boolean equal(long text1, String text2) {
        if (empty(text2))
            return false;
        else if (String.valueOf(text1).equals(text2))
            return true;
        return false;
    }

    public static boolean equal(long l1, long l2) {
        return l1 == l2;
    }

    public static boolean equal(String text1, String text2) {
        if (empty(text1) || empty(text2))
            return false;
        else if (text1.equals(text2))
            return true;
        return false;
    }

    public static boolean isEmail(String text) {
        if (empty(text)) return false;
        //运营那边反应部分手机注册不成功,添加了个中文－
        Pattern p = Pattern.compile(
                "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+\\－]{1,256}" +
                        "\\@" +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                        "(" +
                        "\\." +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                        ")+"
        );
        return text.matches(p.pattern());
    }

    /**
     * 判断是否手机号
     *
     * @param text 手机号
     * @return boolean
     */
    public static boolean isMobile(String text) {
        if (empty(text)) return false;
        return text.matches("^(13|14|15|17|18)\\d{9}$");
    }

    public static boolean isWebSite(String text) {
        if (empty(text)) return false;
        return text.matches(WEB_PATTERN.pattern());
    }

    public static boolean isNumber(String text) {
        if (empty(text)) return false;
        try {
            Double.parseDouble(text);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断是否有此文件
     *
     * @param path 文件路径
     * @return 是否有此文件
     */
    public static boolean hasFile(String path) {
        return getFile(path) != null;
    }

    /**
     * 获取是否有此文件
     *
     * @param text 文件路径
     * @return 文件
     */
    public static File getFile(String text) {
        if (empty(text)) return null;
        File file = new File(text);
        if (file.exists()) {
            return file;
        }
        return null;
    }

    public static String getFloat(float number, int index) {
        return getDouble(number, index);
    }

    public static String getDouble(double number, int index) {
        String str = "##0";
        if (index > 0) {
            str += ".";
            for (int i = 0; i < index; i++) {
                str += "0";
            }
        }
        DecimalFormat fnum = new DecimalFormat(str);
        return fnum.format(number);
    }

    public static double getDouble(String text) {
        if (isNumber(text)) {
            return Double.parseDouble(text);
        }
        return 0;
    }

    public static String formatDouble(double f) {
        DecimalFormat df = new DecimalFormat("#.00");
        if (f < 1) {
            return "0" + df.format(f);
        } else {
            return df.format(f);
        }
    }

    public static String formatDouble(String str) {
        DecimalFormat fmt = new DecimalFormat("0.00");
        String outStr = null;
        double d;
        if (empty(str)) {
            return "";
        }
        try {
            d = Double.parseDouble(str);
            outStr = fmt.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outStr;
    }

    public static int getInt(String text) {
        return getInt(text, 0);
    }

    public static int getInt(long text) {
        return (int) text;
    }

    public static int getInt(String text, int def) {
        if (isNumber(text)) {
            return Integer.parseInt(text);
        }
        return def;
    }

    public static long getLong(String text) {
        if (isNumber(text)) {
            return Long.parseLong(text);
        }
        return 0;
    }

    public static Uri getUri(String text) {
        if (!empty(text)) {
            return Uri.parse(text);
        }
        return null;
    }

    public static String getString(Context mContext, int resId) {
        try {
            return mContext.getString(resId);
        } catch (Throwable t) {
            // 不需处理
            return "";
        }
    }

    /**
     * 是否为4-6位数字的验证码
     */
    public static boolean isCode(String text) {
        return Pattern.compile("^\\d{4,6}$").matcher(text).find();
    }

    public static String unicodeToCn(String theString) {
        char aChar;
        int len = theString.length();
        StringBuilder outBuffer = new StringBuilder(len);
        for (int x = 0; x < len; ) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed   \\uxxxx   encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    outBuffer.append(aChar);
                }

            } else
                outBuffer.append(aChar);
        }
        return outBuffer.toString();
    }

    /**
     * 为6~16位数字加字母
     */
    public static final String GER_PASSWORD = "[a-zA-Z0-9]{6,16}";

    /**
     * 是否为6-25位密码
     */
    public static boolean isPassword(String text) {
        return text.matches(GER_PASSWORD);
    }

    /**
     * 判断是否QQ号码
     *
     * @param str QQ号码
     * @return boolean
     */
    public static boolean isQQStr(String str) {
        String regex = "[1-9][0-9]{4,14}";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        return m.matches();
    }
}
